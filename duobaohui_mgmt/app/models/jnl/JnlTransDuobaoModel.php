<?php
/**
 * 夺宝流水表模型
 * User: zhangtaichao
 * Date: 15/11/2
 * Time: 下午6:54
 */

namespace Laravel\Model;


use Illuminate\Support\Facades\DB;

class JnlTransDuobaoModel extends Model{
    protected $nowTime;
    public function __construct() {
        $this->nowTime = date('Y-m-d H:i:s');
        $this->table = 'jnl_trans_duobao';
    }

    /**
     * 根据交易流水号，获取新建状态的夺宝记录
     * @param $jnl_no
     */
    public function getByJnlNo($jnl_no) {
        return DB::table('jnl_trans_duobao')
            ->where('trans_jnl_no',$jnl_no)
            ->where('status','0')
            ->first();
    }
    
    public function loadByOutTradeNo($out_trade_no)
    {
        return DB::table($this->table)->where('trans_jnl_no', $out_trade_no)->first(); 
    }
}