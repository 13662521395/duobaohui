<?php

class AddressTest extends TestCase {

	 /*
     * 获取收货列表
     */
    public function testAddressList(){
        $response = $this->call('GET', '/user/address/address-list',array(
                'user_id'   =>  16
            ));
        $con = json_decode($response->getContent());
        $this->assertEquals($con->code, 1);
        $this->assertTrue(is_int($con->data[0]->address_id));
        echo '收货列表数量:'.count($con->data)."\n\n";
        // var_dump($con->data);
    }


    /*
     * 添加收货地址
     */
    public function testAddAddress(){
        $response = $this->call('GET', '/user/address/add-address?user_id=34543&name=shouhuodizhi&mobile=18612579961&area=guangxiyulin&address=hello world');
        $con = json_decode($response->getContent());
        $this->assertEquals($con->code, 1);
        echo '添加收货地址ID:'.$con->data."\n\n";
    }


    /*
     * 修改收货地址
     */
    public function testAddressEdit(){

        $response = $this->call('GET', '/user/address/address-edit?user_id=34543&address_id=196&name=liangfeng&mobile=18401586654&area=66&address=beijing&isDefault=1');
        $con = json_decode($response->getContent());
        if($con->code === 0){
            $response = $this->call('GET', '/user/address/address-edit?user_id=34543&address_id=196&name=liangfeng&mobile=18612579961&area=66&address=beijing&isDefault=1');
            $con = json_decode($response->getContent());
        }
        $this->assertEquals($con->code, 1);
        echo '修改收货地址状态:'.$con->msg."\n\n";
        
    }


    /*
     * 删除收货地址
     */
    public function testAddressDelete(){
        $response = $this->call('GET', '/user/address/address-delete?address_id=205');
        $con = json_decode($response->getContent());
        if($con->code === 0){
            for ($i=204; $i < 1000; $i++) { 
                $response = $this->call('GET', '/user/address/address-delete?address_id='.$i);
                $con = json_decode($response->getContent());
                if($con->code === 1){
                    break;
                }
                if($con->code === 0){
                    continue;
                }
            }
        }
        $this->assertEquals($con->code, 1);
        echo '删除收货地址状态:'.$con->msg."\n\n";
    }

}