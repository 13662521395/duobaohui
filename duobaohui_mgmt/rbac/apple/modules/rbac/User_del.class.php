<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class User_del extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    protected $view_switch = false;
	protected $accessList	= array(10);

	private $roleId;
	private $msg;

	public function run() {
		if (!$this->_init()) {return FALSE;}
		if(isset($this->request->REQUEST['id'])){ // 提交表单 
			if( $this->del() ){ //参数检查并保存
				$this->ajaxDialog('删除成功');
			}else{
				$this->ajaxDialog( $this->msg, false);
			}
		}
	}

	private function _init() {
		if(isset($this->request->REQUEST['id'])){ // 提交表单 
			$this->roleId = trim($this->request->REQUEST['id']);
		}
		return $this->_check();
	}


	private function _check(){
		return TRUE;
	}


	private function del(){
		return Rbac::getInstance()->delRoleUser($this->roleId);
	}
}
