
ALTER TABLE `sh_bill`	Add column  `notify_time` datetime NOT NULL COMMENT '交易时间';

ALTER TABLE `sh_bill`	change	alipay_id	out_trade_no	varchar(16) NOT NULL DEFAULT '';