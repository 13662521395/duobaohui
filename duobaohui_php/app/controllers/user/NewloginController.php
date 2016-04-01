<?php
/**
 * 用户登录相关接口操作 
 *
 * @author		xuguangjing@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\User;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session 
use Illuminate\Support\Facades\Response;	//引入response
//use App\Libraries\Sms;				//引入发送短信接口

use Laravel\Model\NewloginModel;			//引入model

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class NewloginController extends ApiController {
	
	public function __construct(){
		parent::__construct();
		if (Session::hasOldInput('id')) Session::reflash('id');
	}

/**********************************************手机号登录*************************************************************/
	/** 
	 * 用户手机号密码登录
	 * 
	 * @param string $tel 用户手机号
	 * @param string $password 密码
	 * @return json
	 */
	public function anyLoginTel() {

		if( !Input::has( 'tel' ) ||  !Input::has( 'password' ) ) {

			return Response::json( $this->response( '10005' ) );
		}

		$tel = Input::get( 'tel' );
		$password = Input::get( 'password' );
		
		$user = new NewloginModel();
		//判断账户是否注册存在
		$check = $user->checkReg( $tel );
		if( $check ) {
			return Response::json( $this->response( '10013' ) );
		}
		//检测用户是否登录
		$check = $user->checkLogin( $tel );
		//setcookie('login_user_name', $tel, time() + DEFAULT_COOKIE_EXPIRE, DEFAULT_COOKIE_PATH, DEFAULT_COOKIE_DOMAIN);
		if( Session::has('tel')) {
			return Response::json( $this->response( '10016' ) );
		}

		//检测用户手机号和密码
		$check = $user->checkLoginTel( $tel, $password );	

		//$user->clearSession();

		if( !$check ) {

			return Response::json( $this->response( '10005' )  );
		}

		//获取用户信息
		$userInfo = $user->getUserInfoByMobile( $tel );

		if( empty( $userInfo ) ) {

			return Response::json( $this->response( '20202' )  );
		}
		
		$userInfo = $userInfo[0];
		//判断帐号是否锁定
		if( 1 == $userInfo->locked ) {

			return Response::json( $this->response( '20204' )  );
		}

		//记录登录次数
		$num  = $user->addLoginCount();

		//登录次数超过5此拒绝锁定账户
		if( $num > 5  ) {
			//锁定帐号
			$user->lockedUser( $userInfo->id );

			return Response::json( $this->response( '20203' )  );
		}

		//判断用户密码是否正确
		if( $userInfo->password  != md5( $password ) ) {
			//echo $userInfo->password;exit;
			return Response::json( $this->response( '20202' )  );
		}
		unset($userInfo->password);
		unset($userInfo->locked);
		//清理用户session中登录次数
		// $res['key'] = $user->saveSession($userInfo->id);
		$user->clearLoginCount();

		//记录日志
		$user->writeUserLog($userInfo->id, 'login', 'success');
		$res		 = $this->response( '1' );
		$res['data'] = $userInfo;

		//储存session
		Session::put( 'tel', $tel );
		return Response::json($res);
	}


/***************************************邮箱登录************************************************************/
	/** 
	 * 邮箱密码登录
	 * 
	 * @param string $email 用户邮箱
	 * @param string $password 密码
	 * @return json
	 */
	public function anyLoginEmail() {
		
		if( !Input::has( 'email' ) ||  !Input::has( 'password' ) ) {

			return Response::json( $this->response( '10005' ) );
		}

		$email = Input::get( 'email' );
		$password = Input::get( 'password' );
		
		$user = new NewloginModel();

		//检测用户是否登录
		$check = $user->checkLogin( $email );
		if( Session::has('email')) {
			return Response::json( $this->response( '10016' ) );
		}

		//检测用户名和密码
		$check = $user->checkLoginEmail( $email, $password );	
		//$user->clearSession();

		if( !$check ) {

			return Response::json( $this->response( '10005' )  );
		}

		//获取用户信息
		$userInfo = $user->getUserInfoByEmail( $email );

		if( empty( $userInfo ) ) {

			return Response::json( $this->response( '20202' )  );
		}
		
		$userInfo = $userInfo[0];
		//判断帐号是否锁定
		if( 1 == $userInfo->locked ) {

			return Response::json( $this->response( '20204' )  );
		}

		//记录登录次数
		$num  = $user->addLoginCount();

		//登录次数超过5此拒绝锁定账户
		if( $num > 5  ) {
			//锁定帐号
			$user->lockedUser( $userInfo->id );

			return Response::json( $this->response( '20203' )  );
		}

		//判断用户密码是否正确
		if( $userInfo->password  != md5( $password ) ) {
			//echo $userInfo->password;exit;
			return Response::json( $this->response( '20202' )  );
		}
		unset($userInfo->password);
		unset($userInfo->locked);
		//清理用户session中登录次数
		//$res['key'] = $user->saveSession($userInfo->id);
		$user->clearLoginCount();

		//记录日志
		$user->writeUserLog($userInfo->id, 'login', 'success');
	
		$res		 = $this->response( '1' );
		$res['data'] = $userInfo;
		//存储session
		Session::put( 'email', $email );
		return Response::json($res);
	}



/***************************************退出登录************************************************************/	
	/**
	 * 退出登录
	 *
	 */
	public function  anyLogout() {

		if( !Input::has('uid') ) {
			
			return Response::json( $this->response( '10005' ) );
		}

		$userId = Input::get( 'uid' );

		$user = new NewloginModel();
		//clear session
		$user->clearSession();
		//记录日志
		$user->writeUserLog($userId, 'logout', 'success');

		return Response::json( $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS  ) );
	}


}


