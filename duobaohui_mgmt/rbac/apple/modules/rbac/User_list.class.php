<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class User_list extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    //protected $view_switch = FALSE;

	public function run() {
		$this->view->roleUserList = $this->getRoleUserList();
		$this->view->fromDomain = isset($this->request->GET['fromDomain']) ? $this->request->GET['fromDomain'] : '';
	}

	private function _init() {
		return $this->_check();
	}

	private function _check(){

		return TRUE;
	}

	private function getRoleUserList(){
		return Rbac::getInstance()->getRoleUserList();
	}

}
