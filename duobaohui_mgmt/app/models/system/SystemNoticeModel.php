<?php
/**
 * 系统通知
 * @author guoshijie@shinc.net
 * @version v1.0
 * @copyright shinc
 */

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;


class SystemNoticeModel extends Model {
    private $nowDateTime;

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s');
    }

    public function addSystemNotice($title,$content){
        $res = DB::table('system_notice')->insertGetId(array(
            'title' => $title,
            'content' => $content,
            'create_time' => $this->nowDateTime,
            'update_time' => $this->nowDateTime,
            'status' => '1'
        ));
        return $res;
    }

    public function updateSystemNotice($title,$content,$noticeId){
        $res = DB::table('system_notice')->where('id', $noticeId)->update(array(
            'title' => $title,
            'content' => $content,
            'update_time' => $this->nowDateTime
        ));
        return $res;
    }

    public function getSystemNoticeList($length){
//        $list = DB::select('
//            select
//              id,
//              create_time,
//              update_time,
//              title,
//              content
//            from
//              sh_system_notice
//            WHERE
//              status = "1"
//            order by create_time desc
//        ');

        $table = DB::table('system_notice');
        $table -> select(DB::raw("
              id,
              create_time,
              update_time,
              title,
              content
        "))
            ->where( DB::raw('status'), '=' ,  '1');
        $table->orderBy('create_time', 'desc');

        $list = $table->paginate($length);
        return $list;
    }

    public function delSystemNotice($noticeId){
        return DB::table('system_notice')->where('id', $noticeId)->update(array('status' => '0'));
    }

    public function getSystemNoticeById($noticeId){
        return DB::table('system_notice')->where('id', $noticeId)->first();
    }

}
