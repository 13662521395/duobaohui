<?php
/**
* 用户登录相关业务逻辑操作
*
* @author xuguangjing@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;


class NewloginModel extends Model {

		protected $user = 'user';
/************************************手机号******************************************************/
		/**
		* 检测用户名和密码
		*
		* @param string $tel 手机号用户名
		* @param string $password 密码
		* @return boolean true/false 数据是否通过
		*/
		public function checkLoginTel($tel, $password) {
			//判断数据类型
			//todo
			return true;
		}


		/**
		* 根据手机号获取用户基本信息
		*
		* @param string $tel 手机号
		* @return array 用户基本信息
		*/
		public function getUserInfoByMobile( $tel ) {
			return DB::table( 'user' )
					->leftJoin('user_info','user_info.sh_user_id','=','user.id')
					->where( 'tel', $tel )
					->select( 'user.id','user.tel', 'user.real_name', 'user.nick_name', 'user.password', 'user.locked', 'user.sh_id','user.signature','user.head_pic' )
					->get();
		}


	/**
	 * 检测登录信息
	 *
	 * @param	string	$tel 	手机号
	 * @param	string	$password 	密码
	 * @return	array
	 */
	public function checkReg($tel) {

		$num = DB::table('user')->where('tel', $tel)->count();
		if ($num <= 0){
		 return array('code' => '-1', 'msg' => '帐号不存在,请先注册!');
		}
		
	}



/********************************************用户名********************************************************/
		/**
		* 检测用户名和密码
		*
		* @param string $nick_name 用户名
		* @param string $password 密码
		* @return boolean true/false 数据是否通过
		*/

		public function checkLoginName($nick_name, $password) {
			//判断数据类型
			//todo
			return true;
		}


		/**
		* 根据用户名获取用户基本信息
		*
		* @param string $nick_name 用户名
		* @return array 用户基本信息
		*/
		public function getUserInfoByName( $nick_name) {
			return DB::table( 'user' )
					->leftJoin('user_info','user_info.sh_user_id','=','user.id')
					->where( 'nick_name', $nick_name )
					->select( 'user.id','user.tel', 'user.real_name', 'user.nick_name', 'user.password', 'user.locked', 'user.sh_id','user.signature','user.head_pic' )
					->get();
		}


/******************************************邮箱***********************************************************************/
		/**
		* 检测用户名和密码
		*
		* @param string $email  邮箱用户名
		* @param string $password 密码
		* @return boolean true/false 数据是否通过
		*/

		public function checkLoginEmail($email, $password) {
			//判断数据类型
			//todo
			return true;
		}


		/**
		* 根据用户名获取用户基本信息
		*
		* @param string $email 邮箱用户名
		* @return array 用户基本信息
		*/
		public function getUserInfoByEmail( $email ) {
			return DB::table( 'user' )
					->leftJoin('user_info','user_info.sh_user_id','=','user.id')
					->where( 'email', $email )
					->select( 'user.id','user.tel', 'user.email', 'user.status', 'user.real_name', 'user.nick_name', 'user.password', 'user.locked', 'user.sh_id','user.signature','user.head_pic' )
					->get();
		}

/******************************************登录公用Model*********************************************************************/

		/**
		* 根据用户id获取用户基本信息
		*/
		public function getUserInfoByUserId($userId) {
			return DB::table( 'user' ) 
					->select( 'id', 'tel', 'real_name', 'nick_name', 'password', 'locked', 'sh_id', 'email', 'status' )
					->where( 'id', $userId )
					->get();
		}

		/**
		* 锁定帐号用户
		*
		* @param int $userId 用户id
		* @return boolean true/false
		*/
		public function lockedUser($userId) {
			return DB::table( 'user' ) 
					->where( 'id', $userId )
					->update( ['locked'=>1] );
		}


		

		/**
		* 检测用户是否存在
		*
		* @param int $userId
		* @return boolean
		*/
		public function hasUser( $userId ) {
			$res = DB::table( 'user' ) 
					->select( 'id' )
					->where( 'id', $userId )
					->get();

			if( !empty( $res ) ) {
				return true;
			}

				return false;
		}


		/**
		* 获取登录次数
		*
		* @return int 当天登录次数
		*/
		public function getLoginCount() {
			if( !Session::has( 'login_count' ) ) {
				return 0;
			}

				return Session::get( 'login_count' );
			}


		/**
		* 增加登录次数
		*/
		public function addLoginCount() {
			if( !Session::has( 'login_count' ) ) {
				Session::put('login_count', 0);
			}

			$now = Session::get( 'login_count' );
			$new = $now + 1;
			Session::put( 'login_count', $new );

			return Session::get( 'login_count' );
		}


		/**
		* 清理用户session中登录次数
		*/
		public function clearLoginCount() {
			if( Session::has( 'login_count' ) ) {
				Session::forget( 'login_count' );
			}

		}


		/**
		* 清理session
		*/
		public function clearSession() {
			Session::flush();
		}


		/**
		* 更新用户基本信息
		*
		* @param int $userId
		* @param array $data
		* @return boolean
		*/
		public function updUser( $userId, $data ) {
			return DB::table('user') 
					->where('id', $userId)
					->update( $data );
		}


		/**
		* 记录用户登录信息
		*
		* @param int $userId 用户id
		* @param string $action 用户动作
		* @param string $node 动作注解
		* @return boolean true/false
		*/
		public function writeUserLog($userId, $action, $node='' ) {
			date_default_timezone_set( 'PRC' );
			$createTime = time();
			$data = array(
				'sh_user_id' => $userId,
				'action' => $action,
				'note' => $node,
				'create_time' => $createTime,
			);

			$ret = DB::table( 'user_log' )->insert( $data );

			if( !$ret ) {
				return false;
			}

				return true;
			}

		public function saveSession($userId){
			$key=md5(mt_rand(10,99).$userId);
			Session::put($key, 1);
			return $key;
		}


		/**
		* 检测用户是否登录
		* @param uid
		* @return json
		*/
		public function checkLogin( $userId ) {
			$res = DB::table( 'user' ) ->select( 'id' )
					->get();
			
			if( !empty( $res ) ) {
				return false;
			}else{
				return true;
			}
		}


}
