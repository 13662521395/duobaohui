<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class Role_edit extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    protected $view_switch = true;
	protected $accessList	= array(10);

	private $newData;
	private $roleId;
	private $msg;

	public function run() {
		if (!$this->_init()) {return FALSE;}
		if(isset($this->request->POST['name'])){ // 提交表单 
			if( $this->editCheck() && $this->edit() ){ //参数检查并保存
				$this->ajaxDialog('保存成功');
			}else{
				$this->ajaxDialog($this->msg, false);
			}
		}

		$this->view->fromDomain = isset($this->request->GET['fromDomain']) ? $this->request->GET['fromDomain'] : '';
		$this->view->role = $this->getRoleRow();
		$this->view->nodeList = $this->getNodeList();
	}

	private function _init() {
		$this->roleId = $this->request->REQUEST['role_id'];
		if(isset($this->request->POST['name'])){ // 提交表单 
			$this->newData['role_id'] = trim($this->request->POST['role_id']);
			$this->newData['name'] = trim($this->request->POST['name']);
		}
		if(isset($this->request->POST['node_id'])){ // 提交表单 
			$this->newData['node_id'] = $this->request->POST['node_id'];
		}
		return $this->_check();
	}


	private function _check(){
		$this->access($this->accessList);
		if( !isset($this->request->REQUEST['role_id']) ){
			return false;
		}
		return TRUE;
	}

	private function editCheck(){
		if($this->newData['name']==''){
			$this->msg = '名称不能为空';
			return false;
		}
		return TRUE;
	}

	private function edit(){
		return Rbac::getInstance()->editRole($this->newData);
	}

	private function getRoleRow(){
		return Rbac::getInstance()->getRoleRow($this->roleId);
	}

	

	private function getNodeList(){
		$nodeList = Rbac::getInstance()->getNodeList();
		$accessNodeIds = Rbac::getInstance()->getAccessNodeList($this->roleId);
		$list = array();
		foreach($nodeList as $v){
			if(in_array($v->node_id, $accessNodeIds )){
				$v->access = 1;
			}else{
				$v->access = 0;
			}

			if( strpos($v->action, 'rbac') ===false){
				$list[] = $v;
			}
		}

		return $list;
	}
}
