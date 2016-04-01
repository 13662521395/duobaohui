ALTER TABLE `sh_duobaohui`.`sh_jnl_trans`
CHANGE COLUMN `recharge_channel` `recharge_channel` VARCHAR(6) NULL DEFAULT NULL COMMENT '充值渠道，0=支付宝，1=微信，2=红包，其他待扩展' ;

ALTER TABLE `sh_duobaohui`.`sh_jnl_recharge`
CHANGE COLUMN `recharge_channel` `recharge_channel` VARCHAR(6) NULL DEFAULT NULL COMMENT '充值渠道，0=支付宝，1=微信，2=红包,其他待扩展' ;

CREATE TABLE `sh_ip_factory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) NOT NULL,
  `country` varchar(20) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `county` varchar(20) DEFAULT NULL,
  `sp` varchar(6) DEFAULT NULL,
  `remark` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ipfactory_1` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

ALTER TABLE `sh_duobaohui`.`sh_period_user`
ADD COLUMN `ip` VARCHAR(45) NULL COMMENT 'ip地址' AFTER `is_pay`,
ADD COLUMN `ip_address` VARCHAR(45) NULL COMMENT 'ip对应的地理信息' AFTER `ip`;


