<?php
/**
* 活动
* @author wanghaihong@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model\Admin;

use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Session;

class UserModel extends \Eloquent {
	public function __construct(){
	}

	/**
	 * 根据手机系统获取用户
	 */
	public function getUserListByOsType($os_type){
		$res = DB::table('red_packet')->where('flag',0)->where('send_flag',0)->get();
		return $res;
	}

	public function updateRedPacketSendFlag($id,$send_flag){
		return DB::table('red_packet')
			->where('id', $id)
			->update(array('send_flag' => $send_flag));
	}

	/*
	 * 更新用户昵称
	 */	
	public function updateUserNickName($userId , $nickName){
		return DB::table('user')->where('id' , $userId)->update(array('nick_name' => $nickName));
	}

	/**
	 * 计算用户总数
	 */
	public function getAllUserSum($is_real){
		$res = DB::select('select count(*) sum from sh_user where is_real ='.$is_real);
		return $res;
	}

	public function getNewUserSum($is_real,$date){
		$time = strtotime($date.' 00:00:00');
		Log::info($time);
		$res = DB::select('select count(*) sum from sh_user where is_real ='.$is_real.' and create_time > '.$time);
		return $res;
	}

	/*
	 * 用户列表
	 */	
	public function getUserInfo($condition = array()){
		$isReal = 1;
		if( isset($condition['is_real'])){
			$isReal = $condition['is_real'];
		}

//		return DB::table('user')
//			->leftJoin('user_info', 'user_info.sh_user_id', '=', 'user.id')
//			->leftJoin('red_packet', 'red_packet.tel', '=', 'user.tel')
//			->leftJoin('shop_tel', 'shop_tel.sh_user_tel', '=', 'user.tel')
//			->leftJoin('shop', 'shop_tel.sh_shop_id', '=', 'shop.id')
//			->where('is_delete', 0)
//			->where('is_real', $isReal)
//			->select(
//				'user.id', 'user.tel', 'user.nick_name', 'user.locked', 'user.head_pic', 'user.is_delete',
//				'user.money', 'user.is_real',
//				'user_info.age', 'user_info.sex', 'user_info.born', 'user_info.job','user_info.ip','user_info.ip_address',
//				'user.create_time','red_packet.channel',
//				'shop.head_name','shop.branch_name','user.os_type'
//			)
//			->latest('create_time')
//			->paginate((int)Config::get('app.pageSize'));

		return DB::table('user AS user')
			->leftJoin('user_info as user_info', DB::raw('user_info.sh_user_id'), '=', DB::raw('user.id'))
			->leftJoin('red_packet as red_packet', DB::raw('red_packet.tel'), '=', DB::raw('user.tel'))
			->leftJoin('shop_tel as shop_tel', DB::raw('shop_tel.sh_user_tel'), '=', DB::raw('user.tel'))
			->leftJoin('shop as shop', DB::raw('shop_tel.sh_shop_id'), '=', DB::raw('shop.id'))
			->where(DB::raw('user.is_delete'), 0)
			->where(DB::raw('user.is_real'), $isReal)
			->select(DB::raw("
				user.id,
				user.tel,
				user.nick_name,
				user.locked,
				user.head_pic,
				user.is_delete,
				user.money,
				user.is_real,
				user_info.age,
				user_info.sex,
				user_info.born,
				user_info.job,
				user_info.ip,
				user_info.ip_address,
				user.create_time,
				red_packet.channel,
				shop.head_name,
				shop.branch_name,
				CASE user.os_type
					WHEN '10' THEN 'Android'
					WHEN '01' THEN 'IOS'
					WHEN '11' THEN 'Android,IOS'
					ELSE 'Android'
				END os_type
			"))
			->latest(DB::raw('user.create_time'))
			->paginate((int)Config::get('app.pageSize'));
	}


	/*
	 * 获取用户的总数
	 * wuhui
	 */
	public function getUserInfoTotalNum(){
		$totalNum = DB::table('user')->count();
		return $totalNum;
	}

	/*
	 * 昵称登陆
	 */
	public function nickNameLogin($nickName , $password){
		$userInfo =  DB::table('admin_user')
			->where('nickname' , $nickName)
			->where('password' , $password)
			->first();

		if($userInfo){
			unset($userInfo->password);
		}

		return $userInfo;
	}

	/*
	 *
	 */
	public function getUserDetail($id){
		return DB::table('user')->where('id', $id)->first();
	}

	/*
	 *
	 */
	public function editUser($data){
		return DB::table('user')->where('id',$data['id'])->update($data);
	}



}

