<?php 


 namespace Gate\Package\Helper;


	 class DBProductInventoryTranslator  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_product_inventory_translator'; 

		 const _FIELDS_		= 'translator_id,product_id,company_id,warehouse_id_from,warehouse_id_to,number,user_id,create_time,remark';
}
