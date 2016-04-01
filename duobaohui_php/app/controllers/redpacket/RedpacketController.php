<?php
/**
 * 红包模块接口
 *
 * @author		liangfeng@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Redpacket;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session
use Illuminate\Support\Facades\Response;	//引入response
use Laravel\Service\RedpacketService;
use Laravel\Model\RedpacketModel;
use Laravel\Model\BannerModel;
/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */

class RedpacketController extends ApiController
{

	public function __construct()
	{
		parent::__construct();
	}

	/*
	 * 充值页面广告图
	 * /redpacket/redpacket/recharge-banner
	 */
	public function anyRechargeBanner(){
		$bannerM = new BannerModel();
		$list = $bannerM->getRechargeBanner();
		$this->response = $this->response(1, '成功' ,$list);
		return Response::json($this->response);
	}


	/*
	 * 签到送红包
	 */
//	public function anySignSendRedpacket(){
//		if(!Input::has('user_id')){
//			return Response::json( $this->response( '10005' ) );
//		}
//
//		$userId = Input::get('user_id');
//		//注册送红包
//		$sendRedpacket = new RedpacketService();
//		$status = $sendRedpacket->sendSignRedpacket($userId);
//		if($status){
//			return Response::json( $this->response( '1' ) );
//		}else{
//			return Response::json( $this->response( '0' ) );
//		}
//	}


	/*
	 * 获取红包列表
	 * $type  0为可用   1为已用   2为已过期
	 */
	public function anyGetRedpacketList(){
		if(!Input::has('type')){
			return Response::json( $this->response( '10005' ) );
		}
		if(!Session::has('user')){
			return Response::json( array('code'=>999999,'msg'=>'用户未登陆') );
		}
		$userId = Session::get('user.id');
		$type   = Input::get('type');

		$pageinfo = $this->pageinfo();
		$redpacketM = new RedpacketModel();
		$result['list'] = $redpacketM->getRedpacketByApi($userId,$type, $pageinfo->offset , $pageinfo->length);
		$result['count']= $redpacketM->CountRedpacket($userId);
		if($result['list']){
			$this->response = $this->response(1, '成功' ,$result);
		}else{
			$this->response = $this->response(0, '没有红包');
		}
		return Response::json($this->response);
	}


	/*
	 * 红包详情----领取
	 */
	public function anyGetRedpacketByRedpacketid(){
		if(!Input::has('redpacket_id')){
			return Response::json( $this->response( '10005' ) );
		}

		$redpacketId = Input::get('redpacket_id');

		$redpacketM = new RedpacketModel();
		$result = $redpacketM->getRedpacketByRedpacketid($redpacketId);

		if($result){
			$this->response = $this->response(1, '成功' ,$result);
		}else{
			$this->response = $this->response(0, '失败');
		}
		return Response::json($this->response);
	}

	/*
	 * 商品详情购买页面的红包列表
	 * 条件：该活动可用红包的显示
	 */
	public function anyGetRedpacketByActivityid(){
		if(!Input::has('activity_id') || !Input::has('num')){
			return Response::json( $this->response( '10005' ) );
		}
		if(!Session::has('user')){
			return Response::json( array('code'=>999999,'msg'=>'用户未登陆') );
		}
		$userId     = Session::get('user.id');
		$acticity   = Input::get('activity_id');
		$num        = Input::get('num');

		$pageinfo   = $this->pageinfo();
		$redpacketM = new RedpacketModel();
		$result     = $redpacketM->getRedpacketByActivityid($userId,$acticity,$num,$pageinfo->offset , $pageinfo->length);

		if($result){
			$this->response = $this->response(1, '成功' ,$result);
		}else{
			$this->response = $this->response(0, '没有红包');
		}
		return Response::json($this->response);

	}

	//分页
	private function pageinfo($length=20){
		$pageinfo               = new \stdClass;
		$pageinfo->length       = Input::has('length') ? Input::get('length') : $length;;
		$pageinfo->page         = Input::has('page') ? Input::get('page') : 1;
		$pageinfo->offset		= $pageinfo->page<=1 ? 0 : ($pageinfo->page-1) * $pageinfo->length;
		//$page->totalNum     = (int)Product::getInstance()->getPurchaseTotalNum();
		$pageinfo->totalNum     = 0;
		$pageinfo->totalPage    = ceil($pageinfo->totalNum/$pageinfo->length);

		return $pageinfo;
	}

}
