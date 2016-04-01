<?php

require_once 'db.php';
require_once 'qiniuService.php';

function run(){
	getPage();
}
function getPage(){
	$url = "http://www.china.com.cn/ch-quhua/daima.htm";
	$output = curlPost($url);
	preg_match_all('/table border="0" width="500" cellspacing="1" cellpadding="6" align="center" bgcolor="#999999"\/>(.*)\<\/table\>/Uis' , $output , $links);
	var_dump($links);
	preg_match_all('br\>(.*)\<\/br\>' , $links[0] , $data);
}

/*
 * curl 方式抓取
 */
function curlPost($url, $post='', $autoFollow=0){
	$ch = curl_init();
	$user_agent = 'Mozilla/5.0 (Windows NT 6.1; rv:17.0) Gecko/20100101 Firefox/17.0 FirePHP/0.7.1';
	curl_setopt($ch, CURLOPT_USERAGENT, $user_agent);
	// 2. 设置选项，包括URL
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($ch, CURLOPT_HEADER, 0);
	curl_setopt($ch, CURLOPT_HTTPHEADER, array('X-FORWARDED-FOR:192.168.2.11', 'CLIENT-IP:192.168.2.11'));  //构造IP
	curl_setopt($ch, CURLOPT_REFERER, "http://www.gosoa.com.cn/");   //构造来路
	if($autoFollow){
		curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);  //启动跳转链接
		curl_setopt($ch, CURLOPT_AUTOREFERER, true);  //多级自动跳转
		$res = curl_getinfo($ch , CURLINFO_EFFECTIVE_URL);
		return $res;
	}
	//
	if($post!=''){
		curl_setopt($ch, CURLOPT_POST, 1);//post提交方式
		curl_setopt($ch, CURLOPT_POSTFIELDS, $post);
	}
	// 3. 执行并获取HTML文档内容
	$output = curl_exec($ch);
	curl_close($ch);
	return $output;
}


run();
?>
