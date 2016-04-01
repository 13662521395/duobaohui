<?php 
 namespace Gate\Package\Helper;
/*
 */
 class DBHelper{ 
	/*
	 * 垂直分表的查询
	 */
	public static function getTableJoinList($fields, $pkId, $dbHelper, $offset, $length, $where, $whereParams){
		$namespace		= '\Gate\Package\Helper\\';
		foreach($dbHelper as &$vh){
			$vh = $namespace . $vh;
		}

		$arrFields		= explode(',', str_replace(' ', '', $fields));
		$data			= array();
		// 第一个表为主表，决定排序
		$dbFields	= explode(',', str_replace(' ', '', $dbHelper[0]::_FIELDS_));
		$dbFields	= array_intersect($dbFields, $arrFields);
		$data[0]	= $dbHelper[0]::getConn()->field($pkId . ',' . implode(',', $dbFields) )->where($where, $whereParams)->order($pkId . ' DESC')->limit($offset, $length)->fetchArrAll();
		unset($dbHelper[0]);
		if(empty($data[0])){
			return array();
		}

		$ids = array();
		foreach($data[0] as $v){
			$ids[] = $v[$pkId];
		}
		foreach($dbHelper as $k=>$vh){
			$dbFields	= explode(',', str_replace(' ', '', $vh::_FIELDS_));
			$dbFields	= array_intersect($dbFields, $arrFields);
			if( !empty($dbFields)){
				$data[]		= $vh::getConn()->field($pkId . ',' . implode(',', $dbFields) )->where($pkId.' IN(:id) ', array('id'=>$ids))->fetchArrAll();
			}
		}

		$list = array();
		// 合并数组
		foreach($data as $vFieldList){
			foreach($vFieldList as $v){
				$list[$v[$pkId]] = isset($list[$v[$pkId]]) ? $list[$v[$pkId]] + $v : $v;
			}
		}

		// 转换为对象模式
		foreach($list as &$v){
			$v = (object)$v;
		}
		return $list;
	}


}
