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

class NotifyModel extends Model {

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s');
    }

    /**
     * 获取尚未发送短信的中奖列表
     */
    public function getWinnerSmsList(){
        DB::update('UPDATE sh_sms_notify SET send_flag=1 WHERE send_flag=0 and is_real=1');
        $list = DB::select('SELECT id,user_id,tel,content,send_flag FROM sh_sms_notify where send_flag=1 and is_real=1');
        return $list;
    }

    /**
     * 获取客户端尚未推送中奖消息列表
     */
    public function getAppNotifyList(){
        DB::update('UPDATE sh_app_notify SET send_flag=1 WHERE send_flag=0 and is_real=1');
        $list = DB::select('SELECT id,user_id,send_flag FROM sh_app_notify where send_flag=1 and is_real=1');
        return $list;
    }

    public function updateAppNotify($data){
        return DB::table('app_notify')
            ->where('id', $data['id'])
            ->update(array(
                'type' => $data['type'],
                'ticker' => $data['ticker'],
                'title' => $data['title'],
                'text' => $data['text'],
                'after_open' => $data['after_open'],
                'send_time' => $this->nowDateTime,
                'send_flag' => $data['send_flag'],
                'alias' => $data['alias'],
                'alias_type' => $data['alias_type'],
                'error_code' => $data['error_code'],
                'error_msg' => $data['error_msg']
            ));
    }

    /**
     * 回写中奖短信的发送状态
     * @param $data
     * @return
     */
    public function updateSmsNotify($data){
        return DB::table('sms_notify')
            ->where('id', $data['id'])
            ->update(array(
                'send_time' => $this->nowDateTime,
                'send_flag' => $data['send_flag'],
            ));
    }
}

