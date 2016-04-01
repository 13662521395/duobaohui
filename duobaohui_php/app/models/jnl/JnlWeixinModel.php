<?php
/**
 * 微信回调记录模型
 * User: liangfeng
 * Date: 15/11/20
 * Time: 11:40
 */

namespace Laravel\Model;


use Illuminate\Support\Facades\DB;

class JnlWeixinModel extends Model{
    protected $nowTime;
    public function __construct() {
        $this->nowTime = date('Y-m-d H:i:s');
        $this->table = 'jnl_weixin';
    }

    public function load($transaction_id)
    {
        return DB::table($this->table)->where('transaction_id', $transaction_id)->first();
    }

    public function loadByOutTradeNo($out_trade_no)
    {
        return DB::table($this->table)->where('out_trade_no', $out_trade_no)->first();
    }

}