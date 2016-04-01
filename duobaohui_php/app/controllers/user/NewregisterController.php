<?php
/**
 * 用户注册
 *
 * @author         
 * @version        v1.0
 * @copyright      shinc
 */
namespace Laravel\Controller\User;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session 
use Illuminate\Support\Facades\Response;	//引入response

use Laravel\Model\NewUserModel;
use Laravel\Model\VerifyModel;

class NewregisterController extends ApiController {
	public function __construct() {
		if (Session::hasOldInput('id')) Session::reflash('id');
	}
	
	
	/**
	 * 发送短信接口
	 * @param string $tel 电话号码
	 * @return json
	 */
	public function anySendVerifyCode() {

		if (!Input::has('tel')) {
			return Response::json(array('code' => '-1','msg' => '无电话号码'));
		}

		$newUserModel = new NewUserModel();
		$verifyModel = new VerifyModel();

		if ($newUserModel->checkUser(Input::get('tel'))) return Response::json(array('code' => '20206','msg' => '手机号码已被使用'));
		$data = $verifyModel->sendVerifyCode(Input::get('tel'));
		return Response::json($data);
	}


	/**
	*	校验验证码是否正确
	*	@param  $code 验证码
	*	@param  $tel  手机号
	*	@return json
	*/
	public function anyCheckVerify(){

		if( !Input::has('code') || !Input::has('tel') ){
			return Response::json(array('code' => '10005', 'msg' => '参数错误'));
		}

		$code = Input::get('code');
		$tel = Input::get('tel');
		$user = new VerifyModel();
		$data = $user->checkVerifyCode( $tel , $code  );
		//判断验证码是否正确
		if( !$data ) {
			return Response::json(array('code' => '20208', 'msg' => '验证码错误'));

		}else{
			
			return Response::json(array('code' => '1', 'msg' => '验证码正确'));
		}

	}


	/**
	*	用户注册
	*	@param  $tel 
	*	@param  $password
	*/
	public function anyRegister() {

		if (!Input::has('tel') || !Input::has('password') ){
		 return Response::json(array('code' => '10005', 'msg' => '参数错误'));
		}
	
		$tel          = Input::get('tel');
		$password     = Input::get('password');
		
		$newUserModel = new NewUserModel();
		if ($newUserModel->checkUser($tel)) { 
			return Response::json(array('code' => '0', 'msg' => '电话号码已被注册'));
		}
		$data = $newUserModel->addUser($tel, md5($password) );
		if ($data) {
			Session::put('tel', $tel);
			return Response::json(array('code' => '1', 'msg' => '注册成功,请登录!') );
		} else return Response::json(array('code' => '0', 'msg' => '注册失败!'));
     }
     
}