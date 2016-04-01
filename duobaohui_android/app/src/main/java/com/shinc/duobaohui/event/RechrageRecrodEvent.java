package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.RechargeRecrodBean;

import java.io.Serializable;

/**
 * Created by liugaopo on 15/10/10.
 * 充值记录。Event
 */
public class RechrageRecrodEvent implements Serializable {
    private RechargeRecrodBean bean;

    public RechargeRecrodBean getBean() {
        return bean;
    }

    public RechrageRecrodEvent(RechargeRecrodBean bean) {
        this.bean = bean;
    }
}
