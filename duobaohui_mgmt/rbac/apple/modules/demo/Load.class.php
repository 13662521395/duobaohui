<?php
/*
 * Modules 业务注释
 * @作者
 */
namespace Gate\Modules\Demo;

use \Gate\Package\Demo\Demo;
use \Gate\Package\Sphinx\Goods;


class Load extends \Gate\Libs\Controller {

	//protected $view_switch = FALSE; 

	private $twitter_id;

	public function run() { 

		if (!$this->_init()) { 
			return FALSE;
		}

		$this->view->goods = $this->getGoods();

	}

	private function _init() {

		return $this->check();
	}

	private function getRequest($param, $isInt=null){
		return isset($this->request->REQUEST[$param])
				? $this->request->REQUEST[$param]
				: ($isInt===null ? null : ($isInt ? 0 : ''));
	}

	private function check(){
		return TRUE;
	}

	private function getGoods(){
		return Demo::getInstance()->getGoods();
		//return Goods::getInstance()->guang("双黄连口服液");
	}
}
