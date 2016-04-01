<?php
/**
 * 后台管理订单控制器
 * @author		zhaozhonglin@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Admin;			// 定义命名空间

use AdminController;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Response;
use Laravel\Model\FeedbackModel;

//引入日志类


/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */
class FeedbackController extends AdminController {

    private $feedbackModel;
    private $_length;

    public function __construct(){
        parent::__construct();
        $this->feedbackModel = new FeedbackModel();
        $this->_length = 10;
    }

    /**
     * 用户反馈列表列表
     */
    public function anyFeedbackList(){
        $totalNum =$this->feedbackModel->getFeedbackTotalNum();
        $totalNum = $totalNum[0]->sum;
        $pageinfo = $this->_pageInfo($this->_length , $totalNum);
        $list = $this->feedbackModel->getFeedbackList($pageinfo);

        $data['list']		= $list;
        $data['pageInfo']	= $pageinfo;
        $data['selected']	= 'user';
        $data = array_merge($data, $this->viewData);
        return Response::view('admin.opinion.opinionList',$data);
    }

    private function _pageInfo($length=40 , $totalNum){
        $pageInfo               = new \stdClass;
        $pageInfo->length       = Input::has('length') ? Input::get('length') : $length;
        $pageInfo->page         = Input::has('page') ? Input::get('page') : 0;
        $pageInfo->offset		= $pageInfo->page<=1 ? 0 : ($pageInfo->page-1) * $pageInfo->length;
        $pageInfo->totalNum     = $totalNum;
        $pageInfo->totalPage    = ceil($pageInfo->totalNum/$pageInfo->length);
        return $pageInfo;
    }

}
