<?php

/**
 * @author		wangkenan@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Admin;

use AdminController;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Redirect;

use Illuminate\Support\Facades\Config;

use Laravel\Model\GoodsModel;
use Laravel\Model\Admin\CategoryModel;

use  App\Libraries\Qiniu\Auth;              //引入七牛

class GoodsController extends AdminController {
	private $_length = 20;

	public function __construct(){
		parent::__construct();
	}

	public function getDeleteGoods(){
		if( !Input::has('goods_id') ){
			return Response::json( $this->response( '10005' ) );
		}
		$goods_id = Input::get('goods_id');
		$goods = new GoodsModel();
		$list = $goods->deleteGoods($goods_id);
		if($list){
			$list = array('code' => '1' , 'message' => '删除成功');
		}else{
			$list = array('code' => '0' , 'message' => '删除失败');

		}
		// $list = json_decode($list);
		return $list;
	}

    public function getList(){
		$data = array();
		$goods = new GoodsModel();
		

		$data['goods_name'] = '';
		$data['category_id'] = 0;

		$search = array();
		if(Input::has('goods_name') && !empty(Input::get('goods_name'))){
			$search['goods_name']	= Input::get('goods_name');
			$data['goods_name']		= $search['goods_name'];
		}
		if(Input::has('category_id') && Input::get('category_id') != 0 ){
			$search['category_id']	= Input::get('category_id');
			$data['category_id']	= $search['category_id'];
		}

		$totalNum = $goods->getGoodsTotalNum($search);

		$pageinfo = $this->_pageInfo($this->_length , $totalNum);
		
		$categoryModel = new CategoryModel();
		$category = $categoryModel->getGoodsCategoryList();

		$list = $goods->getGoods($pageinfo->offset , $pageinfo->length , $search);

		$data['list']		= $list;
		$data['category']	= $category;
		$data['pageInfo']	= $pageinfo;
		$data['selected']	= "goods";

        return Response::view('admin.goods.list' , $data);
	}

	public function getAddGoods(){
		
		$categoryModel = new CategoryModel();

		$category = $categoryModel->getGoodsCategoryList();

		$data = array();		
		$data['selected']	= "goods";
		$data['msg']		= "";
		$data['category']	= $category;
		return Response::view('admin.goods.addGoods' , $data);
	
	}

	/*
	 * 添加商品
	 */
	public function postSaveGoods(){
		if( !Input::has('category_id') || !Input::has('goods_name') || !Input::has('img_url') || !Input::has('description') || !Input::has('shop_price')){
			return Redirect::to('/admin/goods/add-goods');
		}

		$data = array();

		$data['sh_category_id']		= Input::get('category_id');
		$data['goods_name']			= Input::get('goods_name');

		//这里因为可以提交多个图片,但暂时只用一个
		$goodsImg					= Input::get('img_url');
		$data['goods_img']			= $goodsImg[0];

		$data['goods_desc']			= Input::get('description');
		$data['shop_price']			= Input::get('shop_price');

		$data['is_real']			= 1;
		if( Input::has('is_real')){
			$data['is_real']		= Input::get('is_real');
		}

		$data['market_price']		= 0;
		if( Input::has('market_price')){
			$data['market_price']	= Input::get('market_price');
		}

		$data['purchase_url']		= 0;
		if( Input::has('purchase_url')){
			$data['purchase_url']	= Input::get('purchase_url');
		}

		$data['create_time']		= time();

		$goodsModule = new GoodsModel();

		$result = $goodsModule->addGoods($data);

		if($result){
			$goodsModule->addGoodsPic($result , $goodsImg);
		}

		$returnData['selected'] = "goods";
		if($result){
			return Redirect::to('/admin/goods/add-goods');
		}else{
			return Redirect::to('/admin/goods/add-goods');
		}
	}

	/*
	 * 修改商品
	 */
	public function getEditGoods(){
		if( !Input::has('goods_id') ){
			return Redirect::to('/admin/goods/list');
		}

		
		$categoryModel = new CategoryModel();
		$category = $categoryModel->getGoodsCategoryList();

		$goodsId	= Input::get('goods_id');
		$goods		= new GoodsModel();
		$info		= $goods->getGoodsInfoById($goodsId);

		$pic		= $goods->getGoodsPic($goodsId);
		
		$data['selected']	= "goods";
		$data['msg']		= "";
		$data['info']		= $info;
		$data['category']	= $category;

		$data['pic']		= $pic;
		return Response::view('admin.goods.edit' , $data);
		
	}
	/*
	 * 保存修改数据
	 */
	public function postUpdateGoods(){
		if( !Input::has('goods_id') ){
			return Redirect::to('/admin/goods/list');
		}

		$goodsId = Input::get('goods_id');

		$data = array();

		if( Input::has('category_id') ){
			$data['sh_category_id']		= Input::get('category_id');
		}

		if( Input::has('goods_name') ){
			$data['goods_name']			= Input::get('goods_name');
		}

		$goodsImg = array();
		if( Input::has('img_url') ){
			//这里因为可以提交多个图片,但暂时只用一个
			$goodsImg					= Input::get('img_url');
			$data['goods_img']			= $goodsImg[0];
		}

		if( Input::has('description') ){
			$data['goods_desc']			= Input::get('description');
		}

		if( Input::has('shop_price') ){
			$data['shop_price']			= Input::get('shop_price');
		}

		if( Input::has('is_real')){
			$data['is_real']		= Input::get('is_real');
		}

		if( Input::has('market_price')){
			$data['market_price']	= Input::get('market_price');
		}
		if( Input::has('purchase_url')){
			$data['purchase_url']	= Input::get('purchase_url');
		}

		$data['update_time']		= date('Y-m-d H:i:s' , time());

		$goodsModule = new GoodsModel();

		$result = $goodsModule->updateGoods($goodsId , $data);

		if(!empty($goodsImg)){
			$goodsModule->deleteGoodsPic($goodsId);
			$goodsModule->addGoodsPic($goodsId , $goodsImg);
		}

		$returnData['selected'] = "goods";

		return Redirect::to('/admin/goods/edit-goods?goods_id='.$goodsId);
	}

	
	private function _pageInfo($length=40 , $totalNum){
		$pageInfo               = new \stdClass;
		$pageInfo->length       = Input::has('length') ? Input::get('length') : $length;;
		$pageInfo->page         = Input::has('page') ? Input::get('page') : 0;
		$pageInfo->offset		= $pageInfo->page<=1 ? 0 : ($pageInfo->page-1) * $pageInfo->length;
		$pageInfo->totalNum     = $totalNum; 
		$pageInfo->totalPage    = ceil($pageInfo->totalNum/$pageInfo->length);

		return $pageInfo;
	}

	/*
	 * 上传商品详情图片
	 */
	public function postUmeditorImg(){
		//$bucket		= Config::get('qiniu_backup');
        //$accessKey	= Config::get('qiniu_accessKey');
        //$secretKey	= Config::get('qiniu_secretKey');
		
		//$expires	= Config::get('qiniu_expires');
		//$key		= 'sh_' + time() . mt_rand(1000 , 9999);

        //$qiniu  = new Auth($accessKey , $secretKey);

        //$upload = $qiniu->uploadToken($bucket, $key, $expires);		
		
		$upKey = 'upfile';

		$img = Input::file($upKey);

		$key		= 'sh_' + time() . mt_rand(1000 , 9999);

		$folder		= '/upload/img/';
		$destinationPath = $_SERVER['DOCUMENT_ROOT'] . $folder;


		if( Input::hasFile($upKey) ){
			if( Input::file($upKey)->isValid() ){
				$extension	= Input::file($upKey)->getClientOriginalExtension();
				$filename	= $key . '.' . $extension;
				
				$fileSize	= Input::file($upKey)->getSize();

				if(Input::file($upKey)->move($destinationPath , $filename)){
					$data = array(
						"originalName" => $filename ,
						"name" => $filename ,
						"size" => $fileSize ,
						"type" => $extension ,
						'url' => '../../..'.$folder . $filename,
						'state' => 'SUCCESS'
					);
					return Response::make(json_encode($data));
				}
			}
		}
		return Response::json(array())->header('status' , '500');

	}

	/*
	 * 商品分类列表
	 */
	public function getCategory(){
		$categoryModel = new CategoryModel();
		$list = $categoryModel->getGoodsCategoryList();
		$data['list'] = $list;
		return Response::view('admin.goods.category' , $data);
	}

	/*
	 * 添加分类
	 */
	public function anyCategoryAdd(){
		$categoryModel = new CategoryModel();
		if( !Input::has('cat_name')){
			$data = array();
			if(Input::has('pid')){
				$pid = Input::get('pid');
				$data['parent'] = $categoryModel->getGoodsCategoryDetail($pid);
			}
			return Response::view('admin.goods.category_add' ,$data);
		}

		$newData['cat_name']	= Input::get('cat_name');
		$newData['description'] = Input::get('description');
		if(Input::has('pid')){
			$newData['pid']			= Input::get('pid');
		}
		if(Input::has('img_url')){
			$imgUrl					= Input::get('img_url');
			$newData['img_url']		= $imgUrl[0];
		}

		$newId = $categoryModel->addGoodsCategory($newData);
		return Redirect::to('/admin/goods/category');
	}

	/*
	 * 编辑分类
	 */
	public function anyCategoryEdit(){
		if( !Input::has('id')){
			return Response::view('admin.goods.category_edit' , array('msg'=>'id错误'));
		}
		$categoryModel = new CategoryModel();
		if( !Input::has('cat_name')){
			$id = Input::get('id');
			$data['detail'] = $categoryModel->getGoodsCategoryDetail($id);
			return Response::view('admin.goods.category_edit' , $data);
		}

		$newData['id']			= Input::get('id');
		$newData['cat_name']	= Input::get('cat_name');
		$newData['description'] = Input::get('description');
		if(Input::has('img_url')){
			$imgUrl					= Input::get('img_url');
			$newData['img_url']		= $imgUrl[0];
		}

		$newId = $categoryModel->editGoodsCategory($newData);

		return Redirect::to('/admin/goods/category-edit');
	}

	/*
	 *
	 */
	public function anyCategoryDel(){
		if( !Input::has('id') ){
			return Response::json( $this->response( '10005' ) );
		}
		$id = Input::get('id');
		$categoryModel = new CategoryModel();
		$isD = $categoryModel->delGoodsCategory($id);
		if($isD===false){
			$response = $this->response(0,'删除失败，请重试');
		}elseif($isD===0){
			$response = $this->response(0,'有子类存在，请先删除子类');
		}else{
			$response = $this->response(1,'删除成功');
		}
		return Response::json( $response );
	}

}
