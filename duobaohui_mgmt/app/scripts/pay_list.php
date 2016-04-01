<?php

/*
 *  脚本的交易记录查询
 *  要求：10分钟跑一次定时脚本
 *  数据存储在  sh_apy_list表中
 *  author:liangfeng@shinc.net
 *  create_time:2015-12-11
 *  算法：脚本每10分钟跑一次，拿pay_lisy表的create_time最新时间，查询create_time最新时间到脚本时间(当前时间)的记录
 */

$libs_path = __DIR__.'/libs';
include($libs_path .'/script_base.php');

date_default_timezone_set('PRC');

class payList extends script_base{

	public function scriptPatList(){

		//拿临时表最后一条记录
		$sql3    = "select create_time from `sh_pay_list` ORDER BY create_time DESC limit 1";
		$lastPay = $this->fetch( $sql3 );

		//初始化执行的自动化操作
		if( is_null($lastPay) || !$lastPay->create_time ){
			$startTime =  date('Y-m-d H:i:s');  //初始化执行时间
			@$lastPay->create_time   = date('2015-10-05 H:i:s');
			file_put_contents(  dirname(__FILE__).'/logs/log.txt', "\n\n".print_r( $startTime.'    初始化执行时间',1 ),FILE_APPEND );
		}else{
			$startTime = date('Y-m-d H:i:s');   //脚本执行时间
		}

		$sql1 = "select sh_user.id as user_id,sh_user.tel,sh_jnl_trans.jnl_no as trans_jnl_no,sh_jnl_trans.trans_code,
				sh_jnl_trans.jnl_status,sh_jnl_trans.pay_type,sh_jnl_trans.amount,
				sh_jnl_trans.create_time,sh_jnl_trans.recharge_channel,sh_jnl_trans_duobao.id as duobao_id,
				sh_bill.id as bill_id,sh_jnl_trans_duobao.status from `sh_user`
				left join `sh_jnl_trans`
					on `sh_jnl_trans`.`user_id` =  `sh_user`.`id`
				left join `sh_jnl_trans_duobao`
					on `sh_jnl_trans_duobao`.`trans_jnl_no` =  `sh_jnl_trans`.`jnl_no`
				left join `sh_bill`
					on `sh_bill`.`out_trade_no` =  `sh_jnl_trans`.`jnl_no`
				left join `sh_jnl_alipay`
					on `sh_jnl_alipay`.`out_trade_no` =  `sh_jnl_trans`.`jnl_no`
				where `sh_user`.`is_real` = 1 and `sh_jnl_trans`.`jnl_status` != 0
					and `sh_jnl_trans`.`create_time` between '$lastPay->create_time' and '$startTime' ORDER BY sh_jnl_trans.create_time DESC";

		$results = $this->fetchAll( $sql1 );

		//var_dump($results);die;
		if(empty($results)){
			file_put_contents( dirname(__FILE__).'/logs/log.txt', "\n\n".print_r( $startTime.'   没有数据',1 ),FILE_APPEND );
			echo "没有数据";die;
		}

		//临时表存储
		$sql2 = "select id from `sh_pay_list` where `create_time` between '$lastPay->create_time' and '$startTime'";
		$hasInsert = $this->fetch( $sql2 );
		if(!empty($hasInsert)){
			$table = 'sh_pay_list';
			$where = "`create_time` between '$lastPay->create_time' and '$startTime'";
			$this->delete( $where ,array(),$table);
		}

		//die;
		$insertData = array();
		foreach($results as $value){
			$insertData[] = (array)$value;
		}
		$savePayList = $this->insertIgnoreAll( $insertData , 'sh_pay_list' );


		if($savePayList){
			echo "\n\n";
			//加背景  echo "\033[41;34m seccess \033[0m";
			//无背景
			echo "\033[34m seccess \033[0m";
			echo "\033[33m seccess \033[0m";
			echo "\033[37m seccess \033[0m";
			file_put_contents(  dirname(__FILE__).'/logs/log.txt', "\n\n".print_r( $startTime.'   seccess',1 ),FILE_APPEND );
			echo "\n\n";

		}else{
			echo "\n\n";
			echo "\033[34m fail \033[0m";
			echo "\033[33m fail \033[0m";
			echo "\033[37m fail \033[0m";
			file_put_contents(  dirname(__FILE__).'/logs/log.txt', "\n\n".print_r( $startTime.'   fail',1 ),FILE_APPEND );
			echo "\n\n";
		}
	}
}

$obj = new payList();
$obj->scriptPatList();