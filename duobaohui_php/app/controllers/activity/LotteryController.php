<?php
/**
 * 抽奖号码
 * @author		wangkenan@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Activity;			// 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session 
use Illuminate\Support\Facades\Response;	//引入response

use Laravel\Model\LotteryModel;			//引入model
//use Laravel\Model\ActivityModel;			//引入model
use Laravel\Model\LotteryMethodModel;	
use Laravel\Model\SpiderModel;					
use App\Libraries\OrderUtil;//引入订单工具类
use Illuminate\Support\Facades\Log;//引入日志类
use App\Libraries\Sms;						//引入发送短信接口
use App\Libraries\LotteryUtil;//引入彩票工具类


/**
 * 	controller的写法：首字母大写，于文件名一致。 继承的父类需引入
 */ 
class LotteryController extends ApiController {
	public function __construct(){
		parent::__construct();
	}

	//private $public_lottery_num = 10000001;

	


	public function anyCreatecodenum(){
		$activity_period_id = Input::get('activity_period_id');
		$lottery = new LotteryMethodModel();
		$lottery -> CreateCodeNum($activity_period_id);
	}

	//获取中奖结果
	public function anyLottery(){
		$lottery = new LotteryMethodModel();
		$spider = new SpiderModel();
		//$test = $spider -> spider_winRecord();
		$code = $lottery -> convertDate(microtime());

		//$this -> spider_wangyi();
	}

	/**
	 * 方法，通过“彩票控”api获取时时彩信息
	 */
	public function lotteryCodeCPK(){
	    if( ! Input::has('lotterycode') ){
	        return Response::json( $this->response( '10005' ) );
	    }
	    if( ! Input::has('recordcnt') ){
	        return Response::json( $this->response( '10005' ) );
	    }
	    $lotterycode = Input::get('lotterycode');//时时彩类型，如：cqssc（重庆时时彩）、hljssc（黑龙江时时彩）
	    $recordcnt = Input::get('recordcnt');//查询纪录数
	    $uid = '144427';
	    $token = '9f2cc4ed8a1ba86d299964cc264c5fbd68034f4b';
	    $format = 'json';
	    $url = 'http://api.caipiaokong.com/lottery/?name='.$lotterycode.
	        '&format='.$format.
	        '&uid='.$uid.
	        '&token='.$token.
	        '&num='.$recordcnt;
	    $data = file_get_contents($url);
	    $array = json_decode($data,true);
	    $lottery = new LotteryModel();
	    $count = 0;
	    if(is_array($array) && count($array)==$recordcnt) {
            Log::info('《彩票控》返回：成功！');
	        foreach($array as $key => $value) {
	            try {
    	            $val['expect'] = $key;
    	            $val['open_code'] = str_replace(',','',$array[$key]['number']);
    	            $val['lottery_code'] = $lotterycode;
    	            $val['source'] = '2';
    	            $val['open_time'] = explode(" ",$array[$key]['dateline'])[0].' '.LotteryUtil::$lottery_period_relation[substr($key,8)];
    	            $val['real_open_time'] = $array[$key]['dateline'];
    	            $val['open_time_stamp'] = strtotime($val['open_time']) ;
    	            $val['create_time'] = date('Y-m-d H:i:s',time());
    	            $lottery->insertLotteryCode($val);//插入到时时彩表
                    var_dump($val);
	            }catch (\Exception $e){
	                $count++;
	                echo($e->getMessage());
	            }
	        }
	        return true;
	    }else if(is_array($array) 
	        && array_key_exists('status', $array) 
	        && array_key_exists('code', $array['status']) 
	        && array_key_exists('text', $array['status'])){
	        Log::error('《彩票控》返回：失败。。'.$array['status']['code'].','.$array['status']['text']);
	        return false;
	    }else{
	        Log::error('《彩票控》返回：失败。。');
	        return false;
	    }
	}
	
	/**
	 * 方法，通过“百度”api获取时时彩信息
	 */
    public function lotteryCodeBD(){
        if( ! Input::has('lotterycode') ){
            return Response::json( $this->response( '10005' ) );
        }
        if( ! Input::has('recordcnt') ){
            return Response::json( $this->response( '10005' ) );
        }
	    $lotterycode = Input::get('lotterycode');//时时彩类型，如：cqssc（重庆时时彩）、hljssc（黑龙江时时彩）
	    $recordcnt = Input::get('recordcnt');//查询纪录数
	    $ch = curl_init();
	    // $url = 'http://apis.baidu.com/apistore/lottery/lotterylist?lotterytype=2';
	    $url = 'http://apis.baidu.com/apistore/lottery/lotteryquery?lotterycode='.$lotterycode.'&recordcnt='.$recordcnt;
	    $header = array(
	        'apikey: 4dcc326047ff1e31e2e07e0393c2073f',
	    );
	    curl_setopt($ch, CURLOPT_HTTPHEADER  , $header);
	    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	    curl_setopt($ch, CURLOPT_URL , $url);
	    $res = curl_exec($ch);
	    $res = json_decode($res);
	    $lottery = new LotteryModel();
	    $count = 0;
	    
	    $errNum = $res -> errNum;
	    if(strcmp($errNum,'0') != 0){
	        Log::error('《百度》返回：失败。。'.$res -> retMsg);
// 	        return Response::json($this->response( $errNum, $res -> retMsg));
            return false;
	    }else{
    	    foreach($res -> retData -> data as $value){
               try {
            	    $val['expect'] = $value -> expect;
            	    $val['open_code'] = str_replace(',','',$value -> openCode);
            	    $val['lottery_code'] = $lotterycode;
    	            $val['source'] = '1';
            	    $val['open_time'] = $value -> openTime;
    	            $val['real_open_time'] = $value -> openTime;
            	    $val['open_time_stamp'] = $value -> openTimeStamp;
            	    $val['create_time'] = date('Y-m-d H:i:s',time());
        	        $lottery->insertLotteryCode($val);//插入到时时彩表
        	    }catch (\Exception $e){
        	        $count++;
                    echo($e->getMessage());
        	    }
    	    }
//     	    return Response::json($this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS));
    	    Log::info('《百度》返回：成功！');
            return true;
	    }
	    
	}
	/**
	 * 脚本，通过“彩票控”api获取时时彩信息，如果“彩票控”获取失败调用“百度”api获取
	 */
    public function anyLotterycode(){
        if(!$this->lotteryCodeCPK()){//彩票控
            Log::error('《彩票控》返回失败，重新调用《百度》api');
            if(!$this->lotteryCodeBD()){//百度
                return Response::json($this->response( ApiController::FAILDCODE, ApiController::FAILD));
            }else{
                return Response::json($this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS));
            }
        }else{
            return Response::json($this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS));
        }
	}
	
	/**
	 * 查询已经生成a_code且没有时时彩开奖号及幸运号的纪录
	 */
	public function queryPeriodResult(){
	    $lottery = new LotteryModel();
	    $list = $lottery->queryPeriodResult();//插入到时时彩表
	    return $list;
	}
	
	/**
	 * 开奖！填充时时彩开奖号，同时生成幸运号，生成订单
	 */
	public function anyCreateluckcode(){
	    $lottery = new LotteryModel();
	    $lotteryMethod = new LotteryMethodModel();
	    $list = $this->queryPeriodResult();
	    $count = 0;
	    //循环开奖
	    foreach($list as $value){
    	    $sh_activity_period_id = $value -> sh_activity_period_id;
    	    $sh_period_result_id = $value -> id;
    	    $a_code = $value -> a_code;
    	    $real_need_times = $value -> real_need_times;
    	    $lottery_period = $value -> lottery_period;
	        
	        //通过活动期数、总需人次、福彩开奖期数计算a_code、获取福彩开奖号、计算余数、计算幸运号
    	    $data = $lotteryMethod->getLuckCode($sh_activity_period_id , $real_need_times , $lottery_period);
            if($data === null){
                continue;
            }
    	    $lottery_code = str_replace(',','',$data['code_b']);
    	    $remainder = $data['remainder'];
    	    $proto_code = $data['proto_code'];
    	    $luck_code = $data['luck_code'];
    	    
    	    //通过活动期数、幸运号查询获奖人user_id
    	    $winningRes = $lottery->queryWinningUserId($sh_activity_period_id , $data['luck_code']);
    	    $user_id = $winningRes[0] -> user_id;
    	    $is_real = $winningRes[0] -> is_real;
    	    $tel = $winningRes[0] -> tel;
    	    $nick_name = $winningRes[0] -> nick_name;
    	    
    	    $order_utile  = new OrderUtil();
    	    $order_sn = $order_utile->createOrdersn();//生成订单号

    	    //开奖结果表数据准备
    	    $period_result_data = array(
                'sh_period_result_id' => $sh_period_result_id,    	        
                'user_id' => $user_id,    	        
                'lottery_code' => $lottery_code,    	        
                'remainder' => $remainder,    	        
                'proto_code' => $proto_code,    	        
                'luck_code' => $luck_code,
				'tel' => $tel,
				'nick_name' => $nick_name,
				'is_real' => $is_real
    	    ); 
    	    
    	    //订单信息表数据准备
    	    $order_data = array(
                'order_sn' => $order_sn,    	        
                'user_id' => $user_id,    	        
                'order_status' => '0',    	        
                'shipping_status' => '0',   	        
                'add_time' => date('Y-m-d H:i:sa', time()),       
                'sh_period_result_id' => $sh_period_result_id    	        
    	    ); 
    	    
    	    //更新到中奖表中，同时生成订单
	        $result = $lottery->updatePeriodResultAndCreateOrder($period_result_data , $order_data );
	        $lottery->insertPushItem($period_result_data , $order_data );
	        //发中奖短信
// 	        if($result && $is_real == 1 && $tel != null){
// 	            try {
//     	            $sms_result = Sms::sendShortMessage( $tel , '亲爱的'.$nick_name.'，恭喜您在夺宝会成功获得奖品！请及时在夺宝会客户端确认收货信息。' );
//     	            Log::info("中奖短信发送结果：".$sms_result['msg'].",user_id:".$user_id.",tel:".$tel.",nick_name:".$nick_name);
// 	            }catch(Exception $e) {
//         	        Log::error($e);
//         	        Log::error("中奖短信发送结果：失败");
//                 }
// 	        }
	        //输出开奖list
	        $temp = array(
	            'sh_activity_period_id' => $sh_activity_period_id,
	            'a_code' => $a_code,
	            'lottery_period' => $lottery_period,
	            'lottery_code' => $lottery_code,
	            'remainder' => $remainder,
	            'luck_code' => $luck_code,
	            'user_id' => $user_id
	        );
	        $list_res[] = $temp;
	        $count ++;
	    }
	    $list_res['count'] = $count;
    	return $list_res;
	}
	

}
