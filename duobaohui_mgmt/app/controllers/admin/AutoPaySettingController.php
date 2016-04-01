<?php
namespace Laravel\Controller\Admin;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\App;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Redirect;
use Illuminate\Support\Facades\View;
use Laravel\Model\Admin\AutoPaySettingModel;
use Laravel\Model\Admin\ActivityModel;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Input;


/**
 * by zhaozhonglin
 * 后台管理，参数设置控制器
 * Class AutoPaySettingController
 * @package Laravel\Controller\Admin
 */
class AutoPaySettingController extends \AdminController {
	private $autoPaySettingModel;

	public function __construct(){
		parent::__construct();
        $this->autoPaySettingModel = new AutoPaySettingModel();
		$this->activityModel = new ActivityModel();

	}

	/**刷单方案列表
	 * @return \Illuminate\Http\Response
	 */
    public function getAutoPaySettingListPre(){
		$list = $this->autoPaySettingModel->getSettingList();

        foreach($list as $item){
            $scope_and_rate_list =  explode(',',$item->scope_and_rate);
            $speed = 0;
            foreach($scope_and_rate_list as $scope_and_rate){
                $scope =  explode('|',$scope_and_rate)[0];
                $rate = explode('|',$scope_and_rate)[1];
                $scope_min = explode('~',$scope)[0];
                $scope_max = explode('~',$scope)[1];
                $speed += ((($scope_min+$scope_max)/2)/$item->frequency)*3600*($rate/100);
            }
            $item->speed = $speed;
        }
		$this->viewData['selected'] = 'activity-list-pre';
		$this->viewData['list'] = $list;
		return Response::view('admin.setting.autopay_setting_list',$this->viewData);
	}

	/**刷单活动列表
	 * @return \Illuminate\Http\Response
	 */
	public function anyActivitySettingListPre(){
		if(Input::has('sh_autopay_main_setting_id')) {
			$sh_autopay_main_setting_id = Input::get('sh_autopay_main_setting_id');
            $need_times_min = Input::get('need_times_min');
            $need_times_max = Input::get('need_times_max');
            $activity_name = Input::get('activity_name');
			$list = $this->autoPaySettingModel->getActivityList($sh_autopay_main_setting_id,$need_times_min,$need_times_max,$activity_name)->appends(Input::all());
			$this->viewData['list'] = $list;
			$this->viewData['result'] = Input::all();
//			var_dump($this->viewData);
//			var_dump(Input::all());
//			die;
            Input::flash();
			return Response::view('admin.setting.autopay_setting_activity_list',$this->viewData);
		} else {
			Log::error('sh_autopay_main_setting_id is null');
			return Response::view('admin.setting.autopay_setting_activity_list');
		}
	}

	/**刷单活动添加
	 * @return \Illuminate\Http\JsonResponse|\Illuminate\Http\Response
	 */
	public function anyAddActivitySetting(){
		if(Input::has('sh_autopay_main_setting_id') && Input::has('sh_activity_id') ) {
			$sh_autopay_main_setting_id = Input::get('sh_autopay_main_setting_id');
			$sh_activity_id = Input::get('sh_activity_id');
            $result = $this->autoPaySettingModel->addActivitySetting($sh_autopay_main_setting_id,$sh_activity_id);
            if($result['code']){
                $result['message'] = '加入方案成功';
                $result['code'] = 'success';
            }else{
                $result['message'] = '加入方案失败,活动被'.$result['name'].'使用';
                $result['code'] = 'fail';
            }
			return Response::json($result);
		}else {
			Log::error('para is null');
			$result['message'] = '参数错误';
			$result['code'] = 'fail';
			return Response::json($result);
		}

	}

	/**刷单活动删除
	 * @return \Illuminate\Http\JsonResponse|\Illuminate\Http\Response
	 */
	public function anyDeleteActivitySetting(){
		if(Input::has('sh_autopay_activity_setting_id')) {
			$sh_autopay_activity_setting_id = Input::get('sh_autopay_activity_setting_id');


			$this->autoPaySettingModel->deleteActivitySetting($sh_autopay_activity_setting_id);

			$result['message'] = '删除方案成功';
			$result['code'] = 'success';
			return Response::json($result);
		}else {
			Log::error('para is null');
			$result['message'] = '参数错误';
			$result['code'] = 'fail';
			return Response::json($result);
		}
	}

	/**刷单方案详情
	 * @return \Illuminate\Http\Response
	 */
	public function getAutoPaySettingDetail(){
        if(Input::has('sh_autopay_main_setting_id')) {
            $sh_autopay_main_setting_id = Input::get('sh_autopay_main_setting_id');
            $auto_pay_setting_detail = $this->autoPaySettingModel->getAutoPaySettingDetail($sh_autopay_main_setting_id);
//            $this->viewData['selected'] = 'activity-list-pre';
            $this->viewData['auto_pay_setting_detail'] = $auto_pay_setting_detail;
//            var_dump($this->viewData);die;
            return Response::view('admin.setting.autopay_setting_detail', $this->viewData);
        }else{
            Log::error('sh_autopay_main_setting_id is null');
            return Response::view('admin.setting.autopay_setting_detail');
        }
	}

    /**刷单方案更新
     * @return \Illuminate\Http\Response
     */
    public function anyUpdateAutoPaySetting(){
//            var_dump(Input::all());die;
        if(Input::has('sh_autopay_main_setting_id')) {
            $sh_autopay_main_setting_id = Input::get('sh_autopay_main_setting_id');
            $name = Input::get('name');
            $scope_and_rate = Input::get('scope_and_rate');
            $frequency = Input::get('frequency');
            $para_array = array(
                'name' => $name,
                'scope_and_rate' => $scope_and_rate,
                'frequency' => $frequency
            );
            $this->autoPaySettingModel->updateAutoPaySetting($sh_autopay_main_setting_id,$para_array);
//            $this->viewData['selected'] = 'activity-list-pre';
//            $this->viewData['auto_pay_setting_detail'] = $auto_pay_setting_detail;
//            var_dump($this->viewData);die;
            return Redirect::to('/admin/setting/auto-pay-setting-list-pre');
        }else{
            Log::error('sh_autopay_main_setting_id is null');
            return Response::view('admin.setting.autopay_setting_detail');
        }
    }

    /**刷单方案关闭
     * @return \Illuminate\Http\Response
     */
    public function anyControlAutoPaySetting(){
        if(Input::has('sh_autopay_main_setting_id') && Input::has('flag')) {
            $sh_autopay_main_setting_id = Input::get('sh_autopay_main_setting_id');
            $flag = Input::get('flag');
            $para_array = array(
                'flag' => $flag
            );
            $this->autoPaySettingModel->updateAutoPaySetting($sh_autopay_main_setting_id,$para_array);
            return Redirect::to('/admin/setting/auto-pay-setting-list-pre');
        }else{
            Log::error('sh_autopay_main_setting_id is null');
            return Response::view('admin.setting.autopay_setting_detail');
        }
    }

    public function getTest(){
        Log::info('aaa');
        $this->autoPaySettingModel->addActivitySetting('10','100');
    }
}
