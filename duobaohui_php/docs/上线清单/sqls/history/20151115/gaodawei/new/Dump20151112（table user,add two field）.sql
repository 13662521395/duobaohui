-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: 192.168.1.226    Database: sh_duobaohui
-- ------------------------------------------------------
-- Server version	5.1.73

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
-- Table structure for table `sh_user`
--

DROP TABLE IF EXISTS `sh_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sh_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `tel` varchar(24) NOT NULL COMMENT '用户手机号',
  `email` varchar(32) NOT NULL DEFAULT '' COMMENT '邮箱',
  `real_name` varchar(32) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `nick_name` varchar(32) NOT NULL COMMENT '用户昵称',
  `password` varchar(40) NOT NULL COMMENT '用户密码',
  `locked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '冻结账户登陆：0-不冻结，1-冻结',
  `sh_id` varchar(32) NOT NULL COMMENT '世和id',
  `signature` varchar(64) NOT NULL DEFAULT '' COMMENT '个性签名',
  `salt` varchar(16) NOT NULL DEFAULT '' COMMENT '密码加密随机生成的字符串',
  `create_time` int(10) NOT NULL COMMENT '注册时间',
  `token` varchar(50) NOT NULL DEFAULT '' COMMENT '帐号激活码',
  `token_exptime` int(10) NOT NULL COMMENT '激活码有效期',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0-未激活，1-已激活',
  `is_delete` tinyint(4) NOT NULL,
  `head_pic` varchar(255) NOT NULL DEFAULT '',
  `money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `is_real` int(11) NOT NULL DEFAULT '1' COMMENT '是否真实用户默认为1,1：真实用户；0:假用户',
  `login_sms_code` varchar(6) NOT NULL DEFAULT '',
  `login_sms_code_expire` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85882 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-12 17:28:30
