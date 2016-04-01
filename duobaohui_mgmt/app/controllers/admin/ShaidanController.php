<?php
/**
 * @author		wuhui@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Admin;

use AdminController;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Redirect;
use Illuminate\Support\Facades\Config;

use App\Libraries\Qiniu\Auth;				//引入七牛

use Laravel\Model\Admin\ShaidanModel;

class ShaidanController extends AdminController {
	private $_length = 20;

	public function __construct(){
		parent::__construct();
	}

	/**
	 * 获取被举报的晒单列表
	 */
	public function getReportShaiDanList(){
		$shaidanModel = new ShaidanModel();
		$data['selected'] = "shaidan";
		$data['msg'] = "";

		$list = $shaidanModel->getReportShaiDanList($this->_length);
		$data['list'] = $list;
		return View::make('admin.shaidan.reportList' , $data);
	}


	/*
	 * 获取晒单列表
	 * wuhui
	 */
    public function getList(){
		$shaidanModel = new ShaidanModel();

		$data['list'] = array();
		$data['selected'] = "shaidan";
		$data['msg'] = "";

		$totalNum = $shaidanModel->getShaidanTotalNum();
		if($totalNum == 0){
			return Response::view('admin.shaidan.list' , $data);
		}
		$pageInfo = $this->_pageInfo($this->_length , $totalNum);
		$data['pageInfo'] = $pageInfo;
		$data['list'] = $shaidanModel->getShaidanList($pageInfo->offset , $pageInfo->length);		

        return View::make('admin.shaidan.list' , $data);
	}

	/*
	 * 显示晒单页面
	 */
	public function getAddShaidan(){
		$data['selected'] = "shaidan";
		$data['msg'] = "";
		if( !Input::has('period_result_id')){
			return Redirect::to('/admin/shaidan/list');
		}

		$periodResultId = Input::get('period_result_id');

		$shaidanModel = new ShaidanModel();
		
		$data['data'] = $shaidanModel->getPeriodResultById($periodResultId);

        return View::make('admin.shaidan.add' , $data);
	}

	/*
	 * 添加晒单数据
	 */
	public function postSaveShaidan(){
		if( !Input::has('period_result_id')){
			return Redirect::to('/admin/shaidan/list');
		}
		$periodResultId = Input::get('period_result_id');

		$shaidanModel = new ShaidanModel();
		$data['data'] = $shaidanModel->getPeriodResultById($periodResultId);
		$data['selected'] = "shaidan";
		$data['msg'] = "添加成功";

		if( !Input::has('user_id') || !Input::has('order_id') || !Input::has('goods_id') || !Input::has('title') || !Input::has('content')){
			$data['msg'] = "参数错误";
			return View::make('admin.shaidan.add' , $data);
		}

		$userId			= Input::get('user_id');
		$orderId		= Input::get('order_id');
		$goodsId		= Input::get('goods_id');
		$title			= Input::get('title');
		$content		= Input::get('content');

		$imgUrl		= array();
		if(Input::has('img_url')){
			$imgUrl = Input::get('img_url');	
		}

		$sqldata = array(
			'sh_user_id'	=> $userId,
			'sh_order_id'	=> $orderId,
			'sh_goods_id'	=> $goodsId,
			'title'		=> $title,
			'content'	=> $content
		);

		
		$shaidanId = $shaidanModel->saveShaidan($sqldata);

		if($shaidanId && !empty($imgUrl)){
			$shaidanModel->saveShaidanImg($shaidanId , $imgUrl);	
		}

		$shaidanModel->updateOrderShaidan($orderId);

        return View::make('admin.shaidan.add' , $data);
	}

	/*
	 * 获取qiniu上传图片的token
	 */
	public function anyJsQiniuToken(){
		if(!Input::has('image')){
			$bucket		= Config::get('app.qiniu_backup');
		}else{
			$bucket = Input::get('image');
		}
		
        $accessKey	= Config::get('app.qiniu_accessKey');
        $secretKey	= Config::get('app.qiniu_secretKey');
		
		$expires	= Config::get('app.qiniu_expires');
		$expires	= 36000;
		$qiniu	= new Auth($accessKey , $secretKey);

		$data['uptoken'] = $qiniu->uploadToken($bucket , '' , $expires);

		return Response::json($data);

	}

	/*
	 * 上传图片
	 */
	public function postUploadImg(){
		$data = array('msg'=>'Success');

		if( !Input::hasFile('file') ){
			$data['msg'] = 'not file be upload';
			return Response::JSON($data)->header('status' , '404');
		}

		if( !Input::file('file')->isValid() ){
			$data['msg'] = 'file don\'t valid';
			return Response::JSON($data)->header('status' , '404');
		}
		
		$destinationPath	= $_SERVER['DOCUMENT_ROOT'].'/upload/img/';
		$filename			= time().mt_rand(1000 , 9999);
		$extension			= Input::file('file')->getClientOriginalExtension();

		$file = Input::file('file')->move($destinationPath , $filename.'.'.$extension);


	}

	/*
	 * 虚拟用户中奖列表
	 */
	public function getWinUserList(){
		$data['selected']	= 'shaidan';	
		$data['list']		= array();
		$shaidanModel = new ShaidanModel();



		$data['selected'] = 'shaidan';
		if(Input::has('start_time')){
			$startTime = Input::get('start_time');
			$endTime = Input::get('end_time');
			$data['list'] = $shaidanModel->getWinUserByNotRealListByTime($startTime,$endTime);
			$data['start_time']	= $startTime;
			$data['end_time']	= $endTime;
		}else{
			$data['list'] = $shaidanModel->getWinUserByNotRealList();
			$data['now_date'] = date('Y-m-d H:i:s');
			$data['tomorow']  = date("Y-m-d H:i:s",strtotime("+1 day"));
		}


        return View::make('admin.shaidan.user_win_list' , $data);
	}

	private function _pageInfo($length=40 , $totalNum){
		$pageInfo               = new \stdClass;
		$pageInfo->length       = Input::has('length') ? Input::get('length') : $length;;
		$pageInfo->page         = Input::has('page') ? Input::get('page') : 0;
		$pageInfo->offset		= $pageInfo->page<=1 ? 0 : ($pageInfo->page-1) * $pageInfo->length;
		$pageInfo->totalNum     = $totalNum; 
		$pageInfo->totalPage    = ceil($pageInfo->totalNum/$pageInfo->length);

		return $pageInfo;
	}

	//删除晒单
	public function getDeleteShaidan(){
		if( !Input::has('shaidan_id') ){
			return Redirect::to('/admin/shaidan/list');
		}

		$shaidanId = Input::get('shaidan_id');
		$shaidanModel = new ShaidanModel();

		if($shaidanModel->deleteShaidan($shaidanId)){
			return Response::json(array('code'=>1 , 'msg'=>'删除成功'));
		}else{
			return Response::json(array('code'=>0 , 'msg'=>'删除失败'));
		}

	}

	//获取网易晒单类容
	public function getShaidanListByWangyi(){
		if( !Input::has('goods_id') ){
			$error = array(
				'code'	=> 0,
				'msg'	=> '参数错误'
			);
			return Response::json($error);
		}

		$goodsId = Input::get('goods_id');

		$shaidanModel = new ShaidanModel();

		$totalNum = $shaidanModel->getShaidanByWangyiTotalNum($goodsId);
		if($totalNum == 0){
			$error['code']	=	0;
			$error['msg'] = '没有查询到数据';
			return Response::json($error);
		}

		$pageInfo = $this->_pageInfo($this->_length , $totalNum);

		$data['pageInfo']	= $pageInfo;
		$data['list']		= $shaidanModel->getShaidanByWangyi($goodsId , $pageInfo->offset , $pageInfo->length);
		$data['code']		= 1;
		$data['msg']		= '请求成功';

		if($data){
			return Response::json($data);
		}else{
			$error['msg'] = '没有查询到数据';
			return Response::json($error);
		}
		
	}

	//修改晒单类容
	public function getEditShaidan(){
		if( !Input::has('shaidan_id') ){
			return Redirect::to('/admin/shaidan/list');
		}

		$shaidanId	= Input::get('shaidan_id');
		$shaidanModel = new ShaidanModel();

		$data['selected'] = "shaidan";
		$data['msg'] = "";

		$data['info'] = $shaidanModel->getShaidanInfo($shaidanId);		

		return Response::View('admin.shaidan.edit' , $data);

	}

	//更新晒单类容
	public function postUpdateShaidan(){
		if( !Input::has('shaidan_id') ){
			$data['msg'] = "参数错误";
			return Redirect::to('/admin/shaidan/list');
		}

		if( Input::has('user_id') ){
			$userId			= Input::get('user_id');
		}
		if( Input::has('order_id') ){
			$orderId		= Input::get('order_id');
		}
		if( Input::has('goods_id') ){
			$goodsId		= Input::get('goods_id');
		}
		if( Input::has('title') ){
			$title			= Input::get('title');
		}
		if( Input::has('title') ){
			$content		= Input::get('content');
		}

		$imgUrl		= array();
		if(Input::has('img_url')){
			$imgUrl = Input::get('img_url');	
		}

		$sqldata = array(
			'sh_user_id'	=> $userId,
			'sh_order_id'	=> $orderId,
			'sh_goods_id'	=> $goodsId,
			'title'		=> $title,
			'content'	=> $content
		);

		$shaidanId	= Input::get('shaidan_id');
		$shaidanModel = new ShaidanModel();

		$isUpdate = $shaidanModel->updateShaidan($shaidanId , $sqldata);

		if($isUpdate && $shaidanId && !empty($imgUrl)){
			$shaidanModel->saveShaidanImg($shaidanId , $imgUrl);	
		}

		return Redirect::to('/admin/shaidan/list?shaidan_id='.$shaidanId);

	}

}
