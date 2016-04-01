<?php
namespace Laravel\Controller\Admin;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\App;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Redirect;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\View;
use App\Libraries\Curl;

class RbacController extends \AdminController {
	public function __construct(){
		parent::__construct();
	}

	public function anyAction($action){
		if(isset($_POST) && !empty($_POST)){ // 提交表单
			$urlParam		= '?fromDomain=/admin/rbac/action/';
			$action .= $urlParam;
			$content =	$this->rbacContent($action, $_POST);
			$obj = json_decode($content);
			if( !is_object($obj)){
				return Response::json($content);
			}
			return Response::json($obj);
		}else{
			$this->viewData['content'] =	$this->rbacContent($action);
		}

		//$this->viewData = array_merge($data, $this->viewData);
		return Response::view('admin.rbac', $this->viewData);
	}

}
