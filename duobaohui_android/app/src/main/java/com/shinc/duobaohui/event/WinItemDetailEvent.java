package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.WinItemDetailBean;

/**
 * Created by liugaopo on 15/11/26.
 */
public class WinItemDetailEvent {
    private WinItemDetailBean bean;

    public WinItemDetailEvent(WinItemDetailBean bean) {
        this.bean = bean;
    }

    public WinItemDetailBean getBean() {
        return bean;
    }
}
