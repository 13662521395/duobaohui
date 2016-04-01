<?php
/**
 * 支付宝回调记录模型
 * User: zhaozhonglin
 * Date: 15/11/2
 * Time: 下午6:54
 */

namespace Laravel\Model;


use Illuminate\Support\Facades\DB;

class JnlAlipayModel extends Model{
    protected $nowTime;
    public function __construct() {
        $this->nowTime = date('Y-m-d H:i:s');
        $this->table = 'jnl_alipay';
    }

    public function load($trade_no)
    {
        return DB::table($this->table)->where('trade_no', $trade_no)->first();
    }
    
    public function loadByOutTradeNo($out_trade_no)
    {
        return DB::table($this->table)->where('out_trade_no', $out_trade_no)->first(); 
    }
    
}