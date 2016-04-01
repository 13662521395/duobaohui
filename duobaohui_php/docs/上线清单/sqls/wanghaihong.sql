LTER TABLE `sh_duobaohui`.`sh_jnl_trans` 
ADD COLUMN `repacket_id` BIGINT(18) NULL DEFAULT NULL COMMENT '红包ID，用于关联红包信息,动帐后必须更新此值' AFTER `jnl_recharge_id`;

ALTER TABLE `sh_duobaohui`.`sh_red_money` 
CHANGE COLUMN `update_time` `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间' ;

ALTER TABLE `sh_duobaohui`.`sh_system_config` 
ADD COLUMN `alipay_show` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '支付宝支付显示' AFTER `ios_audit`,
ADD COLUMN `weixinpay_show` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '微信支付显示' AFTER `alipay_show`;

