package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.GetVerifyCodeBean;

/**
 * Created by liugaopo on 15/10/7.
 */
public class HttpStayStocksEvent {
    private GetVerifyCodeBean getVerifyCodeBean;

    public HttpStayStocksEvent(GetVerifyCodeBean getVerifyCodeBean) {
        this.getVerifyCodeBean = getVerifyCodeBean;
    }

    public GetVerifyCodeBean getGetVerifyCodeBean() {
        return getVerifyCodeBean;
    }
}
