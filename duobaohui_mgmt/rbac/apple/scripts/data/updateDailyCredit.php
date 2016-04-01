<?php 
#!/usr/bin/php
/*
 */
$libs_path = __DIR__.'/../libs';
include($libs_path .'/script_base.php');
class update_daily_credit extends script_base{
	public function run(){
		$sql = "update ha_user_profile set credit_all=credit+credit_daily, credit=credit+credit_daily ,credit_daily=0  where 1=1";
		$res = $this->execute($sql);
		return $res;
	}

}

//ini_set('memory_limit','128M');
$obj = new update_daily_credit();
$obj-> run();

?>
