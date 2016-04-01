drop table sh_jnl_trans;
drop table sh_jnl_recharge;
drop table sh_jnl_alipay;
drop table sh_jnl_trans_duobao;
drop table sh_jnl_deduct;

CREATE TABLE IF NOT EXISTS `sh_duobaohui`.`sh_jnl_trans` (
  `jnl_no` VARCHAR(64) NOT NULL COMMENT '交易流水号',
  `user_id` INT(11) NOT NULL COMMENT '用户ID',
  `trans_code` VARCHAR(45) NOT NULL COMMENT '用于表示此交易类型的字符串',
  `jnl_status` VARCHAR(10) NOT NULL COMMENT '状态.0=新建，1=付款成功，2=付款失败，3=超时未处理，4=交易完成,其他待扩展',
  `jnl_message` VARCHAR(64) NULL COMMENT '交易状态描述',
  `pay_type` VARCHAR(10) NULL COMMENT '支付类型,0=余额，1=充值，其他待扩展',
  `recharge_channel` VARCHAR(6) NULL COMMENT '充值渠道，0=支付宝，1=微信，其他待扩展',
  `amount` DOUBLE NOT NULL COMMENT '交易总金额',
  `amount_balance` DOUBLE NULL COMMENT '交易总金额中所需的余额部分',
  `amount_pay` DOUBLE NULL COMMENT '交易总金额中所需的支付部分',
  `amount_other` DOUBLE NULL COMMENT '交易总金额中所需的其他部分(积分、红包等待扩展)',
  `jnl_recharge_id` INT(11) NULL COMMENT '充值流水ID，用于关联充值信息,动帐后必须更新此值',
  `create_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL,
  PRIMARY KEY (`jnl_no`))
ENGINE = InnoDB
COMMENT = '交易日志表';
CREATE TABLE IF NOT EXISTS `sh_duobaohui`.`sh_jnl_recharge` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '用户id',
  `recharge_channel` VARCHAR(6) NULL COMMENT '充值渠道，0=支付宝，1=微信，其他待扩展',
  `channel_trade_no` VARCHAR(64) NULL COMMENT '充值渠道厂商的交易流水号',
  `trans_jnl_no` VARCHAR(64) NULL COMMENT '对应得交易流水ID',
  `amount` DOUBLE NOT NULL COMMENT '交易金额',
  `latest_balance` DOUBLE NOT NULL COMMENT '交易后最新余额',
  `create_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_jnl_recharge_jnlno` (`trans_jnl_no` ASC))
ENGINE = InnoDB
COMMENT = '用户充值流水表';
CREATE TABLE IF NOT EXISTS `sh_duobaohui`.`sh_jnl_alipay` (
  `trade_no` VARCHAR(64) NOT NULL COMMENT '支付宝交易号',
  `notify_type` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '通知的类型',
  `notify_id` VARCHAR(38) NOT NULL COMMENT '通知校验ID',
  `sign_type` VARCHAR(3) NOT NULL DEFAULT '' COMMENT '签名方式',
  `sign` VARCHAR(130) NOT NULL DEFAULT '' COMMENT '签名',
  `notify_time` DATETIME NOT NULL COMMENT '通知时间',
  `out_trade_no` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '商户网站唯一订单号',
  `subject` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '商品名称',
  `payment_type` VARCHAR(8) NOT NULL DEFAULT '' COMMENT '支付类型 1为夺宝，2为充值，3',
  `trade_status` VARCHAR(15) NOT NULL DEFAULT '' COMMENT '交易状态',
  `seller_id` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '卖家支付宝用户号',
  `seller_email` VARCHAR(15) NOT NULL DEFAULT '' COMMENT '卖家支付宝账号',
  `buyer_id` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '买家支付宝用户号',
  `buyer_email` VARCHAR(15) NOT NULL DEFAULT '' COMMENT '买家支付宝账号',
  `total_fee` DECIMAL(12,2) NOT NULL COMMENT '交易金额',
  `quantity` INT(11) NOT NULL COMMENT '购买数量',
  `price` DECIMAL(12,2) NOT NULL COMMENT '商品单价',
  `body` TEXT NOT NULL COMMENT '商品描述',
  `gmt_create` DATETIME NOT NULL COMMENT '交易创建时间',
  `gmt_payment` DATETIME NOT NULL COMMENT '交易付款时间',
  `is_total_fee_adjust` VARCHAR(3) NOT NULL DEFAULT '' COMMENT '是否调整总价',
  `use_coupon` VARCHAR(3) NOT NULL DEFAULT '' COMMENT '是否使用红包买家',
  `discount` DECIMAL(6,2) NOT NULL COMMENT '折扣',
  `refund_status` VARCHAR(18) NOT NULL DEFAULT '' COMMENT '退款状态',
  `gmt_refund` DATETIME NOT NULL COMMENT '退款时间',
  PRIMARY KEY (`trade_no`),
  INDEX `ix_jnl_alipay_outtradeno` (`out_trade_no` ASC))
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `sh_duobaohui`.`sh_jnl_trans_duobao` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `trans_jnl_no` VARCHAR(64) NOT NULL COMMENT '对应的交易流水号',
  `sh_activity_period_id` INT(11) NOT NULL,
  `num` INT(11) NOT NULL,
  `status` VARCHAR(1) NOT NULL COMMENT '夺宝状态，0=新建,1=夺宝成功，2=夺宝失败',
  `message` VARCHAR(64) NULL COMMENT '结果描述信息',
  `create_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_jnl_duobao_jnlno` (`trans_jnl_no` ASC))
ENGINE = InnoDB
COMMENT = '每笔交易下单夺宝信息';
CREATE TABLE IF NOT EXISTS `sh_duobaohui`.`sh_jnl_deduct` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '用户id',
  `trans_jnl_no` VARCHAR(64) NOT NULL COMMENT '对应得交易流水ID',
  `amount` DOUBLE NOT NULL COMMENT '交易金额',
  `latest_balance` DOUBLE NOT NULL COMMENT '交易后最新余额',
  `create_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_jnl_deduct` (`trans_jnl_no` ASC))
ENGINE = InnoDB
COMMENT = '用户扣款流水表';
