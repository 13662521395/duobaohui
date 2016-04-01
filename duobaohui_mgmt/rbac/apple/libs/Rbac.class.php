<?php
namespace Gate\Libs;
// 配置文件增加设置
// USER_AUTH_ON 是否需要认证
// USER_AUTH_TYPE 认证类型
// USER_AUTH_KEY 认证识别号
// REQUIRE_AUTH_MODULE  需要认证模块
// NOT_AUTH_MODULE 无需认证模块
// USER_AUTH_GATEWAY 认证网关
use Gate\Package\Helper\DBAdminHelper AS Admin;
use Gate\Package\Helper\DBAccessHelper AS Access;
use Gate\Package\Helper\DBNodeHelper AS Node;
use Gate\Package\Helper\DBRoleHelper AS Role;
use Gate\Package\Helper\DBRoleUserHelper AS RoleUser;

class Rbac{
	function __construct()
	{
	}

    public function authenticate($map,$model='')
    {
		$result = Admin::getConn()->where('account=:account', $map)->fetchArr();
		return $result;
    }
    
    public function saveAccessList($authId=null)
    {
    	if(null===$authId)   $authId = $_SESSION['authId'];
    	if(USER_AUTH_TYPE !=2 && !$_SESSION[ADMIN_AUTH_KEY])
    	{
    		$_SESSION['_ACCESS_LIST'] = self::getAccessList($authId);
    	}
    	return ;
    }

	public function getAccessList($authId)
	{
		$table = array(
				'role'=>RBAC_ROLE_TABLE,
				'user'=>RBAC_USER_TABLE,
				'access'=>RBAC_ACCESS_TABLE,
				'node'=>RBAC_NODE_TABLE
		);
		
		$sql = "select node.id,node.name from ".
				$table['role']." as role,".
				$table['user']." as user,".
				$table['access']." as access ,".
				$table['node']." as node ".
				"where user.user_id='{$authId}' and user.role_id=role.id and ( access.role_id=role.id  or (access.role_id=role.pid and role.pid!=0 ) ) and role.status=1 and access.node_id=node.id and node.level=1 and node.status=1";
		$apps = Admin::getConn()->fetchArrAll($sql);

		$access =  array();
		foreach($apps as $key=>$app)
		{
			$appId = $app['id'];
			$appName = $app['name'];
			$access[strtoupper($appName)] = array();
			$sql = "select node.id,node.name from ".
					$table['role']." as role,".
					$table['user']." as user,".
					$table['access']." as access ,".
					$table['node']." as node ".
					"where user.user_id='{$authId}' and user.role_id=role.id and ( access.role_id=role.id  or (access.role_id=role.pid and role.pid!=0 ) ) and role.status=1 and access.node_id=node.id and node.level=2 and node.pid={$appId} and node.status=1";
			
			$modules = Admin::getConn()->fetchArrAll($sql);

			$publicAction  = array();
			foreach($modules as $key=>$module)
			{
				$moduleId = $module['id'];
				$moduleName = $module['name'];
				if('PUBLIC' == strtoupper($moduleName))
				{
					$sql = "select node.id,node.name from ".
							$table['role']." as role,".
							$table['user']." as user,".
							$table['access']." as access ,".
							$table['node']." as node ".
							"where user.user_id='{$authId}' and user.role_id=role.id and ( access.role_id=role.id  or (access.role_id=role.pid and role.pid!=0 ) ) and role.status=1 and access.node_id=node.id and node.level=3 and node.pid={$moduleId} and node.status=1";
					$rs = Admin::getConn()->fetchArrAll($sql);
					foreach ($rs as $a)
					{
						$publicAction[$a['name']] = $a['id'];
					}
					unset($modules[$key]);
					break;
				}
			}
			foreach($modules as $key=>$module)
			{
				$moduleId  = $module['id'];
				$moduleName = $module['name'];
				$sql = "select node.id,node.name from ".
						$table['role']." as role,".
						$table['user']." as user,".
						$table['access']." as access ,".
						$table['node']." as node ".
						"where user.user_id='{$authId}' and user.role_id=role.id and ( access.role_id=role.id  or (access.role_id=role.pid and role.pid!=0 ) ) and role.status=1 and access.node_id=node.id and node.level=3 and node.pid={$moduleId} and node.status=1";
				$rs = Admin::getConn()->fetchArrAll($sql);

				$action = array();
				foreach ($rs as $a)
				{
					$action[$a['name']] = $a['id'];
				}
				$action += $publicAction;
				$access[strtoupper($appName)][strtoupper($moduleName)] = array_change_key_case($action,CASE_UPPER);
			}
		}
		return $access;
	}

    static function checkAccess()
    {
		if(USER_AUTH_ON)
		{
			$_module	=	array();
			$_action	=	array();
		    
			if("" != REQUIRE_AUTH_MODULE)
			{
                $_module['yes'] = explode(',',strtoupper(REQUIRE_AUTH_MODULE));
			}
			else
			{
                $_module['no'] = explode(',',strtoupper(NOT_AUTH_MODULE));
            }
            
			if((!empty($_module['no']) && !in_array(strtoupper(MODULE_NAME),$_module['no'])) || (!empty($_module['yes']) && in_array(strtoupper(MODULE_NAME),$_module['yes'])))
			{
				if("" != REQUIRE_AUTH_ACTION)
				{
					$_action['yes'] = explode(',',strtoupper(REQUIRE_AUTH_ACTION));
				}
				else
				{
					$_action['no'] = explode(',',strtoupper(NOT_AUTH_ACTION));
				}
				if((!empty($_action['no']) && !in_array(strtoupper(ACTION_NAME),$_action['no'])) || (!empty($_action['yes']) && in_array(strtoupper(ACTION_NAME),$_action['yes'])))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
                return false;
            }
        }
        return false;
    }

    static public function AccessDecision($appName=APP_NAME)
    {
		if(RBAC::checkAccess())
		{
            $accessGuid = md5($appName.MODULE_NAME.ACTION_NAME);
			if(empty($_SESSION[ADMIN_AUTH_KEY]))
			{
				if($_SESSION[$accessGuid])
				{
					return true;
				}
				$accessList = $_SESSION[_ACCESS_LIST];
                
                $module = defined('P_MODULE_NAME')?  P_MODULE_NAME   :   MODULE_NAME;
				if(!isset($accessList[strtoupper($appName)][strtoupper($module)][strtoupper(ACTION_NAME)]))
				{
                    $_SESSION[$accessGuid]  =   false;
                    return false;
                }
				else
				{
                    $_SESSION[$accessGuid]	=	true;
                }
			}
			else
			{
				return true;
			}
        }
        return true;
    }
}

