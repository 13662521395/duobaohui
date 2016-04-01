<?php
/**
 * by zhangtaichao
 * 后台管理 活动 模型
 */

namespace Laravel\Model\Admin;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;
use Laravel\Model\LotteryMethodModel;
use App\Libraries\LotteryUtil;//引入彩票工具类
use Laravel\Model\RechargeModel;//引入充值model
use Illuminate\Support\Facades\Log;//引入日志类

class ActivityModel extends \Laravel\Model\Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');

	}



	/*
	 * 总交易金额
	 */
	public function getTotalMoney(){
		$data = DB::table('jnl_recharge')
			 ->leftJoin('user' , 'user.id' , '=' , 'jnl_recharge.user_id')
			 ->where('user.is_real',1)
			->sum('amount');
		return $data;
	}


	/*
	 *  脚本的交易记录查询
	 *
	 */
	public function getScripyPayByTime( $startTime , $endTime ){
		$data = DB::table('user')
			->leftJoin('jnl_trans','jnl_trans.user_id','=','user.id')
			->leftJoin('jnl_trans_duobao','jnl_trans_duobao.trans_jnl_no','=','jnl_trans.jnl_no')
			->leftJoin('bill','bill.out_trade_no','=','jnl_trans.jnl_no')
			->leftJoin('jnl_alipay','jnl_alipay.out_trade_no','=','jnl_trans.jnl_no')
			->where('user.is_real','=',1)
			->where('jnl_trans.jnl_status','!=',0)
			->whereBetween('jnl_trans.create_time', array($startTime, $endTime))
			->orderBy('jnl_trans.create_time','DESC')
			->select('user.id as user_id','user.tel','jnl_trans.jnl_no as trans_jnl_no','jnl_trans.trans_code',
				'jnl_trans.jnl_status','jnl_trans.pay_type','jnl_trans.amount',
				'jnl_trans.create_time','jnl_trans.recharge_channel','jnl_trans_duobao.id as duobao_id','bill.id as bill_id','jnl_trans_duobao.status')
			->get();

		if(empty($data))
			return false;

		//临时表存储
		$hasInsert = DB::table('pay_list')->whereBetween('create_time', array($startTime, $endTime))->first();

		if(!empty($hasInsert)){
			DB::table('pay_list')->whereBetween('create_time', array($startTime, $endTime))->delete();
		}

		//debug($data);
		//$insertData = array();
		foreach($data as $value){
			$insertData[] = (array)$value;
		}
		//debug($insertData);
		$savePayList = DB::table('pay_list')->insert($insertData);

		return $savePayList;
	}


	/*
	 * 今天交易记录
	 *
	 */
	public function getPayByTime($startTime , $endTime){
		return DB::table('pay_list')->where('jnl_status','!=',0)->whereBetween('create_time', array($startTime, $endTime))->orderBy('create_time','DESC')->paginate(20);
	}



	//根据时间查充值信息
	public function getPayRechargeByTime( $startTime , $endTime ){

		return DB::table('pay_list')->where('pay_type',1)->where('jnl_status','!=',0)->whereBetween('create_time', array($startTime, $endTime))->orderBy('create_time','DESC')->paginate(20);

	}


	//根据时间查夺宝信息
	public function getPayDuobaoByTime( $startTime , $endTime ){

		return DB::table('pay_list')->where('status',1)->where('jnl_status','!=',0)->whereBetween('create_time', array($startTime, $endTime))->orderBy('create_time','DESC')->paginate(20);
	}

	/**
	 * 全部支付记录
	 * @param $offset  			int   	分页起始位置
	 * @param $length  			int   	分页长度
	 * @param $condition 		string 	数据库执行条件
	 * @param $conditionValue 	string 	数据库执行条件值
	 */
	public function getPayList( $condition, $conditionValue){

		return DB::table('pay_list')->where($condition,$conditionValue)->where('jnl_status','!=',0)->orderBy('create_time','DESC')->paginate(20);
		return $data;
	}



	/*
	 * 根据订单号查询异常
	 */
	public function getJnlnoBill($jnlNo){

		$data = DB::table('jnl_trans')
			->leftJoin('jnl_trans_duobao','jnl_trans_duobao.trans_jnl_no','=','jnl_trans.jnl_no')
			->leftJoin('jnl_recharge','jnl_recharge.trans_jnl_no','=','jnl_trans_duobao.trans_jnl_no')
			->leftJoin('jnl_deduct','jnl_deduct.trans_jnl_no','=','jnl_trans_duobao.trans_jnl_no')
			->leftJoin('bill','bill.out_trade_no','=','jnl_trans.jnl_no')
			->leftJoin('jnl_alipay','jnl_alipay.out_trade_no','=','jnl_trans.jnl_no')
			->leftJoin('user','user.id','=','jnl_trans.user_id')
			->where('jnl_trans.jnl_no',$jnlNo)
			->where('user.is_real','>',0)
			->select(
					 'jnl_trans.pay_type','jnl_trans.jnl_no', 'jnl_trans.create_time','jnl_trans.amount', 'jnl_trans.trans_code as subject',
					'jnl_trans.jnl_status as status','jnl_deduct.latest_balance as deduct_money','jnl_recharge.latest_balance as recharge_money','user.nick_name', 'user.id as user_id','user.money','user.tel','jnl_trans_duobao.sh_activity_period_id','bill.id as bill_id','jnl_alipay.out_trade_no','jnl_alipay.total_fee')
			->first();

		return $data;
	}



	/*
	 *  手机号查询所有流水记录
	 *
	 */
	public function getTelPayList($tel){
		return DB::table('pay_list')->where('tel', $tel)->orderBy('create_time','DESC')->paginate(20);
	}



	/*
	 *  夺宝信息
	 *  夺宝id->得到流水号，期数id和是否夺宝成功，参与夺宝数量(用来计算价格),
	 *  流水号->到扣款表，得到交易总价，夺宝后剩余余额，到到用户id
	 *  用户id->用户信息，现在的余额。
	 */
	public function getAlipayDuobao( $duobaoId ){
		$data = DB::table('jnl_trans_duobao')
			->leftJoin('jnl_trans','jnl_trans.jnl_no','=','jnl_trans_duobao.trans_jnl_no')
			->leftJoin('jnl_deduct','jnl_deduct.trans_jnl_no','=','jnl_trans_duobao.trans_jnl_no')
			->leftJoin('activity_period','activity_period.id','=','jnl_trans_duobao.sh_activity_period_id')
			->leftJoin('activity','activity.id','=','activity_period.sh_activity_id')
			->leftJoin('goods','goods.id','=','activity.sh_goods_id')
			->leftJoin('user','user.id','=','jnl_trans.user_id')
			->leftJoin('bill','bill.out_trade_no','=','jnl_deduct.trans_jnl_no')
			->where('jnl_trans_duobao.id',$duobaoId)
			->select('jnl_trans_duobao.id','jnl_trans_duobao.trans_jnl_no','jnl_trans_duobao.num',
				'jnl_trans_duobao.status','jnl_deduct.amount','jnl_deduct.latest_balance','jnl_trans.user_id','jnl_deduct.create_time','user.nick_name','user.tel','user.money','bill.id as bill_id','goods.goods_name')
			->first();

		return $data; 
	}



	/*
	 *  充值信息
	 *  得到流水号  -》到充值表，得到id,用户id,充值渠道，支付宝交易号，充值金额，充值后余额，
	 *  得到流水号  -》到alipay表，得到交易信息，如充值金额，充值商品名称
	 *  到用户表   -》用户信息，现在的余额。
	 */
	public function getAlipayRecharge( $jnlNo ){
		$data = DB::table('jnl_recharge')
			->leftJoin('jnl_alipay','jnl_alipay.out_trade_no','=','jnl_recharge.trans_jnl_no')
			->leftJoin('user','user.id','=','jnl_recharge.user_id')
			->leftJoin('bill','bill.out_trade_no','=','jnl_recharge.trans_jnl_no')
			->where('jnl_recharge.trans_jnl_no',$jnlNo)
			->select('jnl_recharge.id','jnl_recharge.user_id','jnl_recharge.recharge_channel','jnl_recharge.channel_trade_no','jnl_recharge.trans_jnl_no','jnl_recharge.amount','jnl_recharge.latest_balance','jnl_recharge.create_time',
					'jnl_alipay.subject','jnl_alipay.total_fee','user.nick_name','user.tel','user.money','bill.id as bill_id'
				)
			->first();
			// debug($data);
		return $data; 
	}



	/*
	 * 支付统计
	 */
	public function getPayAccount(){
		$total['total_fee_count'] = DB::table( 'jnl_alipay AS '.DB::getTablePrefix().'a' )
				->leftJoin('bill','bill.trade_no','=','a.trade_no')
				->where('bill.trade_no','!=','a.trade_no')
				->sum('a.total_fee');

		return $total;
	}



	public function getActivityList(){
		$list = DB::table('activity as a')->select(DB::raw("
			a.id,
			a.current_period,
			a.sh_goods_id,
			b.goods_name,
			a.max_period,
			a.price,
			DATE_FORMAT(a.creat_time, '%Y-%m-%d %T') create_time,
			DATE_FORMAT(a.begin_date, '%Y-%m-%d') begin_date,
			DATE_FORMAT(a.end_date, '%Y-%m-%d') end_date,
			DATE_FORMAT(a.update_time, '%Y-%m-%d %T') update_time,
			a.need_times,
			a.is_online,
			a.is_auto,
			a.is_hot,
			a.user_id,
			c.nickname
		"))->leftJoin('goods as b',DB::raw('b.id'),'=',DB::raw('a.sh_goods_id'))
			->leftJoin('admin_user as c',DB::raw('c.id'),'=',DB::raw('a.user_id'))
			->latest(DB::raw('a.creat_time'))->paginate(10);
		return $list;
	}

	/*
	 * 根据活动ID查询活动详细信息
	 */
	public function getActivityById($activity_id){
		$list = DB::table('activity as a')->select(DB::raw("
			a.id,
			a.current_period,
			a.sh_goods_id,
			b.goods_name,
			a.max_period,
			a.price,
			DATE_FORMAT(a.creat_time, '%Y-%m-%d %T') create_time,
			DATE_FORMAT(a.begin_date, '%Y-%m-%d') begin_date,
			DATE_FORMAT(a.end_date, '%Y-%m-%d') end_date,
			DATE_FORMAT(a.update_time, '%Y-%m-%d %T') update_time,
			a.need_times,
			a.is_online,
			a.is_auto,
			a.is_hot,
			a.user_id,
			c.nickname
		"))->leftJoin('goods as b',DB::raw('b.id'),'=',DB::raw('a.sh_goods_id'))
			->leftJoin('admin_user as c',DB::raw('c.id'),'=',DB::raw('a.user_id'))
			->where(DB::raw('a.id'),'=',$activity_id)
			->first();
		return $list;
	}

	/*
	 * 根据活动ID查询当前最新的“期”的信息
	 */
	public function getLatestPeriodByActivityId($activity_id){

		$result = DB::table('activity_period')
			->select('id',
				'period_number',
				'sh_activity_id',
				'create_time',
				'current_times',
				'real_need_times',
				'real_price',
				'flag')
			->where('sh_activity_id','=',$activity_id)
			->latest('create_time')
			->first();
		return $result;

	}

	public function insertActivity($goods_id,$max_period,$begin_date,$end_date,$need_times,$is_online,$is_auto,$user_id,$is_hot='0') {
		$nowtime = date('Y-m-d H:i:s');
		$id = DB::table('activity')->insertGetId(array(
			'creat_time' => $nowtime,
			'sh_goods_id' => $goods_id,
			'max_period' => $max_period,
			'begin_date' => $begin_date,
			'end_date' => $end_date,
			'need_times' => $need_times,
			'update_time' => $nowtime,
			'is_online' => $is_online,
			'is_auto' => $is_auto,
			'current_period' => 0,
			'user_id' => $user_id,
			'is_hot' => $is_hot
		));
		return $id;
	}

	public function addActivity($data) {
		return DB::table('activity')->insertGetId($data);
	}

	public function updateActivity($id,$data) {
		return DB::table('activity')->where('id', $id)->update($data);
	}

	public function addActivityPeriod($data) {
		return DB::table('activity_period')->insertGetId($data);
	}

	public function getPeriodByActivityId($activity_id) {
		$list = DB::table('activity_period')
			->where('sh_activity_id','=',$activity_id)
			->latest('create_time')
			->paginate(10);
		return $list;
	}


	/**
	 * 判断产品是否已使用新建活动
	 * @param $goods_id
	 * @return bool
     */
	public function isGoodsUsed($goods_id){
		$result = DB::table('activity')
			->where('sh_goods_id','=',$goods_id)
			->first();
		if(empty($result)) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 热门活动是否已过总活动数一半
	 */
	public function isHotMax() {

		$result = DB::table('activity')
			->where('is_online','=','1')
			->whereIn('is_hot',array(0,1))
			->select('is_hot')
			->selectRaw('count(1) count ,is_hot')
			->orderBy('is_hot')
			->groupBy('is_hot')
			->get();

		if(count($result) > 0) {
			if(count($result) == 1) {
				if($result[0]->is_hot == '1') {
					return true;
				} else {
					return false;
				}
			} elseif (count($result == 2)) {
				$nomal = $result[0]->count;
				$hot = $result[1]->count;
				if($nomal <= $hot) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}



	/**
	*	支付宝账务明细异常查询
	* 
	* @param $condition 		string 	数据库执行条件
	* @param $conditionValue 	string 	数据库执行条件值	
	* @author liangfeng@shinc.net
	*/
	public function getAlipayAccount($condition, $conditionValue){
		$data =  DB::table('bill')
			->leftJoin('bill_action' , 'bill_action.trade_no' , '=' , 'bill.trade_no')
			->leftJoin('jnl_alipay' , 'jnl_alipay.trade_no' , '=' , 'bill.trade_no')
			->leftJoin('jnl_trans_duobao' , 'jnl_trans_duobao.trans_jnl_no' , '=' , 'jnl_alipay.out_trade_no')
			->leftJoin('jnl_trans' , 'jnl_trans.jnl_no' , '=' , 'jnl_alipay.out_trade_no')
			->leftJoin('user' , 'user.id' , '=' , 'jnl_trans.user_id')
			->orderBy('bill.id','desc')
			->where($condition,$conditionValue)
			->select( 'bill.id', 'bill.out_trade_no', 'bill.trade_no','bill.type','bill.buyer_id','bill.total_fee','bill.start_date','bill.status',
						  'bill.end_date','bill.notify_time','jnl_trans.user_id', 'jnl_trans_duobao.sh_activity_period_id','user.nick_name','jnl_alipay.total_fee as alipay_total',
						  'bill_action.sh_user_money_to','bill_action.user_id'
						 )
			->paginate(10);
		return $data;
	}


	/**
	 * 处理虚假订单状态
	 * 删除alipay虚假订单记录，写入删除事件，减去用户余额，更改异常状态
	 * @author liangfeng@shinc.net
	 */
    public function changeStatus($id){
    	$data = DB::table('bill')
    		->leftJoin('jnl_alipay' , 'jnl_alipay.trade_no' , '=' , 'bill.trade_no')
    		->leftJoin('jnl_trans_duobao' , 'jnl_trans_duobao.trans_jnl_no' , '=' , 'jnl_alipay.out_trade_no')
    		->leftJoin('jnl_trans' , 'jnl_trans_duobao.trans_jnl_no' , '=' , 'jnl_trans.jnl_no')
    		->leftJoin('user' , 'user.id' , '=' , 'jnl_trans.user_id')
    		->where('bill.id',$id)
    		->select('jnl_alipay.total_fee','jnl_alipay.out_trade_no','jnl_trans.user_id','user.money','bill.id as bill_id','bill.status','bill.trade_no')
    		->first();
    	//只能操作一次
    	if($data->status == 0){
    		//更新bill表的状态
			$statusArr['status'] = 1;

			$saveBillStatus = DB::table('bill')->where('id', $data->bill_id)->update($statusArr);

    		//对原用户余额进行改进
	    	$moneyTo['money'] = $data->money - $data->total_fee;

	    	//插入到动作表中
	    	$billActionData = array(
	    		'bill_id'	=>	$data->bill_id,
	    		'trade_no'	=>	$data->trade_no,
	    		'sh_user_money_from'=> $data->money,
	    		'sh_user_money_to'	=> $moneyTo['money'],
	    		'user_id'	=>  $data->user_id,
	    		'create_time'		=> date('Y-m-d H:i:s')
	    	);
	    	$billAction = DB::table('bill_action')->insertGetId($billActionData);

	    	//更新用户余额
			$saveUserMoney =  DB::table('user')->where('id', $data->user_id)->update($moneyTo);

			//删除订单表的相应记录
			$deleteAlipay = DB::table('jnl_alipay')->where('out_trade_no', $data->out_trade_no)->delete();
			if($deleteAlipay){
				return true;
			}else{
				return false;
			}

    	}else{
    		return false;
    	}

    }



    /**
	 * 修复真实重复订单状态
	 * 更新异常表状态为1，标识已修复，减去用户相应余额，插入记录表中，删除alipay表的所有重复记录
	 * @author liangfeng@shinc.net
	 */
    public function editAlipay($id){
    	$data = DB::table('bill')
    		->leftJoin('jnl_alipay' , 'jnl_alipay.trade_no' , '=' , 'bill.trade_no')
    		->leftJoin('jnl_trans_duobao' , 'jnl_trans_duobao.trans_jnl_no' , '=' , 'jnl_alipay.out_trade_no')
    		->leftJoin('jnl_trans' , 'jnl_trans_duobao.trans_jnl_no' , '=' , 'jnl_trans.jnl_no')
    		->leftJoin('user' , 'user.id' , '=' , 'jnl_trans.user_id')
    		->where('bill.id',$id)
    		->select('jnl_alipay.total_fee','jnl_alipay.out_trade_no','jnl_trans.user_id','user.money','bill.id as bill_id','bill.status','bill.trade_no')
    		->first();
    	// debug($data);
		if($data ->status ==0 ){
			//更新bill表的状态
			$statusArr['status'] = 1;

			$saveBillStatus = DB::table('bill')->where('id', $id)->update($statusArr);
			$moneyTo['money'] = $data->money - $data->total_fee;

			//插入到动作表中
			if(empty($data->money)){
				$data->money =0;
			}
	    	$billActionData = array(
	    		'bill_id'	=>	$data->bill_id,
	    		'trade_no'	=>	$data->trade_no,
	    		'sh_user_money_from'=> $data->money,
	    		'sh_user_money_to'	=> $moneyTo['money'],
	    		'user_id'	=>  $data->user_id,
	    		'create_time'		=> date('Y-m-d H:i:s')
	    	);
	    	$billAction = DB::table('bill_action')->insertGetId($billActionData);

	    	//更新用户余额
			$saveUserMoney =  DB::table('user')->where('id', $data->user_id)->update($moneyTo);

			//删除订单表的相应记录
			$deleteAlipay = DB::table('alipay')->where('trade_no', $data->trade_no)->delete();

			if($deleteAlipay){
				return true;
			}else{
				return false;
			}
    	}


    }

	public function getDataCountForIndex($startDateTime,$nowDateTime){
		$data['count_buy_true'] = DB::table('jnl_deduct')
			->leftJoin('user' , 'user.id' , '=' , 'jnl_deduct.user_id')
			->where('user.is_real',1)
			->whereBetween('jnl_deduct.create_time', array($startDateTime, $nowDateTime))
			->sum('jnl_deduct.amount');

		$data['count_buy_flase'] = DB::table('jnl_deduct')
			->leftJoin('user' , 'user.id' , '=' , 'jnl_deduct.user_id')
			->where('user.is_real',0)
			->whereBetween('jnl_deduct.create_time', array($startDateTime, $nowDateTime))
			->sum('jnl_deduct.amount');
		return $data;
	}

	public function getDataCountForIndex2(){
		$real = DB::table('count_data')->select('trade_money')->where('type',1)->orderBy('create_time', 'desc')->first();
		$data['count_buy_true'] = $real?$real->trade_money:0;

		$fade = DB::table('count_data')->select('trade_money')->where('type',0)->orderBy('create_time', 'desc')->first();
		$data['count_buy_flase'] = $fade?$fade->trade_money:0;
		return $data;
	}

	/**
	 *  数据统计
	 *  统计总购买次数(真实用户,虚拟用户)、红包除外的总消费金额(真实用户,虚拟用户)、统计奖品总价(真实用户,虚拟用户)
	 *  count_buy 					  count_amount						count_total_fee
	 */
	public function getDataCountList($startDateTime,$nowDateTime){
		$data['count_buy_true'] = DB::table('count_data')
			->where('type',1)
			->whereBetween('start_time', array($startDateTime, $nowDateTime))
			->first();
		$data['count_buy_false'] = DB::table('count_data')
			->where('type',0)
			->whereBetween('start_time', array($startDateTime, $nowDateTime))
			->first();

		if(empty($data['count_buy_true'])){
			return $this->getDataCount($startDateTime,$nowDateTime);
		}
		return $data;
	}

	/**
	 *  数据统计
	 *  统计总购买次数(真实用户,虚拟用户)、红包除外的总消费金额(真实用户,虚拟用户)、统计奖品总价(真实用户,虚拟用户)
	 *  count_buy 					  count_amount						count_total_fee
	 */
	public function getDataCount($startDateTime,$nowDateTime){

		//count_buy
		$data['buy_true'] = DB::table('jnl_trans_duobao')
			->leftJoin('jnl_trans','jnl_trans.jnl_no','=','jnl_trans_duobao.trans_jnl_no')
			->leftJoin('user' , 'user.id' , '=' , 'jnl_trans.user_id')
			->where('user.is_real',1)
			->where('jnl_trans_duobao.status',1)
			->whereBetween('jnl_trans_duobao.create_time', array($startDateTime, $nowDateTime))
			->count('jnl_trans_duobao.num');
		/*select sh_jnl_trans_duobao.num from sh_jnl_trans_duobao left join `sh_jnl_trans`
					on sh_jnl_trans.jnl_no =  sh_jnl_trans_duobao.trans_jnl_no left join sh_user on sh_user.id = sh_jnl_trans.user_id  where `sh_user`.`is_real` = 1  and sh_jnl_trans_duobao.status = 1  and  sh_jnl_trans_duobao.create_time between '2015-12-16' and '2015-12-17'*/

		$data['buy_flase'] = DB::table('jnl_trans_duobao')
			->leftJoin('jnl_trans','jnl_trans.jnl_no','=','jnl_trans_duobao.trans_jnl_no')
			->leftJoin('user' , 'user.id' , '=' , 'jnl_trans.user_id')
			->where('user.is_real',0)
			->where('jnl_trans_duobao.status',1)
			->whereBetween('jnl_trans_duobao.create_time', array($startDateTime, $nowDateTime))
			->count('jnl_trans_duobao.num');

		//count_amount
		$data['count_amount_true'] = DB::table('jnl_deduct')
			->leftJoin('user' , 'user.id' , '=' , 'jnl_deduct.user_id')
			->where('is_real',1)
			->whereBetween('jnl_deduct.create_time', array($startDateTime, $nowDateTime))
			->sum('jnl_deduct.amount');
		//5182
		/* select sum(sh_jnl_deduct.amount) from sh_jnl_deduct left join sh_user on sh_user.id = sh_jnl_deduct.user_id left join `sh_jnl_trans`
					on sh_jnl_trans.jnl_no =  sh_jnl_deduct.trans_jnl_no where `sh_user`.`is_real` = 1  and  sh_jnl_deduct.create_time between '2015-12-13' and '2015-12-17' */

		$data['count_amount_flase'] = DB::table('jnl_deduct')
			->leftJoin('user' , 'user.id' , '=' , 'jnl_deduct.user_id')
			->where('is_real',0)
			->whereBetween('jnl_deduct.create_time', array($startDateTime, $nowDateTime))
			->sum('amount');

		//count_total_fee
		$data['count_total_fee_true'] = DB::table('period_result')
			->leftJoin('user' , 'user.id' , '=' , 'period_result.user_id')
			->leftJoin('activity_period' , 'activity_period.id' , '=' , 'period_result.sh_activity_period_id')
			->leftJoin('activity' , 'activity.id' , '=' , 'activity_period.sh_activity_id')
			->where('is_real',1)
			->whereBetween('period_result.luck_code_create_time', array($startDateTime, $nowDateTime))
			->sum('activity.need_times');

		$data['count_total_fee_flase'] = DB::table('period_result')
			->leftJoin('user' , 'user.id' , '=' , 'period_result.user_id')
			->leftJoin('activity_period' , 'activity_period.id' , '=' , 'period_result.sh_activity_period_id')
			->leftJoin('activity' , 'activity.id' , '=' , 'activity_period.sh_activity_id')
			->where('is_real',0)
			->whereBetween('period_result.luck_code_create_time', array($startDateTime, $nowDateTime))
			->sum('activity.need_times');

		return $data;


	}


	/**
	 *  真实用户详细基础统计数据
	 *  统计总购买次数(真实用户,虚拟用户)、红包除外的总消费金额(真实用户,虚拟用户)、统计奖品总价(真实用户,虚拟用户)
	 *  count_buy 					  count_amount						count_total_fee
	 */

	public function getDetailCountData($startDateTime,$nowDateTime){
		$data = DB::table('count_data')
			->where('type',1)
			->whereBetween('start_time', array($startDateTime, $nowDateTime))
			->get();

		$trade_num 		= '';
		$trade_money 	= '';
		$luck_money		= '';
		$start_time		= '';
		foreach ($data as $key => $value) {
			$trade_num 		.= $value->trade_num.',';
			$trade_money	.= $value->trade_money.',';
			$luck_money		.= $value->luck_money.',';

			$arr = explode(' ',$value->start_time);
			// $start_time		.= '\''.$arr[0].'\',';
			$start_time		.= $arr[0].',';
		}

		$dataList = array(
			'count_buy_true' =>	$trade_num,
			'count_amount_true' =>	$trade_money,
			'count_total_fee_true'	=>	$luck_money,
			'start_time'	=> 	$start_time
		);


		// debug($dataList);

		return $dataList;
	}



	/**
	 *  真实用户和虚拟用对比
	 *  统计总购买次数(真实用户,虚拟用户)、红包除外的总消费金额(真实用户,虚拟用户)、统计奖品总价(真实用户,虚拟用户)
	 *  count_buy 					  count_amount						count_total_fee
	 */

	public function getCompareUserCount($startDateTime,$nowDateTime){
		$data = DB::table('count_data')
			// ->where('type',1)
			->whereBetween('start_time', array($startDateTime, $nowDateTime))
			->get();

		$trade_num1 	= '';
		$trade_money1 	= '';
		$luck_money1	= '';
		$start_time1	= '';
		$trade_num2		= '';
		$trade_money2 	= '';
		$luck_money2	= '';
		$start_time2	= '';
		foreach ($data as $key => $value) {
			if($value->type == 0){
				$trade_num1 		.= $value->trade_num.',';
				$trade_money1	.= $value->trade_money.',';
				$luck_money1		.= $value->luck_money.',';

			}else{
				$trade_num2 		.= $value->trade_num.',';
				$trade_money2	.= $value->trade_money.',';
				$luck_money2		.= $value->luck_money.',';

				$arr = explode(' ',$value->start_time);
				$start_time2		.= $arr[0].',';
			}
		}

		//假
		$dataList = array(
			'count_buy_true' 		=>	$trade_num2,
			'count_amount_true' 	=>	$trade_money2,
			'count_total_fee_true'	=>	$luck_money2,
			'count_buy_false' 		=>	$trade_num1,
			'count_amount_false' 	=>	$trade_money1,
			'count_total_fee_false'	=>	$luck_money1,
			'start_time'			=> 	$start_time2
		);


		// debug($dataList);

		return $dataList;
	}




}

