ALTER TABLE `sh_duobaohui`.`sh_order_info`
ADD COLUMN `receive_time` DATETIME NULL DEFAULT NULL COMMENT '收货时间' AFTER `shipping_time`;
update sh_order_info set shipping_time = null;
ALTER TABLE `sh_duobaohui`.`sh_order_info`
CHANGE COLUMN `shipping_time` `shipping_time` DATETIME NULL COMMENT '订单配送时间' ;