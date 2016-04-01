<?php
/**
* 收货地址相关业务逻辑操作
*
* @author liangfeng@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;


class AddressModel extends Model {

	/**
	 * 获取地区信息
	 * 
	 * 
	 * @return array
	 */
	public function getArea(  ) {
		return DB::table( 'area' )->get();
	}


	
	/**
	 * 添加收货地址  设置为默认的,将其他的设置为非默认的
	 * @param user_id   用户id
	 * @param name      收货人姓名
	 * @param mobile    手机号码
	 * @param area      地区
	 * @param address   详细地址
	 * @return	json
	 */

	public function addAddress($user,$name,$mobile,$area,$address) {

		foreach ($user as $key => $userInfo) {	}

		$data = array(
			'user_id'	=> $userInfo->id,
			'consignee' => $name,
			'email'		=> $userInfo->email,
			'district'	=> $area,
			'address'	=> $address,
			'mobile'	=> $mobile,
			'is_default'=> 1
		);

		if( $data['is_default'] ==1 ){
		 	$user_address = DB::table('user_address')
		 	->where('user_id',$userInfo->id)
		 	->where('is_default',1)
		 	->get();
		 	if($user_address != NULL){
		 		// debug($user_address);
		 		$this->editUserAddress($userInfo->id);
		 	}
		 	
		 	$addAddress = DB::table('user_address')->insertGetId($data);
		}

		return $addAddress;
	}


	/**
	 * 设置收货地址为非默认的
	 * @param user_id   用户id
	 * @return	json
	 */

	public function editUserAddress($user_id){
		//获取默认的收货地址的ID
		$id  = $this->getDefaultAddress($user_id);

		//修改默认的收货地址成非默认地址
	  	return   DB::table('user_address')->update(array('is_default'=>0));
	}




	/**
	 *	获取用户默认收货地址信息
	 *	@param		userId		number		用户ID
	 *
	 */

	public function getDefaultAddress($ch_user_id){

		$address = DB::table('user_address')
		->where('user_id',$ch_user_id)
		->where('is_default',1)
		->get();

		if(!$address){
			$oneData=DB::table('user_address')
			->select('id')
			->where('user_id',$ch_user_id)
			->where('is_default',0)
			->get();

			if(!$oneData){

				return false;
			}

			$dataUp = array(
				'is_default' => 1,
				'address_id' => $oneData->address_id
				);

			$addressUp = DB::table('user_address')->update($dataUp);
			
			if(!$addressUp){

				return false;
			}

			$address = DB::table('user_address')
			->where('user_id',$ch_user_id)
			->where('is_default',0)
			->get();

			if(!$address){

				return false;
			}
		}
		// debug($address);
		return $address;
	}


	/**
	 * 根据用户id获取收货地址列表
	 * @param user_id   用户id
	 * @return	json
	 */
	public function getAddressList($userId) {
		return DB::table('user_address')
		->where('user_id',$userId)
		->get();
	}



	/**
	 * 根据收货地址id修改收货信息   
	 * @param address_id    收货地址id
	 * @param name      	收货人姓名
	 * @param mobile    	手机号码
	 * @param area      	地区
	 * @param address   	详细地址
	 * @return	json
	 */
	public function editAddressByAddressId($user_id,$address_id,$name,$mobile,$area,$address,$isDefault) {
		$data = array(
			'consignee' => $name,
			'district'	=> $area,
			'address'	=> $address,
			'mobile'	=> $mobile,
			'is_default'=> $isDefault
		);

		if( $isDefault ==1 ){
			$user_address = DB::table('user_address')
		 	->where('user_id',$user_id)
		 	->where('is_default',1)
		 	->get();
		 	if($user_address != NULL){
		 		// debug($user_address);
		 		$this->editUserAddress($user_id);
		 	}
		}
		return DB::table('user_address')
		->where('address_id',$address_id)
		->update($data);
	}



	/**
	 * 根据用户id删除收货地址列表
	 * @param user_id   用户id
	 * @return	json
	 */
	public function DeleteAddressByAddressId($address_id) {
		return DB::table('user_address')
		->where('address_id',$address_id)
		->delete();
	}
}
