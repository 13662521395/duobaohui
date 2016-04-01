<?php

class OrderTest extends TestCase {

	/**
	 * 订单状态统计接口
	 */
	public function testOrderstatus(){
		$user['user'] =  array(
                'id'        => 17,
                'user_id'   => 17,
                'tel'       => 18612579961,
            );
		$this->session($user);
        $response = $this->call('GET', '/order/status/orderstatus');
        $con = json_decode($response->getContent());
        $this->assertEquals($con->code, 1);
        $this->assertTrue(is_int($con->data[0]->order_status_count_0));
        echo '订单状态统计数量:'.count($con->data)."\n\n";
	}





	/**
	 * 通过订单状态查询订单列表
	 */
	public function testOrderlistbystatus(){
		$user['user'] =  array(
                'id'        => 17,
                'user_id'   => 17,
                'tel'       => 18612579961,
            );
		$this->session($user);
        $response = $this->call('GET', '/order/status/orderlistbystatus?order_status=0');
        $con = json_decode($response->getContent());
        $this->assertEquals($con->code, 1);
        $this->assertTrue(is_string($con->data[0]->goods_name));
      
        echo '通过订单状态查询订单列表数量:'.count($con->data)."\n\n";
	}




	/**
	 * 更新订单状态
	 */
	public function testUpdateorderstatus(){
		$user['user'] =  array(
                'id'        => 17,
                'user_id'   => 17,
                'tel'       => 18612579961,
            );
		$this->session($user);
        $response = $this->call('GET', '/order/status/updateorderstatus?order_id=80&order_status=1&shipping_status=0');
        $con = json_decode($response->getContent());
        if($con->code === 0){
            $response = $this->call('GET', '/order/status/updateorderstatus?order_id=80&order_status=1&shipping_status=1');
            $con = json_decode($response->getContent());
        }
        $this->assertEquals($con->code, 1);
        echo '更新订单状态:'.$con->msg."\n\n";
	}


}