package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.ShaiDanBean;

/**
 * Created by liugaopo on 15/11/23.
 */
public class UserShowOrderRecrodEvent {
    private ShaiDanBean bean;

    public UserShowOrderRecrodEvent(ShaiDanBean bean) {
        this.bean = bean;
    }

    public ShaiDanBean getBean() {
        return bean;
    }
}
