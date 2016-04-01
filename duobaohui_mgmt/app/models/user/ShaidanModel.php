<?php
/**
* 收货地址相关业务逻辑操作
*
* @author wuhui@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Laravel\Model\OrderModel;			
use Laravel\Model\LoginModel;			


class ShaidanModel extends Model {

	public function addShaidan($data , $img){
		$shaidanId = DB::table('user_shaidan')->insertGetId($data);

		if(!$shaidanId){
			return false;
		}

		$shaidanImg = array();
		$i = 0;
		foreach($img as $url){
			$shaidanImg[$i]['sh_user_shaidan_id'] = $shaidanId;
			$shaidanImg[$i]['img_url']			= $url;
			$i++;
		}

		if(!empty($shaidanImg)){

			$addImg = DB::table('shaidan_img')->insert($shaidanImg);
			if(!$addImg){
				return false;
			}
		}

		//更新订单状态
		DB::table('order_info')->where('order_id' , $data['sh_order_id'])->update(array('is_shaidan' => '1'));

		return $shaidanId;

	}	

	public function getShaidan($search  ,  $offset , $length){

		if(isset($search['userId'])){
			$shaidanInfo = DB::table('user_shaidan')->where('is_delete' , 0)->where('sh_user_id' , $search['userId'])->orderBy('id' , 'desc')->skip($offset)->take($length)->get();
		}elseif(isset($search['goodsId'])){
			$shaidanInfo = DB::table('user_shaidan')->where('is_delete' , 0)->where('sh_goods_id' , $search['goodsId'])->orderBy('id' , 'desc')->skip($offset)->take($length)->get();
		}else{
			$shaidanInfo = DB::table('user_shaidan')->where('is_delete' , 0)->orderBy('id' ,  'desc')->skip($offset)->take($length)->get();
		}

		$shaidanIds	= array();
		$orderIds	= array();
		$userIds	= array();

		foreach($shaidanInfo as $sif){
			$shaidanIds[]	= $sif->id;
			$orderIds[]		= $sif->sh_order_id;	
			$userIds[]		= $sif->sh_user_id;
		}

		if(empty($shaidanIds)){
			return false;
		}

		$loginModel = new LoginModel();
		$userInfo	= $loginModel->getUserInfoByUserIds($userIds);

		$orderModel = new OrderModel();
		$orderIds	= implode(',' , $orderIds);
		$orderInfo	= $orderModel->getOrderListByIds($orderIds);

		$imgInfo = DB::table('shaidan_img')->whereIn('sh_user_shaidan_id' , $shaidanIds)->get();

		foreach($shaidanInfo as $sif){
			$img = array();
			foreach($imgInfo as $iif){
				if($sif->id == $iif->sh_user_shaidan_id){
					$img[] = $iif->img_url;
				}	
			}
			$sif->img = $img;
			$sif->orderInfo = '';
			$sif->userInfo	= '';

			foreach($orderInfo as $ov){
				if($ov->order_id == $sif->sh_order_id){
					$sif->orderInfo = $ov;
				}
			}

			foreach($userInfo as $uv){
				if($uv->id == $sif->sh_user_id){
					$sif->userInfo = $uv;
				}
			}


		}

		return $shaidanInfo;
	}
}
