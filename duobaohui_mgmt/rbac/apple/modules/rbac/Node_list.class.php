<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class Node_list extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    //protected $view_switch = FALSE;

	public function run() {
		$this->_init();
		$this->view->nodeList = $this->getNodeList();
		$this->view->fromDomain = isset($this->request->GET['fromDomain']) ? $this->request->GET['fromDomain'] : '';
	}

	private function _init() {
		$this->tag = isset($this->request->GET['tag']) ? $this->request->GET['tag'] : '';
		$this->fromDomain = isset($this->request->GET['fromDomain']) ? $this->request->GET['fromDomain'] : '';

		return $this->_check();
	}

	private function _check(){

		return TRUE;
	}

	private function getNodeList(){
		return Rbac::getInstance()->getNodeList($this->tag);
	}

}
