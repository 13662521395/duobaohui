<?php
namespace Gate\Config\Scripts;

class MySQL extends \Phplib\Config {

	protected function __construct() {
		$this->hitao_hot = $this->hitao_hot();
	}

	private function hitao_hot() {
		$config = array();
		$config['MASTER']    = array('HOST' => 'localhost', 'PORT' => '3306', 'USER' => 'root', 'PASS' => 'duobaohui', 'DB' => 'hitao_hot');
		$config['SLAVES'][] = array('HOST' => 'localhost',	'PORT' => '3306', 'USER' => 'root', 'PASS' => 'duobaohui', 'DB' => 'hitao_hot');
		return $config;
	}
}
