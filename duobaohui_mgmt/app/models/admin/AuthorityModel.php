<?php
/**
* 权限控制
* @author wuhui@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model\Admin;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;

class AuthorityModel extends \Eloquent {
	public function __construct(){
	}

	/*
	 * 获取访问权限列表
	 */	
	public function getAuthorityList($offset , $length){
		return DB::table('admin_authority')
			->skip($offset)->take($length)
			->get();
	}

	/*
	 * 获取访问权限总数
	 */
	public function getAuthorityTotalNum(){
		return DB::table('admin_authority')
			->count();
	}

	/*
	 * 获取管理员列表
	 */
	public function getAuthorityUserList($offset , $length){
		return DB::table('admin_user')
			->skip($offset)->take($length)
			->get();
	}

	/*
	 * 获取管理员列表总数
	 */
	public function getAuthorityUserTotalNum(){
		return DB::table('admin_user')
			->count();
	}

	/*
	 * 获取管理组列表
	 */
	public function getAuthorityGroupList(){
		return DB::table('admin_group')
			//->skip($offset)->take($length)
			->get();
	}

	/*
	 * 获取管理组列表总数
	 */
	public function getAuthorityGroupTotalNum(){
		return DB::table('admin_group')
			->count();
	}

	/*
	 * 添加访问权限
	 */
	public function addAuthority($url , $notes){
		return DB::table('admin_authority')->insertGetId(array('url'=>$url , 'url_notes'=>$notes));
	}

	/*
	 * 添加管理员
	 */
	public function addAuthorityUser($data){
		return DB::table('admin_user')->insertGetId($data);
	}

	/*
	 * 添加管理组
	 */
	public function addAuthorityGroup($data){
		return DB::table('admin_group')->insertGetId($data);
	}

	/*
	 * 保存访问节点与组之间的关系
	 */
	public function addAuthorityGroupRelation($authorityId , $groupIds){

		$relation = array();
		$i = 0;
		foreach($groupIds as $groupId){
			$relation[$i]['authority_id']	= $authorityId;
			$relation[$i]['group_id']		= $groupId;
			$i++;
		}

		return DB::table('admin_authority_group')->insert($relation);
	}

	/*
	 * 保存用户与组之间的关系
	 */
	public function addAuthorityUserGroupRelation($userId , $groupIds){
		$relation = array();
		$i = 0;
		foreach($groupIds as $groupId){
			$relation[$i]['admin_user_id']	= $userId;
			$relation[$i]['group_id']		= $groupId;
			$i++;
		}

		return DB::table('admin_user_group')->insert($relation);	
	}

	/*
	 * 获取管理用户信息
	 */
	public function getAuthorityUserInfo($userId){
		$info = DB::table('admin_user')
			->where('admin_user.id' , $userId)
			->first();

		$info->group_info = array();

		$userGroup = DB::table('admin_user_group')->where('admin_user_id' , $userId)->get();

		$groupIds = array();

		foreach($userGroup as $ugv){
			$groupIds[] = $ugv->group_id;
		}

		if(empty($groupIds)){
			return $info;
		}

		$groupInfo = DB::table('admin_group')->whereIn('id' , $groupIds)->get();

		foreach($groupInfo as $giv){
			$info->group_info[$giv->id] = $giv;
		}

		return $info;
	}

	/*
	 * 保存修改的用户信息
	 */
	public function saveEditAuthorityUser($userId , $data){
		return DB::table('admin_user')->where('id' , $userId)->update($data);
	}

	/*
	 * 更新用户与组之间的关系
	 */
	public function updateAuthorityUserGroupRelation($userId , $groupIds){
		$relation = array();
		$i = 0;
		foreach($groupIds as $groupId){
			$relation[$i]['admin_user_id']	= $userId;
			$relation[$i]['group_id']		= $groupId;
			$i++;
		}

		DB::table('admin_user_group')->where('admin_user_id' , $userId)->delete();

		return DB::table('admin_user_group')->insert($relation);	
	}

	/*
	 * 获取权限节点信息
	 */
	public function getAuthorityInfo($authorityId){
		$info = DB::table('admin_authority')
			->where('id' , $authorityId)
			->first();

		$info->group_info = array();

		$userGroup = DB::table('admin_authority_group')->where('authority_id' , $authorityId)->get();

		$groupIds = array();

		foreach($userGroup as $ugv){
			$groupIds[] = $ugv->group_id;
		}

		if(empty($groupIds)){
			return $info;
		}

		$groupInfo = DB::table('admin_group')->whereIn('id' , $groupIds)->get();

		foreach($groupInfo as $giv){
			$info->group_info[$giv->id] = $giv;
		}

		return $info;
	}

	/*
	 * 保存修改访问节点的信息
	 */
	public function saveEditAuthority($authorityId , $data){

		return DB::table('admin_authority')->where('id' , $authorityId)->update($data);
	}

	/*
	 * 保存修改访问节点与组之间的关系
	 */
	public function updateAuthorityGroupRelation($authorityId , $groupIds){

		$relation = array();
		$i = 0;
		foreach($groupIds as $groupId){
			$relation[$i]['authority_id']	= $authorityId;
			$relation[$i]['group_id']		= $groupId;
			$i++;
		}

		DB::table('admin_authority_group')->where('authority_id' , $authorityId)->delete();

		return DB::table('admin_authority_group')->insert($relation);
	}

	/*
	 * 获取管理组信息
	 */
	public function getAuthorityGroupInfo($groupId){
		return DB::table('admin_group')->where('id' , $groupId)->first();
	}

	/*
	 * 保存修改的组信息
	 */
	public function saveEditAuthorityGroup($groupId , $data){
		return DB::table('admin_group')->where('id' , $groupId)->update($data);
	}

	/*
	 * 验证是否有权限访问
	 */
	public function isCanVisit($userId , $url){

		$authorityId = DB::table('admin_authority')
			->where('url' , $url)
			->select('id')
			->first();

		if(!$authorityId){
			return false;
		}

		$authorityIds = DB::table('admin_user_group')
			->where('admin_user_id' , $userId)
			->where('admin_authority_group.authority_id' , $authorityId->id)
			->leftJoin('admin_authority_group' , 'admin_user_group.group_id' , '=' , 'admin_authority_group.group_id')
			->select('admin_authority_group.authority_id')
			->get();
	
		if($authorityIds){
			return true;	
		}else{
			return false;
		}
	}

	/*
	 * 删除权限节点
	 */
	public function deleteAuthority($authorityId){
		return DB::table('admin_authority')
			->where('id' , $authorityId)
			->delete();
	}

}

