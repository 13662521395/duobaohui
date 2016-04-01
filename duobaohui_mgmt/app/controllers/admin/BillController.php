<?php
/**
 * @author		wanghaihong@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Admin;

use AdminController;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Response;

use Laravel\Model\Admin\ActivityModel;

date_default_timezone_set('PRC');
class BillController extends AdminController {
	public function __construct(){
		parent::__construct();
	}


	/*
	 * 脚本的交易记录查询
	 * 10分钟跑一次定时脚本
	 * /admin/bill/script-paylist
	 */
	public function getScriptPaylist(){
		$startTime = date('Y-m-d H:i:s');
		$endTime   = date("Y-m-d H:i:s",strtotime("-10 minute"));
		$activityM = new ActivityModel();
		$data	   = $activityM->getScripyPayByTime($startTime , $endTime);
		if($data){
			return Response::json(array('code'=>1 , 'msg'=>'成功'));
		}else{
			return Response::json(array('code'=>0 , 'msg'=>'没有交易'));
		}
	}



	/*
	 * 支付记录
	 */
	public function getPayList(){
		$data['now_date'] = date('Y-m-d 00:00:00');
		$data['tomorow']  = date("Y-m-d 00:00:00",strtotime("+1 day"));
		$data['selected'] = "bill";

		$activityM = new ActivityModel();
		$data['total_money'] = $activityM->getTotalMoney();

		//交易记录
		if(!Input::has('user_id') && !Input::has('tel') && !Input::has('jnl_no') && !Input::has('start_time') && !Input::has('end_time')){
			//默认今天
			$startTime = date('Y-m-d 00:00:00');
			$endTime   = date("Y-m-d 00:00:00",strtotime("+1 day"));
			$data['list'] 		= $activityM->getPayByTime($startTime , $endTime);
			return Response::view('admin.bill.pay_list' , $data);
		}else{
			if(Input::has('tel')){
				$tel = Input::get( 'tel' );
				$data['list'] 	= $activityM->getTelPayList(  $tel );
				$data['tel']	= $tel;

			}elseif(Input::has('jnl_no')){
				$condition 		= 'trans_jnl_no';
				$conditionValue = Input::get('jnl_no');
				$data['list'] 	= $activityM->getPayList($condition , $conditionValue);
				$data['jnl_no']	= $condition;
		
			}elseif(Input::has('start_time') && Input::has('end_time')){
				$startTime = Input::get('start_time');
				$endTime   = Input::get('end_time');
				$type	   = Input::get('choice');
				$data['start_time']	= $startTime;
				$data['end_time']	= $endTime;

				if($type ==0 ){
					$data['list'] 		= $activityM->getPayByTime($startTime , $endTime);
					$data['choice']		= 0;
				}elseif($type ==1){
					$data['list'] 		= $activityM->getPayRechargeByTime($startTime , $endTime);
					$data['choice']		= 1;
				}elseif($type ==2){
					$data['list'] 		= $activityM->getPayDuobaoByTime($startTime , $endTime);
					$data['choice']		= 2;
				}
			}

		}
		$data['selected'] = "bill";
        return Response::view('admin.bill.pay_list' , $data);
	}


	/*
	 *  搜索异常
	 */
	public function getSearchBill(){
		$activityM = new ActivityModel();
		$data['list'] = $activityM->getSearchBill();
		$data['selected'] = "bill";
		return Response::view('admin.bill.search_bill' , $data);
	}

	/*
	 *  夺宝信息
	 */
	public function getAlipayDuobao(){
		if(!Input::has('duobao_id')){

		}
		$duobaoId = Input::get( 'duobao_id' );
		$activityM = new ActivityModel();
		$data['list'] 	= $activityM->getAlipayDuobao( $duobaoId );
		$data['selected'] = "bill";
        return Response::view('admin.bill.alipay_duobao' , $data);
	}



	/*
	 *  充值信息
	 */
	public function getAlipayRecharge(){
		if(!Input::has('jnl_no')){

		}
		$jnlNo = Input::get( 'jnl_no' );
		$activityM = new ActivityModel();
		$data['list'] 	= $activityM->getAlipayRecharge( $jnlNo );
		$data['selected'] = "bill";
        return Response::view('admin.bill.alipay_recharge' , $data);

	}


	/*
	 * 支付宝账务明细异常
	 * liangfeng@shinc.net
	 */
	public function getAlipayAccount(){
		$activityM = new ActivityModel();
		if(Input::has('user_id')){
			$conditionValue = Input::get( 'user_id' );
			$condition 		= 'user.id';
			$data['list'] 	= $activityM->getAlipayAccount( $condition, $conditionValue );
			$data['user_id']= $conditionValue;
		}else{
			$data['list'] = $activityM->getAlipayAccount($condition='user.is_real' , $conditionValue=0);
		}
		$data['selected'] = "bill";
        return Response::view('admin.bill.alipay_account' , $data);
	}

	/*
	 * 处理虚假订单状态
	 */
    public function getChangeStatus(){
		if( !Input::has('id') ){
			// return Redirect::to('/admin/user/login');
		}
		$id = Input::get('id');
		$activityM = new ActivityModel();
		if($activityM->changeStatus($id)){
			return Response::json(array('code'=>1 , 'msg'=>'删除成功'));
		}else{
			return Response::json(array('code'=>0 , 'msg'=>'删除失败'));
		}
	}


	/*
	 * 修复真实重复订单状态
	 */
    public function getEditAlipay(){
		if( !Input::has('id') ){
			// return Redirect::to('/admin/user/login');
		}
		$id = Input::get('id');
		$activityM = new ActivityModel();
		if($activityM->editAlipay($id)){
			return Response::json(array('code'=>1 , 'msg'=>'删除成功'));
		}else{
			return Response::json(array('code'=>0 , 'msg'=>'删除失败'));
		}
	}

	/*
	 *	 基础统计数据
	 *   统计总购买次数(真实用户,虚拟用户)、红包除外的总消费金额(真实用户,虚拟用户)、统计奖品总价(真实用户,虚拟用户)
	 */
	 public function getIndex(){
	 	$activityM = new ActivityModel();
	 	if(Input::get('start_time') && Input::has('end_time')){
	 		$startDateTime = Input::get('start_time');
			$nowDateTime   = Input::get('end_time');
	 	}else{
			$startDateTime = date('Y-m-d 00:00:00');
			$nowDateTime   = date("Y-m-d 00:00:00",strtotime("+1 day"));
	 	}
	 	 $data['list']  = $activityM->getDataCountList($startDateTime,$nowDateTime);
		 //debug($data);
	 	 $data['selected'] = "bill";
		 $data['start_time']	= $startDateTime;
		 $data['end_time']	= $nowDateTime;
	 	return Response::view('admin.bill.index' , $data);
	 }

	 /*
	  *  基础统计数据
	  *  统计总购买次数(真实用户,虚拟用户)、红包除外的总消费金额(真实用户,虚拟用户)、统计奖品总价(真实用户,虚拟用户)
	  */
	 public function getDetailCountData(){
	 	$activityM = new ActivityModel();
	 	if(Input::has('start_time') && Input::has('end_time')){
	 		$startDateTime = Input::get('start_time');
			$nowDateTime   = Input::get('end_time');
	 	}else{
			$startDateTime = date('Y-m-01 00:00:00');
			$nowDateTime   = date("Y-m-31 00:00:00");
	 	}
	 	$data['list']  = $activityM->getDetailCountData($startDateTime,$nowDateTime);
	 	$data['selected'] = "bill";
	 	return Response::json($data);
	 }

	  /*
	  *  真实用户和虚拟用对比
	  *  统计总购买次数(真实用户,虚拟用户)、红包除外的总消费金额(真实用户,虚拟用户)、统计奖品总价(真实用户,虚拟用户)
	  */
	 public function getCompareUserCount(){
	 	$activityM = new ActivityModel();
	 	if(Input::has('start_time') && Input::has('end_time')){
	 		$startDateTime = Input::get('start_time');
			$nowDateTime   = Input::get('end_time');
	 	}else{
			$startDateTime = date('Y-m-01 00:00:00');
			$nowDateTime   = date("Y-m-31 00:00:00");
	 	}
	 	$data['list']  = $activityM->getCompareUserCount($startDateTime,$nowDateTime);
	 	$data['selected'] = "bill";
	 	return Response::json($data);
	 }





}
