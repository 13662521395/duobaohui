ALTER TABLE `sh_duobaohui`.`sh_user`
ADD COLUMN `os_type` VARCHAR(10) NOT NULL DEFAULT '10' COMMENT '第一位代表android,第二位代表ios, (1:登陆过  0:从未登陆)' AFTER `set_cookie`;



