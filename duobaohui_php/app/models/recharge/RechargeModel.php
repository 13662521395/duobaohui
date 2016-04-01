<?php 
/**
 *	充值业务逻辑操作
 *
 *	@author		liangfeng@shinc.net
 *	@version	v1.0
 *	@copyright	shinc
 */
namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;
use Laravel\Model\NotifyUrlModel;//引入model
use Illuminate\Support\Facades\Log;//引入日志类

class RechargeModel extends Model{

	/**
	 * 获取充值记录
	 * 
	 * @param	int		$user_id 	用户id
	 * @param   int 	$offset 	当前页数
	 * @param   int 	$length 	显示条数
	 * @return  array
	 */
	public function getRecharge( $user_id , $offset , $length ) {

		$recharges	=	DB::table( 'jnl_trans' )
			->where( 'user_id', $user_id )
			->where( 'pay_type', '1' )
			->orderBy('create_time','desc')
			->skip($offset)->take($length)
			->select('jnl_status','amount','amount_pay','recharge_channel','create_time')
			->get();

		//交易时间与当前时间的时间差比较
		$nowDate	=	date('Y-m-d H:i:s');

		$jnl_msg = [
			'0' => '未支付',
			'1' => '支付成功',
			'2' => '支付失败',
			'3' => '超时',
			'4' => '支付成功'
		];

		$channel_msg = [
			'0' => '支付宝',
			'1' => '微信',
			'2' => '红包'
		];

		$nowDate    =    date('Y-m-d H:i:s');

		foreach ( $recharges as $key => $records ) {
			$records->amount = (string)$records->amount;

			if(isset($jnl_msg[$records->jnl_status])) {
				$records->status = $jnl_msg[$records->jnl_status];
			} else {
				$records->status = '状态未明';
			}
			if(!empty($records->amount_pay)) {
				$records->amount = $records-> amount_pay;
			}
			if(isset($channel_msg[$records->recharge_channel])) {
				$records->recharge_channel = $channel_msg[$records->recharge_channel];
			} else {
				$records->recharge_channel = '未知';
			}

			if($records->jnl_status == 0){
				$orderTime    =    $records->create_time;
				$date=intval((strtotime($nowDate)-strtotime($orderTime))/86400);
				$hour=intval(floor((strtotime($nowDate)-strtotime($orderTime))%86400/3600));
				if( $date >= 1 || $hour >= 24 ){
					$records->status = '已过期';
				}
			}
		}

		return $recharges;


	}


	/**
	 * 充值夺宝币
	 * 
	 * @param	array		$data
	 * @return boolean
	 */
	public function addRecharge( $data ){
		return DB::table('recharge')->insertGetId($data);
	}


	/**
	 * 修改充值信息
	 * 
	 * @param	int		$recharge_id
	 * @param	array		$data
	 * @return boolean
	 */
	public function editRecharge( $recharge_id , $data ){
		return DB::table('recharge')
		->where('id',$recharge_id)
		->update($data);
	}



	/**
	 * 未付款夺宝币
	 * 
	 * @param	array		$data
	 * @return boolean
	 */
	public function orderRecharge( $data ){
		return DB::table('recharge')->insertGetId($data);
	}



	/**
	 * 更新用户及余额信息
	 * 
	 * @param	
	 * @return boolean
	 */
	public function getUserInfo( $recharge_id,$user_id ){
		$money = DB::table('recharge')
		->select('total_fee')
		->where( 'id', $recharge_id )
		->where( 'payment_status', 1 )
		->first();

		$user = DB::table('user')
		->select('money')
		->where( 'id', $user_id )
		->first();

		$data = array(
			'money' => $money->total_fee + $user->money
			);

		return DB::table('user')
		->where('id',$user_id)
		->update($data);
		
	}


	/**
	 * 获取余额信息
	 * 
	 * @param	array		
	 * @return 	array
	 */
	public function getUserMoney( $user_id ){
		$row = DB::table('user')
			->select('id','tel','nick_name','head_pic','money')
			->where( 'id', $user_id )
			->where('locked','0')
			->first();

		$row->money = intval($row->money);

		return $row;
	}

	/*
	 * 更新用户余额
	 */
	public function updateUserMoney($money , $userId){
		return DB::table('user')->where('id' , $userId)->update(array('money'=>$money));
	}


	/**
	* 统计用户充值金额总数
	* @param user.id(session)	array    用户id	 
	*/
	public function CountForUserMoney($userId) {
		return DB::table('recharge')->where('sh_user_id' , $userId)->where('payment_status' , 1)->sum('total_fee');
	}

	/*
	 * 支付宝支付
	 */
	public function addAlipayNotify($status){
		DB::beginTransaction();

		// 添加支付记录
		$alipayM = new NotifyUrlModel();
		$alipayId = $alipayM->addAlipay($verifyResult);
		if(!$alipayId){
			DB::rollback();
			return false;
		}

		// 更新夺宝订单状态
		$lotteryMethodM = new LotteryMethodModel();
		$isS = $lotteryMethodM->setBuyOrderStatus($userId, $periodId, 8);
		if($isS===false){
			DB::rollback();
			return false;
		}
		DB::commit();
		return true;
	}


	/*
	 * 充值事务
	 */
	public function rechargeTransaction($verifyResult , $userId , $activityPeriodId = 0 , $rechargeId = 0, $buyOderId=0){
		DB::beginTransaction();

		// 保存支付信息
		$alipayM = new NotifyUrlModel();
		$alipayRow = $alipayM->hasTradeNo($verifyResult['trade_no']);
		if( $alipayRow ){
			$alipayM->editAlipay($alipayRow->id,$verifyResult);
			$alipayId = $alipayRow->id;
		}else{
			$alipayId = $alipayM->addAlipay($verifyResult);
		}

		Log::info('保存支付信息===============>'.$alipayId);
		//file_put_contents('/home/log/php/duobaohui/log', "AlipayId ----- $alipayId\n",FILE_APPEND);
		if(!$alipayId){
			DB::rollback();
			return false;
		}

		//file_put_contents('/home/log/php/duobaohui/log', "支付信息\n",FILE_APPEND);
		//file_put_contents('/home/log/php/duobaohui/log', print_r($verifyResult , true),FILE_APPEND);
		//file_put_contents('/home/log/php/duobaohui/log', "user_id---$userId----activity_period_id-----$activityPeriodId\n",FILE_APPEND);

		//保存支付和活动期数的信息
		$result = $alipayM->addAlipayPeriod($alipayId , $userId , $activityPeriodId, $buyOderId);
		Log::info('保存支付和活动期数的信息===============>'.$result);
		//file_put_contents('/home/log/php/duobaohui/log', "支付和活动期数信息---$result\n",FILE_APPEND);
		if( !$result ){
			DB::rollback();
			return false;
		}
		//file_put_contents('/home/log/php/duobaohui/log', "1111111\n",FILE_APPEND);

		//实例化充值业务模型

		$data = array(
			'sh_user_id'    => $userId,
			'payment_type'  => 0,
			'create_time'   => $verifyResult['notify_time'],
			'total_fee'     => $verifyResult['total_fee'],
			'payment_status'=> 1
		);

		//file_put_contents('/home/log/php/duobaohui/log' , print_r($data , true),FILE_APPEND);
		//file_put_contents('/home/log/php/duobaohui/log' , "rechargeId----$rechargeId\n",FILE_APPEND);

		//不传rechargeId为直接充值,传rechargeId为修改充值
		if( !$rechargeId ){

			$rechargeId = $this->addRecharge($data);
			Log::info('纪录充值日志===============>'.$rechargeId);
			if( !$rechargeId ){
				DB::rollback();
				return false;
			}

		}else{

			$data = array(
				'create_time'   => $verifyResult['notify_time'],
				'total_fee'     => $verifyResult['total_fee'],
				'payment_status'=> 1
			);
			$result = $this->editRecharge($rechargeId , $data);
			if(!$result){
				DB::rollback();
				return false;
			}

		}

		//更新用户余额
		$money = $this->getUserInfo($rechargeId , $userId);
		Log::info('更新用户余额===============>'.$money);
		//file_put_contents('/home/log/php/duobaohui/log', "更新用户余额---$money\n",FILE_APPEND);
		if(!$money){
			DB::rollback();
			return false;
		}
		DB::commit();
		return true;
	}


	/*
	 * 获取活动ID
	 *
	 */

	public function getPeriodId($jnl_no){
		return DB::table('jnl_trans_duobao')
			->leftJoin('activity_period','activity_period.id','=','jnl_trans_duobao.sh_activity_period_id')
			->where('jnl_trans_duobao.trans_jnl_no',$jnl_no)
			->select('jnl_trans_duobao.sh_activity_period_id','activity_period.sh_activity_id')
			->first();
	}

}
