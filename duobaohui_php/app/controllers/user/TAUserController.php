<?php

namespace Laravel\Controller\User;	//定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\Redirect;
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Laravel\Model\TAUserModel;
use Laravel\Model\UserModel;				//引用model

use Illuminate\Support\Facades\Response;	//引入response方法
use App\Libraries\Qiniu\Auth;				//引入七牛


/**
 * TA的主页信息
 * by zhangtaichao
 * @package Laravel\Controller\Individual
 */
class TAUserController extends ApiController {

	public function __construct(){
		parent::__construct();
	}

	/**
	 * 获取夺宝记录
	 * 参数:
	 * user_id 用户id
	 * page 页码1,2,3,4...
	 * @return \Illuminate\Http\JsonResponse
     */
	public function anyDuobaoRecord() {
		if(!Input::has('user_id')) {
			$this->response = $this->response(10005,'user_id不能为空');
			return Response::json($this->response);
		}
		$user_id = Input::get('user_id');
		$tamodel = new TAUserModel();
		$res = $tamodel->duobaoRecords($user_id);
		if(!empty($res)) {
			$this->response = $this->response(1,null,$res->getItems());
		} else {
			$this->response = $this->response(0);
		}
		return Response::json($this->response);
	}

	/**
	 * 获取中奖纪录
	 * 参数：
	 * user_id
	 * page 页码：1，2，3，4...
	 * @return \Illuminate\Http\JsonResponse
	 */
	public function anyWinningRecord() {
		if(!Input::has('user_id')) {
			$this->response = $this->response(10005,'user_id不能为空');
			return Response::json($this->response);
		}
		$user_id = Input::get('user_id');
		$tamodel = new TAUserModel();
		$res = $tamodel->winningRecords($user_id);
		if(!empty($res)) {
			$this->response = $this->response(1,null,$res->getItems());
		} else {
			$this->response = $this->response(0);
		}
		return Response::json($this->response);
	}

	public function anyShaidanRecord() {
		$con = new ShaidanController();
		return $con->anyGetShaidan();
	}


	public function anyGetDuobaoCode() {
		if(!Input::has('user_id') || !Input::has('period_id')) {
			$this->response = $this->response(10005,'user_id,period_id 不能为空');
			return Response::json($this->response);
		}
		$tamodel = new TAUserModel();

		$this->response = $this->response(1,null,$tamodel->duobaoCode(Input::get('period_id'),Input::get('user_id')));
		return Response::json($this->response);
	}

	public function anyGetNum() {
		if(!Input::has('user_id')) {
			$this->response = $this->response(10005,'user_id不能为空');
			return Response::json($this->response);
		}
		$tamodel = new TAUserModel();
		$duobao_num = $tamodel->countDuobaoRecords(Input::get('user_id'));
		$duobao_num = $duobao_num->total;
		$winning_num = $tamodel->countWinningRecords(Input::get('user_id'));
		$winning_num = $winning_num->total;
		$shaidan_num = $tamodel->countShandan(Input::get(('user_id')));


		return Response::json($this->response(1,null,compact('duobao_num','winning_num','shaidan_num')));
	}
}
