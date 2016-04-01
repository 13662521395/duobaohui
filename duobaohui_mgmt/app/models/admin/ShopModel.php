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
use Illuminate\Support\Facades\Log;

class ShopModel extends Model {

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s',time());
    }

    public function adjustDbSum(){
        $sum = 0;
        $list = DB::select('SELECT * FROM sh_shop order by id asc');
        foreach ($list as $item) {
            $shop_id = $item->id;
            $db_sum = DB::select('
                select sum(d.num) sum
                from sh_shop_tel a
                join sh_user b on a.sh_user_tel = b.tel
                join sh_jnl_trans c on c.user_id = b.id
                join sh_jnl_trans_duobao d on c.jnl_no = d.trans_jnl_no
                where 1 = 1
                and a.sh_shop_id = '.$shop_id.'
                and b.is_real=1
                and b.is_delete = 0
                and c.jnl_status = 4
                and c.trans_code = "duobao"
                and d.status = 1
                group by a.sh_shop_id;
            ');
            $db_num = $db_sum?$db_sum[0]->sum:0;

            $param_array = array(
                'duobao_sum' => $db_num,
            );

            $res = DB::table('shop')
                ->where('id', $shop_id)
                ->update($param_array);
            $sum += $res;
        }
        return $sum;
    }

    public function adjustDays(){
        $list = DB::select('SELECT * FROM sh_shop_share where days=0 order by insert_date desc;');
        foreach ($list as $item) {
            $shop_id = $item->sh_shop_id;
            $insert_date = $item->insert_date;
            $scan_sum = $item->scan_sum;
            $real_num = $item->real_num;

            $first_date = DB::select('SELECT insert_date FROM sh_shop_share where sh_shop_id = '.$shop_id.' order by insert_date asc limit 1');
            $first = $first_date[0]->insert_date;

            $days_sql=DB::select('select TIMESTAMPDIFF(DAY,"'.$first.'","'.$insert_date.'") days');
            $days = $days_sql[0]->days;

            if($days == 0){
                $avg_scan_sum = 0;
                $avg_real_num = 0;
            }else{
                $avg_scan_sum = $scan_sum/$days;
                $avg_real_num = $real_num/$days;
            }

            $param_array = array(
                'days' => $days,
                'avg_scan_sum' => $avg_scan_sum,
                'avg_real_num' => $avg_real_num,
            );

            DB::table('shop_share')
                ->where('sh_shop_id', $shop_id)
                ->where('insert_date', $insert_date)
                ->where('scan_sum', $scan_sum)
                ->update($param_array);
        }
    }

    public function adjustShopShareOsType(){
        $sum = 0;
        $list = DB::select('SELECT * FROM sh_shop_share where scan_sum_android=0 order by insert_date desc limit 10000');
        foreach ($list as $item) {
            $shop_id = $item->sh_shop_id;
            $insert_date = $item->insert_date;
            $scan_sum = $item->scan_sum;

            $scan_sum_android = DB::select('SELECT count(*) sum FROM sh_shop_scan where sh_shop_id = '.$shop_id.' and create_time < "'.$insert_date.'" and os_type=1');
            $scan_sum_ios = DB::select('SELECT count(*) sum FROM sh_shop_scan where sh_shop_id = '.$shop_id.' and create_time < "'.$insert_date.'" and os_type=2');

            $scan_num_android = $scan_sum_android[0]->sum;
            $scan_num_ios = $scan_sum_ios[0]->sum;

            $red_packet_android = DB::select('select count(*) sum from sh_shop_tel a inner join sh_red_packet b on a.sh_user_tel=b.tel where a.sh_shop_id='.$shop_id.' and b.os_type=1 and b.channel=7 and a.create_time < "'.$insert_date.'" and b.create_time < "'.$insert_date.'"');
            $red_packet_ios = DB::select('select count(*) sum from sh_shop_tel a inner join sh_red_packet b on a.sh_user_tel=b.tel where a.sh_shop_id='.$shop_id.' and b.os_type=2 and b.channel=7 and a.create_time < "'.$insert_date.'" and b.create_time < "'.$insert_date.'"');

            $red_num_android = $red_packet_android[0]->sum;
            $red_num_ios = $red_packet_ios[0]->sum;

            $param_array = array(
                'scan_sum_android' => $scan_num_android,
                'scan_sum_ios' => $scan_num_ios,
                'red_packet_android' => $red_num_android,
                'red_packet_ios' => $red_num_ios,
            );

            $res = DB::table('shop_share')
                ->where('sh_shop_id', $shop_id)
                ->where('insert_date', $insert_date)
                ->where('scan_sum', $scan_sum)
                ->update($param_array);
            $sum += $res;
            Log::info('本次已更新 '.$sum.' 条数据');
        }
        return $sum;
    }

    public function updateScanNumByDate($date){
        $afterDay = date("Y-m-d",strtotime("$date +1 day"));
        $list = DB::select('select count(*) sum from sh_shop_scan where create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00") and create_time > concat(date_format("'.$date.'", "%Y-%m-%d"), " 00:00:00")');
        $list1 = DB::select('select count(*) sum from sh_shop_scan where scene = 1 and create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00") and create_time > concat(date_format("'.$date.'", "%Y-%m-%d"), " 00:00:00")');
        $list2 = DB::select('select count(*) sum from sh_shop_scan where scene = 2 and create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00") and create_time > concat(date_format("'.$date.'", "%Y-%m-%d"), " 00:00:00")');
        $list3 = DB::select('select count(*) sum from sh_shop_scan where scene = 3 and create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00") and create_time > concat(date_format("'.$date.'", "%Y-%m-%d"), " 00:00:00")');
        $list4 = DB::select('select count(*) sum from sh_shop_scan where scene = 4 and create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00") and create_time > concat(date_format("'.$date.'", "%Y-%m-%d"), " 00:00:00")');

        $scan_sum = $list[0]->sum;
        $scene_one_scan = $list1[0]->sum;
        $scene_two_scan = $list2[0]->sum;
        $scene_three_scan = $list3[0]->sum;
        $scene_four_scan = $list4[0]->sum;

        $res = array(
            'scan_sum' => $scan_sum,
            'scene_one_scan' => $scene_one_scan,
            'scene_two_scan' => $scene_two_scan,
            'scene_three_scan' => $scene_three_scan,
            'scene_four_scan' => $scene_four_scan,
        );

        if($list){
            DB::table('shop_share_history')->where('date', $date)->update($res);
        }
    }

    public function updateScanNumBeforeDate($date){
        $afterDay = date("Y-m-d",strtotime("$date +1 day"));

        for($i=1;$i<=7;$i++){
            $list = DB::select('select count(*) sum from sh_shop_scan where create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00") and sh_shop_id = '.$i);
            $list1 = DB::select('select count(*) sum from sh_shop_scan where scene = 1 and create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00") and sh_shop_id = '.$i);
            $list2 = DB::select('select count(*) sum from sh_shop_scan where scene = 2 and create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00") and sh_shop_id = '.$i);
            $list3 = DB::select('select count(*) sum from sh_shop_scan where scene = 3 and create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00") and sh_shop_id = '.$i);
            $list4 = DB::select('select count(*) sum from sh_shop_scan where scene = 4 and create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00") and sh_shop_id = '.$i);
            $scan_sum = $list[0]->sum;
            $scene_one_scan = $list1[0]->sum;
            $scene_two_scan = $list2[0]->sum;
            $scene_three_scan = $list3[0]->sum;
            $scene_four_scan = $list4[0]->sum;

            $res = array(
                'scan_sum' => $scan_sum,
                'scene_one_scan' => $scene_one_scan,
                'scene_two_scan' => $scene_two_scan,
                'scene_three_scan' => $scene_three_scan,
                'scene_four_scan' => $scene_four_scan,
            );

            DB::table('shop_share')
                ->where( 'sh_shop_id', $i)
                ->where( DB::raw('date_format(insert_date,"%Y-%m-%d")'), '=' ,  $date)
                ->update($res);
        }
    }

    public function getShopShareOsTypePercent($beginDate = null, $endDate = null){
        $table = DB::table('red_packet');
        $table->select(DB::raw("
          CASE
              WHEN os_type = '1' THEN 'Android'
              WHEN os_type = '2' THEN 'IOS'
              WHEN os_type = '3' THEN 'Other'
          END os_remark,
          count(*) sum,
          os_type
        "))
            ->where( DB::raw('source'), '=' ,  '1');

        if($beginDate && $endDate){
            $table->where( DB::raw('create_time'), '<' , DB::raw("concat('".$endDate."',' 00:00:00')"));
            $table->where( DB::raw('create_time'), '>' , DB::raw("concat('".$beginDate."',' 00:00:00')"));
        }

        $table->groupBy('os_type');
        $list = $table->get();
        return $list;
    }

    public function getAllScanOsTypePercent($beginDate = null, $endDate = null){
        $table = DB::table('shop_scan');
        $table->select(DB::raw("
          CASE
              WHEN os_type = '1' THEN 'Android'
              WHEN os_type = '2' THEN 'IOS'
              WHEN os_type = '3' THEN 'Other'
          END os_remark,
          count(*) sum,
          os_type
        "))
            ->where( DB::raw('source'), '=' ,  '1');

        if($beginDate && $endDate){
            $table->where( DB::raw('create_time'), '<' , DB::raw("concat('".$endDate."',' 00:00:00')"));
            $table->where( DB::raw('create_time'), '>' , DB::raw("concat('".$beginDate."',' 00:00:00')"));
        }

        $table->groupBy('os_type');
        $list = $table->get();
        return $list;
    }

    public function getShopShareOsTypeByDate($date){
        $afterDay = date("Y-m-d",strtotime("$date +1 day"));
        $list = DB::select('
            select
                CASE
                  WHEN os_type = "1" THEN "Android"
                  WHEN os_type = "2" THEN "IOS"
                  WHEN os_type = "3" THEN "Other"
                END os_remark,
                count(*) sum,
                os_type
            from
                sh_red_packet
            where
                create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00")
                and create_time > concat(date_format("'.$date.'", "%Y-%m-%d"), " 00:00:00")
                and source = "1"
            group by os_type;
        ');
        return $list;
    }

    /**
     * 获取指定日期的扫码数
     * @param $date
     */
    public function getScanNumByDate($date){
        $afterDay = date("Y-m-d",strtotime("$date +1 day"));
        $list = DB::select('
            select
                count(*) sum
            from
                sh_shop_scan
            where
                create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00")
                and create_time > concat(date_format("'.$date.'", "%Y-%m-%d"), " 00:00:00")
        ');
        return $list;
    }

    public function getScanOsTypeByDate($date){
        $afterDay = date("Y-m-d",strtotime("$date +1 day"));
        $list = DB::select('
            select
                CASE
                  WHEN a.os_type = "1" THEN "Android"
                  WHEN a.os_type = "2" THEN "IOS"
                  WHEN a.os_type = "3" THEN "Other"
                END os_remark,
                count(*) sum,
                a.os_type
            from
                sh_shop_scan a
            inner JOIN
                sh_shop b
            ON
                a.sh_shop_id= b.id
            where
                a.create_time < concat(date_format("'.$afterDay.'", "%Y-%m-%d"), " 00:00:00")
                and a.create_time > concat(date_format("'.$date.'", "%Y-%m-%d"), " 00:00:00")
                and b.enabled = 1
            group by a.os_type;
        ');
        return $list;
    }

    public function getShopShareHistoryResult($length){
        $list = DB::table('shop_share_history')->orderBy('date', 'desc')->paginate($length);
        return $list;
    }

    public function getShopShareHistoryResultAjax($length){
        $list = DB::select('
            select
              date,
              red_packet_sum,
              real_sum,
              percent,
              percent_scan
            from sh_shop_share_history order by date desc limit '.$length.'
        ');
        return $list;
    }

    public function addShopShareHistory($data){
        $array = array();
        $array['date'] = $data['date'];
        $array['create_time'] = $this->nowDateTime;
        $array['update_time'] = $this->nowDateTime;
        $array['avg_customer_sum'] = $data['avg_customer_sum'];
        $array['avg_scan_sum'] = $data['avg_scan_sum'];

        $array['scan_sum'] = $data['scan_sum'];
        $array['scan_sum_android'] = $data['scan_sum_android'];
        $array['scan_sum_ios'] = $data['scan_sum_ios'];

        $array['red_packet_sum'] = $data['red_packet_sum'];
        $array['red_packet_android'] = $data['red_packet_android'];
        $array['red_packet_ios'] = $data['red_packet_ios'];

        $array['percent_scan'] = $data['percent_scan'];
        $array['avg_real_sum'] = $data['avg_real_sum'];
        $array['real_sum'] = $data['real_sum'];
        $array['percent'] = $data['percent'];
        $array['scene_one_sum'] = $data['scene_one'];
        $array['scene_two_sum'] = $data['scene_two'];
        $array['scene_three_sum'] = $data['scene_three'];
        $array['scene_four_sum'] = $data['scene_four'];
        $array['scene_one_scan'] = $data['scene_one_scan'];
        $array['scene_two_scan'] = $data['scene_two_scan'];
        $array['scene_three_scan'] = $data['scene_three_scan'];
        $array['scene_four_scan'] = $data['scene_four_scan'];
        $array['duobao_sum'] = $data['duobao_sum'];
        DB::table('shop_share_history')->where('date', '=', $data['date'])->delete();
        $id = DB::table('shop_share_history')->insert($array);
        return $id;
    }

    public function getShopShareByDate($date,$shop_id){
        $res = DB::select('
            select
                sh_shop_id,
                days,
                scan_sum,
                scan_sum_android,
                scan_sum_ios,
                avg_scan_sum,
                scan_num,
                red_packet_android,
                red_packet_ios,
                real_num,
                avg_real_num,
                scene_one,
                scene_two,
                scene_three,
                scene_four,
                insert_date,
                scene_one_scan,
                scene_two_scan,
                scene_three_scan,
                scene_four_scan,
                CONCAT(cast(red_packet_percent*100 as decimal(10,0)),"%") red_packet_percent,
                duobao_sum
            from
              sh_shop_share
            where
              sh_shop_id = '.$shop_id.'
              and insert_date < concat(date_format("'.$date.'", "%Y-%m-%d"), " 00:00:00")
            order by insert_date desc
            limit 1
        ');
        return $res;
    }

    public function getShopShareList(){
        $count = DB::select('select count(*) sum from sh_shop where enabled = 1');
        $count=$count[0]->sum;
        $list = DB::select('
            select
              a.sh_shop_id,
              b.head_name,
              b.branch_name,
              a.days,
              a.scan_sum,
              a.scan_sum_android,
              a.scan_sum_ios,
              a.avg_scan_sum,
              a.scan_num,
              a.red_packet_android,
              a.red_packet_ios,
              a.real_num,
              a.avg_real_num,
              CONCAT(cast(a.percent*100 as decimal(10,0)),"%") percent,
              a.scene_one,
              a.scene_two,
              a.scene_three,
              a.scene_four,
              a.insert_date,
              a.scene_one_scan,
              a.scene_two_scan,
              a.scene_three_scan,
              a.scene_four_scan,
              CONCAT(cast(a.red_packet_percent*100 as decimal(10,0)),"%") red_packet_percent
            from
              sh_shop_share a
            inner JOIN
              sh_shop b
            ON
              a.sh_shop_id = b.id
            order by a.insert_date desc,a.sh_shop_id desc
            limit '.$count.'
        ');
        return $list;
    }

    public function getScanSumByShopId($shopId){
        $sql = 'SELECT count(*) num FROM sh_shop_scan where sh_shop_id = '.$shopId;
        $list = DB::select($sql);
        return $list;
    }

    public function getShopShareListAjax($length, $order){
        $count = DB::select('select count(*) sum from sh_shop where enabled = 1');
        $count=$count[0]->sum;

        if($count > $length){
            $limit = $length;
        }elseif($count < $length){
            $limit = $count;
        }else{
            $limit = $length;
        }

        $table = DB::table('shop_share AS a');
        $table->select(DB::raw("
          a.sh_shop_id,
          b.head_name,
          b.branch_name,
          a.scan_num,
          a.real_num,
          CONCAT(cast(a.percent*100 as decimal(10,0)),'%') percent,
          a.scene_one,
          a.scene_two,
          a.scene_three,
          a.scene_four,
          a.insert_date
        "))
            ->join('shop AS b' ,DB::raw('a.sh_shop_id'),'=',DB::raw('b.id'));
        $table->orderBy(DB::raw('a.insert_date'), 'desc');
        $table->orderBy(DB::raw($order), 'desc');
        $list = $table->take($limit)->get();
        return $list;
    }


    public function addShop($data) {
        $data['create_time'] = $this->nowDateTime;
        $data['update_time'] = $this->nowDateTime;
        $id = DB::table('shop')->insertGetId($data);
        return $id;
    }

    public function getShopList($length) {
        $shops = DB::table('shop')->where('enabled', '1')->paginate($length);
        return $shops;
    }

    public function getShopList2() {
        $shops = DB::table('shop')->where('enabled', '1')->get();
        return $shops;
    }

    public function delShop($shopId) {
        return DB::table('shop')->where('id', $shopId)->update(array('enabled' => '0'));
    }

    public function getShopById($shopId) {
        return DB::table('shop')->where('id', $shopId)->first();
    }

    public function editShop($id, array $data) {
        return DB::table('shop')->where('id', $id)->update($data);
    }

}

