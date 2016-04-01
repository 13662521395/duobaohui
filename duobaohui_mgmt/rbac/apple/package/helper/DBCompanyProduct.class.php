<?php 


 namespace Gate\Package\Helper;


	 class DBCompanyProduct  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_company_product'; 

		 const _FIELDS_		= 'id,company_id,product_id,user_id,sales,purchase,points,lowest,type,deduct_type,deduct,unit,create_time,update_time,update_uid,remark,is_delete';
}
