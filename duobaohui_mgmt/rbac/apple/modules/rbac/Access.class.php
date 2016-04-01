<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class Access extends \Gate\Libs\AdmController {
     //TRUE: 输出view 页面; FALSE: 输出json格式数据
    protected $view_switch = FALSE;

	public function run() {
		if(!$this->_init()){
			$this->view = '用户参数错误';
		}
		$this->view = $this->getList();
	}

	private function _init() {
		$isCheck = $this->_check();
		if($isCheck){
			$this->userId = $this->request->GET['user_id'];
		}
		return $isCheck;
	}

	private function _check(){
		if(!isset($this->request->GET['user_id'])){
			return false;	
		}

		return TRUE;
	}

	protected function getList(){
		$accessList = Rbac::getInstance()->getUserActions($this->userId);
		return $accessList;
	}

}
