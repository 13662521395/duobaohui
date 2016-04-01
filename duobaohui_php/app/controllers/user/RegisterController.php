<?php 
/**
 * 模块化--用户注册
 *
 * @author			xuguangjing@shinc.net
 * @version			v1.0
 * @copyright		shinc
 */
namespace Laravel\Controller\User;			//定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Response;	//引入response
use Illuminate\Support\Facades\Session;		//引入session 
use App\Libraries\Sms;						//引入发送短信接口

use Laravel\Model\RegisterModel;		//引入model
use Laravel\Model\LoginModel;			//引入model

/**
 * controller的写法: 首字母大写，于文件名一致，继承的父类需引入
 */
class RegisterController extends ApiController {

	public function __construct(){

		parent::__construct();
	}

	
/**
 * 用户名密码注册接口
 * 
 * @param string nice_name	用户名
 * @param string password	密码
 * @return		json 
*/
	public function anyNameRegister() {

		if( !Input::has('nick_name') || !Input::has('password') ) {

			return Response::json( $this->response( '10005'));
		}

		$nick_name = Input::get( 'nick_name' );
		$password = Input::get( 'password' );

		$user = new RegisterModel(); 
		//判断用户名是否注册过
		if( $user->checkUserName( $nick_name ) ) {

				return Response::json( $this->response( '10014') );
		}

		$data = $user->addUserName( $nick_name,md5( $password) );

		if( $data ) {
			// Session::put( 'nick_name', $nick_name );

			return Response::json( $this->response( '1' ) );
		}else{

			return Response::json( $this->response( '0' ) );
		}

	}


/***************************************************************************************/
/**
 * 手机号注册
 *
 * @param string tel	手机号
 * @param string password	密码
 * @return	json
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

		$user = new RegisterModel();
		//write db	
		$id = $user->writeVerify( $tel, $rand, $liveTime );

		if( !$id ) {

			return false;
		}

		//$res = Sms::sendShortMessage( $tel, $content );
		$res = Sms::sendRegisterCode( $tel, $rand);

		return $res;
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

		$user = new RegisterModel();

		$isUser = $user->checkUserTel( $tel );
		if($type == 0){

			//判断用户是否注册过
			if( $isUser ) {

				return Response::json( $this->response( '20206' ) );
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
	* 新增手机号注册
	*/
	public function anyTelRegister() {

        if( !Input::has('tel') || !Input::has('password') || !Input::has( 'nick_name' ) || !Input::has( 'code' ) ) {
            return Response::json( $this->response( '10005'));
        }

        $tel = Input::get( 'tel' );
        $password = Input::get( 'password' );
		$nick_name = Input::get( 'nick_name');

		$code   = Input::get( 'code' );
		$type	= Input::get( 'type' , 0 );


	 	$user = new RegisterModel();
	 	$isUser = $user->checkUserTel( $tel );

        //判断用户是否注册过
		if( $type == 0 ) {

			if( $isUser ){
				return Response::json( $this->response( '20206' ) );
			
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

		$salt = str_random(6);
		$loginM = new LoginModel();
		$password = $loginM->encryptPassword($password,$salt);

        $data = $user->addUserTel( $tel, $password, $nick_name, $salt);
        if( $data ) {

            Session::put( 'tel', $tel );		//进入登录状态
            	return Response::json( $this->response( '1' ) );
        	}else{
            	return Response::json( $this->response( '0' ) );
		}
	}




	/**
	 * 异常信息提示接口
	 *
	 */
	public function anySendException( ) {
		if( !Input::has( 'tel' ) || !Input::has('code')) {

			return Response::json( $this->response( '10005' ) );
		}
		$tel  = Input::get( 'tel' );
		$code = Input::get( 'code' );

		$res = Sms::sendExceptionMsg( $tel, $code);
//		return false;
		if($res){
			return Response::json( $this->response( '1' ) );
		}else{
			return Response::json( $this->response( '0' ) );
		}
	}



}

