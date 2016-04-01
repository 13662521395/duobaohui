<?php 


 namespace Gate\Package\Helper;


	 class DBUserIdentity  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_user_identity'; 

		 const _FIELDS_		= 'id,identity,rbac_access';
}
