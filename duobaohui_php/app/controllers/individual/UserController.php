<?php
/**
 * 用户个人中心相关接口操作 
 *
 * @author		xuguangjing@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */
namespace Laravel\Controller\Individual;	//定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Laravel\Model\UserModel;				//引用model

use Illuminate\Support\Facades\Response;	//引入response方法
use App\Libraries\Qiniu\Auth;				//引入七牛

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class UserController extends ApiController {	
	public $response;
	private $_model;
	
	public function __construct(){
		parent::__construct();
		$this->_model = new UserModel();
	}
	
	
	/**
	* 获取用户信息
	* @param  string  user_id 用户id
	* @return json
	*/
	public function anyGetUserInfo(){
		if(!Input::has('user_id')){
			return Response::json( $this->response( '10005' ) );
		}
		$userId = Input::get('user_id');
		//判断用户是否存在
		if(!$this->_model->checkUserId( $userId ) ) {

				return Response::json( $this->response( '10013') );
		}

		$this->response = $this->response(ApiController::SUCCESSCODE , '获取成功',$this->_model->getUserInfo($userId));
		return Response::json($this->response);
		

	}


	/**
	* 修改用户个人信息
	* @param string  user_id 用户id 必须
	* @param string  $real_name 用户真名   可选
	* @param string  $nick_name 用户名    可选
	* @param string  $tel       用户手机号 可选
	* @return json
	*/
	public function anyUpdateUserInfo(){
		if(!Input::has('user_id') ){
			
			return Response::json($this->response('10005'));
		}

		$shUserId		= Input::get('user_id');
		$updateDate		= array('user'=>array(),'user_info'=>array(),'user_config'=>array());
		/*可选参数更改*/
		//更改用户名
		if(Input::has('realName')){
			$updateDate['user']['real_name']	= Input::get('realName');
		}
		//更改用户网名
		if(Input::has('nickName')){
			$updateDate['user']['nick_name']	= Input::get('nickName');
		}
		//更改用户电话
		if(Input::has('tel')){
			$updateDate['user']['tel']			= Input::get('tel');
		}
		//更改用户邮箱
		if(Input::has('email')){
			$updateDate['user']['email']	= Input::get('email');
		}
		//更改用户年龄
		if(Input::has('age')){
			$updateDate['user_info']['age']		= Input::get('age');
		}
		//更改用户头像
		if(Input::has('head_pic')){
			$updateDate['user_info']['head_pic']		= Input::get('head_pic');
		}
		//更改性别
		if(Input::has('sex')){
			$updateDate['user_info']['sex']		= Input::get('sex');
		}
		//更改出生日期
		if(Input::has('born')){
			$updateDate['user_info']['born']	= Input::get('born');
		}
		//更改用户职业
		if(Input::has('job')){
			$updateDate['user_info']['job']		= Input::get('job');
		}
		//是否开启匿名评论
		if(Input::has('is_anonymous')){
			$updateDate['user_config']['is_anonymous']	= Input::get('is_anonymous');
		}
		if(empty($updateDate)){
			$this->response	= $this->response('10005');
		}
		
		$isUpdate = $this->_model->updataUserInfo($shUserId , $updateDate);
		
		if($isUpdate){
			
			return Response::json( $this->response( '1' ) );
		}else{
			
			return Response::json( $this->response( '0' ) );
		}
	}


	public function anyGetUserMessage(){
		if(!Input::has('user_id')){
			return Response::json($this->response('10005'));
		}
		$userId = Input::get('user_id');
		$data = $this->_model->getParentComment($userId);
		debug($data);
		if(!empty($data)){
			$this->response = $this->response(1, '成功' ,$data);
		}else{
			$this->response = $this->response(0, '失败' ,$data);
		}
		return Response::json($this->response);
	}


	
	public function anyGetToken(){
		if(!Input::has('image')){
			$this->response = $this->response(ApiController::FAILDCODE , '参数错误');
			return Response::json($this->response);
		}
		$image=Input::get('image');
		$accessKey = 'h591Hrv-oh3BornRVEQqlDE7IJQYFgM-dkA44tKM';
		$secretKey = 'XFwQNCCycfAf6fv_Ox-teKB8Tf2Bk21Xr5cqYXEm';
		$qiniu	= new Auth($accessKey , $secretKey);
		$data['token'] = $qiniu->uploadToken($image);

		$this->response = $this->response(1, '成功' ,$data);
		return Response::json($this->response);

	}

	


}
