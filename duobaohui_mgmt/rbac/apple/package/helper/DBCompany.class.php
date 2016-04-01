<?php 


 namespace Gate\Package\Helper;


	 class DBCompany  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_company'; 

		 const _FIELDS_		= 'id,uid,company,name,tel,fax,shouji,site,email,pc,address,address_path,beizhu,create_time,update_time,replace_uid,is_delete';
}
