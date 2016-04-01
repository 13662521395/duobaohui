ALTER TABLE `sh_duobaohui`.`sh_banner` 
CHANGE COLUMN `type` `type` TINYINT(4) NOT NULL COMMENT '0: 移动端跳转，1：web页面跳转，2：欢迎页大图, 3: 移动端跳转充值页, 4:充值页面图片' ;

ALTER TABLE `sh_duobaohui`.`sh_buy_order` 
CHANGE COLUMN `create_time` `create_time` DATETIME NOT NULL ;

CREATE TABLE IF NOT EXISTS `sh_duobaohui`.`sh_jnl_sign` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '用户ID',
  `create_time` DATETIME NOT NULL COMMENT '签到时间',
  `ip` VARCHAR(45) NOT NULL,
  `os_type` VARCHAR(2) NOT NULL,
  `cookie` VARCHAR(128) NOT NULL,
  `continue_num` INT(11) NOT NULL COMMENT '连续签到天数',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 56
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '签到表';

ALTER TABLE `sh_duobaohui`.`sh_red_money` 
DROP COLUMN `creat_time`,
CHANGE COLUMN `issue_time` `issue_time` DATETIME NULL DEFAULT NULL COMMENT '发放时间' ,
CHANGE COLUMN `receive_time` `receive_time` DATETIME NULL DEFAULT NULL COMMENT '领取时间' ,
CHANGE COLUMN `use_time` `use_time` DATETIME NULL DEFAULT NULL COMMENT '使用时间' ,
CHANGE COLUMN `overdue_time` `overdue_time` DATETIME NULL DEFAULT NULL COMMENT '过期时间' ,
CHANGE COLUMN `update_time` `update_time` DATETIME NULL DEFAULT NULL COMMENT '发放时间' ,
ADD COLUMN `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间' AFTER `overdue_time`;

ALTER TABLE `sh_duobaohui`.`sh_red_money_batch` 
DROP COLUMN `creat_time`,
CHANGE COLUMN `recharge` `recharge` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '充值送,0否，1为充30，2为充50，3为充100，其他' ,
CHANGE COLUMN `sign` `sign` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '签到连续,0否，1是5天，2为10天，其他' ,
CHANGE COLUMN `buy` `buy` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '累计购买次数送,0否，1为100，2为150，其他' ,
CHANGE COLUMN `new_user` `new_user` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '新用户领取,0否，1是' ,
CHANGE COLUMN `inviter` `inviter` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '邀请者奖励,0否，1是' ,
CHANGE COLUMN `is_delete` `is_delete` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '0：不取消，1：取消' ,
ADD COLUMN `create_time` DATETIME NOT NULL COMMENT '活动创建时间' AFTER `end_date`,
ADD COLUMN `is_start` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '0为不启用，1为启用' AFTER `create_time`;

ALTER TABLE `sh_duobaohui`.`sh_red_money_price` 
ADD COLUMN `end_num` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '剩余数量' AFTER `num`;

CREATE TABLE IF NOT EXISTS `sh_duobaohui`.`sh_searchword` (
  `word` VARCHAR(8) NOT NULL COMMENT '关键词',
  `num` VARCHAR(45) NULL DEFAULT NULL COMMENT '搜索次数',
  PRIMARY KEY (`word`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '用户搜索的关键词';

ALTER TABLE `sh_duobaohui`.`sh_shop_scan` 
DROP COLUMN `sh_ship_sub_id`,
CHANGE COLUMN `cookie` `cookie` VARCHAR(63) NOT NULL COMMENT 'cookie' ,
CHANGE COLUMN `user_agent` `user_agent` VARCHAR(64) NOT NULL ,
CHANGE COLUMN `user_ip` `user_ip` VARCHAR(64) NOT NULL DEFAULT '' ,
ADD COLUMN `sh_shop_sub_id` INT(11) NOT NULL DEFAULT '0' AFTER `sh_shop_id`, 
COMMENT = '扫码' ;

CREATE TABLE IF NOT EXISTS `sh_duobaohui`.`sh_system_config` (
  `ios_audit` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'iOS 审核开关',
  PRIMARY KEY (`ios_audit`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '系统设置';

ALTER TABLE `sh_duobaohui`.`sh_user` 
ADD COLUMN `session_id` CHAR(40) NOT NULL DEFAULT '' COMMENT '单点登录验证session id' AFTER `os_type`;

insert INTo  sh_duobaohui.sh_system_config values(0);

