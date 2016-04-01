<?php

use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\Cookie;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Redirect;
use Illuminate\Support\Facades\Response;

use Laravel\Model\Admin\AuthorityModel;
use App\Libraries\Curl;

class AdminController extends BaseController {

	protected $_loginUserId;
	protected $_loginUserNickName;
	protected $_url;
	protected $lang;	//获取接口语言

	protected function __construct(){
		$this->viewData = array();
		$userInfo = Session::get('userInfo');
		if(!empty($userInfo)) {
			$userInfo->menu = $this->getMenu($userInfo->user_id);
			$this->_loginUserId = $userInfo->user_id;
		}
		$lang = '';

		if( Input::has( 'lang' ) ) {
			$lang = Input::get( 'lang' );
			$lang = trim( $lang );
		}
		$this->lang = ( 'en'==$lang ) ? $lang : 'zh';
	}



	/*
	 * 定义通用报错列表
	*
	* @return	array
	*/
	public function getErrorList(){

		return array(

			0=>array("en"=>"Failed", "zh"=>"失败"),
			1=>array("en"=>"Success", "zh"=>"成功"),

			/*
            |--------------------------------------------------------------------------
            | 系统级错误
            |--------------------------------------------------------------------------
            |
            | 系统级错误
            |
            */
			10001=>array("en"=>"System error", "zh"=>"系统错误"),
			10002=>array("en"=>"Service unavailable", "zh"=>"服务暂停"),
			10003=>array("en"=>"Remote service error", "zh"=>"远程服务错误"),
			10004=>array("en"=>"IP limit", "zh"=>"IP限制"),
			10005=>array("en"=>"Param error", "zh"=>"参数错误"),
			10006=>array("en"=>"Illegal request", "zh"=>"非法请求"),
			10007=>array("en"=>"Request api not found", "zh"=>"接口不存在"),
			10008=>array("en"=>"HTTP method error", "zh"=>"请求方式错误"),
			10009=>array("en"=>"Request body length over limit", "zh"=>"请求长度超过限制"),
			10010=>array("en"=>"Invalid user", "zh"=>"不合法的用户"),
			10011=>array("en"=>"User requests out of rate limit", "zh"=>"用户请求频次超过上限"),
			10012=>array("en"=>"Request timeout", "zh"=>"请求超时"),
			10013=>array("en"=>"User doesn't exists", "zh"=>"用户不存在"),
			10014=>array("en"=>"Username has registered", "zh"=>"用户名已注册"),
			10015=>array("en"=>"No phone number","zh"=>"无电话号码"),
			10016=>array("en"=>"User has login","zh"=>"用户已登录"),
			10017=>array("en"=>"exit login fail","zh"=>"退出登录失败"),
			10018=>array("en"=>"User has not login","zh"=>"用户未登录"),
			10019=>array("en"=>"create token Failed","zh"=>"令牌未生成"),
			10020=>array("en"=>"User has not token","zh"=>"令牌无效"),
			/*
            |--------------------------------------------------------------------------
            | 服务级错误
            |--------------------------------------------------------------------------
            |
            | 服务级错误
            | 2[级别]01[模块]01[错误编号]
            |
            */
			//20000 - 20099   Common error    公共错误
			20001=>array("en"=>"Unknown error", "zh"=>"未知错误"),
			20002=>array("en"=>"DB error", "zh"=>"数据库错误"),
			20003=>array("en"=>"Object already exists", "zh"=>"记录已存在"),
			//20100 - 20199   System model error    系统模块错误
			20101=>array("en"=>"Cid parameter is null", "zh"=>"Cid参数为null"),
			20102=>array("en"=>"Failed to initialize user data", "zh"=>"初始化用户数据失败"),
			//20200 - 20299   User model error    用户模块错误
			20201=>array("en"=>"Uid parameter is null", "zh"=>"Uid参数为null"),
			20202=>array("en"=>"Username or password error", "zh"=>"用户名或密码错误"),
			20203=>array("en"=>"Username and pwd auth out of rate limit", "zh"=>"用户名密码认证超过请求限制"),
			20204=>array("en"=>"Accounts have been locked", "zh"=>"账户已被锁定"),
			20205=>array("en"=>"Failed to modify password", "zh"=>"修改密码失败"),
			20206=>array("en"=>"The phone number has been used", "zh"=>"该手机号已经被使用"),
			20207=>array("en"=>"The account has bean bind phone", "zh"=>"该用户已经绑定手机"),
			20208=>array("en"=>"Verification code error", "zh"=>"验证码错误"),
			20209=>array("en"=>"Failed to send verification code", "zh"=>"发送验证码失败"),
			//20300 - 20399   Article model error    文章模块错误
			20301=>array("en"=>"Aid parameter is null", "zh"=>"Aid参数为null"),
			20302=>array("en"=>"Content is null", "zh"=>"内容为空"),
			20303=>array("en"=>"Article not found", "zh"=>"文章不存在"),
			20350=>array("en"=>"Article category error", "zh"=>"文章分类错误"),
			20351=>array("en"=>"Caid parameter is null", "zh"=>"Caid参数为null"),
			//20400 - 20499   Comment model error    评论模块错误
			20401=>array("en"=>"Coid parameter is null", "zh"=>"Coid参数为null"),
			20402=>array("en"=>"Comment does not exist", "zh"=>"不存在的评论"),
			20403=>array("en"=>"Illegal comment", "zh"=>"不合法的评论"),
			//20500 - 20599   Share model error    分享模块错误
			20501=>array("en"=>"You had get the red envelopes", "zh"=>"您已领取过该红包"),
			20502=>array("en"=>"Red envelopes for failure, please try again later", "zh"=>"红包领取失败,请稍后再试"),
			20503=>array("en"=>"Congratulations, red envelopes for success", "zh"=>"恭喜，红包领取成功"),
			20504=>array("en"=>"Phone number format is not correct", "zh"=>"手机号格式不正确"),
			20505=>array("en"=>"Mobile phone number can't be empty", "zh"=>"手机号码不能为空"),

			/*
            |--------------------------------------------------------------------------
            | 购买夺宝错误
            |--------------------------------------------------------------------------
            |
            | 业务级错误
            | 3[级别]01[模块]01[错误编号]
            |
            */
			30000=>array("en"=>"", "zh"=>"购买失败"),
			30001=>array("en"=>"", "zh"=>"本期已下线"),
			30002=>array("en"=>"", "zh"=>"用户不存在"),
			30003=>array("en"=>"", "zh"=>"剩余次数不足"),
			30004=>array("en"=>"", "zh"=>"数据库操作失败"),
		);
	}

	/**
	 * 定义响应数据规范
	 * 语言:zh[中文简体]、en[英文]
	 *
	 * @param 	string	$code 	状态码
	 * @param 	string	$msg 	状态码
	 * @param 	array	$data 	状态码
	 * @return	array
	 */
	public function response( $code, $msg = null, $data = array() ) {
		$code = (int)$code;
		if( null == $msg ) {
			$errList = $this->getErrorList();

			if( !array_key_exists( $code, $errList ) ) {
				return 'key not exist in config';
			}

			$msg = $errList[ $code ][ $this->lang ];

		}

		$ret = array(
			'code' => $code,
			'msg' => "{$msg}",
		);

		if( null != $data ) {
			$ret[ 'data' ] = $data;
		}

		return $ret;
	}

	/*
	 *
	 */
	protected $jsonMenu;
	protected function rbacAccess(){
		$action = 'menu_list?user_id='.$this->_loginUserId;
		/*
		if(isset($_GET['mid'])){
			$action .= '&mid='.$_GET['mid'];
		}
		 */
		if(isset($_SERVER['REDIRECT_URL'])){
			$action .= '&action='.$_SERVER['REDIRECT_URL'];
		}
		$this->viewData['rbacMenu'] = $this->rbacContent($action);
		if($this->viewData['rbacMenu']=='' || strpos($this->viewData['rbacMenu'],'<ul')===false){
			echo  '获取菜单失败<br/>';
			echo $this->viewData['rbacMenu'];
			die;
		}
		$this->viewData['rbacMenu'] = str_replace('<ul class="nav nav-list">', '', $this->viewData['rbacMenu'] );
		$access = $this->rbacContent('access?user_id='.$this->_loginUserId);

		if($access==''){
			echo  '获取权限失败<br/>';
			echo $this->viewData['rbacMenu'];
			die;
		}
		$accessList = json_decode($access);
		if( !is_array($accessList)){
			echo  '获取权限错误<br/>';
			echo $access;
			die;
		}
		if( !in_array($this->_url, $accessList) ){
			die('<script>alert("未授权的页面");history.go(-1);</script>');
		}


/*
		$url = 'http://localhost.rbac/rbac/menu_list?user_id='.$this->_loginUserId;
		if(isset($_GET['mid'])){
			$url .= '&mid='.$_GET['mid'];
		}

		$curl = new Curl();
		$this->viewData['rbacMenu'] = $curl->get($url);
		$this->viewData['rbacMenu'] = str_replace('<ul class="nav nav-list">', '', $this->viewData['rbacMenu'] );
		$access = $curl->get('http://localhost.rbac/rbac/access?user_id='.$this->_loginUserId);
		$access = json_decode($access);
		if( !in_array($this->_url, $access) ){
			die('<script>alert("未授权的页面");history.go(-1);</script>');
		}
 */
	}

	protected function rbacContent($action, $postData=array()){
		if(isset($_SERVER['QUERY_STRING']) && $_SERVER['QUERY_STRING']!=''){
			$action .= strpos($action,'?') ? '&' : '?';
			$action .= $_SERVER['QUERY_STRING'];
		}

		/*
		$url = RBAC_URL .'/rbac/'.$action;
		$curl = new Curl();
		if(!empty($postData)){
			$content = $curl->post($url, http_build_query($postData));
		}else{
			$content = $curl->get($url);
		}
*/

		//exec('which php', $arr);
		//$system_php = $arr[0];
		//$command = $system_php.' '.RBAC_PATH.'/apple/public/index.php rbac/'.$action;
		$command = Config::get('app.system_php').' '.RBAC_PATH.'/apple/public/index.php rbac/'.$action;
		if(!empty($postData)){
			$command .= ' post ' . http_build_query($postData);
		}

		// 转义特殊字符
		$command = str_replace('&', '\&', $command);

		$arr = array();
		exec($command, $arr);
		$content = implode('', $arr);
		return $content;
	}


	/**
	 * 调用海红接口，获取菜单信息
	 * 有问题找海红啊啊啊啊
	 * @param $user_id
	 * @return menu
	 */
	public function getMenu($user_id) {
		if(!isset($user_id)) {
			return null;
		}
		$action = 'menu_list?user_id='.$user_id;
		/*
		if(isset($_GET['mid'])){
			$action .= '&mid='.$_GET['mid'];
		}
		 */
		$url = \Illuminate\Support\Facades\Request::path();
		$action .= '&action=/' . $url;
		$menu = $this->rbacContent($action);

		if($menu=='' || strpos($menu,'<ul')===false){
			Log::error("获取菜单失败=>{$menu}");
			return null;
		}
		$menu = str_replace('<ul class="nav nav-list">', '',$menu );
		return $menu;
	}
}
