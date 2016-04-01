<?php 


 namespace Gate\Package\Helper;


	 class DBUserProfile  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_user_profile'; 

		 const _FIELDS_		= 'user_id,company_id,nickname,realname,password,cookie,is_actived,invite_code,active_code,salt,email,mobile,status,is_delete,grade,description,identity,last_login_time,last_login_ip,last_comment_time,last_add_article_time,daily_add_article,create_time,avatar_c,avatar_width,avatar_height,is_recommend';
}
