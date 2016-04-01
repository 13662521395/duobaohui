<?php
/**
 * 交易纪录控制器
 * @author		zhaozhonglin@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Activity;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session
use Illuminate\Support\Facades\Response;	//引入response

use Laravel\Model\RecordModel;			//引入model
use Laravel\Model\CategoryModel;			//引入model

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */
class RecordController extends ApiController {
	public function __construct(){
		parent::__construct();
	}

	/**
	 * 喇叭（首页）
	 * @param
	 * @return object
	 */
	public function anyNewestwinninglist(){
	    $recordM = new RecordModel();
	    $newest_winning_list = $recordM->getNewestWinningList();
	    if(empty($newest_winning_list)){
	        $this->response = $this->response('20302');
	    }else {
	        $this->response = $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS, $newest_winning_list);
	    }
	    return Response::json($this->response);
	}

	/**
	 * 夺宝纪录（我的，夺宝纪录）
	 * @param flag 0:全部 1:进行中 2:已揭晓
	 * @return json
	 */
	public function anyRecords(){
		$flag = Input::has('flag')?Input::get('flag'):0;
	    $user = Session::get('user');
	    $user_id = $user['id'];
	    $join_records_list = $this->joinRecords($user_id);
	    $period_id_list = array();
	    foreach ($join_records_list as $join_record){
	        $period_id_list[] = $join_record->sh_activity_period_id;
	    }
	    $winning_records_list = $this->winningRecords($period_id_list);

		$running_records_list = array();
		$finished_records_list = array();
	    foreach ($join_records_list as $join_record){
			$isFinished = false;
			$running_records_hasAdd = false;
	        $join_record->rate = $this->getRate($join_record->current_times, $join_record->real_need_times);
			if($join_record->rate < 100) {
				$running_records_list[] = $join_record;
				$running_records_hasAdd = true;
			} else {
				foreach ($winning_records_list as $winning_record){
					if ($join_record->sh_activity_period_id == $winning_record->sh_activity_period_id){
						$join_record->winning_user = $winning_record;
						$finished_records_list[] = $join_record;
						$isFinished = true;
					}
				}
			}
			if(!$isFinished && !$running_records_hasAdd){
				$running_records_list[] = $join_record;
			}
		}

		switch($flag){
			case 0: $res_list = $join_records_list; break;
			case 1: $res_list = $running_records_list; break;
			case 2: $res_list = $finished_records_list; break;
			default: $res_list = $join_records_list;
		}

	    if(empty($res_list)){
            $this->response = $this->response('20302');
	    }else{
            $this->response = $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS, $res_list);
        }
	    return Response::json($this->response);
	}

	public function anyRecordsNum(){
		$user = Session::get('user');
		$user_id = $user['id'];
		$join_records_list = $this->joinRecordsAll($user_id);
		$period_id_list = array();
		foreach ($join_records_list as $join_record){
			$period_id_list[] = $join_record->sh_activity_period_id;
		}
		$winning_records_list = $this->winningRecords($period_id_list);

		$running_records_list = array();
		$finished_records_list = array();
		foreach ($join_records_list as $join_record){
			$isFinished = false;
			$running_records_hasAdd = false;
			$join_record->rate = $this->getRate($join_record->current_times, $join_record->real_need_times);
			if($join_record->rate < 100) {
				$running_records_list[] = $join_record;
				$running_records_hasAdd = true;
			} else {
				foreach ($winning_records_list as $winning_record){
					if ($join_record->sh_activity_period_id == $winning_record->sh_activity_period_id){
						$join_record->winning_user = $winning_record;
						$finished_records_list[] = $join_record;
						$isFinished = true;
					}
				}
			}
			if(!$isFinished && !$running_records_hasAdd){
				$running_records_list[] = $join_record;
			}
		}

		$res_data = array();
		$res_data['all'] = count($join_records_list);
		$res_data['run'] = count($running_records_list);
		$res_data['finish'] = count($finished_records_list);
		if(empty($res_data)){
			$this->response = $this->response('20302');
		}else{
			$this->response = $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS, $res_data);
		}
		return Response::json($this->response);
	}

	/**
	 * 本期夺宝次数列表（查看我的号码）
	 */
	public function anyLotterytimeslistbyperiodid(){
	    if( ! Input::has('sh_activity_period_id') ){
	        return Response::json( $this->response( '10005' ) );
	    }
	    $sh_activity_period_id = Input::get('sh_activity_period_id');
		if(Input::has('user_id')) {
			$user_id = Input::get('user_id');
		} else {
			$user = Session::get('user');
			$user_id = $user['id'];
		}
	    $recordM = new RecordModel();
	    $pageinfo = $this->pageinfo();
	    $lottery_code_list = $recordM -> getLotterytimeslistbyperiodid($user_id , $sh_activity_period_id , $pageinfo->offset, $pageinfo->length );
	    if(empty($lottery_code_list)){
	        $this->response = $this->response('20302');
	    }else {
	        $this->response = $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS, $lottery_code_list);
	    }
	    return Response::json($this->response);
	}
	/**
	 * 本期、本次 夺宝号列表（查看我的号码）
	 */
	public function anyLotterycodelistbyperiodid(){
	    if( ! Input::has('sh_activity_period_id') ){
	        return Response::json( $this->response( '10005' ) );
	    }
	    //夺宝纪录主键id
	    if( ! Input::has('sh_period_user_id') ){
	        return Response::json( $this->response( '10005' ) );
	    }

		if(Input::has('user_id')) {
			$user_id = Input::get('user_id');
		} else {
			$user = Session::get('user');
			$user_id = $user['id'];
		}
	    $sh_activity_period_id = Input::get('sh_activity_period_id');
	    $sh_period_user_id = Input::get('sh_period_user_id');

	    $recordM = new RecordModel();
	    $pageinfo = $this->pageinfo();
	    $lottery_code_list = $recordM -> getLotterycodelistbyperiodid($user_id , $sh_activity_period_id , $sh_period_user_id , $pageinfo->offset, $pageinfo->length );
	    foreach($lottery_code_list as $value){
		    $value->code_num = (string)$value->code_num;
	    }

		if(empty($lottery_code_list)){
	        $this->response = $this->response('20302');
	    }else {
	        $this->response = $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS, $lottery_code_list);
	    }
	    return Response::json($this->response);
	}
	/**
	 * 计算详情（详情页）
	 */
	public function anyCountdetail(){
	    if( ! Input::has('sh_activity_period_id') ){
	        return Response::json( $this->response( '10005' ) );
	    }
	    $sh_activity_period_id = Input::get('sh_activity_period_id');
	    $recordM = new RecordModel();
	    $count_detail_res = $recordM -> getCountdetail( $sh_activity_period_id );
	    $lottery_code_list_res = $recordM -> getLotteryCodeList( $sh_activity_period_id );
	    foreach ($lottery_code_list_res as $lottery_code){
	        $buy_time = $lottery_code -> buy_time;
// 	        $buy_time_date = explode(' ',$buy_time)[1];
// 	        $buy_time_time = explode(' ',$buy_time)[0];

// 	        $date_time = date('Y-m-d H:i:s', $buy_time_date);
// 			$time = date('His', $buy_time_date);
// 			$mic_s = (str_replace('0.','.',number_format($buy_time_time,3)));

// 			$date_time_mic_s = $date_time.$mic_s;
// 			$time_mic_s = $time.str_replace('.','',$mic_s);
			$date_time_mic_s = $lottery_code -> buy_time_date;
			$time_mic_s = $lottery_code -> buy_time_code;

			$var['buy_time'] = $buy_time;
			$var['date_time_mic_s'] = $date_time_mic_s;
			$var['time_mic_s'] = $time_mic_s;
			$var['user_id'] = $lottery_code -> user_id;
			$var['nick_name'] = $lottery_code -> nick_name;
			$lottery_code_list[] = $var;
	    }
	    $res_list['count_detail_res'] = $count_detail_res;
	    $res_list['lottery_code_list'] = $lottery_code_list;
	    if(empty($res_list)){
	        $this->response = $this->response('20302');
	    }else {
	        $this->response = $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS, $res_list);
	    }
	    return Response::json($this->response);
	}

	/**
	 * 参与纪录（我的，夺宝纪录）
	 * @param 用户id $user_id
	 * @return object
	 */
	private function joinRecords($user_id){
	    $recordM = new RecordModel();
	    $pageinfo = $this->pageinfo();
	    $list = $recordM->getJoinRecords($user_id,$pageinfo->offset, $pageinfo->length);
	    return $list;
	}

	private function joinRecordsAll($user_id){
		$recordM = new RecordModel();
		$list = $recordM->getJoinRecordsAll($user_id);
		return $list;
	}

	/**
	 * 中奖纪录（我的，夺宝纪录）
	 * @param 期数表主键数组 $period_id_list
	 * @return object
	 */
	private function winningRecords($period_id_list){
		$recordM = new RecordModel();
		$winning_records_list = $recordM->getWinningRecords($period_id_list);
		return $winning_records_list;
	}

	/** 中奖纪录改版
	 * @return \Illuminate\Http\JsonResponse|null
	 */
	public function anyWinningRecords(){
		$recordM = new RecordModel();
		$user = Session::get('user');
		$user_id = $user['id'];
		$res = $recordM->winningRecords($user_id);
		if(!empty($res)) {
			$this->response = $this->response(1,null,$res->getItems());
		} else {
			$this->response = $this->response(0);
		}
		return Response::json($this->response);

	}

	/** 中奖纪录详情改版（我的，中奖纪录）
	 * @return \Illuminate\Http\JsonResponse|null
	 */
	public function anyWinningDetail(){
		if( ! Input::has('sh_activity_period_id') ){
			return Response::json( $this->response( '10005' ) );
		}
		$recordM = new RecordModel();
		$user = Session::get('user');
		$user_id = $user['id'];
		$sh_activity_period_id = Input::get('sh_activity_period_id');
		$res = $recordM->winningDetail($user_id,$sh_activity_period_id);
		if(!empty($res)) {
			$this->response = $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS, $res);
		} else {
			$this->response = $this->response(0);
		}
		return Response::json($this->response);

	}

	/**
	 * 往期揭晓（详情页）
	 */
	public function anyWinninghistorylist(){
	    if( ! Input::has('sh_activity_id') ){
	        return Response::json( $this->response( '10005' ) );
	    }
	    $sh_activity_id = Input::get('sh_activity_id');
	    $period_number = Input::get('period_number');
	    $recordM = new RecordModel();
	    $pageinfo = $this->pageinfo();
	    $winninghistorylist = $recordM -> getWinninghistorylist($sh_activity_id , $period_number ,$pageinfo->offset, $pageinfo->length );
	    if(empty($winninghistorylist)){
	        $this->response = $this->response('20302');
	    }else {
	        $this->response = $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS, $winninghistorylist);
	    }
	    return Response::json($this->response);
	}

	/**
	 * 计算进度条百分比
	 */
	public function getRate($current_times, $real_need_times){
	    $rate = $real_need_times >0 ? $current_times/$real_need_times * 100 : 0;
	    $rate = ceil($rate);
	    if($rate==100 && $current_times<$real_need_times){
	        $rate = 99;
	    }
	    return $rate;
	}


	private function pageinfo($length=20){
	    $pageinfo               = new \stdClass;
	    $pageinfo->length       = Input::has('length') ? Input::get('length') : $length;;
	    $pageinfo->page         = Input::has('page') ? Input::get('page') : 1;
	    $pageinfo->offset		= $pageinfo->page<=1 ? 0 : ($pageinfo->page-1)*$pageinfo->length;
	    //$page->totalNum     = (int)Product::getInstance()->getPurchaseTotalNum();
	    $pageinfo->totalNum     = 0;
	    $pageinfo->totalPage    = ceil($pageinfo->totalNum/$pageinfo->length);
	    return $pageinfo;
	}

	public function getTest(){
	    //$recordM = new RecordModel();
	    //$res = $recordM -> test();
	    //return $res;
	}
}
