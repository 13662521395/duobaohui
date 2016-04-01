<?php
/**
 * 意见反馈
 * @author		wangkenan@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\System;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session 
use Illuminate\Support\Facades\Response;	//引入response

use Laravel\Model\SystemModel;			//引入model

use Illuminate\Support\Facades\Log;//引入日志类

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class OpinionController extends ApiController {
	public function __construct(){
		parent::__construct();
	}

	public function anyAddOpinion(){
		if( !Input::has('content')){
			return Response::json( $this->response( '10005' ) );
		}

		$content = Input::get('content');
		$systemM = new SystemModel();
		$list = $systemM->addOpinion($content, Session::get('user.id'));

		//var_dump($list->goods_desc);

		//$list->goods_desc = str_replace(',', '', $list->goods_desc);

		$this->response = $this->response((int)$list, '成功');
		return Response::json($this->response);

	}

	
}
