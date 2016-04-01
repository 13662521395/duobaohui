<?php
/**
* 活动
* @author wanghaihong@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model\Admin;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;

class ShaidanModel extends \Eloquent {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');

	}

	public function getReportShaiDanList($length){
		$list = DB::table('report_shaidan')
			->join('user_shaidan','report_shaidan.sh_shaidan_id','=','user_shaidan.id')
			->join('user' , 'user_shaidan.sh_user_id' , '=' , 'user.id')
			->select(
					'report_shaidan.sh_shaidan_id',
					'user.nick_name' ,
					'user_shaidan.title',
					'user_shaidan.content',
					'report_shaidan.report_num',
					DB::raw('sum(report_num) as sum')
			)
			->where('user_shaidan.is_delete' , 0)
			->groupBy('report_shaidan.sh_shaidan_id')
			->orderBy('report_shaidan.sh_shaidan_id' , 'desc')
			->paginate($length);

		if($list){
			foreach($list as $item){
				$shaidan_id = $item->sh_shaidan_id;
				$report_array = DB::select('
					SELECT
						b.content,
						a.report_num
					FROM
						sh_report_shaidan a
					inner join
						sh_report b
					on
						a.sh_report_id = b.id
					inner join
						sh_user_shaidan c
					on
						a.sh_shaidan_id = c.id
					where
						c.is_delete = 0
						and a.sh_shaidan_id = '.$shaidan_id.'
					order by a.report_num desc;
				');
				$item->report_array = $report_array;

				$shaidanIds = array();
				$shaidanIds[] = $shaidan_id;

				$imgUrl = DB::table('shaidan_img')
					->select('img_url')
					->whereIn('sh_user_shaidan_id' , $shaidanIds)
					->get();
				$imgArray = array();
				if($imgUrl){
					foreach ($imgUrl as $i) {
						$imgArray[] = $i->img_url;
					}
				}
				$item->img_url = $imgArray;
			}
			return $list;
		}else{
			return false;
		}

	}

	/*
	 * 获取已晒单的信息
	 * wuhui
	 */
	public function getShaidanList($offset,$length){
		$list = DB::table('user_shaidan')
			->leftjoin('user' , 'user_shaidan.sh_user_id' , '=' , 'user.id')
			->leftjoin('order_info' , 'user_shaidan.sh_order_id' , '=' , 'order_info.order_id')
			->leftjoin('goods' , 'user_shaidan.sh_goods_id' , '=' , 'goods.id')
			->leftjoin('period_result' , 'period_result.id' , '=' , 'order_info.sh_period_result_id')
			->select(
				'user_shaidan.id',
				'user.nick_name' , 
				'user.is_real' , 
				'user_shaidan.id' , 
				'user_shaidan.content' ,
				'user_shaidan.create_time',
				'user_shaidan.title' , 
				'order_info.order_sn',
				'goods.goods_name',
				'goods.goods_img',
				'period_result.luck_code_create_time',
				'goods.is_real as goods_is_real'
			)
			->where('user_shaidan.is_delete' , 0)
			->skip($offset)->take($length)
			->orderBy('id' , 'desc')
			->paginate(10);

		if(empty($list)){
			return false;
		}
		$shaidanIds = array();
		foreach($list as $lv){
			$shaidanIds[] = $lv->id;
		}

		$imgUrl = DB::table('shaidan_img')
			->select('id' , 'sh_user_shaidan_id' , 'img_url')
			->whereIn('sh_user_shaidan_id' , $shaidanIds)
			->get();

		foreach($list as $lv){
			$lv->img_url = array();
			foreach($imgUrl as $iv){
				if($iv->sh_user_shaidan_id == $lv->id){
					$lv->img_url[] = $iv->img_url; 
				}
			}
		}

		return $list;
	}

	/*
	 * 获取已晒单的总数
	 * wuhui
	 */
	public function getShaidanTotalNum(){
		$totalNum = DB::table('user_shaidan')->count();
		return $totalNum;
	}

	/*
	 * 获取虚拟用户中奖列表
	 * wuhui
	 */
	public function getWinUserByNotRealList(){
		$list = DB::table('period_result')
			->select('user.nick_name' , 'user.id' , 'goods.goods_name' , 'goods.goods_img' , 'period_result.luck_code_create_time' , 'order_info.order_sn' , 'period_result.id as period_result_id')
			->leftjoin('user' , 'user.id' , '=' , 'period_result.user_id')
			->leftjoin('activity_period' , 'activity_period.id' , '=' , 'period_result.sh_activity_period_id')
			->leftjoin('activity' , 'activity.id' , '=' , 'activity_period.sh_activity_id')
			->leftjoin('goods' , 'goods.id' , '=' , 'activity.sh_goods_id')
			->leftjoin('order_info' , 'order_info.sh_period_result_id' , '=' , 'period_result.id')
			->where('user.is_real' , 0)
			->where('order_info.is_shaidan' , 0)
			->orderBy('luck_code_create_time' , 'ASC')
			->paginate(10);
		return $list;
	}

	/*
	 * 根据时间获取虚拟用户中奖列表
	 *
	 */
	public function getWinUserByNotRealListByTime($startTime,$endTime){
		$list = DB::table('period_result')
			->select('user.nick_name' , 'user.id' , 'goods.goods_name' , 'goods.goods_img' , 'period_result.luck_code_create_time' , 'order_info.order_sn' , 'period_result.id as period_result_id')
			->leftjoin('user' , 'user.id' , '=' , 'period_result.user_id')
			->leftjoin('activity_period' , 'activity_period.id' , '=' , 'period_result.sh_activity_period_id')
			->leftjoin('activity' , 'activity.id' , '=' , 'activity_period.sh_activity_id')
			->leftjoin('goods' , 'goods.id' , '=' , 'activity.sh_goods_id')
			->leftjoin('order_info' , 'order_info.sh_period_result_id' , '=' , 'period_result.id')
			->where('user.is_real' , 0)
			->whereBetween('period_result.luck_code_create_time', array($startTime, $endTime))
			->where('order_info.is_shaidan' , 0)
			->orderBy('luck_code_create_time' , 'ASC')
			->paginate(10);
		return $list;
	}

	/*
	 * 获取中奖信息
	 */
	public function getPeriodResultById($periodResultId){
		$list = DB::table('period_result')
			->select('user.nick_name' , 'user.id' , 'goods.goods_name' , 'goods.goods_img' , 'period_result.luck_code_create_time' , 'order_info.order_sn' , 'order_info.order_id' ,'period_result.id as period_result_id' , 'goods.id as goods_id')
			->leftjoin('user' , 'user.id' , '=' , 'period_result.user_id')
			->leftjoin('activity_period' , 'activity_period.id' , '=' , 'period_result.sh_activity_period_id')
			->leftjoin('activity' , 'activity.id' , '=' , 'activity_period.sh_activity_id')
			->leftjoin('goods' , 'goods.id' , '=' , 'activity.sh_goods_id')
			->leftjoin('order_info' , 'order_info.sh_period_result_id' , '=' , 'period_result.id')
			->where('period_result.id' , $periodResultId)
			->first();
		return $list;
	}

	/*
	 * 获取虚拟用户中奖总数
	 */
	public function getWinUserByNotRealTotalNum(){
		return DB::table('period_result')
			->leftjoin('user' , 'user.id' , '=' , 'period_result.user_id')
			->where('user.is_real' , 0)
			->count();
	}

	/*
	 * 更新订单的晒单状态
	 */
	public function updateOrderShaidan($orderId){
		return DB::table('order_info')
			->where('order_id' , $orderId)
			->update(array('is_shaidan' => 1));
	}

	/*
	 * 保存晒单
	 */
	public function saveShaidan($data){
		return DB::table('user_shaidan')->insertGetId($data);
	}

	/*
	 * 保存晒单图片
	 */
	public function saveShaidanImg($shaidanId , $imgUrl){
		$data = array();	
		$i = 0;
		foreach($imgUrl as $vi){
			$data[$i]['sh_user_shaidan_id'] = $shaidanId;
			$data[$i]['img_url']			= $vi;
			$i++;
		}
		return DB::table('shaidan_img')->insert($data);
	}

	/*
	 * 删除晒单
	 */
	public function deleteShaidan($shaidanId){
		return DB::table('user_shaidan')->where('id' , $shaidanId)->update(array('is_delete' => 1));
	}

	/*
	 * 获取网易的晒单信息
	 */
	public function getShaidanByWangyi($goodsId , $offset , $length){
		$tmp_163_id = DB::table('goods')->select('tmp_163_id')->where('id' , $goodsId)->first();

		if(!$tmp_163_id){
			return false;
		}

		$wangyi = DB::table('goods_163_winrecord')
			->select('id' , 'nickname' , 'content' , 'title')
			->where('wangyi_goods_id' , $tmp_163_id->tmp_163_id)
			->skip($offset)->take($length)
			->get();

		if($wangyi){
			return $wangyi;
		}else{
			return false;
		}
	}

	/*
	 * 获取网易的晒单信息的总数
	 */
	
	public function getShaidanByWangyiTotalNum($goodsId){
		$tmp_163_id = DB::table('goods')->select('tmp_163_id')->where('id' , $goodsId)->first();

		if(!$tmp_163_id){
			return 0;
		}

		$wangyi = DB::table('goods_163_winrecord')
			->select('nickname' , 'content' , 'title')
			->where('wangyi_goods_id' , $tmp_163_id->tmp_163_id)
			->count();

		if($wangyi){
			return $wangyi;
		}else{
			return 0;
		}
	}

	/*
	 * 通过Id获取晒单信息
	 */
	public function getShaidanInfo($shaidanId){
		$info = DB::table('user_shaidan')
			->leftjoin('user' , 'user_shaidan.sh_user_id' , '=' , 'user.id')
			->leftjoin('order_info' , 'user_shaidan.sh_order_id' , '=' , 'order_info.order_id')
			->leftjoin('goods' , 'user_shaidan.sh_goods_id' , '=' , 'goods.id')
			->select(
				'user_shaidan.id',
				'user.id as user_id',
				'user.nick_name' , 
				'user.is_real' , 
				'user_shaidan.id' , 
				'user_shaidan.content' , 
				'user_shaidan.title' , 
				'order_info.order_sn',
				'order_info.order_id',
				'order_info.sh_period_result_id',
				'goods.id as goods_id',
				'goods.goods_name',
				'goods.goods_img',
				'goods.is_real as goods_is_real'
			)
			->where('user_shaidan.id' , $shaidanId)
			->first();

		if( !$info ){
			return false;
		}

		$imgUrl = DB::table('shaidan_img')
			->select('id' , 'sh_user_shaidan_id' , 'img_url')
			->where('sh_user_shaidan_id' , $shaidanId)
			->get();

		$info->img_url = array();
		if(!empty($imgUrl)){
			$info->img_url = $imgUrl;
		}

		return $info;
	}

	/*
	 * 更新晒单类容
	 */
	public function updateShaidan($shaidanId , $data){
		return DB::table('user_shaidan')->where('id' , $shaidanId)->update($data);
	}

}

