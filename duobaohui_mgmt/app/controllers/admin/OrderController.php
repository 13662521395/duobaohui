<?php
/**
 * 后台管理订单控制器
 * @author		zhaozhonglin@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Admin;			// 定义命名空间

use AdminController;
use App\Libraries\Sms;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Redirect;

use Illuminate\Support\Facades\Config;
use Laravel\Model\Admin\OrderModel;
use Illuminate\Support\Facades\Log;//引入日志类
use Illuminate\Support\Facades\Validator;



/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class OrderController extends AdminController {
    
    private $orderModel;
    
	public function __construct(){
		parent::__construct();
		$this->orderModel = new OrderModel();
	}
	
	
	/**
	 * 订单列表
	 */
	
	public function anyOrderList(){
	    $shipping_status = Input::get('shipping_status');
		$luck_code_create_time = Input::get('luck_code_create_time');
	    $flag = Input::get('flag');
	    $pageinfo = $this->pageinfo();
	    $order_list = $this->orderModel->orderList($shipping_status ,$luck_code_create_time, $pageinfo->offset , $pageinfo->length);
		$order_list->appends(Input::all());
	    $data = array();
	    $data['list'] = $order_list;
	    $data['page'] = $pageinfo->page;
	    $data['selected'] = "orders";
		$data = array_merge($data,$this->viewData);
	    if($flag == 'ajax'){
    	    return Response::json($data);
	    }else{
    	    return Response::view('admin.order.list' , $data);
	    }
	     
	}
	
	/**
	 * 订单详情
	 */
	
	public function anyOrderDetail(){
	    $result = array();
	    if(Input::has('order_id')) {
	        $order_id = Input::get('order_id');
	        $order_detail = $this->orderModel->orderDetail($order_id);
	        if( $order_detail != null) {
	            $result['msg'] = '查询成功';
	            $result['order_detail']=$order_detail;
	        }else {
	            $result['msg'] = '查询失败';
	        }
	    } else {
	        $result['msg'] = 'param check error';
	        Log::error('param check error:' + json_encode(Input::all()));
	    }
	    return Response::json($result);
	}
	
	/**
	 * 订单修改
	 */
	public function anyOrderModify(){
	    $result = array();
		$input = Input::all();
		Log::info($input);
	    if(Input::has('order_id') && Input::has('express_company') && Input::has('invoice_no') && Input::has('nickName') && Input::has('tel')) {
	         
	        $order_id = Input::get('order_id');
	        $express_company = Input::get('express_company');
	        $invoice_no = Input::get('invoice_no');
			$nickName = Input::get('nickName');
			$tel = Input::get('tel');

	        $num = $this->orderModel->orderModify($order_id,$express_company,$invoice_no);
	        if( $num == 1) {
	            $result['msg'] = '更新成功';
	            $result['code'] = '1';
				$res = Sms::sendOutDelivery($tel,$nickName,$express_company,$invoice_no);
				$this->orderModel->addJnlOrderSendSms($order_id,$res,'SMS_2175771');
				if($res) {
					$res1 = Sms::sendTakeDelivery($tel);
					$this->orderModel->addJnlOrderSendSms($order_id,$res1,'SMS_2175771');
				}
	        }else {
	            $result['msg'] = '更新失败';
	        }
	    } else {
	        $result['msg'] = 'param check error';
	        Log::error('param check error:' + json_encode(Input::all()));
	    }
	    return Response::json($result);
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
