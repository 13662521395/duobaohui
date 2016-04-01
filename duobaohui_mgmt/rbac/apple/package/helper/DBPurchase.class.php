<?php 


 namespace Gate\Package\Helper;


	 class DBPurchase  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_purchase'; 

		 const _FIELDS_		= 'purchase_id,company_id,order_id,warehouse_id,supplier_id,product_id,number,real_number,library,user_id,remark,return,status,create_time,update_time';
}
