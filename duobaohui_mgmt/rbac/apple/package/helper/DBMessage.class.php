<?php 


 namespace Gate\Package\Helper;


	 class DBMessage  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_message'; 

		 const _FIELDS_		= 'msg_id,user_id_from,user_id_to,replay_msg_id,content,is_read,is_delete,send_time,read_time';
}
