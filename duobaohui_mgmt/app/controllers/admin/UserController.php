<?php
/**
 * @author		wuhui@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Admin;

use AdminController;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\URL;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Redirect;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\Cookie;

use Laravel\Model\Admin\UserModel;

class UserController extends AdminController {
	private $_length = 20;

	public function __construct(){
		parent::__construct();
	}

    public function getLogin(){
        return Response::view('admin.user.login');
	}

    public function getLogout(){
		Session::flush();
		return Redirect::to(URL::route('login'));
	}

	/*
	 * 更新用户昵称
	 */
	public function postUpdateUserNickName(){
		$data = array(
				'code'	=> 1,
				'msg'	=> '成功'
		);
		if( !Input::has('user_id') || !Input::has('nick_name') ){
			$data['code']	= 0;
			$data['msg']	= '参数错误';
			return Response::json($data);
		}

		$userId		= Input::get('user_id');
		$nickName	= Input::get('nick_name');

		$userModel	= new UserModel();

		$isUpdate	= $userModel->updateUserNickName($userId , $nickName);

		if($isUpdate){
			$data['code']	= 1;
			$data['msg']	= '更新成功';	
		}else{
			$data['code']	= 0;
			$data['msg']	= '更新失败';	
		}
		return Response::json($data);
	}

	/**
	 * 用户列表
	 */
	public function getUserList(){
		$condition = array();
		if(Input::has('is_real')){
			$condition['is_real'] = Input::get('is_real');
			$userInfo['is_real'] = Input::get('is_real');
		}
		$userInfo['list'] = array();
		$userInfo['selected'] = "user";
		$userInfo['msg'] = "";

		$userModel	= new UserModel();
		$list	= $userModel->getUserInfo($condition);
		$userInfo['userList'] = $list;

		$res = $userModel->getAllUserSum(1);
		$userInfo['sum'] = $res?$res[0]->sum:0;

		$todaySum = $userModel->getNewUserSum(1,date('Y-m-d'));
		$userInfo['todaySum'] = $todaySum?$todaySum[0]->sum:0;

        return View::make('admin.user.user_list' , $userInfo);
	}


	//分页
	private function pageInfo($length=40 , $totalNum){
		$pageInfo               = new \stdClass;
		$pageInfo->length       = Input::has('length') ? Input::get('length') : $length;;
		$pageInfo->page         = Input::has('page') ? Input::get('page') : 1;
		$pageInfo->offset		= $pageInfo->page<=1 ? 0 : ($pageInfo->page-1) * $pageInfo->length;
		$pageInfo->totalNum     = $totalNum; 
		$pageInfo->totalPage    = ceil($pageInfo->totalNum/$pageInfo->length);

		return $pageInfo;
	}

	/*
	 * 登陆
	 */
	public function login(){
		return Response::View('admin.user.login');	
	}

	/*
	 * 检测登陆
	 */
	public function postNickNameLogin(){
		if( !Input::has('nick_name') || !Input::has('password') ){
			return Redirect::to('/admin/user/login');
		}

		$nickName	= Input::get('nick_name');
		$password	= Input::get('password');

		$userModel	= new UserModel();
		// 获取rbac系统的用户信息
		$content = $this->rbacContent('login', array('login_name'=>$nickName, 'password'=>$password));
		$content = json_decode($content);
		if(!is_object($content)){
			die($content);
		}

		if($content->status && isset($content->userinfo)){
			$userInfo = $content->userinfo;
			$userInfo->accessList = $this->getAccessList($userInfo->user_id);

			Session::put('userInfo',$userInfo);
			Session::migrate(true);
			return Redirect::to('/admin/index');
		}else{
			return Redirect::to(URL::route('login'));
		}
	}

	public function anyEdit(){
		if( !Input::has('id')){
			return Redirect::to('/admin/user/user-list');
		}
		$userModel	= new UserModel();
		if( !Input::has('nick_name')){
			$id = Input::get('id');
			$data['detail'] = $userModel->getUserDetail($id);
			return Response::view('admin.user.edit' , $data);
		}

		$newData['id']			= Input::get('id');
		$newData['tel']	= Input::get('tel');
		$newData['nick_name'] = Input::get('nick_name');

		$userModel->editUser($newData);

		return Redirect::to('/admin/user/edit?id='.$newData['id']);
	}


	public function anyDel(){
		if( !Input::has('id')){
			$response = $this->response(10005);
			return Response::json( $response );
		}
		$userModel	= new UserModel();
		$newData['id']			= Input::get('id');
		$newData['is_delete']	= 1;

		$isD = $userModel->editUser($newData);

		if($isD===false){
			$response = $this->response(0,'删除失败，请重试');
		}else{
			$response = $this->response(1,'删除成功');
		}
		return Response::json( $response );
	}

	/*
	 * 
	 */
	public function getNoAuth(){
		return Response::View('admin.no_auth');
	}



	/**
	 * 调用海红接口，获取权限信息
	 * 有问题找海红啊啊啊啊
	 * @param $user_id
	 * @return mixed|null
	 */
	public function getAccessList($user_id) {
		if(!isset($user_id)) {
			return null;
		}
		$access = $this->rbacContent('access?user_id='.$user_id);
		if($access==''){
			return null;
		}
		$accessList = json_decode($access);
		return $accessList;
	}

}
