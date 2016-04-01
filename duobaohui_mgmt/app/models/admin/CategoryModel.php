<?php
/**
* 活动
* @author wuhui@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model\Admin;

use Illuminate\Support\Facades\DB;
use Laravel\Model\Admin\CategoryHelperModel AS CategoryHM;

class CategoryModel extends \Laravel\Model\Model {

	public function __construct(){
	}
	
	/*
	 * 根据parent_id获取分类列表
	 */
	public function getCategoryListByPid($pid = 0){
		return DB::table('category')->where('pid' , $pid)->get();	
	}

	/*
	 * 通过分类ID获取产品列表
	 */
	public function getGoodsInfoByCatId($categoryId = 1){
		return DB::table('goods')
			->select('id','goods_name')
			->where('sh_category_id' , $categoryId)
			->where('is_delete','0')
			->get();
	}

	/*
	 *
	 */
	public function getGoodsCategoryList(){
		// 此处排序。必须加上level=DESC, id=ASC, 然后传入CategoryHM
		$list = DB::table('category')->select('*')->orderby('level', 'DESC')->orderby('sort', 'ASC')->orderby('id', 'ASC')->get();
		$list = CategoryHM::getList($list);
		return $list;
/*
		return DB::table('category as a')
			->select(DB::raw('a.id category_id, a.cat_name, b.id goods_id, b.goods_name'))
			->leftJoin('goods as b',DB::raw('b.sh_category_id'),'=',DB::raw('a.id'))
			->get();
 */
	}

	/*
	 * 新增商品分类
	 */
	public function addGoodsCategory($data){
		$newId = CategoryHM::addC($data, 'category');
		return $newId;
	}

	/*
	 * 编辑商品分类
	 */
	public function editGoodsCategory($data){
		return CategoryHM::editC($data, 'category');
	}

	public function delGoodsCategory($id){
		$isD = CategoryHM::delC($id, 'category');
		if($isD===0){ // 有子类存在，请先删除子类;
		}
		return $isD;
	}

	public function getGoodsCategoryDetail($id){
		$detail = CategoryHM::getDetail($id, 'category');
		if($detail->pid>0){
			$detail->parent = CategoryHM::getDetail($detail->pid, 'category');
		}
		return $detail;
	}

}

