<?php
function pr($p,$d=1){
	print_r($p);
	$d===1 && die();
}
$base = dirname(__FILE__);
include $base.'/config.php';

class script_base{
	public $mysqlConn;
	public function __construct(){
		//header("Content-type: text/html; charset=utf-8");
	}

	public function __destruct(){
	}

	function query($sql){
		//return mysql_unbuffered_query($sql);
		return mysql_query($sql);
	}


	public function pdo_execute($sql){
		$stmt = $this->pdo_db()->prepare($sql);
		return $stmt->execute();
	}

	private $pdo_db;
	public function pdo_db(){
		if( is_null($this->pdo_db)){
		/*
			$host		= 'duobaohui.mysql.rds.aliyuncs.com';
			$username	= 'geshang';
			$password	= 'geshang2013';
			$db			= 'hayao';
		 */

			$host		= 'localhost';
			$username	= 'root';
			$password	= 'duobaohui';
			$db			= 'hayao';

			$dsn = 'mysql:dbname='.$db.';host='.$host;
			$this->pdo_db = new PDO($dsn, $username, $password);
			$stmt = $this->pdo_db -> prepare("SET NAMES utf8");
			$stmt->execute();
		}
		return $this->pdo_db;
	}

	function connect() 
	{
		/*
		$host		= 'duobaohui.mysql.rds.aliyuncs.com';
		$username	= 'geshang';
		$password	= 'geshang2013';
		$db			= 'hayao';
		 */
		$host		= 'localhost';
		$username	= 'root';
		$password	= 'duobaohui';
		$db			= 'hayao';
		//echo 'connecting...'.PHP_EOL.PHP_EOL;;
		$this->mysqlConn= mysql_connect($host, $username, $password, false) or die(mysql_error());
		$sta2 = $this->query("SET NAMES utf8");
		$sta3 = mysql_select_db($db) or die(mysql_error());
	}

	function close_connect()
	{
		mysql_close($this->mysqlConn);
	}

	function select($sql)
	{
		if( !strpos($sql, 'SQL_NO_CACHE')){//脚步执行的mysql不缓存，节省内存开销
			$sql = str_ireplace('select','select SQL_NO_CACHE', $sql);
		}
		$this->connect();
		$result = $this->query($sql) or die(mysql_error().'-------'.$sql);
		$list = array();
		while($row = mysql_fetch_assoc($result))
		{
			$list[] = $row;
		}
		mysql_free_result($result);
		$this->close_connect();
		return $list;
	}

	/*
	 * 返回对象方式访问字段
	 */
	function fetchAll($sql)
	{
		if( !stripos($sql, 'SQL_NO_CACHE')){//脚步执行的mysql不缓存，节省内存开销
			$sql = str_ireplace('select','SELECT SQL_NO_CACHE', $sql);
		}
		$this->connect();
		$result = $this->query($sql) or die(mysql_error().'----'. $sql);
		$list = array();
		while($row = mysql_fetch_object($result))
		{
			$list[] = $row;
		}
		mysql_free_result($result);
		$this->close_connect();
		return $list;
	}

    function fetchArrAll($sql)
	{   
		if( !stripos($sql, 'SQL_NO_CACHE')){//脚步执行的mysql不缓存，节省内存开销
			$sql = str_ireplace('select','SELECT SQL_NO_CACHE', $sql);
		}   
		$this->connect();
		$result = $this->query($sql) or die(mysql_error().'----'. $sql);
		$list = array();
		while($row = mysql_fetch_array($result))
		{   
			$list[] = $row;
		}   
		mysql_free_result($result);
		$this->close_connect();
		return $list;
	}   	

	/*
	 * 返回对象方式访问字段
	 */
	function fetch($sql)
	{
		if( !stripos($sql, 'SQL_NO_CACHE')){//脚步执行的mysql不缓存，节省内存开销
			$sql = str_ireplace('select','SELECT SQL_NO_CACHE', $sql);
		}
		if( !stripos($sql, 'limit')){
			$sql .= ' LIMIT 1';
		}
		$this->connect();
		$result = $this->query($sql) or die(mysql_error().'----'. $sql);
		$row = mysql_fetch_object($result);
		mysql_free_result($result);
		$this->close_connect();
		return $row;
	}

	function insert($data, $table){
		$table		= mysql_escape_string($table);
		$data		= array_map('mysql_escape_string', $data);
		$strFields	= implode(',', array_keys($data));
		$strval		= "'".implode("','" , $data). "'";
		$sql		= "insert into `$table` ($strFields) values($strval)";
		return $this -> execute($sql);
	}

	/*
	 * 批量新增
	 */
	function insertAll($arrData, $table){
		$table		= mysql_escape_string($table);
		$arrVal = array();
		foreach($arrData as $data){
			$data		= array_map('mysql_escape_string', $data);
			$arrVal[]	= "('".implode("','", $data) . "')";
		}
		$strval = implode(',', $arrVal);

		$strFields	= implode(',', array_keys($arrData[0]));
		$sql		= "insert  into `$table` ($strFields) values{$strval}";
		return $this -> execute($sql);
	}

	function insertIgnoreAll($arrData, $table){
		$table		= mysql_escape_string($table);
		$arrVal = array();
		foreach($arrData as $data){
			$data		= array_map('mysql_escape_string', $data);
			$arrVal[]	= "('".implode("','", $data) . "')";
		}
		$strval = implode(',', $arrVal);

		$strFields	= implode(',', array_keys($arrData[0]));
		$sql		= "insert IGNORE into `$table` ($strFields) values{$strval}";
		return $this -> execute($sql);
	}

	function insertIgnore($data, $table){
		$table		= mysql_escape_string($table);
		$data		= array_map('mysql_escape_string', $data);
		$strFields	= implode(',', array_keys($data));
		$strval		= "'".implode("','" , $data). "'";
		$sql		= "insert IGNORE into `$table` ($strFields) values($strval)";
		return $this -> execute($sql);
	}

	function update( $data, $where, $param, $table){
		$table		= mysql_escape_string($table);
		$data		= array_map('mysql_escape_string', $data);
		foreach($data as $field=>$v){
			$arrFields[] = $field . "='" .  $v . "'";
		}
		$strFields = implode(',', $arrFields);

		foreach($param as $kp=>$vp){
			if( is_array($vp) ){  // in
				$arrInWhere = array();
				foreach($vp as $kd=>$vd){
					$vd = "'".mysql_escape_string($vd)."'";
					$arrInWhere[] = $vd;
				}
				$where = str_replace(':'.$kp, implode(',', $arrInWhere), $where);
			}else{
				$vp = "'".mysql_escape_string($vp)."'";
				$where = str_replace(':'.$kp, $vp, $where);
			}
		}

		$sql = "UPDATE {$table} SET {$strFields} WHERE  {$where}";
		$isW = $this->execute($sql);
		return $isW===FALSE ? FALSE : true;	// 无论是否改变了数据，只要没报错，就算成功
	}

	function delete($where, $param, $table){
		foreach($param as $kp=>$vp){
			if( is_array($vp) ){  // in
				$arrInWhere = array();
				foreach($vp as $kd=>$vd){
					$vd = "'".mysql_escape_string($vd)."'";
					$arrInWhere[] = $vd;
				}
				$where = str_replace(':'.$kp, implode(',', $arrInWhere), $where);
			}else{
				$vp = "'".mysql_escape_string($vp)."'";
				$where = str_replace(':'.$kp, $vp, $where);
			}
		}
		$sql = "DELETE FROM `$table` WHERE {$where}";
		$isW = $this->execute($sql);
		return $isW===FALSE ? FALSE : true;	// 无论是否改变了数据，只要没报错，就算成功
	}


	public function increment( $field, $param, $table){
		if(is_array($field)){
			$sqlField = array();
			foreach($field as $kf=>$vn){
				$sqlField[] = $kf . '=' . $kf . '+'. $vn;
			}
			$strSqlField = implode(',', $sqlField);
		}else{
			$strSqlField = "{$field}={$field}+1";
		}
		//is_null($table) && $table = $this->table ;
		$whereField = key($param);
		//$where = 'WHERE ' .  $whereField. '=:'. $whereField;
		$where = 'WHERE ' .  $whereField. '='."'". mysql_escape_string($param[$whereField])."'";
		$sql = "UPDATE {$table} SET {$strSqlField} {$where}";

		$isW = $this->execute($sql, $param);
		return $isW===FALSE ? FALSE : true;	// 无论是否改变了数据，只要没报错，就算成功
	}

	public function decrement($field, $param,$table){
		if(is_array($field)){
			$sqlField = array();
			foreach($field as $kf=>$vn){
				$sqlField[] = $kf . '=' . $kf . '-'. $vn;
			}
			$strSqlField = implode(',', $sqlField);
		}else{
			$strSqlField = "{$field}={$field}-1";
		}
		//is_null($table) && $table = $this->table ;
		$whereField = key($param);
		//$where = 'WHERE ' .  $whereField. '=:'. $whereField;
		$where = 'WHERE ' .  $whereField. '='."'". mysql_escape_string($param[$whereField])."'";
		$sql = "UPDATE {$table} SET {$strSqlField} {$where}";

		$isW = $this->execute($sql, $param);
		return $isW===FALSE ? FALSE : true;	// 无论是否改变了数据，只要没报错，就算成功
	}

	function execute($sql)
	{
		$this->connect();
		$rs = $this->query($sql) or die(mysql_error(). '---'. $sql); 
		if(stripos($sql, 'insert')!==false && mysql_insert_id() !== 0){
			$rs = mysql_insert_id();
		}
		$this->close_connect();
		return $rs;
	}
	
	function curlGet($url, $get='', $autoFollow=0){
		$ch = curl_init();
		//$timeout = 5;
		curl_setopt ($ch, CURLOPT_URL, $url);
		curl_setopt ($ch, CURLOPT_RETURNTRANSFER, 1);
		//curl_setopt ($ch, CURLOPT_CONNECTTIMEOUT, $timeout);
		$file_contents = curl_exec($ch);
		curl_close($ch);
		return $file_contents;
	}

    /*
     * curl 方式抓取
     */
    function curlPost($url, $post='', $autoFollow=0){
        $ch = curl_init();
        $user_agent = 'Safari Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.73.11 (KHTML, like Gecko) Version/7.0.1 Safari/537.73.11';
        curl_setopt($ch, CURLOPT_USERAGENT, $user_agent);
        // 2. 设置选项，包括URL
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_HEADER, 0);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('X-FORWARDED-FOR:61.135.169.125', 'CLIENT-IP:61.135.169.125'));  //构造IP
		curl_setopt($ch, CURLOPT_REFERER, "http://www.baidu.com/");   //构造来路
		curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'GET');
		if($autoFollow){
			curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);  //启动跳转链接
			curl_setopt($ch, CURLOPT_AUTOREFERER, true);  //多级自动跳转
		}
		//
		if($post!=''){
			curl_setopt($ch, CURLOPT_POST, 1);//post提交方式
			curl_setopt($ch, CURLOPT_POSTFIELDS, $post);
		}
        // 3. 执行并获取HTML文档内容
        $output = curl_exec($ch);
        curl_close($ch);
        return $output;
    } 

	public function curlImage($url , $ip, $refer){
        $ch = curl_init();
        $user_agent = 'Mozilla/5.0 (Windows NT 6.1; rv:17.0) Gecko/20100101 Firefox/17.0 FirePHP/0.7.1';
        curl_setopt($ch, CURLOPT_USERAGENT, $user_agent);
        // 2. 设置选项，包括URL
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_HEADER, 0);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('X-FORWARDED-FOR:'. $ip, 'CLIENT-IP:'.$ip));  //构造IP
        curl_setopt($ch, CURLOPT_REFERER, $refer);   //构造来路
		//
        // 3. 执行并获取HTML文档内容
        $output = curl_exec($ch);
        curl_close($ch);
        return $output;
	}


	/*
	* 并发请求
	 $connomains array 链接数组
	*/
	public function  curlMulti($connomains){
		$mh = curl_multi_init();
		foreach ($connomains as $i => $url) {
			$conn[$i] = curl_init($url);//初始化各个子连接
			curl_setopt($conn[$i], CURLOPT_RETURNTRANSFER, 1);//不直接输出到浏览器
			curl_multi_add_handle ($mh,$conn[$i]);//加入多处理句柄
		}

		$active	= 0;//连接数
		do {
			do{
				//这里$active会被改写成当前未处理数
				//全部处理成功$active会变成0
				$mrc	= curl_multi_exec($mh, $active);

				//这个循环的目的是尽可能的读写，直到无法继续读写为止(返回CURLM_OK)
				//返回(CURLM_CALL_MULTI_PERFORM)就表示还能继续向网络读写
			}while($mrc==CURLM_CALL_MULTI_PERFORM);

			//如果一切正常，那么我们要做一个轮询，每隔一定时间(默认是1秒)重新请求一次
			//这就是curl_multi_select的作用,它在等待过程中，如果有就返回目前可以读写的句柄数量,以便
			//继续读写操作,0则没有可以读写的句柄(完成了)

		} while ($mrc==CURLM_OK&& $active &&curl_multi_select($mh)!=-1);//直到出错或者全部读写完毕

		if ($mrc != CURLM_OK) {
			print "Curl multi read error $mrc\n";
		}

		// retrieve data
		foreach ($connomains as $i => $url) {
			if (($err = curl_error($conn[$i])) == '') {
				$res[$i]=curl_multi_getcontent($conn[$i]);
			} else {
				print "Curl error on handle $i: $err/n";
			}
			curl_multi_remove_handle($mh,$conn[$i]);
			curl_close($conn[$i]);
		}
		curl_multi_close($mh);
		return $res;
	}

	public $logPath='/home/work/hitao_hot/gate/scripts/data/logs/';
	function log($file, $content, $isAppend=1){
		$filepath = $this->logPath;
		if($isAppend){
			return file_put_contents($filepath . $file, $content . "\n", FILE_APPEND);
		}else{
			return file_put_contents($filepath . $file, $content);
		}
	}

	function getLog($file){
		//$file = $this->logPath . $file;
		$content = file_get_contents($file);
		$content = trim($content);
		return $content;
	}
	
}
