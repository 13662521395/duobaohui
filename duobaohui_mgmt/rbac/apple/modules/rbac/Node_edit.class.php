<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class Node_edit extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    protected $view_switch = true;
	protected $accessList	= array(10);

	private $newData;
	private $nodeId;
	private $roleIds;
	private $msg;

	public function run() {
		if (!$this->_init()) {return FALSE;}
		if(isset($this->request->POST['name'])){ // 提交表单 
			if( $this->editCheck() && $this->edit() ){ //参数检查并保存
				$this->ajaxDialog('保存成功,请添加如下sql到上线代码中@_@:<br/>' . implode(';<br/>',$GLOBALS['rbac_sql']).';');
			}else{
				$this->ajaxDialog($this->msg, false);
			}
		}

		$this->view->fromDomain = $this->fromDomain;
		$this->view->node = $this->getNodeRow();
		$this->view->roleList = $this->getRoleList();
		$this->view->parent = Rbac::getInstance()->getNodeRow($this->view->node->pid);
	}

	private function _init() {
		$this->fromDomain = isset($this->request->GET['fromDomain']) ? $this->request->GET['fromDomain'] : '';
		$this->nodeId		= trim($this->request->REQUEST['node_id']);

		if(isset($this->request->POST['name'])){ // 提交表单 
			$this->newData['node_id']	= trim($this->request->POST['node_id']);
			$this->newData['name']		= trim($this->request->POST['name']);
			$this->newData['action']	= trim($this->request->POST['action']);
			$this->newData['sort']		= trim($this->request->POST['sort']);
			$this->newData['status']	= trim($this->request->POST['status']);
			$this->newData['type']		= trim($this->request->POST['type']);
			$this->newData['pid']		= isset($this->request->POST['pid']) ? trim($this->request->POST['pid']) : 0;

			$this->roleIds				= $this->request->POST['roleIds'];
		}
		return $this->_check();
	}


	private function _check(){
		if( !isset($this->request->REQUEST['node_id']) ){
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
		return Rbac::getInstance()->editNode($this->newData,$this->roleIds);
	}

	private function getNodeRow(){
		return Rbac::getInstance()->getNodeRow($this->nodeId);
	}

	/*
	 * 角色列表
	 */
	private function getRoleList(){
		$roleList = Rbac::getInstance()->getRoleList();
		$accessRoleIds = Rbac::getInstance()->getAccessRole($this->nodeId);
		foreach($roleList as $v){
			if(in_array($v->role_id, $accessRoleIds )){
				$v->access = 1;
			}else{
				$v->access = 0;
			}
		}
		return $roleList;
	}
}
