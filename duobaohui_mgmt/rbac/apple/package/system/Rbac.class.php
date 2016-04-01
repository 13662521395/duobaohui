<?php
/*
 * 权限管理系统
 * @author
 */
namespace Gate\Package\System;
use Gate\Package\Helper\DBUserProfile;
use Gate\Package\Helper\DBRbacNode;
use Gate\Package\Helper\DBRbacRole;
use Gate\Package\Helper\DBRbacUser;
use Gate\Package\Helper\DBRbacRoleUser;
use Gate\Package\Helper\DBRbacAccess;
use Gate\Package\Helper\DBCategoryHelper;
use Gate\Package\User\Userinfo;
use Gate\Libs\Utilities;

class Rbac{
	private static $instance;
    public static function getInstance(){
        is_null(self::$instance) && self::$instance = new self(); 
        return self::$instance;
    }



	/***********************************************************
	 * 节点列表
	 */
	public function getNodeList($tag=''){
		$strWhere = '1=1';
		$arrWhere = array();
		if($tag=='menu'){
			$strWhere = 'type IN(0,1)';
		}

		$list = DBRbacNode::getConn()->field('node_id,name,status,action,pid,level')->where($strWhere, $arrWhere)->order('level DESC, sort, node_id')->fetchAll();
		$newList = DBCategoryHelper::getCategoryList($list, 'node_id');

		return $newList;
	}


	/*
	 * 菜单列表
	 */
	public function getMenuList($userId){
		$accessNodeIds = $this->accessNodeIds($userId);
		if(empty($accessNodeIds)){
			return false;
		}
		$list = DBRbacNode::getConn()->field('node_id,name,action,pid,type')->where('status=0 AND type IN(0,1) AND node_id IN(:node_ids)', array('node_ids'=>$accessNodeIds)) ->order('level DESC, sort, node_id')->fetchAll();
		$newList = DBCategoryHelper::getCategoryList($list, 'node_id', true);

		return $newList;
	}

	/*
	 *
	 */
	public function getNodeRow($id){
		$row = DBRbacNode::getConn()->field('*')->where('node_id=:id', array('id'=>$id))->limit(1)->fetch();
		return $row;
	}

	public function getNodeRowByAction($action){
		$row = DBRbacNode::getConn()->field('*')->where('action=:action', array('action'=>$action))->fetch();
		return $row;
	}

	/*
	 * 新增节点
	 * roleIds  授权
	 */
	public function addNode($nodeData, $roleIds){
		$GLOBALS['rbac_sql'] = array(); // 获取新增节点的操作sql
		$nodeId = DBCategoryHelper::addCategory($nodeData, 'DBRbacNode', 'node_id');
		if($nodeData['pid']>0 && $nodeData['type']!=2 && $nodeId){
			//非页面区域则把父级改成目录
			DBRbacNode::getConn()->update(array('type'=>0), 'node_id=:id',array('id'=>$nodeData['pid']));
			$GLOBALS['rbac_sql'][] = $GLOBALS['sql_dump'];
		}
		
		if(!empty($roleIds)){
			$accessData = array();
			foreach($roleIds as $val){
				$accessData[] = array(
					'role_id'=>$val,
					'node_id'=>$nodeId
				);
			}
			DBRbacAccess::getConn()->insertAll($accessData);
			//$GLOBALS['rbac_sql'][] = $GLOBALS['sql_dump'];
		}
		return $nodeId;
	}

	/*
	 * 编辑节点
	 * roleIds  授权
	 */
	public function editNode($nodeData, $roleIds){
		$GLOBALS['rbac_sql'] = array(); // 获取节点的操作sql
		$isUp = DBCategoryHelper::editCategory($nodeData, 'DBRbacNode', 'node_id');

		if($isUp){
			// 清除旧的access
			DBRbacAccess::getConn()->delete('node_id=:node_id', array('node_id'=>$nodeData['node_id']));
			//$GLOBALS['rbac_sql'][] = $GLOBALS['sql_dump'];
			// 新的access
			$accessData = array();
			foreach($roleIds as $val){
				$accessData[] = array(
					'role_id'=>$val,
					'node_id'=>$nodeData['node_id']
				);
			}
			DBRbacAccess::getConn()->insertAll($accessData);
			//$GLOBALS['rbac_sql'][] = $GLOBALS['sql_dump'];
		}
		return $isUp;
	}

	/*
	 * 删除节点
	 * roleIds  授权
	 */
	public function delNode($id){
		$GLOBALS['rbac_sql'] = array(); // 获取节点的操作sql
		// 检查是否有子节点
		$row = DBRbacNode::getConn()->field('node_id')->where('pid=:id', array('id'=>$id))->limit(1)->fetch();
		if($row){
			return '请先删除该节点下的所有子节点';
		}

		$isD = DBCategoryHelper::delCategory($id, 'node_id');
		if($isD){
			// 清除
			DBRbacAccess::getConn()->delete('node_id=:node_id', array('node_id'=>$id));
			//$GLOBALS['rbac_sql'][] = $GLOBALS['sql_dump'];
		}
		return $isD;
	}

	/*
	 * 节点的权限
	 */
	public function getAccessRole($nodeId){
		return DBRbacAccess::getConn()->field('role_id')->where('node_id=:node_id', array('node_id'=>$nodeId))->fetchCol();
	}

	/*
	 * 权限组的节点
	 */
	public function getAccessNodeList($roleId){
		$nodeIds = DBRbacAccess::getConn()->field('node_id')->where('role_id =:role_id', array('role_id'=>$roleId))->fetchCol();
		return $nodeIds;
	}

	/*
	 * 权限的节点
	 */
	public function getNodeAccessList($action, $roleIds){
		// 节点
		$list = DBRbacNode::getConn()->field('node_id, type')->where('type !=0 AND action=:action',array('action'=>$action))->fetchAll();
		if(!$list){
			return false;
		}

		$nodeIds = array();
		foreach($list as $v){
			$nodeIds[] = $v->node_id;
		}

		// 检查是否有权限
		$nodeIds = DBRbacAccess::getConn()->field('node_id')->where('role_id IN(:role_id) AND node_id IN(:node_id)', array('role_id'=>$roleIds, 'node_id'=>$nodeIds))->fetchCol();
		if($nodeIds){
			// 有权限的节点
			$accessList = array();
			foreach($list as $v){
				if( in_array($v->node_id, $nodeIds) ){
					$accessList[] = $v;
				}
			}
			return $accessList;
		}
		return false;
	}


	/***********************************************************
	 * 角色列表
	 */
	public function getRoleList(){
		$list = DBRbacRole::getConn()->field('role_id,name,status')->where('status=0', array())->fetchAll();
		return $list;
	}

	/*
	 *
	 */
	public function getRoleRow($roleId){
		$row = DBRbacRole::getConn()->field('*')->where('role_id=:id', array('id'=>$roleId))->limit(1)->fetch();
		return $row;
	}

	/*
	 * 新增角色
	 */
	public function addRole($data){
		$nodeIds = $data['node_id'];
		unset($data['node_id']);
		$newId = DBRbacRole::getConn()->insert($data);
		if($newId && !empty($nodeIds)){
			$accessData = array();
			if(is_array($nodeIds)){
				foreach($nodeIds as $val){
					$accessData[] = array(
						'role_id'=>$newId,
						'node_id'=>$val
					);
				}
				DBRbacAccess::getConn()->insertAll($accessData);
			}else{
				$accessData['role_id'] = $newId;
				$accessData['node_id'] = $data['node_id'];
				DBRbacAccess::getConn()->insert($accessData);
			}
		}
		return $newId;
	}

	/*
	 * 编辑角色
	 */
	public function editRole($data){
		if(!isset($data['role_id'])){
			return false;
		}
		$nodeIds = $data['node_id'];
		unset($data['node_id']);
		$data['update_time'] = time();
		$isUp = DBRbacRole::getConn()->update($data, 'role_id=:id', array('id'=>$data['role_id']));
		if($isUp!==false && !empty($nodeIds)){
			// 清除旧的
			DBRbacAccess::getConn()->delete('role_id=:id', array('id'=>$data['role_id']));
			//
			$accessData = array();
			if(is_array($nodeIds)){
				foreach($nodeIds as $val){
					$accessData[] = array(
						'role_id'=>$data['role_id'],
						'node_id'=>$val
					);
				}
				DBRbacAccess::getConn()->insertAll($accessData);
			}else{
				$accessData['role_id'] = $data['role_id'];
				$accessData['node_id'] = $nodeIds;
				DBRbacAccess::getConn()->insert($accessData);
			}
		}
		return $isUp;
	}

	/*
	 * 删除权限组
	 */
	public function delRole($id){
		return DBRbacRole::getConn()->update(array('status'=>1), 'role_id=:id', array('id'=>$id));
	}

	/***********************************************************
	 * 权限用户列表
	 */
	public function getRoleUserList(){
		$roleUserList = DBRbacRoleUser::getConn()->fetchAll();
		if(empty($roleUserList)){
			return array();
		}
		$userIds		= array();
		$roleIds		= array();
		$userRoleIds	= array();
		foreach($roleUserList as $v){
			$userIds[$v->user_id]		= $v->user_id;
			$userRoleIds[$v->user_id][]	= $v->role_id;
			$roleIds[$v->role_id] = $v->role_id;
		}
		$roleList = DBRbacRole::getConn()->field('role_id AS id, role_id,name')->where('role_id IN(:ids)', array('ids'=>$roleIds))->fetchAssocAll();

		//$userList = Userinfo::getInstance()->getUserListByIds($userIds);
		$userList = DBRbacUser::getConn()->field('*')->where('user_id IN(:ids)', array('ids'=>$userIds))->fetchAll();
		foreach($userList as $vu){
			$vu->role = array();
			foreach($userRoleIds[$vu->user_id] as $rid){
				if(isset($roleList[$rid])){
					$vu->role[] = $roleList[$rid];
				}
			}
		}
		return $userList;
	}

	/*
	 * 用户的权限信息
	 *
	 */
	public function getUserList(){
		$roleUserList = DBRbacRoleUser::getConn()->fetchAll();
		if(empty($roleUserList)){
			return array();
		}

		$userIds		= array();
		$roleIds		= array();
		$userRoleIds	= array();
		foreach($roleUserList as $v){
			$userIds[$v->user_id]		= $v->user_id;
			$userRoleIds[$v->user_id][]	= $v->role_id;
			$roleIds[$v->role_id] = $v->role_id;
		}

		$roleList = DBRbacRole::getConn()->field('role_id AS id, role_id,name')->where('role_id IN(:ids)', array('ids'=>$roleIds))->fetchAssocAll();
		$userList = array();
		foreach($userRoleIds as $ku=>$vrIds){
			$userList[$ku] = new \stdClass();
			$userList[$ku]->user_id = $ku;
			$userList[$ku]->login_name = '账号';
			$userList[$ku]->nick_name = '昵称';
			$userList[$ku]->role = array();
			foreach($vrIds as $vr){
				$userList[$ku]->role[] = $roleList[$vr];
			}
		}

		return $userList;

	}

	/*
	 * 权限用户
	 */
	public function getRoleUser($id){
		return DBRbacRoleUser::getConn()->where('id=:id', array('id'=>$id))->fetch();
	}

	/*
	 * 用户的角色权限
	 */
	public function getUserRoleIds($userId){
		return DBRbacRoleUser::getConn()->field('role_id')->where('user_id=:id', array('id'=>$userId))->fetchCol();
	}

	public function getUserById($userId){
		return  DBRbacUser::getConn()->field('*')->where('user_id =:ids', array('ids'=>$userId))->fetch();
	}
	
	private $accessNodeIds;
	private function accessNodeIds($userId){
		if(is_null($this->accessNodeIds)){
			$roleIds = $this->getUserRoleIds($userId);
			if(in_array(1,$roleIds)){ // admin权限组访问全部节点 ,必须 admin==1 
				$this->accessNodeIds = DBRbacNode::getConn()->field('node_id')->where('status=0', array())->fetchCol();
			}else{
				$this->accessNodeIds = DBRbacAccess::getConn()->field('node_id')->where('role_id IN(:role_id)', array('role_id'=>$roleIds))->fetchCol();
			}
		}

		return $this->accessNodeIds;
	}

	/*
	 * 用户所拥有的节点
	 */
	public function getUserActions($userId){
		$accessNodeIds = $this->accessNodeIds($userId);
		if(empty($accessNodeIds)){
			return array();
		}
		$actions = DBRbacNode::getConn()->field('action')->where('status=0 AND node_id IN(:node_ids)', array('node_ids'=>$accessNodeIds))->fetchCol();
		return $actions;
	}

	/*
	 * 检查账号是否重复
	 */
	public function hasUser($name, $userId=0){
		if($userId>0){
			$row = DBRbacUser::getConn()->field('user_id')->where('login_name=:login_name AND user_id !=:id', array('login_name'=>$name, 'id'=>$userId))->fetch();
		}else{
			$row = DBRbacUser::getConn()->field('user_id')->where('login_name=:login_name', array('login_name'=>$name))->fetch();
		}
		if($row){ // 已存在账号名用户
			return true;
		}
		return false;
	}

	/*
	 * 验证密码
	 */
	public function checkPassword($userId, $password){
		$row = DBRbacUser::getConn()->field('salt,password')->where('user_id=:user_id', array('user_id'=>$userId))->fetch();
		if( !$row){ // 已存在账号名用户
			return false;
		}
		if($row->password==$this->getEncryptPassword($row->salt, $password)){
			return true;
		}
		return false;
	}

	/*
	 * 新增权限用户
	 */
	public function addRoleUser($newData){
		$userData = $newData;
		unset($userData['role_id'],$userData['is_save']);
		$row = DBRbacUser::getConn()->field('user_id')->where('login_name=:login_name', array('login_name'=>$userData['login_name']))->fetch();
		if($row){ // 已存在账号名用户
			return false;
		}
		$userData['salt'] = substr(Utilities::getUniqueId(), 0,6);
		$userData['password'] = $this->getEncryptPassword($userData['salt'], $userData['password']);
		$userData['create_time'] = date('Y-m-d H:i:s');

		$newUserId = DBRbacUser::getConn()->insert($userData);

		$roleIds = DBRbacRoleUser::getConn()
			->field('id')
			->where('user_id=:user_id AND role_id IN(:role_id)', array('user_id'=>$newUserId, 'role_id'=>$newData['role_id']))
			->fetchCol();
		if(!empty($roleIds)){ //过滤已存在的权限
			$newData['role_id'] = array_intersect($newData['role_id'],$roleIds);
		}

		if(empty($newData['role_id'])){
			return true;
		}

		foreach($newData['role_id'] as $rid){
			$data[] = array(
				'user_id' => $newUserId,
				'role_id' => $rid
			);
		}
		$newId = DBRbacRoleUser::getConn()->insertAll($data);
		return $newId;
	}

	/*
	 * 编辑权限用户
	 */
	public function editRoleUser($data){
		$roleIds = $data['role_id'];
		$userId = $data['user_id'];
		$userData = $data;
		unset($userData['role_id']);
		unset($userData['is_save']);
		unset($userData['user_id']);
		// 清除旧的access
		DBRbacRoleUser::getConn()->delete('user_id=:user_id', array('user_id'=>$userId));
		// 新的access
		$newData = array();
		foreach($roleIds as $val){
			$newData[] = array(
				'role_id'=>$val,
				'user_id'=>$userId
			);
		}
		if($userData['password']==''){
			unset($userData['password']);
		}else{
			$row = DBRbacUser::getConn()->field('salt')->where('user_id=:user_id', array('user_id'=>$userId))->fetch();
			$userData['password'] = $this->getEncryptPassword($row->salt, $userData['password']);
		}

		DBRbacRoleUser::getConn()->insertAll($newData);
		DBRbacUser::getConn()->update($userData, 'user_id=:id', array('id'=>$userId));
		return true;
	}

	/*
	 *
	 */
	public function login($data){
		$row = DBRbacUser::getConn()->field('*')->where('login_name=:login_name', array('login_name'=>$data['login_name']))->fetch();
		if($row && $row->password==$this->getEncryptPassword($row->salt, $data['password'])){
			unset($row->password,$row->salt);
			$row->id = $row->user_id;
			return $row;
		}
		return false;
	}

	/*
	 * 删除权限用户
	 */
	public function delRoleUser($id){
		$isD = DBRbacUser::getConn()->delete('user_id=:id', array('id'=>$id));
		if($isD){
			$isD = DBRbacRoleUser::getConn()->delete('user_id=:id', array('id'=>$id));
		}
		return $isD;
	}

	/*
	 * 加密密码
	 */
	public function getEncryptPassword($salt, $password){
        return sha1($salt . md5($password));
	}

}
