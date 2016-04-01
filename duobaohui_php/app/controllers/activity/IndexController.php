<?php
/**
 * 活动首页
 * @author		wanghaihong@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Activity;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session 
use Illuminate\Support\Facades\Response;	//引入response
use Illuminate\Support\Facades\Request;	//引入response
use Illuminate\Support\Facades\Cache;
use Carbon\Carbon;
use Laravel\Model\SystemModel;			//引入model
use Laravel\Model\ActivityModel;			//引入model
use Laravel\Model\BannerModel;			
use Laravel\Model\RechargeModel;//引入充值model
use Laravel\Model\LotteryMethodModel;//引入model


/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class IndexController extends ApiController {
	public function __construct(){
		parent::__construct();
	}

	public function anyWelcome(){
		$bannerM = new BannerModel();
		$row = $bannerM->getWelcome();
		if(!$row){
			$this->response = $this->response(1, '成功' ,array('url'=>'http://7xlbf0.com1.z0.glb.clouddn.com/o_1a7rjcrmn9u5ari1ofc1re8o449.png'));
		}else{
			$this->response = $this->response(1, '成功' ,array('url'=>$row->pic_url));
		}
		return Response::json($this->response);
	}

	public function anyBanner(){
		$version = Input::has('version') ? Input::get('version') : '';
		$bannerM = new BannerModel();
		$list = $bannerM->getBannerList($version);

		$this->response = $this->response(1, '成功' ,$list);
		return Response::json($this->response);
	}

	/** 
	 * 活动商品列表
	 * @return json
	 */
	public function anyList() {
		//echo '耗时'.round(microtime(true)-$GLOBALS['start'],3).'秒<br/><br/>';
		
		$deviceId = Input::has('device_id') ? Input::get('device_id') : 0;
		$cacheKey = 'period_list_jd'.$deviceId;
		$oldStrIds = '';
		$oldIds = array();
		$excludeStrIds = '';
		$condition = array();

		$pageinfo = $this->pageinfo(40);
		$offset = $pageinfo->offset;

		$sortvalue = 'rq';
		$version = '';
		if(Input::has('sort')) $sortvalue = Input::get('sort');
		if(Input::has('version')) $version = Input::get('version');
		switch($sortvalue){
			case 'rq': // 人气
				$sort = array('(current_period*need_times + current_times)','DESC'); break;
			case 'zx': // 最新
				$sort = array('period_id','DESC'); break;
			case 'jd': // 进度
				$sort = array('(current_times/real_need_times)','DESC'); 
				// 为排序重复问题，利用缓存解决
				if ( Input::has('device_id') && $pageinfo->page>1){ // 增量
					$oldStrIds = Cache::get($cacheKey);
					$oldIds = $oldStrIds != '' ? explode(',', $oldStrIds) : array();
				}else{
					Cache::forget($cacheKey);
				}

				// 存储数量大于当前偏移量，说明访问的页面已经存过了,再过滤就不显示了
				if(count($oldIds)<=$pageinfo->offset){
				// 未存储过，过滤存储过的id
					$offset = 0;
					$excludeStrIds = $oldStrIds;
				}


				break;
			case 'zxrs0': // 总须人数-升序 
				$sort = array('need_times','ASC'); break;
			case 'zxrs1': // 总须人数-倒序 
				$sort = array('need_times','DESC'); break;
		}

		$activityM = new ActivityModel();

		if(Input::has('keyword')){
			$condition['goods_name'] = trim(Input::get('keyword'));
			$activityM->saveSearchWord($condition['goods_name']);
		}

		if(Input::has('category_id')){
			$condition['category_id'] = Input::get('category_id');
		}

		if($excludeStrIds!=''){
			$condition['excludeStrIds'] = $excludeStrIds;
		}

		$list = $activityM->getActivityList($offset, $pageinfo->length, $sort, $condition, $version);

		// 排序分页缓存
		if(Input::has('device_id') && Input::has('sort') && Input::get('sort')=='jd' && !empty($list)){
			$ids = array();
			// 第一次读取将缓存，读取过的不再缓存
			if(count($oldIds)<=$pageinfo->offset){
				foreach($list as $v){
					if( !in_array($v->period_id, $oldIds)){
						$tmpList[] = $v;
					}
					$ids[] = $v->period_id;
				}
				$list = $tmpList;

				$strIds = implode(',', $ids);
				if ($oldStrIds!=''){ // 增量
					$strIds = $oldStrIds . ',' .$strIds;
				}
				
				$expiresAt = Carbon::now()->addMinutes(5); // 生命期
				Cache::put($cacheKey, $strIds, $expiresAt);
			}
		}

		$this->response = $this->getPageResponse(1, '成功' ,$list,$pageinfo);
		return Response::json($this->response);
	}

	/*
	 * 图文详情
	 */
	public function anyGoodsDesc(){
		if( !Input::has('goods_id') ){
			return Response::json( $this->response( '10005' ) );
		}

		$goods_id = Input::get('goods_id');
		$activityM = new ActivityModel();
		$list = $activityM->getGoodsDesc($goods_id);

		//$list->goods_desc = str_replace(',', '', $list->goods_desc);
		$this->response = $this->response(1, '成功' ,$list);
		return Response::json($this->response);
	}


	/*
	 * 奖品详情
	 */
	public function anyDetail(){
		if( !Input::has('period_id') && !Input::has('activity_id')){
			return Response::json( $this->response( '10005' ) );
		}
		if( Input::has('period_id')){
			$periodId = Input::get('period_id');
		}
		$activityM = new ActivityModel();
		if(Input::has('activity_id')){
			$periodId = $activityM->getActivityPeriodId(Input::get('activity_id'));
		}
		$loginUserId = Session::has('user') ? Session::get('user.id') : 0;
		$version = Input::has('version') ? Input::get('version') : '';

		$activityDetail = $activityM->getActivityDetail($periodId, $loginUserId);
		//debug($activityDetail);
		if(!$activityDetail){
			$this->response = $this->response(0, '数据不存在' ,$activityDetail);
		}else{
			$systemM    = new SystemModel();
			$ios_audit  = $systemM->checkIOSAudit( Request::header('user-agent') , $version);
			if( $ios_audit ){
				$activityDetail->type =0;
			}else{
				$activityDetail->type =2;
			}
			$this->response = $this->response(1, '成功' ,$activityDetail);
		}
		return Response::json($this->response);
	}

	/*
	 * 参与记录
	 */
	public function anyPeriodUser(){
		if( !Input::has('period_id') ){
			return Response::json( $this->response( '10005' ) );
		}
		$periodId = Input::get('period_id');
		$pageinfo = $this->pageinfo();
		$activityM = new ActivityModel();
		$list = $activityM->getPeriodUserList($periodId, $pageinfo->offset, $pageinfo->length, $pageinfo->end_id);

		$this->response = $this->response(1, '成功' ,$list);
		return Response::json($this->response);
	}



	/*
	 * 最新揭晓页面
	 */
	public function anyResult(){

		$version = '';
		if(Input::has('version')) $version = Input::get('version');
		$pageinfo = $this->pageinfo();
		$activityM = new ActivityModel();
		$list = $activityM->getResultList($pageinfo->offset, $pageinfo->length, $version);
		
		$this->response = $this->response(1, '成功' ,$list);
		return Response::json($this->response);
	}

	/*
	 * 购买的号码
	 */
	public function anyDetailUsercode(){
		//Session::put('user',array('id'=>1,'user_id'=>1));
		if( !Input::has('period_id') || !Session::has('user')){
			return Response::json( $this->response( '10005' ) );
		}
		$periodId = Input::get('period_id');
		$loginUserId = Session::has('user') ? Session::get('user.id') : 0;
		$pageinfo = $this->pageinfo();

		$activityM = new ActivityModel();
		$codes = $activityM->getUserCodeList($periodId, $loginUserId, $pageinfo->offset, $pageinfo->length);

		if(empty($codes)){
			$this->response = $this->response(0, '无数据' ,$codes);
		}else{
			$str_code = array();
			foreach($codes as $arr_code){
				$str_code[] = (string)$arr_code;
			}
			$this->response = $this->response(1, '成功' ,$str_code);
		}
		return Response::json($this->response);
	}

	private function pageinfo($length=20){
		$pageinfo               = new \stdClass;
		$pageinfo->length       = Input::has('length') ? Input::get('length') : $length;;
		$pageinfo->page         = Input::has('page') ? Input::get('page') : 1;
		$pageinfo->end_id       = Input::has('end_id') ? Input::get('end_id') : 0;
		$pageinfo->offset		= $pageinfo->page<=1 ? 0 : ($pageinfo->page-1) * $pageinfo->length;
		//$page->totalNum     = (int)Product::getInstance()->getPurchaseTotalNum();
		$pageinfo->totalNum     = 0; 
		$pageinfo->totalPage    = ceil($pageinfo->totalNum/$pageinfo->length);

		return $pageinfo;
	}

	public function anyCategory(){
		$activityM = new ActivityModel();
		$list = $activityM->getCategoryList();

		$this->response = $this->response(1, '成功' ,$list);
		return Response::json($this->response);
	}

	public function getTest(){

			$lotteryMethodM = new LotteryMethodModel();
			$lotteryMethodM->setBuyOrderStatus(1,1,1);
			die;



			$buyOrder = $lotteryMethodM->getBuyOrder(Input::get('buy_order_id'));
			if(!$buyOrder){
				return false;
			}
			$userId = $buyOrder->user_id;


		die;
		$activityM = new ActivityModel();
		$activityM->createTestData();

		$this->response = $this->response(1, '成功' ,array());
		return Response::json($this->response);
	}

}
