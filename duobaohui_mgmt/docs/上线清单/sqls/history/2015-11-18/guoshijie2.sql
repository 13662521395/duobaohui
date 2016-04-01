CREATE TABLE `sh_duobaohui`.`sh_shop_share` (
  `sh_shop_id` INT UNSIGNED NOT NULL COMMENT '商家ID',
  `scan_num` INT NOT NULL DEFAULT 0 COMMENT '扫码总数',
  `real_num` INT NOT NULL DEFAULT 0 COMMENT '注册用户',
  `percent` DECIMAL(12,4) NOT NULL DEFAULT 0 COMMENT '用户转化率',
  `scene_one` INT NOT NULL DEFAULT 0 COMMENT '场景1：桌贴',
  `scene_two` INT NOT NULL DEFAULT 0 COMMENT '场景2：桌立',
  `scene_three` INT NOT NULL DEFAULT 0 COMMENT '场景三：易拉宝',
  `scene_four` INT NOT NULL DEFAULT 0 COMMENT '场景四：厕所');

ALTER TABLE `sh_duobaohui`.`sh_shop_share`
ADD COLUMN `insert_date` DATETIME NOT NULL AFTER `scene_four`;

-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: shihe137.aliyun.server.com    Database: sh_duobaohui
-- ------------------------------------------------------
-- Server version	5.5.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sh_rbac_access`
--

DROP TABLE IF EXISTS `sh_rbac_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sh_rbac_access` (
  `role_id` smallint(6) unsigned NOT NULL,
  `node_id` smallint(6) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sh_rbac_access`
--

LOCK TABLES `sh_rbac_access` WRITE;
/*!40000 ALTER TABLE `sh_rbac_access` DISABLE KEYS */;
INSERT INTO `sh_rbac_access` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,7),(1,8),(1,12),(1,42),(1,43),(1,44),(1,45),(1,46),(1,47),(1,48),(1,50),(1,51),(1,52),(1,53),(1,54),(1,55),(1,56),(1,57),(1,59),(1,61),(1,62),(1,82),(1,89),(1,97),(1,99),(1,100),(1,102),(1,103),(1,104),(1,106),(1,108),(1,111),(1,112),(1,113),(1,114),(1,115),(1,116),(1,117),(1,120),(1,121),(1,122),(1,123),(1,124),(1,125),(1,126),(1,127),(1,128),(1,129),(1,130),(1,131),(1,132),(1,133),(1,134),(1,135);
/*!40000 ALTER TABLE `sh_rbac_access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sh_rbac_node`
--

DROP TABLE IF EXISTS `sh_rbac_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sh_rbac_node` (
  `node_id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `action` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `remark` varchar(255) NOT NULL,
  `sort` smallint(6) unsigned NOT NULL,
  `pid` smallint(6) unsigned NOT NULL,
  `pid_path` varchar(255) NOT NULL COMMENT '完整路径',
  `level` tinyint(1) unsigned NOT NULL,
  `is_end` tinyint(1) NOT NULL,
  `top_id` int(10) unsigned NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '节点类型：目录，页面，页面模块',
  `url` varchar(255) NOT NULL COMMENT '页面url',
  `is_delete` tinyint(1) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sh_rbac_node`
--

LOCK TABLES `sh_rbac_node` WRITE;
/*!40000 ALTER TABLE `sh_rbac_node` DISABLE KEYS */;
INSERT INTO `sh_rbac_node` VALUES (1,'权限管理','rbac',0,'',100,0,'',1,0,1,0,'',0,'0000-00-00 00:00:00',0),(2,'权限组','/admin/rbac/action/role_list',0,'',50,1,'1',2,0,1,1,'',0,'0000-00-00 00:00:00',0),(3,'节点','/admin/rbac/action/node_list',0,'',100,1,'1',2,0,1,1,'',0,'0000-00-00 00:00:00',0),(4,'出入账','bill',0,'',11,0,'',1,0,4,0,'',0,'2014-09-23 01:54:32',0),(5,'统计','/admin/bill/index',0,'',50,4,'4',2,0,4,1,'',0,'2014-09-23 01:55:11',0),(7,'首页','/admin/index',0,'',10,0,'',1,0,7,0,'',0,'2014-09-23 01:59:55',0),(8,'焦点图','/admin/banner/index',0,'',50,7,'7',2,0,7,1,'',0,'2014-09-23 02:00:59',0),(12,'客户端','client',0,'',50,0,'',1,0,0,0,'',0,'2014-09-26 22:36:11',0),(42,'组用户','/admin/rbac/action/user_list',0,'',50,1,'1',2,0,1,1,'',0,'2014-12-15 08:12:08',0),(43,'add','/admin/rbac/action/node_add',0,'',0,3,'3',3,0,1,2,'',0,'2014-12-18 08:30:30',0),(44,'edit','/admin/rbac/action/node_edit',0,'',0,3,'3',3,0,1,2,'',0,'2014-12-18 08:52:52',0),(45,'add','/admin/banner/add',0,'',0,8,'7-8',3,0,7,2,'',0,'2014-12-19 03:11:37',0),(46,'edit','/admin/banner/edit',0,'',0,8,'7-8',3,0,7,2,'',0,'2014-12-19 03:12:02',0),(47,'支付记录','/admin/bill/pay-list',0,'',0,4,'4',2,0,4,1,'',0,'2014-12-19 03:13:05',0),(48,'支付宝账务明细异常','/admin/bill/alipay-account',0,'',0,4,'4',2,0,4,1,'',0,'2014-12-19 03:13:16',0),(50,'add','/admin/rbac/action/role_add',0,'',0,2,'2',3,0,1,2,'',0,'2014-12-19 03:14:41',0),(51,'edit','/admin/rbac/action/role_edit',0,'',0,2,'2',3,0,1,2,'',0,'2014-12-19 03:14:48',0),(52,'del','/admin/rbac/action/role_del',0,'',0,2,'2',3,0,1,2,'',0,'2014-12-19 03:14:57',0),(53,'del','/admin/rbac/action/node_del',0,'',0,3,'3',3,0,1,2,'',0,'2014-12-19 03:15:34',0),(54,'add','/admin/rbac/action/user_add',0,'',0,42,'1-42',3,0,1,2,'',0,'2014-12-19 03:16:53',0),(55,'edit','/admin/rbac/action/user_edit',0,'',0,42,'1-42',3,0,1,2,'',0,'2014-12-19 03:17:07',0),(56,'del','/admin/rbac/action/user_del',0,'',0,42,'1-42',3,0,1,2,'',0,'2014-12-19 03:17:21',0),(57,'活动','activitiy',0,'',10,0,'',1,0,57,0,'',0,'2014-12-29 04:19:30',0),(59,'添加活动','/admin/activity/add-activity',0,'',10,57,'57',2,0,57,1,'',0,'2014-12-29 04:30:12',0),(61,'添加商品','/admin/goods/add-goods',0,'',10,82,'82',2,0,82,1,'',0,'2014-12-29 04:30:47',0),(62,'商品列表','/admin/goods/list',0,'',10,82,'-82',2,0,82,1,'',0,'2014-12-29 06:14:21',0),(82,'商品','goods',0,'',10,0,'',1,0,82,0,'',0,'2015-01-05 10:06:12',0),(89,'add','/goods/product_order_add',0,'',0,61,'82-61',3,0,82,2,'',0,'2015-01-07 08:24:04',0),(97,'晒单','shaidan',0,'',10,0,'',1,0,97,0,'',0,'2015-01-12 10:11:41',0),(99,'晒单列表','/admin/shaidan/list',0,'',0,97,'97',2,0,97,1,'',0,'2015-01-12 10:13:10',0),(100,'虚拟用户晒单','/admin/shaidan/win-user-list',0,'',0,97,'97',2,0,97,1,'',0,'2015-01-12 10:13:50',0),(102,'add','/goods/purchase_add_test',0,'',0,59,'57-59',3,0,57,2,'',1,'2015-01-13 02:12:44',0),(103,'订单','order',0,'',10,0,'',1,0,103,0,'',0,'2015-01-13 06:39:42',0),(104,'订单列表','/admin/order/order-list',0,'',10,103,'103',2,0,103,1,'',0,'2015-01-13 06:40:17',0),(106,'发布新版本','/admin/client/release-new-version-pre',0,'',10,12,'12',2,0,0,1,'',0,'2015-01-14 09:03:19',0),(108,'活动列表','/admin/activity/list-pre',0,'',1,57,'57',2,0,57,1,'',0,'2015-01-22 02:07:37',0),(111,'客户端版本列表','/admin/client/version-list',0,'',10,12,'12',2,0,0,1,'',0,'2015-11-12 20:44:20',0),(112,'商户','shop',0,'',10,0,'',1,0,112,0,'',0,'2015-11-12 20:51:30',0),(113,'添加商户','/admin/shop/add-one-shop',0,'',10,112,'112',2,0,112,1,'',0,'2015-11-12 20:53:18',0),(114,'商户列表','/admin/shop/shop-list',0,'',10,112,'112',2,0,112,1,'',0,'2015-11-12 20:53:49',0),(115,'用户','user',0,'',10,0,'',1,0,115,0,'',0,'2015-11-12 20:54:37',0),(116,'用户列表','/admin/user/user-list',0,'',10,115,'115',2,0,115,1,'',0,'2015-11-12 20:55:10',0),(117,'用户反馈','/admin/opinion/feedback-list',0,'',10,115,'115',2,0,115,1,'',0,'2015-11-12 20:55:51',0),(120,'del','/admin/banner/del',0,'',10,8,'7-8',3,0,7,2,'',0,'2015-11-16 23:40:47',0),(121,'添加商户','/admin/shop/add-shop',0,'',10,113,'112-113',3,0,112,2,'',0,'2015-11-17 23:19:42',0),(122,'ajax获取upload_token','/admin/shaidan/js-qiniu-token',0,'',10,106,'12-106',3,0,0,2,'',0,'2015-11-17 23:20:40',0),(123,'编辑商户','/admin/shop/edit',0,'',10,114,'112-114',3,0,112,2,'',0,'2015-11-17 23:21:32',0),(124,'编辑商户提交','/admin/shop/edit-shop',0,'',10,114,'112-114',3,0,112,2,'',0,'2015-11-17 23:22:09',0),(125,'删除商户','/admin/shop/del',0,'',10,114,'112-114',3,0,112,2,'',0,'2015-11-17 23:22:51',0),(126,'商户推广统计','/admin/shop/shop-share',0,'',10,112,'112',2,0,112,1,'',0,'2015-11-17 23:23:30',0),(127,'消息推送','notify_push',0,'',10,0,'',1,0,127,0,'',0,'2015-11-17 23:24:21',0),(128,'消息推送','/admin/notify/client-notify-push-pre',0,'',10,127,'127',2,0,127,1,'',0,'2015-11-17 23:25:17',0),(129,'消息推送提交','/admin/notify/client-notify-push',0,'',10,128,'127-128',3,0,127,2,'',0,'2015-11-17 23:25:51',0),(130,'消息推送crontab定时任务','/admin/notify/send-app-notify',0,'',10,128,'127-128',3,0,127,2,'',0,'2015-11-17 23:26:23',0),(131,'中奖推送记录','/admin/notify/client-win-notify-list',0,'',10,127,'127',2,0,127,1,'',0,'2015-11-17 23:27:06',0),(132,'发布新版本提交','/admin/client/release-new-version',0,'',10,106,'12-106',3,0,0,2,'',0,'2015-11-17 23:28:05',0),(133,'退出','/admin/user/logout',0,'',10,0,'',1,0,133,2,'',0,'2015-11-18 11:30:32',0),(134,'订单详情','/admin/order/order-detail',0,'',10,104,'103-104',3,0,103,2,'',0,'2015-11-18 11:30:32',0),(135,'订单修改','/admin/order/order-modify',0,'',10,104,'103-104',3,0,103,2,'',0,'2015-11-18 11:30:32',0);
/*!40000 ALTER TABLE `sh_rbac_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sh_rbac_role`
--

DROP TABLE IF EXISTS `sh_rbac_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sh_rbac_role` (
  `role_id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `status` tinyint(1) unsigned NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` int(11) unsigned NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sh_rbac_role`
--

LOCK TABLES `sh_rbac_role` WRITE;
/*!40000 ALTER TABLE `sh_rbac_role` DISABLE KEYS */;
INSERT INTO `sh_rbac_role` VALUES (1,'超级管理员组',0,'2015-11-18 19:43:20',0);
/*!40000 ALTER TABLE `sh_rbac_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sh_rbac_role_user`
--

DROP TABLE IF EXISTS `sh_rbac_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sh_rbac_role_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sh_rbac_role_user`
--

LOCK TABLES `sh_rbac_role_user` WRITE;
/*!40000 ALTER TABLE `sh_rbac_role_user` DISABLE KEYS */;
INSERT INTO `sh_rbac_role_user` VALUES (1,1,1);
/*!40000 ALTER TABLE `sh_rbac_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sh_rbac_user`
--

DROP TABLE IF EXISTS `sh_rbac_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sh_rbac_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(32) NOT NULL,
  `nick_name` varchar(32) NOT NULL,
  `password` char(40) NOT NULL,
  `salt` char(6) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_name_UNIQUE` (`login_name`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sh_rbac_user`
--

LOCK TABLES `sh_rbac_user` WRITE;
/*!40000 ALTER TABLE `sh_rbac_user` DISABLE KEYS */;
INSERT INTO `sh_rbac_user` VALUES (1,'admin','管理员','b09dcc095e6c7953465547edcffc235ac02509a9','d$1Ms9','2015-11-16 13:47:51','2015-11-18 11:41:31');
/*!40000 ALTER TABLE `sh_rbac_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-18 19:52:14

ALTER TABLE `sh_duobaohui`.`sh_shop_share`
CHANGE COLUMN `scan_num` `scan_num` INT(11) NOT NULL DEFAULT '0' COMMENT '领红包人数' ,
ADD COLUMN `scan_sum` INT NOT NULL DEFAULT 0 COMMENT '扫码人数' AFTER `sh_shop_id`;

ALTER TABLE `sh_duobaohui`.`sh_shop_share`
CHANGE COLUMN `percent` `percent` DECIMAL(12,4) NOT NULL DEFAULT '0.0000' COMMENT '用户转化率(注册人数real_num/领红包人数scan_num)' ,
CHANGE COLUMN `scene_one` `scene_one` INT(11) NOT NULL DEFAULT '0' COMMENT '场景1：桌贴  领红包人数' ,
CHANGE COLUMN `scene_two` `scene_two` INT(11) NOT NULL DEFAULT '0' COMMENT '场景2：桌立 领红包人数' ,
CHANGE COLUMN `scene_three` `scene_three` INT(11) NOT NULL DEFAULT '0' COMMENT '场景三：易拉宝 领红包人数' ,
CHANGE COLUMN `scene_four` `scene_four` INT(11) NOT NULL DEFAULT '0' COMMENT '场景四：厕所 领红包人数' ,
ADD COLUMN `scene_one_scan` INT NOT NULL DEFAULT 0 COMMENT '场景1：桌贴  扫码人数' AFTER `insert_date`,
ADD COLUMN `scene_two_scan` INT NOT NULL DEFAULT 0 COMMENT '场景2：桌立 扫码人数' AFTER `scene_one_scan`,
ADD COLUMN `scene_three_scan` INT NOT NULL DEFAULT 0 COMMENT '场景三：易拉宝 扫码人数' AFTER `scene_two_scan`,
ADD COLUMN `scene_four_scan` INT NOT NULL DEFAULT 0 COMMENT '场景四：厕所 扫码人数' AFTER `scene_three_scan`,
ADD COLUMN `red_packet_percent` DECIMAL(12,4) NOT NULL DEFAULT 0 COMMENT '领红包率（领红包人数scan_num/扫码人数scan_sum）' AFTER `scene_four_scan`;

