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
use Illuminate\Support\Facades\Response;	//引入response

use Laravel\Model\SystemModel;			//引入model
use Laravel\Model\ClientModel;
use Illuminate\Support\Facades\Log;//引入日志类

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class DownloadController extends ApiController {
	public function __construct(){
		parent::__construct();
	}

	/**
	 * 输手机号，领夺宝币，下载APP(商家)
	 * @param shopId 商家id
	 * @return \Illuminate\Http\Response
	 */
	public function getApp() {
		$data = array();
		$data[ 'shopId' ] = Input::has( 'shopId' )?Input::get( 'shopId' ):0;
		$data[ 'shareType' ] = '1';//shareType 推广类型 1:商家推广 2:用户推广
		$data[ 'activityId' ] = '1';
		return Response::view('system.download', $data);
	}

	/**
	 * 输手机号，领夺宝币，下载APP(用户客户端分享)
	 * @param userId 用户id
	 * @return \Illuminate\Http\Response
	 */
	public function getShare() {
		$data = array();
		$data[ 'userId' ] = Input::has( 'userId' )?Input::get( 'userId' ):0;
		$data[ 'shareType' ] = '2';//shareType 推广类型 1:商家推广 2:用户推广
		$data[ 'activityId' ] = '1';
		return Response::view('system.download', $data);
	}

	/**
	 * 前往下载app页面
	 * @return \Illuminate\Http\Response
	 */
	public function getAppOne(){
		$os_type = Input::has( 'os_type' )?Input::get('os_type'):1;
		$clientM = new ClientModel();
		$list = $clientM->getLatelyAppUrl($os_type);

		$data = array();
		if($list) {
			$url = $list[0]->url;
			Log::info($url);
			$data['url'] = $url;
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
	 * 扫码直接下载
	 */
	public function getDownloadDirect(){
		return Response::view('system.download_direct');
	}

	
}
