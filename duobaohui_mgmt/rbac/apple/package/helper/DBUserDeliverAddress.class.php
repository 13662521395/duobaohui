<?php 


 namespace Gate\Package\Helper;


	 class DBUserDeliverAddress  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_user_deliver_address'; 

		 const _FIELDS_		= 'id,user_id,area_id,area_name,detail_address,zip_code,receiver_name,contact,is_default,is_delete';
}
