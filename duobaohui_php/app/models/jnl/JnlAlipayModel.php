<?php
/**
 * 支付宝回调记录模型
 * User: zhaozhonglin
 * Date: 15/11/2
 * Time: 下午6:54
 */

namespace Laravel\Model;


use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;

class JnlAlipayModel extends Model{
    protected $nowTime;
    public function __construct() {
        $this->nowTime = date('Y-m-d H:i:s');
        $this->table   = 'jnl_alipay';
    }

    public function load($trade_no)
    {
        return DB::table($this->table)->where('trade_no', $trade_no)->first();
    }
    
    public function loadByOutTradeNo($out_trade_no)
    {
        return DB::table($this->table)->where('out_trade_no', $out_trade_no)->first(); 
    }

    public function addInfo($data)
    {
        try{
            $keys           = array();
            $values         = array();
            foreach($data as  $key => $value){
                $keys[]     = $key;
                $values[]   = $value;
            }
            $keyInfos       = implode( ',', $keys ) ;
            $valueInfos     = "'" . implode( "','", $values ) . "'";
            $sql = "insert into sh_jnl_alipay ($keyInfos) values ($valueInfos)";

            Log::error(var_export($sql, true), array(__CLASS__));

            $res = DB::insert("insert into sh_jnl_alipay ($keyInfos) values ($valueInfos)");
            return $res;
        }catch (\Exception $e){
            Log::error(var_export('Exception==========================', true), array(__CLASS__));
            Log::error($e->getMessage());
            Log::error(print_r($e->getTrace(),true));
            Log::error($e->getTraceAsString());
        }finally {
            Log::error(var_export('finally==========================', true), array(__CLASS__));
        }

    }


    
}