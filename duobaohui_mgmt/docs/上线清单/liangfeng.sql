CREATE TABLE `sh_pay_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `tel` varchar(24) NOT NULL COMMENT '用户手机号',
  `trans_jnl_no` varchar(64) NOT NULL DEFAULT '' COMMENT '对应得交易流水ID',
  `trans_code` varchar(45) DEFAULT '0' COMMENT '用于表示此交易类型的字符串',
  `jnl_status` varchar(10) NOT NULL DEFAULT '0' COMMENT '状态.0=新建，1=付款成功，2=付款失败，3=超时未处理，4=交易完成,其他待扩展',
  `pay_type` varchar(10) NOT NULL DEFAULT '' COMMENT '支付类型,0=余额，1=充值，其他待扩展',
  `amount` double NOT NULL COMMENT '交易总金额',
  `create_time` datetime NOT NULL,
  `recharge_channel` varchar(6) NOT NULL DEFAULT '0' COMMENT '充值渠道，0=支付宝，1=微信，2=红包,其他待扩展',
  `duobao_id` int(11) NOT NULL DEFAULT '0' COMMENT '夺宝id',
  `bill_id` int(11) NOT NULL DEFAULT '0' COMMENT '异常id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1274 DEFAULT CHARSET=utf8 COMMENT='交易记录临时表';


# ************************************************************
# Sequel Pro SQL dump
# Version 4135
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 121.42.175.165 (MySQL 5.7.10-log)
# Database: ok_red_money
# Generation Time: 2015-12-18 08:40:06 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;



# Dump of table sh_red_money
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sh_red_money`;

CREATE TABLE `sh_red_money` (
  `id` bigint(18) unsigned NOT NULL COMMENT '红包编号',
  `sh_red_money_batch_id` bigint(16) unsigned NOT NULL DEFAULT '0' COMMENT '红包批次表ID',
  `sh_red_money_price_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '批次单价表ID',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0未发放，1已发放',
  `issue_time` datetime DEFAULT NULL COMMENT '发放时间',
  `sh_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `is_new_red_money` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0新红包，1旧红包',
  `is_receive` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0未领取，1已领取',
  `is_use` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0未使用，1已使用',
  `is_overdue` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0未过期，1已过期',
  `receive_time` datetime DEFAULT NULL COMMENT '领取时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `overdue_time` datetime DEFAULT NULL COMMENT '过期时间',
  `creat_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包详情表';

LOCK TABLES `sh_red_money` WRITE;
/*!40000 ALTER TABLE `sh_red_money` DISABLE KEYS */;

INSERT INTO `sh_red_money` (`id`, `sh_red_money_batch_id`, `sh_red_money_price_id`, `status`, `issue_time`, `sh_user_id`, `is_new_red_money`, `is_receive`, `is_use`, `is_overdue`, `receive_time`, `use_time`, `overdue_time`, `creat_time`, `update_time`)
VALUES
  (201512180001,201512180001,1,0,NULL,0,0,0,0,0,NULL,NULL,NULL,'2015-12-18 14:29:10',NULL),
  (201512180002,201512180001,2,0,NULL,0,0,0,0,0,NULL,NULL,NULL,'2015-12-18 14:29:10',NULL),
  (201512180003,201512180001,3,0,NULL,0,0,0,0,0,NULL,NULL,NULL,'2015-12-18 14:29:10',NULL),
  (201512180004,201512180001,1,0,'2015-12-18 14:31:10',39193,1,0,0,0,NULL,NULL,'2015-12-20 14:29:10','2015-12-18 14:29:10','2015-12-18 14:31:10');

/*!40000 ALTER TABLE `sh_red_money` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sh_red_money_activity
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sh_red_money_activity`;

CREATE TABLE `sh_red_money_activity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sh_red_money_batch_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '红包批次表ID',
  `sh_activity_id` int(11) NOT NULL DEFAULT '0' COMMENT '活动表ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='批次与活动关系表';

LOCK TABLES `sh_red_money_activity` WRITE;
/*!40000 ALTER TABLE `sh_red_money_activity` DISABLE KEYS */;

INSERT INTO `sh_red_money_activity` (`id`, `sh_red_money_batch_id`, `sh_activity_id`)
VALUES
  (1,201512180001,100),
  (2,201512180001,102),
  (3,201512180001,103);

/*!40000 ALTER TABLE `sh_red_money_activity` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sh_red_money_batch
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sh_red_money_batch`;

CREATE TABLE `sh_red_money_batch` (
  `id` bigint(16) unsigned NOT NULL,
  `red_money_name` varchar(40) NOT NULL COMMENT '红包名称',
  `is_auto` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0：自动，1：手动',
  `recharge` tinyint(1) DEFAULT '0' COMMENT '充值送,0否，1为充30，2为充50，3为充100，其他',
  `sign` tinyint(1) DEFAULT '0' COMMENT '签到连续,0否，1是5天，2为10天，其他',
  `buy` tinyint(1) DEFAULT '0' COMMENT '累计购买次数送,0否，1为100，2为150，其他',
  `new_user` tinyint(1) DEFAULT '0' COMMENT '新用户领取,0否，1是',
  `inviter` tinyint(1) DEFAULT '0' COMMENT '邀请者奖励,0否，1是',
  `num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '数量',
  `end_num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '剩余数量',
  `total_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '总价',
  `consumption` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '消费限制，0.00为无限制',
  `admin_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后操作人',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0：取消，1：不取消',
  `begin_date` datetime DEFAULT NULL COMMENT '活动开始日期',
  `end_date` datetime DEFAULT NULL COMMENT '活动截止日期',
  `creat_time` datetime DEFAULT NULL COMMENT '活动创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包批次表';

LOCK TABLES `sh_red_money_batch` WRITE;
/*!40000 ALTER TABLE `sh_red_money_batch` DISABLE KEYS */;

INSERT INTO `sh_red_money_batch` (`id`, `red_money_name`, `is_auto`, `recharge`, `sign`, `buy`, `new_user`, `inviter`, `num`, `end_num`, `total_price`, `consumption`, `admin_id`, `is_delete`, `begin_date`, `end_date`, `creat_time`)
VALUES
  (201512180001,'充值送红包',0,1,0,0,0,0,1700,1699,2600.00,0.00,1,0,'2015-12-18 14:29:10','2015-12-20 14:29:10','2015-12-18 14:12:10');

/*!40000 ALTER TABLE `sh_red_money_batch` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sh_red_money_price
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sh_red_money_price`;

CREATE TABLE `sh_red_money_price` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sh_red_money_batch_id` bigint(16) NOT NULL DEFAULT '0' COMMENT '红包批次表ID',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `num` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='批次与单价关系表';

LOCK TABLES `sh_red_money_price` WRITE;
/*!40000 ALTER TABLE `sh_red_money_price` DISABLE KEYS */;

INSERT INTO `sh_red_money_price` (`id`, `sh_red_money_batch_id`, `price`, `num`)
VALUES
  (1,201512180001,1.00,1000),
  (2,201512180001,2.00,500),
  (3,201512180001,3.00,200);

/*!40000 ALTER TABLE `sh_red_money_price` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
