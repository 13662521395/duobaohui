<?php

use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\Cookie;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Redirect;
use Illuminate\Support\Facades\Response;

use Laravel\Model\Admin\AuthorityModel;

class AdminController extends BaseController {

	protected $_loginUserId;
	protected $_loginUserNickName;
	protected $_url;

	protected function __construct(){
		$request_url = $_SERVER['REQUEST_URI'];

		$request_url = explode('?' , $request_url);

		$this->_url = $request_url[0];
		
		if($this->_url != '/admin/user/login' && $this->_url != '/admin/user/nick-name-login' && $this->_url != '/admin/user/no-auth'){
			if($this->_isLogin()){
				$this->_isCanVisit();	
			}
		}
	}


	/*
	 * 验证是否登陆
	 */
	protected function _isLogin(){
		$userInfo = Session::get(Cookie::get(Config::get('app.Cookie-Key')));	
		if(isset($userInfo->id)){
			$this->_loginUserId = $userInfo->id;
			$this->_loginUserNickName = $userInfo->nickname;
			return true;
		}else{
			header('Location:/admin/user/login');
			die;
		}
	}

	/*
	 * 验证是否有权限访问
	 */
	protected function _isCanVisit(){
		$authorityModel = new AuthorityModel();

		if(!$authorityModel->isCanVisit($this->_loginUserId , $this->_url)){
			header('Location:/admin/user/no-auth');
			die;
		}else{
			return true;
		}
	}




}
