<?php
/**
* 活动
* @author wanghaihong@shinc.net
* @version v1.0
* @copyright shinc
*/

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;
use Laravel\Model\LotteryMethodModel;
use App\Libraries\LotteryUtil;//引入彩票工具类
use Laravel\Model\RechargeModel;//引入充值model
use Laravel\Model\SystemModel;
use Illuminate\Support\Facades\Log;//引入日志类
use Illuminate\Support\Facades\Request;
use Illuminate\Support\Facades\Config;

class ActivityModel extends Model {
	public function __construct(){
		$this->init();
	}

	private function init(){
		$this->nowDateTime = date('Y-m-d H:i:s');

	}

	/*
	 * 抽奖活动列表
	 */
	public function getActivityList($offset, $length, $sort, $condition=array(), $version='') {

		// 针对ios 审核，不显示iPhone产品
		$whereApple = '';
		$systemM = new SystemModel();
		if( $systemM->checkIOSAudit( Request::header('user-agent'), $version ) ){
			$whereApple = ' AND (`sh_g`.goods_name not like "%apple%" AND `sh_g`.goods_name not like "%mac%" AND `sh_g`.goods_name not like "%ipad%" AND `sh_g`.goods_name not like "%iphone%") ';
		}

		if(isset($condition['excludeStrIds'])){
			$excludeStrIds = $condition['excludeStrIds'];
		}
		/*
		$activityList = DB::table('activity_period AS '.DB::getTablePrefix().'ap')
				->leftJoin('activity AS '.DB::getTablePrefix().'a','a.id','=','ap.sh_activity_id')
				->leftJoin('goods AS '.DB::getTablePrefix().'g','g.id','=','a.sh_goods_id')
				->where( 'ap.flag', 1 )
				->where( 'a.begin_date', '<' ,  $this->nowDateTime)
				->orderBy($sort[0],$sort[1])
				->skip($offset)->take($length)
				->select( 'ap.id AS period_id','ap.current_times','ap.real_need_times','ap.create_time',
						  'a.id','a.max_period', 'a.price',
						  'g.id AS goods_id','g.goods_name', 'g.goods_img')
				->get();
		 */
		$where = '';
		$params = array('begin_date'=>$this->nowDateTime, 'offset'=>$offset, 'length'=>$length);

		if(!empty($excludeStrIds)){
		   	$where .=  ' AND sh_ap.id NOT IN('.$excludeStrIds.')';
		}
		if(isset($condition['goods_name'])){
			$where .= ' AND sh_g.goods_name LIKE :goods_name';
			$params['goods_name'] = '%'.$condition['goods_name'].'%';
		}
		$where .= $whereApple;

		if(isset($condition['category_id'])){
			// 获取该分类的所有子类的id
			$cids = DB::table('category')->where('pid_path', 'LIKE', $condition['category_id'].'-%')->pluck('id');
			$cids[] = $condition['category_id'];
			$whereInSql = $this->whereInSql(array('category_id'=>$cids));
			$where .= ' AND sh_g.sh_category_id IN('.$whereInSql['sql'].')';
			$params += $whereInSql['param'];
		}

		$fields = '`sh_ap`.`id` as `period_id`, `sh_ap`.`current_times`, `sh_ap`.`real_need_times`, `sh_ap`.`create_time`, `sh_a`.`id`, `sh_a`.`max_period`, `sh_a`.`price`, `sh_g`.`id` as `goods_id`, `sh_g`.`goods_name`, `sh_g`.`goods_img`, `sh_a`.`current_period`';
		$activityList = DB::select("select {$fields} from `sh_activity_period` as `sh_ap`
						left join `sh_activity` as `sh_a`
							on `sh_a`.`id` = `sh_ap`.`sh_activity_id`
						left join `sh_goods` as `sh_g`
							on `sh_g`.`id` = `sh_a`.`sh_goods_id`
						where `sh_ap`.`flag` = 1
							and `sh_a`.`begin_date` < :begin_date
						{$where}
						order by {$sort[0]}  {$sort[1]}
						limit :offset,:length",
					$params);

		foreach($activityList as $v){
			$v->rate = $this->getRate( $v->current_times, $v->real_need_times );

			//$v->rate .= '%';
		}

		return $activityList;
	}

	public function getGoodsDesc($goods_id){
		$desc = DB::table('goods')
				->select('goods_desc')
				->where(array('id' => $goods_id))
				->first();
		return $desc;
	}

	/*
	 * 活动的最新期id
	 */
	public function getActivityPeriodId($activityId){
		return DB::table('activity_period')->where('sh_activity_id', $activityId)->where('flag',1)->orderBy('id','DESC')->pluck('id');
	}

	/*
	 * 活动详情
	 */
	public function getActivityDetail($periodId, $loginUserId){
		$activityDetail = DB::table( 'activity_period AS '.DB::getTablePrefix().'ap' )
				->leftJoin('activity AS '.DB::getTablePrefix().'a','ap.sh_activity_id','=','a.id')
				->leftJoin('goods AS '.DB::getTablePrefix().'g','g.id','=','a.sh_goods_id')
				->where( 'ap.id', $periodId )
				->select( 'ap.id AS period_id','ap.create_time','ap.current_times','ap.real_need_times','ap.period_number',
						  'a.id','a.max_period', 'a.price', 'a.is_online','a.current_period',
						  'g.id AS goods_id','g.goods_name', 'g.goods_img')
				->first();

		if(!$activityDetail){
			return $activityDetail;
		}

		// 状态: 默认进行中
		$activityDetail->status = 0;

		// 进度
		$activityDetail->rate = $this->getRate($activityDetail->current_times, $activityDetail->real_need_times);

		// 轮播图
		$goodsPicList = DB::table('goods_pic')
			->where('sh_goods_id',$activityDetail->goods_id)
			->select('link_url','pic_url')
			->get();

		if(empty($goodsPicList)){
			$goodsPicList[0] = new \stdClass;
			$goodsPicList[0]->title = $activityDetail->goods_name;
			$goodsPicList[0]->type = 0;
			$goodsPicList[0]->link_url = '';
			$goodsPicList[0]->pic_url =$activityDetail->goods_img;

		}else{
			foreach($goodsPicList as $v){
				$v->title = $activityDetail->goods_name;
				$v->type = 0;
			}
		}

		$activityDetail->goods_pic = $goodsPicList;

		// 查看活动人数是否够了
		if($activityDetail->current_times==$activityDetail->real_need_times){
			$result = DB::table( 'period_result' )
				->where('sh_activity_period_id', $activityDetail->period_id)
				//->where( 'luck_code_create_time', '<', $this->nowDateTime )
				->select('luck_code','luck_code_create_time','pre_luck_code_create_time', 'user_id')
				->first();

			if($result){
				// 获取最新一期的period_id
				$nextPeriodId = DB::table('activity_period')
					->where('sh_activity_id', $activityDetail->id)
					->where('flag', 1)
					->take(1)
					->pluck('id');
				if($nextPeriodId){
					$activityDetail->next_period_id = $nextPeriodId;
				}

				// 预计第几期福彩
				$lottery_period = LotteryUtil::getNextLotteryPeriod();
				$activityDetail->lottery_period = $lottery_period['date_period'];

				// 状态:即将揭晓
				if($result->pre_luck_code_create_time > $this->nowDateTime){
					$activityDetail->status = 1;
					$activityDetail->left_second = (strtotime($result->pre_luck_code_create_time) - strtotime($this->nowDateTime)) * 100;
				}elseif($result->luck_code==''){ // 仍未获取福彩号
					$activityDetail->status = 3;
				}else{
				//状态: 已揭晓
					$activityDetail->status = 2;
					$activityDetail->luck_code_create_time = $result->luck_code_create_time;
					$activityDetail->pre_luck_code_create_time = $result->pre_luck_code_create_time;

					// 中奖用户
					$rs = DB::select('
						select sum(`times`) as times,`sh_pu`.user_id, `sh_u`.nick_name,`sh_pu`.create_time,`sh_u`.head_pic,sh_pu.ip,sh_pu.ip_address
						from `sh_period_user` as `sh_pu` 
						left join `sh_user` as `sh_u` on `sh_u`.`id` = `sh_pu`.`user_id` 
						where `sh_pu`.`sh_activity_period_id` = ? and `sh_pu`.`user_id` = ?', array($periodId, $result->user_id));
					$activityDetail->luck_user = $rs[0];
					if($activityDetail->luck_user->times){
						$activityDetail->luck_user->luck_code = $result->luck_code;
					}
				}
			}

		}


		// 登陆用户是否参与
		if($loginUserId){
			$codeList = DB::table( 'activity_code' )
				->where('activity_period_id', $periodId)
				->where('user_id', $loginUserId)
				->take(10)
				->select('code_num')
				->lists('code_num');

			$code = array();
			foreach($codeList as $values){
				$code[] = (string)$values;
			}

			$times = DB::table('period_user')
				->where('sh_activity_period_id', $periodId)
				->where('user_id', $loginUserId)
				->sum('times');


			if($codeList){
				$activityDetail->login_user = new \stdClass;
				$activityDetail->login_user->code = $code;
				$activityDetail->login_user->times = $times;
			}
		}

		return $activityDetail;
	}



	/*
	 * 即将揭晓列表
	 */
	public function getResultList($offset, $length, $version='') {
		// 针对ios 审核，不显示iPhone产品
		$whereApple = '';
		$systemM = new SystemModel();
		if( $systemM->checkIOSAudit( Request::header('user-agent'), $version ) ){
			$whereApple = ' AND (`sh_g`.goods_name not like "%apple%" AND `sh_g`.goods_name not like "%mac%" AND `sh_g`.goods_name not like "%ipad%" AND `sh_g`.goods_name not like "%iphone%") ';
			$activityList = DB::table( 'period_result AS '.DB::getTablePrefix().'pr' )
					->leftJoin('activity_period AS '.DB::getTablePrefix().'ap' ,'ap.id','=','pr.sh_activity_period_id')
					->leftJoin('activity AS '.DB::getTablePrefix().'a','ap.sh_activity_id','=','a.id')
					->leftJoin('goods AS '.DB::getTablePrefix().'g','g.id','=','a.sh_goods_id')
					//->where( 'pr.pre_luck_code_create_time', '>', $this->nowDateTime )
					->where( 'g.goods_name', 'not like','%apple%' )
					->where( 'g.goods_name', 'not like','%mac%' )
					->where( 'g.goods_name', 'not like','%ipad%' )
					->where( 'g.goods_name', 'not like','%iphone%' )

					->orderBy('pr.pre_luck_code_create_time','DESC')
					->skip($offset)->take($length)
					->select( 'pr.pre_luck_code_create_time','pr.user_id','pr.luck_code_create_time', 'pr.luck_code',
							  'ap.id AS period_id','ap.current_times','ap.real_need_times','ap.create_time','ap.real_price', 'ap.period_number',
							  'a.id',
							  'g.id AS goods_id','g.goods_name', 'g.goods_img')
					->get();

		}else{
			$activityList = DB::table( 'period_result AS '.DB::getTablePrefix().'pr' )
					->leftJoin('activity_period AS '.DB::getTablePrefix().'ap' ,'ap.id','=','pr.sh_activity_period_id')
					->leftJoin('activity AS '.DB::getTablePrefix().'a','ap.sh_activity_id','=','a.id')
					->leftJoin('goods AS '.DB::getTablePrefix().'g','g.id','=','a.sh_goods_id')
					//->where( 'pr.pre_luck_code_create_time', '>', $this->nowDateTime )
					->orderBy('pr.pre_luck_code_create_time','DESC')
					->skip($offset)->take($length)
					->select( 'pr.pre_luck_code_create_time','pr.user_id','pr.luck_code_create_time', 'pr.luck_code',
							  'ap.id AS period_id','ap.current_times','ap.real_need_times','ap.create_time','ap.real_price', 'ap.period_number',
							  'a.id',
							  'g.id AS goods_id','g.goods_name', 'g.goods_img')
					->get();
		}

		foreach($activityList as $v){
			$v->real_price = intval($v->real_price);
			if($v->pre_luck_code_create_time > $this->nowDateTime){ // 即将揭晓
				$v->left_second = (strtotime($v->pre_luck_code_create_time) - strtotime($this->nowDateTime)) * 100;
			}elseif($v->user_id>0){ // 已开奖
				$userIds[] = $v->user_id;
				$periodIds[] = $v->period_id;
			}

		}


		// 中奖用户
		if(isset($userIds)){
			$strPeriodIds = implode(',', $periodIds);
			$strUserIds = implode(',', $userIds);
			$userList = DB::select('
				select sum(`times`) as times,`sh_pu`.user_id, `sh_u`.nick_name, `sh_pu`.`sh_activity_period_id` as period_id
				from `sh_period_user` as `sh_pu` 
				left join `sh_user` as `sh_u` on `sh_u`.`id` = `sh_pu`.`user_id` 
				where `sh_pu`.`sh_activity_period_id` in ('.$strPeriodIds.') 
				and `sh_pu`.`user_id` in('.$strUserIds.') 
				group by `sh_pu`.`user_id`, `sh_pu`.`sh_activity_period_id`');

			// 合并
			foreach($activityList as $va){
				foreach($userList as $vu){
					if($va->user_id==$vu->user_id && $va->period_id ==$vu->period_id){
						//unset($va->user_id);
						$va->user = $vu;
						continue;
					}
				}
			}
		}

		return $activityList;
	}


	/*
	 * 夺宝详情页-参与记录-用户
	 */
	public function getPeriodUserList($periodId, $offset, $length, $endId=0){
		if(!$endId){ // 兼容旧版新版上线可删除
			//$endId = DB::table( 'period_user AS '.DB::getTablePrefix().'pu' )->orderBy('pu.id','DESC')->pluck('id'); 
			$userList = DB::table( 'period_user AS '.DB::getTablePrefix().'pu' )
				->leftJoin('user AS '.DB::getTablePrefix().'u','u.id','=','pu.user_id')
				->where( 'pu.sh_activity_period_id', $periodId )
				->orderBy('pu.id','DESC')
				->skip($offset)->take($length)
				->select('pu.id','pu.user_id','u.nick_name', 'pu.times', 'pu.create_time', 'u.head_pic','pu.ip','pu.ip_address')
				->get();
		}else{
			$userList = DB::table( 'period_user AS '.DB::getTablePrefix().'pu' )
				->leftJoin('user AS '.DB::getTablePrefix().'u','u.id','=','pu.user_id')
				->where( 'pu.sh_activity_period_id', $periodId )
				->where( 'pu.id', '<',$endId)
				->orderBy('pu.id','DESC')
				->take($length)
				->select('pu.id','pu.user_id','u.nick_name', 'pu.times', 'pu.create_time', 'u.head_pic','pu.ip','pu.ip_address')
				->get();
		}

		return $userList;
	}

	public function getCurrentAndReal($periodId){
		return DB::table('activity_period')->where('id' , $periodId)->select('current_times' , 'real_need_times')->first();
	}

	/*
	 * 参与夺宝
	 */
	public function addPeriodUser($userId, $periodId, $times , $buyId){
		Log::error(" ---- addPeriodUser:\n");
		$period = DB::table( 'activity_period AS '.DB::getTablePrefix().'ap' )
			->where('ap.id',$periodId)
			->select('current_times', 'real_need_times')
			->first();

		Log::error(" period:".print_r($period,1)."\n");
		// 购买数量大于实际剩余数量
		if( ($period->real_need_times - $period->current_times) < $times ){
			Log::error(" 购买数量大于实际剩余数\n");
			return false;
		}

		$periodUserData = array(
			'sh_activity_period_id' => $periodId,
			'create_time'			=> $this->nowDateTime,
			'user_id'				=> $userId,
			'times'					=> $times,
			'buy_id'				=> $buyId
		);
		$newId = DB::table('period_user')->insertGetId($periodUserData);
		Log::error(" newId:".$newId."\n");

		if($newId){ //参与总人次
			$current = DB::table('activity_period')
				->where('id', $periodId)
				->increment('current_times', $times);
				//->update(array('current_times'=> $times));
		}
		return $newId;
	}

	public function getPeriodUserByBuyId($buyId){
		return DB::table('period_user')->where('buy_id' , $buyId)->first();
	}

	/*
	 * 更新夺宝数量
	 */
	public function updatePeriodUser($times , $buyId){
		$period = DB::table( 'activity_period AS '.DB::getTablePrefix().'ap' )
			->where('ap.id',$periodId)
			->select('current_times', 'real_need_times')
			->first();

		// 购买数量大于实际剩余数量
		if( ($period->real_need_times - $period->current_times) < $times ){
			return false;
		}

		$isUpdate = DB::table('period_user')->update('times' , $times);

		if($isUpdate){ //参与总人次
			$current = DB::table('activity_period')
				->where('buy_id', $buyId)
				->increment('current_times', $times);
				//->update(array('current_times'=> $times));
		}else{
			return false;
		}
		$periodUserId = DB::table('period_user')->select('id')->where('buy_id' , $buyId)->first();
		if($periodUserId){
			return false;
		}
		return $periodUserId['id'];
	}

	/*
	 * 查看是否达到开奖条件
	 */
	public function checkPeriodStatus($periodId){
		$status = 0;
		$period = DB::table( 'activity_period AS '.DB::getTablePrefix().'ap' )
			->where('ap.id',$periodId)
			->select('current_times', 'real_need_times', 'flag')
			->first();

		// 人数已满
		if($period->current_times==$period->real_need_times){
			$status = 1;
		// 已下线（产生过下一期了，避免重复执行下一期开启）
		}elseif($period->flag==0){
			$status = 2;
		}
		return $status;
	}

	/*
	 * 新增中奖结果
	 */
	public function addPeriodResult($periodId){
		Log::info('---------- 下线');
		// 本期下线
		$isUp = DB::update('UPDATE sh_activity_period SET flag=0 WHERE id=:id AND flag=1', array('id'=>$periodId));
		if(!$isUp){
			return false;
		}

		$lotteryMethodM = new LotteryMethodModel();

		// 生成运算所需记录
		$lotteryMethodM->codeTime($periodId);
		// 获取a_code
		$a_code = $lotteryMethodM->getCodeTimeFormPeriod($periodId);

		// 获取福彩期号
		$lottery_period = LotteryUtil::getNextLotteryPeriod();
		$pre_luck_code_create_time = strtotime($lottery_period['date_time'])+60*7;
		$pre_luck_code_create_time = date('Y-m-d H:i:s', $pre_luck_code_create_time);

		// 进入中奖表
		$periodResultData = array(
			'sh_activity_period_id'	=>$periodId,
			'user_id'				=> 0,
			'lottery_period'		=> $lottery_period['date_period'],
			'a_code'				=> $a_code,
			'a_code_create_time'	=> $this->nowDateTime,
			'pre_luck_code_create_time'	=> $pre_luck_code_create_time
		);
		Log::info('---------- 新增中奖结果');
		$newId = DB::table('period_result')->insertGetId($periodResultData);
		return $newId;
	}

	/*
	 * 开始下一期
	 */
	public function nextPeriod($periodId){
		$lotteryMethodM = new LotteryMethodModel();

		Log::info('---------- 活动信息');
		// 活动信息
		$activityDetail = DB::table( 'activity_period AS '.DB::getTablePrefix().'ap' )
				->leftJoin('activity AS '.DB::getTablePrefix().'a','ap.sh_activity_id','=','a.id')
				->where( 'ap.id', $periodId )
				->where( 'a.is_online', 1 )
				->select('a.id','a.need_times','a.current_period', 'a.max_period', 'a.price')
				->first();
		Log::info('---------- '. print_r($activityDetail,1));
		// 已下线
		if(!$activityDetail || empty($activityDetail)){
			return false;
		}

		// 如果已最后一期, 则不再生成下一期
		if($activityDetail->current_period >= $activityDetail->max_period){
			return false;
		}

		Log::info('---------- 开启新一期');
		// 开启新一期
		$new_current_period = $activityDetail->current_period + 1;
		$activityPeriodData = array('period_number'=>$new_current_period, 'sh_activity_id'=>$activityDetail->id, 'create_time'=>$this->nowDateTime,'current_times'=>0, 'real_need_times'=>$activityDetail->need_times, 'real_price'=>$activityDetail->price, 'flag'=>1);
		$newActivityPeriodId = DB::table('activity_period')->insertGetId($activityPeriodData);

		// 更新活动当前期
		DB::table('activity')
			->where('id', $activityDetail->id)
			->increment('current_period');

		Log::info('---------- 生成下一期夺宝号');
		// 生成下一期夺宝号
		$lotteryMethodM->createCodeNum($newActivityPeriodId);
		return $newActivityPeriodId;

	}

	/*
	 * 用户购买的号码
	 */
	public function getUserCodeList($periodId, $userId , $offset, $length){
		$codes = DB::table('activity_code')
				->where('activity_period_id', $periodId)
				->where('user_id', $userId)
				->select('code_num')
				->skip($offset)->take($length)
				->lists('code_num');

		return $codes;
	}

    /*
     *根据用户购买ID获取购买的号码
     */
    public function getUserCodeListByBuyId($buyId , $offset, $length){
		$codes = DB::table('activity_code')
                ->where('buy_id', $buyId)
				->select('code_num')
				->skip($offset)->take($length)
				->lists('code_num');

		return $codes;
	}

	/*
	 * 通过buy_id获取用户本期活动参与的次数
	 */
	public function getUserCodeNumBybuyId($activityPeriodId , $userId , $buyId){
		return DB::table('activity_code')
					->where('activity_period_id' , $activityPeriodId)
					->where('user_id' , $userId)
					->where('buy_id' , $buyId)
					->count();
	}
	/*
	 * 获取用户本期活动参与的次数
	 */
	public function getUserCodeNum($activityPeriodId , $userId){
		return DB::table('activity_code')
					->where('activity_period_id' , $activityPeriodId)
					->where('user_id' , $userId)
					->count();
	}

	/*
	 *
	 */
	public function getRate($current_times, $real_need_times){
		$rate = $real_need_times >0 ? $current_times/$real_need_times * 100 : 0;
		$rate = ceil($rate);
		if($rate==100 && $current_times<$real_need_times){
			$rate = 99;
		}
		return $rate;
	}

	/*
	 * 开奖等待时间
	 */
	public function getWaitTime(){
		// 间隔
		$hi = date('Hi');
		$hi = (int)$hi;
		if($hi>=1000 && $hi<2200){ // 10:00 ~ 22:00
			$min = 10 + 7;
			$preHi = floor($hi/10)*10 + $min ;
		}elseif( ($hi>=2200 && $hi<=2359) || ($hi>=0 && $hi<155) ){ //22:00 ~ 01:55
			$min = 5 + 7;
			$preHi = floor($hi/10)*10 + $min;
		}else{  // 01:55 ~ 10:00
			$preHi = 1000 + 7;
		}


		$preHi = (string)$preHi;
		if(strlen($preHi)<3){
			$preHi = '00'.$preHi;
		}
		if(strlen($preHi)<4){
			$preHi = '0'.$preHi;
		}
		$preDatetime = date("Y-m-d").' '.$preHi[0].$preHi[1]. ':' . $preHi[2].$preHi[3].':40';
		return $preDatetime;


		/*
		// 获取上一期的福彩号
		$open_time = DB::table('lottery_shishi')->orderBy('open_time', 'DESC')->pluck('open_time');
		$hi = date('Hi');
		if($hi>=1000 && $hi<2200){ // 10:00 ~ 22:00
			$time = 60*10;
		}elseif( ($hi>=2200 && $hi<=2359) || ($hi>=0 && $hi<155) ){ //22:00 ~ 01:55
			$time = 60*5;
		}else{  // 01:55 ~ 10:00
			$time = 60*60*8 + 60*5;
		}
		$time += 60*7;
		$open_time = date("Y-m-d H:i:s", strtotime($open_time) + $time + 60*2);
		return $open_time;
		 */
	}

	/*
	 * 获取活动真实价格
	 */
	public function getActivityRealPrice($activityPeriodId){
		$row = DB::table('activity_period')
			->where('id' , $activityPeriodId)
			->where('flag' , 1)
			->select('id' , 'real_price')
			->first();

		$row->real_price = intval($row->real_price);

		return $row;
	}


	/*
	 * 生成测试数据
	 */
	public function createTestData(){
		set_time_limit(300);
		$activityNum = 0;
		$max_period = 10;

		$goodsList = DB::table('goods')->take($activityNum)->select('id','goods_name', 'market_price')->get();
		DB::table('activity')->truncate();
		DB::table('activity_period')->truncate();
		DB::table('activity_code')->truncate();
		DB::table('activity_code_record')->truncate();
		DB::table('period_result')->truncate();
		DB::table('period_user')->truncate();
		//DB::table('lottery_shishi')->truncate();
		DB::table('order_info')->truncate();
		DB::table('order_action')->truncate();
		DB::table('shaidan_img')->truncate();
		DB::table('user_shaidan')->truncate();
		DB::table('alipay')->truncate();
		DB::table('alipay_period')->truncate();

		for($i=0;$i<$activityNum;$i++){
			// new activity
			$need_times = ceil($goodsList[$i]->market_price);
			$activityData = array('creat_time'=>$this->nowDateTime, 'sh_goods_id'=>$goodsList[$i]->id, 'current_period'=>1, 'max_period'=>$max_period, 'price'=>1,'begin_date'=>$this->nowDateTime, 'end_date'=>'2016-10-10 00:00:00', 'need_times'=>$need_times, 'user_id'=>1,'update_time'=>$this->nowDateTime,'is_online'=>1);
			$newActivityId = DB::table('activity')->insertGetId($activityData);

			// new activity_period
			$activityPeriodData = array('period_number'=>1, 'sh_activity_id'=>$newActivityId, 'create_time'=>$this->nowDateTime,'current_times'=>0, 'real_need_times'=>$need_times, 'real_price'=>1, 'flag'=>1);
			$newActivityPeriodId =DB::table('activity_period')->insertGetId($activityPeriodData);;

			//new activity_code
			$lotteryMethodM = new LotteryMethodModel();
			$lotteryMethodM->createCodeNum($newActivityPeriodId);
		}

 		//@file_get_contents('http://localhost.api.duobaohui.com/activity/lottery/lotterycode?lotterycode=cqssc&recordcnt=20');
		//@file_get_contents('http://api.duobaohui.com/activity/lottery/lotterycode?lotterycode=cqssc&recordcnt=20');

	}

	/*
	 * 夺宝购买事务
	 */
	public function buyTransaction($userId, $periodId, $num , $buyId, $money){
		DB::beginTransaction();
			Log::error("---buyTransaction start-----:\n");
			Log::error("user_id:$userId\n");
			Log::error("period_id:$periodId\n");
			Log::error("num:$num\n");
			Log::error("buy_id:$buyId\n");
			Log::error("money:$money\n");

			// 新增夺宝记录
			$newPeriodUserId = $this->addPeriodUser($userId, $periodId, $num , $buyId);
			Log::error("newPeriodUserId:$newPeriodUserId\n");
            //file_put_contents('/home/log/php/duobaohui/log', "新增夺宝记录 \n",FILE_APPEND);
            //file_put_contents('/home/log/php/duobaohui/log', print_r($newPeriodUserId,1),FILE_APPEND);

			if(!$newPeriodUserId){
				DB::rollback();
				return false;
			}
			// 购买的夺宝号
			$lotteryMethodM = new LotteryMethodModel();
			$isUp = $lotteryMethodM->getRandomCode($periodId, $userId, $buyId , $num, $newPeriodUserId );
			Log::error("getRandomCode:".print_r($isUp,1)."\n");
			if(!$isUp){
				DB::rollback();
				return false;
			}
			//file_put_contents('/home/log/php/duobaohui/log', "夺宝号购买成功\n",FILE_APPEND);

			//更新用户余额
			$recharge = new RechargeModel();
			$isUpdateUserMoney = $recharge->updateUserMoney($money , $userId);

			Log::error("updateUserMoney:".print_r($isUpdateUserMoney,1)."\n");
			if(!$isUpdateUserMoney){
				//file_put_contents('/home/log/php/duobaohui/log', "更新用户余额失败\n",FILE_APPEND);
				DB::rollback();
				return false;
			}
			$lotteryMethodM = new LotteryMethodModel();
			$lotteryMethodM->setBuyOrderStatus($userId, $periodId, 8);
			Log::info('---------- 设置订单状体结束');
			Log::error("------buyTransaction end ----\n");
		DB::commit();
		return true;
	}

	/*
	 * 开奖事务
	 */
	public function resultTransaction($periodId,$userId){
		DB::beginTransaction();
		Log::info('<------------------------ 开始开奖事务');
			// 进入开奖流程
			$isAdd = $this->addPeriodResult($periodId);
		Log::info('---------- 开奖结束');
			if(!$isAdd){
				DB::rollback();
				return false;
			}
			// 同时进行下一期
			$isUp = $this->nextPeriod($periodId);
			Log::info('---------- 生成下一期结束');

		DB::commit();
		return true;
	}

	/*
	 * 根据ID获取期信息
	 */
	public function findPeriodById($period_id) {
		return DB::table('activity_period')
			->where('id',$period_id)
			->first();
	}

	/*
	 * 根据活动ID查询活动详细信息
	 */
	public function getActivityById($activity_id){
		$list = DB::table('activity as a')->select(DB::raw("
			a.id,
			a.current_period,
			a.sh_goods_id,
			b.goods_name,
			a.max_period,
			a.price,
			DATE_FORMAT(a.creat_time, '%Y-%m-%d %T') create_time,
			DATE_FORMAT(a.begin_date, '%Y-%m-%d') begin_date,
			DATE_FORMAT(a.end_date, '%Y-%m-%d') end_date,
			DATE_FORMAT(a.update_time, '%Y-%m-%d %T') update_time,
			a.need_times,
			a.is_online,
			a.is_auto,
			a.is_hot,
			a.user_id,
			c.nickname
		"))->leftJoin('goods as b',DB::raw('b.id'),'=',DB::raw('a.sh_goods_id'))
			->leftJoin('admin_user as c',DB::raw('c.id'),'=',DB::raw('a.user_id'))
			->where(DB::raw('a.id'),'=',$activity_id)
			->first();
		return $list;
	}

	/*
	 * 根据活动ID查询当前最新的“期”的信息
	 */
	public function getLatestPeriodByActivityId($activity_id){

		$result = DB::table('activity_period')
			->select('id',
				'period_number',
				'sh_activity_id',
				'create_time',
				'current_times',
				'real_need_times',
				'real_price',
				'flag')
			->where('sh_activity_id','=',$activity_id)
			->latest('create_time')
			->first();

		$result->real_price = intval($result->real_price);
		return $result;

	}
	public function addActivityPeriod($data) {
		return DB::table('activity_period')->insertGetId($data);
	}
	public function updateActivity($id,$data) {
		return DB::table('activity')->where('id', $id)->update($data);
	}

	/*
	 * 根据活动ID查询是否有未下线（is_online＝1）且没有生成新的一期的活动
	 */
	public function getOnlineActivityList($flag){
		$list = DB::table( 'activity AS '.DB::getTablePrefix().'a' )
			->leftjoin('activity_period AS '.DB::getTablePrefix().'ap', 'ap.sh_activity_id','=',
				DB::raw('sh_a.id AND sh_ap.flag = ' . $flag))
//			->leftJoin('activity_period AS '.DB::getTablePrefix().'ap' , function($join)
//			{
//				$join->on('ap.sh_activity_id', '=', 'a.id')
//					->on('ap.flag', '=', DB::raw(1));
//			})
			->where( 'a.is_online', '=' , '1')
			->select(
				'a.id as activity_id',
				'ap.id as sh_activity_period_id',
				'ap.flag',
				'ap.period_number',
				'a.current_period',
				'a.need_times',
				'a.price',
				'a.max_period')
			->get();
		return $list;
	}

	/**
	 *
	 */
	public function updateActivityPeriodFlag($sh_activity_id){
		$update_res = DB::table('activity_period AS '.DB::getTablePrefix().'ap' )
			->where( 'ap.sh_activity_id', '=' ,  $sh_activity_id)
			->where( 'ap.flag', '=' ,  2)
			->update(array(
				'flag' => 1
			));
		return $update_res;
	}
	public function getActivityByPeriodId($period_id){
		$result = DB::table( 'activity_period AS '.DB::getTablePrefix().'ap' )
			->where( 'ap.id', '=' , $period_id)
			->select('ap.sh_activity_id')
			->first();
		return $result;
	}

	/*
	 * 奖品分类
	 */
	public function getCategoryList(){
		$list = DB::table('category')->select('id','cat_name','img_url', 'goods_num')->where('level',1)->where('goods_num','>', 0)->get();
		return $list;
	}

	private function whereInSql($paramData){
		$strSql = '';
		$param = array();
		foreach($paramData as $kp=>$vp){
			if( is_array($vp) ){  // in
				$arrInWhere = array();
				foreach($vp as $kd=>$vd){
					$ki = $kp . '_' . $kd;
					$arrInWhere[] = ':'. $ki;
					$param[$ki] = $vd;
				}
				$strSql = implode(',', $arrInWhere);
			}else{
				$param[$kp] = $vp;
				$strSql = ':'.$kp;
			}
		}

		return array('sql'=>$strSql, 'param'=>$param);
	}

	public function saveSearchWord($keyword){
		$row = DB::table('searchword')->where('word', $keyword)->first();
		if($row){
			DB::table('searchword')->where('word',$keyword)->increment('num');
		}else{
			DB::table('searchword')->insert(array('word'=>$keyword, 'num'=>0));
		}
	}

}

