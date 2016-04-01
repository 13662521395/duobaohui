<?php 


 namespace Gate\Package\Helper;


	 class DBUserConnect  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_user_connect'; 

		 const _FIELDS_		= 'user_id,user_type,status,auth,old_user_id';
}
