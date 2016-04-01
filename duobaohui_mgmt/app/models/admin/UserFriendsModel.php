<?php
/**
 * 活动
 * @author wanghaihong@shinc.net
 * @version v1.0
 * @copyright shinc
 */

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;

class UserFriendsModel extends Model {

    private $nowDateTime;

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s',time());
    }

    /**
     * 查询邀请好友数量排行
     * @param $_length
     * @return
     */
    public function getUserFriendsNumList($_length){
        $table = DB::table('user_friends AS a');
        $table->select(DB::raw("
            b.id,
            b.nick_name,
            b.head_pic,
            b.tel,
            count(a.tel) sum
        "))
            ->join('user AS b', DB::raw('a.sh_user_id'), '=', DB::raw('b.id'))
            ->join('user AS c', DB::raw('a.tel'), '=', DB::raw('c.tel'))
            ->where(DB::raw('b.is_real'), '=' ,  1)
            ->groupBy(DB::raw('b.nick_name'));

        $table->orderBy(DB::raw('sum'), 'desc');
        $list = $table->paginate($_length);
        return $list;
    }

    /**
     * 每日邀请数量列表
     * @param $_length
     * @param $date
     * @return
     */
    public function getUserFriendsNumListByDate($_length,$date){
        $afterDay = date("Y-m-d",strtotime("$date +1 day"));
        $table = DB::table('user_friends AS a');
        $table->select(DB::raw("
            b.id,
            b.nick_name,
            b.head_pic,
            b.tel,
            count(a.tel) sum
        "))
            ->join('user AS b', DB::raw('a.sh_user_id'), '=', DB::raw('b.id'))
            ->join('user AS c', DB::raw('a.tel'), '=', DB::raw('c.tel'))
            ->where(DB::raw('b.is_real'), '=' ,  1)
            ->where(DB::raw('a.create_time'), '>', DB::raw('concat(date_format("'.$date.'", "%Y-%m-%d"), " 00:00:00")'))
            ->where(DB::raw('a.create_time'), '<', DB::raw('concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00")'))
            ->groupBy(DB::raw('b.nick_name'));

        $table->orderBy(DB::raw('sum'), 'desc');
        $list = $table->paginate($_length);
        return $list;
    }

}

