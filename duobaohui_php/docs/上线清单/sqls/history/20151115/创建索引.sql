ALTER TABLE `sh_activity_code` ADD INDEX sh_activity_code_activityPeriodId ( `activity_period_id` );
ALTER TABLE `sh_activity_code` ADD INDEX sh_activity_code_userId ( `user_id` );
ALTER TABLE `sh_activity_code` ADD INDEX sh_activity_code_buyId ( `buy_id` );

ALTER TABLE `sh_period_user` ADD INDEX sh_period_user_shActivityPeriodId ( `sh_activity_period_id` );
ALTER TABLE `sh_period_user` ADD INDEX sh_period_user_userId ( `user_id` );
ALTER TABLE `sh_period_user` ADD INDEX sh_period_user_buyId ( `buy_id` );
