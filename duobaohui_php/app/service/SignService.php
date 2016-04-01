<?php
/**
 * User: Steven Guo
 * Date: 15/11/4
 * Time: 上午11:15
 */

namespace Laravel\Service;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Session;
use Laravel\Model\SignModel;
use Laravel\Service\RedpacketService;

class SignService {

    private $signModel;
    private $targetSign;
    private $nowTime;

    public function __construct(){
        $this->signModel = new SignModel();
        $this->targetSign = 5;
        $this->nowTime = date('Y-m-d H:i:s');
    }

    public function getSign($user_id){
        $data = array();
        $data['user_id'] = $user_id;
        $signInfo = $this->signModel->getSign($user_id);
        if($signInfo){
            $data = array_merge($data, $this->getData($signInfo));
            $data['continue_num'] = $signInfo->continue_num;
        } else {
            $data['subDay'] = -1;
            $data['is_today'] = 0;
            $data['has_sign'] = 0;
            $data['no_sign'] = $this->targetSign - $data['has_sign'];
        }
        Log::info($data);
        return $data;
    }

    public function getData($signInfo){
        $data = array();
        $create_time =  $signInfo->create_time;
        $continue_num =  $signInfo->continue_num;

        $subDay = $this->timeDiff(strtotime(date('Y-m-d',strtotime($create_time))),strtotime(date('Y-m-d')));
        $subDay = $subDay['day'];
        $data['subDay'] = $subDay;
        if($subDay == 0){
             $data['is_today'] = 1;
             $data['has_sign'] = $continue_num;
        }elseif($subDay == 1){
            $data['is_today'] = 0;
            if($continue_num == $this->targetSign){
                $data['has_sign'] = 0;
            }else{
                $data['has_sign'] = $continue_num;
            }
        }else{
             $data['is_today'] = 0;
             $data['has_sign'] = 0;
        }
        $data['no_sign'] = $this->targetSign - $data['has_sign'];
        return  $data;
    }

    /**
     * 签到入库
     * @param $data
     * @return array|bool|int    1：成功   2：失败   3：今日已签到
     */
    public function addSign($data){
        $signInfo = $this->getSign($data[ 'user_id' ]);
        Log::info($signInfo);
        $subDay = $signInfo['subDay'];
        if($subDay==0){
            return 3;
        }elseif($subDay==1 && $signInfo['continue_num'] < $this->targetSign){
            $data['continue_num'] = $signInfo['continue_num']+1;
            if($data['continue_num'] == 5){
                $sendRedpacket = new RedpacketService();
                $sendRedpacket->sendSignRedpacket($data[ 'user_id' ]);
            }
        }else{
            $data['continue_num'] = 1;
        }
        $signRes = $this->signModel->addSign($data);

        if($signRes){
            return 1;
        }else{
            return 2;
        }
    }

    /**
     * 计算两个时间戳之差
     * @param $begin_time
     * @param $end_time
     * @return array
     */
    function timeDiff( $begin_time, $end_time ){
        if ( $begin_time < $end_time ) {
            $starttime = $begin_time;
            $endtime = $end_time;
        } else {
            $starttime = $end_time;
            $endtime = $begin_time;
        }
        $timediff = $endtime - $starttime;
        $days = intval( $timediff / 86400 );
        $remain = $timediff % 86400;
        $hours = intval( $remain / 3600 );
        $remain = $remain % 3600;
        $mins = intval( $remain / 60 );
        $secs = $remain % 60;
        $res = array( "day" => $days, "hour" => $hours, "min" => $mins, "sec" => $secs );
        Log::info($res);
        return $res;
    }

}