<?php 


 namespace Gate\Package\Helper;


	 class DBProductMedicinal  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_product_medicinal'; 

		 const _FIELDS_		= 'product_id,sn,name,name_en,drug_form,specification,produce_factory,produce_address,type,category,self_code,bar_code';
}
