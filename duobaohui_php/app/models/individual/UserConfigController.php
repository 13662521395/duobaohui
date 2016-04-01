<?php
/**
 * 用户个人中心配置信息相关接口操作 
 *
 * @author		xuguangjing@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */
namespace Laravel\Model;	//定义命名空间

use Illuminate\Support\Facades\DB;

class UserConfigModel extends Model {

	protected $table = 'user_config';
	
	public function validData($data){
	
		return $data;
	}
	

}
