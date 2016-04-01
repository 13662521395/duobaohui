<?php

	/* 支付宝批量退款接口
	 * @author    majianchao@shinc.com
	 * @version   v0.1
	 *@copyright shinc
	 */
namespace Laravel\Controller\AlipayRefund; //定义命名空间

use  ApiController;  //引入接口公共父类，用于继承
use  Illuminate\Support\Facades\View; //引入视图类
use  Illuminate\Support\Facades\Input;//引入参数类

//use Larverl\Model\AlipayApiModel;//引入model

use App\Libraries\alipayConfigs; //配置文件
use App\Libraries\AlipaySubmit;

class  AlipayApiController extends  ApiController{

	public  function __construct() {

		parent::__construct();
	}


	public function anyApi(){
		
		$alipayConfigs  = new alipayConfigs();
		$alipay_config  = $alipayConfigs->alipayRefundConfigs();

		//服务器异步通知页面路径 需要改成互联网能够访问的网址。
		$notify_url = "http://192.168.1.226:8015/alipayRefund/alipayRefunds/notify-alipay";
        //需http://格式的完整路径，不允许加?id=123这类自定义参数
        //卖家支付宝帐户
        $seller_email = Input::get('WIDseller_email');
        //必填

        //退款当天日期
        $refund_date = date('Y-m-d H:i:s');
        //必填，格式：年[4位]-月[2位]-日[2位] 小时[2位 24小时制]:分[2位]:秒[2位]，如：2007-10-01 13:13:13

        //批次号
        $batch_no = $d = date('YmdHis').rand(100000000000000000000,995929929979995999989);
        //必填，格式：当天日期[8位]+序列号[3至24位]，如：201008010000001

        //退款笔数
        $batch_num = Input::get('WIDbatch_num');
        //必填，参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）

        //退款详细数据
        $detail_data = Input::get('WIDdetail_data');
        //必填，具体格式请参见接口技术文档
       /************************************************************/

			//构造要请求的参数数组，无需改动
			$parameter = array(
					"service" => "refund_fastpay_by_platform_pwd",
					"partner" => trim($alipay_config['partner']),
					"notify_url"	=> $notify_url,
					"seller_email"	=> $seller_email,
					"refund_date"	=> $refund_date,
					"batch_no"	=> $batch_no,
					"batch_num"	=> $batch_num,
					"detail_data"	=> $detail_data,
					"_input_charset"	=> trim(strtolower($alipay_config['input_charset']))
			);


			//建立请求
			$alipaySubmit = new AlipaySubmit($alipay_config);
			$html_text = $alipaySubmit->buildRequestForm($parameter,"get", "确认");
			echo $html_text;
	}






}

?>