package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.WxPayBean;

/**
 * Created by liugaopo on 15/11/18.
 */
public class WxPayEvent {
    private WxPayBean wxPayBean;

    public WxPayEvent(WxPayBean wxPayBean) {
        this.wxPayBean = wxPayBean;
    }

    public WxPayBean getWxPayBean() {
        return wxPayBean;
    }
}
