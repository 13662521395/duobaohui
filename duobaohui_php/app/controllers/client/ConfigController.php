<?php
/**
 * 客户端配置
 * @author		wanghaihong
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Client;	    // 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session
use Illuminate\Support\Facades\Request;
use Illuminate\Support\Facades\Response;	//引入response

use Laravel\Model\ShopModel;
use Laravel\Model\SystemModel;			//引入model
use Laravel\Model\ClientModel;

class ConfigController extends ApiController {

    public function __construct(){
        parent::__construct();
    }

    /**
     * 
     */
    public function anyPay(){
		$systemM = new SystemModel();
		$config = $systemM->getPayConfig();

		$this->response = $this->response(1, '' , $config);
		return Response::json($this->response);
    }


}
