<?php 
namespace App\Libraries;
/*
 * 发送短信工具
 */
use App\Libraries\Curl;
use App\Libraries\Taobao\Sms AS TaobaoSms;

class Sms {

	//给单个用户发送一条短信
	//param@phone str 传前自行判断是否符合手机号码要求
	//param@content str 短信内容
	public static function sendShortMessage($phone,$content=""){
		
		$error	= array("status"=>0,"msg"=>"");
		$url	= "http://smsapi.c123.cn/OpenPlatform/OpenApi?action=sendOnce";

		$ac		= '1001@501108780001';
		$auth	= 'D4EF373D4603D9DEFD96346506CD7828';
		$cgid	= '4843';
		$csid	= '5710';
 
		/*
		$ac		= Config::get( 'sms_ac' );
		$auth	= Config::get( 'sms_auth' );
		$cgid	= Config::get( 'sms_cgid' );
		$csid	= Config::get( 'sms_csid' );
		 */
		$c		= urlencode($content);
		$m		= $phone;

		$url .= "&ac=".$ac;
		$url .= "&authkey=".$auth;
		$url .= "&cgid=".$cgid;
		$url .= "&csid=".$csid;
		$url .= "&c=".$c;
		$url .= "&m=".$m;
		
		$curl = new Curl();
		$post_res = $curl->post($url,"");
		$check_res = simplexml_load_string($post_res);

		if($check_res && isset($check_res->attributes()->result)){
			if($check_res->attributes()->result == 1){
				$error["status"] = 1;
				$error["msg"] = "成功";
			}else{
				$error["msg"] = "发送失败,请重试";
			}
		}else{
			$error["msg"] = "网络繁忙,请重试";
		}
		return $error;
	}

	/*
	 * 淘宝大鱼短信平台
	 * 用户注册验证码
	 */
	public static  function sendRegisterCode($tel, $code){
		$sms = new TaobaoSms();
		$resp = $sms->send($tel, '夺宝会', '注册验证', $code, 'SMS_2040102');
		$result = (array)$resp->result;
		if($result['success']=='true'){
			return true;
		}
		return false;
	}

	//SMS_2560059
	public static  function sendDownloadSMS($tel){
		$sms = new TaobaoSms();
		$resp = $sms->send2($tel, '夺宝会', 'SMS_2560059');
		$result = (array)$resp->result;
		if($result['success']=='true'){
			return true;
		}
		return false;
	}



}
