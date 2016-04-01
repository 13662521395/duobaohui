<?php 


 namespace Gate\Package\Helper;


	 class DBGoodsInShopInfo  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_goods_in_shop_info'; 

		 const _FIELDS_		= 'id,shop_id,goods_id,price,num,sale_num,show_time,status,remarks';
}
