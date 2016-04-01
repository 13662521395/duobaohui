#彩票API
ALTER TABLE `sh_duobaohui`.`sh_lottery_shishi` 
ADD COLUMN `create_time` DATETIME COMMENT '创建时间' AFTER `lottery_code`,
ADD COLUMN `source` CHAR(1) NOT NULL DEFAULT '1' COMMENT '来源：1:百度，2:彩票控' AFTER `create_time`;

ALTER TABLE `sh_duobaohui`.`sh_lottery_shishi` 
ADD COLUMN `real_open_time` DATETIME NULL DEFAULT NULL COMMENT '真正开奖时间' AFTER `source`;
ALTER TABLE `sh_duobaohui`.`sh_lottery_shishi` 
CHANGE COLUMN `real_open_time` `real_open_time` DATETIME NULL DEFAULT NULL COMMENT '真正开奖时间' AFTER `open_time_stamp`,
CHANGE COLUMN `source` `source` CHAR(1) NOT NULL DEFAULT '1' COMMENT '来源：1:百度，2:彩票控' AFTER `lottery_code`;
