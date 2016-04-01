<?php
namespace Laravel\Model\Admin;

use Laravel\Model\Model;
use Illuminate\Support\Facades\DB;

class BannerModel extends Model {
	public function getBannerList() {
		return DB::table('banner')->where('type', '!=' ,2)->orderBy('sort', 'asc')->get();
	}
	
	public function getBannerById($id) {
		return DB::table('banner')->where('id', $id)->first();
	}
	
	public function addBanner(array $data) {
		return DB::table('banner')->insert($data);
	}
	
	public function editBanner($id, array $data) {
		return DB::table('banner')->where('id', $id)->update($data);
	}
	
	public function delBanner($id) {
		return DB::table('banner')->where('id', $id)->delete();
	}

	/*
	 * 欢迎页
	 * 只有一个
	 */
	public function getWelcome(){
		$row =  DB::table('banner')->where('type', 2)->first();
		if(!$row){
			$data = array('title'=>'欢迎光临', 'link_url'=>'', 'pic_url'=>'http://7xlbf0.com1.z0.glb.clouddn.com/o_1a7rjcrmn9u5ari1ofc1re8o449.png','type'=>2);
			// 初始化
			DB::table('banner')->insert($data);
			$row =  DB::table('banner')->where('type', 2)->first();
		}
		return $row;
	}


	/*
	 * 
	 * 支付页图
	 * 只有一个
	 */
	public function getRecharge(){
		$row =  DB::table('banner')->where('type', 4)->first();
		if(!$row){
			$data = array('title'=>'支付页面图', 'link_url'=>'', 'pic_url'=>'http://7xlbf0.com1.z0.glb.clouddn.com/o_1a2rscdbi1ac373g1m3gct8ai9.jpg','type'=>4);
			// 初始化
			DB::table('banner')->insert($data);
			$row =  DB::table('banner')->where('type', 4)->first();
		}
		return $row;
	}
}
