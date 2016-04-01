<?php
/**
 * 用户个人中心相关接口逻辑操作 
 *
 * @author		xuguangjing@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */
namespace Laravel\Model;
use Illuminate\Support\Facades\DB;

class UserModel extends Model
{
	protected $table = 'user';
	protected $comment = 'comment';
	protected $userInfo = 'user_info';
	protected $userConfig = 'user_config';
	
	public function validData($data){
		return $data;
	}


	/**
	*	判断用户是否存在
	*/
	public function checkUserId( $id ) {

		$data = DB::table( 'user' )	->select( 'id' )
				->where( 'id', $id )
				->get();

		if( !empty($data) ) {
			return true;
		}else{
			return false;
		}	
	}


	/**
	* 获取用户个人信息
	*/
	public function getUserInfo($userId){
		return DB::table($this->table)
		->leftJoin('user_info','user.id','=','user_info.sh_user_id')
		->leftJoin('user_config','user.id','=','user_config.sh_user_id')
		->where('user.id',$userId)
		->select('user.id','user.nick_name','user.signature','user_info.head_pic','user_info.born','user_info.job','user_config.is_anonymous')
		->get();
	}


	/**
	* 修改用户个人信息
	*/
	public function updataUserInfo($shUserId , $updateDate){
		$user=false;
		$userInfo=false;
		$userConf=false;
		if(!empty($updateDate['user'])){
			$user=DB::table($this->table)
			->where('id',$shUserId)
			->update($updateDate['user']);
		}
		if(!empty($updateDate['user_info'])){
			// date_default_timezone_set('PRC');
			// $updateTime = date('Y-m-d H:i:s');
			// $updateDate['user_info']['update_time'] = $updateTime;
			
			$userInfo=DB::table($this->userInfo)
			->where('sh_user_id',$shUserId)
			->update($updateDate['user_info']);
		}
		if(!empty($updateDate['user_config'])){
			$userConf=DB::table($this->userConfig)
			->where('sh_user_id',$shUserId)
			->update($updateDate['user_config']);
		}
		if($user || $userInfo ||$userConf){
			return true;
		}else{
			return false;
		}
		
	}


	/**
	* 获取用户评论
	*/
	public function getParentComment($userId){

		$commentList = DB::table($this->comment)
		->leftJoin('resources','comment.sh_resources_id','=','resources.id')
		->where('sh_user_id',$userId)
		->select('comment.id as id','comment.content','resources.id as resources_id','resources.title as resources_title')
		->get();

		$commentArr=array();
		
		foreach($commentList as $key=>$val){
			$commentArr[] = $val->id;
		}

		$returnCommentList = DB::table($this->comment)
		->leftJoin('user_info','comment.sh_user_id','=','user_info.sh_user_id')
		->leftJoin('user','comment.sh_user_id','=','user.id')
		->leftJoin('user_mobile','comment.sh_user_id','=','user_mobile.sh_user_id')
		->whereIn('comment.parent_id',$commentArr)
		->select('comment.id as comment_id','comment.parent_id','user_info.head_pic','user.id as user_id','user.nick_name','user_mobile.os','comment.create_time','comment.content')
		->get();
		
		foreach($returnCommentList as $k=>$v){
			foreach($commentList as $key=>$val){
				if($v->parent_id==$val->id){
					$v->parent=$val;
				}
			}
		}
		
		return $returnCommentList;
	}



	public function getUserList($userIds){
		return DB::table($this->table)
		->leftJoin('user_info','user.id','=','user_info.sh_user_id')
		->leftJoin('user_config','user.id','=','user_config.sh_user_id')
		->whereIn('user.id',$userIds)
		->select('user.id','user.nick_name','user.signature','user_info.head_pic','user_config.is_anonymous')
		->get();
	}
}
