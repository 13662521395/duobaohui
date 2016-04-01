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
use Illuminate\Support\Facades\Request;
use Illuminate\Support\Facades\Config;
use Laravel\Model\SystemModel;

class BannerModel extends Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');
	}

	public function getBannerList($version=''){
		$list = DB::table('banner')
				->select('*')
				->orderBy('sort', 'ASC')
				->where('type', '!=', '2')
				->where('type', '!=', '4')
				->get();

		// 针对ios 审核，不显示iPhone产品
		$data = array();
		$systemM = new SystemModel();
		if( $systemM->checkIOSAudit( Request::header('user-agent'), $version) ){
			foreach($list as $v){
				if(stripos($v->title,'apple')!==false  || stripos($v->title,'ipad')!==false ||  stripos($v->title,'iPhone')!==false || stripos($v->title, 'mac')!==false ){
					continue;
				}
				$data[]	= $v;
			}
		}else{
			$data = $list;
		}

		return $data;
	}

	public function getWelcome(){
		$row = DB::table('banner')
				->select('*')
				->orderBy('sort', 'ASC')
				->where('type', '2')
				->first();
		return $row;
	}

	public function getRechargeBanner(){
		$row = DB::table('banner')
			->select('*')
			->orderBy('sort', 'ASC')
			->where('type', '4')
			->first();
		return $row;
	}
}
