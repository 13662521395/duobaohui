CREATE TABLE `sh_pay_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `tel` varchar(24) NOT NULL COMMENT '用户手机号',
  `trans_jnl_no` varchar(64) NOT NULL DEFAULT '' COMMENT '对应得交易流水ID',
  `trans_code` varchar(45) DEFAULT '0' COMMENT '用于表示此交易类型的字符串',
  `jnl_status` varchar(10) NOT NULL DEFAULT '0' COMMENT '状态.0=新建，1=付款成功，2=付款失败，3=超时未处理，4=交易完成,其他待扩展',
  `pay_type` varchar(10) NOT NULL DEFAULT '' COMMENT '支付类型,0=余额，1=充值，其他待扩展',
  `amount` double NOT NULL COMMENT '交易总金额',
  `create_time` datetime NOT NULL,
  `recharge_channel` varchar(6) NOT NULL DEFAULT '0' COMMENT '充值渠道，0=支付宝，1=微信，2=红包,其他待扩展',
  `duobao_id` int(11) NOT NULL DEFAULT '0' COMMENT '夺宝id',
  `bill_id` int(11) NOT NULL DEFAULT '0' COMMENT '异常id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1274 DEFAULT CHARSET=utf8 COMMENT='交易记录临时表';