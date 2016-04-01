<?php
/**
 * 签到
 * @author		Steven Guo
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Client;	    // 定义命名空间

use ApiController;							//引入接口公共父类，用于继承
use Illuminate\Support\Facades\View;		//引入视图类
use Illuminate\Support\Facades\Input;		//引入参数类
use Illuminate\Support\Facades\Session;		//引入session
use Illuminate\Support\Facades\Request;
use Illuminate\Support\Facades\Response;	//引入response

use Laravel\Model\ShopModel;
use Laravel\Model\SystemModel;			//引入model
use Laravel\Model\ClientModel;
use Illuminate\Support\Facades\Log;
use Laravel\Service\SignService;//引入日志类

class SignController extends ApiController {

    private $signService;

    public function __construct(){
        parent::__construct();
        $this->nowDateTime = date('Y-m-d H:i:s');
        $this->signService = new SignService();
    }

    /**
     * 签到H5页面
     * @param userId 用户id
     * @return \Illuminate\Http\Response
     */
    public function anySignIn(){
        if(!Input::has( 'userId' )){
            return Response::json( $this->response( '10005' ) );
        }
        $user_id = Input::get( 'userId' );
        $data = $this->signService->getSign($user_id);
        Log::info($data);
        return Response::view('client.sign', $data);
    }

    /**
     * 签到
     */
    public function anyAddSign(){
        if(!Input::has( 'userId' )){
            return Response::json( $this->response( '10005' ) );
        }

        $data = array();
        $data[ 'cookie' ] = Session::getId();
        $data[ 'ip' ] = Request::ip();
        $data[ 'user_id' ] = Input::get( 'userId' );
        $data[ 'os_type' ] = Input::has( 'os_type' )?Input::get( 'os_type' ):1;
        $data[ 'create_time' ] = $this->nowDateTime;
        $res = $this->signService->addSign($data);
        Log::info($res);
        $data = $this->signService->getSign($data[ 'user_id' ]);
        $data['signRes'] = $res;
        if($res){
            return Response::json( $data );
        }else{
            return Response::json( $this->response( 0 ) );
        }
    }


}
