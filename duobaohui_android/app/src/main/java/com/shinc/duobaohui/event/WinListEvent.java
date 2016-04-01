package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.WinListBean;

/**
 * Created by liugaopo on 15/11/26.
 * 中间纪录 V1.2
 */
public class WinListEvent {
    private WinListBean bean;

    public WinListEvent(WinListBean bean) {
        this.bean = bean;
    }

    public WinListBean getBean() {
        return bean;
    }
}
