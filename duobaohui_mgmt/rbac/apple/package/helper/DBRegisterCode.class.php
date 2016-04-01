<?php 


 namespace Gate\Package\Helper;


	 class DBRegisterCode  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_register_code'; 

		 const _FIELDS_		= 'mobile,code,expire,status';
}
