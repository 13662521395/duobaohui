<?php
/**
 * 消息推送管理
 * User: guoshijie
 * Date: 15/10/29
 * Time: 下午3:43
 */
namespace Laravel\Controller\Admin;

use AdminController;
use AndroidCustomizedcast;
use AndroidBroadcast;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\View;
use Laravel\Model\NotifyAdminModel;
use Illuminate\Support\Facades\App;

class NotifyController extends AdminController {
    private $notifyM;
    private $_length;
    private $appkey;
    private $appMasterSecret;
    private $timestamp;
    private $production_mode = NULL;

    public function __construct() {
        $this->appkey = '560949fbe0f55a9111003080';
        $this->appMasterSecret = 'hgxtrl2wrndyuoukgbx1ro2j7qc0kihf';
        $this->timestamp = strval(time());
        $this->notifyM = new NotifyAdminModel();
        $this->_length = 10;
        $this->production_mode = App::environment() == 'local'?false:true;
        parent::__construct();
    }

    /**
     * 后台消息推送
     */
    public function postClientNotifyPush(){
        try{
            $notifyM = new NotifyAdminModel();
            $data = array(
                'ticker' => Input::get('notify_ticker'),
                'title' => Input::get('notify_title'),
                'text' => Input::get('notify_content'),
                'alias_type' => Input::get('alias_type'),
                'alias' => Input::get('notify_alias'),
                'user_id' => Input::get('notify_alias'),
                'after_open' => 'go_app',
                'type' => Input::get('push_type'),
                'display_type'=>'notification',
                'msg_type'=>'normal',
                'adddate'=>date('Y-m-d H:i:s',time()),
                'send_flag'=>'0'
            );
            Log::info($data);
            if($data['type']=='Customizedcast'){
                $is_real = $notifyM->getUserRealFlagById($data['user_id']);
                if($is_real){
                    $data['is_real'] = $is_real[0]->is_real;
                    $res = $notifyM->addPushNotify($data);
                    $data['msg'] = $res?"推送成功":"推送失败，请稍候再试";
                }else{
                    $data['msg'] = "userId不存在,请重试";
                }
            } else {
                $data['is_real'] = 1;
                $res = $notifyM->addPushNotify($data);
                $data['msg'] = $res?"推送成功":"推送失败，请稍候再试";
            }
            $data['selected']	= "notify";
            $data = array_merge($data, $this->viewData);
            return Response::view('admin.notify.clientNotifyPushPre',$data);
        }catch(Exception $e) {
            Log::error($e);
        }
    }

    /**
     * 后台推送普通消息
     */
    public function anySendAppNotify(){
        $notifyM = new NotifyAdminModel();
        $list = $notifyM->getAppNotifyList();
        Log::info($list);
        if($list){
            foreach($list as $item){
                try{
                    $data['id'] = $item->id;
                    $data['type'] = $item->type;
                    $data['msg_type'] = $item->msg_type;
                    $data['ticker'] = $item->ticker;
                    $data['title'] = $item->title;
                    $data['text'] = $item->text;
                    $data['alias'] = $item->user_id;
                    $data['alias_type'] = $item->alias_type;
                    $data['after_open'] = $item->after_open;
                    $data['display_type'] = $item->display_type;

                    if($item->type == 'Broadcast') {
                        $res_pre = $this->sendAndroidBroadcast($data);
                        $res = json_decode($res_pre);
                        if($res->ret == 'SUCCESS') {
                            $data['send_flag'] = 2;
                            $data['error_code'] = $res->ret;
                        } else {
                            $data['send_flag'] = 3;
                            $data['error_code'] = $res->data->error_code;
                        }
                        $data['error_msg'] = $res_pre;
                    } else {
                        $res_pre = $this->anySendAndroidCustomizedcast($data);
                        $res = json_decode($res_pre);
                        if($res->ret == 'SUCCESS') {
                            $data['send_flag'] = 2;
                            $data['error_code'] = $res->ret;
                        } else {
                            $data['send_flag'] = 3;
                            $data['error_code'] = $res->data->error_code;
                        }
                        $data['error_msg'] = $res_pre;
                    }
                    Log::info($data);
                    $notifyM->updateAppNotify($data);
                }catch(Exception $e) {
                    Log::error($e);
                    return Response::json($this->response( 0 ));
                }
            }
            return Response::json($this->response( 1 ));
        }
        return Response::json($this->response( 20302 ));
    }

    public function getClientNotifyPushPre(){
        $data = array();
        $data['selected']	= "notify";
        $data['msg']		= "";
        $data = array_merge($data, $this->viewData);
        return Response::view('admin.notify.clientNotifyPushPre',$data);
    }

    /**
     * 客户端中奖消息推送列表
     * @return \Illuminate\Http\Response
     */
    public function getClientWinNotifyList() {
        $data = array();
        $data['selected']	= "notify";
        $data['msg']		= "";

        $app_status = Input::get('app_status');
        $sms_status = Input::get('sms_status');
        $luck_code_create_time = Input::get('luck_code_create_time');

        $list = $this->notifyM->getWinNotifyList($this->_length,$app_status,$sms_status,$luck_code_create_time);
        $list->appends(Input::all());
        $data['list'] = $list;
        $data['app_status'] = $app_status?$app_status:-1;
        $data['sms_status'] = $sms_status?$sms_status:-1;
        $data['begin_date'] = $luck_code_create_time?$luck_code_create_time:date('Y-m-d',time());
        $data = array_merge($data, $this->viewData);
        return Response::view('admin.notify.winNotifyList',$data);
    }


    /**
     * 自定义播(customizedcast)
     * 开发者通过自有的alias进行推送, 可以针对单个或者一批alias进行推送，也可以将alias存放到文件进行发送
     * @param $data
     * @return mixed
     * @throws \Exception
     * @internal param $alias
     * @internal param $alias_type
     */
    public function anySendAndroidCustomizedcast($data) {
        try {
            $customizedcast = new AndroidCustomizedcast();
            $customizedcast->setAppMasterSecret($this->appMasterSecret);
            $customizedcast->setPredefinedKeyValue("appkey",           $this->appkey);
            $customizedcast->setPredefinedKeyValue("timestamp",        $this->timestamp);

            $customizedcast->setPredefinedKeyValue("alias",            $data['alias']);
            $customizedcast->setPredefinedKeyValue("alias_type",       $data['alias_type']);

            $customizedcast->setPredefinedKeyValue("ticker",           $data['ticker']);
            $customizedcast->setPredefinedKeyValue("title",            $data['title']);
            $customizedcast->setPredefinedKeyValue("text",             $data['text']);
            $customizedcast->setPredefinedKeyValue("after_open",       $data['after_open']);

//            print("Sending customizedcast notification, please wait...\r\n");
            $customizedcast->setPredefinedKeyValue("production_mode", $this->production_mode);
            $customizedcast->setExtraField("display_type",$data['display_type']);
            $customizedcast->setExtraField("msg_type",$data['msg_type']);
            $result = $customizedcast->send();
//            print("Sent SUCCESS\r\n");
            return $result;
        } catch (Exception $e) {
            Log::error("Caught exception: " . $e->getMessage());
        }
    }

    /**
     * 广播(broadcast) 向安装该App的所有设备发送消息
     */
    function sendAndroidBroadcast($data) {
        try {
            $brocast = new AndroidBroadcast();
            $brocast->setAppMasterSecret($this->appMasterSecret);
            $brocast->setPredefinedKeyValue("appkey",           $this->appkey);
            $brocast->setPredefinedKeyValue("timestamp",        $this->timestamp);
            $brocast->setPredefinedKeyValue("ticker",           $data['ticker']);
            $brocast->setPredefinedKeyValue("title",            $data['title']);
            $brocast->setPredefinedKeyValue("text",             $data['text']);
            $brocast->setPredefinedKeyValue("after_open",       $data['after_open']);
            // Set 'production_mode' to 'false' if it's a test device.
            // For how to register a test device, please see the developer doc.
//            $brocast->setPredefinedKeyValue("production_mode", "true");
            $brocast->setPredefinedKeyValue("production_mode", $this->production_mode);
            // [optional]Set extra fields
            $brocast->setExtraField("display_type",$data['display_type']);
            $brocast->setExtraField("msg_type",$data['msg_type']);
            $result = $brocast->send();
            return $result;
        } catch (Exception $e) {
            print("Caught exception: " . $e->getMessage());
        }
    }


}