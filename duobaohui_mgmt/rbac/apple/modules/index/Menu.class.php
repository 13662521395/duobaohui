<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Index;
use Gate\Package\System\Rbac;

class Menu extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    //protected $view_switch = false;
	protected $accessList	= array(10,20);

	public function run() {
		$this->view->menuList = $this->getMenuList();
	}

	private function _init() {
		return $this->_check();
	}

	private function _check(){

		return TRUE;
	}

	private function getMenuList(){
		return Rbac::getInstance()->getMenuList($this->loginUser->identity);
	}
}
