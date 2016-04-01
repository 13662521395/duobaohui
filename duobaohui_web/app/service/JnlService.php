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
use Laravel\Model\JnlTransDuobaoModel;
use Laravel\Model\JnlTransModel;

class JnlService {

    private $activityModel;
    private $jnlTransModel;
    private $jnlTransDuobaoModel;
    private $nowTime ;

    /*
     * 夺宝交易交易码
     */
    const TransCode_Duobao = 'duobao';

    /**
     * 充值交易交易吗
     */
    const TransCode_Recharge = 'recharge';

    /**
     * 流水表状态:新建
     */
    const JnlTrans_Status_new = '0';//新建
    /**
     * 流水表状态:支付已成功
     */
    const JnlTrans_Status_pay_success = '1';//支付已成功
    /**
     * 流水表状态:支付失败
     */
    const JnlTrans_Status_pay_fail = '2';//支付失败
    /**
     * 流水表状态:超时未处理
     */
    const JnlTrans_Status_outtime = '3';
    /**
     * 流水表状态:交易完成
     */
    const JnlTrans_Status_finished = '4';

    /**
     * 夺宝状态:新建
     */
    const Duobao_Status_new = '0';//新建
    /**
     * 夺宝状态:成功
     */
    const Duobao_Status_success = '1';//夺宝成功
    /**
     * 夺宝状态:失败
     */
    const Duobao_Status_fail = '2';//夺宝失败

    /**
     * 支付类型：余额支付
     */
    const JnlTrans_Paytype_balance = '0';
    /**
     * 支付类型：需充值
     */
    const JnlTrans_Paytype_needRecharge = '1';

    /**
     * 支付渠道，支付宝支付
     */
    const Recharge_Channel_alipay = '0';

    /**
     * 支付渠道，微信支付
     */
    const Recharge_Channel_weixin = '1';

    protected $logContext = array(__CLASS__);

    public function __construct() {
        $this->activityModel = new ActivityModel();
        $this->jnlTransModel = new JnlTransModel();
        $this->jnlTransDuobaoModel = new JnlTransDuobaoModel();
        $this->nowTime = date('Y-m-d H:i:s');
    }


    /**
     * 夺宝交易生成一笔订单流水
     * @param $period_id
     * @param $num
     * @return array
     * @throws \Exception
     */
    public function createJnlTransDuobao($period_id,$num) {
        $result = [
            'code' => 'error'
        ];


        if(empty(UserService::getCurrentUserId())) {
            $result['code'] = '0';
            $result['msg'] = 'SESSION获取用户信息失败';
            return $result;
        }

        if(!is_numeric($period_id) || !is_numeric($num)) {
            $result['msg'] = '参数错误';
            return $result;
        }

        $user_id = UserService::getCurrentUserId();
        $result['code'] = 'error';

        $periodServcie = new PeriodService();
        if(!$periodServcie->isPeriodValid($period_id,$num)) {
            $result['msg'] = '当前期剩余人次不足';
            return $result;
        }

        $jnl_no = $this->generateJnlNo();

        $jnlTrans = [
            'jnl_no' => $jnl_no,
            'user_id' => $user_id,
            'trans_code' => self::TransCode_Duobao,
            'jnl_status' => self::JnlTrans_Status_new,
            'amount' => $num,
            'create_time' => $this->nowTime,
            'update_time' => $this->nowTime
        ];
        $jnlTransDuobao = [
            'trans_jnl_no' => $jnl_no,
            'sh_activity_period_id' => $period_id,
            'num' => $num,
            'status' => self::Duobao_Status_new,
            'create_time' => $this->nowTime,
            'update_time' => $this->nowTime
        ];

        DB::beginTransaction();
        try {
            $this->jnlTransModel->add($jnlTrans);
            $this->jnlTransDuobaoModel->add($jnlTransDuobao);
            Log::info("新增夺宝订单成功{$jnl_no}",$this->logContext);
            DB::commit();
            $result['jnl_no'] = $jnl_no;
            $result['code'] = 'success';
            return $result;
        }
        catch (\Exception $e) {
            DB::rollBack();
            Log::error(var_export($e->getTrace(),true),$this->logContext);
            throw $e;
        }
    }

    public function createJnlTransRecharge($amount,$recharge_channel) {
        $result = [
            'code' => 'error'
        ];
        if(empty(UserService::getCurrentUserId())) {
            $result['msg'] = 'SESSION获取用户信息失败';
            return $result;
        }
        if(empty($amount) || !is_numeric($amount) || !isset($recharge_channel)) {
            $result['msg'] = '参数错误';
            return $result;
        }
        $user_id = UserService::getCurrentUserId();
        $jnl_no = $this->generateJnlNo();
        $jnlTrans = [
            'jnl_no' => $jnl_no,
            'user_id' => $user_id,
            'trans_code' => self::TransCode_Recharge,
            'jnl_status' => self::JnlTrans_Status_new,
            'jnl_message' => '待付款',
            'pay_type' => self::JnlTrans_Paytype_needRecharge,
            'recharge_channel' => $recharge_channel,
            'amount' => $amount,
            'create_time' => $this->nowTime,
            'update_time' => $this->nowTime
        ];
        $this->jnlTransModel->add($jnlTrans);
        Log::info("新增充值订单成功{$jnl_no}",$this->logContext);
        $result['jnl_no'] = $jnl_no;
        $result['code'] = 'success';
        return $result;
    }

    /**
     * 更新流水记录状态
     */
    public function updateJnlTransStatus($jnl_no,$jnl_status,$jnl_message,$pay_type) {
        if(!isset($jnl_no) || !isset($jnl_status)) {
            return false;
        }

        $param = [
            'jnl_status' => $jnl_status,
            'update_time' => $this->nowTime
        ];
        if(isset($jnl_message)) {
            $param['jnl_message'] = $jnl_message;
        }
        if(isset($pay_type)) {
            $param['pay_type'] = $pay_type;
        }
        $num = $this->jnlTransModel->update($jnl_no,$param);

        return $num > 0 ? true : false;
     }


    /**
     * 支付回调，更新流水表信息
     */
    public function payCallbackUpdateJnl($jnl_no,$amount_pay,$jnl_recharge_id,$status,$recharge_channel) {

        if(empty($jnl_no) || !is_numeric($amount_pay) || !is_numeric($jnl_recharge_id) || empty($status)) {
            return false;
        }

        $param = [
            'jnl_status' => $status,
            'pay_type' => self::JnlTrans_Paytype_needRecharge,
            'recharge_channel' => $recharge_channel,
            'amount_pay' => $amount_pay,
            'jnl_recharge_id' => $jnl_recharge_id,
            'update_time' => $this->nowTime
        ];
        $num = $this->jnlTransModel->update($jnl_no,$param);
        return $num > 0 ? true : false;

    }

    /**
     * 更新夺宝状态
     */
    public function updateDuobaoStatus($id,$status,$message) {

        if(!isset($id) || !isset($status)) {
            return false;
        }
        $param = [
            'status' => $status,
            'update_time' => $this->nowTime
        ];
        if(isset($message)) {
            $param['message'] = $message;
        }
        $num = $this->jnlTransDuobaoModel->update($id,$param);
        return $num > 0 ? true : false;

    }

    /**
     * 生成唯一订单号
     */
    function generateJnlNo() {
        date_default_timezone_set('PRC');
        $yCode = array('A','B','C','D','E','F','G','H','I','J');
        $orderSn = '';
        $orderSn .= $yCode[(intval(date('Y')) - 1970) % 10];
        $orderSn .= strtoupper(dechex(date('m')));
        $orderSn .= date('d').substr(time(), -5);
        $orderSn .= substr(microtime(), 2, 5);
        $orderSn .= sprintf('%02d', mt_rand(0, 99));
        return $orderSn;
    }
}