<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class User_edit extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    protected $view_switch = true;

	private $newData;
	private $msg;

	public function run() {
		$this->init();
		if(isset($this->request->POST['is_save'])){ // 提交表单 
			if($this->_check() && $this->edit() ){ //参数检查并保存
				$this->ajaxDialog('编辑成功', 1);
			}else{
				$this->ajaxDialog($this->msg, false);
			}
		}

		$this->view->userRole = $this->getUserRole();
		$this->view->roleList = $this->getRoleList();
		$this->view->fromDomain = isset($this->request->GET['fromDomain']) ? $this->request->GET['fromDomain'] : '';
	}

	private function init() {
		if(isset($this->request->POST['is_save'])){ // 提交表单 
			$this->viewUserId = $this->request->POST['user_id'];
			$this->newData['user_id'] = trim($this->request->POST['user_id']);
			$this->newData['login_name'] = trim($this->request->POST['login_name']);
			$this->newData['password'] = trim($this->request->POST['password']);
			$this->newData['nick_name'] = trim($this->request->POST['nick_name']);
			$this->newData['role_id'] = $this->request->POST['role_id'];
		}else{
			$this->viewUserId = $this->request->GET['user_id'];
		}
	}


	private function _check(){
		if(isset($this->request->POST['is_save'])){ // 提交表单 
			if($this->request->POST['user_id']=='' || $this->request->POST['login_name']=='' || $this->request->POST['nick_name']==''){
				$this->msg = '用户信息不能为空';
				return false;
			}
			if(!isset($this->request->POST['role_id']) || !is_array($this->request->POST['role_id']) || empty($this->request->POST['role_id'])){
				$this->msg = '权限组不能为空';
				return false;
			}
			if($this->request->POST['password']!='' && $this->request->POST['password']!=$this->request->POST['password2']){
				$this->msg = '两次密码不一致';
				return false;
			}
			if($this->request->POST['old_password']!=''){
				$isC = Rbac::getInstance()->checkPassword($this->request->POST['user_id'], $this->request->POST['old_password']);
				if(!$isC){
					$this->msg = '原密码错误';
					return false;
				}
			}

			if( Rbac::getInstance()->hasUser($this->newData['login_name'], $this->newData['user_id']) ){
				$this->msg = '登录名已存在,不能重复!';
				return false;
			}
		}
		return TRUE;
	}


	private function edit(){
		return Rbac::getInstance()->editRoleUser($this->newData);
	}

	/*
	 * 角色列表
	 */
	private function getRoleList(){
		return Rbac::getInstance()->getRoleList();
	}

	private function getUserRole(){
		$userRole = new \stdClass;
		$userRole->role_id = Rbac::getInstance()->getUserRoleIds($this->viewUserId);
		$userRole->user = Rbac::getInstance()->getUserById($this->viewUserId,'user_id, nickname, realname');
		return $userRole;
	}
}
