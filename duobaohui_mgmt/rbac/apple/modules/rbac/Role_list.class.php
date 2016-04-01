<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class Role_list extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    //protected $view_switch = FALSE;
	protected $accessList	= array(10);

	public function run() {
		$this->view->roleList = $this->getRoleList();
		$this->view->fromDomain = isset($this->request->GET['fromDomain']) ? $this->request->GET['fromDomain'] : '';
	}

	private function _init() {
		return $this->_check();
	}

	private function _check(){

		return TRUE;
	}

	private function getRoleList(){
		return Rbac::getInstance()->getRoleList();
	}

}
