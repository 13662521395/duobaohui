<?php

class RechargeTest extends TestCase {

	/*
     * 获取充值记录
     */
	public function testRecharge(){
		$user['user'] =  array(
                'id'        => 34543,
                'user_id'   => 34543,
                'tel'       => 18612579961,
            );
		$this->session($user);
        $response = $this->call('GET', '/recharge/recharge/recharge');
        $con = json_decode($response->getContent());
        $this->assertEquals($con->code, 1);
        $this->assertTrue(is_int($con->data[0]->sh_user_id));
        echo '获取充值记录数量:'.count($con->data)."\n\n";
	}


	/*
     * 未付款下订单
     */
	public function testOrderRecharge(){
		$user['user'] =  array(
                'id'        => 34543,
                'user_id'   => 34543,
                'tel'       => 18612579961,
            );
		$this->session($user);
        $response = $this->call('GET', '/recharge/recharge/order-recharge?pay_type=1&total_fee=20');
        $con = json_decode($response->getContent());
        $this->assertEquals($con->code, 1);
        echo '未付款下订单状态:'.$con->msg."\n\n";
	}



	/*
     * 获取用户余额
     */
	public function testGetMoney(){
		$user['user'] =  array(
                'id'        => 34543,
                'user_id'   => 34543,
                'tel'       => 18612579961,
            );
		$this->session($user);
        $response = $this->call('GET', '/recharge/recharge/get-money');
        $con = json_decode($response->getContent());
        $this->assertEquals($con->code, 1);
        $this->assertTrue(is_string($con->data->nick_name));
        echo '获取用户余额:'.$con->data->money."\n\n";
        // var_dump($con->data);
	}

}