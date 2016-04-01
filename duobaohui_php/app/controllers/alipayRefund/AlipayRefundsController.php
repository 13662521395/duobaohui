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

use Larverl\Model\AlipayRefund\AlipayRefundsModel;//引入model

use App\Libraries\alipayConfigs; //配置文件
use App\Libraries\AlipayNotifys;

class  AlipayRefundsController extends  ApiController{

	public  function __construct() {

		parent::__construct();
	}


	public function anyNotifyAlipay(){
		$alipay_config  = new alipayConfigs();
		$alipay_configs = $alipay_config->alipayRefundConfigs();
		$alipayNotify   = new AlipayNotifys($alipay_configs);
		$verify_result  = $alipayNotify->verifyNotify();
		if($verify_result) {//验证成功
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//请在这里加上商户的业务逻辑程序代

		
		//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
		
	    //获取支付宝的通知返回参数，可参考技术文档中服务器异步通知参数列表
		
		//批次号

		$batch_no = $_POST['batch_no'];

		//批量退款数据中转账成功的笔数

		$success_num = $_POST['success_num'];

		//批量退款数据中的详细信息
		$result_details = $_POST['result_details'];

		$data  = array(
				'batch_no'       => Input::get('batch_no'),
 				'success_num'    => Input::get('success_num'),
 				'result_details' => Input::get('result_details')
			);
		$alipay = new AlipayRefundsModel();
				$alipay->addAlipay($data);

		//判断是否在商户网站中已经做过了这次通知返回的处理
			//如果没有做过处理，那么执行商户的业务程序
			//如果有做过处理，那么不执行商户的业务程序
	        
		echo "success";		//请不要修改或删除

		//调试用，写文本函数记录程序运行情况是否正常
		//logResult("这里写入想要调试的代码变量值，或其他运行的结果记录");

		//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else {
		    //验证失败
		    echo "fail";

		    //调试用，写文本函数记录程序运行情况是否正常
		    //logResult("这里写入想要调试的代码变量值，或其他运行的结果记录");
			}
				
		      
	}






}

?>