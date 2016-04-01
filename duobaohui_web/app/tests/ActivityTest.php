<?php
class ActivityTest extends TestCase {
	/*
	 * 首页-banner
	 */
	public function testBanner(){
		$response = $this->call('GET', '/activity/index/banner');
		$con = json_decode($response->getContent());
		if(!$con){
			echo '页面报错'."\n";
		}
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_string($con->data[0]->pic_url));
		echo 'banner数量:'.count($con->data)."\n\n";
	}

	/*
	 * 首页-活动列表
	 */
	public function testActivityList(){
		$response = $this->call('GET', '/activity/index/list');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_string($con->data[0]->goods_img));
		echo '夺宝活动数量:'.count($con->data)."\n\n";
	}

	/*
	 * 图文详情
	 */
	public function testGoodsDesc(){
		$response = $this->call('GET', '/activity/index/goods-desc?goods_id=100');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_string($con->data->goods_desc));
		echo '图文详情数量:'.count($con->data)."\n\n";

	}


	/*
	 * 奖品详情
	 */
	public function testDetail(){
		$response = $this->call('GET', '/activity/index/detail?period_id=10');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_string($con->data->goods_name));
		echo '奖品详情商品:'.$con->data->goods_name."\n\n";
	}


	/*
	 * 参与记录
	 */
	public function testPeriodUser(){
		$response = $this->call('GET', '/activity/index/period-user?period_id=10');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_string($con->data[0]->nick_name));
		echo '参与记录人昵称:'.$con->data[0]->nick_name."\n\n";
	}




	/*
	 * 即将揭晓页面
	 */
	public function testResult(){
		$response = $this->call('GET', '/activity/index/result');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_string($con->data[0]->goods_name));
		echo '即将揭晓页面:'.$con->data[0]->goods_name."\n\n";
	}



	/*
	 * 购买的号码
	 */
	public function testDetailUsercode(){
		$user['user'] =  array(
                'id'        => 21,
                'user_id'   => 21,
                'tel'       => 18612579961,
            );
		$this->session($user);
		$response = $this->call('GET', '/activity/index/detail-usercode?period_id=1');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_int($con->data[0]));
		echo '购买的号码:'.$con->data[0]."\n\n";
	}




	/**
	 * 喇叭（首页）
	 * @param
	 * @return object
	 */
	public function testNewestwinninglist(){
		$response = $this->call('GET', '/activity/record/newestwinninglist');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_string($con->data[0]->nick_name));
		echo '喇叭（首页）:'.$con->data[0]->nick_name."\n\n";
	}



	/**
	 * 夺宝纪录（我的，夺宝纪录）
	 */
	public function testRecords(){
		$user['user'] =  array(
                'id'        => 21,
                'user_id'   => 21,
                'tel'       => 18612579961,
            );
		$this->session($user);
		$response = $this->call('GET', '/activity/record/records');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_string($con->data[0]->goods_name));
		echo '夺宝纪录（我的，夺宝纪录）:'.$con->data[0]->goods_name."\n\n";
	}




	/**
	 * 本期夺宝次数列表（查看我的号码）
	 */
	public function testLotterytimeslistbyperiodid(){
		$user['user'] =  array(
                'id'        => 21,
                'user_id'   => 21,
                'tel'       => 18612579961,
            );
		$this->session($user);
		$response = $this->call('GET', '/activity/record/lotterytimeslistbyperiodid?sh_activity_period_id=1');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_int($con->data[0]->sh_period_user_id));
		echo '本期夺宝次数列表（查看我的号码）:'.$con->data[0]->sh_period_user_id."\n\n";
	}




	/**
	 * 本期、本次 夺宝号列表（查看我的号码）
	 */
	public function testLotterycodelistbyperiodid(){
		$user['user'] =  array(
                'id'        => 21,
                'user_id'   => 21,
                'tel'       => 18612579961,
            );
		$this->session($user);
		$response = $this->call('GET', '/activity/record/lotterycodelistbyperiodid?sh_activity_period_id=1&sh_period_user_id=5');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_int($con->data[0]->code_num));
		echo '本期、本次 夺宝号列表（查看我的号码）:'.$con->data[0]->code_num."\n\n";
	}



	/**
	 * 计算详情（详情页）
	 */
	public function testCountdetail(){
		$response = $this->call('GET', '/activity/record/countdetail?sh_activity_period_id=1');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_array($con->data->lottery_code_list));
		echo '计算详情（详情页）:'.count($con->data->lottery_code_list)."\n\n";

	}




	/**
	 * 往期揭晓（详情页）
	 */
	public function testWinninghistorylist(){
		$response = $this->call('GET', '/activity/record/winninghistorylist?sh_activity_id=10&period_number=10000389');
		$con = json_decode($response->getContent());
		$this->assertEquals($con->code, 1);
		$this->assertTrue(is_string($con->data[0]->luck_code));
		echo '往期揭晓（详情页）:'.$con->data[0]->luck_code."\n\n";
	}



	/**
     * 扫二维码送红包
     * @param tel 手机号
     */
    public function testGiveredpacket() {

    	$response = $this->call('GET', '/activity/share/giveredpacket?tel=15600598762');
		$con = json_decode($response->getContent());
		$this->assertTrue(is_string($con->data->tel));
		if($con->code === 20503){
			$this->assertEquals($con->code, 20503);
		}
		if($con->code === 20501){
			$this->assertEquals($con->code, 20501);
			
		}
		echo '扫二维码送红包:'.$con->data->tel.":".$con->msg."\n\n";
    }






























	/*
	 * 模拟支付
	 */
	public function testNotify(){
		$verifyResult = Array(
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
				'total_fee' => 20,
				'seller_email' => 'admin@shinc.net',
				'price' => 0.01,
				'buyer_id' => 2088712044133340,
				'use_coupon' => 'N',
				'sign_type' => 'RSA',
				'sign' => 'mVd5lMtnAXEhSjv7ZQfQjeVkTH8kJGm2Hj/9CbZwAf32Us//aiDFSn9xmzlYQIcAt/HsJmMb/dU/FaTkXoBeMB21z+RPcYiizLjtpaxjgEhr75O9ESZVbxzLqiBxAh2J7eieBYofd4P03+PeQNZyVZV2Xm7jhi/t5cqMfVUZp8A='
			);
		//var_dump($verifyResult);
	}


}
