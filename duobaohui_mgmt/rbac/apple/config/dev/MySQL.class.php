<?php
namespace Gate\Config\Dev;

class MySQL extends \Phplib\Config {

	protected function __construct() {
		$this->sh_duobaohui = $this->sql_conf();
	}

	private function sql_conf() {
		// 兼容laravel
		$configFile = require_once(ROOT_PATH.'/../../app/config/database.php');
		$config = $configFile['connections']['mysql'];


		$con = array();
		$con['MASTER']		= array('HOST' => $config['host'],'PORT' => '3306', 'USER' => $config['username'], 'PASS' => $config['password'], 'DB' => $config['database']);
		$con['SLAVES'][]		= array('HOST' => $config['host'],'PORT' => '3306', 'USER' => $config['username'], 'PASS' => $config['password'], 'DB' => $config['database']);
		return $con;
	}

}
