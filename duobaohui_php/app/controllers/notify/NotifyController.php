<?php

/**
 * 消息推送
 * @author		guoshijie@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */
namespace Laravel\Controller\notify;

use ApiController;
use App\Libraries\Sms;
use Illuminate\Support\Facades\App;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Response;
use Laravel\Model\NotifyModel;
use Laravel\Service\PushService;

class NotifyController extends ApiController {

    protected $timestamp        = NULL;
    protected $validation_token = NULL;
    protected $pushService;

    public function __construct(){
        $this->timestamp = strval(time());
        $this->pushService = new PushService();
        parent::__construct();
    }

    /**
     * 客户端中奖消息推送
     */
    public function anySendAndroidAppNotify() {
        $notifyM = new NotifyModel();
        $winnerList = $notifyM->getAppNotifyList();
        if($winnerList){
            foreach($winnerList as $item){
                try{
                    $os_type = $item->os_type;
                    $data = $this->getPushArray($item);
                    if(substr($os_type,0,1) == '1'){
                        $res_str = $this->pushService->sendAndroidCustomizedcast($data);
                    }
                    if(substr($os_type,1,1) == '1') {
                        $res_str = $this->pushService->sendIOSCustomizedcast($data);
                    }
                    $notifyM->updateAppNotify($this->getResArray($res_str,$data));
                }catch(Exception $e) {
                    Log::error($e);
                }
            }
            return Response::json($this->response( 1 ));
        }
        return Response::json($this->response( 20302 ));
    }

    public function getResArray($res_str,$data){
        $res = json_decode($res_str);
        if($res->ret == 'SUCCESS') {
            $data['send_flag'] = 2;
            $data['error_code'] = $res->ret;
        }else{
            $data['send_flag'] = 3;
            $data['error_code'] = $res->data->error_code;
        }
        $data['error_msg'] = $res_str;
        return $data;
    }

    public function getPushArray($item){
        $data = array(
            'alias_type' => 'userId',
            'ticker' => '天啦噜！中奖了！人品侧漏啦！',
            'title' => '天啦噜！中奖了！人品侧漏啦！',
            'after_open' => 'go_app',
            'type' => 'Customizedcast',
        );
        $data['id'] = $item->id;
        $data['alias'] = $item->user_id;
        $data['period_number'] = $item->period_number;
        $data['goods_name'] = $item->goods_name;
        $data['msg_type'] = $item->msg_type;
        $data['text'] = '(第'.$item->period_number.'期)'.$item->goods_name.',属于您了';
        return $data;
    }

    /**
     * 发送中奖短信通知
     */
    public function anySendSmsNotify() {
        $notifyM = new NotifyModel();
        $smsList = $notifyM->getWinnerSmsList();
        $result = array();
        if($smsList) {
            $count = 0;
            foreach($smsList as $item){
                $sms_result = $this->sendSmsAli($item->tel,$item->nick_name);
                $result[] = "发送时间:".$this->time.",发送结果：".$sms_result['msg'].",tel:".$item->tel.",content:".$item->content;
                $data = array();
                $data['id'] = $item->id;
                if($sms_result){
                    $data['send_flag'] = 2;
                } else {
                    $data['send_flag'] = 3;
                }
                $res = $notifyM->updateSmsNotify($data);
                $count = $count + $res;
            }
            if($count > 0){
                return Response::json($this->response( ApiController::SUCCESSCODE, ApiController::SUCCESS,$result));
            }
        }
        return Response::json($this->response( 20302 ));
    }

    /**
     * 发送中奖短信
     * @param $tel
     * @param $content
     * @return array
     */
    public function sendSms($tel,$content) {
        try {
            $sms_result = Sms::sendShortMessage( $tel, $content );
            Log::info("中奖短信发送结果：".$sms_result['msg']."tel:".$tel.",content:".$content);
            return $sms_result;
        }catch(Exception $e) {
            Log::error($e);
            Log::error("中奖短信发送结果：失败");
        }
    }

    /**
     * @param $tel
     * @param $username
     * @return bool
     */
    public function sendSmsAli($tel,$username) {
        try {
            $sms_result = Sms::sendWin($tel,$username);
            Log::info("中奖短信发送结果：".$sms_result.",tel:".$tel.",用户名:".$username);
            return $sms_result;
        }catch(Exception $e) {
            Log::error($e);
            Log::error("中奖短信发送结果：失败");
        }
    }



}