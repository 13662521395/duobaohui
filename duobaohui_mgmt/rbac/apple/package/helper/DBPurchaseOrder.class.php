<?php 


 namespace Gate\Package\Helper;


	 class DBPurchaseOrder  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_purchase_order'; 

		 const _FIELDS_		= 'id,company_id,order_No,user_id,status,create_time,remark';
}
