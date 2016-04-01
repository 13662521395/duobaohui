<?php
//
//
//class UserTest extends TestCase {
//
//
//    /*
//     * 获取验证码自动化测试
//     */
//    // public function testGetVerify(){
//    //     $response = $this->call('GET', '/user/register/get-verify?tel=18612579961');
//    //     $con = json_decode($response->getContent());
//    //     $this->assertTrue($response->isOk());
//    //     if(!$con->code == 20206){
//    //          $this->assertEquals($con->code, 1);
//    //     }
//    //     echo "获取验证码接口\n";
//    //     var_dump($con);
//    //     echo "\n\n";
//    // }
//
//    /*
//     * 用户手机号注册
//     */
//    // public function testTelRegister(){
//    //     $response = $this->call('GET', '/user/register/tel-register?tel=18612579961&password=123456&nick_name=辉仔&code=562938');
//    //     $con = json_decode($response->getContent());
//    //     $this->assertTrue($response->isOk());
//    //     if(!$con->code == 20206){
//    //          $this->assertEquals($con->code, 1);
//    //     }
//    //     echo "用户手机号注册接口\n";
//    //     var_dump($con);
//    //     echo "\n\n";
//
//    // }
//
//
//
//
//
//    /*
//     * 用户手机号登录接口
//     */
//    public function testTokenInfo(){
//
//        //先获取Token
//        Route::enableFilters();
//        $Token     = $this->call('GET', '/user/token');
//        $TokenJson = json_decode($Token->getContent());
//        if($TokenJson->code == 1){
//             $TokenString   =   $TokenJson->data;
//        }
//        $key = md5('123456shinc'.$TokenString);
//        //手机号登录
//        $response = $this->call('GET', '/user/login/login-tel?tel=18612579961&password=123456&key='.$key.'&form_token='.$TokenString);
//        $con = json_decode($response->getContent());
//        if($con->code == 1){
//            $userInfo   =   $con->data;
//            $user_session = array(
//                'id'        => $userInfo->id,
//                'user_id'   => $userInfo->id,
//                'tel'       => $userInfo->tel,
//                'nick_name' => $userInfo->nick_name
//            );
//            $this->session( $user_session );
//        }
//        $this->assertEquals($con->code, 1);
//        $this->assertTrue(is_string($con->data->session_id));
//        //session_id
//        echo '用户手登录SessionId:'.$con->data->session_id."\n\n";
//    }
//
//
//    /*
//     * 获取用户信息
//     */
//    public function testGetUserInfo(){
//        //真实姓名写入数据库失败   realName=梁枫
//        $response = $this->call('GET', '/individual/user/get-user-info?user_id=34543');
//        $con = json_decode($response->getContent());
//        $this->assertEquals($con->code, 1);
//        $this->assertTrue(is_string($con->data[0]->nick_name));
//        // var_dump($con->data[0]->nick_name);
//        echo '获取用户信息:'.$con->data[0]->nick_name."\n\n";
//    }
//
//
//    /*
//     * 修改用户信息
//     */
//    public function testUpdateUserInfo(){
//        //真实姓名写入数据库失败   realName=梁枫
//        $response = $this->call('GET', '/individual/user/update-user-info?user_id=34543&nickName=liangfeng&realName=梁枫&email=1092313007@qq.com&age=22&head_pic=www.baidu.com&sex=1&born=1993-01-13&job=programenter&is_anonymous=1');
//        $con = json_decode($response->getContent());
//        if($con->code === 0){
//            $response = $this->call('GET', '/individual/user/update-user-info?user_id=34543&nickName=liangfeng&realName=liangfeng&email=1092313008@qq.com&age=23&head_pic=www.baidu.com&sex=1&born=1993-01-13&job=programenter&is_anonymous=1');
//            $con = json_decode($response->getContent());
//        }
//        $this->assertEquals($con->code, 1);
//        echo '修改用户信息:'.$con->msg."\n\n";
//
//    }
//
//
//
//    /*
//     * 手机号重置密码时验证手机号
//     */
//    // public function resetGetVerify(){
//    //     //真实姓名写入数据库失败   realName=梁枫
//    //     $response = $this->call('GET', '/user/reset/get-verify?tel=18612579961');
//    //     $con = json_decode($response->getContent());
//    //     $this->assertEquals($con->code, 1);
//    //     echo '重置密码时验证:'.$con->msg."\n\n";
//    // }
//
//
//    /*
//     * 手机号重置密码
//     */
//    public function testRegTel(){
//        //真实姓名写入数据库失败   realName=梁枫
//        $response = $this->call('GET', '/user/reset/reg-tel?tel=18612579961&password=123456&code=147895&type=0');
//        $con = json_decode($response->getContent());
//        $this->assertTrue($response->isOk());
//        if($con->code === 20208){
//           // $this->resetGetVerify();
//           echo '手机号重置密码失败,您需要重写test的验证码:'.$con->msg."\n";
//        }elseif($con->code === 0){
//           echo '手机号重置密码失败,您需要重新设置密码:'.$con->msg."\n";
//        }
//        // $this->assertEquals($con->code, 1);
//        echo '手机号重置密码状态:'.$con->msg."\n\n\n";
//
//
//
//
//        echo "自动化测试PHPUnit结束\n\n";
//    }
//
//
//}
