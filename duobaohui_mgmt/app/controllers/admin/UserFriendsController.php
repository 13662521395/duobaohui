<?php
/**
 * 客户端邀请好友
 * @author		guoshijie@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Admin;

use AdminController;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\View;
use Laravel\Model\UserFriendsModel;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Input;

class UserFriendsController extends AdminController {

    private $friendsM;
    private $_length = 20;

    public function __construct(){
        $this->friendsM = new UserFriendsModel();
        parent::__construct();
    }

    /**
     * 邀请好友数量总计列表
     * @return mixed
     */
    public function getUserFriendsNumList(){
        $list = $this->friendsM->getUserFriendsNumList($this->_length);
        $data['selected']	= "user";
        $data['msg']		= "";
        $data['list'] = $list;
        return Response::view('admin.user.userFriendsList',$data);
    }

    /**
     * 每日邀请
     */
    public function getUserFriendsNumByDate(){
        $date = Input::has('date')?Input::get('date'):date('Y-m-d');
        $list = $this->friendsM->getUserFriendsNumListByDate($this->_length,$date);
        $data['selected']	= "user";
        $data['msg']		= "";
        $data['list'] = $list;
        $data['date'] = $date;
        $data['today'] = date('Y-m-d',time());
        return Response::view('admin.user.userFriendsListByDay',$data);
    }
}