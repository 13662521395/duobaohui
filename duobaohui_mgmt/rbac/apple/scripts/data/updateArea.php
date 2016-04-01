<?php

require_once 'db.php';

function run(){
	update();	
}

function connMysql(){
	$con = mysql_connect('localhost' , 'root' , 'duobaohui');
	if(!$con){
		die('Could not connect:' . mysql_error());
	}

	$sta2 =mysql_query("SET NAMES utf8");
	mysql_select_db('hayao' , $con);
	return $con;
}

function update(){
	$page = 0;
	while(1){
		$result = getAreaList($page);
		if(empty($result)){
			return 1;
		}
		$con = connMysql();
		for($i = 0 ; $i < count($result) ; $i++){
			$division = $result[$i]['division'];
			if(substr($division , 2 , 5) == '0000'){
				$level = 1;
				$pid = 0;
				$id =  $result[$i]['id'];
				$pid_path = "0-".$id;
				$time = time();
				$sql = "update ha_area set pid_path='$pid_path' , level=$level , pid=$pid , update_time=$time , create_time=$time where id=$id;";
				mysql_query($sql);
			}elseif(substr($division , 4 , 5) == '00'){
				$temp = substr($division , 0 , 2);
				(int)$temp_division = $temp.'0000';
				$sql = "select id , pid_path from ha_area where division=$temp_division;";
				$temp_pid = mysql_query($sql);
				$list = array();
				while($row = mysql_fetch_array($temp_pid))
				{
					$list[] = $row;
				}
				$level = 2;
				$pid = $list[0]['id'];
				$id =  $result[$i]['id'];
				$pid_path = $list[0]['pid_path'].'-'.$id;
				$time = time();
				$sql = "update ha_area set pid_path='$pid_path' , level=$level , pid=$pid , update_time=$time , create_time=$time where id=$id;";
				mysql_query($sql);	
			}else{
				$temp = substr($division , 0 , 4);
				(int)$temp_division = $temp.'00';
				$sql = "select id , pid_path from ha_area where division=$temp_division;";
				$temp_pid = mysql_query($sql);
				$list = array();
				while($row = mysql_fetch_array($temp_pid))
				{
					$list[] = $row;
				}
				$level = 3;
				$pid = $list[0]['id'];
				$id =  $result[$i]['id'];
				$pid_path = $list[0]['pid_path'].'-'.$id;
				$time = time();
				$sql = "update ha_area set pid_path='$pid_path' , level=$level , pid=$pid , update_time=$time , create_time=$time where id=$id;";
				mysql_query($sql);	
			}

		}
		$page++;

		mysql_close($con);
	}
}

function getAreaList($page){
	
	$con = connMysql();	

	$length = 20;
	$offset = $page*$length;
	$sql = "select * from ha_area limit $offset , $length;";
	$result =  mysql_query($sql);
	while($row = mysql_fetch_array($result))
	{
		$list[] = $row;
	}
	mysql_close($con);
	return $list;
}
run();
?>
