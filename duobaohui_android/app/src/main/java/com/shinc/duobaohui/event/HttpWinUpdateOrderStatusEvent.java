package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.GetVerifyCodeBean;

/**
 * Created by liugaopo on 15/11/26.
 */
public class HttpWinUpdateOrderStatusEvent {
    private GetVerifyCodeBean getVerifyCodeBean;

    public GetVerifyCodeBean getGetVerifyCodeBean() {
        return getVerifyCodeBean;
    }

    public HttpWinUpdateOrderStatusEvent(GetVerifyCodeBean getVerifyCodeBean) {
        this.getVerifyCodeBean = getVerifyCodeBean;
    }
}
