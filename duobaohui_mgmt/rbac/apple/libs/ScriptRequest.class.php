<?php
namespace Gate\Libs;

class ScriptRequest {

	private $request_data = NULL;

	public static function getRequest() {
		static $singleton = NULL;
		is_null($singleton) && $singleton = new ScriptRequest();
		return $singleton;
	}

	public function __construct() {
		$argv = $GLOBALS['argv'];
		$path = $argv[1];

		if(strpos($argv[1], '?')){
			$arrUri = explode('?', $argv[1]);
			$path = $arrUri[0];
			parse_str($arrUri[1],$_GET);
			$_REQUEST = $_GET;
		}

		if(isset($argv[2])){
			if($argv[2]!='get' && $argv[2]!='post'){
				echo '第二个参数必须是 get 或 post, 第三个参数是值';die;
			}
			if(isset($argv[3])){
				parse_str($argv[3], $data);
			}
			if($argv[2]=='get'){
				$_GET = !is_null($_GET) ? array_merge($data, $_GET) : $data;
				$_REQUEST = $_GET;
			}
			if($argv[2]=='post'){
				$_POST = $argv[2]=='post' ? $data : null;
				$_REQUEST = !is_null($_GET) ? array_merge($_GET, $_POST) : $_POST;
			}
		}
		// initialize HTTP data
		$this->request_data['protocol']  = 'script';
		$this->request_data['domain']    = $argv[0];
		$this->request_data['uri']      = $argv[1];
		$this->request_data['path']      = $path;
		$this->request_data['path_args'] = explode('/', $this->path);
		$this->request_data['method']    = isset($argv[2]) ? $argv[2]: 'get';
		$this->request_data['GET']       = $_GET;
		$this->request_data['POST']      = $_POST;
		$this->request_data['COOKIE']    = Utilities::zaddslashes($_COOKIE);
		$this->request_data['REQUEST']   = Utilities::zaddslashes($_REQUEST);
		$this->request_data['headers']   = Utilities::parseRequestHeaders();
		$this->request_data['base_url']  = $argv[0];
		$this->request_data['ip']        = '127.0.0.1';
		$this->request_data['time']      = time();
		//$this->request_data['session']   = \Gate\Libs\Session::singleton()->load($_COOKIE);
	}

	public function __get($name) {
		if (!isset($this->request_data[$name])) {
			return NULL;
		}
		return $this->request_data[$name];
	}




    public function __toString() {
        return serialize($this->request_data);
        //return json_encode($this->request_data);
    }

}
