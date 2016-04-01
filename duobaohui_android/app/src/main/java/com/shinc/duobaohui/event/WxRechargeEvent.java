package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.WxPayBean;

/**
 * Created by liugaopo on 15/11/19.
 */
public class WxRechargeEvent {
    private WxPayBean wxPayBean;

    public WxRechargeEvent(WxPayBean wxPayBean) {
        this.wxPayBean = wxPayBean;
    }

    public WxPayBean getWxPayBean() {
        return wxPayBean;
    }
}
