<?php
/**
 * Created by PhpStorm.
 * User: zhangtaichao
 * Date: 15/11/2
 * Time: 下午3:53
 */

namespace Laravel\Service;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;
use Laravel\Model\ActivityModel;
use Laravel\Model\ActivityPeriodModel;
use Laravel\Model\JnlDeductModel;
use Laravel\Model\RechargeModel;
use Laravel\Model\JnlTransModel;
use Laravel\Model\JnlTransDuobaoModel;
use Laravel\Model\LotteryMethodModel;



class PeriodService {

    protected $activityModel;
    protected $rechargeModel;
    protected $jnlTransModel;
    protected $jnlTransDuobaoModel;
    protected $jnlDeductModel;
    protected $jnlService;
    protected $activityPeriodModel;

    private $nowTime ;
    protected $logContext = array(__CLASS__);
    public function __construct() {
        $this->activityModel = new ActivityModel();
        $this->rechargeModel = new RechargeModel();
        $this->jnlTransModel = new JnlTransModel();
        $this->jnlTransDuobaoModel = new JnlTransDuobaoModel();
        $this->jnlDeductModel = new JnlDeductModel();
        $this->jnlService = new JnlService();
        $this->activityPeriodModel = new ActivityPeriodModel();
        $this->nowTime = date('Y-m-d H:i:s');
    }


    /**
     * 余额支付，夺宝
     */
    public function duobaoUseBanance($jnl_no) {
        return $this->duobao($jnl_no,JnlService::JnlTrans_Paytype_balance);
    }

    /**
     * 需要充值，等待回调，夺宝
     */
    public function duobaoNeedRecharge($jnl_no) {
        return $this->duobao($jnl_no,JnlService::JnlTrans_Paytype_needRecharge);
    }

    private function duobao($jnl_no,$pay_type) {
        $result = [
            'code' => 'error'
        ];
        $jnlInfo = $this->jnlTransModel->load($jnl_no);
        Log::debug("jnlInfo=>" . var_export($jnlInfo,true),$this->logContext);
        if(empty($jnl_no)) {
            $result['msg'] = "流水号不存在:jnl_no=>{$jnl_no}";
            Log::warning($result['msg'],$this->logContext);
            return $result;
        }else{
            if($jnlInfo->trans_code == JnlService::TransCode_Recharge){
                return;
            }
        }

        $jnl_status = $jnlInfo->jnl_status;

        if($pay_type == JnlService::JnlTrans_Paytype_balance) {//余额支付
            if($jnl_status != JnlService::JnlTrans_Status_new) {
                $result['msg'] = '订单状态不正确';
                Log::warning($result['msg'],$this->logContext);
                return $result;
            }
        } else {//需等回调结果支付
            if($jnl_status != JnlService::JnlTrans_Status_pay_success) {//支付未成功
                $result['msg'] = '支付尚未成功';
                Log::warning($result['msg'],$this->logContext);
                return $result;
            }
        }

        $userInfo = $this->rechargeModel->getUserMoney($jnlInfo->user_id);
        Log::debug('userInfo=>' . var_export($userInfo,true),$this->logContext);

        //检查余额
        if(!$this->isBalanceEnough($userInfo,$jnlInfo->amount)) {
            $result['msg'] = '余额不足,需充值';
            return $result;
        }
        $dbr = $this->_duobao($jnlInfo,$userInfo,$pay_type);
        if(is_bool($dbr) && $dbr == true) {
            $result['code'] = 'success';
        } else {
            $result = array_merge($result,$dbr);
        }
        return $result;
    }

    private function _duobao($jnlInfo,$userInfo,$pay_type) {
        $flag = false;
        $result['code'] = 'error';
        $duobaoInfo = $this->jnlTransDuobaoModel->getByJnlNo($jnlInfo->jnl_no);
        if(empty($duobaoInfo) || empty($userInfo)) {
            $this->jnlService->updateJnlTransStatus($jnlInfo->jnl_no,JnlService::JnlTrans_Status_finished,"查询夺宝流水失败jnl_no=>{$jnlInfo->jnl_no}",$pay_type);
            Log::warning("查询夺宝流水失败jnl_no=>{$jnlInfo->jnl_no}");
            return false;
        }

        $user_id = $userInfo->id;
        $user_money = $userInfo->money;
        $amount = $jnlInfo->amount;
        $jnl_no = $jnlInfo->jnl_no;
        $duobao_jnl_id = $duobaoInfo->id;
        $period_id = $duobaoInfo->sh_activity_period_id;
        $num = $duobaoInfo->num;

        DB::beginTransaction();

        try{
            //扣款
            if(!$this->_deduct($user_id,$jnl_no,$user_money,$amount)) {
                DB::rollBack();
                $this->jnlService->updateDuobaoStatus($duobao_jnl_id,JnlService::Duobao_Status_fail,'扣款操作失败，交易失败');
                $this->jnlService->updateJnlTransStatus($jnlInfo->jnl_no,JnlService::JnlTrans_Status_finished,'夺宝失败',$pay_type);
                $result['msg'] = '扣款操作失败，交易失败';
                return $result;
            }
            //更新夺宝次数
            if(!$this->activityPeriodModel->updatePeriodTimesWithCheck($period_id,$num)) {
                DB::rollBack();
                $this->jnlService->updateDuobaoStatus($duobao_jnl_id,JnlService::Duobao_Status_fail,'所余夺宝次数不足');
                $this->jnlService->updateJnlTransStatus($jnlInfo->jnl_no,JnlService::JnlTrans_Status_finished,'夺宝失败',$pay_type);
                $result['msg'] = '所余夺宝次数不足';
                return $result;
            }
            //增加夺宝记录
            $periodUserData = array(
                'sh_activity_period_id' => $period_id,
                'create_time'			=> $this->nowTime,
                'user_id'				=> $user_id,
                'times'					=> $num,
                'buy_id'				=> $jnl_no
            );
            $period_user_id = $this->activityPeriodModel->addPeriodUser($periodUserData);
            if(empty($period_user_id)) {
                DB::rollBack();
                $this->jnlService->updateDuobaoStatus($duobao_jnl_id,JnlService::Duobao_Status_fail,'增加夺宝记录失败(addPeriodUser)');
                $this->jnlService->updateJnlTransStatus($jnlInfo->jnl_no,JnlService::JnlTrans_Status_finished,'夺宝失败',$pay_type);
                $result['msg'] = '增加夺宝记录失败';
                return $result;
            }

            // 购买的夺宝号
            $lotteryMethodM = new LotteryMethodModel();
            //获取随机夺宝号
            $isUp = $lotteryMethodM->getRandomCode($period_id, $user_id, "'{$jnl_no}'" , $num, $period_user_id );
            if(!$isUp) {
                DB::rollBack();
                $this->jnlService->updateDuobaoStatus($duobao_jnl_id,JnlService::Duobao_Status_fail,'获取夺宝号失败');
                $this->jnlService->updateJnlTransStatus($jnlInfo->jnl_no,JnlService::JnlTrans_Status_finished,'夺宝失败',$pay_type);
                $result['msg'] = '获取夺宝号失败';
                return $result;
            }

            $this->jnlService->updateDuobaoStatus($duobao_jnl_id,JnlService::Duobao_Status_success,'夺宝成功');
            $this->jnlService->updateJnlTransStatus($jnl_no,JnlService::JnlTrans_Status_finished,'夺宝成功',$pay_type);
            DB::commit();

        } catch (\Exception $e) {
            DB::rollBack();
            Log::error($e->getTraceAsString(),$this->logContext);
            $this->jnlService->updateDuobaoStatus($duobao_jnl_id,JnlService::Duobao_Status_fail,$e->getMessage());
            $this->jnlService->updateJnlTransStatus($jnlInfo->jnl_no,JnlService::JnlTrans_Status_finished,'夺宝失败',$pay_type);
            throw $e;
        }
        $this->checkAndAddPeriodResult($period_id);
        $addResult = $this->checkAndNextPeriod($period_id);
        if(is_array($addResult)) {
            if(isset($addResult['message'])) {
                Log::info("添加期结果:period_id=>{$period_id},{$addResult['message']}");
            } else {
                Log::info("添加期结果:period_id=>{$period_id}" . var_export($addResult,true));
            }
        }
        return true;
    }

    //扣款并记录日志
    private function _deduct($user_id,$trans_jnl_no,$oldMoney,$deductMoney) {
        if(empty($user_id) || empty($trans_jnl_no) || !is_numeric($oldMoney) || !is_numeric($deductMoney)) {
            return false;
        }
        $latestMoney = $oldMoney - $deductMoney;
        $num = $this->rechargeModel->updateUserMoney($latestMoney,$user_id);
        if($num > 0) {
            $data = [
                'user_id' => $user_id,
                'trans_jnl_no' => $trans_jnl_no,
                'amount' => $deductMoney,
                'latest_balance' => $latestMoney,
                'create_time' => $this->nowTime
            ];
            $id = $this->jnlDeductModel->add($data);
            Log::info('扣款结果=>' . $id,$this->logContext);
            return empty($id) ? false : true;
        } else {
            Log::error('更新用户余额失败',$this->logContext);
            return false;
        }
    }


    /**
     * 判断某期是否可以进行夺宝
     * @param $period_id
     * @return bool true=可以夺宝 false=不可以
     * true=可以夺宝 false=不可以
     */
    public function isPeriodValid($period_id,$num=null) {

        $periodInfo = $this->activityModel->findPeriodById($period_id);
        if(empty($periodInfo)) {
            return false;
        }
        if($periodInfo->flag != '1') {//非有效期数
            Log::warning('当前期已完成=>' . var_export($periodInfo,true),$this->logContext);
            return false;
        }
        if($periodInfo->current_times >= $periodInfo->real_need_times) {//当前人次已到达所需人次
            Log::warning('当前人次已到达所需人次=>' . var_export($periodInfo,true),$this->logContext);
            return false;
        }
        if(!empty($num)) {
            if($periodInfo->real_need_times < $periodInfo->current_times + $num) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断用户余额是否足够支付
     * @param $userInfo 用户信息
     * @param $amount 所需金额
     * @return bool
     */
    public function isBalanceEnough($userInfo,$amount) {

        if(empty($userInfo)) {
            Log::warning('查询用户信息为空user_id',$this->logContext);
            return false;
        }
        $bal = (double)$userInfo->money;
        $amount = (double)$amount;
        if($bal < $amount) {
            Log::warning("用户余额不足,所需{$amount},实际=>" . var_export($userInfo,true),$this->logContext);
            return false;
        }
        return true;
    }

    /**
     * 检查通过，则新增期结果记录
     * @param $period_id
     */
    public function checkAndAddPeriodResult($period_id) {
        if($this->isPeriodFull($period_id)) {
            $this->addPeriodResult($period_id);
        }
    }

    /**
     * 检查当前期是否已满
     */
    private function isPeriodFull($period_id) {
        $periodDetail = $this->activityPeriodModel->load($period_id);
        if(empty($periodDetail)) {
            return false;
        }

        if($periodDetail->flag != '1') {
            return false;
        }

        if($periodDetail->current_times >= $periodDetail->real_need_times) {
            Log::info("当前期所需人次已满:period_id=>{$period_id}");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 新增中奖结果
     * @param $period_id
     */
    private function addPeriodResult($period_id) {
        $this->activityModel->addPeriodResult($period_id);
        Log::info("生成结果记录:period_id=>{$period_id}");
    }

    /**
     * @param $activity_id
     * @return array
     */
    public function createPeriod($activity_id) {
        $result = array(
            'code' => 'error'
        );

        $activityDetail = $this->activityModel->getActivityById($activity_id);

        if(empty($activityDetail) || $activityDetail->is_online == '0') {
            $result['message'] = '活动已下线';
            return $result;
        }

        $latestPeriod = $this->activityModel->getLatestPeriodByActivityId($activity_id);

        $period_num = $activityDetail->current_period + 1;

        if(!empty($latestPeriod)) {
            if($latestPeriod->flag == '1') {//正在进行
                $result['message'] = '该活动已有正在进行的期，添加期失败';
            }elseif((int)$latestPeriod->period_number >= (int)$activityDetail->max_period) {
                $result['message'] = '活动已达最大期数，添加期失败';
            }
            $period_num = $latestPeriod->period_number + 1;
        }
        if(isset($result['message'])) {
            return $result;
        }


        return DB::transaction(function() use ($period_num,$activity_id,$activityDetail,$result) {
            $param = array(
                'period_number' => $period_num,
                'sh_activity_id' => $activity_id,
                'create_time'  => date('Y-m-d H:i:s'),
                'current_times' => 0,
                'real_need_times' => $activityDetail->need_times,
                'real_price' => $activityDetail->price,
                'flag' => '1'
            );

            $periodId = $this->activityModel->addActivityPeriod($param);
            Log::info("period_id;" + $periodId);
            $this->activityModel->updateActivity($activity_id,array('current_period'=>$period_num));
            $lotteryMethodM = new LotteryMethodModel();
            $nums = $lotteryMethodM->createCodeNum($periodId);
            Log::info("添加夺宝号码数:{$nums}");
            $result['message'] = '添加期成功';
            $result['code'] = 'success';
            return $result;
        });
    }

    /**
     * 检查通过则新增一期
     * @param $period_id
     * @return array|bool
     */
    public function checkAndNextPeriod($period_id) {
        $periodDetail = $this->activityPeriodModel->load($period_id);
        if(empty($periodDetail)) {
            return false;
        }
        if($periodDetail->flag == '0') {
            Log::info("准备生成下一期,period_id=>{$period_id}");
            return $this->createPeriod($periodDetail->sh_activity_id);
        }
    }
}