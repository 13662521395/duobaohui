<?php

/*
** 运营模块的系列操作
** anthor:liangfeng@shinc.net
*/

namespace Laravel\Controller\Admin;

use AdminController;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Cache;
use Laravel\Model\Admin\OperateModel;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Redirect;
use Laravel\Service\RedpacketService;

class OperateController extends AdminController {

	protected $selected;
	protected $operates;
	private   $redpacketService;

	public function __construct(){
		parent::__construct();
		$this->selected     = 'operate';
		$this->operates     = new OperateModel();
		$this->redpacketService = new RedpacketService();
	}

	/*
	** 红包管理页面
	*/
	public function anyRedManage(){
		if(!Input::has('money') || !Input::has('programme') ){
			$data['info']       = $this->operates->getBatchInfo();
		}else{
			$search = array();
			if(Input::get('money')      == true) {
				$search['money']        = Input::get('money');
			}
			if(Input::get('programme')  == true) {
				$search['programme']    = Input::get('programme');
			}
			$search['end_num']          = Input::get('end_num');
			$search['end_num_input']    = Input::get('end_num_input');
			$search['total_price']      = Input::get('total_price');
			$search['total_price_input']= Input::get('total_price_input');
			$data['search']             = $this->operates->getBatchInfoBysearch($search);
			$data['param']              = $search;
		}

		$data['selected']               = $this->selected;

		return Response::view('admin.operate.red_manage' , $data);
	}


	/*
	 * 开启和关闭红包发放
	 */
	public function anyRedManageSend(){

		if(!Input::has('id') || !Input::has('is_start')){
			Redirect::to('/admin/operate/red-manage');
		}
		$id                 = Input::get('id');
		$isStart            = Input::get('is_start');
		$data               = $this->operates->openOrCloseRedpacket($id,$isStart);
		if($data){
			return Response::json( $this->response( '1' ) );
		}else{
			return Response::json( $this->response( '0' ) );
		}

	}


	/*
	 * 查看红包使用详情
	 */
	public function anyLookRedpacketInfo(){
		if(!Input::has('batch_id')){
			return Response::json( $this->response( '10005' ) );
		}
		$batchId            = Input::get('batch_id') ;
		$data['list']       = $this->operates->getRedpacketSupply($batchId);
		$data['selected']   = $this->selected;
		return Response::view('admin.operate.red_supply' , $data);
	}


	/*
	 * 查看红包夺宝详细信息
	 */
	public function anyLookRedpacketInfomation(){
		if(!Input::has('id')){
			return Response::json( $this->response( '10005' ) );
		}
		$id                 = Input::get('id') ;
		$data['list']       = $this->operates->getRedpacketInfomation($id);
		$data['use_info']   = $this->operates->RedpacketUseInfi($id);
		$data['selected']   = $this->selected;
		return Response::view('admin.operate.red_duobao_info' , $data);
	}


	/*
	 * 批次红包明细页面
	 */
	public function anyRedManageInfo(){
		if(!Input::has('id')){
			Redirect::to('/admin/operate/red-manage');
		}
		$id                 = Input::get('id');
		if(!Input::has('choice')){
			$data['info']   = $this->operates->getRedpacketInfoByBatch($id);
		}else{
			$choice         = Input::get('choice');
			$data['list']   = $this->operates->getRedpacketInfoBySearch($id,$choice);
			$data['choice'] = $choice;
		}

		$data['desc']       = $this->operates->getRedpacketByBatch($id);
		$data['activity']   = $this->operates->getActivityByBatchId($id);

		$data['selected']   = $this->selected;

		return Response::view('admin.operate.red_info' , $data);
	}

	/*
	 * * ================================================================================================
	 * * ================================================================================================
	 * * =====================================以下是生成红包页面和逻辑代码====================================
	 * * ================================================================================================
	 * * ================================================================================================
	 * * ================================================================================================
	 */


	/*
	** 设置红包可用活动
	*/
	public function anyGetAllActivity(){

		$info              = Cache::has('activity') ? Cache::get('activity') : $this->operates->getAllActivity();

		return Response::view('admin.operate.check_activity' , ['list'=>$info]);
	}


	/*
	** 生成红包页面
	*/
	public function anyCreateRed(){

		$data               = array();
		$hasRedpacketId     = $this->operates->getLastBatchNumber();
		if($hasRedpacketId){
			$subId          = substr($hasRedpacketId->id,0,8);
			if($subId == date('Ymd')){
				$newRedpacketId = intval($hasRedpacketId->id + 1);
			}else{
				$newRedpacketId = $this->redpacketService->createTodayFirstBatchNumer();
			}
		}else{
			$newRedpacketId = $this->redpacketService->createTodayFirstBatchNumer();
		}
		$data['bat_id']     = $newRedpacketId;
		$data['tag']        = $this->redpacketService->sendRedPacketWayTag();
		$data['selected']   = $this->selected;

		return Response::view('admin.operate.create_red' , $data);
	}


	/*
	 * 批次生成红包==========重点
	 */
	public function anyCreateRedpacketforbatch(){

		Log::error(var_export("\n\n批次生成红包详细日记开始==========================:\n", true));
		Log::error(var_export(Input::all(), true));

		if( !Input::has('batch_id') || !Input::has('red_name') || !Input::has('option_val') ||
			!Input::has('validity') || !Input::has('price')    || !Input::has('buy_money')  || !Input::has('activity_info')){
			Log::error(var_export("\n参数错误==========================:\n", true));
			return Response::json( $this->response( '10005' ) );
		}
		$id                 = Input::get('batch_id');
		$batchName          = Input::get('red_name');
		$optionVal          = Input::get('option_val');
		$validity           = Input::get('validity');
		$prices             = Input::get('price');
		$buyMoney           = Input::get('buy_money');
		$activityInfo       = Input::get('activity_info');


		//方案
		$option = array();
		if($optionVal == 1 || $optionVal==2 ||$optionVal==3){
			$option['recharge'] = $optionVal;
		}elseif($optionVal == 4){
			$option['sign'] = $optionVal;
		}elseif($optionVal == 5){
			$option['buy']  = $optionVal;
		}elseif($optionVal == 6){
			$option['new_user'] = $optionVal;
		}elseif($optionVal == 7){
			$option['inviter']  = $optionVal;
		}else{
			return Response::json( $this->response( '10005' ) );
		}

		$status = $this->redpacketService->createRedpacketforbatch($id,$batchName,$validity,$prices,$buyMoney,$activityInfo,$option);

		if($status){
			return Response::json( $this->response( '1' ) );
		}else{
			return Response::json( $this->response( '0' ) );
		}

	}








}