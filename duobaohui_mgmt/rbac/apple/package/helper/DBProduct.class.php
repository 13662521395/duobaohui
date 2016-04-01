<?php 


 namespace Gate\Package\Helper;


	 class DBProduct  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_product'; 

		 const _FIELDS_		= 'product_id,user_id,category_id,sn,name,name_en,self_code,type,produce_address,produce_factory,specification,drug_form,bar_code,create_time,update_time,remark,is_delete';
}
