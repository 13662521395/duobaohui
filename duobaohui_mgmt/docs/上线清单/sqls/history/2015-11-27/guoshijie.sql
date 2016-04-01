CREATE TABLE `sh_duobaohui`.`sh_shop_share_history` (
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  `date` VARCHAR(24) NOT NULL COMMENT '日期',
  `avg_customer_sum` INT NOT NULL DEFAULT 0 COMMENT '日均客流量',
  `scan_sum` INT NOT NULL DEFAULT 0 COMMENT '扫码人数',
  `percent_scan` VARCHAR(45) NOT NULL DEFAULT '0.00%' COMMENT '扫码率',
  `real_sum` INT NOT NULL DEFAULT 0 COMMENT '注册用户',
  `percent` VARCHAR(45) NOT NULL DEFAULT '0.00%' COMMENT '用户转化率',
  `scene_one_sum` INT NOT NULL DEFAULT 0 COMMENT '桌贴描码人数',
  `scene_two_sum` INT NOT NULL DEFAULT 0 COMMENT '桌立描码人数',
  `scene_three_sum` INT NOT NULL DEFAULT 0 COMMENT '易拉宝描码人数',
  `scene_four_sum` INT NOT NULL DEFAULT 0 COMMENT '厕所描码人数')
COMMENT = '每天商户推广总计表';

ALTER TABLE `sh_duobaohui`.`sh_red_packet`
ADD COLUMN `os_type` TINYINT(1) NOT NULL DEFAULT 3 COMMENT '系统类型    1：android   2:ios  3: 未知' AFTER `flag`;

CREATE TABLE `sh_duobaohui`.`sh_shop_scan` (
  `sh_shop_id` INT NOT NULL COMMENT '商家id',
  `scene` VARCHAR(1) NOT NULL COMMENT '场景：1：桌贴，2：桌立，3：易拉宝，4：厕所',
  `create_time` DATETIME NOT NULL,
  `os_type` TINYINT(1) NOT NULL COMMENT '系统类型    1：android   2:ios  3: 未知');


INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('商户推广注册用户条形表','/admin/shop/shop-share-real-sum','10','0','2','126','3','112','112-126');
insert into `sh_rbac_access` (role_id,node_id) values('1','176'),('2','176');

INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('商户用户转化率topN','/admin/shop/shop-share-percent','10','0','2','126','3','112','112-126');
insert into `sh_rbac_access` (role_id,node_id) values('1','180'),('2','180');

INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('操作系统占比','/admin/shop/os-type-percent','10','0','2','143','3','112','112-143');
insert into `sh_rbac_access` (role_id,node_id) values('1','181'),('2','181');

INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('领红包人群手机OS占比','/admin/shop/all-os-type-percent','10','0','2','126','3','112','112-126');
insert into `sh_rbac_access` (role_id,node_id) values('1','182'),('2','182');

CREATE TABLE `sh_duobaohui`.`sh_report` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

INSERT INTO `sh_report` (`id`,`content`) VALUES (1,'暴力色情');
INSERT INTO `sh_report` (`id`,`content`) VALUES (2,'谣言欺诈');
INSERT INTO `sh_report` (`id`,`content`) VALUES (3,'反动言论');
INSERT INTO `sh_report` (`id`,`content`) VALUES (4,'恶意辱骂');
INSERT INTO `sh_report` (`id`,`content`) VALUES (5,'侵权违规');

CREATE TABLE `sh_duobaohui`.`sh_report_shaidan` (
  `sh_shaidan_id` INT NOT NULL COMMENT '晒单id',
  `sh_report_id` INT NOT NULL COMMENT '举报详情id',
  `report_num` INT NOT NULL DEFAULT 0 COMMENT '已举报次数',
  `create_time` DATETIME NOT NULL COMMENT '创建日期',
  `update_time` DATETIME NOT NULL COMMENT '更新日期');

ALTER TABLE `sh_duobaohui`.`sh_report_shaidan`
ADD COLUMN `report_user_id` INT UNSIGNED NULL COMMENT '举报人id' AFTER `update_time`;
ALTER TABLE `sh_duobaohui`.`sh_report_shaidan`
CHANGE COLUMN `report_user_id` `report_user_id` INT(10) NULL DEFAULT NULL COMMENT '举报人id' ;

CREATE TABLE `sh_duobaohui`.`sh_system_notice` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '通知id',
  `create_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL,
  `title` VARCHAR(45) NOT NULL COMMENT '通知标题',
  `content` VARCHAR(1024) NOT NULL COMMENT '通知内容',
  `user_id` INT NULL COMMENT '发布人ID',
  PRIMARY KEY (`id`));
ALTER TABLE `sh_duobaohui`.`sh_system_notice`
ADD COLUMN `status` VARCHAR(1) NOT NULL DEFAULT '1' COMMENT '状态：1：可用，0：禁用' AFTER `user_id`;