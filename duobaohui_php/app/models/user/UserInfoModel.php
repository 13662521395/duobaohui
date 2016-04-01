<?php
/**
* 
*用户消息
* @author majianchao@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;


class UserInfoModel extends Model {

	public function __construct() {
		$this->table = 'user_info';
	}

	public function findByUserId($user_id) {
		return DB::table('user_info')->where('sh_user_id',$user_id)->first();
	}


}