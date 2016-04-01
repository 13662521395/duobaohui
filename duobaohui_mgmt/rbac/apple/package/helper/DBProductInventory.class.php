<?php 


 namespace Gate\Package\Helper;


	 class DBProductInventory  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_product_inventory'; 

		 const _FIELDS_		= 'inventory_id,product_id,company_id,warehouse_id,number';
}
