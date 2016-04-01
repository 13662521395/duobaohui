<?php
/**
 * 第三方登录相关接口操作 
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

use Laravel\Model\ThirdpartModel;			//引入model

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 

class ThirdpartController extends ApiController {
	
	public function __construct(){
		parent::__construct();
	}


	/**
	* 第三方登录
	*/
	public function anyThirdpartLogin(){
		if(!Input::has( 'key' ) || !Input::has('thirdpart') || !Input::has('cid')){
			return Response::json( $this->response( '10005' ) );
		}
		$user = new ThirdpartModel();
		$cid=Input::get('cid');
		$key=Input::get('key');
		$thirdpart=Input::get('thirdpart');
		$userInfo = $user->thirdpartLogin($key,$thirdpart,$cid);
		if(!$userInfo){
			$this->response = $this->response(0, '获取数据失败');
			return Response::json($this->response);
		}
		unset($userInfo->password);
		unset($userInfo->locked);
		//$userInfo = $user->getUserInfoByUserId($userId);
		$res		 = $this->response( '1' );
		$res['data'] = $userInfo;

		return Response::json($res);
	}
}