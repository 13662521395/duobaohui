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

class ClientModel extends Model {

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s');
    }

    public function addAppVersion($data) {
        $data['create_time'] = date('y-m-d H:i:s',time());
        $data['update_time'] = date('y-m-d H:i:s',time());
        $id = DB::table('app_version')->insertGetId($data);
        return $id;
    }

    public function getVersionList($osType,$pageinfo) {
//        $list = DB::select('SELECT create_time,version_number,version_desc,is_force,os_type,url FROM sh_duobaohui.sh_app_version where os_type=? order by create_time desc', array($osType));

        $list = DB::table('app_version')->where('os_type', $osType)->orderBy('create_time', 'desc')->paginate($pageinfo->length);
        return $list;
    }

    /**
     * 根据系统类型查询
     * @param $osType
     * @return mixed
     */
    public function getLatelyAppUrl($osType) {
        $list = DB::select('SELECT os_type,url FROM sh_app_version where os_type=? order by create_time desc limit 1', array($osType));
        return $list;
    }

    public function getVersionTotalNum($osType) {
        $res = DB::select('select count(*) sum from sh_app_version WHERE os_type=?', array($osType));
        return $res;
    }

	/*
	 * ios审核开关
	 */
	public function audit($iosAudit){
		DB::table('system_config')->update(array('ios_audit'=>$iosAudit));
	}

	public function getIOSAudit(){
		return DB::table('system_config')->pluck('ios_audit');
	}

}

