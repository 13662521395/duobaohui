<?php
/* *
 * 功能：账务明细分页查询接口接入页
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
namespace  Laravel\Controller\Alipay; //定义命名空间

use  ApiController;  //引入接口公共父类，用于继承
use  Illuminate\Support\Facades\View; //引入视图类
use  Illuminate\Support\Facades\Input;//引入参数类

use Laravel\Model\NotifyUrlModel;//引入model

use App\Libraries\accountConfig;
use App\Libraries\AccountSubmit;
use Illuminate\Support\Facades\Response;

use Illuminate\Support\Facades\Config;

class  AlipayapiController extends  ApiController {

        public function  __construct() {
                parent::__construct();
                $this->init();
        }

        private function init(){
        }

        public function anyAccount(){
                $accountConfig  = new accountConfig();
                $accountConfigs = $accountConfig->accountConfigs();

                // 初始页号
                $pageNo = 1;
                //必填，必须是正整数

                //账务查询开始时间
                $gmtStartTime = date("Y-m-d 00:00:00",strtotime("-1 day"));
                // $gmtStartTime = '2015-10-14 00:00:00';
                //格式为：yyyy-MM-dd HH:mm:ss
                //账务查询结束时间
                $gmtEndTime   = date("Y-m-d 00:00:00");
                // $gmtEndTime   = '2015-10-15 00:00:00';
                //格式为：yyyy-MM-dd HH:mm:ss

                /************************************************************/

                //构造要请求的参数数组，无需改动
                $parameter      = array(
                        "service"       => "account.page.query",
                        "partner"       => trim($accountConfigs['partner']),
                        "page_no"       => $pageNo,
                        "gmt_start_time"=> $gmtStartTime,
                        "gmt_end_time"  => $gmtEndTime,
                        "_input_charset"=> trim(strtolower($accountConfigs['input_charset']))
                );

                //建立请求
                $alipaySubmit   = new AccountSubmit($accountConfigs);


                $htmlText       = $alipaySubmit->buildRequestHttp( $parameter );

                //请在这里加上商户的业务逻辑程序代码

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

                //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表

                $accountInfomation = json_decode( xml_to_json( $htmlText ) );
                // debug($accountInfomation);

                $tradeNo = array();

                foreach ($accountInfomation->response as $key => $accountLogList) {

                        foreach ($accountLogList as $key => $AccountQuery) {

                               //var_dump($AccountQuery->AccountQueryAccountLogVO);die;

                                if(!isset($AccountQuery ->AccountQueryAccountLogVO)){
                                    continue;
                                }

                                foreach ($AccountQuery->AccountQueryAccountLogVO as  $value) {
                                         $tradeNo[] = $value->trade_no;
                                }
                        }
                }
                // $tradeNo[] ='2015101400001000990065388111';
                // debug($tradeNo);
                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

                $activityM      = new NotifyUrlModel();

                $data = $activityM->getComparisonInfo( $tradeNo , $gmtStartTime , $gmtEndTime );
                $data['start_time'] =  $gmtStartTime;
                $data['end_time']   =  $gmtEndTime;

                logResult(json_encode($data));

                $this->response = $this->response(1 , '账务明细中，本地没有的交易号' , $data);
                return Response::json($this->response);

        }
}