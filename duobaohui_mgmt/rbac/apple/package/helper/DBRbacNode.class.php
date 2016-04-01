<?php 


 namespace Gate\Package\Helper;


	 class DBRbacNode  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'sh_rbac_node'; 

		 const _FIELDS_		= 'node_id,name,action,status,remark,sort,pid,pid_path,level,is_end,top_id,type,url,is_delete,create_time,update_time';
}
