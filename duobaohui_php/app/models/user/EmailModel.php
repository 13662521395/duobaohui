<?php
/**
* 邮箱相关业务逻辑操作
*
* @author xuguangjing@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;


class EmailModel extends Model {

	/**
	 * 检测用户邮箱是否注册
	 * 
	 * @param string $email 	用户邮箱
	 * @return boolean
	 */
	public function checkUserEmail( $email ) {

		$res = DB::table( 'user' ) ->select( 'id' )
				->where( 'email', $email )
				->get();

		if( !empty( $res ) ) {
			return true;
		}else{
			return false;
		}
	}


	/**
	 * 检测邮箱是否验证
	 * 
	 * @param	string	$email  邮箱
	 * @param	string	$token	帐号激活码
	 * @return boolean
	 */
	public function checkToken( $email, $token ) {

		$res = DB::table( 'user' ) ->select('id')
				->where( 'tel', $tel )
				->where( 'token', $token)
				->where( 'token_exptime', '>', time() )
				->count();
		
		if( $res > 0 ) {
			
			return false;
		}else{
		
			return true;
		}
	}


	/**
	 * 链接入库
	 * @param string $email  邮箱
	 * @param string $token  帐号激活码
	 * @param integer $token_exptime 激活码有效期
	 * @return boolean
	 */
	public function writeToken( $email, $token, $token_exptime ) {

		return DB::table('user')->insertGetId(array(
				'email'=>$email,
				'token'=>$token,
				'token_exptime'=>$token_exptime,
				
		 ));
	}


	/**
	 * 新增注册用户信息
	 *
	 * @param	string	$email	邮箱
	 * 
	 * @return $userId 
	 */
	public function addUserEmail( $email, $password, $nick_name ) {

		date_default_timezone_set( 'PRC' );
		$createTime = time();

		$userId = DB::table('user')->insertGetId(array(
					'email' => $email,
					'password' => $password,
					'nick_name' => $nick_name,
					'create_time' => $createTime,
			));

		if( $userId>0 ) {

				date_default_timezone_set( 'PRC' );
				$createTime = time();
			
				DB::table( 'user_info' ) ->insertGetId(array(
					'sh_user_id'=> $userId,
					'sh_user_level_id'=>1,
					'head_pic'=>'http://laravel.com/assets/img/laravel-logo.png',
					'create_time' => $createTime,
				));

				DB::table ( 'user_config') ->insertGetId(array(
					'sh_user_id' => $userId
				));
		}
	
		return $userId;
	}

}