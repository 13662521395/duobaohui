<?php
/*
 * 
 * @author
 */
namespace Gate\Modules\Rbac;
use Gate\Package\System\Rbac;

class Node_del extends \Gate\Libs\AdmController {
    // TRUE: 输出view 页面; FALSE: 输出json格式数据
    protected $view_switch = true;
	protected $accessList	= array(10);

	private $nodeId;
	private $msg;

	public function run() {
		if (!$this->_init()) {return FALSE;}
		if(isset($this->request->REQUEST['id'])){ // 提交表单 
			if( $this->del() ){ //参数检查并保存
				$this->ajaxDialog('删除成功,请添加如下sql到上线代码中@_@: ' . implode('; ',$GLOBALS['rbac_sql']).';');
			}else{
				$this->ajaxDialog($this->msg, false);
			}
		}
	}

	private function _init() {
		if(isset($this->request->REQUEST['id'])){ // 提交表单 
			$this->nodeId = trim($this->request->REQUEST['id']);
		}
		return $this->_check();
	}


	private function _check(){
		return TRUE;
	}


	private function del(){
		$return = Rbac::getInstance()->delNode($this->nodeId);
		if($return!==true && is_string($return)){
			$this->msg = $return;
			return false;
		}
		return true;
	}
}
