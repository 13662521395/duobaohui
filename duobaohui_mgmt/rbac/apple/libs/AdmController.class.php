<?php
/*
 * web基类
 */
namespace Gate\Libs;
use \Gate\Libs\Controller;
use Gate\Package\System\Rbac;

class AdmController extends Controller {
	protected $accessList = array();
	protected $loginUser = array();
	public function run(){}

	protected function baseInit(){
		/*
		// 登录认证
		$action = '/'.$this->module. '/' . $this->action;
		if( USER_AUTH_ON && !isset($this->request->session->id) && strpos(NOT_AUTH_ACTION, $action)===false  ){
			$this->redirect('/user/login');
		}
		// 权限管理
		if(USER_ACCESS_ON){
			if( !$this->access() ){
				die('<script>alert("未授权的页面");history.go(-1);</script>');
			}
		}

		$this->view->menu = $this->getMenuList();
		 */
	}


	/*
	 * 验证授权
	 */
	protected function access(){
		$this->loginUser = $this->request->session;
		$this->view->loginUser = $this->loginUser;
		$action = '/'.$this->module. '/' . $this->action;
		if(strpos(NOT_ACCESS_ACTION, $action)!==false){
			return true;
		}
		
		//if(isset($this->loginUser) &&  !in_array($this->loginUser->identity, $this->accessList)){
		if(isset($this->loginUser)){
			$accessList = Rbac::getInstance()->getNodeAccessList($action, $this->loginUser->role);
			if($accessList){
				foreach($accessList as $v){
					// 模块不检查加密
					if($v->type==2){
						return true;
					}
					// 页面要检查加密
					if($v->type==1 &&  $this->request->GET['a'] == $this->nodeEncrypt($v->node_id)){
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	/*
	 * ajax请求返回
	 * 页面
	 * forwardUrl: 网站页面的url
	 */
	protected function ajaxForward($forwardUrl, $message='成功', $ok=true){
		$statusCode = $ok ? 200 : 300;

		$array = array(
					'statusCode'	=> $statusCode,
					'message'		=> $message,
					'navTabId'		=> $forwardUrl,
					'rel'			=> '',
					'callbackType'	=> 'forward',
					'forwardUrl'	=> $forwardUrl);

		die( json_encode($array));
	}

	/*
	 * ajax请求返回
	 * 弹出窗口
	 * navTabId: tab页面的id
	 */
	protected function ajaxDialog($message='成功', $status=true){
		$array = array(
					'status'		=> $status,
					'message'		=> $message);
		if(!$status){
			$array['error'] = $message;
		}

		die( json_encode($array));
	}

	/*
	 * 菜单列表
	 */
	protected function getMenuList(){
		$menu = new \stdClass;
		if(isset($this->loginUser->identity)){
			$menu->list = Rbac::getInstance()->getMenuList($this->loginUser->role);
			// 当前选中的菜单
			$menu->active = array();
			if(isset($this->request->GET['mid'])){
				$row = Rbac::getInstance()->getNodeRow($this->request->GET['mid']);
				$menu->active = $row->pid_path!='' ? explode('-', $row->pid_path) : array();
				$menu->active[] = $row->node_id;
			}

			//访问权限加密
			foreach($menu->list as $v){
				//$v->auth = $this->nodeEncrypt($v->node_id);
			}
		}
		return $menu;
	}

	/*
	 * 节点访问加密
	 */
	private function nodeEncrypt($nodeId){
		return substr(md5($this->loginUser->salt.$nodeId),0,6);
	}
}
