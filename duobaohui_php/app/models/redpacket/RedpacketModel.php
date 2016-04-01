<?php
/**
 *	发放红包   ---单一职责
 *  流程：获取红包信息，插入用户ID，更新发放时间，更新时间，更新价格表和批次表剩余数量
 *	@author		liangfeng@shinc.net
 *	@version	v1.3.1
 *	@copyright	shinc
 */
namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;//引入日志类

date_default_timezone_set('PRC');
class RedpacketModel extends Model
{

	private $date;


	public function __construct(){
		$this->date = date('Y-m-d H:i:s');
	}

	/*
	 * 根据红包发放方案获取红包Id
	 */
	public function getRedpacketInfo($feild,$value,$userId){

		$checkUser  = DB::table('user')->where('id',$userId)->where('is_real',1)->first();
		if(!$checkUser){
			return array('code'=>'-2','msg'=>'用户身份校验失败');
		}

		$redpackets =  DB::table('red_money')
			->leftJoin('red_money_batch','red_money_batch.id','=','red_money.sh_red_money_batch_id')
			->where("red_money_batch.$feild",'=',$value)
			->where("red_money_batch.is_start",'=',1)
			->where("red_money.sh_user_id",'=',0)
			->where("red_money.status",'=',0)
			->select('red_money.id','red_money.sh_red_money_batch_id','red_money.sh_red_money_price_id')
			->first();
		if(empty($redpackets)){
			return array('code'=>'-1','msg'=>'没有可用红包');
		}

		$status =  $this->sendPacket($redpackets,$userId);
		if($status){
			return array('code'=>'1','msg'=>'赠送红包成功');
		}else{
			return array('code'=>'-3','msg'=>'红包发放失败');
		}
	}


	/*
	 * 根据红包ID更新用户领取红包，更新发放时间、更新价格关系表和批次表的数量
	 */
	public function sendPacket($redpackets,$userId){
		DB::beginTransaction();

		Log::error("\n\n---Send Redpacket Transaction start-----:\n");
		$updateUser = DB::table('red_money')->where('id',$redpackets->id)->update(array('status'=>1,'sh_user_id'=>$userId,'issue_time'=>$this->date,'update_time'=>$this->date));
		if(!$updateUser){
			DB::rollback();
			return false;
		}

		$prices = DB::table('red_money_price')->where('id',$redpackets->sh_red_money_price_id)->select('end_num')->first();

		if($prices->end_num == 0){
			return false;  //数量没了
		}
		$newNum = intval($prices->end_num)-1;
		$updatePrice = DB::table('red_money_price')->where('id',$redpackets->sh_red_money_price_id)->update(array('end_num'=>$newNum));
		if(!$updatePrice){
			DB::rollback();
			return false;
		}

		$batchs = DB::table('red_money_batch')->where('id',$redpackets->sh_red_money_batch_id)->select('end_num')->first();
		$batchNewNum = intval($batchs->end_num)-1;
		$updateBatchs = DB::table('red_money_batch')->where('id',$redpackets->sh_red_money_batch_id)->update(array('end_num'=>$batchNewNum));
		if(!$updateBatchs){
			DB::rollback();
			return false;
		}

		Log::info('---------- 发放红包事务结束');
		DB::commit();
		return true;
	}


	/*
	 * 获取红包列表
	 * $type  0为可用   1为已用   2为已过期
	 */
	public function getRedpacketByApi($userId,$type,$offset,$length){

		if($type == 0){
			$feild1 = 'is_use';
			$feild2 = 'is_overdue';
			$value1  = '0';
			$value2  = '0';
		}elseif($type == 1){
			$feild1 = 'is_use';
			$feild2 = 'is_overdue';
			$value1  = '1';
			$value2  = '0';
		}elseif($type == 2){
			$feild1 = 'status';
			$feild2 = 'is_overdue';
			$value1  = '1';
			$value2  = '1';
		}else{
			return array();
		}
		$redpackets =  DB::table('red_money')
			->leftJoin('red_money_batch','red_money_batch.id','=','red_money.sh_red_money_batch_id')
			->leftJoin('red_money_price','red_money_price.id','=','red_money.sh_red_money_price_id')
			->where("red_money.sh_user_id",'=',$userId)
			->where("red_money.$feild1",'=',$value1)
			->orderBy('red_money.id','desc')
			->where("red_money.$feild2",'=',$value2)
			->skip($offset)->take($length)
			->select('red_money.id','red_money_batch.red_money_name','red_money_price.price','red_money.overdue_time','red_money_batch.consumption','red_money.sh_red_money_batch_id','red_money.is_new_red_money')
			->get();

		if(empty($redpackets)){
			return array();
		}

		$redpacketIds = array();
		foreach($redpackets as $val){

			$redpacketIds[] = $val->id;

			$activity =  DB::table('red_money_activity')
				->where('sh_red_money_batch_id',$val->sh_red_money_batch_id)
				->count();
			$val->activity    = $activity;

			$val->overdue_time = substr($val->overdue_time,0,10);
			$zero1=strtotime ($this->date);
			$zero2=strtotime ($val->overdue_time);
			$surplus=ceil(($zero2-$zero1)/86400);
			if($surplus > 365){
				$val->surplus = -1;
			}elseif($surplus > 0 ){
				$val->surplus = ceil(($zero2-$zero1)/86400); //60s*60min*24h
			}else{  //过期
				$val->surplus = 0;
				DB::beginTransaction();
				$status = DB::table('red_money')->where('sh_red_money_batch_id','=',$val->id)->update(array('is_overdue'=>1,'update_time'=>$this->date));
				if($status){
					DB::commit();
				}else{
					DB::rollback();
				}
			}
		}

		//更新为旧红包
		DB::table('red_money')->whereIn('id',$redpacketIds)->update(array('is_new_red_money'=>1));

		return $redpackets;
	}


	/*
	 * 红包统计
	 */
	public function CountRedpacket($userId){
		$data = array();

		$data['issue'] = DB::table('red_money')->where('sh_user_id',$userId)->where('is_use',0)->where('is_overdue',0)->count();
		$data['use']   = DB::table('red_money')->where('sh_user_id',$userId)->where('is_use',1)->count();
		$data['is_overdue'] = DB::table('red_money')->where('sh_user_id',$userId)->where('is_overdue',1)->count();


		return $data;
	}


	/*
	 * 红包详情----领取
	 */
	public function getRedpacketByRedpacketid($redpacketId){
		$redpackets =  DB::table('red_money')
			->leftJoin('red_money_batch','red_money_batch.id','=','red_money.sh_red_money_batch_id')
			->leftJoin('red_money_price','red_money_price.id','=','red_money.sh_red_money_price_id')
			->where("red_money.id",'=',$redpacketId)
			->select('red_money.id','red_money_batch.red_money_name','red_money_price.price','red_money.overdue_time','red_money_batch.consumption','red_money.sh_red_money_batch_id','red_money.is_new_red_money')
			->first();

		if(empty($redpackets)){
			return array();
		}

		if($redpackets->is_new_red_money == 0){
			DB::table('red_money')->where('id',$redpacketId)->update(array('is_new_red_money'=>1,'is_receive'=>1,'receive_time'=>$this->date,'update_time'=>$this->date));
		}

		$redpackets->overdue_time = substr($redpackets->overdue_time,0,10);
		$zero1=strtotime ($this->date);
		$zero2=strtotime ($redpackets->overdue_time);
		$surplus=ceil(($zero2-$zero1)/86400);
		if($surplus > 365){
			$redpackets->surplus = -1;
		}elseif($surplus > 0 ){
			$redpackets->surplus = ceil(($zero2-$zero1)/86400); //60s*60min*24h
		}else{  //过期
			$redpackets->surplus = 0;
			DB::beginTransaction();
			$status = DB::table('red_money')->where('sh_red_money_batch_id','=',$redpackets->id)->update(array('is_overdue'=>1,'update_time'=>$this->date));
			if($status){
				DB::commit();
			}else{
				DB::rollback();
			}
		}

		$activity = DB::table('red_money_activity')
			->leftJoin('activity','activity.id','=','red_money_activity.sh_activity_id')
			->leftJoin('activity_period','activity_period.sh_activity_id','=','activity.id')
			->leftJoin('goods','goods.id','=','activity.sh_goods_id')
			->where('sh_red_money_batch_id',$redpackets->sh_red_money_batch_id)
			->where('activity_period.flag',1)
			->select('red_money_activity.id','red_money_activity.sh_red_money_batch_id','red_money_activity.sh_activity_id','activity.current_period','activity_period.id as period_id','goods.goods_name','goods.goods_img')
			->get();

		$redpackets->activity = array();
		if(empty($activity)){
			$redpackets->activity = array();
		}

		foreach($activity as $list){
			if($redpackets->sh_red_money_batch_id == $list->sh_red_money_batch_id){
				$redpackets->activity[] = $list;
			}
		}

		return $redpackets;
	}


	/*
	 * 商品详情购买页面的红包列表
	 * 条件：该活动可用红包的显示
	 */
	public function getRedpacketByActivityid($userId,$acticity,$num,$offset,$length){
		$redpacketsOne =  DB::table('red_money')
			->leftJoin('red_money_batch','red_money_batch.id','=','red_money.sh_red_money_batch_id')
			->leftJoin('red_money_price','red_money_price.id','=','red_money.sh_red_money_price_id')
			->leftJoin('red_money_activity','red_money_activity.sh_red_money_batch_id','=','red_money_batch.id')
			->where('red_money.sh_user_id',$userId)
			->where('red_money_batch.consumption','<=',$num)
//			->where('red_money.is_new_red_money',1)
			->where('red_money.is_use',0)
			->where('red_money.is_overdue',0)
			->where('red_money_activity.sh_activity_id',$acticity)
			->orderBy('red_money.id','desc')
			->skip($offset)->take($length)
			->select('red_money.id','red_money_batch.red_money_name','red_money_price.price','red_money.overdue_time','red_money_batch.consumption','red_money.sh_red_money_batch_id','red_money.is_new_red_money')
			->get();

		$redpacketsTwo =  DB::table('red_money')
			->leftJoin('red_money_batch','red_money_batch.id','=','red_money.sh_red_money_batch_id')
			->leftJoin('red_money_price','red_money_price.id','=','red_money.sh_red_money_price_id')
			->where('red_money.sh_user_id',$userId)
			->where('red_money_batch.consumption','<=',$num)
//			->where('red_money.is_new_red_money',1)
			->where('red_money.is_use',0)
			->where('red_money.is_overdue',0)
			->orderBy('red_money.id','desc')
			->skip($offset)->take($length)
			->select('red_money.id','red_money_batch.red_money_name','red_money_price.price','red_money.overdue_time','red_money_batch.consumption','red_money.sh_red_money_batch_id','red_money.is_new_red_money')
			->get();

		$issueRedpackets = array();
		foreach($redpacketsTwo as $val){
			$activity =  DB::table('red_money_activity')
				->where('sh_red_money_batch_id',$val->sh_red_money_batch_id)
				->count();
			if($activity != true){
				$issueRedpackets[] = $val;
			}
		}
		$data =  array_merge($redpacketsOne,$issueRedpackets);

		foreach($data as $list){
			$list->overdue_time = substr($list->overdue_time,0,10);
			$zero1=strtotime ($this->date);
            $zero2=strtotime ($list->overdue_time);
			$surplus=ceil(($zero2-$zero1)/86400);
			if($surplus > 365){
				$list->surplus = -1;
			}elseif($surplus > 0 ){
				$list->surplus = ceil(($zero2-$zero1)/86400); //60s*60min*24h
			}else{  //过期
				$list->surplus = 0;
				DB::beginTransaction();
				$status = DB::table('red_money')->where('sh_red_money_batch_id','=',$list->id)->update(array('is_overdue'=>1,'update_time'=>$this->date));
				if($status){
					DB::commit();
				}else{
					DB::rollback();
				}
			}

		}

		return $data;

	}

	/*
	 * 统计可用红包数量
	 */
	public function getIssueRedpackets($user_id,$sh_activity_id,$num){
		return count($this->getRedpacketByActivityid($user_id,$sh_activity_id,$num,0,1000000));
	}


	/*
	 * 使用红包
	 */
	public function issueRedpacket($redpacketId){
		$data =  DB::table('red_money')->where('id', $redpacketId)->where('is_use',0)->where('is_overdue',0)->where('overdue_time','>',$this->date)->first();

		if(!$data){
			return false;
		}

		$param  =  array(
			'is_use'        => 1,
			'use_time'      => $this->date,
			'update_time'    => $this->date
		);

		$updateInfo =  DB::table('red_money')->where('id', $redpacketId)->update($param);

		return $updateInfo;

	}


	/*
	 * 查看新红包数量
	 */
	public function getLookNewRedpacket($userId){
		return DB::table('red_money')->where('sh_user_id',$userId)->where('is_new_red_money',0)->where('is_overdue',0)->count();
	}


	/*
	 * 根据用户ID获取用户设备信息
	 */
	public function userPhoneInfo($userId){
		return DB::table('user')->where('id',$userId)->select('os_type')->first();
	}
}