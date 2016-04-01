<?php
namespace Laravel\Controller\Admin;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\App;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Redirect;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\View;
use Laravel\Model\Admin\ActivityModel;
use Laravel\Model\LotteryMethodModel;
use Laravel\Model\GoodsModel;
use Laravel\Service\PeriodService;

/**
 * by zhangtaichao
 * 后台管理，活动相关逻辑
 * Class ActivityController
 * @package Laravel\Controller\Admin
 */
class ActivityController extends \AdminController {
	private $categoryModel;
	private $activityModel;
    private $goodsModel;
	public function __construct(){
		parent::__construct();
		$this->categoryModel = App::make('Laravel\Model\Admin\CategoryModel');
		$this->activityModel = new ActivityModel();
        $this->goodsModel = new GoodsModel();

	}

	/**
	 *
	 */
    public function getListPre(){
		$this->viewData['selected'] = 'activity-list-pre';
//        return Response::view('admin.activity.list');
		return $this->anyList();
	}
	/*
	 * 获取活动列表
	 */
	public function anyList(){
		$list = $this->activityModel->getActivityList();
		$this->viewData['list'] = $list;
		return Response::view('admin.activity.list',$this->viewData);

	}

    /**
     * 新增活动录入页面
     */
    public function getAddActivity(){
		$result = $this->categoryModel->getGoodsCategoryList();
		$data['category'] = $result;
		return Response::view('admin.activity.add',$data);
	}
    /**
     * 修改活动录入页面
     */
    public function getUpdatePre(){
		$result = $this->activityModel->getActivityById(Input::get('activity_id'));
		$data['activityInfo'] = $result;
		return Response::view('admin.activity.updatePre',$data);
	}

    public function postSaveActivity(){
        $validation = Validator::make(Input::all()
            ,array(
                "goods_id" => "required|integer",
                "needTimes" => "required|integer",
                "maxPeriod" => "integer",
                "begin_date" => "required|date_format:Y-m-d",
                "end_date" => "date_format:Y-m-d",
                "is_online" => "required|size:1",
                "is_auto" => "required|size:1")
            ,array(
                "goods_id.integer" => "请选择商品",
                "required" => "不能为空",
                "integer" => "必须为整数"
            )
        );

        if($validation->fails()) {
            $result = array(
                'code'=>'error',
                'messages'=>$validation->messages()
            );
            Log::error('参数校验失败:' . $validation->messages());
           return  Redirect::back()->withErrors($validation)->withInput();
        }

        if(!Input::has('activity_id')) {
            if($this->activityModel->isGoodsUsed(Input::get('goods_id'))) {
                $error['goods_id'] = '所选商品已有活动存在，请勿重复添加';
            }
        }

        $goodInfo = $this->goodsModel->getGoodsInfoById(Input::get('goods_id'));
        $price = (int)$goodInfo->market_price;
        $needTimes = (int)Input::get('needTimes');
        if($price > $needTimes) {
            $error['needTimes'] = '所需人次小于商品市场价 ARE YOU SURE!!! ';
        }


        if(isset($error) && is_array($error) && count($error) > 0) {
            Log::error('参数校验失败:' . json_encode($error));
            return  Redirect::back()->withErrors($error)->withInput();
        }

        if(empty(Input::get('end_date'))) {
            $end_date = date('2100-01-01');
        } else {
            $end_date = Input::get('end_date');
        }
        if(empty(Input::get('maxPeriod'))) {
            $maxPeriod = 2147483647;
        } else {
            $maxPeriod = Input::get('maxPeriod');
        }

        if(Input::has('activity_id')) {//修改

            $updateParam = [
                'max_period' => $maxPeriod,
                'begin_date' => Input::get('begin_date'),
                'end_date' => $end_date,
                'user_id' => $this->_loginUserId,
                'is_online' => Input::get('is_online'),
                'is_auto' => Input::get('is_auto'),
                'need_times' => Input::get('needTimes'),
                'update_time' => date('Y-m-d H:m:s'),
                'is_hot' => Input::get('is_hot')
            ];
            $num = $this->activityModel->updateActivity(Input::get('activity_id'),$updateParam);
            $rs = [
                'code' => 'success',
                'msg' => "修改成功{$num}条记录"
            ];
            Log::info('修改活动成功');
        } else {//新增
            $activity_id = $this->activityModel->insertActivity(
                Input::get('goods_id')
                ,$maxPeriod
                ,Input::get('begin_date')
                ,$end_date
                ,Input::get('needTimes')
                ,Input::get('is_online')
                ,Input::get('is_auto')
                ,$this->_loginUserId
                ,Input::get('is_hot')
            );
            if(Input::get('is_online') == '1') {//新增活动并上线，自动生成新的一期
                $periodService = new PeriodService();
                $result = $periodService->createPeriod($activity_id);
            }
            $rs = [
                'code' => 'success',
                'msg' => '新增活动成功'
            ];
        }

        return Response::view('admin.activity.addResult',$rs);

    }
	public function getGoodsByCategory(){
        if(!Input::has('category_id')) {
            Response::json(array(
                "errormsg" => "category_id is null"
            ));
        }
        $catid = Input::get('category_id');
        $goods = $this->categoryModel->getGoodsInfoByCatId($catid);
		return Response::json($goods);
	}

    public function postCreatePeriod() {
        if(!Input::has('activity_id')) {
            return Response::json(array('code'=>'activity_id is null'));
        }
        $periodService = new PeriodService();
        $result = $periodService->createPeriod(Input::get('activity_id'));
        Log::info("添加期结果=>" . var_export($result,true));
        return Response::json($result);
    }



    /*
     * 查询某个活动期的信息
     */
    public function getPeriodList() {
        if(Input::has('activity_id')) {
            $activity_id = Input::get('activity_id');
            $list = $this->activityModel->getPeriodByActivityId($activity_id);
            $list->appends(Input::all());
            return Response::view('admin.activity.periodList',array('list'=>$list));
        } else {
            Log::warn('activity_id is null');
            return Response::view('admin.activity.periodList');
        }
    }

    public function postUpdateOnline() {
        $result = array();
        if(Input::has('is_online') && Input::has('activity_id')) {
            $num = $this->activityModel->updateActivity(Input::get('activity_id'),
                array(
                    'is_online' => Input::get('is_online'),
                    'user_id' => $this->_loginUserId,
                    'update_time' => date('Y-m-d H:i:s')
                ));
            if($num == 1) {
                $result['msg'] = '更新成功';
            }else {
                $result['msg'] = '更新失败';
            }
        } else {
            $result['msg'] = 'param check error';
            Log::error('param check error:' + json_encode(Input::all()));
        }
        return Response::json($result);
    }

    public function postUpdateAuto() {
        $result = array();
        if(Input::has('is_auto') && Input::has('activity_id')) {
            $num = $this->activityModel->updateActivity(Input::get('activity_id'),
                array(
                    'is_auto' => Input::get('is_auto'),
                    'user_id' => $this->_loginUserId,
                    'update_time' => date('Y-m-d H:i:s')
                ));
            if($num == 1) {
                $result['msg'] = '更新成功';
            }else {
                $result['msg'] = '更新失败';
            }
        } else {
            $result['msg'] = 'param check error';
            Log::error('param check error:' + json_encode(Input::all()));
        }
        return Response::json($result);
    }
}
