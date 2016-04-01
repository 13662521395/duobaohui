<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class User_add extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    protected $view_switch = true;

	private $newData;
	private $msg;

	public function run() {
		$this->_init();
		if(isset($this->request->REQUEST['is_save'])){ // 提交表单 
			if( $this->_check() && $this->add() ){ //参数检查并保存
				$this->ajaxDialog('添加成功');
			}else{
				$this->ajaxDialog($this->msg, false);
			}
		}

		$this->view->roleList = $this->getRoleList();
		$this->view->fromDomain = isset($this->request->GET['fromDomain']) ? $this->request->GET['fromDomain'] : '';
	}

	private function _init() {
		if(isset($this->request->REQUEST['is_save'])){ // 提交表单 
			$this->newData['login_name'] = trim($this->request->REQUEST['login_name']);
			$this->newData['password'] = trim($this->request->REQUEST['password']);
			$this->newData['password2'] = trim($this->request->REQUEST['password2']);
			$this->newData['nick_name'] = trim($this->request->REQUEST['nick_name']);
			$this->newData['role_id'] = isset($this->request->REQUEST['role_id']) ? $this->request->REQUEST['role_id'] : array();
		}
		if(isset($this->request->REQUEST['role_id'])){
			$this->newData['role_id'] = $this->request->REQUEST['role_id'];
		}
	}


	private function _check(){
		if(isset($this->request->REQUEST['is_save'])){ // 提交表单 
			if($this->newData['login_name']==''){
				$this->msg = '登录名不能为空';
				return false;
			}
			if($this->newData['nick_name']==''){
				$this->msg = '昵称不能为空';
				return false;
			}
			if($this->newData['password']==''){
				$this->msg = '密码不能为空';
				return false;
			}
			if($this->newData['password']!=$this->newData['password2']){
				$this->msg = '两次密码不一致';
				return false;
			}
			unset($this->newData['password2']);

			if(Rbac::getInstance()->hasUser($this->newData['login_name'])){
				$this->msg = '登录名已存在,不能重复!';
				return false;
			}

			if(!isset($this->newData['role_id']) || !is_array($this->newData['role_id']) || empty($this->newData['role_id'])){
				$this->msg = '权限组不能为空';
				return false;
			}
		}
		return TRUE;
	}


	private function add(){
		return Rbac::getInstance()->addRoleUser($this->newData);
	}

	/*
	 * 角色列表
	 */
	private function getRoleList(){
		return Rbac::getInstance()->getRoleList();
	}
}
