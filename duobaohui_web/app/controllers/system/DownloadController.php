<?php
/**
 * 下载页面
 * @author		wangkenan@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\System;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session
use Illuminate\Support\Facades\Request;
use Illuminate\Support\Facades\Response;	//引入response

use Laravel\Model\ShopModel;
use Laravel\Model\SystemModel;			//引入model
use Laravel\Model\ClientModel;
use Illuminate\Support\Facades\Log;//引入日志类

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */
class DownloadController extends ApiController {
	public function __construct(){
		parent::__construct();
		$this->nowDateTime = date('Y-m-d H:i:s');
	}

	/**
	 * 输手机号，领夺宝币，下载APP(商家)
	 * @param shopId 商家id
	 * @return \Illuminate\Http\Response
	 */
	public function getApp() {
		$sessionId = Session::getId();
		Log::info("shop_qrCode_sessionId ".$sessionId);

		$data = array();
		$data[ 'shopId' ] = Input::has( 'shopId' )?Input::get( 'shopId' ):0;
		$data[ 'shopChannelId' ] = Input::has( 'shopChannelId' )?Input::get( 'shopChannelId' ):0;
		$data[ 'scene' ] = Input::has( 'scene' )?Input::get( 'scene' ):1;
		$data[ 'shareType' ] = '1';//shareType 推广类型 1:商家推广 2:用户推广
		$data[ 'activityId' ] = '1';
		$data[ 'source' ] = 1;
		$data[ 'sessionId' ] = $sessionId;
		Log::info($data);
		return Response::view('system.download', $data);
	}

	/**
	 * 输手机号，领夺宝币，下载APP(用户客户端分享)
	 * @param userId 用户id
	 * @return \Illuminate\Http\Response
	 */
	public function getShare() {
		$sessionId = Session::getId();
		Log::info("user_friends_sessionId ".$sessionId);

		$data = array();
		$data[ 'userId' ] = Input::has( 'userId' )?Input::get( 'userId' ):0;
		$data[ 'channel' ] = Input::has( 'channel' )?Input::get( 'channel' ):2;

		if($data[ 'userId' ] == -1){//官方(微信公众号|官方微博)推广
			$data[ 'source' ] = 3;
			$data[ 'shopId' ] = -1;
		}else{//来自客户端(邀请好友) 用于记录扫码数
			$data[ 'source' ] = 2;
			$data[ 'shopId' ] = 0;
		}

		$data[ 'scene' ] = Input::has( 'scene' )?Input::get( 'scene' ):1;
		$data[ 'shareType' ] = '2';//shareType 推广类型 1:商家推广 2:用户推广
		$data[ 'activityId' ] = '1';
		$data[ 'sessionId' ] = $sessionId;
		return Response::view('system.download', $data);
	}

	/**
	 * 前往下载app页面
	 * @return \Illuminate\Http\Response
	 */
	public function getAppOne(){
		$shopChannelId = Input::has( 'shopChannelId' )?Input::get('shopChannelId'):0;
		$os_type = Input::has( 'os_type' )?Input::get('os_type'):1;
		$tel = Input::get( 'tel' );
		$shopId = Input::get( 'shopId' );
		$scene = Input::get( 'scene' );
		$channel = Input::get( 'channel' );
		$source = Input::get( 'source' );

		$clientM = new ClientModel();
		$list = $clientM->getLatelyAppUrl($os_type);

		$data = array();
		if($list) {
			$url = $list[0]->url;
			Log::info($url);
			$data['url'] = $url;
			$data['tel'] = $tel;
			$data['shopId'] = $shopId;
			$data['shopChannelId'] = $shopChannelId;
			$data['scene'] = $scene;
			$data['channel'] = $channel;
			$data['source'] = $source;
			$data['os_type'] = $os_type;
			Log::info($data);
		}
		return Response::view('system.download_one' , $data);
	}

	/**
	 * 获取下载APP链接
	 */
	public function getDownloadAppUrl(){
		$os_type = Input::has( 'os_type' )?Input::get('os_type'):1;
		$clientM = new ClientModel();
		$list = $clientM->getLatelyAppUrl($os_type);
		if($list) {
			$url = $list[0]->url;
		}
		$this->response = $this->response(1, '成功' ,$url);
		return Response::json($this->response);
	}

	/**
	 * 记录下载次数
	 */
	public function anyRecordDownloadItem(){
		$data = array();
		$shopM = new ShopModel();

		$data[ 'sh_shop_id' ] = Input::has( 'shopId' )?Input::get( 'shopId' ):0;
		$data[ 'sh_shop_sub_id' ] = Input::has( 'shopChannelId' )?Input::get( 'shopChannelId' ):0;
		$data[ 'phone_num' ] = Input::get( 'phone_num' );
		$data[ 'scene' ] = Input::has( 'scene' )?Input::get( 'scene' ):1;
		$data[ 'os_type' ] = Input::has( 'os_type' )?Input::get( 'os_type' ):1;
		$data[ 'user_agent' ] = Input::get( 'user_agent' );
		$data[ 'channel' ] = Input::has( 'channel' )?Input::get( 'channel' ):7;
		$data[ 'source' ] = Input::get( 'source' );
		$data[ 'cookie' ] = Session::getId();
		$data[ 'create_time' ] = date('Y-m-d H:i:s');
		$data[ 'user_ip' ] = Request::ip();

		$res = $shopM->addShopDownload($data);
		if($res){
			return Response::json($this->response(1));
		}
		return Response::json($this->response(0));
	}

	/**
	 * 记录扫码数
	 * @return \Illuminate\Http\JsonResponse
	 */
	public function postRecordNum() {
		$data = array();
		$shopM = new ShopModel();

		$data[ 'sh_shop_id' ] = Input::has( 'shopId' )?Input::get( 'shopId' ):0;
		$data[ 'sh_shop_sub_id' ] = Input::has( 'shopChannelId' )?Input::get( 'shopChannelId' ):0;
		$data[ 'scene' ] = Input::has( 'scene' )?Input::get( 'scene' ):1;
		$data[ 'os_type' ] = Input::has( 'os_type' )?Input::get( 'os_type' ):1;
		$data[ 'channel' ] = Input::has( 'channel' )?Input::get( 'channel' ):7;
		$data[ 'source' ] = Input::get( 'source' );
		$data[ 'cookie' ] = Input::get( 'sessionId' );
		$data[ 'user_agent' ] = Input::get( 'user_agent' );
		$data[ 'create_time' ] = date('Y-m-d H:i:s');
		$data[ 'user_ip' ] = Request::ip();

		$res = $shopM->addShopShareScan($data);
		if($res){
			return Response::json($this->response(1));
		}
		return Response::json($this->response(0));
	}

	/**
	 * 扫码直接下载
	 */
	public function getDownloadDirect(){
		return Response::view('system.download_direct');
	}

}
