<?php 


 namespace Gate\Package\Helper;


	 class DBUserProfileOld  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_user_profile_old'; 

		 const _FIELDS_		= 'user_id,nickname,realname,password,salt,mobile,email,is_delete,create_time,is_check,no_pass_reason,avatar_c,avatar_width,avatar_height';
}
