<?php
namespace Gate\Libs;

class Dispatcher {

	private $request = NULL;
	private $module = NULL;
	private $action = NULL;
	private $xhprof = FALSE;
    private $userid = NULL;


	public static function get() {
		static $singleton = NULL;
		is_null($singleton) && $singleton = new Dispatcher();
		return $singleton;
	}

	private function __construct() {
		try {
			if(isset($GLOBALS['argv'][1])){ // 脚本执行
				$this->request = new \Gate\Libs\ScriptRequest();
			}else{
				$this->request = new \Gate\Libs\HttpRequest();
			}
            //$this->userid = $this->request->session->id;
		}
		catch (\Exception $e) {
			$log = new \Phplib\Tools\Liblog('gate_error_log', 'normal');
			$log->w_log($e->getMessage());
			echo json_encode(array('error_code' => 10002, 'message' => $e->getMessage()));
			die();
		}
	}

	public function dispatch() {
		//output HTML CODE
        if (OA_VIEW_SWITCH == 'ON') {
            $path_args = $this->request->path_args;
            // first arg is the module's name
            $module = array_shift($path_args);
            $action = array_shift($path_args);

			if(empty($module)){
				$module = 'index';
			}
			if(empty($action)){
				$action = 'index';
			}

			$this->module = $module;
			$this->action = $action;
            $class = '\\Gate\\Modules\\' . ucwords($module) . '\\' . ucwords($action);
            $this->request->path_args = $class;
            if (class_exists($class)) {
				$controller = new $class($this->request, $this->module, $this->action);
				$controller->control();
			}else{
				$class = '\\Gate\\Modules\\Index\\Index';
				$controller = new $class($this->request, $this->module, $this->action);
			}
            $controller->echoTemplate();

        }
        //output json data
        else  {
            $path_args = $this->request->path_args;
            // first arg is the module's name
            $module = array_shift($path_args);
            empty($module) && $module = 'index';
            $this->module = $module;

            $action = array_shift($path_args);
            empty($action) && $action = 'index';
            $this->action = $action;
            // pass the control to module's Router class
            
			$record_path=$module."/".$action;
            $class = '\\Gate\\Modules\\' . ucwords($module) . '\\' . ucwords($action);
            $this->request->path_args = $path_args;
            if (!class_exists($class)) {
                $class = "\\Gate\\Modules\\Index\\Index";
            }
            $controller = new $class($this->request);
            $controller->control();
            $controller->echoView();

		}



    }

	private function startAnylize() {
		if ($this->xhprof === TRUE) {
			xhprof_enable(XHPROF_FLAGS_CPU + XHPROF_FLAGS_MEMORY);	
		}
	}

	private function finishAnylize() {
		if ($this->xhprof === TRUE) {
			$xhprof_data = xhprof_disable();	
			$xhprof_obj = new \Gate\Package\User\Xhprof();
			$uniqid = uniqid();
			$author = $this->module . ':' . $this->action;
			$xhprof_obj->addData($uniqid, $xhprof_data, $author);
		}
	}

	public function get_request() {
		return $this->request;
	}

	public function get_module() {
		return $this->module;
	}

	public function get_action() {
		return $this->action;
	}
}
