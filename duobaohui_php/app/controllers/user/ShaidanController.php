<?php
/**
 * 设置收货地区操作
 *
 * @author		wuhui@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\User;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Response;	//引入response
use Illuminate\Support\Facades\Session;		//引入参数类

use Laravel\Model\ShaidanModel;				//引入model
use Laravel\Model\NewloginModel;

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */
class ShaidanController extends ApiController {
	public $response;	//定义public公开成员变量
	private $_model;	//定义private私有成员变量，通常$_ 开头

	public function __construct(){
		parent::__construct();
		$this->_model = new ShaidanModel();
	}


	public function anyAddShaidan(){
		if( !Input::has('user_id') || !Input::has('content') || !Input::has( 'title' )  || !Input::has( 'order_id' ) || !Input::has( 'goods_id' )) {
            return Response::json( $this->response( '10005'));
        }
		$img = array();
		if( Input::has('img') ){
			$img = Input::get('img');
		}

		$userId		= Input::get('user_id');
		$orderId	= Input::get('order_id');
		$content	= Input::get('content');
		$title		= Input::get('title');
		$goodsId	= Input::get('goods_id');

		$user = new NewloginModel();
	 	$isUser = $user->getUserInfoByUserId( $userId );

		if(!$isUser){

	 		return Response::json( $this->response( '0' )  );
	 	}

		//$orderInfo	=	$this->_model->getOrderId($orderId);

		//if(!$orderInfo){

		//	return Response::json( $this->response( '0' )  );
		//}


		$data = array();
		$data['sh_user_id']		= $userId;
		$data['sh_order_id']	= $orderId;
		$data['content']		= $content;
		$data['title']			= $title;
		$data['sh_goods_id']	= $goodsId;
		$data['create_time']	= date('Y-m-d H:i:s');

		$result=$this->_model->addShaidan($data , $img);

		if( $result ) {
			$this->response = $this->response(1, '添加成功',$result);
		}else{

			$this->response = $this->response(0,'添加失败');
		}

		return Response::json($this->response);
	}

	public function anyGetShaidan(){

		$search = array();
		if( Input::has('user_id') ) {
			$search['userId']	= Input::get('user_id');
		}
		if( Input::has('goods_id') ) {
			$search['goodsId']	= Input::get('goods_id');
		}

		$version = '';
		if(Input::has('version')) $version = Input::get('version');

		$pageinfo = $this->pageinfo();
		$shaidan = $this->_model->getShaidan($search , $pageinfo->offset , $pageinfo->length, $version);

		if($shaidan){
			$this->response = $this->response(1, '成功',$shaidan);
		}else{
			$this->response = $this->response(0,'失败');
		}

		return Response::json($this->response);

	}

	private function pageinfo($length=10){
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
