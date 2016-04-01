<?php
/*
 * 用户模块自动化测试类
 * 异常情况将调用发短信接口通知警报
 * /user/register/send-exception?tel=18612579961
 * 建议短信通知使用旧版接口，方便错误提示。
 * author:liangfeng@shinc.net
 * time : 2015-12-07 15:34:00
 *
 * 使用说明：该程序为1.2版本接口的全自动化测试，如果有异常且不是网络延迟的错误将会自动发送手机短信提示。
 *
 * 命令行执行：/Applications/MAMP/bin/php/php5.6.7/bin/php phpunit.phar
 *
 * 迁移到不同环境或跟换数据库数据时，需要添加一个完善测试信息的账号，并检查自动化测试中所填写的参数是否对测试数据一致。
 *
 * 自动化测试分为读写自动化测试，写的自动化测试默认处于关闭状态
 *
 * 读的操作只需要修改构造方法中的user_id 即可。
 *
 */
date_default_timezone_set('PRC');

use App\Libraries\Smtp;
class UserTest extends TestCase
{
    protected $send_flag                = '';
    protected $conf                     = '';

    //读部分自动化测试  参数配置
    protected $user_id                  = '';
    protected $tel                      = 18612579961;   //手机号
    protected $code                     = 123456;       //验证码
    protected $period_id                = 2030;
    protected $order_status             = 0;
    protected $flag                     = 0;            //
    protected $sh_activity_period_id    = 4476;         //活动期数ID
    protected $sh_period_user_id        = 1003807;      //活动参与用户ID
    protected $sh_activity_id           = 113;          //活动ID
    protected $goods_id                 = 100;          //商品ID
    protected $pay_type                 = '1';          //支付类型
    protected $total_fee                = '20';         //总价
    protected $jnl_no                   = 'FB10494009970516';   //订单号
    protected $amount                   = 1;            //金额
    protected $recharge_channel         = 0;            //交易渠道


    public function __construct(){
        $this->conf     = dirname(dirname(dirname(__FILE__))).'/config/app.php';
        $this->user_id  = 1890;
    }

    /*********************************************
     *
     *
     *              读部分自动化测试
     *
     *
     * *******************************************
     */

    public function LoginSms(){
        $api_msg = '1、快速登录';
        $url     = 'user/login/login-sms?tel='.$this->tel.'&code='.$this->code;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function SendLogSms(){
        $api_msg = '2、快速登录获取验证码';
        $url     = 'user/login/send-login-sms?tel='.$this->tel;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testAddressList(){
        $api_msg = '3、获取地址列表信息';
        $url     = 'user/address/address-list?user_id='.$this->user_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testBanner(){
        $api_msg = '7、首页banner接口';
        $url     = 'activity/index/banner';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testNewestwinninglist(){
        $api_msg = '8、首页，小喇叭（获取最近中奖者的信息）';
        $url     = 'activity/record/newestwinninglist';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testIndexList(){
        $api_msg = '9、活动奖品列表';
        $url     = 'activity/index/list';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testPeriodUser(){
        $api_msg = '10、参与记录';
        $url     = 'activity/index/period-user?period_id='.$this->period_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testIndexDetail(){
        $api_msg = '11、获取商品详情的接口';
        $url     = 'activity/index/detail?period_id='.$this->period_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testOrderstatus(){
        $api_msg = '12、中奖记录 ~ 获得总值';
        $url     = 'order/status/orderstatus';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testOrderlistbystatus(){
        $api_msg = '13、中奖记录';
        $url     = 'order/status/orderlistbystatus?order_status='.$this->order_status.'&user_id='.$this->user_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testRecordRecords(){
        $api_msg = '15、夺宝记录';
        $url     = 'activity/record/records?flag='.$this->flag.'&user_id='.$this->user_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testRecordCountdetail(){
        $api_msg = '16、计算详情';
        $url     = 'activity/record/countdetail?sh_activity_period_id='.$this->sh_activity_period_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testRecordLotterytimeslistbyperiodid(){
        $api_msg = '17、夺宝次数';
        $url     = 'activity/record/lotterytimeslistbyperiodid?sh_activity_period_id='.$this->sh_activity_period_id.'&user_id='.$this->user_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testRecordLotterycodelistbyperiodid(){
        $api_msg = '18、本期、本次 夺宝号列表（查看我的号码）';
        $url     = 'activity/record/lotterycodelistbyperiodid?sh_activity_period_id='.$this->sh_activity_period_id.'&sh_period_user_id='.$this->sh_period_user_id.'&user_id='.$this->user_id;
        return $this->autoSessionTest( $api_msg , $url);
    }

    public function testDetailUsercode(){
        $api_msg = '19、用户的夺宝后的详细信息';
        $url     = 'activity/index/detail-usercode?period_id='.$this->period_id.'&user_id='.$this->user_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testGetShaidan(){
        $api_msg = '21、获取晒单列表';
        $url     = 'user/shaidan/get-shaidan?user_id='.$this->user_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testRechargeRecord(){
        $api_msg = '22、充值';
        $url     = 'recharge/recharge/recharge-record?user_id='.$this->user_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testWinninghistorylist(){
        $api_msg = '23、往期揭晓';
                $url     = 'activity/record/winninghistorylist?sh_activity_id='.$this->sh_activity_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testGoodsDesc(){
        $api_msg = '24、图文详情接口';
        $url     = 'activity/index/goods-desc?goods_id='.$this->goods_id;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testOrderRecharge(){
        $api_msg = '26、充值记录';
        $url     = 'recharge/recharge/order-recharge?pay_type='.$this->pay_type.'&total_fee='.$this->total_fee;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testIndexResult(){
        $api_msg = '28、等待揭晓';
        $url     = 'activity/index/result';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function DuobaoBalance(){
        $api_msg = '31、夺宝币支付接口';
        $url     = 'period/duobao/duobao-balance?jnl_no='.$this->jnl_no;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testRechargeConfirm(){
        $api_msg = '33、充值时获取充值的订单号';
        $url     = 'recharge/recharge/recharge-confirm?amount='.$this->amount.'&recharge_channel='.$this->recharge_channel;
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function Weixinapi()
    {
        $api_msg = '34、微信充值时获取充值的订单号';
        $url = 'alipay/weixinapi/wx-pay';
        return $this->autoSessionTest($api_msg, $url);
    }

    public function UserCodeList()
    {
        $api_msg = '35、支付结果访问的接口';
        $url = 'alipay/notifyUrl/get-user-code-list';
        return $this->autoSessionTest($api_msg, $url);
    }

    public function testGetMoney()
    {
        $api_msg = '36、更新用户的信息';
        $url = 'recharge/recharge/get-money';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testRecordRecordsNum()
    {
        $api_msg = '39、夺宝记录Tab上的提示数量接口';
            $url = '/activity/record/records-num';
        return $this->autoSessionTest($api_msg, $url);
    }

    public function testTauserDuobaoRecord()
    {
        $api_msg = '40、TA的主页－夺宝纪录';
        $url = 'tauser/duobao-record?user_id='.$this->user_id;
        return $this->autoSessionTest($api_msg, $url);
    }

    public function testTauserWinningRecord()
    {
        $api_msg = '41、TA的主页－中奖纪录';
        $url = 'tauser/winning-record?user_id='.$this->user_id;
        return $this->autoSessionTest($api_msg, $url);
    }

    public function testTauserShaidanRecord()
    {
        $api_msg = '42、TA的主页－晒单记录';
        $url = 'tauser/shaidan-record';
        return $this->autoSessionTest($api_msg, $url);
    }

    public function testTausergetnum()
    {
        $api_msg = '43、TA的主页－Tab（num）';
        $url = 'tauser/get-num?user_id='.$this->user_id;
        return $this->autoSessionTest($api_msg, $url);
    }

    public function testNoticeLatestDate()
    {
        $api_msg = '45、检查消息的事件戳；（用于辨别有咩有新的消息）';
        $url = 'system/notice/latest-date';
        return $this->autoSessionTest($api_msg, $url);
    }

    public function testIndexListsearch()
    {
        $api_msg = '46、分类数据接口（同时也是兼顾搜索）';
        $url = 'activity/index/list';
        return $this->autoSessionTest($api_msg, $url);
    }

    public function testIndexcategory()
    {
        $api_msg = '47、获取分类数据的接口地址';
        $url = 'activity/index/category';
        return $this->autoSessionTest($api_msg, $url);
    }

    public function testSystemnoticelist()
    {
        $api_msg = '48、通知';
        $url = 'system/notice/system-notice-list';
        return $this->autoSessionTest($api_msg, $url);
    }

    public function testRecordWinningrecords()
    {
        $api_msg = '49、中奖纪录,V1.2';
        $url = 'activity/record/winning-records';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function testRecordWinningdetail()
    {
        $api_msg = '50、中奖纪录 item详情,V1.2';
        $url = 'activity/record/winning-detail?sh_activity_period_id='.$this->sh_activity_period_id.'&user_id='.$this->user_id;
        return $this->autoSessionTest( $api_msg , $url );
    }


    /***********************************************
     *
     *
     *
     *              写部分自动化测试   默认关闭
     *
     *
     *
     * *********************************************
     */

    public function AddAddress(){
        $api_msg = '4、添加收货地址';
        $url     = 'user/address/add-address?name=liang&mobile=18612579951&area=guangxi&address=yulin';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function AddressEdit(){
        $api_msg = '5、根据地址Id,修改该地址';
        $url     = 'user/address/address-edit?address_id=131&name=feng&mobile=18612579951&area=nanning&address=qingxiu&isDefault=1';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function AddressDelete(){
        $api_msg = '6、根据地址Id,删除该地址';
        $url     = 'user/address/address-delete?address_id=132';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function Updateorderstatus(){
        $api_msg = '14、修改订单状态 ~ 待确认';
        $url     = 'order/status/updateorderstatus?order_id=3865&order_status=1&shipping_status=1&user_id=1227';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function AddShaidan(){
        $api_msg = '20、添加晒单';
        $url     = 'user/shaidan/add-shaidan?user_id=1227&content=testtesttest&title=testtesttest&order_id=236&goods_id=24';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function RegNickname(){
        $api_msg = '25、修改昵称';
        $url     = 'user/reset/reg-nickname?nick_name=liangfeng';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function ResetHeadPic(){
        $api_msg = '27、修改头像';
        $url     = 'user/reset/head-pic?head_pic=www.baidu.com/img/2432312.jpg';
        return $this->autoSessionTest( $api_msg , $url  );
    }

    public function DuobaoConfirm(){
        $api_msg = '32、添加订单的接口';
        $url     = 'period/duobao/duobao-confirm?period_id=4857&num=5';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function AddOpinion()
    {
        $api_msg = '37、意见反馈';
        $url = 'system/opinion/add-opinion?content=dsfsafjsdsdsjhghgyfy';
        return $this->autoSessionTest($api_msg, $url);
    }

    public function NotifyUrlBuyOrder()
    {
        $api_msg = '38、传给后台生成夺宝订单';
        $url = 'alipay/notifyUrl/buy-order?period_id=50&num=10';
        return $this->autoSessionTest( $api_msg , $url );
    }

    public function ReportShaidan()
    {
        $api_msg = '44、举报晒单接口';
        $url = 'report/report/report-shaidan?shaidan_id=64&report_id=3&user_id=39193';
        return $this->autoSessionTest($api_msg, $url);
    }




    /*
     * 通用测试模块方法
     * $api_msg   string    接口信息
     * $method    string    请求方法
     * $url       string    接口地址
     */
    public function autoSessionTest($api_msg , $url)
    {
        $conf =require $this->conf;
        $this->publicWay($api_msg , $url , 'http://'.$conf['Cookie-Key']);

        if(!empty($this->send_flag)){
           for($i=0;$i<2;$i++){
               sleep(10);
               $this->publicWay($api_msg , $url , 'http://'.$conf['Cookie-Key'] );

               if($i==1)
                   $this->sendSmtp($this->send_flag);
                   //$this->curlGet($this->send_flag , 'http://'.$conf['Cookie-Key']);
           }
        }
    }


    private function curlGet($api_msg , $conf){
        $ch=curl_init($conf.'/user/register/send-exception?tel=18612579961&code='.$api_msg);
        curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
        curl_setopt($ch,CURLOPT_BINARYTRANSFER,true);
        $output=curl_exec($ch);
        echo $output;
    }

    private  function publicWay($api_msg , $url , $conf ){
        try {

            $user['user'] = array(
                'id'      => $this->user_id,
                'user_id' => $this->user_id,
                'tel'     => 18612579961,
                'nick_name' => 'liang'
            );
            $this->session($user);

            $response = $this->call('GET', $conf.'/'.$url);
            $con = json_decode($response->getContent());
            $this->assertTrue($response->isOk());
            $this->assertTrue(is_int($con->code));

            if($con->code == 1 || $con->code == 0) {

            }
            else {
                echo $msg = '【执行时间:'.date('Y-m-d H:i:s').'】    '.$api_msg . 'response错误信息    【状态码:' . $con->code . "  描述:" . $con->msg .'】   '.$url. "\n\n";
                file_put_contents(dirname(__FILE__).'/test-log.log',print_r($msg,1),FILE_APPEND);
                $this->send_flag = $msg;
            }
        } catch (\Exception $e) {
            echo '【执行时间:'.date('Y-m-d H:i:s').'】   '.$api_msg.$url . '代码错误' . $e . "\n\n";
            $msg = '【执行时间:'.date('Y-m-d H:i:s').'】   '.$api_msg. "程序异常错误    $url \n\n";
            file_put_contents(dirname(__FILE__).'/test-log.log',print_r($msg,1),FILE_APPEND);
            $this->send_flag = $msg;
        }
    }



    private function sendSmtp($send_flag){
        $smtpserver         =   'smtp.126.com';
        $smtpserverport     =   25;
        $smtpusermail       =   "breeze323136@126.com";
        $smtpemailto        =   "1092313007@qq.com";
        $smtpuser           =   "breeze323136";
        $smtppass           =   "liang15521755250";
        $mailsubject        =   "自动监控异常警告";
        $mailbody           =   $send_flag;
        $mailtype           =   "txt";
        $smtp               =   new smtp($smtpserver,$smtpserverport,true,$smtpuser,$smtppass);
        $smtp->debug        =   false;
        if($smtp->sendmail($smtpemailto,$smtpusermail,$mailsubject,$mailbody,$mailtype)=="1"){
            echo "已发送";
        }else{
            echo "发送失败";
        }
    }

    
}