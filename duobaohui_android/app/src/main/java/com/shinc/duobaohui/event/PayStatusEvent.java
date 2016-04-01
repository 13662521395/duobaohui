package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.PayStatusBean;

/**
 * Created by liugaopo on 15/10/19.
 * 生成Order的结果传递的Event：
 */
public class PayStatusEvent {
    private PayStatusBean payStatusBean;

    public PayStatusEvent(PayStatusBean payStatusBean) {
        this.payStatusBean = payStatusBean;
    }

    public PayStatusBean getPayStatusBean() {
        return payStatusBean;
    }
}
