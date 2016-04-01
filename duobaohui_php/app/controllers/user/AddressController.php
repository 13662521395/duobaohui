<?php
/**
 * 设置收货地区操作
 *
 * @author		liangfeng@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\User;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入参数类
use Illuminate\Support\Facades\Response;	//引入response


use Laravel\Model\AddressModel;				//引入model
use Laravel\Model\NewloginModel;				

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class AddressController extends ApiController {
	public $response;	//定义public公开成员变量
	private $_model;	//定义private私有成员变量，通常$_ 开头

	public function __construct(){
		parent::__construct();
		$this->_model = new AddressModel();
	}
	

	/**
	 * 获取中国地区表
	 *
	 * @return	json
	 */
	public function anyArea() {
		$data=$this->_model->getArea();
		
		if($data){

			$this->response = $this->response(1, '获取成功',$data);
		}else{

			$this->response = $this->response(0,'获取失败');
		}

		return Response::json($this->response);

	}


	/**
	 * 添加收货地址
	 * @param user_id   用户id
	 * @param name      收货人姓名
	 * @param mobile    手机号码
	 * @param area      地区
	 * @param address   详细地址
	 * @return	json
	 */
	public function anyAddAddress() {
		if( !Input::has('name') || !Input::has( 'mobile' ) || !Input::has( 'area' ) || !Input::has( 'address' )) {
            return Response::json( $this->response( '10005'));
        }

		if(!Session::has('user')){
            return Response::json( $this->response( '10018'));
		}

        $user_id = Session::get( 'user.user_id' );
        $name 	 = Input::get( 'name' );
		$mobile  = Input::get( 'mobile' );
		$area	 = Input::get( 'area' );
		$address = Input::get( 'address' );

		//根据用户id获取用户信息
		$user = new NewloginModel();
	 	$isUser = $user->getUserInfoByUserId( $user_id );

	 	if(!$isUser){

	 		return Response::json( $this->response( '0' )  );
	 	}else{

	 		$data=$this->_model->addAddress($isUser,$name,$mobile,$area,$address);

	 		if( $data ) {

				$this->response = $this->response(1, '添加成功',$data);
			}else{

				$this->response = $this->response(0,'添加失败');
			}

			return Response::json($this->response);
	 	}		

	}



	/**
	 * 根据用户id获取收货地址列表
	 * @param user_id   用户id
	 * @return	json
	 */
	public function anyAddressList() {
		if(!Session::has('user')){
            return Response::json( $this->response( '10018'));
		}

        $userId = Session::get( 'user.user_id' );

 		$data=$this->_model->getAddressList($userId);
 		if( $data ) {

			$this->response = $this->response(1, '获取成功',$data);
		}else{

			$this->response = $this->response(0,'获取失败');
		}

		return Response::json($this->response);	

	}



	/**
	 * 根据收货地址id修改收货信息
	 * @param address_id    收货地址id
	 * @param name      	收货人姓名
	 * @param mobile    	手机号码
	 * @param area      	地区
	 * @param address   	详细地址
	 * @return	json
	 */
	public function anyAddressEdit() {
		if(!Input::has('address_id') || !Input::has('name') || !Input::has( 'mobile' ) || !Input::has( 'area' ) || !Input::has( 'address' ) || !Input::has( 'isDefault' ) ) {
            return Response::json( $this->response( '10005'));
        }

		if(!Session::has('user')){
            return Response::json( $this->response( '10018'));
		}

        $user_id = Session::get( 'user.user_id' );
        $address_id  = Input::get( 'address_id' );
        $name 	     = Input::get( 'name' );
		$mobile      = Input::get( 'mobile' );
		$area	     = Input::get( 'area' );
		$address     = Input::get( 'address' );
		$isDefault   = Input::get( 'isDefault' );


 		$data=$this->_model->editAddressByAddressId($user_id,$address_id,$name,$mobile,$area,$address,$isDefault);

 		if( $data ) {

			$this->response = $this->response(1, '修改成功',$data);
		}else{

			$this->response = $this->response(0,'修改失败');
		}

		return Response::json($this->response);	

	}




	/**
	 * 根据收货地址id删除收货信息
	 * @param address_id   收货地址id
	 * @return	json
	 */
	public function anyAddressDelete() {
		if( !Input::has('address_id') ) {
            return Response::json( $this->response( '10005'));
        }
		if(!Session::has('user')){
            return Response::json( $this->response( '10018'));
		}

        $user_id = Session::get( 'user.user_id' );
        $address_id = Input::get( 'address_id' );

 		$data=$this->_model->DeleteAddressByAddressId($address_id);

 		if( $data ) {

			$this->response = $this->response(1, '删除成功',$data);
		}else{

			$this->response = $this->response(0,'删除失败');
		}

		return Response::json($this->response);	

	}



}
