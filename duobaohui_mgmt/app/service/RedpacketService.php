<?php
namespace Laravel\Service;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\DB;
use Laravel\Model\Admin\OperateModel;
use Illuminate\Support\Facades\Response;

class RedpacketService{


	/*
	 * 批次生成红包
	 */
	public function createRedpacketforbatch($id,$batchName,$validity,$prices,$buyMoney,$activityInfo,$option){

		Log::error(var_export("\n批次生成红包事务开始执行：\n", true));
		DB::beginTransaction();
		try{
			$validityDate       = $this->createBatchValidity($validity);          //创建批次红包有效期间
			$batchNum           = $this->createBatchPrice($id,$prices);           //先插入金额 hasTomany
			if(!$batchNum){
				DB::rollBack();
				return false;
			}

			$this->createBatchActivity($id,$activityInfo);                  //插入活动限制

			$data = array(
				'id'                => $id,
				'red_money_name'    => $batchName,
				'is_auto'           => 0,
				'recharge'          => array_key_exists('recharge',$option) ? $option['recharge'] : 0,
				'sign'              => array_key_exists('sign',$option) ? $option['sign'] : 0,
				'buy'               => array_key_exists('buy',$option) ? $option['buy'] : 0,
				'new_user'          => array_key_exists('new_user',$option) ? $option['new_user'] : 0,
				'inviter'           => array_key_exists('inviter',$option) ? $option['inviter'] : 0,
				'num'               => $batchNum[0],
				'end_num'           => $batchNum[0],
				'total_price'       => $batchNum[1],
				'consumption'       => $buyMoney,
				'admin_id'          => 1,
				'is_delete'         => 0,
				'begin_date'        => $validityDate[0],
				'end_date'          => $validityDate[1],
				'create_time'       => date('Y-m-d H:i:s'),
				'is_start'          => 0,
			);
			Log::error(var_export("\n批次生成红包数据：\n", true));
			Log::error(var_export($data, true));

			$operates       = new OperateModel();

			if($operates->insertRedpacketBatch($data)){                 //批次红包
				$redStatus  = $this->createMoreRed($id,$validityDate);  //批量生成红包
				if($redStatus){
					DB::commit();
					return true;
				}else{
					DB::rollBack();
					return false;
				}
			}else{
				DB::rollBack();
				return false;
			}

		}catch (\Exception $e){
			DB::rollBack();
			Log::error(print_r($e, 1), array(__CLASS__));
			return false;
		}
	}

	/*
	 * 批量生成红包
	 * 大于1000条分批插入
	 */
	private function createMoreRed($id,$validityDate){
		$operates = new OperateModel();
		$hasRedpacketId     = $operates->getLastRedMoneyNumber();
		if($hasRedpacketId){
			$subId          = substr($hasRedpacketId->id,0,8);
			if($subId == date('Ymd')){
				$newRedpacketId = intval($hasRedpacketId->id + 1);
			}else{
				$newRedpacketId = $this->createTodayFirstRedpacketNumer();
			}
		}else{
			$newRedpacketId = $this->createTodayFirstRedpacketNumer();
		}

		$pricesInfo         =   $operates->getPriceByBatchId($id);
		$redpacketData      = array();
		$num = 0;
		$pricesNum          =   count($pricesInfo);
		for($i=0;$i<$pricesNum;$i++){   //$pricesNum   3  遍历3条数据
			$forNum = $pricesInfo[$i]->num;

			for($j=0;$j<$forNum;$j++){
				$num++;
				$redpacketData[] = array(
					'id'                        => $newRedpacketId++,
					'sh_red_money_batch_id'     => $id,
					'sh_red_money_price_id'     => $pricesInfo[$i]->id,
					'status'                    => 0,
					'issue_time'                => '',
					'sh_user_id'                => 0,
					'is_new_red_money'          => 0,
					'is_receive'                => 0,
					'is_use'                    => 0,
					'is_overdue'                => 0,
					'receive_time'              => '',
					'use_time'                  => '',
					'overdue_time'              => $validityDate[1],
					'create_time'               => date('Y-m-d H:i:s'),
					'update_time'               => ''
				);

				if($num ==1000){

					//每次插入1000条
					Log::info("\n\n\n\n每次生成的1000个红包:\n");
					$status = $operates->insetRedpacket($redpacketData);
					if(!$status){
						Log::error("\n\n\n\n每次生成的1000个红包状态:\n".print_r($status, 1));
						return false;
					}
					unset($redpacketData);
					$num =0;
				}

				if($j == $forNum){   //已插入完数据
					Log::info("\n\n已插入完数据");
					return true;
				}

				//usleep(10);

			}

			if(isset($redpacketData)){
				Log::info("\n\n如果不足1000条数据执行此操作\n");
				Log::info("\n查看剩余多少条:".count($redpacketData));
				return $operates->insetRedpacket($redpacketData);
			}else{
				Log::info("\n\n已插入完数据");
				return true;
			}

		}

	}


	/*
	 * 创建批次红包有效期间
	 */
	private function createBatchValidity($validity){

		$nowTime            =  date('Y-m-d H:i:s');
		if(count($validity)      == 1  && $validity[0] == 0){
			$startDate      =  $nowTime;
			$endDate        =  '2100-12-12 00:00:00';
		}elseif(count($validity) == 1  && $validity[0] != 0){
			$startDate      =  $nowTime;
			$endDate        =  date('Y-m-d H:i:s',strtotime("+$validity[0] day"));
		}elseif(count($validity) ==2 ){
			$rand           =  rand($validity[0],$validity[1]);
			$startDate      =  $nowTime;
			$endDate        =  date('Y-m-d H:i:s',strtotime("+$rand day"));
		}
		Log::error(var_export("\n有效期开始时间:$startDate\n", true));
		Log::error(var_export("\n有效期结束时间:$endDate  \n", true));
		return array($startDate,$endDate);
	}

	/*
	 * 创建批次红包单价
	 * return $num  总数量
	 */
	private function createBatchPrice($id,$prices){
		try{
			$moneyData       = array();
			$num             = '';
			$totalPrice      = '';
			foreach($prices as $val){
				$price       = explode(',', $val);
				$moneyData[] = array(
					'sh_red_money_batch_id' => $id,
					'price'  => $price[0],
					'num'    => $price[1],
					'end_num'=> $price[1]
				);
				$num         += $price[1];
				$totalPrice  += intval($price[0]) * intval($price[1]);
			}
			$operates = new OperateModel();
			$createPrice     = $operates->insertPrice($moneyData);
			Log::error(var_export('插入单价表数据返回状态:     '.$createPrice."\n\n", true));
			Log::error(var_export('单价总数量:     '.$num."\n\n", true));
			return array($num,$totalPrice);
		}catch (\Exception $e){
			Log::error(var_export($e, true), array(__CLASS__));
		}finally{
			Log::error(var_export("========插入单价finally=========\n", true));
		}
	}


	/*
	 * 创建批次红包可用活动限制
	 */
	private function createBatchActivity($id,$activityInfo){
		try{
			if($activityInfo == 0){
				return false;
			}
			$activity         = explode(',', $activityInfo);
			$activityData     = array();
			foreach($activity as $key=>$info){
				if( !empty($info) ){
					$activityData[]    = array(
						'sh_red_money_batch_id' => $id,
						'sh_activity_id'        => $info
					);
				}
			}
			$operates = new OperateModel();
			$insertActivity   = $operates->insertActivity($activityData);
			Log::error(var_export('插入限制活动表数据返回状态:     '.$insertActivity."\n\n", true));
			return $insertActivity;
		}catch (\Exception $e){
			Log::error(var_export($e, true), array(__CLASS__));
		}finally{
			Log::error(var_export("\n\n========创建批次红包可用活动限制finally=========\n", true));
		}
	}

	/*
	** 创建12位唯一批次号
	*/
	protected function batchNum(){

		date_default_timezone_set('PRC');
		$batNum      = '';
		$batNum     .= date('Ymd');
		$batNum     .= substr(microtime(), 2, 5);
		$batNum     .= sprintf('%02d', mt_rand(0, 99));

		return  $batNum;
	}

	/*
    ** 创建今天初始化批次号
    */
	public function createTodayFirstBatchNumer(){

		date_default_timezone_set('PRC');
		$firstNum   = intval(date('Ymd').'001');

		return $firstNum;
	}

	/*
    ** 创建今天初始化红包号
    */
	public function createTodayFirstRedpacketNumer(){

		date_default_timezone_set('PRC');
		$firstNum   = intval(date('Ymd').'000001');

		return $firstNum;
	}

	/*
	** 发放方案单维映射表
	*/
	public function sendRedPacketWayTag(){

		return $tag = [
			'----选择发放方案----','充值满30送','充值满50送','充值满100送','连续签到5天送','累计购买100送','新用户领取','被邀请者充满20,邀请者奖励'
		];

	}
}