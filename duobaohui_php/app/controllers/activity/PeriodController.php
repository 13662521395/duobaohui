<?php
/**
 * 活动首页
 * @author		wanghaihong@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Activity;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use App\Libraries\Qiniu\Config;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Response;
use Laravel\Model\ActivityPeriodModel;
use Illuminate\Support\Facades\Cache;
use Laravel\Model\RechargeModel;
use Laravel\Service\JnlService;
use Illuminate\Support\Facades\Session;		//引入session
use Illuminate\Support\Facades\Request;
use Laravel\Service\PeriodService;
use Laravel\Service\UserService;
use App\Libraries\TokenUtil;//引入token工具类
use Laravel\Model\SystemModel;			//引入model
use Laravel\Model\RedpacketModel;

/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class PeriodController extends ApiController {

	protected $logContext = array(__CLASS__);

	public function __construct(){
		parent::__construct();
	}

	/**
	 * 夺宝确认页面
	 */
	public function anyDuobaoConfirm() {
		$period_id  = Input::get('period_id');
		$num        = Input::get('num');
		$version = Input::has('version') ? Input::get('version') : '';
		if(!is_numeric($period_id) || !is_numeric($num) || $num == 0) {
			$this->response = $this->response('10005');
		} else {
			$jnlService         = new JnlService();
			$result             = $jnlService->createJnlTransDuobao($period_id,$num);
			Log::info("生成夺宝订单号结果:" + var_export($result,true),$this->logContext);
			$token              = TokenUtil::token_create();
			$result['token']    = $token;

			$systemM    = new SystemModel();
			$ios_audit  = $systemM->checkIOSAudit( Request::header('user-agent') , $version);
			if( $ios_audit ){
				$result['blance_type']     = 1;   //1为隐藏,0为显示
			}else{
				$result['blance_type']     = 0;   //1为隐藏,0为显示
			}

			Cache::add($token,SESSION::get('user.user_id'), 60);

			if(isset($result['jnl_no'])) {

				$periodModel    = new ActivityPeriodModel();
				$periodDetail   = $periodModel->loadPeriodDetail($period_id);

				$rechargeModel  = new RechargeModel();
				$userInfo       = $rechargeModel->getUserMoney(UserService::getCurrentUserId());
				$userInfo->money= intval($userInfo->money);
				$redpacketM     = new RedpacketModel();
				$result['redpackets']     = $redpacketM->getIssueRedpackets(UserService::getCurrentUserId(),$periodDetail->sh_activity_id,$num);

				$merge          = array_merge($result,(array)$periodDetail,(array)$userInfo);
				$this->response = $this->response('1',null,$merge);
			} else {
				$this->response = $this->response('0',isset($result['msg']) ? $result['msg'] : null,$result);
			}
		}
		return Response::json($this->response);
	}

	/**
	 * 使用余额夺宝
	 */
	public function anyDuobaoBalance() {
		$jnl_no = Input::get("jnl_no");
		$redpacket   = Input::has('redpacket_id') ? Input::get('redpacket_id') : '0';
		if(empty($jnl_no)) {
			$this->response = $this->response('10005');
		} else {
			UserService::setIPInfo(Input::ip());
			$periodService = new PeriodService();
			$result = $periodService->duobaoUseBanance($jnl_no,$redpacket);
			if(isset($result['code']) && $result['code'] == 'success') {

				$rechargeModel = new RechargeModel();
				$userInfo = $rechargeModel->getUserMoney(UserService::getCurrentUserId());
				$userInfo->money = intval($userInfo->money);
				$merge = array_merge($result,(array)$userInfo);
				$this->response = $this->response('1',null,$merge);
			} else {
				$this->response = $this->response('0',isset($result['msg']) ? $result['msg'] : null,$result);
			}
		}
		return Response::json($this->response);
	}




	/**
	 * 活动商品列表
	 * @return json
	 */
	public function anyList() {
		//echo '耗时'.round(microtime(true)-$GLOBALS['start'],3).'秒<br/><br/>';

		$sort = array('current_times','DESC'); 
		if(Input::has('sort')){
			switch(Input::get('sort')){
				case 'rq': // 人气
					$sort = array('current_times','DESC'); break;
				case 'zx': // 最新
					$sort = array('period_id','DESC'); break;
				case 'jd': // 进度
					$sort = array('(current_times/real_need_times)','DESC'); break;
				case 'zxrs0': // 总须人数-升序 
					$sort = array('need_times','ASC'); break;
				case 'zxrs1': // 总须人数-倒序 
					$sort = array('need_times','DESC'); break;
			}
		}

		$pageinfo = $this->pageinfo(40);
		$activityM = new ActivityModel();
		$list = $activityM->getActivityList($pageinfo->offset, $pageinfo->length, $sort);
		
		$this->response = $this->response(1, '成功' ,$list);
		return Response::json($this->response);
	}

	/**
	 * 生成下期活动
	 */
	public function anyCreateNextPeriodActivity(){
		$periodService = new PeriodService();
		$periodService->preCreateNextPeriodActivity();
//		$periodService->test();
	}


}
