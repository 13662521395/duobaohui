<?php
namespace Gate\Libs;
use \Gate\Libs\Router;
use \Phplib\Template\View;

abstract class Controller {

	protected $request = NULL;
	protected $module = NULL;
	protected $action = NULL;
	protected $head = 200;
    //receive format data
	protected $view;
 	protected $view_switch = TRUE;

	protected $error_code = 0;
	protected $message = 'OK';

	protected $client = NULL;

	protected $loginUserId = 0;
    protected $username = '';

    protected $companyId = 0;

	protected $tpl;
	/**
	 * Subclasses must implement this method to route requests.
	 */
	abstract public function run();

	public function __construct($request, $module, $action) {

		$this->request = $request;
		$this->module = $module;
		$this->action = $action;
		$this->loginUserId = isset($this->request->session->id) ? $this->request->session->id : 0;
		$this->companyId = isset($this->request->session->company_id) ? $this->request->session->company_id : 0;
		$this->view = new \stdClass;
		$this->tpl = $this->module.'/'.$this->action;	
	}

	protected function baseInit(){}

	public function control() {
		try {
			$this->baseInit();
			$this->run();
		}
		catch (\Exception $e) {
            if (!$e instanceof \Gate\Libs\VException) {
                $log = new \Phplib\Tools\Liblog('gate_error_log', 'normal');
                $message = $e->getMessage();
                $log->w_log($message . " by file " . $e->getFile() . " in line " . $e->getLine());
			    $this->setError(400, 11011, $message);
            }
            else {
                $http_code = $e->getHttpCode();
                $error_code = $e->getCode();
                $message = $e->getEMessage();
			    $this->setError($http_code, $error_code, $message);
            }
		}
	}

    public function echoView() {
		$this->echoHeader();
		echo $this->formatJson();
	}

    //support template output
    public function echoTemplate() {

    	//add view switch in controller
    	/*if ($this->view_switch) {
	        $templateDriver = TemplateDriver::getInstance();
	        $templateDriver->loadParam($this->view, $this->module, $this->action, $this->request->session);

	        $templateDriver->loadTemplate();
		}
		else*/if ($this->view_switch) {
			try{
				$view = new View();
				//定义模板路径,缓存路径和编译完成后的路径
				$view->config->compile_dir = TEMPLATE_PATH;
				$view->config->cache_dir = TEMPLATE_PATH_CACHE;
				$view->config->template_path = TEMPLATE_PATH_TPL;

				//获取要显示的数据并赋值给模板
				foreach($this->view as $k=>$v){
					$view->assign($k , $v);
				}

				//可加缓存
				$view->display($this->tpl);
				//$view->display($this->module.'/'.$this->action , 'demo' , true);
			}catch(\Exception $e){
				 echo 'view: ' .$e->getMessage();
			}

			$arr = error_get_last();//获取刚发生的错误信息，并返回数组，无错410             if(!is_null($arr)) //不为null,则表示出错了
			if(!is_null($arr)){
				print_r($arr);
			}

    	}else{
    		$this->echoView();
    	}
		
    }

	public function display($tpl){
		$this->tpl = $tpl;	
	}

	public function checkStatusValid() {
		if (200 == $this->head) {
			return TRUE;
		}
		return FALSE;
	}

	
	private function formatJson() {
		if (200 === $this->head) {
			$response = $this->view;
		}
		else {
			$arrError = $this->getConstant('error_message');
			$response = array(
				'error_code' => $this->error_code,
				//'message' => $this->message,
				'message' => $arrError[$this->error_code],
			);
		}
		return json_encode($response);
	}

	protected function setError($head = 200, $errorCode = 0, $message = 'OK') {
		$this->head = $head;
		$this->error_code = $errorCode;
		//$this->message = $message;
	}

	protected function echoHeader() {
		if (200 == $this->head) {
			header('Content-Type: text/plain; charset=UTF-8');
			return;
		}
		$this->setHeaderByHttpStatusCode($this->head);
	}

	protected function setHeaderByHttpStatusCode($code) {
		$codes = array(
			'400' => '400 Bad Request',
			'401' => '401 Unauthorized',
			'404' => '404 Not Found',
		);

		if (!isset($codes[$code])) {
			throw new \Exception(sprintf("Unknown HTTP status code: %s.", $code));
		}

		header("HTTP/1.1 {$codes[$code]}");
	}

	protected function authorizeOrderBackend() {
		$token = $this->request->REQUEST['token'];
		//md5('wearedootaman');
		$rightToken = '4b99ac82830933d95aece3b4f5e47bbf';
		if (empty($token) || $token != $rightToken) {
			throw new \Gate\Libs\VException('in valid token:' . $token, 29010);
		}
		$white = array();
		$adminUid = $this->request->REQUEST['admin_uid'];
		if (empty($adminUid) || (!empty($white) && !in_array($adminUid, $white))) {
			throw new \Gate\Libs\VException('in valid admin uid', 29011);
		}
		return $adminUid;
	}

	protected function getConstant($constFile){
		return parse_ini_file(ROOT_PATH . '/constant/' . $constFile . '.ini', true);
	}

	protected function redirect($url){
		header("Location:".$url);
		die;
	}

	protected function error404(){
		header("Status: 404 Not Found");die;
	}

	protected function getRequestByGetData($param){
		return isset($this->request->GET[$param]) ? $this->request->GET[$param] : false;
	}

	protected function getRequestByPostData($param){
		return isset($this->request->POST[$param]) ? $this->request->POST[$param] : false;
	}

	protected function getRequestData($param){
		return isset($this->request->REQUEST[$param]) ? $this->request->REQUEST[$param] : false;
	}




}
