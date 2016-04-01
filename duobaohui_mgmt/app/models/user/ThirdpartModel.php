<?php
/**
* 第三方登录相关业务逻辑操作
*
* @author xuguangjing@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;


class ThirdpartModel extends Model {

	protected $thirdpart_login = 'thirdpart_login';
	protected $user = 'user';


	/**
	* 第三方登录
	*/
	public function thirdpartLogin($key,$thirdpart,$cid){
		$thirduserInfo=DB::table($this->thirdpart_login)
		->leftJoin('user', 'thirdpart_login.sh_user_id', '=', 'user.id')
		->leftJoin('user_info','user_info.sh_user_id','=','thirdpart_login.sh_user_id')
		->where('key',$key)
		->where('thirdpart',$thirdpart)
		->where('user.sh_company_id',$cid)
		->skip(0)->take(1)
		->select('user.*')
		->get();
		
		if(empty($thirduserInfo)){
			if($thirdpart==0){
				$nick_name='qq-'.mt_rand(1000,9999);
			}
			elseif($thirdpart==1){
				$nick_name='sina-'.mt_rand(1000,9999);
			}
			$userId=DB::table($this->user)
			->insertGetId(array('sh_company_id'=>$cid,'nick_name'=>$nick_name,'head_pic'=>'http://laravel.com/assets/img/laravel-logo.png'));
			
			if($userId>0){
				$userInfo=DB::table($this->user)
				->where('id',$userId)
				->get();
				DB::table($this->thirdpart_login)
				->insertGetId(array('sh_user_id'=>$userId,'thirdpart'=>$thirdpart,'key'=>$key));

				DB::table('user_info')
				->insertGetId(array('sh_user_id'=>$userId,'sh_user_level_id'=>1));

				DB::table('user_config')
				->insertGetId(array('sh_user_id'=>$userId));
				return $userInfo[0];
			}else{
				return false;
			}
		}else{
			return $thirduserInfo[0];
		}
	}


}
