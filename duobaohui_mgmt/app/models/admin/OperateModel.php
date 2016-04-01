<?php

namespace Laravel\Model\Admin;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Log;//引入日志类

class OperateModel extends \Laravel\Model\Model {

	protected $date;

	public function __construct(){
		$this->date = date('Y-m-d H:i:s');
	}


	/*
	 * 获取红包管理的信息
	 */
	public function getBatchInfo(){
		$data   = DB::table('red_money_batch')->where('is_delete','=',0)->orderBy('create_time','DESC')->paginate(15);

		foreach($data as $val){
			$val->end_date = substr($val->end_date,0,10);
			$zero1=strtotime ($this->date);
			$zero2=strtotime ($val->end_date);
			//debug(ceil(($zero2-$zero1)/86400));
			if(ceil(($zero2-$zero1)/86400) <= 0){   //过期
				DB::beginTransaction();
				$status = DB::table('red_money')->where('sh_red_money_batch_id','=',$val->id)->update(array('is_overdue'=>1,'update_time'=>$this->date));
				if($status){
					DB::commit();
				}else{
					DB::rollback();
				}
			}
		}

		$type   = 0;
		$prices =  $this->getPrice($type);  //单价
		$tag    =  $this->sendRedPacketWayTag();    //发放方案
		$info   =  $this->getPriceByBatchINfo($data,$prices,$tag);
		return $info;
	}


	/*
	 * 搜索查询红包管理页面
	 * @param array $search  搜索条件
	 */
	public function getBatchInfoBysearch($search){
		$tag          =  $this->sendRedPacketWayTag();    //发放方案

		//金额匹配
		if( isset($search['money']) ) {
			if($search['money']       == 1) {
				$type = 1;  //获取单价唯一的所有数据
			} elseif($search['money'] == 2) {
				$type = 2;  //获取多个金额的所有数据
			} else {
				$type = 0;  //全部
			}
		} else {
			$type     = 0;  //全部
		}
		$prices       =  $this->getPrice($type);  //单价

		//发放方案
		if( isset($search['programme'])  ) {
			if($search['programme']      == 1) {
				$isAuto = 0;    //自动
			}elseif($search['programme'] == 2) {
				$isAuto = 1;    //手动
			}
		} else {
			$isAuto     = 0;    //默认自动
		}

		//剩余数量
		if($search['end_num']       == 0 && $search['end_num_input'] != '') {  //剩余数量大于
			$endNum = $search['end_num_input'];
			$symbols= '>';
		} elseif($search['end_num'] == 1 && $search['end_num_input'] != '') {  //剩余数量小于
			$endNum = $search['end_num_input'];
			$symbols= '<';
		} else {
			$endNum = -1;   //全部余数
			$symbols= '>';
		}

		//总金额
		if( $search['total_price']  == 0 && $search['total_price_input'] != '' ) {  //总金额大于
			$totalPrice     = $search['total_price_input'];
			$priceSymbols   = '>';

		} elseif(    $search['total_price'] == 1 && $search['total_price_input'] != ''   ) {  //总金额小于
			$totalPrice     = $search['total_price_input'];
			$priceSymbols   = '<';

		} else {   //全部余数
			$totalPrice     = -1;
			$priceSymbols   = '>';
		}

		Log::info("---------- 红包搜索查询条件参数:\n"."金额匹配的参数:".$type."\n发放方案:".$isAuto."\n剩余数量标识和数量:".$symbols.'   '.$endNum."\n总金额标识和数量".$priceSymbols.'   '.$totalPrice);

		$data = DB::table('red_money_batch')
			->where('is_delete','=',0)
			->where('is_auto',$isAuto)
			->where('end_num',$symbols,$endNum)
			->where('end_num',$priceSymbols,$totalPrice)
			->orderBy('create_time','DESC')
			->paginate(15);
		$info = $this->getPriceByBatchINfo($data,$prices,$tag);

		return $info;
	}


	/*
	 * 查询条件,获取单个金额的
	 * @param int $type 获取金额条件 $type = 0 不限制  or 1 获取单个金额   or 2 多个金额
	 */
	public function getPrice($type){
		$list =  DB::table('red_money_price')->get();

		$arrtmp = array();
		$unique = array();
		foreach($list as $val) {
			if(isset($arrtmp[$val->sh_red_money_batch_id])){
				$unique[$val->sh_red_money_batch_id] = $val;
				unset($arrtmp[$val->sh_red_money_batch_id]);
			}else{
				$arrtmp[$val->sh_red_money_batch_id] = $val;
			}
		}

		if($type==1){
			$arrtmp = array_values($arrtmp);
			return $arrtmp;
		}elseif($type == 2){

			if(!empty($unique)){
				$batId     = array();
				foreach($unique as $info){
					$batId[] = $info->sh_red_money_batch_id;
				}
				$data = DB::table('red_money_price')->whereIn('sh_red_money_batch_id',$batId)->get();
				return $data;
			}else{
				return $list;
			}
		}else{
			return $list;
		}
	}

	/*
	 * 启动和不停止红包发放
	 */
	public function openOrCloseRedpacket($id,$isSatrtId){
		$arr = array('is_start'=>$isSatrtId);
		return DB::table('red_money_batch')->where('id', $id)->update($arr);
	}


	/*
	 * 根据批次ID获取红包信息
	 * @param int $ind 批次ID
	 */
	public function getRedpacketByBatch($id){
		$data =  DB::table('red_money_batch')
			->where('id','=',$id)
			->where('is_delete','=',0)
			->orderBy('create_time','DESC')
			->get();
		$type   = 0;
		$prices =  $this->getPrice($type);  //单价
		$tag    =  $this->sendRedPacketWayTag();    //发放方案
		$info   = $this->getPriceByBatchINfo($data,$prices,$tag);
		return $info;
	}


	/*
	 * 根据批次ID获取活动和操作者信息
	 */
	public function getActivityByBatchId($id){
		$data = DB::table('red_money_batch')
			->leftJoin('admin_user','admin_user.id','=','red_money_batch.admin_id')
			->leftJoin('red_money_activity','red_money_activity.sh_red_money_batch_id','=','red_money_batch.id')
			->leftJoin('activity','activity.id','=','red_money_activity.sh_activity_id')
			->leftJoin('goods','goods.id','=','activity.sh_goods_id')
			->where('red_money_batch.id',$id)
			->select('goods.goods_name','admin_user.nickname')
			->get();
		if(!empty($data)){
			$good      = '';
			$userName  = '';
			foreach($data as $list){
				$good       .= $list->goods_name.' , ';
				$userName    = $list->nickname;
			}

			$arr = array($good,$userName);
			//debug($arr);
			return $arr;
		}else{
			return false;
		}
	}

	/*
	 * 根据批次ID获取获取红包使用情况
	 */
	public function getRedpacketSupply($id){

		$data = DB::table('red_money')
			->leftJoin('red_money_price','red_money_price.id','=','red_money.sh_red_money_price_id')
			->where('red_money.sh_red_money_batch_id',$id)
			->groupBy('red_money_price.price')
			->select('red_money_price.id','red_money_price.price','red_money_price.num','red_money_price.end_num')
			->get();

		$arrtmp = array();
		foreach($data as $info){
			$arrtmp[] = $info->id;
			$info->is_use      =DB::table('red_money')->where('sh_red_money_batch_id',$id)->where('sh_red_money_price_id',$info->id)->groupBy('sh_red_money_price_id')->sum('is_use');
			$info->status      =DB::table('red_money')->where('sh_red_money_batch_id',$id)->where('sh_red_money_price_id',$info->id)->groupBy('sh_red_money_price_id')->sum('status');
			$info->is_overdue  =DB::table('red_money')->where('sh_red_money_batch_id',$id)->where('sh_red_money_price_id',$info->id)->groupBy('sh_red_money_price_id')->sum('is_overdue');
		}
		return $data;
	}


	/*
	 * 根据红包ID获取红包使用详细信息
	 */
	public function getRedpacketInfomation($id){
		$data = DB::table('red_money')
			->leftJoin('red_money_batch','red_money_batch.id','=','red_money.sh_red_money_batch_id')
			->leftJoin('red_money_price','red_money_price.id','=','red_money.sh_red_money_price_id')
			->leftJoin('admin_user','admin_user.id','=','red_money_batch.admin_id')
			->leftJoin('user','user.id','=','red_money.sh_user_id')
			->where('red_money.id',$id)
			->select('red_money.id','red_money_price.price','red_money.is_use','red_money.is_receive','red_money.status','red_money.receive_time','red_money.use_time','red_money.issue_time','admin_user.nickname','user.nick_name')
			->first();

		return $data;
	}


	/*
	 * 红包参与夺宝详情
	 */
	public function RedpacketUseInfi($id){
		$data = DB::table('jnl_trans')
			->leftJoin('red_money','red_money.id','=','jnl_trans.repacket_id')
			->leftJoin('red_money_price','red_money_price.id','=','red_money.sh_red_money_price_id')
			->leftJoin('jnl_trans_duobao','jnl_trans_duobao.trans_jnl_no','=','jnl_trans.jnl_no')
			->leftJoin('activity_period','activity_period.id','=','jnl_trans_duobao.sh_activity_period_id')
			->leftJoin('activity','activity.id','=','activity_period.sh_activity_id')
			->leftJoin('goods','goods.id','=','activity.sh_goods_id')
			->where('jnl_trans.repacket_id',$id)
			->where('jnl_trans_duobao.status',1)
			->select('goods.goods_name','jnl_trans_duobao.num','red_money_price.price as redpacket_price','jnl_trans.pay_type','jnl_trans.recharge_channel','jnl_trans.amount')
			->first();

		return $data;
	}

	/*
	 * 根据批次ID获取所有红包详细信息
	 * @param int $ind 批次ID
	 */
	public function getRedpacketInfoByBatch($id){
		return  DB::table('red_money')
			->leftJoin('red_money_price','red_money_price.id','=','red_money.sh_red_money_price_id')
			->leftJoin('user','user.id','=','red_money.sh_user_id')
			->where('red_money.sh_red_money_batch_id','=',$id)
			->select('red_money.id','red_money.status','red_money.issue_time','red_money.sh_user_id','red_money.receive_time','red_money.use_time','red_money.overdue_time',
					'red_money_price.price','user.nick_name')
			->paginate(10);
	}


	/*
	 * 根据批次ID和搜索条件查询红包信息
	 * $choice   0 全部   1未发放  2已发放   3已领取   4已使用  5已过期
	 */
	public function getRedpacketInfoBySearch($id,$choice){
		if($choice == 1){
			$filde = 'status';
			$value = 0;
		}elseif($choice == 2){
			$filde = 'status';
			$value = 1;
		}elseif($choice == 3){
			$filde = 'is_receive';
			$value = 1;

		}elseif($choice == 4){
			$filde = 'is_use';
			$value = 1;

		}elseif($choice == 5){
			$filde = 'is_overdue';
			$value = 1;

		}else{
			return $this->getRedpacketInfoByBatch($id);
		}

		return  DB::table('red_money')
			->leftJoin('red_money_price','red_money_price.id','=','red_money.sh_red_money_price_id')
			->leftJoin('user','user.id','=','red_money.sh_user_id')
			->where('red_money.sh_red_money_batch_id','=',$id)
			->where("red_money.$filde",'=',$value)
			->select('red_money.id','red_money.status','red_money.issue_time','red_money.sh_user_id','red_money.receive_time','red_money.use_time','red_money.overdue_time',
				'red_money_price.price','user.nick_name')
			->paginate(15);
//		->get();
	}


	/*
	 * 获取数据库最后一个批次号
	 */
	public function getLastBatchNumber(){
		return DB::table('red_money_batch')->orderBy('id','desc')->limit(1)->select('id')->first();
	}

	/*
	 * 获取所有活动
	 * DB::raw  存储过程
	 */
	public function getAllActivity(){
		$result = DB::table('activity as a')->select(DB::raw("
			a.id,
			a.sh_goods_id,
			b.goods_name,
			a.max_period,
			a.is_online,
			c.cat_name
		"))->leftJoin('goods as b',DB::raw('b.id'),'=',DB::raw('a.sh_goods_id'))
			->leftJoin('category as c',DB::raw('c.id'),'=',DB::raw('b.sh_category_id'))
			->latest(DB::raw('a.creat_time'))->get();

		if(!empty($result)){
			$data    = $this->arrForeach($result);
			Cache::put('activity',$data,3600);  //写入缓存
		}else{
			$data    = array();
		}
		return $data;
	}


	/*
	 * 添加批次红包价格
	 * @param array $data  插入批次价格关系表数据
	 */
	public function insertPrice($data){
		return DB::table('red_money_price')->insert($data);
	}

	/*
	 * 添加可限制活动
	 * @param array $data  插入活动关系表数据
	 */
	public function insertActivity($data){
		return DB::table('red_money_activity')->insert($data);
	}


	/*
	 * 批量生成红包操作
	 * @param array $data  插入批次表数据
	 */
	public function insertRedpacketBatch($data){
		return DB::table('red_money_batch')->insert($data);
	}

	/*
	 * 根据批次ID获取单价信息
	 * @param int $id  批次ID
	 */
	public function getPriceByBatchId($id){
		return DB::table('red_money_price')->where('sh_red_money_batch_id',$id)->get();
	}


	/*
	 * 获取红包表最后一条数据
	 */
	public function getLastRedMoneyNumber(){
		return DB::table('red_money')->orderBy('id','desc')->limit(1)->select('id')->first();
	}

	/*
	 * 大批量生成红包
	 * @param array  $data  插入红包表数据
	 */
	public function insetRedpacket($data){
		return DB::table('red_money')->insert($data);
	}


	/*
	 *  按分类分组公共方法
	 *  @param  array $result
	 *  @return array $arr
	 */
	protected function arrForeach( $result ){
		$arr     =   array();
		foreach($result as $k=>$v){
			//按分类分组
			$category = $v->cat_name;
			$arr[$category][] = $v;
		}

		return $arr;
	}


	/*
	** 发放方案单维映射表公共方法
	*/
	protected function sendRedPacketWayTag(){
		return $tag = [
			'----选择发放方案----','充值满30送','充值满50送','充值满100送','连续签到5天送','累计购买100送','新用户领取','被邀请者充满20,邀请者奖励'
		];
	}

	/*
	 * 根据批次表信息来拼接价格输出
	 * @param void  $data    查询批次表信息
	 * @param void  $prices  查询价格表信息
	 * @param array $tag     发放方案关系映射表
	 */
	protected function getPriceByBatchINfo($data,$prices,$tag){
		foreach($data as $value){
			if($value ->recharge    == true){
				$value->recharge    =  $tag[$value->recharge];
			}elseif($value->sign    == true){
				$value->recharge    =  $tag[$value->sign];
			}elseif($value->buy     == true){
				$value->recharge    =  $tag[$value->buy];
			}elseif($value->new_user== true){
				$value->recharge    =  $tag[$value->new_user];
			}elseif($value->inviter == true){
				$value->recharge     =  $tag[$value->inviter];
			}else{
				$value->recharge    =  '————';
			}
			$value->price           =  '';
			foreach( $prices as $price ){
				if($price->sh_red_money_batch_id == $value->id){
					$value->price  .=  $price->price."&nbsp;&nbsp;&nbsp;";
				}
			}
		}
		return $data;
	}

}