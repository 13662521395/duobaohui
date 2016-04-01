
ALTER TABLE `sh_bill`	Add column  `notify_time` datetime NOT NULL COMMENT '交易时间';

ALTER TABLE `sh_bill_action`	Add column  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id';

ALTER TABLE `sh_bill`	change	alipay_id	out_trade_no	varchar(16) NOT NULL DEFAULT '' COMMENT '流水号';
