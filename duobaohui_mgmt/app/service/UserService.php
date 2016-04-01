<?php
/**
 * User: zhangtaichao
 * Date: 15/11/4
 * Time: 上午11:15
 */

namespace Laravel\Service;
use Illuminate\Support\Facades\Session;


class UserService {

    public static function getCurrentUser() {
        $userInfo = Session::get('user');
        if(empty($userInfo) || !is_array($userInfo)) {
           return null;
        } else {
            return $userInfo;
        }
    }
    public static function getCurrentUserId() {
        $userInfo = self::getCurrentUser();
        if(empty($userInfo)) {
            return null;
        } else {
            return $userInfo['id'];
        }
    }

}