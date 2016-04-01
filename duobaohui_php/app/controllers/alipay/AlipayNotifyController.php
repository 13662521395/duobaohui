<?php
namespace  Laravel\Controller\Alipay; //定义命名空间

use  ApiController;  //引入接口公共父类，用于继承
use  Illuminate\Support\Facades\View; //引入视图类
use  Illuminate\Support\Facades\Input;//引入参数类

use Laravel\Model\NotifyUrlModel;//引入model
use Laravel\Model\LotteryMethodModel;//引入model
use Laravel\Model\ActivityModel;//引入model
use Laravel\Model\RechargeModel;//引入充值model
use Laravel\Model\LoginModel;//引入充值model

use App\Libraries\alipayConfig;//引入支付宝移动支付异步服务器配置文件
use App\Libraries\AlipayNotify;//引入支付宝移动支付异步服务器支付宝扩展
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Cache;

use Illuminate\Support\Facades\Config;

class  AlipayNotifyController extends  ApiController {

	public function  __construct() {
		parent::__construct();
		$this->init();
	}

	private function init(){
	}

    //公共方法
    private function alipayConf(){
        $alipay_config  = new alipayConfig();
        $alipay_configs = $alipay_config->alipayConfigs();
        $alipayNotify = new AlipayNotify($alipay_configs);

        $verify_result = $alipayNotify->verifyNotify();
        return $verify_result;

    }

	//购买操作
	public function anyBuy(){
		if(!Input::has('period_id') || !Input::has('num') || Input::get('num')<=0){
			return Response::json( $this->response( '10005' ) );
		}

		$userId = $this->getUserId();
		if(!$userId){
			return Response::json( $this->response( '10005' ) );
		}

		$periodId=Input::get('period_id');
		$num = Input::get('num');

		$activityM	= new ActivityModel();
		$realPrice	= $activityM->getActivityRealPrice($periodId);
		if( !$realPrice ){
			$this->response = $this->response(0,'未找到商品');
			return Response::json($this->response);
		}

		$totalPrice	= $num * $realPrice->real_price;
		$buyId 	= time() . $userId . mt_rand(100 , 999) . $periodId;
		$buy 	= $this->_buy($userId , $periodId , $num , $totalPrice , $buyId);

		if($buy === true){
			$loginModel		= new LoginModel();
			$userInfo		= $loginModel->getUserInfoByUserId($userId);
            $userInfo->buy_id = $buyId;
			$this->response = $this->response(1,'成功' , $userInfo);
			return Response::json($this->response);
		}elseif($buy == 1){
			$this->response = $this->response(0,'用户不存在');
			return Response::json($this->response);
		}elseif($buy == 2){
			$this->response = $this->response(0,'夺宝币不足,请充值!');
			return Response::json($this->response);
		}elseif($buy == 3){
			$this->response = $this->response(0,'购买数量大于实际剩余数量!');
			return Response::json($this->response);
		}else{
			$this->response = $this->response(0,'未知错误');
			return Response::json($this->response);
		}


	}

	private function _buy( $userId , $periodId , $num , $price , $buyId ){
		// 奖状态
		$activityM = new ActivityModel();
		$periodStatus = $activityM->checkPeriodStatus($periodId);
		if($periodStatus==2){ //已下线
			$lotteryMethodM = new LotteryMethodModel();
			$lotteryMethodM->setBuyOrderStatus($userId, $periodId, 3);
			return false;
		}

		if($periodStatus==0){ //未开奖

            $recharge = new RechargeModel();

			//获取用户余额
			$userMoney = $recharge->getUserMoney($userId);

			if(!$userMoney){
				$lotteryMethodM = new LotteryMethodModel();
				$lotteryMethodM->setBuyOrderStatus($userId, $periodId, 4);
				return 1;
				//$this->response = $this->response(0,'用户不存在');
			}

			if((float)$price > (float)$userMoney->money){
				$lotteryMethodM = new LotteryMethodModel();
				$lotteryMethodM->setBuyOrderStatus($userId, $periodId, 7);
				return 2;
				//$this->response = $this->response(0,'夺宝币不足,请充值!');
			}

			//获取次数
			$activityNum = $activityM->getCurrentAndReal($periodId);

			//计算剩余次数
			$surplusNum = $activityNum->real_need_times - $activityNum->current_times;

			if($surplusNum < $num){
				$lotteryMethodM = new LotteryMethodModel();
				$lotteryMethodM->setBuyOrderStatus($userId, $periodId, 5);
				//'购买数量大于实际剩余数量!'
				return 3;
			}

			$money = (float)$userMoney->money - (float)$price;
			// 购买事务
			$isBuy = $activityM->buyTransaction($userId, $periodId, $num , $buyId, $money);
			if(!$isBuy){
				$lotteryMethodM = new LotteryMethodModel();
				$lotteryMethodM->setBuyOrderStatus($userId, $periodId, 6);
				return false;
			}

		}

		// 奖状态
		$periodStatus = $activityM->checkPeriodStatus($periodId);
		if($periodStatus == 1){
			// 开奖结果事务
			$activityM->resultTransaction($periodId, $userId);
		}

		return true;
	}

    //支付操作
	public function anyNotify(){
		// test 单元测试使用
		if(Config::get('app.debug') && Cache::has('phpunitTest')){
			$phpunitTest = Cache::get('phpunitTest');
			$verify_result = $phpunitTest['verify_result'];
		}else{ // 正常访问
			$verify_result =  $this->alipayConf();
		}

       if(!Input::has('activity_period_id') || !Input::has('buy_id') || !Input::has('buy_order_id')){
			return Response::json( $this->response( '10005' ) );
		}

		$userId = $this->getUserId();
		$activityPeriodId 	= Input::get('activity_period_id');
		$buyId 				= Input::get('buy_id');
		$buyOderId 			= Input::get('buy_order_id');

		if(Config::get('app.debug')){
			if( Input::has('num') ){
				$num = Input::get('num');
			}
			$verify_result['total_fee'] = $verify_result['total_fee'] * 100 * (int)$num;
		}else{
			$activityM = new ActivityModel();
			$realPrice = $activityM->getActivityRealPrice($activityPeriodId);
			$num = $verify_result['total_fee'] / $realPrice->real_price;
		}

		$rechargeId = 0;
		if(Input::has('recharge_id')){
			$rechargeId = Input::get('recharge_id');
		}

       	if($verify_result && $verify_result['trade_status'] == 'TRADE_SUCCESS'){

			if($this->_recharge($verify_result , $userId , $activityPeriodId , $rechargeId, $buyOderId)){
				$lotteryMethodM = new LotteryMethodModel();
				$lotteryMethodM->setBuyOrderStatus($userId, $activityPeriodId,1 );
				$buy = $this->_buy($userId , $activityPeriodId , $num , $verify_result['total_fee'] , $buyId);
				if($buy){
					return 'success';
				}else{
					return 'fail';
				}
			}else{
				return 'fail';
			}

	   }else{
		   	return "fail";

	   }
	}

	public function anyGetUserCodeList() {
		if( !Input::has('buy_id') ){
			return Response::json( $this->response( '10005' ) );
		}

		$buyId 				= Input::get('buy_id');

		$activityModel 	= new ActivityModel();

		$periodUser 	= $activityModel->getPeriodUserByBuyId($buyId);
		if(!$periodUser){
			$this->response = $this->response(10043 , '没有购买信息' );
			return Response::json($this->response);
		}
		if($periodUser->times == 0){
			$this->response = $this->response(10043 , '购买失败' );
			return Response::json($this->response);
		}

		$codeList 		= $activityModel->getUserCodeListByBuyId($buyId , 0 , 10);

		if(empty($codeList)){
			$this->response = $this->response(0 , '生成码失败' );
			return Response::json($this->response);
		}
		$codeTotalNum	= $activityModel->getUserCodeNumBybuyId($periodUser->sh_activity_period_id , $periodUser->user_id , $buyId);

		$data			= array('codeList' => $codeList , 'codeTotalNum'=>$codeTotalNum );
		$this->response = $this->response(1 , '成功' ,$data);
		return Response::json($this->response);
	}

    //充值操作
    public function anyRecharge(){

        $verify_result =  $this->alipayConf();
		if(!Input::has('buy_order_id')){
			return Response::json( $this->response( '10005' ) );
		}

        if($verify_result && $verify_result['trade_status'] == 'TRADE_SUCCESS'){

			if(Config::get('app.debug')){
				$verify_result['total_fee']	= $verify_result['total_fee'] * 2000;
			}

			$buyOderId	= Input::get('buy_order_id');
			$userId		= $this->getUserId();
			if($this->_recharge($verify_result , $userId, 0,0, $buyOderId )){
				return 'success';
			}else{
				return 'fail';
			}

        }else{
            return "fail";
        }
    }

	private function _recharge($verifyResult , $userId , $activityPeriodId = 0 , $rechargeId = 0, $buyOderId=0){
		$rechargeM = new RechargeModel();
		return $rechargeM->rechargeTransaction($verifyResult , $userId , $activityPeriodId, $rechargeId, $buyOderId);
	}



	public function getTestRecharge(){
		if(Config::get('app.debug')){
			$verify_result = Array(
				'discount' => 0.00,
				'payment_type' => 1,
				'subject' => 'iPhone6 4.7英寸 128G',
				'trade_no' => 2015101300001000340065586579,
				'buyer_email' => 13240372487,
				'gmt_create' => '2015-10-13 19:43:03',
				'notify_type' => 'trade_status_sync',
				'quantity' => 1,
				'out_trade_no' => 101319425912922,
				'seller_id' => 2088911708976095,
				'notify_time' => '2015-10-13 19:43:04',
				'body' => 2,
				'trade_status' => 'TRADE_SUCCESS',
				'is_total_fee_adjust' => 'N',
				'total_fee' => 0.01,
				'seller_email' => 'admin@shinc.net',
				'price' => 0.01,
				'buyer_id' => 2088712044133340,
				'use_coupon' => 'N',
				'sign_type' => 'RSA',
				'sign' => 'mVd5lMtnAXEhSjv7ZQfQjeVkTH8kJGm2Hj/9CbZwAf32Us//aiDFSn9xmzlYQIcAt/HsJmMb/dU/FaTkXoBeMB21z+RPcYiizLjtpaxjgEhr75O9ESZVbxzLqiBxAh2J7eieBYofd4P03+PeQNZyVZV2Xm7jhi/t5cqMfVUZp8A='
			);

			file_put_contents('/home/log/php/duobaohui/log', "支付信息\n",FILE_APPEND);
			file_put_contents('/home/log/php/duobaohui/log', print_r($verify_result , true),FILE_APPEND);
			if($verify_result && $verify_result['trade_status'] == 'TRADE_SUCCESS'){

				file_put_contents('/home/log/php/duobaohui/log', "支付宝请求成功\n",FILE_APPEND);

				$userId = $this->getUserId();

				if(Config::get('app.debug')){
					$verify_result['total_fee']	= $verify_result['total_fee'] * 2000;
				}

				if($this->_recharge($verify_result , $userId )){
					echo  'success';
				}else{
					echo  'fail';
				}

			}else{
				echo "fail";
			}
		}

	}





	public function getTestNotify(){
		//$activityM = new ActivityModel();
		//$activityM->resultTransaction(959, 2057);

		die;

		if(Config::get('app.debug')){
			$verify_result = Array(
				'discount' => 0.00,
				'payment_type' => 1,
				'subject' => 'iPhone6 4.7英寸 128G',
				'trade_no' => 2015101300001000340065586579,
				'buyer_email' => 13240372487,
				'gmt_create' => '2015-10-13 19:43:03',
				'notify_type' => 'trade_status_sync',
				'quantity' => 1,
				'out_trade_no' => 101319425912922,
				'seller_id' => 2088911708976095,
				'notify_time' => '2015-10-13 19:43:04',
				'body' => 2,
				'trade_status' => 'TRADE_SUCCESS',
				'is_total_fee_adjust' => 'N',
				'total_fee' => 0.01,
				'seller_email' => 'admin@shinc.net',
				'price' => 0.01,
				'buyer_id' => 2088712044133340,
				'use_coupon' => 'N',
				'sign_type' => 'RSA',
				'sign' => 'mVd5lMtnAXEhSjv7ZQfQjeVkTH8kJGm2Hj/9CbZwAf32Us//aiDFSn9xmzlYQIcAt/HsJmMb/dU/FaTkXoBeMB21z+RPcYiizLjtpaxjgEhr75O9ESZVbxzLqiBxAh2J7eieBYofd4P03+PeQNZyVZV2Xm7jhi/t5cqMfVUZp8A='
			);

			if($verify_result && $verify_result['trade_status'] == 'TRADE_SUCCESS'){

				if(!Input::has('activity_period_id') ){
					file_put_contents('/home/log/php/duobaohui/log', "参数错误\n",FILE_APPEND);
					return Response::json( $this->response( '10005' ) );
				}

				$userId = $this->getUserId();
				$activityPeriodId = Input::get('activity_period_id');

				if(Config::get('app.debug')){
					if( Input::has('num') ){
						$num = Input::get('num');
					}
					$verify_result['total_fee'] = $verify_result['total_fee'] * 100 * (int)$num;
				}else{
					$activityM = new ActivityModel();
					$realPrice = $activityM->getActivityRealPrice($activityPeriodId);
					$num = $verify_result['total_fee'] / $realPrice->real_price;
				}

				file_put_contents('/home/log/php/duobaohui/log', "user_id-----$userId------num------$num\n",FILE_APPEND);

				$rechargeId = 0;
				if(Input::has('recharge_id')){
					$rechargeId = Input::get('recharge_id');
				}

				if($this->_recharge($verify_result , $userId , $activityPeriodId , $rechargeId)){
					file_put_contents('/home/log/php/duobaohui/log', "充值成功\n",FILE_APPEND);
					$buy = $this->_buy($userId , $activityPeriodId , $num , $verify_result['total_fee']);

					return 'success';
				}else{
					return 'fail';
				}

			}else{
				return "fail";

			}
		}
	}



	/*
	 * 生成订单
	 */
	public function anyBuyOrder(){
		if(!Input::has('period_id') || !Input::has('num') || Input::get('num')<=0){
			return Response::json( $this->response( 0, '参数错误' ) );
		}
		if(!Session::has('user')){
			return Response::json( $this->response( 10018 ) );
		}

		$userId = Session::get('user.user_id');
		$periodId=Input::get('period_id');
		$num = Input::get('num');

		$lotteryMethodM = new LotteryMethodModel();
		$newId = $lotteryMethodM->addBuyOrder($periodId, $userId, $num);

		$this->response = $this->response(1 , '订单成功' , $newId);
		return Response::json($this->response);

	}

	private function getUserId(){
		if(Input::has('buy_order_id')){
			$lotteryMethodM = new LotteryMethodModel();
			$buyOrder = $lotteryMethodM->getBuyOrder(Input::get('buy_order_id'));
			if(!$buyOrder){
				return false;
			}
			$userId = $buyOrder->user_id;
		}else{
			$userId = Session::get('user.user_id');
		}
		return $userId;
	}


}

