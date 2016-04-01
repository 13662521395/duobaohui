<?php
/**
 * by zhangtaichao
 * 后台管理 活动 模型
 */

namespace Laravel\Model\Admin;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;//引入日志类

class AutoPaySettingModel extends \Laravel\Model\Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');
	}


	/**
	 * @return mixed
	 */
	public function getSettingList(){
		$data = DB::table('autopay_main_setting as ams')
			->select(DB::raw("
				ams.id as sh_autopay_main_setting_id,
				ams.name,
				ams.frequency,
				ams.flag,
				ams.scope_and_rate,
				ams.create_time,
				ams.modify_time,
				count(aas.id) as activity_counts,
				sum(a.need_times) as all_need_times"))
//			->sum('a.need_times as all_need_times')
//			->count('aas.id as activity_counts')
			->leftJoin('autopay_activity_setting as aas',DB::raw('ams.id'),'=',DB::raw('aas.sh_autopay_main_setting_id'))
			->leftJoin('activity as a',DB::raw('a.id'),'=',DB::raw('aas.sh_activity_id'))
			->groupBy(DB::raw('ams.id'))
			->latest(DB::raw('modify_time'))->paginate(10);
		return $data;
	}
//	/**
//	 * @return mixed
//	 */
//	public function getSettingActivityList($sh_autopay_main_setting_id){
//		$data = DB::table('autopay_activity_setting AS '.DB::getTablePrefix(). 'aas')
//			->leftJoin('activity AS '.DB::getTablePrefix(). 'a' ,'a.id','=','aas.sh_activity_id')
//			->leftJoin('goods AS '.DB::getTablePrefix(). 'g' ,'g.id','=','a.sh_goods_id')
//			->where( 'a.is_online' ,  '1')
//			->where( 'aas.sh_autopay_main_setting_id' ,  $sh_autopay_main_setting_id)
//			->select(
//				'aas.id',
//				'aas.sh_autopay_main_setting_id',
//				'aas.sh_activity_id',
//				'aas.create_time',
//				'a.need_times',
//				'g.goods_name')
//			->latest('aas.create_time')->paginate(1);
//		return $data;
//	}

	public function getActivityList($sh_autopay_main_setting_id,$need_times_min,$need_times_max,$activity_name){
		$table = DB::table('activity as a ');
		$table->select(DB::raw("
			a.id as sh_activity_id,
			a.current_period,
			a.sh_goods_id,
			b.goods_name,
			a.max_period,
			a.price,
			DATE_FORMAT(a.creat_time, '%Y-%m-%d %T') create_time,
			DATE_FORMAT(a.begin_date, '%Y-%m-%d') begin_date,
			DATE_FORMAT(a.end_date, '%Y-%m-%d') end_date,
			DATE_FORMAT(a.update_time, '%Y-%m-%d %T') update_time,
			a.need_times,
			a.is_online,
			a.is_auto,
			a.is_hot,
			a.user_id,
			aas.sh_autopay_main_setting_id,
			aas.id as sh_autopay_activity_setting_id,
			ams.name
		"))
		->leftJoin('goods as b',DB::raw('b.id'),'=',DB::raw('a.sh_goods_id'))
		->leftJoin('autopay_activity_setting as aas' , function($join) use ( $sh_autopay_main_setting_id )
			{
				$join->on(DB::raw('a.id'), '=', DB::raw('aas.sh_activity_id'))
					->on(DB::raw('aas.sh_autopay_main_setting_id'), '=', DB::raw($sh_autopay_main_setting_id));
			})
		->leftJoin('autopay_main_setting as ams',DB::raw('ams.id'),'=',DB::raw('aas.sh_autopay_main_setting_id'))
		->latest(DB::raw('aas.sh_autopay_main_setting_id'),DB::raw('a.id'));
		if($need_times_min != null){
			$table->where( DB::raw('a.need_times'), '>=' ,  $need_times_min);
		}
		if($need_times_max != null){
			$table->where( DB::raw('a.need_times'), '<=' ,  $need_times_max);
		}
		if($activity_name != null){
			$table->where( DB::raw('b.goods_name'), 'like' ,  '%'.$activity_name.'%');
		}
		$result = $table->paginate(10);
		return $result;
	}

	public function addActivitySetting($sh_autopay_main_setting_id,$sh_activity_id){
		$object = DB::table('autopay_activity_setting as aas')->select(DB::raw("
				aas.id,
				ams.name
			"))
			->leftJoin('autopay_main_setting as ams',DB::raw('aas.sh_autopay_main_setting_id'),'=',DB::raw('ams.id'))
			->where(DB::raw("aas.sh_activity_id"),$sh_activity_id)
			->first();
		if($object != null){
			$result['code']='0';
			$result['name']=$object->name;
			return $result;
		}else{
			DB::table('autopay_activity_setting')->insertGetId(array(
				'sh_autopay_main_setting_id'=>$sh_autopay_main_setting_id,
				'sh_activity_id'=>$sh_activity_id,
				'create_time'=>date('Y-m-d H:i:sa', time())
			));
			$result['code']='1';
			return $result;
		}
	}

	public function deleteActivitySetting($sh_autopay_activity_setting_id){
		return DB::table('autopay_activity_setting')
			->where('id' , $sh_autopay_activity_setting_id)
			->delete();
	}

	public function getAutoPaySettingDetail($sh_autopay_main_setting_id){
		$list = DB::table('autopay_main_setting as ams')->select(DB::raw("
			ams.id,
			ams.name,
			ams.frequency,
			ams.flag,
			ams.scope_and_rate,
			ams.create_time,
			ams.modify_time
		"))
		->where(DB::raw("ams.id"),$sh_autopay_main_setting_id)
		->first();
		return $list;
	}

	public function updateAutoPaySetting($sh_autopay_main_setting_id,$para_array){
		$table  = DB::table('autopay_main_setting AS '.DB::getTablePrefix().'ams' );
		$table	->where( 'ams.id', '=' ,  $sh_autopay_main_setting_id);
		$table	->update($para_array);

		return $table;
	}

}

