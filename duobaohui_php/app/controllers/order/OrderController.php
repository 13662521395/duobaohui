<?php
/**
 * 订单控制器
 * @author		zhaozhonglin@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Order;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session 
use Illuminate\Support\Facades\Response;	//引入response

use Laravel\Model\OrderModel;			//引入model
use Laravel\Model\CategoryModel;			//引入model

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class OrderController extends ApiController {
	public function __construct(){
		parent::__construct();
	}
	
	
	/**
	 * 订单状态统计
	 */
	public function anyOrderstatus(){
	    $orderM = new OrderModel();
	    if(Session::has("user.id") != true){
	    	return Response::json( $this->response( '10013' ) );
		}
	    $user = Session::get('user');
	    $user_id = $user['id'];
	    $list = $orderM->getOrderStatusList($user_id);
	    $this->response = $this->response(ApiController::SUCCESSCODE, ApiController::SUCCESS, $list);
	    return Response::json($this->response);	
	}
	
	/**
	 * 通过订单状态查询订单列表
	 */
	public function anyOrderlistbystatus(){
		if(Session::has("user.id") != true){
	    	return Response::json( $this->response( '10013' ) );
		}
	    $user = Session::get('user');
	    $user_id = $user['id'];
	    $order_status = Input::get('order_status');
	    $orderM = new OrderModel();
	    $pageinfo = $this->pageinfo();
	    $list = $orderM->getOrderListByStatus($order_status, $user_id,$pageinfo->offset, $pageinfo->length);
	    if(empty($list)){
	        $this->response = $this->response('20302');
	    }else {
		    //$orders = array();
		    foreach($list as $orderList){
			    $orderList->order_status = (string)$orderList->order_status;
		    }
    	    $this->response = $this->response(ApiController::SUCCESSCODE, ApiController::SUCCESS, $list);
	    }
	    return Response::json($this->response);
	}
	/**
	 * 更新订单状态
	 */
	public function anyUpdateorderstatus(){
	    if( !Input::has('order_id') ){
	        return Response::json( $this->response( '10005' ) );
	    }
	    if( !Input::has('order_status') ){
	        return Response::json( $this->response( '10005' ) );
	    }
	    if( !Input::has('shipping_status') ){
	        return Response::json( $this->response( '10005' ) );
	    }
	    if(Session::has("user.id") != true){
	    	return Response::json( $this->response( '10013' ) );
		}
        $user = Session::get('user');
	    $user_id = $user['id'];
	    $order_id = Input::get('order_id');
	    $order_status = Input::get('order_status');
	    $shipping_status = Input::get('shipping_status');
	    $address_id = Input::get('address_id');
	    
	    $orderM = new OrderModel();
	    
	    $res = $orderM->updateOrderStatus( $order_id , $order_status , $shipping_status , $user_id , $address_id );
	    if( ! $res ){
	        $this->response = $this->response(ApiController::FAILDCODE, ApiController::FAILD);
	    }else {
    	    $this->response = $this->response(ApiController::SUCCESSCODE, ApiController::SUCCESS);
	    }
	    return Response::json($this->response);
	}
	


	private function pageinfo($length=20){
		$pageinfo               = new \stdClass;
		$pageinfo->length       = Input::has('length') ? Input::get('length') : 20;
		$pageinfo->page         = Input::has('page') ? Input::get('page') : 1;
		$pageinfo->offset		= $pageinfo->page<=1 ? 0 : ($pageinfo->page-1)*$pageinfo->length;
		//$page->totalNum     = (int)Product::getInstance()->getPurchaseTotalNum();
		$pageinfo->totalNum     = 0; 
		$pageinfo->totalPage    = ceil($pageinfo->totalNum/$pageinfo->length);
//         var_dump($pageinfo -> page);
//         die;
		return $pageinfo;
	}

}
