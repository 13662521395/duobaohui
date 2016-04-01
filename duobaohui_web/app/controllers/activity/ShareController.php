<?php

/**
 * 分享
 * @author		guoshijie@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */
namespace Laravel\Controller\Activity;

use ApiController;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Response;
use Laravel\Model\ShareModel;

class ShareController extends ApiController {

    public function __construct(){
        parent::__construct();
    }

    /**
     * 扫二维码送红包_下载（商家）
     * @param tel 手机号
     * @param activityId 活动id
     * @param shopId 商家id
     * @return array|\Illuminate\Http\JsonResponse
     */
    public function getGiveRedPacket() {
        if ( !Input::has( 'tel' ) || !Input::has( 'activityId' ) || !Input::has( 'shopId' ) || !Input::has( 'scene' ) ) {
            return $this->response = $this->response( '10005' );
        }

        $tel = Input::get( 'tel' );
        $activityId = Input::get( 'activityId' );
        $shopId = Input::get( 'shopId' );
        $scene = Input::get( 'scene' );
        $source = Input::get( 'source' );
        $channel = '7';
        $os_type = Input::has('os_type')?Input::get('os_type'):'3';
        $sh_shop_sub_id = Input::has( 'shopChannelId' )?Input::get( 'shopChannelId' ):0;

        $result['tel'] = $tel;
        $shareM = new ShareModel();
        if($shareM -> isMobile($tel)) {
            $has = $shareM -> hasGetRedPacket($tel,$activityId);
            if($has) {
                $this->response = $this->response( "20501", null ,$result);
            } else {
                $res = $shareM -> addRedPacket($tel,$os_type,$channel,$source);
                if(isset($res)) {
                    $shareM->addShopUserTel($shopId,$tel,$scene,$sh_shop_sub_id);
                    $result['expiry_time'] = $res['expiry_time'];
                    $result['amount'] = $res['amount'];
                    $this->response = $this->response( '20503', null ,$result);
                } else {
                    $this->response = $this->response( '20502', null ,$result);
                }
            }
        } else {
            return $this->response = $this->response( '20504', null ,$result);
        }
        return Response::json( $this->response );
    }

    /**
     * 扫二维码送红包_下载（用户客户端分享）
     * @param tel 手机号
     * @param activityId 活动id
     * @param userId 用户id
     * @return json
     */
    public function getShareRedPacket() {
        if ( !Input::has( 'tel' ) || !Input::has( 'activityId' ) || !Input::has( 'userId' ) ) {
            return $this->response = $this->response( '10005' );
        }

        $tel = Input::get( 'tel' );
        $activityId = Input::get( 'activityId' );
        $userId = Input::get( 'userId' );
        $os_type = Input::has('os_type')?Input::get('os_type'):'3';
        $channel = Input::has('channel')?Input::get('channel'):'2';
        $source = Input::get( 'source' );

        $result['tel'] = $tel;
        $shareM = new ShareModel();
        if($shareM -> isMobile($tel)) {
            $has = $shareM -> hasGetRedPacket($tel,$activityId);
            if($has) {
                $this->response = $this->response( "20501", null ,$result);
            } else {
                $res = $shareM -> addRedPacket($tel,$os_type,$channel,$source);
                if(isset($res)) {
                    $shareM->addUserFriend($userId,$tel,$channel,$source);
                    $result['expiry_time'] = $res['expiry_time'];
                    $result['amount'] = $res['amount'];
                    $this->response = $this->response( '20503', null ,$result);
                } else {
                    $this->response = $this->response( '20502', null ,$result);
                }
            }
        } else {
            return $this->response = $this->response( '20504', null ,$result);
        }
        return Response::json( $this->response );
    }

}