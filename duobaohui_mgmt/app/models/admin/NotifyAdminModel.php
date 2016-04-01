<?php

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;

class NotifyAdminModel extends Model {

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s',time());
    }

    public function getAppNotifyList(){
        DB::update('UPDATE sh_app_notify SET send_flag=1 WHERE send_flag=0 and is_real=1 and msg_type="normal"');
        $list = DB::select('
            SELECT
                a.id,a.type,a.user_id,a.ticker,a.title,a.text,a.after_open,a.alias,a.alias_type,a.send_flag,a.msg_type,a.display_type
            FROM
                sh_app_notify a
            where a.send_flag=1
            and a.is_real=1
            and a.msg_type="normal"
	    ');
        return $list;
    }

    public function updateAppNotify($data){
        return DB::table('app_notify')
            ->where('id', $data['id'])
            ->update(array(
                'send_time' => $this->nowDateTime,
                'send_flag' => $data['send_flag'],
                'error_code' => $data['error_code'],
                'error_msg' => $data['error_msg']
            ));
    }

    public function addPushNotify($data){
        try {
            return DB::table('app_notify')->insertGetId(array(
                'type'=>$data['type'],
                'adddate'=>$data['adddate'],
                'user_id'=>$data['user_id'],
                'ticker'=>$data['ticker'],
                'title'=>$data['title'],
                'text'=>$data['text'],
                'after_open'=>$data['after_open'],
                'send_flag'=>$data['send_flag'],
                'is_real'=>$data['is_real'],
                'msg_type'=>$data['msg_type'],
                'alias_type'=>$data['alias_type'],
                'alias'=>$data['alias'],
                'display_type'=>$data['display_type']
            ));
        }catch(Exception $e) {
            Log::error($e);
        }
    }

    public function getUserRealFlagById($user_id){
        $res = DB::select('select is_real from sh_user where id = ? and is_real=1',array($user_id));
        return $res;
    }

    public function getWinNotifyTotalNum(){
        $res = DB::select('
            select
                count(*) sum
            from
                sh_period_result a
            inner join
                sh_activity_period b
            on
                a.sh_activity_period_id = b.id
            inner join
                sh_activity c
            on
                b.sh_activity_id = c.id
            inner join
                sh_goods d
            on
                c.sh_goods_id = d.id
            inner join
                sh_sms_notify e
            on
                e.sh_period_result_id = a.id
            inner join
                sh_app_notify f
            on
                f.sh_period_result_id = a.id
            inner join
                sh_user g
            on
                a.user_id = g.id
            where
                e.is_real = 1
            order by
                a.pre_luck_code_create_time desc;
        ');
        return $res;
    }

    /**
     * 中奖消息推送列表管理
     * @param $length
     * @param $app_status
     * @param $sms_status
     * @param $luck_code_create_time
     * @return mixed
     */
    public function getWinNotifyList($length,$app_status,$sms_status,$luck_code_create_time) {
        $table = DB::table('period_result AS a');
        $table->select(DB::raw("
            a.sh_activity_period_id,
            g.nick_name,
            b.period_number,
            d.goods_name,
            d.goods_img,
            a.pre_luck_code_create_time,
            CASE
                WHEN f.send_flag = '0' THEN '待推送'
                WHEN f.send_flag = '1' THEN '推送中'
                WHEN f.send_flag = '2' THEN '已推送'
                WHEN f.send_flag = '3' THEN '推送失败'
                WHEN f.send_flag = '4' THEN '状态未明'
            END app_send_flag,
            f.send_time app_send_time,
            CASE
                WHEN e.send_flag = '0' THEN '待发送'
                WHEN e.send_flag = '1' THEN '发送中'
                WHEN e.send_flag = '2' THEN '已发送'
                WHEN e.send_flag = '3' THEN '发送失败'
                WHEN e.send_flag = '4' THEN '状态未明'
            END sms_send_flag,
            e.send_time sms_send_time
        "))
            ->join('activity_period AS b' ,DB::raw('a.sh_activity_period_id'),'=',DB::raw('b.id'))
            ->join('activity AS c' ,DB::raw('b.sh_activity_id'),'=',DB::raw('c.id'))
            ->join('goods AS d' ,DB::raw('c.sh_goods_id'),'=',DB::raw('d.id'))
            ->join('sms_notify AS e' ,DB::raw('e.sh_period_result_id'),'=',DB::raw('a.id'))
            ->join('app_notify AS f' ,DB::raw('f.sh_period_result_id'),'=',DB::raw('a.id'))
            ->join('user AS g' ,DB::raw('a.user_id'),'=',DB::raw('g.id'))
            ->where( DB::raw('e.is_real'), '=' ,  '1');
            if($app_status != null && $app_status != ''){
                $table->where( DB::raw('f.send_flag'), '=' ,  $app_status);
            }
            if($sms_status != null && $sms_status != ''){
                $table->where( DB::raw('e.send_flag'), '=' ,  $sms_status);
            }
            if($luck_code_create_time != null && $luck_code_create_time != ''){
                $table->where( DB::raw("date_format(a.pre_luck_code_create_time,'%Y-%m-%d')"), '=' ,  $luck_code_create_time);
            }
            $table->orderBy('pre_luck_code_create_time', 'desc');

        $list = $table->paginate($length);
        return $list;
    }

}

