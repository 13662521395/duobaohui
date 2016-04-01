<?php
require_once(ROOT_PATH. '/config/local.inc.php');

if(isset($_SERVER['SERVER_NAME'])){
	$DOMAIN_NAME = 'http://' . $_SERVER['SERVER_NAME'] . '/';
	define('SERVER_NAME', $_SERVER['SERVER_NAME']);
}else{
	$DOMAIN_NAME = '';
	define('SERVER_NAME', '');
}
define('BASE_URL', $DOMAIN_NAME);
define('MEI_BASE_URL', str_replace('admin.', '', $DOMAIN_NAME));
define('OA_VIEW_SWITCH', 'ON');

define("TEMPLATE_PATH",  ROOT_PATH . '/views/');
define("TEMPLATE_PATH_TPL",  ROOT_PATH . '/tpls/');
define("TEMPLATE_PATH_CACHE",  ROOT_PATH . '/cache/');
define("TEMPLATE_CSS_PATH",  '/static/css/');
define('DOMAIN_NAME' , $DOMAIN_NAME);
define('PAGE_TITLE' , '');
define('DOMAIN_ROOT_PATH', str_replace('/admin', '/gate',ROOT_PATH));

define('DEFAULT_COOKIE_PATH', '/');
define('DEFAULT_COOKIE_DOMAIN', '.rbac');
define('DEFAULT_COOKIE_NAME', '_auth');
define('DEFAULT_COOKIE_EXPIRE', 31536000); //一年
//七牛
define("QN_AK","h591Hrv-oh3BornRVEQqlDE7IJQYFgM-dkA44tKM");
define("QN_SK","XFwQNCCycfAf6fv_Ox-teKB8Tf2Bk21Xr5cqYXEm");

#RBAC
/*
list($module, $action) = explode('/', trim($_SERVER['REQUEST_URI'], '/'));
define('APP_NAME', 'Rbac');
define('MODULE_NAME', $module);
define('ACTION_NAME', $action);
 */
define('USER_AUTH_ON', true);					// 开启登录认证
define('NOT_AUTH_ACTION', '/user/login,/user/logout,/user/register');		           // 无需认证操作, 逗号分隔
define('USER_ACCESS_ON', true);					// 开启权限管理
//define('USER_ACCESS_ON', false);					// 开启权限管理
define('NOT_ACCESS_ACTION', '/user/login,/index/index,/user/logout');					// 无需权限管理, 逗号分隔
//define('NOT_ACCESS_USER', '1');					// 无需权限用户, 逗号分隔

define('USER_AUTH_KEY', 'authId');	           // 用户认证SESSION标记
define('ADMIN_AUTH_KEY', 'administrator');

require_once(ROOT_PATH . '/config/'.CONFIG_PATH.'/config.inc.php');
