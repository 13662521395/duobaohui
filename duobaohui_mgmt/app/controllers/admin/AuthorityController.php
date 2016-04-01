<?php
/**
 * @author		wuhui@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Admin;

use AdminController;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Redirect;

use Laravel\Model\Admin\AuthorityModel;

class AuthorityController extends AdminController {
	private $_length = 20;

	public function __construct(){
		parent::__construct();
	}

	private function _pageInfo($length=40 , $totalNum){
		$pageInfo               = new \stdClass;
		$pageInfo->length       = Input::has('length') ? Input::get('length') : $length;;
		$pageInfo->page         = Input::has('page') ? Input::get('page') : 0;
		$pageInfo->offset		= $pageInfo->page<=1 ? 0 : ($pageInfo->page-1) * $pageInfo->length;
		$pageInfo->totalNum     = $totalNum; 
		$pageInfo->totalPage    = ceil($pageInfo->totalNum/$pageInfo->length);

		return $pageInfo;
	}

	/*
	 * 获取访问权限列表
	 */
	public function getAuthorityList(){
		$authorityModel = new AuthorityModel();

		$totalNum	= $authorityModel->getAuthorityTotalNum();

		$pageInfo	= $this->_pageInfo($this->_length , $totalNum);

		$data['pageInfo']	= $pageInfo;
		$data['list']		= $authorityModel->getAuthorityList($pageInfo->offset , $pageInfo->length);
		$data['selected']	= 'admin';

		return Response::View('admin.authority.authority_list' , $data);
	}

	/*
	 * 获取管理员列表
	 */
	public function getAuthorityUserList(){
		$authorityModel = new AuthorityModel();

		$totalNum	= $authorityModel->getAuthorityUserTotalNum();
		$pageInfo	= $this->_pageInfo($this->_length , $totalNum);

		$data['pageInfo']	= $pageInfo;
		$data['list']		= $authorityModel->getAuthorityUserList($pageInfo->offset , $pageInfo->length);
		$data['selected']	= 'admin';

		return Response::View('admin.authority.authority_user_list' , $data);
	}

	/*
	 * 获取管理组列表
	 */
	public function getAuthorityGroupList(){
		$authorityModel = new AuthorityModel();

		//$totalNum	= $authorityModel->getAuthorityGroupTotalNum();
		//$pageInfo	= $this->_pageInfo($this->_length , $totalNum);

		//$data['pageInfo']	= $pageInfo;
		//$data['list']		= $authorityModel->getAuthorityGroupList($pageInfo->offset , $pageInfo->length);
		$data['list']		= $authorityModel->getAuthorityGroupList();
		$data['selected']	= 'admin';

		return Response::View('admin.authority.authority_Group_list' , $data);
	}

	/*
	 * 添加访问权限
	 */
	public function getAddAuthority(){
		
		$authorityModel = new AuthorityModel();

		$groupList = $authorityModel->getAuthorityGroupList();

		$data['groupList']	= $groupList;
		$data['selected']	= 'admin';
		return Response::View('admin.authority.add_authority' , $data);
	}
	
	/*
	 * 保存访问权限
	 */
	public function postSaveAuthority(){
		$data = array();
		$data['code'] = '1';
		$data['msg'] = '添加成功';
		$data['selected']	= 'admin';
		if( !Input::has('url') || !Input::has('notes') ){
			$data['code'] = '0';
			$data['msg'] = '参数错误';
			return Redirect::to('/admin/authority/add-authority');
		}

		$url	= Input::get('url');
		$notes	= Input::get('notes');

		$authorityModel = new AuthorityModel();

		$result = $authorityModel->addAuthority($url , $notes);

		$group = array();

		if( Input::has('group') ){
			$group = Input::get('group');
		}

		if($result){
			if( !empty($group)){
				$isInsert = $authorityModel->addAuthorityGroupRelation($result , $group);
				if($isInsert){
					return Redirect::to('/admin/authority/add-authority');
				}else{
					return Redirect::to('/admin/authority/add-authority');
				}
			}else{
				return Redirect::to('/admin/authority/add-authority');
			}
		}else{
		
			$data['code'] = '0';
			$data['msg'] = '添加权限节点失败';
			return Redirect::to('/admin/authority/add-authority');
		}

	}

	/*
	 * 修改访问权限
	 */
	public function getEditAuthority(){
		if( !Input::has('authority_id') ){
			return Redirect::to('/admin/authority/authority-list');
		}

		$authorityId = Input::get('authority_id');

		$data['selected']	= 'admin';

		$authorityModel = new AuthorityModel();
		
		$info = $authorityModel->getAuthorityInfo($authorityId);
		$data['info'] = $info;

		$groupList = $authorityModel->getAuthorityGroupList();

		$data['groupList']	= $groupList;

		return Response::View('admin.authority.edit_authority' , $data);
	}

	/*
	 * 保存修改访问权限
	 */
	public function postSaveEditAuthority(){
		if( !Input::has('authority_id') ){
			return Redirect::to('/admin/authority/authority-list');
		}

		$authorityId = Input::get('authority_id');

		if( Input::has('url') ){
			$sqlData['url'] = Input::get('url');
		}
		if( Input::has('notes') ){
			$sqlData['url_notes'] = Input::get('notes');
		}

		$group = array();

		if( Input::has('group') ){
			$group = Input::get('group');
		}

		$authorityModel = new AuthorityModel();

		$isUpdate = $authorityModel->saveEditAuthority($authorityId , $sqlData);

		/*
		 * 这里要改成ajax提交
		 */
		$authorityModel->updateAuthorityGroupRelation($authorityId , $group);

		return Redirect::to('/admin/authority/edit-authority?authority_id='.$authorityId);	}

	/*
	 * 添加管理员
	 */
	public function getAddAuthorityUser(){
		$authorityModel = new AuthorityModel();

		$groupList = $authorityModel->getAuthorityGroupList();

		$data['groupList']	= $groupList;
		$data['selected']	= 'admin';
		return Response::View('admin.authority.add_authority_user' , $data);
	}

	/*
	 * 保存添加的管理员
	 */
	public function postSaveAuthorityUser(){
		$data = array();
		$data['code'] = '1';
		$data['msg'] = '添加成功';
		$data['selected']	= 'admin';

		if( !Input::has('nick_name') || !Input::has('password') || !Input::has('repassword') ){
			$data['code'] = '0';
			$data['msg'] = '参数错误';
			return Redirect::to('/admin/authority/add-authority-user');	
		}

		$sqlData['nickname']	= Input::get('nick_name');
		$sqlData['realname']	= Input::get('nick_name');
		$sqlData['password']	= Input::get('password');
		$sqlData['sh_company_id']	= 1;

		$repassword	= Input::get('repassword');

		if( $sqlData['password'] != $repassword){
			$data['code'] = '0';
			$data['msg'] = '两次输入的密码不一致';
			return Redirect::to('/admin/authority/add-authority-user');	
		}

		$sqlData['password'] = md5($sqlData['password']);

		$sqlData['create_time'] = date('Y-m-d H:i:s' , time());

		if( Input::has('tel') ){
			$sqlData['tel'] = Input::get('tel');
		}

		if( Input::has('email') ){
			$sqlData['email'] = Input::get('email');
		}

		$authorityModel = new AuthorityModel();

		$result = $authorityModel->addAuthorityUser($sqlData);

		$group = array();

		if( Input::has('group') ){
			$group = Input::get('group');
		}

		if($result){
			if( !empty($group)){
				$isInsert = $authorityModel->addAuthorityUserGroupRelation($result , $group);
				if($isInsert){
					return Redirect::to('/admin/authority/add-authority-user');
				}else{
				
					$data['code'] = '0';
					$data['msg'] = '添加用户与管理组之前关系失败';
					return Redirect::to('/admin/authority/add-authority-user');
				}
			}else{
				return Redirect::to('/admin/authority/add-authority-user');
			}
		}else{
		
			$data['code'] = '0';
			$data['msg'] = '添加用户失败';
			return Redirect::to('/admin/authority/add-authority-user');
		}
	}

	/*
	 * 修改用户信息
	 */
	public function getEditAuthorityUser(){
		if( !Input::has('user_id') ){
			return Redirect::to('/admin/authority/authority-user-list');
		}

		$userId = Input::get('user_id');

		$data['selected']	= 'admin';

		$authorityModel = new AuthorityModel();
		
		$info = $authorityModel->getAuthorityUserInfo($userId);
		$data['info'] = $info;

		$groupList = $authorityModel->getAuthorityGroupList();

		$data['groupList']	= $groupList;

		return Response::View('admin.authority.edit_authority_user' , $data);
	}

	/*
	 * 保存修改的用户信息
	 */
	public function postSaveEditAuthorityUser(){
		if( !Input::has('user_id') ){
			return Redirect::to('/admin/authority/authority-user-list');
		}

		$userId = Input::get('user_id');

		$sqlData = array();
		if( Input::has('nick_name') ){
			$sqlData['nickname']	= Input::get('nick_name');
		}

		if( Input::has('tel') ){
			$sqlData['tel']		= Input::get('tel');
		}

		if( Input::has('email') ){
			$sqlData['email']	= Input::get('email');
		}

		$group = array();

		if( Input::has('group') ){
			$group = Input::get('group');
		}

		$authorityModel = new AuthorityModel();

		$isUpdate = $authorityModel->saveEditAuthorityUser($userId , $sqlData);

		/*
		 * 这里要改成ajax提交
		 */
		$authorityModel->updateAuthorityUserGroupRelation($userId , $group);

		return Redirect::to('/admin/authority/edit-authority-user?user_id='.$userId);

	}

	/*
	 * 添加管理组
	 */
	public function getAddAuthorityGroup(){
		$data['selected']	= 'admin';
		return Response::View('admin.authority.add_authority_group' , $data);
	}

	/*
	 * 保存添加的管理组
	 */
	public function postSaveAuthorityGroup(){
		$data = array();
		$data['code'] = '1';
		$data['msg'] = '添加成功';
		$data['selected']	= 'admin';

		if( !Input::has('group_name') ){
			$data['code'] = '0';
			$data['msg'] = '参数错误';
			return Response::View('admin.authority.add_authority_group' , $data);	
		}

		$sqlData['group_name']	= Input::get('group_name');

		$authorityModel = new AuthorityModel();

		$result = $authorityModel->addAuthorityGroup($sqlData);

		return Response::View('admin.authority.add_authority_group' , $data);
	}

	/*
	 * 修改管理组
	 */
	public function getEditAuthorityGroup(){
		if( !Input::has('group_id') ){
			return Redirect::to('/admin/authority/authority-group-list');
		}

		$groupId = Input::get('group_id');

		$authorityModel = new AuthorityModel();

		$info = $authorityModel->getAuthorityGroupInfo($groupId);

		$data['info'] = $info;

		$data['selected']	= 'admin';
		return Response::View('admin.authority.edit_authority_group' , $data);
	}

	/*
	 * 保存修改的管理组
	 */
	public function postSaveEditAuthorityGroup(){
		$data['selected']	= 'admin';

		if( !Input::has('group_id') ){
			return Redirect::to('/admin/authority/authority-group-list');	
		}
		
		$groupId = Input::get('group_id');

		if( Input::has('group_name')){
			$sqlData['group_name']	= Input::get('group_name');
		}

		$authorityModel = new AuthorityModel();

		$result = $authorityModel->saveEditAuthorityGroup($groupId , $sqlData);

		return Redirect::to('/admin/authority/edit-authority-group?group_id='.$groupId);	
	}

	/*
	 * 删除权限节点
	 */
	public function getDeleteAuthority(){
		if( !Input::has('authority_id') ){
			return Redirect::to('/admin/authority/authority-list');	
		}

		$page = 0;
		if( Input::has('page') ){
			$page = Input::get('page');
		}

		$authorityId = Input::get('authority_id');

		$authorityModel = new AuthorityModel();

		$authorityModel->deleteAuthority($authorityId);

		return Redirect::to('/admin/authority/authority-list?page='.$page);	

	}

}
