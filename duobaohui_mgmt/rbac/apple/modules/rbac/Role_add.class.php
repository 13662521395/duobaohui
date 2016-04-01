<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class Role_add extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    protected $view_switch = true;
	protected $accessList	= array(10);

	private $newData;
	private $msg;

	public function run() {
		if (!$this->_init()) {return FALSE;}
		if(isset($this->request->POST['name'])){ // 提交表单 
			if( $this->addCheck() && $this->add() ){ //参数检查并保存
				$this->ajaxDialog('添加成功');
			}else{
				$this->ajaxDialog($this->msg, false);
			}
			
		}
		$this->view->fromDomain = isset($this->request->GET['fromDomain']) ? $this->request->GET['fromDomain'] : '';
		$this->view->nodeList = $this->getNodeList();
	}

	private function _init() {
		if(isset($this->request->POST['name'])){ // 提交表单 
			$this->newData['name'] = trim($this->request->POST['name']);
		}
		if(isset($this->request->POST['node_id'])){ // 提交表单 
			$this->newData['node_id'] = $this->request->POST['node_id'];
		}
		if(isset($this->request->POST['role_id'])){  
			$this->newData['role_id'] = trim($this->request->POST['role_id']);
		}
		return $this->_check();
	}


	private function _check(){
		return TRUE;
	}

	private function addCheck(){
		if($this->newData['name']==''){
			$this->msg = '名称不能为空';
			return false;
		}
		return TRUE;
	}

	private function add(){
		return Rbac::getInstance()->addRole($this->newData);
	}

	private function getNodeList(){
		$list = array();
		$nodeList =  Rbac::getInstance()->getNodeList();
		foreach($nodeList as $v){
			if( strpos($v->action, 'rbac') ===false){
				$list[] = $v;
			}
		}
		return $list;
	}
}
