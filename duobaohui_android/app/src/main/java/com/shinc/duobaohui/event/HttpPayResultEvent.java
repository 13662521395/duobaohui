package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.PayResultBean;

/**
 * Created by liugaopo on 15/10/8.
 */
public class HttpPayResultEvent {
    private PayResultBean payResultBean;

    public HttpPayResultEvent(PayResultBean payResultBean) {
        this.payResultBean = payResultBean;
    }

    public PayResultBean getPayResultBean() {
        return payResultBean;
    }
}
