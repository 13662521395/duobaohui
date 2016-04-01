ALTER TABLE `sh_duobaohui`.`sh_shop_share_history`
CHANGE COLUMN `scan_sum` `red_packet_sum` INT(11) NOT NULL DEFAULT '0' COMMENT '扫码人数' ;

ALTER TABLE `sh_duobaohui`.`sh_shop_share_history`
CHANGE COLUMN `red_packet_sum` `red_packet_sum` INT(11) NOT NULL DEFAULT '0' COMMENT '领取红包人数' ,
ADD COLUMN `scan_sum` INT NOT NULL DEFAULT 0 COMMENT '扫码人数' AFTER `avg_customer_sum`;

ALTER TABLE `sh_duobaohui`.`sh_shop_share_history`
CHANGE COLUMN `scene_one_sum` `scene_one_sum` INT(11) NOT NULL DEFAULT '0' COMMENT '桌贴领红包人数' ,
ADD COLUMN `scene_one_scan` INT NOT NULL DEFAULT 0 COMMENT '桌贴描码人数' AFTER `scene_four_sum`,
ADD COLUMN `scene_two_scan` INT NOT NULL DEFAULT 0 AFTER `scene_one_scan`,
ADD COLUMN `scene_three_scan` INT NOT NULL DEFAULT 0 AFTER `scene_two_scan`,
ADD COLUMN `scene_four_scan` INT NOT NULL DEFAULT 0 AFTER `scene_three_scan`;

INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path,remark,`is_end`,`url`,`is_delete`,update_time) VALUES('扫码人群手机系统占比','/admin/shop/scan-os-type-percent','10','0','2','126','3','112','112-126','',0,'',0,0);
INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path,remark,`is_end`,`url`,`is_delete`,update_time) VALUES('每天扫码人群手机操作系统占比','/admin/shop/scan-os-type-percent-by-date','10','0','2','143','3','112','112-143','',0,'',0,0);

INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('邀请好友总计','/admin/userFriends/user-friends-num-list','10','0','1','115','2','115','115');
INSERT INTO sh_rbac_node(name,action,sort,status,type,pid,level,top_id,pid_path) VALUES('每日邀请','/admin//user-friends-num-by-date','10','0','1','115','2','115','115');
UPDATE sh_rbac_node SET node_id='186',name='每日邀请',action='/admin/userFriends/user-friends-num-by-date',sort='10',status='0',type='1',pid='115' WHERE node_id='186';

ALTER TABLE `sh_duobaohui`.`sh_shop_share`
ADD COLUMN `days` INT(11) NOT NULL DEFAULT 0 COMMENT '累计推广天数' AFTER `sh_shop_id`,
ADD COLUMN `avg_real_num` INT(11) NOT NULL DEFAULT 0 COMMENT '平均每天注册人数' AFTER `real_num`;

ALTER TABLE `sh_duobaohui`.`sh_shop_share_history`
ADD COLUMN `avg_scan_sum` INT(11) NOT NULL DEFAULT 0 AFTER `avg_customer_sum`,
ADD COLUMN `avg_real_sum` INT(11) NOT NULL DEFAULT 0 AFTER `percent_scan`;
