<?php
/**
 * 签到
 * Created by PhpStorm.
 * User: guoshijie
 * Date: 15/10/29
 * Time: 下午3:43
 */

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Session;

class SignModel extends Model {

    protected $table;

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s');
        $this->table = 'jnl_sign';
    }

    /**
     * 获取签到信息
     * @param $user_id
     * @return
     */
    public function getSign($user_id){
        $res = DB::table($this->table)->where('user_id', $user_id)->orderBy('create_time','desc')->first();
        return $res;
    }

    /**
     * 签到
     * @param $data
     * @return mixed
     */
    public function addSign($data){
        return DB::table($this->table)->insertGetId($data);
    }

}

