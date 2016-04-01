<?php 
/**
 * 模块化--发送短息
 *
 * @author			马建超@shinc.net
 * @version			v1.0
 * @copyright		shinc
 */
namespace Laravel\Controller\Sms;			//定义命名空间

use ApiController;							//x引入接口公共父类，用于继承
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Response;	//引入response
use Illuminate\Support\Facades\Session;		//引入session 
use App\Libraries\Sms;						//引入发送短信接口

use Laravel\Model\RegisterModel;		//引入model

/**
 * controller的写法: 首字母大写，于文件名一致，继承的父类需引入
 */
class SmSController extends ApiController {

	public function __construct(){

		parent::__construct();
	}

	/**
	 * 发送短信
	 *
	 * @param string tel	手机号
	 * @param string content
	 * @return	json
	 */
	public function  anySms( ) {
		if( !Input::has('tel') && !Input::has('content')){
			return Response::json( $this->response( '10005'));
		}
		$tel     = Input::get('tel');
		$content = Input::get('content');

		//$res = Sms::sendShortMessage( $tel, $content );

		//if( 1 != $res['status'] ) {
		$res = Sms::sendRegisterCode( $tel, $content);
		if( $res ) {
			
			return Response::json( $this->response( '0' ) );
		}

			return Response::json( $this->response( '1' ) );
	}





}

