<?php

//use Illuminate\Support\Facades\Route;
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

	return Redirect::to(\Illuminate\Support\Facades\URL::route('login'));
});
Route::get('/login',['as' => 'login','uses' => 'Laravel\Controller\Admin\UserController@login']);
Route::post('/login',['uses' => 'Laravel\Controller\Admin\UserController@postNickNameLogin']);
Route::get('/no-auth',['uses' => 'Laravel\Controller\Admin\UserController@getNoAuth']);
Route::post('/send-app-notify',['uses' => 'Laravel\Controller\Admin\NotifyController@anySendAppNotify']);
Route::get('/add-shop-share-result',['uses' => 'Laravel\Controller\Admin\ShopController@getAddShopShareResult']);
Route::get('/get-daily-report',['uses' => 'Laravel\Controller\Admin\ShopController@getDailyReport']);
Route::any('/adjust-db-sum',['uses' => 'Laravel\Controller\Admin\ShopController@adjustDbSum']);
Route::any('/any-send-sms-for-ios',['uses' => 'Laravel\Controller\Admin\SmsController@anySendSmsForIos']);

//admin
Route::group( array( 'prefix' => 'admin' , 'before' => 'auth') , function(){
     Route::controller('index' , 'Laravel\Controller\Admin\IndexController');
     Route::controller('banner' , 'Laravel\Controller\Admin\BannerController');
     Route::controller('user' , 'Laravel\Controller\Admin\UserController');
     Route::controller('userFriends' , 'Laravel\Controller\Admin\UserFriendsController');
     Route::controller('shaidan' , 'Laravel\Controller\Admin\ShaidanController');
     Route::controller('goods' , 'Laravel\Controller\Admin\GoodsController');
	 Route::controller('activity' , 'Laravel\Controller\Admin\ActivityController');
     Route::controller('authority' , 'Laravel\Controller\Admin\AuthorityController');
     Route::controller('client' , 'Laravel\Controller\Admin\ClientController');
     Route::controller('shop' , 'Laravel\Controller\Admin\ShopController');
     Route::controller('opinion' , 'Laravel\Controller\Admin\FeedbackController');
     Route::controller('notify' , 'Laravel\Controller\Admin\NotifyController');
     Route::controller('systemNotice' , 'Laravel\Controller\Admin\SystemNoticeController');

     //红包
     Route::controller('operate' , 'Laravel\Controller\Admin\OperateController');

	 // 账单
     Route::controller('bill' , 'Laravel\Controller\Admin\BillController');
     Route::controller('order' , 'Laravel\Controller\Admin\OrderController');

	 //rbac
	 Route::controller('rbac' , 'Laravel\Controller\Admin\RbacController');

     //setting
     Route::controller('setting' , 'Laravel\Controller\Admin\AutoPaySettingController');
});



