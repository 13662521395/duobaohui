<?php

	/*
	 *  全自动化基础数据统计脚本
	 *  要求：每5分钟或10分钟跑一次crontab自动按00:00:00执行一次
	 *  每一次都查询是否是初始交易
	 *  数据存储在  sh_count_data表中
	 *  author:liangfeng@shinc.net
	 *  create_time:2015-11-15
	 */

$base = dirname(__FILE__);
include($base .'/libs/script_base.php');


date_default_timezone_set('PRC');

class countData extends script_base{

	protected $int_time ='';
	protected $new_time ='';


	public function __construct(){
		$this->int_time = date( "Y-m-d 00:00:00" );
		$this->new_time = date( "Y-m-d H:i:s" );
	}

	public function anyStartCount(){

		print_r( "\n\n\n\n统计每天的【总购买次数(真实用户,虚拟用户)、红包除外的总消费金额(真实用户,虚拟用户)、统计奖品总价(真实用户,虚拟用户)】\n" );
		print_r( "程序初始自动化操作，查询上线时间至今每天的数据是否已进行统计\n" );
		print_r( "拿临时表2015-10-10的数据\n\n\n\n" );
		$has_data_sql 	            = "select start_time from `sh_count_data` where start_time = '2015-10-10 00:00:00'";
		$has_data                   = $this->fetch( $has_data_sql );

		if  (!$has_data)  {

			$start_date_time        = "2015-10-10 00:00:00";
			$last_date_time         = "2015-10-10 23:59:59";
			$status = $this->deleteAll( 'sh_count_data' );
			print_r( "删除所有数据: $status \n\n\n" );

		}else{

			$has_last_sql 	        = "select start_time from `sh_count_data` order by start_time desc";
			$has_last               = $this->fetch( $has_last_sql );
			print_r( "拿最后一条记录的时间:\n\n\n" );
			print_r( $has_last );

			if  ( $has_last->start_time == $this->int_time ) {

				$start_date_time    = $this->int_time;
				$last_date_time     = $this->new_time;
				print_r( "当前需执行的时间 $start_date_time to $last_date_time\n\n\n" );

			}else{

				$start_date_time    = date( "Y-m-d H:i:s" , strtotime("$has_last->start_time +1 days") );
				$last_date_time     = substr( $start_date_time , 0 , 10 )." 23:59:59";
				print_r( "拿最后一条时间加一天后的时间: $start_date_time to $last_date_time \n\n\n" );

			}
		}

		if  ($start_date_time != date( "Y-m-d 00:00:00") )  {

			print_r( "如果日期不等于今天，循环执行操作统计，直至到今天的日期为止:\n\n\n" );
			$days = $this->diffBetweenTwoDays( $start_date_time , $this->int_time );
			print_r( "统计最大循环数:" );
			print_r( $days ."\n\n" );


			for ( $i=0;$i<$days+1;$i++ )  {
				if  ( $i ==0 )  {

					$this->publicWay( $start_date_time , $last_date_time );

				}else{

					$start_date_time = date( "Y-m-d H:i:s" , strtotime("$start_date_time +1 days") );
					$last_date_time  = substr( $start_date_time , 0 , 10 )." 23:59:59";
					$this->publicWay( $start_date_time , $last_date_time );

				}
			}
		}else{

			$this->publicWay( $start_date_time , $last_date_time );

		}
	}

	private function publicWay( $start_date_time , $last_date_time ){
		print_r( "开始执行时间:".$start_date_time."\n\n\n" );
		//count_buycount_buy_false
		$sql1 = "select count(`sh_jnl_trans_duobao`.`num`) as count_buy_true from `sh_jnl_trans_duobao`
				left join `sh_jnl_trans`
					on `sh_jnl_trans`.`jnl_no` =  `sh_jnl_trans_duobao`.`trans_jnl_no`
				left join `sh_user`
					on `sh_user`.`id` =  `sh_jnl_trans`.`user_id`
				where `sh_user`.`is_real` = 1 and `sh_jnl_trans_duobao`.`status` = 1
					and `sh_jnl_trans_duobao`.`create_time` between '$start_date_time' and '$last_date_time'";

		$sql2 = "select count(`sh_jnl_trans_duobao`.`num`) as count_buy_false from `sh_jnl_trans_duobao`
				left join `sh_jnl_trans`
					on `sh_jnl_trans`.`jnl_no` =  `sh_jnl_trans_duobao`.`trans_jnl_no`
				left join `sh_user`
					on `sh_user`.`id` =  `sh_jnl_trans`.`user_id`
				where `sh_user`.`is_real` = 0 and `sh_jnl_trans_duobao`.`status` = 1
					and `sh_jnl_trans_duobao`.`create_time` between '$start_date_time' and '$last_date_time'";

		//count_amount
		$sql3 = "select sum(`sh_jnl_deduct`.`amount`) as count_amount_true from `sh_jnl_deduct`
				left join `sh_user`
					on `sh_user`.`id` =  `sh_jnl_deduct`.`user_id`
				where `sh_user`.`is_real` = 1
					and `sh_jnl_deduct`.`create_time`
						between '$start_date_time' and '$last_date_time'";

		$sql4 = "select sum(`sh_jnl_deduct`.`amount`) as count_amount_false from `sh_jnl_deduct`
				left join `sh_user`
					on `sh_user`.`id` =  `sh_jnl_deduct`.`user_id`
				where `sh_user`.`is_real` = 0
					and `sh_jnl_deduct`.`create_time`
						between '$start_date_time' and '$last_date_time'";

		//count_total_fee
		$sql5 = "select sum(`sh_activity`.`need_times`) as count_total_fee_true from `sh_period_result`
				left join `sh_user`
					on `sh_user`.`id` =  `sh_period_result`.`user_id`
				left join `sh_activity_period`
					on `sh_activity_period`.`id` = `sh_period_result`.`sh_activity_period_id`
				left join `sh_activity`
					on `sh_activity`.`id` = `sh_activity_period`.`sh_activity_id`
				where `sh_user`.`is_real` = 1
					and `sh_period_result`.`luck_code_create_time`
						between '$start_date_time' and '$last_date_time'";


		$sql6 = "select sum(`sh_activity`.`need_times`) as count_total_fee_false from `sh_period_result`
				left join `sh_user`
					on `sh_user`.`id` =  `sh_period_result`.`user_id`
				left join `sh_activity_period`
					on `sh_activity_period`.`id` = `sh_period_result`.`sh_activity_period_id`
				left join `sh_activity`
					on `sh_activity`.`id` = `sh_activity_period`.`sh_activity_id`
				where `sh_user`.`is_real` = 0
					and `sh_period_result`.`luck_code_create_time`
						between '$start_date_time' and '$last_date_time'";

		$result1  = $this->fetch( $sql1 );
		$result2  = $this->fetch( $sql2 );
		$result3  = $this->fetch( $sql3 );
		$result4  = $this->fetch( $sql4 );
		$result5  = $this->fetch( $sql5 );
		$result6  = $this->fetch( $sql6 );

		//记录到统计数据表中真实用户数据
		$dateTime = date( 'Y-m-d H:i:s' );

		$data1[]  = array(
			'trade_num' 	=> $result1->count_buy_true,
			'trade_money'	=> $result3->count_amount_true,
			'luck_money'	=> $result5->count_total_fee_true,
			'create_time'	=> $dateTime,
			'type'			=> 1,
			'start_time'	=> $start_date_time,
			'end_time'		=> $last_date_time,
		);

		echo "需要记录的真实用户数据:\n";
		var_dump($data1);
		echo "\n\n\n";

		$sql7 	= "select id from sh_count_data where start_time between '$start_date_time' and '$last_date_time'";
		$resultBbyCountDate = $this->fetchAll( $sql7 );

		echo "查询数据库是否有该记录:\n";
		var_dump( $resultBbyCountDate );
		echo "\n\n\n";

		if  ( empty($resultBbyCountDate ) || $resultBbyCountDate == false ){

		}else{

			$ids       = array();
			foreach ( $resultBbyCountDate as $key => $countDataIds ) {
				$ids[] = $countDataIds->id;
			}

			$table     = 'sh_count_data';
			$where     = '`id` in (:ids)';
			$del       = $this->delete( $where , array('ids' => $ids) , $table );
			echo "数据库有该数据，正在删除:\n";
			var_dump( $del );
			echo "\n\n\n";
		}

		$insertData1   = $this->insertIgnoreAll( $data1 , 'sh_count_data' );
		$data2[]       = array(
			'trade_num' 	=> $result2->count_buy_false,
			'trade_money'	=> $result4->count_amount_false,
			'luck_money'	=> $result6->count_total_fee_false,
			'create_time'	=> $dateTime,
			'type'			=> 0,
			'start_time'	=> $start_date_time,
			'end_time'		=> $last_date_time,
		);

		$insertData2   = $this->insertIgnoreAll( $data2 , 'sh_count_data' );
		echo "正在插入到数据库中:";
		var_dump( $insertData2 );
		echo "\n\n\n";

		echo "执行结果：\n";

		if  ( $insertData1 != 0 && $insertData2 != 0 )  {
			echo "\033[34m seccess \033[0m";
			echo "\033[33m seccess \033[0m";
			echo "\033[37m seccess \033[0m";
			echo "\n\n\n";
			//exit;
		}   else    {
			echo "\033[34m fail \033[0m";
			echo "\033[33m fail \033[0m";
			echo "\033[37m fail \033[0m";
			print_r( $insertData1.$insertData2 );
			echo "\n\n\n";
		}
	}


	/**
	 * 求两个日期之间相差的天数
	 * (针对1970年1月1日之后，求之前可以采用泰勒公式)
	 * @param string $day1
	 * @param string $day2
	 * @return number
	 */
	private function diffBetweenTwoDays ( $day1 , $day2 )
	{
		$second1 = strtotime( $day1 );
		$second2 = strtotime( $day2 );

		if ( $second1 < $second2 ) {
			$tmp     = $second2;
			$second2 = $second1;
			$second1 = $tmp;
		}
		return ( $second1 - $second2 ) / 86400;
	}

}

$obj = new countData();
$obj->anyStartCount();