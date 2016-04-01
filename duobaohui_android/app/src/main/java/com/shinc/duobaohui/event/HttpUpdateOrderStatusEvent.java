package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.GetVerifyCodeBean;

/**
 * Created by liugaopo on 15/10/6.
 */
public class HttpUpdateOrderStatusEvent {
    private GetVerifyCodeBean getVerifyCodeBean;

    public GetVerifyCodeBean getGetVerifyCodeBean() {
        return getVerifyCodeBean;
    }

    public HttpUpdateOrderStatusEvent(GetVerifyCodeBean getVerifyCodeBean) {
        this.getVerifyCodeBean = getVerifyCodeBean;
    }
}

