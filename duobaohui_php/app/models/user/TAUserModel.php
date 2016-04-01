<?php
/**
* TA用户信息查询
*
* @author zhangtaichao
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;


class TAUserModel extends Model {

    public function countDuobaoRecords($user_id,$recentDays = 30) {
        $builder = DB::table('period_user');
        $builder= $builder->newQuery();

        $count = $builder->selectRaw("COUNT(*) total
			FROM
				(SELECT
					1
				FROM
					sh_period_user a
				JOIN sh_activity_period b ON b.id = a.sh_activity_period_id
				JOIN sh_activity c ON c.id = b.sh_activity_id
				JOIN sh_goods d ON d.id = c.sh_goods_id
				LEFT JOIN sh_period_result e ON e.sh_activity_period_id = a.sh_activity_period_id
				LEFT JOIN sh_user f ON f.id = e.user_id
				WHERE
					a.create_time > DATE_SUB(CURDATE(), INTERVAL {$recentDays} DAY)
						AND a.user_id = {$user_id}
				GROUP BY a.sh_activity_period_id
				) t
			")->first();
        return $count;
    }
	/**
	 * 查询用户夺宝记录
	 * @param $user_id
	 * @param $recentDays 最近记录的天数
     */
	public function duobaoRecords($user_id,$recentDays = 30,$pageSize = 10) {
		if(empty($user_id)) {
			return null;
		}
		$builder = DB::table('period_user');
		$builder= $builder->newQuery();
		$res = $builder->selectRaw("
				a.user_id,
				d.goods_name,
				d.goods_img,
				a.sh_activity_period_id period_id,
				SUM(a.times) times,
				b.period_number,
				b.real_need_times,
				b.current_times,
				b.real_need_times - b.current_times remain_times,
				FLOOR((b.current_times / b.real_need_times) * 100) progress,
				b.flag,
				e.id result_id,
				e.user_id result_user_id,
				e.luck_code,
				e.luck_code_create_time,
				f.nick_name result_user_name,
				(SELECT
						SUM(times)
					FROM
						sh_period_user g
					WHERE
						g.user_id = e.user_id
							AND g.sh_activity_period_id = e.sh_activity_period_id) result_user_sum
			FROM
				sh_period_user a
					JOIN
				sh_activity_period b ON b.id = a.sh_activity_period_id
					JOIN
				sh_activity c ON c.id = b.sh_activity_id
					JOIN
				sh_goods d ON d.id = c.sh_goods_id
					LEFT JOIN
				sh_period_result e ON e.sh_activity_period_id = a.sh_activity_period_id and e.pre_luck_code_create_time < now()
					LEFT JOIN
				sh_user f ON f.id = e.user_id
			WHERE
				a.create_time > DATE_SUB(CURDATE(), INTERVAL {$recentDays} DAY)
					AND a.user_id = {$user_id}
			GROUP BY a.sh_activity_period_id
			ORDER BY a.create_time DESC

		")->paginate($pageSize);
		return $res;

	}

    public function countWinningRecords($user_id) {
        $builder = DB::table('period_user');
        $builder= $builder->newQuery();
        $count = $builder->selectRaw("
                count(1) total
            FROM
                sh_period_result a
                    JOIN
                sh_activity_period b ON b.id = a.sh_activity_period_id
                    JOIN
                sh_activity c ON c.id = b.sh_activity_id
                    JOIN
                sh_goods d ON d.id = c.sh_goods_id
            WHERE
                a.user_id = {$user_id}
                and a.pre_luck_code_create_time < now()

            ")->first();
        return $count;
    }
	/**
	 * 查询中奖纪录
	 * @param $user_id
	 * @param int $pageSize
	 * @return null
	 */
	public function winningRecords($user_id,$pageSize=10) {
		if(empty($user_id)) {
			return null;
		}
		$builder = DB::table('period_user');
        $builder= $builder->newQuery();
		$res = $builder->selectRaw("
			a.user_id,
			d.goods_name,
			d.goods_img,
			b.real_need_times,
			a.luck_code,
			a.luck_code_create_time,
			a.pre_luck_code_create_time,
			(select sum(times) from sh_period_user where user_id = a.user_id and sh_activity_period_id = a.sh_activity_period_id) total_times
		FROM
			sh_period_result a
				JOIN
			sh_activity_period b ON b.id = a.sh_activity_period_id
				JOIN
			sh_activity c ON c.id = b.sh_activity_id
				JOIN
			sh_goods d ON d.id = c.sh_goods_id
		WHERE
			a.user_id = {$user_id}
			and a.pre_luck_code_create_time < now()

		")->paginate($pageSize);
        return $res;
	}

	/**
	 * @param $period_id
	 * @param $user_id
	 * @return mixed
	 */
	public function duobaoCode($period_id,$user_id) {
		return DB::table('activity_code')
			->where('activity_period_id',$period_id)
			->where('user_id',$user_id)
			->select('code_num','create_time')
			->get();
	}

    public function countShandan($user_id) {
        return DB::table('user_shaidan')
            ->where('sh_user_id',$user_id)
			->where('is_delete',0)
            ->count();
    }
}
