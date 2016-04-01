<?php

use Illuminate\Support\Facades\Route;
use Illuminate\Support\Facades\Redirect;

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the Closure to execute when that URI is requested.
|
*/

Route::get('/', function() {

	return Redirect::to('/home');
});
//Route::get('/user', 'ErrorController@error');
Route::get('/home', 'HomeController@showWelcome');


// 活动
Route::group( array('prefix' => 'activity'),function() {
		Route::any('/record/records-num', array('before' => 'auth', 'uses' => 'Laravel\Controller\Activity\RecordController@anyRecordsNum'));
		Route::any('/record/records', array('before' => 'auth', 'uses' => 'Laravel\Controller\Activity\RecordController@anyRecords'));
		Route::any('/record/records-winning-records', array('before' => 'auth', 'uses' => 'Laravel\Controller\Activity\RecordController@anyWinningRecords'));

		//Route::get('/index/detail', array('before' => 'token_create', 'uses' => 'Laravel\Controller\Activity\IndexController@getDetail'));
		Route::controller('/index', 'Laravel\Controller\Activity\IndexController');			//用户登录
		Route::controller('/lottery', 'Laravel\Controller\Activity\LotteryController');
		Route::controller('/record', 'Laravel\Controller\Activity\RecordController');		//夺宝纪录
		Route::controller('/share', 'Laravel\Controller\Activity\ShareController');
		Route::controller('/rule', 'Laravel\Controller\Activity\RuleController');
});

// 系统
Route::group( array('prefix' => 'system'),function() {
	Route::any('/opinion/add-opinion', array('before' => 'auth', 'uses' => 'Laravel\Controller\System\OpinionController@anyAddOpinion'));

    Route::controller('/opinion', 'Laravel\Controller\System\OpinionController');			//系统
    Route::controller('download', 'Laravel\Controller\System\DownloadController');
    Route::controller('/notice', 'Laravel\Controller\System\SystemNoticeController');
});

// 订单
Route::group( array('prefix' => 'order'),function() {
    //Route::get('/status/orderlistbystatus', array('before' => 'token_create', 'uses' => 'Laravel\Controller\Order\OrderController@getOrderlistbystatus'));
    Route::controller('/status', 'Laravel\Controller\Order\OrderController');			//订单
});

//回调
Route::group(array('prefix' => 'callback'), function () {

    Route::controller('/alipay', 'Laravel\Controller\Callback\AlipayCallbackController');    //阿里回调控制器
	Route::controller('/aliwebpay', 'Laravel\Controller\Callback\AlipayWebCallbackController');    //阿里web支付回调控制器
	Route::controller('/weixin', 'Laravel\Controller\Callback\WeixinCallbackController');    //微信回调控制器
});

//用户路由
Route::group( array('prefix' => 'user'),function() {

		Route::any('/token', array('before' => 'token_create', 'uses' => 'Laravel\Controller\User\TokenController@anyToken'));
		Route::get('/login/login-tel', array('before' => 'token_validate', 'uses' => 'Laravel\Controller\User\LoginController@anyLoginTel'));
		Route::get('/address/address-list', array('before' => 'auth', 'uses' => 'Laravel\Controller\User\AddressController@anyAddressList'));
		Route::get('/shaidan/get-shaidan', array('uses' => 'Laravel\Controller\User\ShaidanController@anyGetShaidan'));

		Route::controller('/register','Laravel\Controller\User\RegisterController');	//用户注册
		Route::controller('/login', 'Laravel\Controller\User\LoginController');			//用户登录
		Route::controller('/reset', 'Laravel\Controller\User\ResetController');			//用户找回密码
		Route::controller('/email', 'Laravel\Controller\User\EmailController');			//邮箱
		Route::controller('/thirdpart', 'Laravel\Controller\User\ThirdpartController');	//第三方登录
		Route::controller('newlogin', 'Laravel\Controller\User\NewloginController');
		Route::controller('newregister', 'Laravel\Controller\User\NewregisterController');
		Route::controller('newreset', 'Laravel\Controller\User\NewresetController');
		Route::controller('/usersms', 'Laravel\Controller\User\UserSmsController');     //用户发送消息  消息系统
		Route::controller('/address','Laravel\Controller\User\AddressController');    //收货地址操作
		Route::controller('/shaidan','Laravel\Controller\User\ShaidanController');    //晒单

});

//消息推送
Route::group( array('prefix' => 'notify'),function() {
	Route::controller('/notify', 'Laravel\Controller\notify\NotifyController');	//打开软件初始化app
});

//支付宝 微信
Route::group( array('prefix' => 'alipay'), function() {

		Route::get('/notifyUrl/buy', array('before' => 'token_validate', 'uses' => 'Laravel\Controller\Alipay\NotifyUrlController@anyBuy'));
		Route::get('/notifyUrl/notify', array('before' => 'token_validate', 'uses' => 'Laravel\Controller\Alipay\NotifyUrlController@anyNotify'));
		Route::get('/notifyUrl/recharge', array('before' => 'token_validate', 'uses' => 'Laravel\Controller\Alipay\NotifyUrlController@anyRecharge'));
		Route::controller('/notifyUrl', 'Laravel\Controller\Alipay\NotifyUrlController');
		Route::controller('/alipayapi', 'Laravel\Controller\Alipay\AlipayapiController');
		Route::controller('/weixinapi', 'Laravel\Controller\Alipay\WeixinapiController');
		Route::controller('/webapi', 'Laravel\Controller\Alipay\AlipayWebapiController');
});

//购买
Route::group( array('prefix' => 'period'), function() {

	Route::any('/duobao/duobao-confirm', array('before' => 'auth', 'uses' => 'Laravel\Controller\Activity\PeriodController@anyDuobaoConfirm'));
	Route::controller('/duobao', 'Laravel\Controller\Activity\PeriodController');
});

//用户充值记录
Route::group( array('prefix' => 'recharge' ), function(){
		Route::controller('/recharge', 'Laravel\Controller\Recharge\RechargeController');
});

////微信支付
//Route::group( array('prefix' => 'weixin' ), function(){
//		Route::get('/pay', array('uses' => 'Laravel\Controller\Weixin\WeixinController@anyWeixin'));
//});

//支付宝退款
Route::group( array('prefix' => 'alipayRefund'), function() {

		Route::controller('/alipayRefunds', 'Laravel\Controller\AlipayRefund\AlipayRefundsController');
		Route::controller('/alipayApi',     'Laravel\Controller\AlipayRefund\AlipayApiController');

});
//发送短信
Route::group( array('prefix' => 'sms'), function() {

		Route::controller('/SmS', 'Laravel\Controller\Sms\SmSController');

});

//个人中心设置
Route::group( array('prefix' => 'individual'),function() {

	Route::controller('/appConfig', 'Laravel\Controller\Individual\AppConfigController');	//打开软件初始化app
	Route::controller('/user', 'Laravel\Controller\Individual\UserController');				//修改用户个人信息
	Route::controller('/userConfig', 'Laravel\Controller\Individual\UserConfigController');	//修改用户个人配置
});

//七牛存储
Route::group(
	array('prefix' => 'qiniu'),
	function() {
		Route::controller('/qiNiu', 'Laravel\Controller\QiNiu\QiNiuController');
	}
);

//晒单举报
Route::group( array('prefix' => 'report'),function() {
	Route::controller('/report', 'Laravel\Controller\Report\ReportController');
});

//TA的主页
Route::controller('/tauser','Laravel\Controller\User\TAUserController');

//红包模块
Route::group( array('prefix' => 'redpacket'), function() {

	Route::controller('/redpacket', 'Laravel\Controller\Redpacket\RedpacketController');

});

//客户端签到
Route::group( array('prefix' => '/client'),function() {
	Route::controller('/sign', 'Laravel\Controller\Client\SignController');
	Route::controller('/config', 'Laravel\Controller\Client\ConfigController');
});

