<?php
/**
* 打开软件获取app配置接口
*
* @author xuguangjing@shinc.net
* @version v1.0
* @copyright shinc
*/
namespace Laravel\Model;			//定义命名空间
use Illuminate\Support\Facades\DB; 	//关联数据库

class AppConfigModel extends Model {
	protected $table = 'app_config';
	
	public function validData($data){
		return $data;
	}
	
	public function getAppConfig($companyId){
		return DB::table($this->table)
		->where('sh_company_id',$companyId)
		->where('enabled',1)
		//->select('')
		->get();
	}
}
