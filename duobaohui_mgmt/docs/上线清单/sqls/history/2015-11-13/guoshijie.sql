CREATE TABLE `sh_duobaohui`.`sh_jnl_order_sms` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` MEDIUMINT(8) NOT NULL COMMENT '订单号',
  `send_time` DATETIME NOT NULL,
  `send_flag` VARCHAR(1) NOT NULL COMMENT '发送状态：0：待发送  1：发送中  2：发送成功   3：发送失败   4：未知错误',
  `sms_template` VARCHAR(45) NOT NULL COMMENT '短信模板号',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;