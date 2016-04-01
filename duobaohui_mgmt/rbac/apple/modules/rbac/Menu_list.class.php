<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class Menu_list extends \Gate\Libs\AdmController {
     //TRUE: 输出view 页面; FALSE: 输出json格式数据
    //protected $view_switch = FALSE;

	public function run() {
		if(!$this->_init()){
			$this->view = '用户参数错误';
		}
		$this->view->menu = $this->getList();
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
			$menu = new \stdClass;
			$menu->list = Rbac::getInstance()->getMenuList($this->userId);
			$menu->activeIds = array();
			if(isset($this->request->GET['action'])){
				//$row = Rbac::getInstance()->getNodeRow($this->request->GET['mid']);
				$row = Rbac::getInstance()->getNodeRowByAction($this->request->GET['action']);
				$menu->activeIds = $row->pid_path!='' ? explode('-', $row->pid_path) : array();
				$menu->activeIds[] = $row->node_id;
			}
		return $menu;
	}

}
