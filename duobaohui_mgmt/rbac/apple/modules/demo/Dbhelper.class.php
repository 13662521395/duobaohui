<?php
/*
 * 开发助手
 * 生成package/helper/DBHelper
 */
namespace Gate\Modules\Demo;
use Gate\Package\Demo\Demo;

class Dbhelper extends \Gate\Libs\Controller {
	private $table_name;
	private $database;
	private $db_table_pre;

	private function _init() {
		// 数据库
		$this->database = 'hayao';
		$this->db_table_pre = 'ha_';

		// 只有开发环境才能使用本功能
		if($_SERVER['SERVER_ADDR']!='192.168.11.10'){
			return FALSE;
		}

		if(!empty($_REQUEST['table_name'])){
			$this->table_name = trim($_REQUEST['table_name']);
		}

		return TRUE;
			
	}

	public function run() {
		if (!$this->_init()) {
			return FALSE;
		}
		if( !empty($this->table_name)){
			$this->create();
		}
	}


	private function create(){
		// 
		$tableName	= str_replace($this->db_table_pre,'',$this->table_name);
		$tableName	= str_replace(' ', '', ucwords(str_replace('_', ' ', strtolower($tableName))));
		//$dbhelper	= 'DB'. $tableName . 'Helper';
		$dbhelper	= 'DB'. $tableName;
		$file		= ROOT_PATH . '/package/helper/' . $dbhelper . '.class.php';

		//
		$fields		= $this->getTableFields();
		if(!$fields){
			echo '表错误';
			return false;
		}
		$fieldsDesc = $this->getTableFieldsDesc();
		$code = <<<CODE
<?php
/*
 * 
 */
namespace Gate\Package\Helper;

class {$dbhelper} extends \Phplib\DB\DBModel {
	const _DATABASE_= '{$this->database}';
	const _TABLE_	= '{$this->table_name}';
	const _FIELDS_	= '{$fields}';

	{$fieldsDesc}
}
CODE;


		//$isC = file_put_contents($file, $code, FILE_APPEND);
		$isC = file_put_contents($file, $code);

		if($isC){
			echo $dbhelper . ' 添加成功';
		}
	}

	/*
	 *
	 */
	public function getTableFields(){
		$list = Demo::getInstance()->getTableFields($this->table_name);
		if(empty($list)){
			return FALSE;
		}
		foreach($list as $v){
			$fieldsList[] = $v->COLUMN_NAME ;
		}
		return implode(",", $fieldsList);
	}

	public function getTableFieldsDesc(){
		$list = Demo::getInstance()->getTableFields($this->table_name);
		if(empty($list)){
			return FALSE;
		}
		foreach($list as $v){
			$fieldsList[] = '// '. $v->COLUMN_NAME . '					-- ' . $v->DATA_TYPE . '	 ' . $v->COLUMN_COMMENT;
		}
		return implode("\n	", $fieldsList);
	}

}
