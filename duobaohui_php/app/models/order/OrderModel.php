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
use Laravel\Model\ActivityModel;			//引入model
use Laravel\Model\SystemModel;			//引入model
use Illuminate\Support\Facades\Request;
use Illuminate\Support\Facades\Config;

class OrderModel extends Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');

	}
	
	/**
	 * 订单状态Tab
	 */
	public function getOrderStatusList($user_id) {
// 	    $orderStatusList = DB::select('select a.order_status_count_0,b.order_status_count_1,c.order_status_count_2 from 
//         (select count(order_status) as order_status_count_0 ,order_status from sh_order_info where user_id = ? and order_status = \'0\') as a,
//         (select count(order_status) as order_status_count_1 ,order_status from sh_order_info where user_id = ? and order_status = \'1\') as b,
//         (select count(order_status) as order_status_count_2 ,order_status from sh_order_info where user_id = ? and order_status = \'2\') as c', 
// 	    array($user_id,$user_id,$user_id));
	    $orderStatusList = DB::select('
	        select d.order_status_count_all,a.order_status_count_0,b.order_status_count_1,c.order_status_count_2 from 
                (select 
                count(oi.order_status) as order_status_count_all from sh_period_result pr 
                left join sh_order_info oi 
                on pr.id = oi.sh_period_result_id
                where pr.user_id = ? 
                and sysdate() > pr.pre_luck_code_create_time) as d,
                (select 
                count(oi.order_status) as order_status_count_0 ,order_status from sh_period_result pr 
                left join sh_order_info oi 
                on pr.id = oi.sh_period_result_id
                where pr.user_id = ? 
                and oi.order_status = \'0\'
                and sysdate() > pr.pre_luck_code_create_time) as a,
	            (select 
                count(oi.order_status) as order_status_count_1 ,order_status from sh_period_result pr 
                left join sh_order_info oi 
                on pr.id = oi.sh_period_result_id
                where pr.user_id = ? 
                and oi.order_status = \'1\'
                and sysdate() > pr.pre_luck_code_create_time) as b,
	            (select 
                count(oi.order_status) as order_status_count_2 ,order_status from sh_period_result pr 
                left join sh_order_info oi 
                on pr.id = oi.sh_period_result_id
                where pr.user_id = ? 
                and oi.order_status = \'2\'
                and sysdate() > pr.pre_luck_code_create_time) as c',
	    array($user_id,$user_id,$user_id,$user_id));
	
	    return $orderStatusList;
	}
	
	
	/**
	 * 通过订单IDs列表
	 */
	public function getOrderListByIds($orderIds, $version='') {
		// 针对ios 审核，不显示iPhone产品
		$whereApple = '';
		$systemM = new SystemModel();
		if( $systemM->checkIOSAudit( Request::header('user-agent') , $version) ){
			$whereApple = ' AND (g.goods_name not like "%apple%" AND g.goods_name not like "%mac%" AND g.goods_name not like "%ipad%" AND g.goods_name not like "%iphone%") ';
		}


		$sql = "select 
            oi.order_id,
            oi.order_sn,
            pu.ip,
            pu.ip_address,
            ap.period_number,
            g.id as goods_id,
            g.goods_name,
            g.goods_img,
            ap.real_need_times,
            sum(pu.times) as times,
            pr.luck_code,
            oi.order_status,
            oi.shipping_status,
	        oi.consignee,
	        oi.address,
	        oi.mobile,
	        oi.is_shaidan,
            pr.user_id,
            pr.sh_activity_period_id,
            pr.a_code_create_time,
            pr.pre_luck_code_create_time,
        case when pr.luck_code_create_time >= pr.pre_luck_code_create_time then pr.luck_code_create_time 
        else pr.pre_luck_code_create_time 
        end luck_code_create_time 
        from
            sh_period_result pr
        left join
            sh_order_info oi
                on pr.id = oi.sh_period_result_id
        left join 
            sh_period_user pu
                on pr.sh_activity_period_id = pu.sh_activity_period_id and pr.user_id = pu.user_id
        left join
            sh_activity_period ap
                on pr.sh_activity_period_id = ap.id
        left join
            sh_activity a
                on ap.sh_activity_id = a.id 
        left join
            sh_goods g
                on a.sh_goods_id = g.id
        where 
	        oi.order_id IN($orderIds)
		{$whereApple}
        group by 
	        pu.sh_activity_period_id , pu.user_id
	    order by 
			pu.create_time desc";

	    $orderList = DB::select($sql);
	    
	    return $orderList;
	}
	
	/**
	 * 订单状态列表
	 */
	public function getOrderListByStatus($order_status, $user_id,$offset, $length) {
// 	    $orderList = DB::select('
//         select 
//             oi.order_id,
//             oi.order_sn,
//             ap.period_number,
//             g.goods_name,
//             g.goods_img,
//             ap.real_need_times,
//             sum(pu.times) as times,
//             pr.luck_code,
//             pr.a_code_create_time,
//             pr.luck_code_create_time,
//             oi.order_status,
//             oi.shipping_status,
// 	        oi.address,
// 	        oi.mobile,
//             pr.user_id,
//             pr.sh_activity_period_id,
//             pr.pre_luck_code_create_time
//         from
//             sh_period_result pr
//         left join
//             sh_order_info oi
//                 on pr.id = oi.sh_period_result_id
//         left join 
//             sh_period_user pu
//                 on pr.sh_activity_period_id = pu.sh_activity_period_id and pr.user_id = pu.user_id
//         left join
//             sh_activity_period ap
//                 on pr.sh_activity_period_id = ap.id
//         left join
//             sh_activity a
//                 on ap.sh_activity_id = a.id 
//         left join
//             sh_goods g
//                 on a.sh_goods_id = g.id
//         where 
//             pu.user_id = ?
//         and
//             sysdate() > pr.pre_luck_code_create_time
//         and 
//             oi.order_status = ?
//         group by 
// 	        pu.sh_activity_period_id , pu.user_id
// 	    order by 
// 			pu.create_time desc
// 	    limit ?,?
// 	    ',array($order_status , $user_id , $offset , $length));
	    
	    $sql = "select 
            oi.order_id,
            oi.order_sn,
            ap.period_number,
            g.id as goods_id,
            g.goods_name,
            g.goods_img,
            ap.real_need_times,
            sum(pu.times) as times,
            pr.luck_code,
            oi.order_status,
            oi.shipping_status,
	        oi.consignee,
	        oi.address,
	        oi.mobile,
	        oi.is_shaidan,
            pr.user_id,
            pr.sh_activity_period_id,
            pr.a_code_create_time,
            pr.pre_luck_code_create_time,
        case when pr.luck_code_create_time >= pr.pre_luck_code_create_time then pr.luck_code_create_time 
        else pr.pre_luck_code_create_time 
        end luck_code_create_time 
        from
            sh_period_result pr
        left join
            sh_order_info oi
                on pr.id = oi.sh_period_result_id
        left join 
            sh_period_user pu
                on pr.sh_activity_period_id = pu.sh_activity_period_id and pr.user_id = pu.user_id
        left join
            sh_activity_period ap
                on pr.sh_activity_period_id = ap.id
        left join
            sh_activity a
                on ap.sh_activity_id = a.id 
        left join
            sh_goods g
                on a.sh_goods_id = g.id
        where 
            pu.user_id = $user_id and sysdate() > pr.pre_luck_code_create_time ";
	    if($order_status != null && $order_status != ''){
	        $sql = $sql." and oi.order_status =   $order_status ";
	    }
	    $sql = $sql." group by 
	        pu.sh_activity_period_id , pu.user_id order by pu.create_time desc limit  $offset,$length ";
	    
	    $orderList = DB::select($sql);

	    return $orderList;
	}

	public function checkWaitTime($orderList){
		$activityM = new ActivityModel();
		$tmp = array();
		foreach($orderList as $v){
			$waitTime = $activityM->getWaitTime();
			$left_second =  strtotime($v->a_code_create_time) + $waitTime  - time(); 
			if($left_second<0){
				$tmp[] = $v;
			}
		}
		return $tmp;
	}

	/*
	 * 订单状态列表总笔数
	 */
	public function getOrderListCountByStatus($order_status, $user_id) {
	    return DB::table( 'period_result AS '.DB::getTablePrefix().'pr' )
	    ->leftJoin('period_user AS '.DB::getTablePrefix().'pu' ,'pr.sh_activity_period_id','=','pu.sh_activity_period_id')
	    ->leftJoin('activity_period AS '.DB::getTablePrefix().'ap' ,'pu.sh_activity_period_id','=','ap.id')
	    ->leftJoin('activity AS '.DB::getTablePrefix().'a' ,'ap.sh_activity_id','=','a.id')
	    ->leftJoin('order_info AS '.DB::getTablePrefix().'oi' ,'pr.id','=','oi.sh_period_result_id')
	    ->leftJoin('goods AS '.DB::getTablePrefix().'g' ,'a.sh_goods_id','=','g.id')
	    ->where( 'oi.order_status', '=' ,  $order_status)
	    ->where( 'pr.user_id', '=' ,  $user_id)
	    ->count();
	}
	
	
	/**
	 * 更新订单状态
	 */
	public function updateOrderStatus( $order_id , $order_status , $shipping_status , $user_id ,$address_id) {
	    if( !empty($address_id) && $address_id != ''){
	        $address_res = DB::select('select district,address,consignee,mobile FROM sh_user_address where address_id = ?',array($address_id));
	        $district = $address_res[0]->district;//收货地区
	        $address = $address_res[0]->address;//收货详细地址
	        $consignee = $address_res[0]->consignee;//收货人姓名
	        $mobile = $address_res[0]->mobile;//收货人手机号
	        $update_res = DB::update('update 
	                       sh_order_info 
	                   set
	                   	   confirm_time = ?,
	                       order_status = ?,
	                       shipping_status=?,
	                       consignee=?,
	                       address=?,
	                       mobile=?
	                   where 
	                       user_id = ?
	                   and
	                       order_id = ?', array(date('Y-m-d H:i:s', time()),$order_status, $shipping_status , $consignee , $district.$address , $mobile , $user_id ,  $order_id));
	    }else{
    	    $update_res = DB::table('order_info AS '.DB::getTablePrefix().'oi' )
    	    ->where( 'oi.user_id', '=' ,  $user_id)
    	    ->where( 'oi.order_id', '=' ,  $order_id)
    	    ->update(array(
    	        'order_status' => $order_status,
    	        'shipping_status' => $shipping_status,
				'receive_time' => date('Y-m-d H:i:s', time())
    	    ));
	    }
	    if ( $update_res ){
    	    $data['order_id'] = $order_id;
    	    $data['user_id'] = $user_id;
    	    $data['user_type'] = '0';
    	    $data['order_status'] = $order_status;
    	    $data['shipping_status'] = $shipping_status;
    	    $data['pay_status'] = '0';
    	    $data['action_note'] = ' ';
    	    $data['log_time'] = time();
    	    $data['create_time'] = date('Y-m-d H:i:sa', time());
    	    DB::table('order_action')->insertGetId($data);
	    }
	    
// 	    $res = DB::transaction(function()use( $order_id , $order_status , $shipping_status , $user_id ,$address_id,$address_res){
    	    
// 	    });
	    return $update_res;
	}
}

