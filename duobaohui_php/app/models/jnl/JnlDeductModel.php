<?php
/**
 * 扣款记录模型
 * User: zhangtaichao
 * Date: 15/11/2
 * Time: 下午6:54
 */

namespace Laravel\Model;


use Illuminate\Support\Facades\DB;

class JnlDeductModel extends Model{
    protected $nowTime;
    public function __construct() {
        $this->nowTime = date('Y-m-d H:i:s');
        $this->table = 'jnl_deduct';
    }
}