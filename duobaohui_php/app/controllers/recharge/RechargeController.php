<?php
/**
 * 
 *
 * @author		liangfeng@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Recharge;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session 
use Illuminate\Support\Facades\Response;	//引入response

use Laravel\Model\RechargeModel;
use Laravel\Service\JnlService;                //引入model

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 

class RechargeController extends ApiController {
	
	public function __construct(){
		parent::__construct();
	}

	/**
	 * 准备充值，并生成订单
     */
	public function anyRechargeConfirm() {
		$amount = Input::get('amount');
		$recharge_channel = Input::get('recharge_channel');
		$jnlService = new JnlService();
		$result = $jnlService->createJnlTransRecharge($amount,$recharge_channel);
		if(isset($result['jnl_no'])) {
			$this->response = $this->response('1',null,$result);
		} else {
			$this->response = $this->response($result['code'],$result['msg']);
		}
		return Response::json($this->response);

	}

	/**
	* 根据用户id获取充值记录
	* @param user.id(session)	array    用户id
	*
	*/
	public function anyRechargeRecord() {
		
		$recharge = new RechargeModel();

		//debug(Input::root());
		//检测用户是否存在
		if(Session::has("user.id") != true){
			$this->response = $this->response('10013');

			return Response::json($this->response);
		}
		$user_id =Session::get("user.id");

		//获取充值记录
		$pageinfo = $this->pageinfo();
		$res = $recharge->getRecharge( $user_id , $pageinfo->offset , $pageinfo->length);
		if( $res ) {

			$this->response = $this->response(1, '获取成功',$res);
		}else{

			$this->response = $this->response(0, '获取失败');
		}

		return Response::json($this->response);	
		
	}


	//分页
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


	/**
	* 未付款下订单
	* @param user.id(session)	array    用户id
	* @param pay_type 			int 	 是否付款 1为已付款  0为未付款
	* @param total_fee 			float 	 充值金额 	 
	*/
	public function anyOrderRecharge() {
		
		if(Session::has("user.id") != true){
        	return Response::json( $this->response( '10013'));
		}

		if( !Input::has('pay_type') || !Input::has('total_fee') ) {
            return Response::json( $this->response( '10005'));
        }
        
		$user_id =Session::get("user.id");
        $pay_type = Input::get('pay_type');
        $total_fee= Input::get('total_fee');
 		
		
        $data = array(
	        	'sh_user_id' 	=> $user_id,
	        	'payment_type'	=> $pay_type,
	        	'total_fee'		=> $total_fee,
	        	'create_time'	=> date('Y-m-d H:i:s',time()),
	        	'payment_status'=> 0,
        	);

		$recharge = new RechargeModel();
		
		
		//获取充值记录
		$res = $recharge->orderRecharge( $data );
		if( $res ) {

			$this->response = $this->response(1, '获取成功',$res);
		}else{

			$this->response = $this->response(0, '获取失败');
		}

		return Response::json($this->response);	
		
	}

	/**
	* 获取用户余额
	* @param user.id(session)	array    用户id	 
	*/
	public function anyGetMoney() {
		
		if(Session::has("user.id") != true){
        	return Response::json( $this->response( '10013'));
		}
        
		$user_id =Session::get("user.id");

		$recharge = new RechargeModel();
		
		
		//获取充值记录
		$res = $recharge->getUserMoney( $user_id );
		if( $res ) {

			$this->response = $this->response(1, '获取成功',$res);
		}else{

			$this->response = $this->response(0, '获取失败');
		}

		return Response::json($this->response);	
		
	}



	/**
	* 统计用户充值金额记录总数
	* @param user.id(session)	array    用户id	 
	*/
	public function anyCountUserMoney() {
		
		if(Session::has("user.id") != true){
        	return Response::json( $this->response( '10013'));
		}
		$userId =Session::get("user.id");

		$recharge = new RechargeModel();
		$res = $recharge->CountForUserMoney( $userId );

		if( $res ) {
			$this->response = $this->response(1, '获取用户充值记录成功',$res);
		}else{
			$this->response = $this->response(0, '获取失败');
		}

		return Response::json($this->response);	
		
	}



}