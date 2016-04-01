<?php
/**
 * 系统通知
 * @author		guoshijie@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Admin;			// 定义命名空间

use AdminController;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\View;
use Laravel\Model\SystemNoticeModel;

//引入日志类


/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */
class SystemNoticeController extends AdminController {

    private $systemNoticeModel;
    private $_length;

    public function __construct(){
        parent::__construct();
        $this->systemNoticeModel = new SystemNoticeModel();
        $this->_length = 10;
    }

    /**
     * 发布系统通知页面
     */
    public function getAddSystemNoticePre(){
        $data = array();
        $data['selected']	= "systemNotice";
        $data['msg']		= "";
        return Response::view('admin.system.addSystemNotice',$data);
    }

    /**
     * 发布系统通知提交
     */
    public function postAddSystemNotice(){
        $title = Input::get('title');
        $content = Input::get('description');

        $res = $this->systemNoticeModel->addSystemNotice($title,$content);
        $data['selected']	= "systemNotice";
        $data['msg'] = $res?"发布成功":"发布失败，请稍候再试";
        return Response::view('admin.system.addSystemNotice',$data);
    }

    /**
     * 发布系统通知提交
     */
    public function postUpdateSystemNotice(){
        $title = Input::get('title');
        $content = Input::get('description');
        $noticeId = Input::get('noticeId');

        $res = $this->systemNoticeModel->updateSystemNotice($title,$content,$noticeId);
        $data['selected']	= "systemNotice";
        $data['notice'] = $this->systemNoticeModel->getSystemNoticeById($noticeId);
        $data['msg'] = $res?"修改成功":"修改失败，请稍候再试";
        return Response::view('admin.system.editSystemNotice',$data);
    }

    public function getSystemNoticeList(){
        $data['selected']	= "systemNotice";
        $data['msg']		= "";

        $list = $this->systemNoticeModel->getSystemNoticeList($this->_length);
        $data['list'] = $list;
        return Response::view('admin.system.systemNoticeList',$data);
    }

    public function postDelNotice(){
        $noticeId = Input::get('noticeId');
        $res = $this->systemNoticeModel->delSystemNotice($noticeId);

        if (!$noticeId){
            return Response::json(array('code' => '-1', 'msg' => 'invalid noticeId'));
        }

        if ($res){
            return Response::json(array('code' => '1', 'msg' => 'success'));
        }else{
            return Response::json(array('code' => '0', 'msg' => 'fail'));
        }
    }

    public function getSystemNoticeById(){
        $noticeId = Input::get('noticeId');
        $data = array();
        $data['selected'] = 'systemNotice';
        $data['notice'] = $this->systemNoticeModel->getSystemNoticeById($noticeId);
        return Response::view('admin.system.editSystemNotice', $data);
    }

}
