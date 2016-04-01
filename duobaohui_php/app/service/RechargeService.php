<?php
/**
 * User: zhangtaichao
 * Date: 15/11/4
 * Time: 上午11:36
 */

namespace Laravel\Service;


use Illuminate\Support\Facades\Log;
use Laravel\Model\JnlRechargeModel;
use Laravel\Model\JnlTransModel;
use Laravel\Model\RechargeModel;
use Illuminate\Support\Facades\DB;
use Laravel\Service\RedpacketService;
class RechargeService {
    protected $jnlRechargeModel;
    protected $rechargeModel;
    protected $jnlTransModel;

    /**
     * 充值渠道
     */
    const Recharge_Channel_Alipay = '0';

    protected $logContext = array(__CLASS__);
    public function __construct() {
        $this->jnlRechargeModel = new JnlRechargeModel();
        $this->rechargeModel = new RechargeModel();
        $this->jnlTransModel = new JnlTransModel();
    }

    public function recharge($jnl_no,$amount,$channel_trade_no,$recharge_channel) {
        if(empty($jnl_no) || !is_numeric($amount) || empty($channel_trade_no || !isset($recharge_channel))) {
            Log::error("充值失败，参数传递错误",$this->logContext);
            return false;
        }
        $jnlInfo = $this->jnlTransModel->load($jnl_no);
        $user_id = $jnlInfo->user_id;
        $userInfo = $this->rechargeModel->getUserMoney($user_id);
        if(empty($userInfo)) {
            Log::error("充值失败，获取用户余额失败，用户状态或不正常,jnl_no={$jnl_no}",$this->logContext);
            return false;
        }
        $latestMoney = $userInfo->money + $amount;
        DB::beginTransaction();
        try{
            $num = $this->rechargeModel->updateUserMoney($latestMoney,$user_id);
            if($num > 0) {
                $param = [
                    'user_id' => $user_id,
                    'recharge_channel' => $recharge_channel,
                    'channel_trade_no' => $channel_trade_no,
                    'trans_jnl_no' => $jnl_no,
                    'amount' => $amount,
                    'latest_balance' => $latestMoney,
                    'create_time' => date('Y-m-d H:i:s')
                ];
                $id = $this->jnlRechargeModel->add($param);
                if(empty($id)) {
                    DB::rollBack();
                    return false;
                } else {
                    DB::commit();
                    Log::error("充值送红包,user_id:".$user_id."充值金额:".$amount);
                    $sendRedpacket = new RedpacketService();
                    $sendRedpacket->sendRechargeRedpacket($user_id,$amount);
                    return $id;
                }
            } else {
                DB::rollBack();
                return false;
            }
        } catch (\Exception $e) {
            DB::rollBack();
            Log::error("充值失败" . json_encode($e->getTrace()),$this->logContext);
            throw $e;
        }
    }
}