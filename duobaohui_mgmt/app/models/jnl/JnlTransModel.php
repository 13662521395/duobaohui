<?php
/**
 * 交易流水表模型
 * User: zhangtaichao
 * Date: 15/11/2
 * Time: 下午6:54
 */

namespace Laravel\Model;


use Illuminate\Support\Facades\DB;

class JnlTransModel extends Model{
    protected $nowTime;
    public function __construct() {
        $this->nowTime = date('Y-m-d H:i:s');
        $this->table = 'jnl_trans';
    }

    public function load($jnl_no)
    {
        return DB::table($this->table)->where('jnl_no', $jnl_no)->first();
    }

    // 更新
    public function update($jnl_no, $data)
    {
        return DB::table($this->table)->where('jnl_no', $jnl_no)->update($data);
    }


}