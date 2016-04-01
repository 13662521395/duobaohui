package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.ComputionalDetailsBean;

/**
 * Created by liugaopo on 15/10/8.
 */
public class ComputionalDetaillsEvent {
    private ComputionalDetailsBean computionalDetailsBean;

    public ComputionalDetailsBean getComputionalDetailsBean() {
        return computionalDetailsBean;
    }

    public ComputionalDetaillsEvent(ComputionalDetailsBean computionalDetailsBean) {
        this.computionalDetailsBean = computionalDetailsBean;
    }
}
