<?php
/**
* 活动
* @author wanghaihong@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;

class BannerModel extends Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');
	}

	public function getBannerList(){
		$list = DB::table('banner')
				->select('*')
				->orderBy('sort', 'ASC')
				->get();
		return $list;
	}
}
