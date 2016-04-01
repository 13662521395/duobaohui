<?php 


 namespace Gate\Package\Helper;


	 class DBAreaProvince  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_area_province'; 

		 const _FIELDS_		= 'id,name,division,pid,level,update_time,create_time,pid_path,is_delete,abbreviation';
}
