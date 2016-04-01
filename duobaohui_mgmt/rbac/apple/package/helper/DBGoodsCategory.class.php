<?php 


 namespace Gate\Package\Helper;


	 class DBGoodsCategory  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_goods_category'; 

		 const _FIELDS_		= 'id,name,type,pid,level,is_delete,sort,description,goods_num,top_id,is_end,path,update_time,create_time';
}
