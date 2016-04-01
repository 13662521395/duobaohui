ALTER TABLE `sh_duobaohui`.`sh_shop_share`
ADD COLUMN `duobao_sum` INT NOT NULL DEFAULT 0 COMMENT '共计夺宝(人次)' AFTER `red_packet_percent`;

ALTER TABLE `sh_duobaohui`.`sh_shop_share_history`
ADD COLUMN `duobao_sum` INT NOT NULL DEFAULT 0 AFTER `scene_four_scan`;

ALTER TABLE `sh_duobaohui`.`sh_shop`
ADD COLUMN `duobao_sum` INT NOT NULL DEFAULT 0 COMMENT '总计夺宝人次 ' AFTER `enabled`;


USE `sh_duobaohui`;

DELIMITER $$

DROP TRIGGER IF EXISTS sh_duobaohui.sh_jnl_deduct_AFTER_INSERT$$
USE `sh_duobaohui`$$
CREATE DEFINER = CURRENT_USER TRIGGER `sh_duobaohui`.`sh_jnl_deduct_AFTER_INSERT` AFTER INSERT ON `sh_jnl_deduct` FOR EACH ROW
BEGIN
	update sh_shop set duobao_sum = duobao_sum+new.amount where id = (
		select sh_shop_id from sh_shop_tel a inner join sh_user b on a.sh_user_tel = b.tel where b.id = new.user_id
    );
END
$$
DELIMITER ;





