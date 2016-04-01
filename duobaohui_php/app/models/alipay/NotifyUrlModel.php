<?php
/*
 *支付宝支付异步服务器接收支付业务逻辑操作
 *
 *@aythor	  majianchao@shinc.net
 *@version    v1.0
 *@copyright  shinc
 */

namespace  Laravel\Model;
#namespace  Laravel\Model\NotifyUrl;

use Illuminate\Support\Facades\DB;


class  NotifyUrlModel extends  Model{

	/*
	 * 对支付回调参数进行操作
	 *
	 *@param	array		$data		支付宝的回调参数
	 *
	 *@return	boolean		true/false  数据是否通过
	 * */
	protected  $alipay  = 'alipay';
	
	public  function  addAlipay($data){
		return DB::table('alipay')
		->insertGetId($data);
	}

	public function addAlipayPeriod($alipayId , $userId , $activityPeriodId, $buyOderId=0){
		$data = array(
			'alipay_id'				=> $alipayId,
			'user_id'				=> $userId,
			'activity_period_id'	=> $activityPeriodId,
			'buy_order_id'			=> $buyOderId
		);
		return DB::table('alipay_period')->insert($data);
	}

	public function getAlipayStatus($activityPeriodId , $userId){
		return DB::table('alipay_period')
				->where('activity_period_id' , $activityPeriodId)
				->where('user_id' , $userId)
				->get();
	}

	/*
	 * 查看是否已存在订单
	 */
	public function hasTradeNo($tradeNo){
		$row = DB::table('alipay')
				->where('trade_no' , $tradeNo)
				->select('id','trade_status')
				->first();
		if($row){
			return $row;
		}

		return false;
	}

	/*
	 * 更新状态
	 */
	public function editAlipay($id, $data){
		return DB::table('alipay')->where('id', $id)->update($data);
	}


	/**
	*
	* 比较支付宝账务与本地订单账务是否相同
	* @param 	$tradeNo	唯一交易号
	*/
	public function getComparisonInfo( $tradeNo , $gmtStartTime , $gmtEndTime ){
		$hostDataInfo = DB::table('alipay')
				->whereIn('trade_no' , $tradeNo)
				->whereBetween('gmt_create', array($gmtStartTime, $gmtEndTime))
				->select('trade_no')
				->get();

		if(!empty($hostDataInfo)){
			$hostTradeNo = array();
			foreach ($hostDataInfo as $hostData) {
				$hostTradeNo[] = $hostData->trade_no;
			}
			$hostTradeNo = array_unique($hostTradeNo);
			
			
			$failTrade = array_diff($tradeNo,$hostTradeNo);

			$path = app_path().'storage/logs'.strftime("%Y%m%d",time()).'.log';
		
			return $failTrade;
		}else{
			return $tradeNo;
		}
		
	}


}

