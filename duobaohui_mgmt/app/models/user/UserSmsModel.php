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


class UserSmsModel extends Model {

	/**
	* 发送消息sms父级ID创建
	*/
	public  function  addSms($user_id , $u_id){	

		$data   = $this->selectSmsId($user_id , $u_id  );
		if($data != '' || $data != null){
				foreach($data as $value){
					$data = $value->id; 
				}
		}
		
		//没有发送过 就创建一个
		if(!$data){

				$data =  DB::table('sms')->insertGetId(
				array(
					'user_id'     => $user_id,
					'u_id'		  => $u_id,
					'status'	  => 0,
					'create_time' => time(),
					)
				);

			}
			
		return $data;
	}

	/**
	* 查询有没有发送过信息 有就在该父级ID创建新消息内容，没有则创建新的
	*/
	public  function  selectSmsId($user_id , $u_id  ){

		$data  = DB::table('sms')
				->select('id' , 'status')
				->where('user_id' , $user_id)
				->where('u_id' , $u_id)
				->get();

		if(!$data){
				$data  = DB::table('sms')
					->select('id' , 'status')
					->where('user_id' , $u_id)
					->where('u_id' , $user_id)
					->get();

		}
		return  $data;

	}

	/**
	* 发送消息sms——info创建
	*/
	public  function  addSmsInfo($sms_id , $user_name , $u_name , $content){
		//debug($sms_id);
		return DB::table('sms_info')->insertGetId(
			array(
				'sh_sms_id'     => $sms_id,
				'u_name'		=> $u_name,
				'user_name'		=> $user_name,
				'status'		=> 0,
				'create_time'   => date('Y-m-d H:i:s'),
				'content'		=> $content
			)
		);
	}
	



	/**
	 * 获取信息的总条数  或者 消息列表  $par 值 为 get() 获取消息列表  或者 count()  获取未读取消息总条数
	 * 
	 */
	
	public  function  getList($u_id ,$par){
		//获取用户父级ID
		$data = DB::table('sms')
				->select('*')
				->where('u_id' , $u_id)
				->get();

		//集合用户信息父级ID		
				$ids = array();
				foreach($data   as $value ){
					$ids[] = $value->id;

				}
				
		//查询用户消息的详细情况
		return   DB::table('sms_info')
				
				->select('*')
				->whereIn('sh_sms_id' , $ids)
				->where('status', 0)
				->$par();
			
	}
	
	
	/**
	 * 获取消息详情 
	 */
	public  function  getContent($sid){
		
		
		return   DB::table('sms_info')	
				->select('*')
				->where('sh_sms_id' , $sid)
				->get();
		
	}
	/**
	 * 更改消息详情
	 */
	public  function  getStatus($sid){

		$data = $this->getContent($sid);
		foreach($data as $value){
			$ids = $value->sh_sms_id;

		}
		//更改用户消息的详细情况
		return   DB::table('sms_info')
				->select('*')
				->where('sh_sms_id' , $ids)
				->update(array('status' => 1));
	}


	/**
	 * 删除信息
	 */
	
	// public  function  delSms($id){            物理删除
    
	// 	return  DB::table('sms_info')
	// 			->where('sh_sms_id',$id)
	// 			->delete();
	// }
	//状态删除
	public  function  delSms($id){		
		
		return  DB::table('sms_info')
				->where('sh_sms_id',$id)
				->update(array('status' => 2));
	}

}