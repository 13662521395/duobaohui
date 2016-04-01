<?php 


 namespace Gate\Package\Helper;


	 class DBOrderProduct  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_order_product'; 

		 const _FIELDS_		= 'id,order_id,product_id,warehouse_id,price,num';
}
