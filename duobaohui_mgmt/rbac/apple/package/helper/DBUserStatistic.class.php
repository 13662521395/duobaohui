<?php 


 namespace Gate\Package\Helper;


	 class DBUserStatistic  extends \Phplib\DB\DBModel { 

		 const _DATABASE_	= 'sh_duobaohui'; 

		 const _TABLE_		= 'ch_user_statistic'; 

		 const _FIELDS_		= 'user_id,follow_num,fans_num,like_article_num,article_num,article_num_check,article_num_in,collect_topic_num,comment_num';
}
