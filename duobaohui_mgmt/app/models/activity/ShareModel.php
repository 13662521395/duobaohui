<?php

/**
 * 分享送红包
 * @author guoshijie@shinc.net
 * @version v1.0
 * @copyright shinc
 */
namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;

use Laravel\Model\LotteryMethodModel;
use Illuminate\Support\Facades\Log;//引入日志类

class ShareModel extends Model {
    
    public function __construct(){
        $this->init();
    }
    
    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s');
    }
    
    public function hasGetRedPacket($tel, $activityId) {
        $list = DB::select('select id,tel,amount from sh_red_packet where tel = ? and sh_activity_id = ?', array($tel,$activityId));
        if(count($list) > 0) {
            return true;
        }
        return false;
    }
    
    public function addRedPacket($tel) {
        $data['tel'] = $tel;
        $data['amount'] = '1';
        $data['expiry_time'] = date('Y-m-d H:i:s', strtotime('7 days'));
        $data['create_time'] = $this->nowDateTime;
        $data['sh_activity_id'] = 1;
        $res = DB::table('red_packet')->insert($data);
        if($res) {
            return $data;
        }
        return null;
    }

    /**
     * 添加商家与手机号关系
     * @param $shopId 商家id
     * @param $tel  用户手机号
     * @param $scene
     * @return null
     */
    public function addShopUserTel($shopId,$tel,$scene) {
        $data['sh_user_tel'] = $tel;
        $data['sh_shop_id'] = $shopId;
        $data['scene'] = $scene;
        $data['create_time'] = date('y-m-d H:i:s',time());

        $has = DB::select('select 1 from sh_shop_tel where sh_user_tel = ? and sh_shop_id = ?', array($tel,$shopId));
        if(!$has) {
            $res = DB::table('shop_tel')->insert($data);
            return $res;
        }
        return null;
    }

    /**
     * 添加用户与推荐手机号关系
     * @param $userId 用户id
     * @param $tel  用户手机号
     * @return
     */
    public function addUserFriend($userId,$tel) {
        $data['tel'] = $tel;
        $data['sh_user_id'] = $userId;
        $data['create_time'] = date('y-m-d H:i:s',time());

        $has = DB::select('select 1 from sh_user_friends where tel = ? and sh_user_id = ?', array($tel,$userId));
        if(!$has) {
            $res = DB::table('user_friends')->insert($data);
            return $res;
        }
        return null;
    }

    /**
     * 验证手机号是否正确
     * @param $mobile
     * @return boolean
     */
    function isMobile($mobile) {
        if (!is_numeric($mobile)) {
            return false;
        }
        return preg_match('#^13[\d]{9}$|^14[5,7]{1}\d{8}$|^15[^4]{1}\d{8}$|^17[0,6,7,8]{1}\d{8}$|^18[\d]{9}$#', $mobile) ? true : false;
    }
    
    
}