<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class Login extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    protected $view_switch = false;

	private $newData;
	private $msg;

	public function run() {
		$this->_init();
		$this->view->status = 0;
		if(isset($this->request->REQUEST['login_name'])){ // 提交表单 
			if( $this->_check() ){ //参数检查并保存
				$userinfo = $this->loginUser();
				if($userinfo){
					$this->view->status = 1;
					$this->view->userinfo = $userinfo;
				}else{
					$this->view->message = '账户密码错误';
				}
			}else{
				$this->view->message = $this->msg;
			}
		}
	}

	private function _init() {
		if(isset($this->request->REQUEST['login_name'])){ // 提交表单 
			$this->newData['login_name'] = trim($this->request->REQUEST['login_name']);
		}
		if(isset($this->request->REQUEST['password'])){ // 提交表单 
			$this->newData['password'] = trim($this->request->REQUEST['password']);
		}
	}


	private function _check(){
		if(!isset($this->request->REQUEST['login_name']) || empty($this->request->REQUEST['login_name'])){ // 提交表单 
			$this->msg = '登录名不能为空';
			return false;
		}
		if(!isset($this->request->REQUEST['password']) || empty($this->request->REQUEST['password'])){ // 提交表单 
			$this->msg = '密码不能为空';
			return false;
		}
		return TRUE;
	}


	private function loginUser(){
		return Rbac::getInstance()->login($this->newData);
	}

}
