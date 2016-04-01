<?php 


 namespace Gate\Package\Helper;


	 class DBProductWarehouse  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_product_warehouse'; 

		 const _FIELDS_		= 'warehouse_id,company_id,name,is_default,address,address_path,remark,sort,is_delete,create_time,update_time';
}
