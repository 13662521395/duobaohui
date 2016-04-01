<?php
/**
 * Created by PhpStorm.
 * User: guoshijie
 * Date: 15/10/29
 * Time: 下午3:43
 */

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;

class FeedbackModel extends Model {

    private $nowDateTime;

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s',time());
    }

    public function getFeedbackList($pageinfo) {
        $list = DB::table('system_opinion')
            ->leftJoin('user', 'system_opinion.user_id', '=', 'user.id')
            ->select('system_opinion.id', 'system_opinion.content', 'system_opinion.create_time', 'system_opinion.user_id','user.tel')
            ->orderBy('system_opinion.create_time', 'desc')
            ->paginate($pageinfo->length);
        return $list;
    }

    public function getFeedbackTotalNum(){
        $res = DB::select('select count(*) sum from sh_system_opinion');
        return $res;
    }

}

