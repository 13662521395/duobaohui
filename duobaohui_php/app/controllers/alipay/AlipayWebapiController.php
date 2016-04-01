<?php
/* *
 * IOS wap网页支付接口
 */
namespace  Laravel\Controller\Alipay; //定义命名空间

use  ApiController;  //引入接口公共父类，用于继承
use  Illuminate\Support\Facades\View; //引入视图类
use  Illuminate\Support\Facades\Input;//引入参数类
use  Illuminate\Support\Facades\Session;
use  Illuminate\Support\Facades\Response;
use  App\Libraries\alipayWebConfig;
use  App\Libraries\PaySubmit;
use Laravel\Model\RechargeModel;
use Illuminate\Support\Facades\Cache;


class  AlipayWebapiController extends  ApiController
{
    protected $token;

    public function __construct(){
        parent::__construct();
    }


    /*
     *
     */

    public function anyWebapi(){
        if(!Input::has('total') || !Input::has('nperStr') || !Input::has('goodId') || !Input::has('goodName') || !Input::has('goodDescribe') ){
            return Response::json( $this->response( '10005'));
        }

        if(!Input::has('token')){
            return Response::json( $this->response( '0','9999','no login'));
        }

        $token       = Input::get('token');
        if(!Cache::has($token)){
            return Response::json( $this->response( '10006'));
        }

        $user_id     = Cache::get($token);

        $this->token = Cache::get($token);

        $recharge    = new RechargeModel();

        //获取充值记录
        $res         = $recharge->getUserMoney( $user_id );
        if( !$res ) {
            return Response::json( $this->response( '10018'));
        }

        $data = array(
            'total'         => Input::get('total'),
            'nperStr'       => Input::get('nperStr'),
            'userMoney'     => $res->money,
            'renci'         => intval(Input::get('total')),
            'goodName'      => Input::get('goodName'),
            'goodDescribe'  => Input::get('goodDescribe'),
            'out_trade_no'  => Input::get('goodId')
        );

        return View::make('alipay.alipay2',$data);
    }

    public function getReturnInfo(){
        if(!Input::has('trade_status')){
            return Response::json( $this->response( '10006'));
        }
        if(Input::get('trade_status') != 'TRADE_SUCCESS'){
            return Response::json( $this->response( '10021'));
        }
        $jnl_no         = Input::get('out_trade_no');
        $pay_type       = 0;

        $recharge       = new RechargeModel();
        $getPeriod      = $recharge->getPeriodId($jnl_no);  //sh_activity_period_id

        if($getPeriod){
            $periodId   = $getPeriod->sh_activity_period_id;
            $activityId = $getPeriod->sh_activity_id;
        }else{
            $periodId   = 0;
            $activityId = 0;
        }

        $total_fee      = Input::get('total_fee');
        $data = array(
            'jnl_no'    => $jnl_no,
            'pay_type'  => $pay_type,
            'amount'    => $total_fee,
            'renci'     => intval($total_fee),
            'nperStr'   => $activityId,
            'goodName'  => Input::get('body'),
            'period_id' => $periodId
        );

        Cache::forget($this->token);  //支付成功后释放内存

        return View::make('alipay.alipay_ok',$data);

    }


    public function getReturnFaild(){
        $data = array(
            'msg' => Input::get('msg')
        );
        return View::make('alipay.alipay_failed',$data);
    }




    public function curlPayAfter( $url , $pay_type=''){
        $ch=curl_init('http://'.Input::getHttpHost().$url.'&pay_type='.$pay_type.'&recharge_channel=0');
        curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
        curl_setopt($ch,CURLOPT_BINARYTRANSFER,true);
        $output=curl_exec($ch);
        return $output;
    }



    /* *
     * 功能：手机网站支付接口接入页
     * 版本：3.3
     * 修改日期：2012-07-23
     * 说明：
     * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
     * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

     *************************注意*************************
     * 如果您在接口集成过程中遇到问题，可以按照下面的途径来解决
     * 1、商户服务中心（https://b.alipay.com/support/helperApply.htm?action=consultationApply），提交申请集成协助，我们会有专业的技术工程师主动联系您协助解决
     * 2、商户帮助中心（http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9）
     * 3、支付宝论坛（http://club.alipay.com/read-htm-tid-8681712.html）
     * 如果不想使用扩展功能请把扩展功能参数赋空值。
     */
    public function getWebpay(){
        $alipayObj  = new alipayWebConfig();
        $alipay_config = $alipayObj->config();
        $alipay_config['seller_id']	= $alipay_config['partner'];
        /**************************请求参数**************************/
        //debug($alipay_config);
        if(!Input::has('out_trade_no') || !Input::has('subject') || !Input::has('total_fee') || !Input::has('body') ){
            return Response::json( $this->response( '10005'));
        }

        $redpacket   = Input::has('redpacket_id') ? Input::get('redpacket_id') : '0';
        file_put_contents('log.txt',"\n\n红包参数".print_r($redpacket,1),FILE_APPEND);

        //支付类型
        $payment_type = "1";
        //必填，不能修改
        //服务器异步通知页面路径

        $notify_url = 'http://'.Input::getHttpHost().'/callback/aliwebpay/callback';
        //需http://格式的完整路径，不能加?id=123这类自定义参数


        //商户订单号
        $out_trade_no = Input::get('out_trade_no');
        //商户网站订单系统中唯一订单号，必填

        //返回地址
        //$return_url  = 'http://'.Input::getHttpHost().'/alipay/notifyUrl/get-user-code-list?buy_id='.$out_trade_no.'&pay_type=0';
        $return_url = 'http://'.Input::getHttpHost().'/alipay/webapi/return-info';
        //debug($return_url);
        //订单名称
        $subject = Input::get('subject');
        //必填

        //付款金额
        $total_fee = Input::get('total_fee');
        //必填

        //商品展示地址
        //$show_url = $_POST['WIDshow_url'];
        //必填，需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

        //订单描述
        $body = Input::get('body');
        //选填

        //超时时间
        $it_b_pay = '120m';
        //选填

        //钱包token
        //$extern_token = $_POST['WIDextern_token'];
        //选填

        //debug($alipay_config);
        /************************************************************/

        //构造要请求的参数数组，无需改动
        $parameter = array(
            "service" => "alipay.wap.create.direct.pay.by.user",
            "partner" => trim($alipay_config['partner']),
            "seller_id" => trim($alipay_config['seller_id']),
            "payment_type"	=> $payment_type,
            "notify_url"	=> $notify_url,
            "return_url"	=> $return_url,
            "out_trade_no"	=> $out_trade_no,
            "subject"	=> $subject,
            "total_fee"	=> $total_fee,
            //"show_url"	=> $show_url,
            "body"	=> $body,
            "it_b_pay"	=> $it_b_pay,
            //"extern_token"	=> $extern_token,
            "_input_charset"	=> trim(strtolower($alipay_config['input_charset'])),
            'redpacket_id'      => $redpacket,
        );

        //debug($parameter);
        //建立请求
        $alipaySubmit = new PaySubmit($alipay_config);
        $html_text = $alipaySubmit->buildRequestForm($parameter,"get", "确认");
        echo $html_text;
        //return Response::json( $this->response( '1'));
    }



}
