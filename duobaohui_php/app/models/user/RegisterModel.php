<?php 
/**
 *	用户名注册业务逻辑操作
 *
 *	@author		xuguangjing@shinc.net
 *	@version	v1.0
 *	@copyright	shinc
 */
namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;
use Laravel\Service\JnlService;
use Laravel\Service\RechargeService;
use Illuminate\Support\Facades\Log;

class RegisterModel extends Model {

	/**
	 *	检测用户名是否注册过
	 *
	 *	@param	string	$nick_name	用户名
	 *	@return	boolean
	 */
	public function checkUserName( $nick_name ) {

		$data = DB::table( 'user' )	->select( 'id' )
				->where( 'nick_name', $nick_name )
				->get();

		if( !empty($data) ) {
			return true;
		}else{
			return false;
		}	
	}


	/**
	 *	新增用户名密码注册信息
	 *	@param	string	$nick_name	用户名
	 *	@return	$data
	 */
	public function addUserName( $nick_name, $password ) {

		date_default_timezone_set( 'PRC' );
		$createTime = time();

		$userId = DB::table( 'user' )->insertGetId( array(
				'nick_name' => $nick_name,
				'password' => $password,
				'head_pic'=>'',
				'create_time' => $createTime,
		));

		if( $userId>0 ) {
				
				date_default_timezone_set( 'PRC' );
				$createTime = time();

				DB::table( 'user_info' ) ->insertGetId(array(
					'sh_user_id'=> $userId,
					'sh_user_level_id'=>1,
					'create_time' => $createTime,
				));

				DB::table ( 'user_config') ->insertGetId(array(
					'sh_user_id' => $userId
				));
		}
		return $userId;

	}


/********************************************************************************/
   /**
	 * 检测用户手机号是否注册过
	 * 
	 * @param string $tel 手机号
	 * @return boolean
	 */
	public function checkUserTel( $tel ) {

		$res = DB::table( 'user' ) ->select( 'id' )
				->where( 'tel', $tel )
				->get();

		if( !empty( $res ) ) {
			return true;
		}else{
			return false;
		}
	}


	/**
	 * 检测验证码
	 * 
	 * @param	string	$tel 手机号	
	 * @param	string	$code	验证码
	 * @return boolean
	 */
	public function checkVerify( $tel, $code ) {

		$res = DB::table( 'user_vertify_code' ) ->select('id')
				->where( 'tel', $tel )
				->where( 'vertify_code', strtoupper($code) )
				->where( 'live_time', '>', time() )
				->count();
		
		if( $res > 0 ) {
			
			return false;
		}else{
		
			return true;
		}
	}


	/**
	 * 验证码入库
	 * @param string $tel 电话号
	 * @param string $code 验证码
	 * @param integer $liveTime 存活最大期限
	 * @return boolean
	 */
	public function writeVerify( $tel, $code, $liveTime ) {

		return DB::table('user_vertify_code')->insertGetId(array(
				'tel'=>$tel,
				'vertify_code'=>$code, 
				'live_time'=>$liveTime
		 ));
	}


	/**
	 * 新增注册用户信息
	 *
	 * @param	string	$tel	手机号
	 * 
	 * @return $userId 
	 */
	public function addUserTel( $tel, $password, $nick_name , $salt='') {

		date_default_timezone_set( 'PRC' );
		$createTime = time();

		$userId = DB::table('user')->insertGetId(array(
					'tel' => $tel,
					'password' => $password,
					'nick_name' => $nick_name,
					'salt' => $salt,
					'head_pic'=>'',
					'create_time' => $createTime,
			));

		if( $userId>0 ) {

				date_default_timezone_set( 'PRC' );
				$createTime = time();
			
				DB::table( 'user_info' ) ->insertGetId(array(
					'sh_user_id'=> $userId,
					'sh_user_level_id'=>1,
					'create_time' => $createTime,
				));

				DB::table ( 'user_config') ->insertGetId(array(
					'sh_user_id' => $userId
				));
				
// 				$this->redPacketToBalance($tel);
		}
	
		return $userId;
	}

	/**
	 * （注册成功后）红包转余额，红包失效
	 * @param string $tel 手机号
	 * @return bool
	 */
	public function redPacketToBalance($tel) {
		$jnlService = new JnlService();
		$rechargeService = new RechargeService();
	    $row = DB::select('select id,tel,amount from sh_red_packet where tel = ? and flag = ?', array($tel,'0'));
	    if(count($row) > 0) {
	        $amount = $row[0]->amount;
			$jnl_res = $jnlService->createJnlTransRecharge($amount,$jnlService::Recharge_Channel_redpacket);
			Log::error($jnl_res);
			$code = $jnl_res['code'];
			Log::error('code==>'.$code);
			if($code=='success'){
				$jnl_no = $jnl_res['jnl_no'];
				$chargeId = $rechargeService->recharge($jnl_no,$amount,$jnl_no,$jnlService::Recharge_Channel_redpacket);
				$status = $chargeId?$jnlService::JnlTrans_Status_pay_success:$jnlService::JnlTrans_Status_pay_fail;
				$res = $jnlService->payCallbackUpdateJnl($jnl_no,$amount,$chargeId,$status,$jnlService::Recharge_Channel_redpacket);
				Log::info('res==>'.$res);
				if($res){
					return DB::table('red_packet') -> where('tel', $tel) -> update(array('flag' => '1'));
				}
			}
	    }
		return false;
	}


}
