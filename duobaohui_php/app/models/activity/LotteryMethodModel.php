<?php
/**
* 夺宝号码
* @author wanghaihong@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Session;

class LotteryMethodModel extends Model {

	// 获取福彩时时号码
	public function getLotteryCode(){
		$lottery = new LotteryModel();
		$list = $lottery -> getLotteryCode();
		return $list;
	}

	/*
	 * 获取随机夺宝号码
	 */
	public function getRandomCode($activity_period_id , $user_id, $buyId, $num=1 ,$sh_period_user_id=0){

		$lottery = new LotteryModel();

		// var_dump($buy_time);
		// die;
		// 更新增加用户夺宝号
        $res = $lottery -> updateUserCode($activity_period_id,$num , $user_id, $sh_period_user_id , $buyId);

		return $res;
	}

	/*
	 * 获取中奖号码
	 */
	public function getLuckCode($activity_period_id , $need_times , $expect){
		// CodeA  50人参与记录总和
		$code_a = $this -> getCodeTimeFormPeriod($activity_period_id);

		// CodeB 福彩号码
		$lottery = new LotteryModel();
		$lotteryCodeRes = $lottery -> getLotteryCode($expect);

		//如果24小时内该期“老时时彩”开奖结果仍未公布，则默认“老时时彩”开奖结果为00000

		if($lotteryCodeRes === null){
		  $res = $lottery -> getPreluckcodecreatetime($expect);
		  $time = time();
		  $pre_luck_code_create_time =  $res[0]->pre_luck_code_create_time;
		  $old_time = strtotime($pre_luck_code_create_time);
		  $diff_days = floor(($time - $old_time)/86400);
	      $lotteryCodeRes = new \stdClass();
	      $lotteryCodeRes -> expect = $expect;
		  if($diff_days >= 1){
		      $lotteryCodeRes -> open_code = '00000';
		  }else{
		      return null;
		  }
		}

		$code_b =  $lotteryCodeRes -> open_code;
		$lottery_period =  $lotteryCodeRes -> expect;
		$remainder = ($code_a + $code_b) % $need_times;
		$proto_code = 10000001;
		$luck_code = $remainder + $proto_code;

		$data['code_a'] = $code_a;
		$data['code_b'] = $code_b;
		$data['lottery_period'] = $lottery_period;
		$data['remainder'] = $remainder;
		$data['proto_code'] = $proto_code;
		$data['luck_code'] = $luck_code;

		return $data;
	}

	public function convertDate($buy_time , $is_code = true){
		$time = explode(' ', $buy_time);
		$dataHaomiao = rand(1,999);
		$dataHaomiao = sprintf("%03d",$dataHaomiao);
		
		$buy_time = array();
		
		$datatime_date = date('Y-m-d H:i:s',$time[1]);
		$buy_time[] = ($datatime_date.".".$dataHaomiao);

		$datatime = date('His',$time[1]);
		$buy_time[] = ($datatime.$dataHaomiao);
		return $buy_time;
	}

	// 获取全站开奖最新50人次时间戳存入参与记录表
	public function codeTime($activity_period_id){
		$lottery = new LotteryModel();
		$codeTime = $lottery -> getCodeTime();
		$data = array();
		foreach($codeTime as $value){
		    $value -> activity_period_id = $activity_period_id;
			$val = $this -> std_class_object_to_array($value);
			$data[] = $val;
		}

		$res = $lottery -> addCodeTime($data);
	}

	//获取最新50人参与时间戳总和
	public function getCodeTimeFormPeriod($activity_period_id){
		$lottery = new LotteryModel();
		$codeTime = $lottery -> getCodeTimeFormPeriod($activity_period_id);

		$buy_time_count = 0;

		foreach ($codeTime as $key => $item) {
			$buy_time = $item -> buy_time_code;
			$buy_time_count+=$buy_time;
		}
		return $buy_time_count;
	}

	public function std_class_object_to_array($stdclassobject){
    $_array = is_object($stdclassobject) ? get_object_vars($stdclassobject) : $stdclassobject;
	    foreach ($_array as $key => $value) {
	        $value = (is_array($value) || is_object($value)) ? std_class_object_to_array($value) : $value;
	        $array[$key] = $value;
	    }

	    return $array;
	}

	/*
	 * 新的一期开始
	 * 生成夺宝号码
	 */
	public function createCodeNum($activity_period_id){

		$lottery = new LotteryModel();

		// 获取生成夺宝号期数，所需人次
		$list = $lottery -> getActivity(array('id' => $activity_period_id));
		Log::info(var_export($list,true));
		$period_num = $list -> id;
		$need_times = $list -> real_need_times;

		$data_arr = array();
		$data = array();
		$data['activity_period_id'] = $activity_period_id;
		$randNum = array();
		for($i=0;$i<$need_times;$i++){
			$randNum[] =  $i + 10000001;
			//$data['code_num'] = $i + 10000001;
			//$data_arr[] = $data;
		}
		shuffle($randNum);
		foreach ($randNum as $key => $value) {
			$data['code_num'] = $value;
			$data_arr[] = $data;
		//	var_dump($value);
		}
		//var_dump($data_arr);
		//die;
		$res = $lottery->addCodeNum($data_arr);
		return $res;
	}


	public function getNextLotteryPeriod(){
		$expect = DB::table('lottery_shishi')->orderBy('open_time', 'DESC')->pluck('expect');
		return $expect+1;
	}

	/*
	 * 新增夺宝订单
	 */
	public function addBuyOrder($periodId, $userId, $num){
		$data = array('sh_activity_period_id'=>$periodId, 'user_id'=>$userId, 'num'=>$num);
		return DB::table('buy_order')->insertGetId($data);
	}

	/*
	 * 查看夺宝订单信息
	 */
	public function getBuyOrder($id){
		$row = DB::table('buy_order')->where('id', $id)->select('*')->first();
		return $row;
	}

	/*
	 * 查看夺宝订单信息
	 */
	public function getBuyOrderList($id){
		$list = DB::table('buy_order')->where('id', $id)->select('*')->get();
		return $list;
	}

	/*
	 * 更新夺宝订单状态
	 */
	public function setBuyOrderStatus($userId,$periodId, $status=0){
		return DB::table('buy_order')->where('user_id', $userId)->where('sh_activity_period_id', $periodId)->update(array('status'=>$status));
	}

}
