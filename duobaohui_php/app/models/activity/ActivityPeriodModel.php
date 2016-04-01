<?php

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;

/**
 *
 *
 */
class ActivityPeriodModel extends Model {
	public function __construct(){
		$this->init();
		$this->table = 'activity_period';
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');

	}

	/**
	 *	更新某期当前期数，并判断是否成功
     */
	public function updatePeriodTimesWithCheck($period_id,$num) {
		$result =  DB::table('activity_period')
			->where('id',$period_id)
			->whereRaw("real_need_times - current_times >= {$num}")
			->increment('current_times',$num);
		if(empty($result) || $result < 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 增加期和用户的关系
	 */
	public function addPeriodUser($data) {
		return DB::table('period_user')->insertGetId($data);

	}
	public function loadPeriodDetail($period_id) {
		$sql = "SELECT
				a.id period_id,
				a.period_number,
				a.current_times,
				a.real_need_times,
				a.create_time,
				a.real_price,
				a.sh_activity_id,
				b.sh_goods_id,
				c.goods_name
			FROM
				sh_activity_period a
					LEFT JOIN
				sh_activity b ON b.id = a.sh_activity_id
					LEFT JOIN
				sh_goods c ON c.id = b.sh_goods_id
			WHERE
				a.id = {$period_id}";
		return DB::selectOne($sql);
	}

}

