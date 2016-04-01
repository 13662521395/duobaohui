<?php
/**
* 系统 
* @author wangkenan@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;


class SystemModel extends Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');
	}
	public function addOpinion($content){
		$res = DB::table('system_opinion')->insert(array('content' => $content));
		return $res;
	}

}
