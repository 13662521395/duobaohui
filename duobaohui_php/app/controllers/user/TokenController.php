<?php
/**
 * 获取token 
 *
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\User;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\Session;		//引入session 
use Illuminate\Support\Facades\Response;	//引入response
use App\Libraries\TokenUtil;//引入token工具类

class TokenController extends ApiController {

	public function __construct(){
		parent::__construct();
	}

	public function anyToken(){
		$token = TokenUtil::getToken();
		if($token){
			$this->response = $this->response(1, '成功' ,$token);
		}else{
			$this->response = $this->response('10019');
		}
		return Response::json($this->response);
	}
}
