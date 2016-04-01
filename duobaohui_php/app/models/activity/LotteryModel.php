<?php
/**
* 夺宝号码
* @author wanghaihong@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;

use Laravel\Model\LotteryMethodModel;
use Illuminate\Support\Facades\Log;//引入日志类

class LotteryModel extends Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');
	}

	public function getLotteryCode($expect){
		$list = DB::table('lottery_shishi')
				->select('open_code','expect','open_time')
				->where(array('expect' => $expect))
				->first();
		return $list;
	}

	public function addCodeNum($data){
		$res = DB::table('activity_code')->insert($data);
		return $res;
	}

	public function getActivity($where=array()){
		$list = DB::table('activity_period')
				->select('id','real_need_times')
				->where($where)
				->first();
		return $list;
	}

	public function getCodeTime(){
		$list = DB::table('activity_code')
				->select('user_id' , 'code_num' , 'buy_time_code' , 'buy_time_date')
				->where('user_id', '>', 0)
				->where('buy_time_date', '<>', '')
				->skip('0')->take(50)
				->orderBy('buy_time_date' , 'DESC')
				->get();
		return $list;
	}

	public function getCodeTimeFormPeriod($activity_period_id){
		$list = DB::table('activity_code_record')
				->select('activity_period_id' , 'user_id' , 'code_num' , 'buy_time_code' , 'buy_time_date')
				->where(array('activity_period_id' => $activity_period_id))
				->get();
		return $list;
	}

	public function addCodeTime($data){
		$res = DB::table('activity_code_record')->insert($data);
		return $res;
	}

	public function getRandomCode($activity_period_id , $user_id, $num=1){
		$list = DB::select('select id,code_num from sh_activity_code where user_id = 0 and activity_period_id=? limit ?', array($activity_period_id, $num));
		return $list;
	}

    public function updateUserCode($activity_period_id,$num,$user_id, $sh_period_user_id , $buyId){
		Log::error("-------updateUserCode\n");
		$ids = DB::table('activity_code')->where('user_id', 0)->where('activity_period_id' ,$activity_period_id)->take($num)->lists('id');
		Log::error("activity_code ids :".print_r($ids,1)."\n");

		$lottery = new LotteryMethodModel();
		$buy_time_date = '';
		$buy_time_code = '';
		foreach($ids as $id){
			$micTime = microtime();
			$time_arr =  $lottery->convertDate($micTime);
			$time_date = $time_arr[0];
			$time_code = $time_arr[1];

		//	var_dump($micTime);
			$buy_time_date .= ' WHEN '.$id.' THEN \'' . $time_date .'\' ';
			$buy_time_code .= ' WHEN '.$id.' THEN \'' . $time_code .'\' ';
		}

		$strIds = implode(',', $ids);

		if(empty($strIds)){
			return false;
		}

		$sql = "
			UPDATE sh_activity_code
			SET user_id = {$user_id},
				sh_period_user_id = {$sh_period_user_id},
                buy_id = {$buyId},
				buy_time_date = CASE id {$buy_time_date}  END,
				buy_time_code = CASE id {$buy_time_code}  END
			WHERE id IN ({$strIds})
			";

		//Log::error("update sql :".$sql."\n");

		return DB::update($sql);
/*
		$res = DB::table('activity_code')
				->where('user_id' ,'=', '0')
				->where('activity_period_id' ,'=', $activity_period_id)
				->take($num)
				->update($data);
		return $res;
 */
	}

	public function addGoodsInfo($data){
		$res = DB::table('goods_163_info')->insertGetId($data);
		return $res;
	}

	public function addGoodsImg($data){
		$res = DB::table('goods_163_pic')->insertGetId($data);
		return $res;
	}

	public function insertLotteryCode($data){
	    $res = DB::table('lottery_shishi')->insert($data);
	    return $res;
	}

	/**
	 * 查询待开奖列表
	 */
	public function queryPeriodResult(){
	    $list = DB::select('SELECT
	                           pr.id,
	                           pr.a_code_create_time,
	                           pr.a_code,
	                           pr.sh_activity_period_id,
	                           pr.lottery_period,
	                           ap.real_need_times
	                       FROM
	                           sh_period_result pr,
	                           sh_activity_period ap
                           where
	                           pr.sh_activity_period_id = ap.id
	                       and
	                           pr.a_code is not null
	                       and
	                           pr.lottery_code = \'\'
	                       and
	                           pr.luck_code = \'\'');
	    return $list;
	}

	/**
	 * 通过期数主键id、夺宝号查询user_id
	 */
	public function queryWinningUserId($sh_activity_period_id , $code_num){
	    $list = DB::table( 'activity_code AS '.DB::getTablePrefix().'ac' )
	    ->leftJoin('user AS '.DB::getTablePrefix().'u' ,'ac.user_id','=','u.id')
	    ->where('ac.activity_period_id', $sh_activity_period_id)
	    ->where('ac.code_num', $code_num)
	    ->select(
	        'ac.activity_period_id',
	        'ac.user_id',
	        'ac.code_num',
	        'u.is_real',
	        'u.tel',
	        'u.nick_name')
	    ->get();
	    
	    return $list;
// 	    $list = DB::select('SELECT
// 	                           ac.activity_period_id,
// 	                           ac.user_id,
// 	                           ac.code_num
// 	                       FROM
// 	                           sh_activity_code ac
//                            where
// 	                           ac.activity_period_id = ?
// 	                       and
// 	                           ac.code_num = ?',array($sh_activity_period_id, $code_num));
// 	    return $list;
	}

	/**
	 * 事务－－填充时时彩开奖号，同时生成幸运号，生成订单
	 */
	public function updatePeriodResultAndCreateOrder($period_result_data , $order_data){
	    try {
    	   $exception = DB::transaction(function() use ( $period_result_data , $order_data ){
    	        DB::table('period_result')
        	    ->where('id', $period_result_data['sh_period_result_id'])
        	    ->update(array(
        	        'user_id' => $period_result_data['user_id'],
        	        'lottery_code' => $period_result_data['lottery_code'],
        	        'remainder' => $period_result_data['remainder'],
        	        'proto_code' => $period_result_data['proto_code'],
        	        'luck_code' => $period_result_data['luck_code'],
        	        'lottery_code_create_time' => date('Y-m-d H:i:sa', time()),
        	        'luck_code_create_time' => date('Y-m-d H:i:sa', time())
        	    ));
    
    		    DB::table('order_info')->insertGetId($order_data);
    	    });
    	    return is_null($exception) ? true : $exception;
	    }catch(Exception $e) {
	        Log::error($e);
            return false;
        }
	}

	public function insertPushItem($period_result_data , $order_data){
		try {
			DB::table('app_notify')->insertGetId(array(
				'adddate'=>$order_data['add_time'],
				'sh_period_result_id'=>$period_result_data['sh_period_result_id'],
				'user_id'=>$period_result_data['user_id'],
				'send_flag'=>0,
				'is_real'=>$period_result_data['is_real'],
				'msg_type'=>'win'
			));

			DB::table('sms_notify')->insertGetId(array(
				'adddate'=>$order_data['add_time'],
				'user_id'=>$period_result_data['user_id'],
				'tel'=>$period_result_data['tel'],
				'content'=>'亲爱的'.$period_result_data['nick_name'].'，恭喜您在夺宝会成功获得奖品！请及时在夺宝会客户端确认收货信息。',
				'send_flag'=>0,
				'sh_period_result_id'=>$period_result_data['sh_period_result_id'],
				'is_real'=>$period_result_data['is_real']
			));
		}catch(Exception $e) {
			Log::error($e);
		}
	}

    public function getPreluckcodecreatetime($lottery_period){
        $list = DB::select('SELECT
	                           pr.pre_luck_code_create_time,
	                           pr.lottery_period,
	                           pr.sh_activity_period_id
	                       FROM
	                           sh_period_result pr
                           where
	                           pr.lottery_period = ?',array($lottery_period));
        return $list;
    }
}
