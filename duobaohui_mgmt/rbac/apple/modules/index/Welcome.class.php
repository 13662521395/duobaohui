<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Index;
use Gate\Package\System\Rbac;
#use Gate\Package\

class Welcome extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    //protected $view_switch = true;
	protected $accessList	= array(10,20);

	public function run() {
		if (!$this->_init()) {return FALSE;}

	}

	private function _init() {

		return $this->_check();
	}


	private function _check(){
		return TRUE;
	}
}
