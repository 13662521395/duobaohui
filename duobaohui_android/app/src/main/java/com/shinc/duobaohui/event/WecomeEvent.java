package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.WeComeBean;

/**
 * Created by liugaopo on 15/12/15.
 */
public class WecomeEvent {
    private WeComeBean comeBean;

    public WeComeBean getComeBean() {
        return comeBean;
    }

    public WecomeEvent(WeComeBean comeBean) {
        this.comeBean = comeBean;
    }
}
