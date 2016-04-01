<?php
$libs_path = __DIR__.'/../libs';
$logs_path = __DIR__.'/../logs';

include_once($libs_path .'/script_base.php');

class test  extends script_base{
	public function run(){
		echo 123;
		var_dump($this->pdo_execute('select * from sh_rbac_node'));
	}
} 

$ss = new test();
echo $ss->run();
