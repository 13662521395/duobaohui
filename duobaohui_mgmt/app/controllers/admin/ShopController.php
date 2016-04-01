<?php
/**
 * 商户管理
 * User: guoshijie
 * Date: 15/10/29
 * Time: 下午3:43
 */
namespace Laravel\Controller\Admin;

use AdminController;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Redirect;
use Laravel\Model\ShopModel;
use Laravel\Service\ShopService;

class ShopController extends AdminController {
    private $shopM;

    public function __construct() {
        parent::__construct();
        $this->shopM = new ShopModel();
        $this->_length = 10;
    }

    public function adjustDbSum(){
        $res = $this->shopM->adjustDbSum();
        $array = array();
        $array['msg'] = '更新了'.$res.'条数据';
        if($res > 0){
            return Response::json($this->response( 1, null, $array));
        }else{
            return Response::json($this->response( 0 ));
        }
    }

    /**
     * 商户推广首页日报
     */
    public function getDailyReport(){
        $shopService = new ShopService();
        $date = Input::has('date')?Input::get('date'):date('Y-m-d');
        $daliy = $shopService->getDailyReport($date);
        debug($daliy);
    }

    public function getUpdateDays(){
        $this->shopM->adjustDays();
    }

    public function getAdjustShopShareOsTypeSum(){
        $sum = $this->shopM->adjustShopShareOsType();
        $array = array();
        $array['msg'] = '更新了'.$sum.'条数据';
        if($sum >= 0){
            return Response::json($this->response( 1, null, $array));
        }else{
            return Response::json($this->response( 0 ));
        }
    }

    /**
     * 更正历史推广记录(商户)
     * @return \Illuminate\Http\JsonResponse
     */
    public function getUpdateShopShareResult(){
        $shopService = new ShopService();
        $date = Input::has('date')?Input::get('date'):date('Y-m-d');
        $num = Input::has('num')?Input::get('num'):1;
        $sum = 0;
        for($i=0;$i<$num;$i++){
            $date2 = date("Y-m-d",strtotime("$date -".$i." day"));
            $res = $shopService->addShopShareHistory($date2);
            $sum+=$res;
        }
        if($sum){
            return Response::json($this->response( 1 ));
        }else{
            return Response::json($this->response( 0 ));
        }
    }

    public function postAdjustScanNumByDate(){
        $date = Input::has('date')?Input::get('date'):date('Y-m-d');
        $num = Input::has('num')?Input::get('num'):1;
        for($i=0;$i<$num;$i++){
            $date2 = date("Y-m-d",strtotime("$date -".$i." day"));
            $this->shopM->updateScanNumByDate($date2);
            $this->shopM->updateScanNumBeforeDate($date2);
        }
        return Response::json($this->response(1));
    }

    /**
     * 查询某天(商户推广)领红包人群的手机系统占比
     */
    public function postOsTypePercent() {
        $date = Input::has('date')?Input::get('date'):date('Y-m-d');
        $list = $this->shopM->getShopShareOsTypeByDate($date);
        if($list){
            $data['list'] = $list;
            $data['date'] = $date;
            return Response::json($this->response( 1,null,$data));
        }
        return Response::json($this->response(0));
    }

    /**
     * 查询所有领红包人群的手机系统占比
     */
    public function postAllOsTypePercent() {
        $list = $this->shopM->getShopShareOsTypePercent(Input::get('beginDate'),Input::get('endDate'));
        if($list){
            $data['list'] = $list;
            return Response::json($this->response( 1,null,$data));
        }
        return Response::json($this->response(0));
    }

    /**
     * 查询所有扫码人群的手机系统占比
     */
    public function postScanOsTypePercent() {
        $list = $this->shopM->getAllScanOsTypePercent(Input::get('beginDate'),Input::get('endDate'));
        if($list){
            $data['list'] = $list;
            return Response::json($this->response( 1,null,$data));
        }
        return Response::json($this->response(0));
    }

    /**
     * 查询某天扫码人群的手机系统占比
     */
    public function postScanOsTypePercentByDate() {
        $date = Input::has('date')?Input::get('date'):date('Y-m-d');
        $list = $this->shopM->getScanOsTypeByDate($date);
        if($list){
            $data['list'] = $list;
            $data['date'] = $date;
            return Response::json($this->response( 1,null,$data));
        }
        return Response::json($this->response(0));
    }

    /**
     * 查询每天商户推广统计结果
     * @return \Illuminate\Http\Response
     */
    public function getShopShareResult(){
        $list = $this->shopM->getShopShareHistoryResult($this->_length);
        $data['selected']	= "shop";
        $data['msg']		= "";
        $data['list'] = $list;
        $data['today'] = date('Y-m-d',time());
        return Response::view('admin.shop.shopShareResult',$data);
    }

    /**
     * ajax请求商户历史推广统计数据
     * @return \Illuminate\Http\JsonResponse
     */
    public function postShopShareHistory(){
        $list = $this->shopM->getShopShareHistoryResultAjax(15);
        sort($list);
        if($list){
           $data['list'] = $list;
            Log::info($list);
           return Response::json($this->response( 1,null,$data));
        }
        return Response::json($this->response(0));
    }

    /**
     * 记录每天商户统计结果
     */
    public function getAddShopShareResult(){
        $shopService = new ShopService();
        $date = Input::has('date')?Input::get('date'):date('Y-m-d');
        $res = $shopService->addShopShareHistory($date);
        if($res){
            return Response::json($this->response( 1 ));
        }else{
            return Response::json($this->response( 0 ));
        }
    }

    /**
     * 商户推广统计(总数)
     */
    public function getShopShare() {
        $data = array();
        $data['selected']	= "shop";
        $data['msg']		= "";
        $list = $this->shopM->getShopShareList();
        $data['list'] = $list;

        $data['beginDate'] = "";
        $data['endDate'] = "";
        $data['today'] = date('Y-m-d',time());
        $data['transName'] = 'getShopShare';
        return Response::view('admin.shop.shopShare',$data);
    }

    /**
     * 查询某时间段内的商户推广统计(总数)
     */
    public function getShopShareBetweenDate() {
        $shopService = new ShopService();
        $data = array();
        $data['selected']	= "shop";
        $data['msg']		= "";

        if(Input::has('beginDate') && Input::has('endDate')){
            $beginDate = Input::get('beginDate');
            $endDate = Input::get('endDate');
            $data['beginDate'] = $beginDate;
            $data['endDate'] = $endDate;
            $list = $shopService->getShopShareBetweenDate($beginDate,$endDate);
        } else {
            $data['beginDate'] = "";
            $data['endDate'] = "";
            $list = $this->shopM->getShopShareList();
        }

        $data['list'] = $list;
        $data['today'] = date('Y-m-d',time());
        $data['transName'] = 'getShopShareBetweenDate';
        return Response::view('admin.shop.shopShare',$data);
    }

    /**
     * 每日推广详情
     * @return \Illuminate\Http\Response
     */
    public function getShopShareByDate(){
        $date = Input::has('date')?Input::get('date'):date('Y-m-d');
        $shopService = new ShopService();
        $list = $shopService->getShopShareByDate($date);
        $data['selected']	= "shop";
        $data['msg']		= "";
        $data['list'] = $list;
        $data['date'] = $date;
        $data['today'] = date('Y-m-d',time());
        return Response::view('admin.shop.shopShareByDay',$data);
    }

    /**
     * 商户推广统计(总数,根据注册用户倒序)
     */
    public function postShopShareRealSum() {
        $data = array();
        $data['selected']	= "shop";
        $data['msg']		= "";

        if(Input::get('beginDate') && Input::get('endDate')){
            $shopService = new ShopService();
            $beginDate = Input::get('beginDate');
            $endDate = Input::get('endDate');
            $data['beginDate'] = $beginDate;
            $data['endDate'] = $endDate;
            $list = $shopService->getShopShareBetweenDate($beginDate,$endDate);
        }else{
            $list = $this->shopM->getShopShareListAjax(10,'a.real_num');
        }
        if($list){
            $data['list'] = $list;
            return Response::json($this->response( 1,null,$data));
        }
        return Response::json($this->response(0));
    }

    /**
     * 商户推广统计(总数,根据用户转化率)
     */
    public function postShopSharePercent() {
        $data = array();
        $data['selected']	= "shop";
        $data['msg']		= "";

        if(Input::get('beginDate') && Input::get('endDate')){
            $shopService = new ShopService();
            $beginDate = Input::get('beginDate');
            $endDate = Input::get('endDate');
            $data['beginDate'] = $beginDate;
            $data['endDate'] = $endDate;
            $list = $shopService->getShopShareBetweenDate($beginDate,$endDate);
        } else{
            $list = $this->shopM->getShopShareListAjax(10,'a.percent');
        }
        if($list){
            $data['list'] = $list;
            return Response::json($this->response( 1,null,$data));
        }
        return Response::json($this->response(0));
    }

    public function getAddOneShop() {
        $data = array();
        $data['selected']	= "shop";
        $data['msg']		= "";
        $data = array_merge($data, $this->viewData);
        return Response::view('admin.shop.addShop',$data);
    }

    public function postAddShop() {
        $input = Input::all();
        $id=$this->shopM->addShop($input);
        if($id) {
            $data['selected']	= "shop";
            $data['msg']='添加成功';
            $data = array_merge($data, $this->viewData);
            return Response::view('admin.shop.addShop',$data);
        }else{
            $data['selected']	= "shop";
            $data['msg']='添加失败,请稍后再试';
        }
    }

    public function getShopList() {
        $list=$this->shopM->getShopList($this->_length);
        $data['selected']	= "shop";
        $data['msg']		= "";
        $data['list'] = $list;
        $data = array_merge($data, $this->viewData);
        return Response::view('admin.shop.shopList',$data);
    }

    public function postDel() {
        $shopid = Input::get('shopid');
        if (!$shopid){
            return Response::json(array('code' => '-1', 'msg' => 'invalid shopId'));
        }
        if ($this->shopM->delShop($shopid))
            return Response::json(array('code' => '1', 'msg' => 'success'));
        else
            return Response::json(array('code' => '0', 'msg' => 'fail'));
    }

    public function getEdit() {
        $shopId = Input::get('shopId');
        $data = array();
        $data['selected'] = 'shop';
        $data['shop'] = $this->shopM->getShopById($shopId);
        $data = array_merge($data, $this->viewData);
        return Response::view('admin.shop.editShop', $data);
    }

    public function postEditShop() {
        $data = Input::all();
        if (!$data){
            return Response::json(array('code' => '-1', 'msg' => 'invalid data'));
        }

        if (isset($data['shopId']) && !empty($data['shopId'])) {
            $shopId = $data['shopId'];
            unset($data['shopId']);
        } else {
            return Response::json(array('code' => '-2', 'msg' => 'invalid shopId'));
        }

        if ($this->shopM->editShop($shopId, $data))
            return Response::json(array('code' => '1', 'msg' => 'success'));
        else
            return Response::json(array('code' => '0', 'msg' => 'fail'));
    }

}