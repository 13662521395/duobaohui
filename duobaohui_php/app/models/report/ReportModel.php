<?php
/**
 * 晒单举报
 * @author guoshijie@shinc.net
 * @version v1.0
 * @copyright shinc
 */

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;


class ReportModel extends Model {
    private $nowDateTime;

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s');
    }

    /**
     * @param $array
     * @return mixed
     */
    public function addReportShaiDan($array){
        $res = DB::table('report_shaidan')->insert(array(
            'sh_shaidan_id' => $array['shaidan_id'],
            'sh_report_id' => $array['report_id'],
            'report_user_id' => $array['user_id'],
            'report_num' => 1,
            'create_time' => $this->nowDateTime,
            'update_time' => $this->nowDateTime,
            ));
        return $res;
    }

    public function getReportShaiDan($shaidan_id,$report_id){
        $res = DB::select('select 1 from sh_report_shaidan where sh_shaidan_id=? and sh_report_id = ?',array($shaidan_id,$report_id));
        return $res;
    }

    public function updateReportShaiDan($shaidan_id,$report_id,$user_id){
        $res = DB::table('report_shaidan')
            ->where('sh_report_id', $report_id)
            ->where('sh_shaidan_id', $shaidan_id)
            ->increment('report_num', 1, ['update_time' => $this->nowDateTime,'report_user_id'=>$user_id]);
        return $res;
    }



}
