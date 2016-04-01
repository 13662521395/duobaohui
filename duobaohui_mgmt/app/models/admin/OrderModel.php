<?php
/**
* 后台管理订单
* @author zhaozhonglin@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model\Admin;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;
use Laravel\Model\ActivityModel;			//引入model

class OrderModel extends \Laravel\Model\Model {
	private $nowDateTime;

	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');

	}

	/**
	 * 记录订单发货短信通知状态
	 */
	public function addJnlOrderSendSms($order_id,$send_res,$sms_template) {
		$send_flag = $send_res?2:3;
		$res = DB::table('jnl_order_sms')->insert(
			['order_id' => $order_id, 'send_time' => $this->nowDateTime,'send_flag'=>$send_flag,'sms_template'=>$sms_template]
		);
		return $res;
	}
	
	/**
	 * 订单状态列表
	 */
	public function orderList($shipping_status,$luck_code_create_time,$offset,$length) {
	    $table = DB::table('period_result AS pr');
	    $table->select(DB::raw("
			pr.id,
	        a.id as activity_id,
	        ap.id as activity_period_id,
			DATE_FORMAT(pr.luck_code_create_time, '%Y-%m-%d %T') luck_code_create_time,
			g.goods_name,
	        g.goods_img,
	        g.purchase_url,
			c.cat_name,
			ap.period_number,
			u.nick_name,
			u.id userid,
			u.tel,
	        u.is_real,
			g.is_real,
			oi.shipping_status,
			oa.create_time,
			CASE
		        WHEN oi.shipping_status = '0' THEN '未发货'
		        WHEN oi.shipping_status = '1' THEN '已发货'
		        WHEN oi.shipping_status = '2' THEN '已收货'
		        WHEN oi.shipping_status = '3' THEN '备货中'
		        ELSE '未知'
		    END shipping_status_view,
			CASE
		        WHEN oi.order_status = '0' THEN '未确认'
		        WHEN oi.order_status = '1' THEN '已确认'
		        WHEN oi.order_status = '2' THEN '已完成'
		        WHEN oi.order_status = '3' THEN '已取消'
		        WHEN oi.order_status = '4' THEN '无效'
		        WHEN oi.order_status = '5' THEN '退货'
		        WHEN oi.order_status = '6' THEN '已删除'
		        ELSE '未知'
		    END order_status_view,
			oi.order_id,
			oi.order_sn,
			oi.order_status,
			oi.consignee,
			oi.address,
			oi.mobile
		"))
	    ->leftJoin('activity_period AS ap' ,DB::raw('pr.sh_activity_period_id'),'=',DB::raw('ap.id'))
	    ->leftJoin('order_info AS oi' ,DB::raw('pr.id'),'=',DB::raw('oi.sh_period_result_id'))
	    ->leftJoin('user AS u' ,DB::raw('oi.user_id'),'=',DB::raw('u.id'))
	    ->leftJoin('activity AS a' ,DB::raw('ap.sh_activity_id'),'=',DB::raw('a.id'))
	    ->leftJoin('goods AS g' ,DB::raw('a.sh_goods_id'),'=',DB::raw('g.id'))
	    ->leftJoin('category AS c' ,DB::raw('g.sh_category_id'),'=',DB::raw('c.id'))
	    ->leftJoin('order_action AS oa' ,DB::raw('oa.order_id'),'=',DB::raw('oi.order_id'))
		->where( DB::raw('u.is_real'), '=' ,  '1')
// 	    ->where( DB::raw('oi.order_status'), '=' ,  '1')
 	    ->where( DB::raw('oa.order_status'), '=' ,  '1');
	    if($shipping_status != null){
	        $table->where( DB::raw('oi.shipping_status'), '=' ,  $shipping_status);
	    }
	    if($luck_code_create_time != null && $luck_code_create_time != ''){
	        $table->where( DB::raw('date_format(pr.luck_code_create_time,\'%Y-%m-%d\')'), '=' ,  $luck_code_create_time);
	    }
	    $table->orderBy(DB::raw('pr.id'), 'desc');
	    $order_list = $table->paginate(10);
	     
	    
	    return $order_list;
	}
	
	/**
	 * 订单详情
	 */
	public function orderDetail($order_id) {
	    
	    $order_detail = DB::table('order_info AS '.DB::getTablePrefix(). 'oi')
		->where( 'oi.order_id' ,  $order_id)
		->where( 'oa.order_status' ,  '1')
	    ->select(
			'oi.order_id',
    	    'oi.order_sn',
    	    'oi.consignee',
    	    'oi.address',
    	    'oi.mobile',
    	    'oi.invoice_no',
    	    'oi.express_company',
		    'u.id as user_id',
			'u.nick_name',
			'u.tel',
			'pr.luck_code',
			'pr.a_code',
			'pr.lottery_code',
			'pr.remainder',
			'pr.luck_code_create_time',
			'ap.period_number',
			'ap.real_need_times',
			 DB::raw('CASE
		        WHEN sh_a.is_online = \'0\' THEN \'已下线\'
		        WHEN sh_a.is_online = \'1\' THEN \'上线\'
		        ELSE \'未知\'
		     END is_online'),
			'g.goods_name',
			'c.cat_name',
			'oa.create_time'
		)
		->leftJoin('user AS '.DB::getTablePrefix(). 'u' ,'u.id','=','oi.user_id')
		->leftJoin('period_result AS '.DB::getTablePrefix(). 'pr' ,'pr.id','=','oi.sh_period_result_id')
		->leftJoin('activity_period AS '.DB::getTablePrefix(). 'ap' ,'ap.id','=','pr.sh_activity_period_id')
		->leftJoin('activity AS '.DB::getTablePrefix(). 'a' ,'a.id','=','ap.sh_activity_id')
		->leftJoin('goods AS '.DB::getTablePrefix(). 'g' ,'g.id','=','a.sh_goods_id')
		->leftJoin('category AS '.DB::getTablePrefix(). 'c' ,'c.id','=','g.sh_category_id')
		->leftJoin('order_action AS '.DB::getTablePrefix(). 'oa' ,'oa.order_id','=','oi.order_id')
        ->get();
	    return $order_detail;
	}
	
	/**
	 * 订单修改
	 */
	public function orderModify($order_id,$express_company,$invoice_no) {
	    
	    $update_res = DB::table('order_info AS '.DB::getTablePrefix().'oi' )
    	    ->where( 'oi.order_id', '=' ,  $order_id)
    	    ->update(array(
				'shipping_time' => date('Y-m-d H:i:s', time()),
				'shipping_status' => '1',
    	        'express_company' => $express_company,
    	        'invoice_no' => $invoice_no
    	    ));
	    return $update_res;
	}
	
	
}

