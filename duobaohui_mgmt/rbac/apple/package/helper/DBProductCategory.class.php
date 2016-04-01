<?php 


 namespace Gate\Package\Helper;


	 class DBProductCategory  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_product_category'; 

		 const _FIELDS_		= 'category_id,name,status,remark,sort,pid,pid_path,level,is_end,top_id,type,is_delete,create_time,update_time';
}
