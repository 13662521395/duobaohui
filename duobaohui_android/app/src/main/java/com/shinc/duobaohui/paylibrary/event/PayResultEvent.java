package com.shinc.duobaohui.paylibrary.event;


import com.shinc.duobaohui.paylibrary.bean.PayResultBean;

/**
 * Created by efort on 15/7/15.
 */
public class PayResultEvent {
    private PayResultBean resetBean;

    public PayResultEvent(PayResultBean resetBean) {
        this.resetBean = resetBean;
    }

    public PayResultBean getResetBean() {
        return resetBean;
    }

    public void setResetBean(PayResultBean resetBean) {
        this.resetBean = resetBean;
    }
}
