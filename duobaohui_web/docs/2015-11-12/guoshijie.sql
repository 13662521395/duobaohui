ALTER TABLE `sh_duobaohui`.`sh_shop_tel`
ADD COLUMN `scene` VARCHAR(1) NOT NULL COMMENT '1：桌贴，2：桌立，3：易拉宝，4：厕所' AFTER `create_time`;
ALTER TABLE `sh_duobaohui`.`sh_shop_tel`
CHANGE COLUMN `scene` `scene` VARCHAR(1) NOT NULL DEFAULT '1' COMMENT '1：桌贴，2：桌立，3：易拉宝，4：厕所' ;


ALTER TABLE `sh_duobaohui`.`sh_shop_tel`
CHANGE COLUMN `sh_user_tel` `sh_user_tel` VARCHAR(24) NULL COMMENT '用户手机号' ,
ADD COLUMN `download_direct` VARCHAR(1) NOT NULL DEFAULT '1' COMMENT '是否直接下载：1：是    0：否' AFTER `scene`;

ALTER TABLE `sh_duobaohui`.`sh_shop_tel`
DROP INDEX `idx_userid_tel` ;
