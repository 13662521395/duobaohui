<?php
/**
 * 打开软件时获取APP配置接口
 *
 * @author		xuguangjing@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */
namespace Laravel\Controller\Individual;

use ApiController;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Input;
use Laravel\Model\AppConfigModel;

use Illuminate\Support\Facades\Response;

class AppConfigController extends ApiController {	
	public $response;
	private $_model;
	
	public function __construct(){
		parent::__construct();
		$this->_model = new AppConfigModel();
	}


	//获取app配置信息
	public function anyGetAppConfig(){
				if(!Input::has('company_id')){
			return Response::json( $this->response( '10005' ) );
		}
		$shCompanyId = Input::get('company_id' , 0);
	
		$data = $this->_model->getAppConfig($shCompanyId);

		$this->response = $this->response(ApiController::SUCCESSCODE , ApiController::SUCCESS , $data);
		return Response::json($this->response);

	}


}
