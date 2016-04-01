<?php
/**
 * 用户个人中心配置信息相关接口操作 
 *
 * @author		xuguangjing@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */
namespace Laravel\Controller\Individual;	//定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Laravel\Model\UserConfigModel;			//引入model

use Illuminate\Support\Facades\Response;

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class UserConfigController extends ApiController {	
	public $response;
	private $_model;
	
	public function __construct(){
		parent::__construct();
		$this->_model = new UserConfigModel();
	}

	
	public function anyGetUserConfig(){

		if(!Input::has('user_id')){
			$this->response = $this->response(ApiController::FAILDCODE , '参数错误');
			return Response::json($this->response);
		}
		$shUserId = Input::get('user_id' , 0);
	
		$data = $this->_model->find(array('sh_user_id'=>$shUserId));

		$this->response = $this->response(ApiController::SUCCESSCODE , ApiController::SUCCESS , $data);
		return Response::json($this->response);

	}

	
	/**
	 *	更新用户配置
	 *	@param		key				需要更新的字段
	 *	@param		value			需要更新的值
	 */
	public function anyUpdateUserConfig(){

		if(!Input::has('user_id')){
			return Response::json($this->response('10005'));
		}
		if(!Input::has('key')){
			return Response::json($this->response('10005'));
		}
		$shUserId	= Input::get('user_id');	
		$key		= Input::get('key');	
		
		if(!in_array($key , $this->_userConfig())){
			return Response::json($this->response('10005'));
		}

		$value		= Input::get('value' , 0);	

		$updateData	= array($key=>$value); 

		$isUpdate	= $this->_model->update($shUserId , $updateData);
		
		if($isUpdate){
			//$this->response = $this->response(ApiController::SUCCESSCODE , '更新成功');
			return Response::json($this->response(ApiController::SUCCESSCODE , '更新成功'));
		}else{
			//$this->response = $this->response(ApiController::FAILDCODE , '更新失败');
			return Response::json($this->response(ApiController::FAILDCODE , '更新失败'));
		}
	}

	
	
	private function _userConfig(){
		return array(
			'is_push' ,					//是否开启推送
			'is_notify' ,				//是否开启消息通知
			'is_location' ,				//是否获取位置信息
			'is_nowifiopen' ,			//不是wifi环境下是否下载
			'is_anonymous'				//是否开启匿名评论
		);			
	}


}
