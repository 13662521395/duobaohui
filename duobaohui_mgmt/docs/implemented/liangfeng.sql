#未执行 统计每天交易数据的table
CREATE TABLE `sh_count_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trade_num` int(16) NOT NULL COMMENT '交易总次数',
  `trade_money` decimal(12,2) NOT NULL COMMENT '交易总金额',
  `luck_money` decimal(12,2) NOT NULL COMMENT '奖品总金额',
  `create_time` datetime NOT NULL COMMENT '交易时间',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户真假，0为虚拟用户，1为真实用户',
  `start_time` datetime NOT NULL COMMENT '脚本开始时间',
  `end_time` datetime NOT NULL COMMENT '脚本结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='交易数据统计表';