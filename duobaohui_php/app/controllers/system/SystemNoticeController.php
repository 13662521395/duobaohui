<?php
/**
 * 系统通知
 * @author		guoshijie@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\System;			// 定义命名空间

use ApiController;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Response;
use Laravel\Model\SystemNoticeModel;
use Laravel\Model\RedpacketModel;
use Illuminate\Support\Facades\Session;		//引入session
//引入日志类


/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */
class SystemNoticeController extends ApiController {

    private $systemNoticeModel;
    private $_length;

    public function __construct(){
        parent::__construct();
        $this->systemNoticeModel = new SystemNoticeModel();
        $this->_length = 10;
    }

    public function anySystemNoticeList(){
        $list = $this->systemNoticeModel->getSystemNoticeList();
        $this->response = $this->response(ApiController::SUCCESSCODE, ApiController::SUCCESS, $list);
        return Response::json($this->response);
    }

    public function anySystemNoticeById(){
        $noticeId = Input::get('noticeId');
        $notice = $this->systemNoticeModel->getSystemNoticeById($noticeId);
        $this->response = $this->response(ApiController::SUCCESSCODE, ApiController::SUCCESS, $notice);
        return Response::json($this->response);
    }

    public function anyLatestDate(){
        $data =array();
        $res = $this->systemNoticeModel->getLatestDate();

        if($res){

            if(Session::has('user')){
                $redpacket = new RedpacketModel();
                $userId    = Session::get('user.id');
                $newCount  = $redpacket->getLookNewRedpacket($userId);
                $data['redpacket'] = $newCount;
            }else{
                $data['redpacket'] = 0;
            }

            $data['latest_date'] = $res[0]->create_time;
            $this->response = $this->response(ApiController::SUCCESSCODE, ApiController::SUCCESS, $data);
        }else{
            $this->response = $this->response(ApiController::FAILDCODE);
        }
        return Response::json($this->response);
    }

}
