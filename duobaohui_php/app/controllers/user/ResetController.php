<?php
/**
 * 用户重置密码相关接口操作 
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
use App\Libraries\Sms;						//引入发送短信接口

use Laravel\Model\ResetModel;				//引入model
use Laravel\Model\LoginModel;			//引入model

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 

class ResetController extends ApiController {
	
	public function __construct(){
		parent::__construct();
	}

/********************************************用户名注册的修改密码********************************************************/
	/**
	* 用户名重置密码
	*
	* @param string $nick_name 用户名
	* @param string $password 新密码
	* @return json
	*/

	public function anyRegName() {
		if( !Input::has('password') || !Input::has('nick_name')) {
			return Response::json( $this->response( '10005' ) );
		}

		$password = Input::get( 'password' );
		$nick_name = Input::get('nick_name');
		
		$user = new ResetModel();
		//检测用户是否存在
		if($user->checkUserName( $nick_name )){
			$this->response = $this->response('10013');

			return Response::json($this->response);
		}
		//更新用户密码
		$res = $user->updatePwd( $nick_name, md5($password) );
		if( !$res ) {

			return Response::json( $this->response( '20205' ) );
		}else{

			return Response::json( $this->response( '1' ) );
		}

		
	}


/*********************************手机号注册修改密码**************************************************/
	
	/**
	* 手机号重置密码
	*
	* @param string $tel 手机号
	* @param string $password 新密码
	* @return json
	*/

	/**
	 * 验证码接口
	 *
	 */
	private function _getVerify( $tel ) {

		$randStr	= str_shuffle('1234567890');
		$rand		= substr($randStr,0,6);
		$content	= $tel."您的验证码是：". $rand .";该验证码在5分钟内有效";

		//有效时间5分钟
		$liveTime = time() + 5*60;

		$user = new ResetModel();
		//write db	
		$id = $user->writeVerify( $tel, $rand, $liveTime );

		if( !$id ) {

			return false;
		}

		//$res = Sms::sendShortMessage( $tel, $content );
		//if( 1 != $res['status'] ) {
		$res = Sms::sendRegisterCode( $tel, $rand);
		if( $res ) {
			
			return false;
		}

		return true;
	}


	/**
	 * 获取验证码-短信发送
	 *
	 */
	public function anyGetVerify() {

		if( !Input::has( 'tel' ) ) {
			
			return Response::json( $this->response( '10005' ) );
		}

		$tel = Input::get( 'tel' );
		
		$type = Input::get( 'type' , 0);

		$user = new ResetModel();

		$isUser = $user->checkUserTel( $tel );
		if($type == 0){

			//判断用户是否存在
			if( $isUser ) {

				return Response::json( $this->response( '10013' ) );
			}
			
		}elseif($type == 1){

			//判断用户是否注册过
			if( !$isUser ) {
				$this->response = $this->response(0, '没有该用户');
				return Response::json($this->response);
			}
		
		}else{

			return Response::json( $this->response( '10005' ) );
		
		}

		$isSendVerify = $this->_getVerify( $tel );

		if($isSendVerify === false){
			return Response::json( $this->response( '500001' ) );
		}
		
		return Response::json( $this->response( '1' ) );
	}


	/**
	* 手机号重置密码
	*
	* @param string $tel 用户名
	* @param string $code 验证码
	* @param string $password 新密码
	* @return json
	*/
	public function anyRegTel() {

        if( !Input::has('tel') || !Input::has('password') || !Input::has( 'code' ) ) {
            return Response::json( $this->response( '10005'));
        }

        $tel = Input::get( 'tel' );
        $password = Input::get( 'password' );
		$code   = Input::get( 'code' );
		$type	= Input::get( 'type' , 0 );
	 	$user = new ResetModel();

	 	 //判断用户是否存在
	 	$isUser = $user->checkUserTel( $tel );
		if( $type == 0 ) {

			if( $isUser ){
				return Response::json( $this->response( '10013' ) );
			
			}

		}elseif( $type == 1 ) {

			if( !$isUser ){
				return Response::json( $this->response( '400014' ) );
			}

		}else{

			return Response::json( $this->response( '200000' ) );
		}
		//判断验证码是否正确
		if( $user->checkVerify( $tel, $code ) ) {

			return Response::json( $this->response( '20208' ) );
		}

		$loginM = new LoginModel();
		$salt = $loginM->getUserSaltByTel($tel);
		$password = $loginM->encryptPassword($password,$salt);

		//更新密码
        $data = $user->updatePassword( $tel, $password );
        if( $data ) {
            Session::put( 'tel', $tel );
            	return Response::json( $this->response( '1' ) );
        	}else{
            	return Response::json( $this->response( '20205' ) );
		}

	}



	/**
	* 修改用户昵称
	*
	* @param string $nick_name 用户名
	* @return json
	*/

	public function anyRegNickname() {
		if( !Input::has('nick_name') ) {
			return Response::json( $this->response( '10005' ) );
		}

		$nick_name = Input::get( 'nick_name' );
		
		$user = new ResetModel();
		//检测用户是否存在
		if(Session::has("user.id") != true){
			$this->response = $this->response('10013');

			return Response::json($this->response);
		}
		//更新用户昵称
		$user_id =Session::get("user.id");
		$res = $user->updateNickname( $user_id, $nick_name );
		if( $res ) {

			return Response::json( $this->response( '1' ) );
		}else{

			return Response::json( $this->response( '0' ) );
		}
		
	}




	/**
	* 修改用户头像
	*
	* @param string $head_pic 用户头像
	* @return json
	*/

	public function anyHeadPic() {
		if( !Input::has('head_pic') ) {
			return Response::json( $this->response( '10005' ) );
		}

		$head_pic = Input::get( 'head_pic' );
		
		$user = new ResetModel();
		//检测用户是否存在
		if(Session::has("user.id") != true){
			$this->response = $this->response('10013');

			return Response::json($this->response);
		}
		//更新用户昵称
		$user_id =Session::get("user.id");
		$res = $user->updateHeadPic( $user_id, $head_pic );
		if( $res ) {

			return Response::json( $this->response( '1' ) );
		}else{

			return Response::json( $this->response( '0' ) );
		}
		
	}



/********************************************邮箱注册的修改密码********************************************************/
	/**
	* 邮箱重置密码
	*
	* @param string $email 用户名
	* @param string $password 新密码
	* @return json
	*/

	public function anyRegEmail() {
		if( !Input::has('password') || !Input::has('email')) {
			return Response::json( $this->response( '10005' ) );
		}

		$password = Input::get( 'password' );
		$email = Input::get('email');
		
		$user = new ResetModel();
		//检测用户是否存在
		if($user->checkUserEmail( $email )){
			$this->response = $this->response('10013');

			return Response::json($this->response);
		}
		//更新用户密码
		$res = $user->updatePsd( $email, md5($password) );
		if( $res ) {

			return Response::json( $this->response( '1' ) );
		}else{

			return Response::json( $this->response( '20205' ) );
		}

		
	}



	
}
