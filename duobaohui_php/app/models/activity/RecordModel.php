<?php
/**
* 订单
* @author zhaozhonglin@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Schema;

class RecordModel extends Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');

	}
	
	
	/**
	 * 夺宝纪录
	 * @param unknown $user_id
	 * @return unknown
	 */
	public function getJoinRecords($user_id,$offset, $length) {
// 	    $joinRecords = DB::table( 'period_user AS '.DB::getTablePrefix().'pu' )
// 	    ->leftJoin('activity_period AS '.DB::getTablePrefix().'ap' ,'pu.sh_activity_period_id','=','ap.id')
// 	    ->leftJoin('activity AS '.DB::getTablePrefix().'a' ,'ap.sh_activity_id','=','a.id')
// 	    ->leftJoin('goods AS '.DB::getTablePrefix().'g' ,'a.sh_goods_id','=','g.id')
// 	    ->where( 'pu.user_id', '=' ,  $user_id)
// 	    ->skip($offset)->take($length)
// 	    ->select(
// 	        'ap.id',
// 	        'ap.period_number',
// 	        'g.goods_name',
// 	        'ap.real_need_times',
// // 	         '(ap.real_need_times - ap.current_times)),
// 	        'pu.times')
//         ->orderBy('pu.create_time', 'desc')
	         
// 	    ->get();
	    $joinRecords = DB::select('
	    select 
            pu.sh_activity_period_id,
            max(pu.create_time) as create_time,
            ap.period_number,
            g.goods_name,
            g.goods_img,
            ap.real_need_times,
	        ap.current_times,
            (ap.real_need_times - ap.current_times) as remainder,
            sum(pu.times) times,
    	    a.is_online
        from
            sh_period_user pu
        left join 
            sh_activity_period ap
                on pu.sh_activity_period_id = ap.id
        left join
            sh_activity a
                on ap.sh_activity_id = a.id 
        left join
            sh_goods g
                on a.sh_goods_id = g.id
        where
            pu.user_id = ?
	    group by 
            pu.sh_activity_period_id , 
            pu.user_id
	    order by 
	        create_time desc 
	    limit ?,?', array($user_id,$offset,$length));
	    return $joinRecords;
	}

	/**
	 * 夺宝纪录
	 * @param unknown $user_id
	 * @return unknown
	 */
	public function getJoinRecordsAll($user_id) {
		$joinRecords = DB::select('
	    select
            pu.sh_activity_period_id,
            max(pu.create_time) as create_time,
            ap.period_number,
            g.goods_name,
            g.goods_img,
            ap.real_need_times,
	        ap.current_times,
            (ap.real_need_times - ap.current_times) as remainder,
            sum(pu.times) times,
    	    a.is_online
        from
            sh_period_user pu
        left join
            sh_activity_period ap
                on pu.sh_activity_period_id = ap.id
        left join
            sh_activity a
                on ap.sh_activity_id = a.id
        left join
            sh_goods g
                on a.sh_goods_id = g.id
        where
            pu.user_id = ?
	    group by
            pu.sh_activity_period_id ,
            pu.user_id
	    order by
	        create_time desc
	    ', array($user_id));
		return $joinRecords;
	}

	/**
	 * 夺宝纪录总笔数
	 * @param unknown $user_id
	 * @return unknown
	 */
	public function getJoinRecordsCount($user_id) {
	    return DB::table( 'period_user AS '.DB::getTablePrefix().'pu' )
	    ->leftJoin('activity_period AS '.DB::getTablePrefix().'ap' ,'pu.sh_activity_period_id','=','ap.id')
	    ->leftJoin('activity AS '.DB::getTablePrefix().'a' ,'ap.sh_activity_id','=','a.id')
	    ->leftJoin('goods AS '.DB::getTablePrefix().'g' ,'a.sh_goods_id','=','g.id')
	    ->where( 'pu.user_id', '=' ,  $user_id)
	    ->count();
	}
	
	/**
	 * 
	 * 中奖纪录
SELECT
ar.sh_activity_period_id,
ar.user_id,
sum(pu.times),
ar.luck_code,
ar.luck_code_create_time
from
sh_period_result ar
left join
sh_period_user pu
on ar.sh_activity_period_id = pu.sh_activity_period_id
left join
sh_user u
on ar.user_id = u.id

where ar.sh_activity_period_id in('17','18')
group by 
            pu.sh_activity_period_id , 
            pu.user_id

	 */
	
	public function getWinningRecords($period_id_list) {
// 	    var_dump($period_id_list);
// 	    die;
// 	    $period_id_list_temp[] = ['12','29'];
// 	    $winning_records_list = DB::select('SELECT
//             ar.sh_activity_period_id,
//             ar.user_id,
//             u.nick_name,
//             sum(pu.times) as times,
//             ar.luck_code,
//             ar.luck_code_create_time,
//             ar.pre_luck_code_create_time
            
//             from
//             sh_period_result ar
//             left join
//             sh_period_user pu
//             on ar.sh_activity_period_id = pu.sh_activity_period_id and ar.user_id = pu.user_id
//             left join
//             sh_user u
//             on ar.user_id = u.id
            
//             where ar.sh_activity_period_id in(?)
//             and sysdate() > ar.pre_luck_code_create_time
//             group by 
//             pu.sh_activity_period_id , 
//             pu.user_id ', array($period_id_list_temp));
	    
	    $winning_records_list = DB::table('period_result AS '.DB::getTablePrefix().'ar')
// 	    ->leftJoin('period_user AS '.DB::getTablePrefix().'pu' ,'ar.sh_activity_period_id','=','pu.sh_activity_period_id')
	    ->leftJoin('period_user AS '.DB::getTablePrefix().'pu' ,function($join){
	        $join->on('ar.sh_activity_period_id','=','pu.sh_activity_period_id')
	        ->on('ar.user_id', '=', 'pu.user_id');
	    })
	    ->leftJoin('user AS '.DB::getTablePrefix().'u' ,'ar.user_id','=','u.id')
	    ->whereIn('ar.sh_activity_period_id', $period_id_list)
	    ->where('ar.user_id', '<>' , '0')
	    ->whereRaw('sysdate() > pre_luck_code_create_time')
	    ->select(
	    'ar.pre_luck_code_create_time',
	    'ar.sh_activity_period_id',
	    'ar.user_id',
        'u.nick_name',
	     DB::raw('SUM(times) as times'),
	    'ar.luck_code',
	    'ar.luck_code_create_time')
	    ->groupBy('pu.sh_activity_period_id','pu.user_id')
	    ->get();
	    return $winning_records_list;
	}
	
	/**
	 * 本期夺宝次数列表
	 */
	public function getLotterytimeslistbyperiodid( $user_id , $sh_activity_period_id , $offset , $length ) {
	    $lottery_code_list = DB::table('period_user AS '.DB::getTablePrefix().'pu')
	    ->where('pu.user_id', $user_id)
	    ->where('pu.sh_activity_period_id', $sh_activity_period_id)
	    ->skip($offset)->take($length)
	    ->select(
	    'pu.id as sh_period_user_id',
	    'pu.sh_activity_period_id',
	    'pu.create_time',
	    'pu.user_id',
	    'pu.times')
	    ->orderBy('pu.create_time', 'desc')
	    ->get();
	    return $lottery_code_list;
	}
	
	/**
	 * 本期、本次 夺宝号列表
	 */
	public function getLotterycodelistbyperiodid( $user_id , $sh_activity_period_id , $sh_period_user_id, $offset , $length ) {
	    $lottery_code_list = DB::table('activity_code AS '.DB::getTablePrefix().'ac')
	    ->where('ac.user_id', $user_id)
	    ->where('ac.activity_period_id', $sh_activity_period_id)
	    ->where('ac.sh_period_user_id', $sh_period_user_id)
	    ->skip($offset)->take($length)
	    ->select(
	    'ac.code_num',
	    'ac.create_time')
	    ->orderBy('ac.create_time', 'desc')
	    ->get();
	    return $lottery_code_list;
	}
	
	/**
	 * 喇叭
	 * @param unknown 
	 * @return unknown
	 */
	public function getNewestWinningList() {
	    
	    $newestWinningList = DB::select('SELECT 
        pr.user_id,
        u.nick_name,
		u.head_pic,
        ap.period_number,
        pr.luck_code_create_time,
        g.goods_name,
        ap.id as sh_activity_period_id,
	    pr.pre_luck_code_create_time
        from
	        
            sh_period_result pr
	        
        left join
	        
            sh_activity_period ap
	        
        on pr.sh_activity_period_id = ap.id
        left join
	        
            sh_activity a
	        
        on ap.sh_activity_id = a.id
        left join
	        
            sh_goods g
	        
        on a.sh_goods_id = g.id
        left join
	        
            sh_user u
	        
        on pr.user_id = u.id
	    where sysdate() > pr.pre_luck_code_create_time
        order by pr.luck_code_create_time desc 
        limit ?', array(10));
	    return $newestWinningList;
	}
	
	/**
	 * 计算详情    获取a_code、时时彩开奖号
	 */
	public function getCountdetail($sh_activity_period_id){
	    $list = DB::select('SELECT
	                           pr.id,
	                           pr.sh_activity_period_id,
	                           pr.a_code_create_time,
	                           pr.a_code,
	                           pr.lottery_code,
	                           pr.lottery_period,
	                           pr.luck_code,
	                           sysdate() > pr.pre_luck_code_create_time as show_code_flag
	                       FROM
	                           sh_period_result pr
                           where
	                           pr.sh_activity_period_id = ?',array($sh_activity_period_id));
	    return $list;
	}
	/**
	 * 计算详情    获取当期全站最后50条交易时间详情
	 */
	public function getLotteryCodeList($sh_activity_period_id){
	    $list = DB::select('SELECT
	                           acr.buy_time,
	                           acr.buy_time_date,
	                           acr.buy_time_code,
	                           acr.user_id,
	                           u.nick_name
	                       FROM
	                           sh_activity_code_record acr
	                       left join
	                           sh_user u
	                       on 
	                           acr.user_id = u.id
                           where
	                           acr.activity_period_id = ?
	                       order by
	                           acr.buy_time_date
	                       desc',
	                       array($sh_activity_period_id));
	    return $list;
	}
	/**
	 * 往期揭晓 通过活动id查询历史开奖纪录
	 */
	public function getWinninghistorylist($sh_activity_id , $period_number , $offset , $length){
        $sql = "SELECT 
                    ap.period_number,
                    pr.id AS sh_period_result_id,
                    ap.sh_activity_id,
                    pr.sh_activity_period_id AS sh_activity_period_id,
                    u.nick_name,
                    u.head_pic,
                    pr.user_id,
                    pr.luck_code,
                    pu.ip,
                    pu.ip_address,
                    SUM(pu.times) AS times,
                    pr.pre_luck_code_create_time,
                    SYSDATE() > pr.pre_luck_code_create_time AS is_winninghistory
                FROM
                    sh_duobaohui.sh_period_result pr
                        LEFT JOIN
                    sh_activity_period ap ON pr.sh_activity_period_id = ap.id
                        LEFT JOIN
                    sh_period_user pu ON pr.sh_activity_period_id = pu.sh_activity_period_id
                        LEFT JOIN
                    sh_user u ON pr.user_id = u.id
                WHERE
                    ap.sh_activity_id = $sh_activity_id";
                if($period_number != null){
            	        $sql = $sql." and ap.period_number < $period_number ";
        	    }
                $sql = $sql." GROUP BY 
                    pu.sh_activity_period_id , pu.user_id
                HAVING 
                    pr.user_id = pu.user_id
                ORDER BY 
                    ap.period_number DESC  limit $length ";
	    $list = DB::select($sql);
	    return $list;
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
		$builder = $builder->newQuery();
		$res = $builder->selectRaw("
			b.id as sh_activity_period_id,
			b.period_number,
			o.order_id,
			o.order_status,
			o.shipping_status,
			o.is_shaidan,
            o.confirm_time,
		case
			when o.order_status = '0' then '待领奖'
		 	when o.order_status = '1' and  o.shipping_status = '0' then '正在派奖'
			when o.order_status = '1' and  o.shipping_status = '1' then '待收货'
		 	when o.order_status = '2' and  o.shipping_status = '2' and o.is_shaidan = '0' then '待晒单'
		 	when o.order_status = '2' and  o.shipping_status = '2' and o.is_shaidan = '1' then '已晒单'
        else
        	'其他'
        end as status,
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
				JOIN
			sh_order_info o ON o.sh_period_result_id = a.id
				LEFT JOIN
			sh_user_shaidan us ON us.sh_order_id = o.order_id
		WHERE
			a.user_id = {$user_id}
		AND
			sysdate() > a.pre_luck_code_create_time
		ORDER BY
			o.order_status ASC ,
        CASE
            WHEN o.order_status = '0' THEN a.pre_luck_code_create_time
			WHEN o.order_status = '1' THEN o.confirm_time
		END
            DESC,
            o.shipping_status ASC ,
        CASE
            WHEN o.shipping_status = '1' THEN o.shipping_time
			WHEN o.shipping_status = '2' THEN o.receive_time
        END
            DESC,
            us.create_time DESC

		")->paginate($pageSize);
		return $res;
	}

	/**
	 * 查询中奖详情
	 * @param $user_id
	 * @param int $pageSize
	 * @return null
	 */
	public function winningDetail($user_id,$sh_activity_period_id) {
		if(empty($user_id)) {
			return null;
		}
		$builder = DB::table('period_user');
		$builder = $builder->newQuery();
		$res = $builder->selectRaw("
			o.order_id,
			o.order_status,
			o.shipping_status,
			o.is_shaidan,
			o.express_company,
			o.invoice_no,
			o.consignee,
    	    o.address,
    	    o.mobile,
    	    o.add_time,
    	    o.confirm_time,
    	    o.shipping_time,
    	    o.receive_time,
    	    us.create_time as shaidan_create_time,
		case
			when o.order_status = '0' then '0'
		 	when o.order_status = '1' and  o.shipping_status = '0' then '1'
			when o.order_status = '1' and  o.shipping_status = '1' then '2'
		 	when o.order_status = '2' and  o.shipping_status = '2' and o.is_shaidan = '0' then '3'
		 	when o.order_status = '2' and  o.shipping_status = '2' and o.is_shaidan = '1' then '4'
        else
        	'-1'
        end as status,
			a.user_id,
			d.id as goods_id,
			d.goods_name,
			d.goods_img,
			b.real_need_times,
			b.period_number,
			a.luck_code,
			a.luck_code_create_time,
			a.pre_luck_code_create_time,
			(select sum(times) from sh_period_user where user_id = a.user_id and sh_activity_period_id = a.sh_activity_period_id) total_times,
			ua.address_id,
			ua.consignee as address_consignee,
			ua.district as address_district,
			ua.address as address_address,
			ua.mobile as address_mobile
		FROM
			sh_period_result a
				JOIN
			sh_activity_period b ON b.id = a.sh_activity_period_id
				JOIN
			sh_activity c ON c.id = b.sh_activity_id
				JOIN
			sh_goods d ON d.id = c.sh_goods_id
				JOIN
			sh_order_info o ON o.sh_period_result_id = a.id
				LEFT JOIN
			sh_user_address ua ON ua.user_id = a.user_id AND ua.is_default = '1'
				LEFT JOIN
			sh_user_shaidan us ON us.sh_order_id = o.order_id
		WHERE
			a.user_id = {$user_id}
		AND
			b.id = {$sh_activity_period_id}

		")->first();
		return $res;
	}
	
	
	public function test(){
// 	    create temporary table tmp(
// 	    id int(11) primary key,
// 	    activity_period_id int(11),
// 	    sh_period_user_id int(11),
// 	    user_id int(11),
// 	    code_num int(6),
// 	    buy_time VARCHAR(32),
// 	    create_time TIMESTAMP
// 	    );
// 	    $res = Schema::create('zzl_test', function($table)
//         {
//             $table->increments('id');
//             $table->integer('activity_period_id');
//             $table->integer('sh_period_user_id');
//             $table->integer('user_id');
//             $table->integer('code_num');
//             $table->string('buy_time',32);
//             $table->timestamp('create_time');
//         });
//	    $res = DB::insert ( DB::raw ('CREATE TEMPORARY TABLE temp') );
//	    var_dump($res);
//	    die;
//	    return $res;
	}
}

