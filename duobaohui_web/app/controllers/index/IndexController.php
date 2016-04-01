<?php
namespace Laravel\Controller\Index;

use Carbon\Carbon;
use Illuminate\Routing\Controller;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Response;
use App\Libraries\Sms;

class IndexController extends Controller {
    public function getIndex() {
        $download_url = DB::table('app_version')->where('os_type', '1')->orderBy('update_time', 'desc')->pluck('url');
        return Response::view('index.index2',array('download_url' => $download_url));
    }

    public function postIndex() {
        if (!Input::has('tel')) return Response::json(array('code' => '-1', 'msg' => 'missing parameters'));
        $tel = Input::get('tel');
        $tel_pattern = "/^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/i";
        if (!preg_match($tel_pattern, $tel)) return Response::json(array('code' => '-2', 'msg' => 'invalid number'));
        switch ($this->sendDownloadSms($tel)) {
            case '1':  return Response::json(array('code' => '1', 'msg' => 'success'));break;
            case '0':  return Response::json(array('code' => '0', 'msg' => 'fail'));break;
            case '-1': return Response::json(array('code' => '-1', 'msg' => '请在1分钟后重发'));break;
        }
    }

    private function sendDownloadSms($tel, $minute = 1) {
        date_default_timezone_set('PRC');
        $expiresAt = Carbon::now()->addMinutes($minute);
        $ip = $this->getIP();
        if (Cache::add($ip.'-'.$tel, date("Y-m-d H:i:s"), $expiresAt)) {
            //$download_url = DB::table('app_version')->orderBy('update_time', 'desc')->pluck('url');
            $download_url = "http://dwz.cn/2ag4RE";
//            if (Sms::sendRegisterCode($tel, $download_url)) return '1';//成功
            if (Sms::sendDownloadSMS($tel)) return '1';//成功
            return '0';//短信失败
        } else return '-1';//等待时间内
    }

    private function getIP() {
        $ip = "";
        if (getenv("HTTP_CLIENT_IP")) $ip = getenv("HTTP_CLIENT_IP");
        else if(getenv("HTTP_X_FORWARDED_FOR")) $ip = getenv("HTTP_X_FORWARDED_FOR");
        else if(getenv("REMOTE_ADDR")) $ip = getenv("REMOTE_ADDR");
        return $ip;
    }
}