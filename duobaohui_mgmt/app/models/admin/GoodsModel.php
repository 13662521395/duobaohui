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

class GoodsModel extends Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');

	}

	public function getGoods($offset , $length , $search = array() ){
		$sql = DB::table('goods')
			->select('goods.id','goods.goods_name','goods.goods_img','goods.shop_price' , 'goods.market_price' , 'goods.sh_category_id' , 'category.cat_name')
			->leftjoin('category' , 'goods.sh_category_id' , '=' , 'category.id')
			->skip($offset)->take($length)
			->orderBy('goods.create_time' , 'desc')
			->where('goods.is_delete' , 0);

		if(isset($search['goods_name'])){
			$sql = $sql->where('goods_name' , 'like' , '%'.$search['goods_name'].'%');
		}
		if(isset($search['category_id'])){
			$sql = $sql->where('sh_category_id' , $search['category_id']);
		}

		$list = $sql->paginate(10);;
		return $list;
	}

	public function getGoodsTotalNum($search = array() ){
		$sql = DB::table('goods')
			->where('goods.is_delete' , 0);

		if(isset($search['goods_name'])){
			$sql = $sql->where('goods_name' , 'like' , '%'.$search['goods_name'].'%');
		}
		if(isset($search['category_id'])){
			$sql = $sql->where('sh_category_id' , $search['category_id']);
		}

		$num = $sql->count();
		return $num;
	}

	public function addGoods($data){
		$id = DB::table('goods')
				->insertGetId($data);
		if($id){ //商品分类数量
			DB::table('category')->where('id', $data['sh_category_id'])->increment('goods_num', 1);
		}
		return $id; 
	}

	public function addGoodsPic($goodsId , $pic){
		$data = array();	
		$i = 0;
		foreach($pic as $vi){
			$data[$i]['sh_goods_id'] = $goodsId;
			$data[$i]['pic_url']	 = $vi;
			$data[$i]['enabled']	 = 1;
			$data[$i]['type']		 = 1;
			$i++;
		}
		return DB::table('goods_pic')->insert($data);	
	}

	public function updateGoods($goods_id , $data){
		$old = DB::table('goods')->where('id' , $goods_id)->select('sh_category_id')->first();

		$res = DB::table('goods')
				->where('id' , $goods_id)
				->update($data);
		if($res && $old->sh_category_id!=$data['sh_category_id']){
			DB::table('category')->where('id', $data['sh_category_id'])->increment('goods_num', 1);
			DB::table('category')->where('id', $old->sh_category_id)->decrement('goods_num', 1);
		}
		return $res;
	}

	public function deleteGoods($goods_id){
		$old = DB::table('goods')->where('id' , $goods_id)->select('sh_category_id')->first();
		$res = DB::table('goods')
				->where('id',$goods_id)
				->update(array('is_delete'=>1));

		if($res){ //商品分类数量
			DB::table('category')->where('id', $old->sh_category_id)->decrement('goods_num', 1);
		}
		return $res;
	}

	/*
	 * 通过商品id获取商品信息
	 */
	public function getGoodsInfoById($goodsId){
		return DB::table('goods')
			->select('id','goods_name','goods_img','market_price' , 'shop_price' , 'goods_desc' , 'sh_category_id' , 'is_real' , 'purchase_url')
			->where('id' , $goodsId)
			->first();	
	}

	/* 
	 * 获取商品图片
	 */
	public function getGoodsPic($goodsId){
		return DB::table('goods_pic')->where('sh_goods_id' , $goodsId)->get();
	}

	/*
	 * 删除商品图片
	 */
	public function deleteGoodsPic($goodsId){
		return DB::table('goods_pic')->where('sh_goods_id' , $goodsId)->delete();
	}

}

