<?php 


 namespace Gate\Package\Helper;


	 class DBOrder  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_order'; 

		 const _FIELDS_		= 'id,user_id,create_time,total_price,status,address_id,integral';
}
