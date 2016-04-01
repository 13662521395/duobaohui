<?php
/**
 * Created by PhpStorm.
 * User: guoshijie
 * Date: 15/10/29
 * Time: 下午3:43
 */

namespace Laravel\Model;

use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;

class ShopModel extends Model {

    public function __construct(){
        $this->init();
    }

    private function init(){
        $this->nowDateTime = date('Y-m-d H:i:s');
    }

    public function addShopShareScan($data) {
        $id = DB::table('shop_scan')->insert($data);
        return $id;
    }

    // 添加
    public function addShopDownload($data)
    {
        return DB::table("shop_download")->insertGetId($data);
    }

}

