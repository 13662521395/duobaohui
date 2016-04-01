CREATE TABLE IF NOT EXISTS `sh_duobaohui`.`sh_autopay_main_setting` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(128) NOT NULL COMMENT '方案名称',
  `frequency` INT(11) NOT NULL COMMENT '频率',
  `flag` CHAR(1) NOT NULL DEFAULT '1' COMMENT '是否启用，0:启用，1:关闭',
  `scope_and_rate` VARCHAR(1024) NOT NULL COMMENT '购买人次范围及概率,如：1~10|80,5~50|5',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `modify_time` DATETIME NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '刷单方案表';

CREATE TABLE IF NOT EXISTS `sh_duobaohui`.`sh_autopay_activity_setting` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sh_autopay_main_setting_id` INT(11) NOT NULL COMMENT '刷单方案id',
  `sh_activity_id` INT(11) NOT NULL COMMENT '活动id',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '刷单方案对应活动表';
ALTER TABLE `sh_duobaohui`.`sh_autopay_activity_setting`
ADD UNIQUE INDEX `index2` (`sh_activity_id` ASC);

insert into sh_autopay_main_setting(name,frequency,flag,scope_and_rate,create_time,modify_time)
values('基本方案','10','0','1~10|80,5~50|5','2015-11-18','2015-11-18');
insert into sh_autopay_main_setting(name,frequency,flag,scope_and_rate,create_time,modify_time)
values('方案A','10','0','1~10|80,5~50|5','2015-11-18','2015-11-18');
insert into sh_autopay_main_setting(name,frequency,flag,scope_and_rate,create_time,modify_time)
values('方案B','10','0','1~10|80,5~50|5','2015-11-18','2015-11-18');
insert into sh_autopay_main_setting(name,frequency,flag,scope_and_rate,create_time,modify_time)
values('方案C','10','0','1~10|80,5~50|5','2015-11-18','2015-11-18');
insert into sh_autopay_main_setting(name,frequency,flag,scope_and_rate,create_time,modify_time)
values('方案D','10','0','1~10|80,5~50|5','2015-11-18','2015-11-18');



-- INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,pid_path) VALUES('刷单方案设置','autopay','10','0','0','0','1','');
-- UPDATE sh_rbac_node SET top_id='136' WHERE node_id='136';
-- insert into `sh_rbac_access` (role_id,node_id) values('1','136');
-- INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('刷单方案列表','/admin/setting/auto-pay-setting-list-pre','10','0','1','136','2','136','136');
-- UPDATE sh_rbac_node SET type='0' WHERE node_id='136';
-- insert into `sh_rbac_access` (role_id,node_id) values('1','138');
-- INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('刷单方案活动设置','/admin/setting/activity-setting-list-pre','10','0','2','136','2','136','136');
-- insert into `sh_rbac_access` (role_id,node_id) values('1','140');
-- INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('刷单方案活动添加','/admin/setting/add-activity-setting','10','0','2','136','2','136','136');
-- insert into `sh_rbac_access` (role_id,node_id) values('1','142');
-- INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('刷单方案详情','/admin/setting/auto-pay-setting-detail','10','0','2','136','2','136','136');
-- insert into `sh_rbac_access` (role_id,node_id) values('1','144');
-- INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('刷单方案更新','/admin/setting/update-auto-pay-setting','10','0','2','136','2','136','136');
-- insert into `sh_rbac_access` (role_id,node_id) values('1','145');
-- INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('刷单方案开启/关闭','/admin/setting/control-auto-pay-setting','10','0','2','136','2','136','136');
-- insert into `sh_rbac_access` (role_id,node_id) values('1','146');

