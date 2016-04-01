<?php
/**
 * 充值记录流水模型
 * User: zhangtaichao
 * Date: 15/11/2
 * Time: 下午6:54
 */

namespace Laravel\Model;


class JnlRechargeModel extends Model{
    protected $nowTime;
    public function __construct() {
        $this->nowTime = date('Y-m-d H:i:s');
        $this->table = 'jnl_recharge';
    }
}