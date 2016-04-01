<?php
/**
* 爬虫数据脚本
* @author wangkenan@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Schema;

use Laravel\Model\LotteryModel;			//引入model


class SpiderModel extends Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');

	}

	public function spider_winRecord(){

		$res = file_get_contents('http://1.163.com/global/share/list.do?pageSize=20&pageNum=2');
		$res = json_decode($res);
		$totalCnt = $res->result->totalCnt;
		$pageSize = 20;
		$pageNum = ceil($totalCnt / $pageSize);

		for($i=1;$i<=$pageNum;$i++){
			$data_arr = array();
			$data = file_get_contents('http://1.163.com/global/share/list.do?pageSize=20&pageNum='.$i);
			$data = json_decode($data);
			$list = $data->result->list;
			for($k=0;$k<$pageSize;$k++){
				$data_list = array();
				$data_list['nickname'] = $list[$k]->author->nickname;
				$data_list['ip_address'] = $list[$k]->author->IPAddress;
				$data_list['ip'] = $list[$k]->author->IP;
				$data_list['avatar_prefix'] = $list[$k]->author->avatarPrefix.'160.jpeg';
				$data_list['content'] = $list[$k]->content;
				$data_list['title'] = $list[$k]->title;
				$data_list['record_id'] = $list[$k]->winRecord->id;
				$data_list['wangyi_goods_id'] = $list[$k]->winRecord->goods->gid;

				$data_list['images'] = implode(',', $list[$k]->images);
				$data_list['page'] = $i.','.$k;
				$data_arr[] = $data_list;
			}
			$res = DB::table('goods_163_winrecord')->insert($data_arr);
			//$data_arr['nickname'] = $list->author->nickname;	
			//print_r($data_arr);
			//die;


			
		}


		

		//var_dump($res->result->totalCnt);
	}

	public function dataToGoods(){
		$goods_list = DB::table('goods_163_info')->get();
		$list_arr = array();
		$list = array();
		foreach ($goods_list as $item) {
			$list['goods_name'] = $item->goods_name;
			$list['market_price'] = $item->market_price;
			$list['goods_desc'] = $item->goods_desc_qiniu;
			$list['goods_img'] = $item->goods_img_qiniu;
			$list['sh_category_id'] = $item->goods_category_id;
			$list['tmp_163_id'] = $item->wangyi_goods_id;
			// $list_arr[] = $list;
			$id_163 = $item->id;

			$goods_id = DB::table('goods')->insertGetId($list);
			$img_pic = DB::table('goods_163_pic')->where('goods_163_id' , $id_163)->get();
			
			foreach ($img_pic as $value) {

				DB::table('goods_pic')->insert(array('pic_url'=>$value->pic_url_qiniu,'sh_goods_id'=>$goods_id));

			}



			//var_dump($list_arr);
			
		}
		// var_dump($goods_list);
	}


	public function dataToWinRecord(){
		$goods_list = DB::table('goods')->get(); 

		foreach ($goods_list as $item) {
			$wangyi_goods_id = $item->tmp_163_id;
			$id = $item->id;
			$shaidan_list = DB::table('goods_163_winRecord')->where(array('wangyi_goods_id'=>$wangyi_goods_id))->get();

			if(!empty($shaidan_list)){
				foreach ($shaidan_list as $item) {
					$shaidan_arr = array();
					$user_list = array();
					$user_info = array();
					$user_list['nick_name'] = $item->nickname;
					$user_list['head_pic'] = $item->avatar_prefix;
					$user_list['is_real'] = 0;

					$user_id = DB::table('user')->insertGetId($user_list);

					$user_info['ip_address'] = $item->ip_address;
					$user_info['ip'] = $item->ip;
					$user_info['sh_user_id'] = $user_id;

					DB::table('user_info')->insert($user_info);

					$shaidan_arr['sh_user_id'] = $user_id;
					$shaidan_arr['content'] = $item->content;
					$shaidan_arr['title']   = $item->title;
					$shaidan_arr['sh_goods_id'] = $id;

					$shaidan_id = DB::table('user_shaidan')->insertGetId($shaidan_arr);
					$images = explode(',', $item->images);
					
					foreach ($images as $value) {
						$img_url = $value."l.jpg";
						DB::table('shaidan_img')->insert(array('sh_user_shaidan_id'=>$shaidan_id,'img_url'=>$img_url));

					}

				}
			}
		}
	}


	// 抓取1.163.com 一元夺宝商品数据

	public function spider_wangyi(){
	  	$imgsrc = "";
	  	$goods_tit = "";
	  	$goods_price = "";
	  	$url_arr = array();
	  	$index = 0;
	  	$url_arr = array();

	  	for($i=1;$i<=8;$i++){
	  		if($i != 7){
		  		$url = 'http://1.163.com/list/'.$i.'-0-1-1.html';
			  	$res = file_get_contents($url);

			  	preg_match_all('/\<div class=\"w-goods-pic\"\>(.*)\<\/div\>/Uis' , $res , $goods_url_arr);
			  	
			  	foreach ($goods_url_arr[1] as $key => $item) {
			  		$goods_desc_img = array();
			  		$index++;
			  		preg_match('/href=\"(.*)\"/Uis', $item , $goods_url);

				    $res = file_get_contents($goods_url[1]);
				   	preg_match_all('/class=\"w-gallery\".*(.*)\<\/ul\>/Uis', $res, $goods_img);
				   	preg_match('/src=\"(.*)\"/Uis',  $goods_img[1][0] , $imgsrc);
				   	preg_match_all('/src-big=\"(.*)\"/Uis',  $goods_img[1][0] , $bigsrc);


				   	$goods_img_arr = $bigsrc[1];

				   	preg_match_all('/\<h1.*\>.*(.*)\<\/h1\>/Uis' , $res , $goods_title);
				   	preg_match('/span\> (.*)\<span/Uis', $goods_title[1][1], $goods_tit);
				   	$goods_tit = $goods_tit[1];
				   	preg_match('/\<span class=\"txt-main\">(.*)\<\/span\>/Uis' , $res, $goods_price);
				   	$goods_price = $goods_price[1];

				   	preg_match('/\<div class=\"w-tabs-panel\"\>(.*)\<\/div\>/Uis', $res, $goods_desc_arr);
				   	preg_match_all('/src=\"(.*)\"/Uis', $goods_desc_arr[1] , $goods_desc);
				   	foreach ($goods_desc[1] as $key => $value){
				   		if($value != "http://mimg.127.net/p/t.gif"){
				   			$goods_desc_img[] = '<img src="'.$value.'" />';
				   		}
				   	}
				   	$goods_desc = implode(',', $goods_desc_img);

				   	preg_match('/gid : (.*),/Uis', $res, $wangyi_goods_id);
				   	$wangyi_goods_id = $wangyi_goods_id[1];

				   				   	
				   	$goods_category_id = $i == 8 ? 7 : $i;

				   	$lottery = new LotteryModel();
				   	$goods_data = array(
				   		'goods_name' => $goods_tit, 
				   		'goods_img'  => $imgsrc[1],
				   		'goods_desc' => $goods_desc,
				   		'is_real'    => 1,
				   		'market_price' => $goods_price,
				   		'wangyi_goods_id' => $wangyi_goods_id,
				   		'goods_category_id' => $goods_category_id

				   	);
				   	$goods_id = $lottery ->addGoodsInfo($goods_data);
				   	foreach ($goods_img_arr as $key => $value) {
				   		$res = $lottery -> addGoodsImg(array('goods_163_id' => $goods_id , 'pic_url' => $value));
				   	}

				   	//$res_comment = $this -> curlGet("http://1.163.com/share/getList.do?gid=895&pageSize=10&pageNum=1");
				   	//var_dump($res_comment);
				    echo '增加'.$index.'个商品';		  
				}
			}
		}
	}

	
	
}

