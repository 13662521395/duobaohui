<?php
/**
 * 短信发送
 * User: guoshijie
 * Date: 15/10/29
 * Time: 下午3:43
 */
namespace Laravel\Controller\Admin;

use AdminController;
use App\Libraries\Sms;
use Illuminate\Support\Facades\View;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Redirect;
use Laravel\Model\Admin\UserModel;
use Laravel\Model\ShopModel;
use Laravel\Service\ShopService;

class SmsController extends AdminController {
    private $userM;
    private $nowTime;

    public function __construct() {
        parent::__construct();
        $this->userM = new UserModel();
        $this->nowTime = date('Y-m-d H:i:s',time());
        $this->_length = 10;
    }

    /**
     * IOS版本上线后，批量给IOS用户推送下载短信
     */
    public function anySendSmsForIos(){
        $os_type = Input::has('os_type')?Input::get('os_type'):2;
        $test = Input::has('test')?Input::get('test'):0;
        $userList = $this->userM->getUserListByOsType($os_type);
        if($userList) {
            $res = $this->sendSmsBatch($userList,$test);
            $sum = $res['sum'];
            $telArray = $res['telArray'];
            if($sum > 0){
                return Response::json($this->response( 1 , 'You have successfully sent the '.$sum.' pieces of information' , $telArray));
            }
            return Response::json($this->response( 0 ));
        }else{
            Log::info('ios userlist is null');
            return Response::json($this->response( 0, "待发用户列表为空"));
        }
    }

    public function sendSmsBatch($list,$test){
        $sum = 0;
        $telArray = array();
        $smsContent = "小伙伴们，夺宝会IOS已正式发布，您还有1元夺宝币未消费，不要让大奖和您擦肩而过！点击链接参与抽奖:http://dwz.cn/2xVedT";

        if($test == 1){
            $tel = '13661162115';
            $res = Sms::sendShortMessage($tel,$smsContent);
            Log::info($res);
            if($res['status']==1){
                $sum += 1;
                $telArray[] = $tel;
            }
            return array('sum'=>$sum,'telArray'=>$telArray);
        } else {
            foreach($list as $item) {
                $id = $item->id;
                $tel = $item->tel;
                $os_type = $item->os_type;
                $create_time = $item->create_time;

                $res = Sms::sendShortMessage($tel,$smsContent);
//                $res = array('status'=>1);
                Log::info($res);
                if($res['status']==1){
                    $this->userM->updateRedPacketSendFlag($id,1);
                    $sum += 1;
                    $telArray[] = $tel;
                    Log::info('Index:'.$sum.'   IOS send msg tel:'.$tel.'  os_type:'.$os_type.'  create_time:'.$create_time.'  send_time:'.$this->nowTime);
                }
            }
            return array('sum'=>$sum,'telArray'=>$telArray);
        }

    }

}