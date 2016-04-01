<?php 
/**
 * 模块化--发送短息
 *
 * @author			马建超@shinc.net
 * @version			v1.0
 * @copyright		shinc
 */
namespace Laravel\Controller\User;			//定义命名空间

use ApiController;							//x引入接口公共父类，用于继承
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Response;	//引入response
use Illuminate\Support\Facades\Session;		//引入session 
use App\Libraries\Sms;						//引入发送短信接口

use Laravel\Model\UserSmsModel;		//引入model

/**
 * controller的写法: 首字母大写，于文件名一致，继承的父类需引入
 */
class UserSmsController extends ApiController {

	//public $response;
	private $_model;
	
	public function __construct(){
		parent::__construct();
		$this->_model = new UserSMsModel();
	}

/**
 * 发送信息
 *
 * @param string   user_id	 发送消息用户ID
 * @param string   user_name 发送消息用户名称
 * @param string   content   接收的信息
 * @param string   u_id  	 接收送消息的用户ID
 * @param string   u_name	 接收信息的用命名称
 * @return	json
 */

	public function  anyAddSms( ) {
		
		if( !Input::has('user_id') || !Input::has('user_name') || !Input::has('content') || !Input::has('u_id') || !Input::has('u_name') ){
			return Response::json( $this->response( '10005'));
		}

		$user_id     = Input::get('user_id');
		$user_name     = Input::get('user_name');
		$u_id     = Input::get('u_id');
		$u_name     = Input::get('u_name');
		$content = Input::get('content');
	
		//查询是否有发送过消息
		$sms_id   = $this->_model->addSms($user_id , $u_id );
	
		//发送消息
		$sms_info = $this->_model->addSmsInfo($sms_id , $user_name , $u_name , $content);
		if( !$sms_info ) {
			//失败
			return Response::json( $this->response( '0' ) );
		}
			 //成功
			return Response::json( $this->response( '1' ));
	}
/**
 *
 *  获取信息列表 或者 总条数
 *
 * @param string   user_id	接收信息的用户ID
 * @return	json
 */
	public function  anyGetSmsList( ) {
		

		if( !Input::has('u_id') || !Input::has('parameter')){
			return Response::json( $this->response( '10005'));
		}

		$u_id     = Input::get('u_id');
		$parameter        = Input::get('parameter');

		$data = $this->_model->getList( $u_id , $parameter );

		if( !$data ) {
			
			return Response::json( $this->response( '0' ) );
		}

			return Response::json( $this->response( '1' , $data)  );
	}


/**
 * 获取消息详情
 *
 * @param string   user_id	用户ID
 * @param string   u_id  发送消息信息的用户ID
 * @return	json
 */
	public function  anyGetSms( ) {
		

		if(!Input::has('id')){
			return Response::json( $this->response( '10005'));
		}

		$id      = Input::get('id');

			
		//获取消息详情
		$data = $this->_model->getContent( $id );
		if(!$data){
			return Response::json( $this->response( '0' , '没有此消息 ' ) );
		}
		//更改已查看的消息的状态
		
		$status  = $this->_model->getStatus( $id );
		
		if( !$data ) {
			
			return Response::json( $this->response( '0' ) );
		}

			return Response::json( $this->response( '1' , $data)  );
	}

/**
 * 删除信息
 *
 * @param string   id  接收的信息ID  
 * @return	json   
 */
	public function  anyDelSms( ) {

		if(  !Input::has('id')){
			return Response::json( $this->response( '10005'));
		}
		$id     = Input::get('id');

		$data = $this->_model->delSms( $id );

		if( !$data ) {
			
			return Response::json( $this->response( '0' ) );
		}

			return Response::json( $this->response( '1', '已删除'  ) );
	}


}

