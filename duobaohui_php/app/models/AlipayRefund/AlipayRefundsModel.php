<?php
/*
 *支付宝支付异步服务器接收支付业务逻辑操作
 *
 *@aythor	  majianchao@shinc.net
 *@version    v1.0
 *@copyright  shinc
 */

namespace  Laravel\Model\AlipayRefund;

use Illuminate\Support\Facades\DB;


class  AlipayRefundsModel extends  Model{

	/*
	 * 对支付回调参数进行操作
	 *
	 *@param	array		$data		支付宝的回调参数
	 *
	 *@return	boolean		true/false  数据是否通过
	 * */
	protected  $alipay_refund  = 'alipay_refund';
	
	public  function  addAlipay($data){
		DB::table('alipay_refund')
		->insert($data);
	}

}

?>
