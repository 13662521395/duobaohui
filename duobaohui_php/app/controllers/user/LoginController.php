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
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session 
use Illuminate\Support\Facades\Response;	//引入response
use Illuminate\Support\Facades\Request;	//引入response
//use App\Libraries\Sms;				//引入发送短信接口

use Laravel\Model\IpFactoryModel;
use Laravel\Model\LoginModel;			//引入model
use Laravel\Model\RegisterModel;			//引入model
use Illuminate\Support\Facades\Config;
use App\Libraries\Sms;
use Laravel\Model\UserInfoModel;
use Laravel\Service\UserService;
use Symfony\Component\Security\Core\User\User;
use Laravel\Service\RedpacketService;

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class LoginController extends ApiController {
	
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
		if( !Input::has( 'tel' ) ||  !Input::has( 'password' ) || !Input::has( 'key' ) || !Input::has( 'form_token' ) ) {

			return Response::json( $this->response( '10005' ) );
		}


		$tel = Input::get( 'tel' );
		$password = Input::get( 'password' );
		$token = Input::get( 'form_token' );
		$key = Input::get( 'key' );
		
		$user = new LoginModel();
		$loginM = new LoginModel();
		$registerM = new RegisterModel();

		$registerM->redPacketToBalance($tel);
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

		//登录次数超过6此拒绝锁定账户
		if( $num > 6  ) {
			//锁定帐号
			$user->lockedUser( $userInfo->id );

			return Response::json( $this->response( '20203' )  );
		}

		$userPassword = $loginM->encryptPassword($password,$userInfo->salt);

		//判断用户密码是否正确
		if( $userInfo->password  != $userPassword ) {
			//echo $userInfo->password;exit;
			return Response::json( $this->response( '20202' )  );
		}

		$userKey = $loginM->encryptKey($password, $token);
		if($key!=$userKey){
			//return Response::json( $this->response( '10020' ) );
		}

		unset($userInfo->password);
		unset($userInfo->salt);
		unset($userInfo->locked);
		//清理用户session中登录次数
		$user->clearLoginCount();

		//记录日志
		$user->writeUserLog($userInfo->id, 'login', 'success');
		$res		 = $this->response( '1' );

		//对象转数组
		// function objectToArray($userInfo){
		//     $userInfo=(array)$userInfo;
		//     foreach($userInfo as $k=>$v){
		//         if( gettype($v)=='resource' ) return;
		//         if( gettype($v)=='object' || gettype($v)=='array' )
		//             $userInfo[$k]=(array)objectToArray($v);
		//     }
		//     return $userInfo;
		// }
		// $res['data'] = [objectToArray($userInfo)];
		$res['data'] = $userInfo;
		//储存session
		$user_session = array(
			'id' 		=> $userInfo->id,
			'user_id' 	=> $userInfo->id,
			'tel'		=> $userInfo->tel,
			'nick_name'	=> $userInfo->nick_name
		);

		Session::put( 'user', $user_session );
		$userInfo->session_id = Session::getId();

		$this->dealDummyUser($userInfo);
		// var_dump(Session::has("user.tel"));die;		//检测session数组是否存在
		return Response::json($res);
	}


/***********************************用户名登录********************************************************/
	/** 
	 * 用户名密码登录
	 * 
	 * @param string $nick_name 用户名
	 * @param string $password 密码
	 * @return json
	 */
	public function anyLoginName() {
		
		if( !Input::has( 'nick_name' ) ||  !Input::has( 'password' ) ) {

			return Response::json( $this->response( '10005' ) );
		}

		$nick_name = Input::get( 'nick_name' );
		$password = Input::get( 'password' );
	
		$user = new LoginModel();

		//检测用户是否登录
		$check = $user->checkLogin( $nick_name );
		if( Session::has('nick_name')) {
			return Response::json( $this->response( '10016' ) );
		}

		//检测用户名和密码
		$check = $user->checkLoginName( $nick_name, $password );	

		//$user->clearSession();

		if( !$check ) {

			return Response::json( $this->response( '10005' )  );
		}

		//获取用户信息
		$userInfo = $user->getUserInfoByName( $nick_name );

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
		//储存session
		Session::put( 'nick_name', $nick_name );
		$res		 = $this->response( '1' );
		$res['data'] = $userInfo;
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
		
		$user = new LoginModel();

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
	 * 刷单程序退出登录
	 *
	 */
	public function  anyShuadanLogout() {

		if( !Input::has('uid') ) {

			return Response::json( $this->response( '10005' ) );
		}

		$userId = Input::get( 'uid' );

		$userInfo = Session::get('user');

		if(!empty($userInfo) && $userId == $userInfo['id']){
			Session::flush();
			Session::migrate(true);
		}else{
			return Response::json( $this->response( '10017' ) );
		}
		return Response::json( $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS  ) );
	}

	/**
	 * 退出登录
	 *
	 */
	public function  anyLogout() {
		Session::flush();
		Session::migrate(true);

		return Response::json( $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS  ) );
	}
	
	public function anySendLoginSms() {
		if (!Input::has('tel')) return Response::json($this->response('-1', '缺少参数'));
		$tel = Input::get('tel');
		$loginModel = new LoginModel();
		$code = $loginModel->setSmsCode($tel);
		if ($code) {
			if(Config::get('app.debug')) {
				return Response::json($this->response('1', '发送成功'));
			} else {
				if (Sms::sendLoginCode($tel, $code)) return Response::json($this->response('1', '发送成功'));
				else return Response::json($this->response('0', '发送失败'));
			}
		} else {
			return Response::json($this->response('-2', '发送失败'));
		}
	}

	public function anyLoginSms() {
		if (!Input::has('tel') || !Input::has('code')) return Response::json(array('code' => '-1', 'msg' => '缺少参数'));
//		$os_type = Input::has('os_type')?Input::get('os_type'):1; //1:android 2:ios

		//1:android 2:ios
		if(Input::has('os_type')){
			$os_type=Input::get('os_type');
		}else{
			if(Input::has('platform')){
				$os_type=Input::get('platform')=="iPhone"?2:1;
			}else{
				$os_type=1;
			}
		}

		Log::info("==============>os_type:".$os_type);
		$tel = Input::get('tel');
		$code  = Input::get('code');
		$loginModel = new LoginModel();
		$registerM = new RegisterModel();

		$userInfo = $loginModel->getUserInfoByMobile($tel);
		if(empty($userInfo)) return Response::json($this->response('-4', '手机号或验证码错误'));
		$userInfo = $userInfo[0];
		//判断帐号是否锁定
		if(1 == $userInfo->locked) return Response::json($this->response('20204'));
		//记录登录次数
		$num  = $loginModel->addLoginCount();
		//登录次数超过6此拒绝锁定账户
		if($num > 6) {
			$loginModel->lockedUser($userInfo->id);//锁定帐号
			return Response::json($this->response('20203'));
		}
		if(Config::get('app.debug')) {
			$data = '1';
		} else {
			$data = $loginModel->checkSmsCode($tel, $code, $os_type);
		}
		$msg = '';
		switch ($data) {
			case '-1':  $msg = '手机号校验错误，请重新获取验证码'; break;
			case '-2':  $msg = '验证码失效，请重新获取验证码'; break;
			case '-3':  $msg = '手机号或验证码错误'; break;
		}
		if (!empty($msg)) return Response::json($this->response($data, $msg, array()));

		//清理用户session中登录次数
		$loginModel->clearLoginCount();
		//记录日志
		$loginModel->writeUserLog($userInfo->id, 'login', 'success');
		$userInfo->money = intval($userInfo->money);
		$userInfo->is_real = intval($userInfo->is_real);

		unset($userInfo->password);
		unset($userInfo->salt);
		unset($userInfo->locked);
		$userInfo->tel = substr_replace($userInfo->tel,'****',3,4);
		$res	 = $this->response('1');
		$res['data'] = $userInfo;

		//存储session
		$user_session = array(
			'id' 		=> $userInfo->id,
			'user_id' 	=> $userInfo->id,
			'tel'		=> $userInfo->tel,
			'nick_name'	=> $userInfo->nick_name,
			'ip'	    => $userInfo->ip,
			'ip_address'=> $userInfo->ip_address,
			'is_real'	=> $userInfo->is_real
		);
		if($userInfo->is_real){
			Config::set('session.lifetime', 43200);
		}

		Session::put('user', $user_session);

		//记录session_id 作单点登录验证
		$loginModel->updateSessionId( $userInfo->id, Session::getId() );

		$red_res = $registerM->redPacketToBalance($tel);
		if($red_res) {
			$userInfo = $loginModel->getUserInfoByMobile($tel);
			$res['data'] = $userInfo[0];
		}
		return Response::json($res);
	}

	public function getTest(){
//		$code = 1234;
		//Sms::sendRegisterCode( 15301113728, $code);
		$tel = Input::get('tel');
		$registerM = new RegisterModel();
		$registerM->redPacketToBalance($tel);
	}

	/**
	 * 处理假用户IP
	 */
	public function dealDummyUser($userInfo) {
		if(empty($userInfo) || $userInfo->is_real != '0') {
			return ;
		}
		if(empty($userInfo->ip_address) && empty($userInfo->ip)) {
			$ipf = new IpFactoryModel();
			$ip = $ipf->getRandomIp();
			$addr = '';
			if($ip->province == $ip->city) {
				$addr = $addr . $ip->province . ' ';
			} else {
				if(!empty($ip->province)) {
					$addr = $addr . $ip->province . ' ';
				}
				if(!empty($ip->city)) {
					$addr = $addr . $ip->city . ' ';
				}
			}

			if(!empty($ip->county)) {
				$addr = $addr . $ip->county;
			}
			$param = [
				'sh_user_id' => $userInfo->id,
				'ip' => $ip->ip,
				'ip_address' => $addr
			];
			$um = new UserInfoModel();

			$uf = $um->findByUserId($userInfo->id);
			if(empty($uf)) {
				$um->add($param);
			} else {
				$um->update($uf->id,$param);
			}
			$tosession = [
				'is_real' => '0',
				'ip' => $ip->ip,
				'ip_address' => $addr
			];
			UserService::addParamToSessionUser($tosession);
			return $ip;
		} else {
			$tosession = [
				'is_real' => '0',
				'ip' => $userInfo->ip,
				'ip_address' => $userInfo->ip_address
			];
			UserService::addParamToSessionUser($tosession);
		}
	}


	/*
	 * 发送短信
	 */
	public function anySendSms(){
		if(!Input::has('tel') || !Input::has('code')){
			return Response::json($this->response('0', '请填写手机号和验证码'));		
		}

		if(Request::getClientIp()!='182.92.189.177'){
			return Response::json($this->response('0', '服务器无权发送'));		
		}

		$tel	= Input::get('tel');
		$code	= Input::get('code');
		$rs = Sms::sendLoginCode($tel, $code);
			
		if($rs){
			return Response::json($this->response('1', '发送成功'));
		}else{
			return Response::json($this->response('0', '发送失败，请重试'));
		}
	}
}


