<?php 


 namespace Gate\Package\Helper;


	 class DBGoods  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_goods'; 

		 const _FIELDS_		= 'id,goods_title,show_time,show_address,daiding_time,show_author,category_id,description,goods_pic_url,goods_type,bought_times,is_delete,create_time,is_hot,is_new,start_time,end_time,sort,status,price,area_id';
}
