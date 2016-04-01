<?php 


 namespace Gate\Package\Helper;


	 class DBComment  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_comment'; 

		 const _FIELDS_		= 'id,pid,resource_id,user_id,content,type,reply_user_id,is_delete,create_time,child_id';
}
