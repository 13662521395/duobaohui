<?php 
 namespace Gate\Package\Helper;

/*
 *
 * 多级分类
 */
 class DBCategoryHelper{ 
	
	/*
	 * 新增多级分类
	 *
	 * DB		表映射类
	 * pkField		主键id
	 */
	public static function addCategory($newData, $DB, $pkField='id'){
		$DB = 'Gate\\Package\\Helper\\'.$DB;
		if($newData['pid']>0){
			$row = $DB::getConn()->field('top_id,level,pid_path')->where($pkField.'=:id', array('id'=>$newData['pid']))->limit(1)->fetch();
			$newData['level'] = $row->level+1;
			$newData['top_id'] = $row->top_id;
			$newData['pid_path'] = $row->pid_path !='' ? $row->pid_path.'-'.$newData['pid'] : $newData['pid'];

		}else{
			$newData['level'] = 1;
			$newData['pid_path'] = '';
		}
		
		$newId = $DB::getConn()->insert($newData);
		$GLOBALS['rbac_sql'][] = $GLOBALS['sql_dump'];
		if($newData['level']==1){
			$DB::getConn()->update(array('top_id'=>$newId), $pkField.'=:id',array('id'=>$newId));
			$GLOBALS['rbac_sql'][] = $GLOBALS['sql_dump'];
		}

		return $newId;
	}



	/*
	 * 编辑多级分类
	 *
	 * DB		表映射类
	 * pkField		主键id
	 */
	public static function editCategory($newData,$DB, $pkField='id'){
		$DB = 'Gate\\Package\\Helper\\'.$DB;
		$oldRow = $DB::getConn()->where($pkField.'=:id',array('id'=>$newData[$pkField]))->fetch();
		// 父级菜单
		if($oldRow->pid!=$newData['pid']){
			if($newData['pid']==0){
				$newData['top_id']		= $newData[$pkField];
				$newData['level']		= 1;
				$newData['pid_path']	= '';
			}else{
				$newParentRow = $DB::getConn()->field('top_id,level,pid_path')->where($pkField.'=:id', array('id'=>$newData['pid']))->fetch();
				$newData['top_id']		= $newParentRow->top_id;
				$newData['level']		= $newParentRow->level + 1;
				$newData['pid_path']	= $newParentRow->pid_path !='' ? $newParentRow->pid_path.'-'.$newData['pid'] : $newData['pid'];
			}

		}

		$isUp = $DB::getConn()->update($newData, $pkField.'=:id', array('id'=>$newData[$pkField]));
			$GLOBALS['rbac_sql'][] = $GLOBALS['sql_dump'];
		if($isUp && $oldRow->pid!=$newData['pid']){
			// 更新子类的pid_path
			if($oldRow->pid!=$newData['pid']){
				$oldPath = $oldRow->pid_path !='' ? $oldRow->pid_path.'-'.$oldRow->$pkField : $oldRow->$pkField;  // 顶级路径为空
				$childList1 = $DB::getConn()->field($pkField.',level,top_id,pid,pid_path')->where('pid_path = :pid_path', array('pid_path'=>$oldPath))->fetchAll();
				$childList2 = $DB::getConn()->field($pkField.',level,top_id,pid,pid_path')->where('pid_path LIKE :pid_path', array('pid_path'=>$oldPath.'-%'))->fetchAll();
				$childList = array_merge($childList1, $childList2);
				if(!empty($childList)){
					$childData = array();
					foreach($childList as $v){
						$childData['level']		= $v->level + ($newData['level'] - $oldRow->level);
						$childData['top_id']	= $newData['top_id'];
						$childData['pid_path']	= $newData[$pkField] . substr($v->pid_path, strlen($oldPath));
						if($newData['pid_path']!=''){
							$childData['pid_path'] = $newData['pid_path'] .'-' . $childData['pid_path'];
						}

						$DB::getConn()->update($childData, $pkField.'=:id', array('id'=>$v->$pkField));
						$GLOBALS['rbac_sql'][] = $GLOBALS['sql_dump'];
					}
				}
			}
		}
		return $isUp;
	}


	/*
	 * 删除分类
	 */
	public static function delCategory($id, $pkField='id'){
		$is =  DBRbacNode::getConn()->delete($pkField.'=:id', array('id'=>$id));
		$GLOBALS['rbac_sql'][] = $GLOBALS['sql_dump'];
		return $is;
	}


	/*
	 * 获取分类列表
	 *
	 * pkField		表的主键id
	 * isMulti	是否多级多维返回
	 *		isMulti=0 : 一维数组,根据level值不同分出级别
	 *		isMulti=1 : 多维数组
	 */
	public static function getCategoryList($list, $pkField='id', $isMulti=false){
		if(empty($list)){return array();}
		$tmp = array();
		foreach($list as $v){
			//返回多维数组
			if(isset($tmp[$v->$pkField])){
				$v->child = $tmp[$v->$pkField]->child;
				unset($tmp[$v->$pkField]);
			}

			$tmp[$v->pid]->child[] = $v;
		}
		$newList = array();
		if($isMulti){
			$newList = $tmp[0]->child;
		}else{
			self::getCategoryChildren($tmp[0]->child, $newList);
		}
		return $newList;
	}

	private static function getCategoryChildren($child, &$newList){
		foreach($child as $v){
			$newV = clone $v;
			unset($newV->child);
			$newList[] =  $newV;
			if(isset($v->child)){
				self::getCategoryChildren($v->child, $newList);
			}
		}
	}

	/*
	 * 创建分类表
	 *
		CREATE TABLE IF NOT EXISTS `aaa_category` (
			`category_id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
			`name` varchar(32) NOT NULL,
			`status` tinyint(1) NOT NULL DEFAULT '0',
			`remark` varchar(255) NOT NULL,
			`sort` smallint(6) unsigned NOT NULL,
			`pid` smallint(6) unsigned NOT NULL,
			`pid_path` varchar(255) NOT NULL COMMENT '完整路径',
			`level` tinyint(1) unsigned NOT NULL,
			`is_end` tinyint(1) NOT NULL,
			`top_id` int(10) unsigned NOT NULL,
			`type` tinyint(1) NOT NULL DEFAULT '0',
			`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
			`update_time` int(10) unsigned NOT NULL,
			PRIMARY KEY (`category_id`)
		) ENGINE=InnoDB  DEFAULT CHARSET=utf8

	 */

}
