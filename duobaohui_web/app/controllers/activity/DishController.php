<?php
/**
 * 抽菜demo
 * @author		guoshijie@shinc.net
 * @version		v1.0
 * @copyright	shinc
 */

namespace Laravel\Controller\Activity;

use Illuminate\Routing\Controller;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Facades\Input;

class DishController extends Controller {

    public $nowTime;

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowTime = date('Y-m-d H:i:s');
    }

    public function anyIndex() {
        $flag = Input::has('flag')?Input::get('flag'):1;
        if($flag==1){
            return Response::view('dish.dish1');
        }elseif($flag==2){
            return Response::view('dish.dish2');
        }elseif($flag==3){
            return Response::view('dish.dish3');
        }else{
            return Response::view('dish.dish1');
        }
    }

    public function anyShaidan(){
        $data = array();
        $data['date']=$this->nowTime;
        $flag = Input::has('flag')?Input::get('flag'):1;
        return Response::view('dish.shaidan'.$flag,$data);
    }
}
