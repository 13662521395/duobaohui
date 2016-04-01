ALTER TABLE `sh_duobaohui`.`sh_category`  CHANGE COLUMN `create_time` `create_time` DATETIME NOT NULL COMMENT '分类创建时间' ;

ALTER TABLE `sh_duobaohui`.`sh_category` CHANGE COLUMN `description` `description` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '描述' ;

UPDATE `sh_duobaohui`.`sh_category` SET `level`='1' WHERE `id`<'8';

UPDATE `sh_duobaohui`.`sh_category` SET `top_id`='2',`is_end`='1' WHERE `id`='2';
UPDATE `sh_duobaohui`.`sh_category` SET `top_id`='3',`is_end`='1' WHERE `id`='3';
UPDATE `sh_duobaohui`.`sh_category` SET `top_id`='4',`is_end`='1' WHERE `id`='4';
UPDATE `sh_duobaohui`.`sh_category` SET `top_id`='5',`is_end`='1' WHERE `id`='5';
UPDATE `sh_duobaohui`.`sh_category` SET `top_id`='6',`is_end`='1' WHERE `id`='6';
UPDATE `sh_duobaohui`.`sh_category` SET `top_id`='7',`is_end`='1' WHERE `id`='7';
