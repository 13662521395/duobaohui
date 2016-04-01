<?php
/**
 * 邮箱相关接口操作 
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
use Illuminate\Support\Facades\Mail;		//引入邮箱

use App\Libraries\Smtp;

use Laravel\Model\EmailModel;				//引入model

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class EmailController extends ApiController {

	public function __construct(){
		parent::__construct();
	}
	

	/**
	 * 邮箱注册
	 *
	 * @param string email		邮箱
	 * @param string nick_name	用户名
	 * @param string password	密码
	 * @return	json
	 */
	public function anyEmailRegister() {

        if( !Input::has('email') || !Input::has('password') || !Input::has( 'nick_name' ) ) {
            return Response::json( $this->response( '10005'));
        }

        $email = Input::get( 'email' );
        $password = Input::get( 'password' );
		$nick_name = Input::get( 'nick_name');
		$type	= Input::get( 'type' , 0 );

		$user = new EmailModel();
	 	$isUser = $user->checkUserEmail( $email );

        //判断用户邮箱是否注册过
		if( $type == 0 ) {

			if( $isUser ){
				return Response::json( $this->response( '10014' ) );
			}

		}elseif( $type == 1 ) {

			if( !$isUser ){
				return Response::json( $this->response( '400014' ) );
			}

		}else{

			return Response::json( $this->response( '200000' ) );
		}

        $data = $user->addUserEmail( $email, md5( $password), $nick_name );
        if( $data ) {
            Session::put( 'email', $email );
            	return Response::json( $this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS ) );
        	}else{
            	return Response::json( $this->response( ApiController::FAILDCODE, ApiController::FAILD ) );
		}
	}




}
