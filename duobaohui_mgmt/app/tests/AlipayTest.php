<?php
/*
 * 支付夺宝
 */
class AlipayTest extends TestCase {

	/*
	 * 支付宝购买
	 */
	public function testNotify(){
		// 模拟支付宝返回数据
		$verify_result = Array(
			'discount' => 0.00,
			'payment_type' => 1,
			'subject' => 'iPhone6 4.7英寸 128G',
			'trade_no' => 2015101300001000340065586579,
			'buyer_email' => 13240372487,
			'gmt_create' => '2015-10-13 19:43:03',
			'notify_type' => 'trade_status_sync',
			'quantity' => 1,
			'out_trade_no' => 101319425912922,
			'seller_id' => 2088911708976095,
			'notify_time' => '2015-10-13 19:43:04',
			'body' => 2,
			'trade_status' => 'TRADE_SUCCESS',
			'is_total_fee_adjust' => 'N',
			'total_fee' => 0.01,
			'seller_email' => 'admin@shinc.net',
			'price' => 0.01,
			'buyer_id' => 2088712044133340,
			'use_coupon' => 'N',
			'sign_type' => 'RSA',
			'sign' => 'mVd5lMtnAXEhSjv7ZQfQjeVkTH8kJGm2Hj/9CbZwAf32Us//aiDFSn9xmzlYQIcAt/HsJmMb/dU/FaTkXoBeMB21z+RPcYiizLjtpaxjgEhr75O9ESZVbxzLqiBxAh2J7eieBYofd4P03+PeQNZyVZV2Xm7jhi/t5cqMfVUZp8A='
		);

		// 利用缓存调度，模拟支付接口返回数据, 
		Cache::put('phpunitTest', array('func'=>'notify','verify_result'=>$verify_result), 1);

		Route::enableFilters(); // 开启过滤

		echo "支付宝购买:\n";
		// 先获取令牌
		$response = $this->call('GET', '/user/token');
		$tokenCon = json_decode($response->getContent());
		$token = $tokenCon->data;

		// 生成订单
		
		
		// 支付
		$response = $this->call('GET', '/alipay/notifyUrl/notify?activity_period_id=121&num=1&buy_order_id=1&buy_id=1&form_token='.$token);
		
		//$con = json_decode($response->getContent());
		$con = $response->getContent();
		if($con!='success'){
			$con = json_decode($response->getContent());
			print_r($con);
		}
		$this->assertEquals($con, 'success');

		

	}

	/*
	 * 多宝币购买
	 */
	public function testBuy(){
		$user['user'] =  array(
			'id'        => 17,
			'user_id'   => 17,
			'tel'       => 18612579961,
		);
		//$this->session($user);
		//$this->flushSession();
	}


	/*
	 * 充值多宝币
	 */



}
