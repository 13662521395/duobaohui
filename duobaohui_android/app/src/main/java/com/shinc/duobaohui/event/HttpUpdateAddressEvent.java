package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.GetVerifyCodeBean;

/**
 * Created by liugaopo on 15/10/6.
 */
public class HttpUpdateAddressEvent {
    private GetVerifyCodeBean getVerifyCodeBean;

    public GetVerifyCodeBean getGetVerifyCodeBean() {
        return getVerifyCodeBean;
    }

    public HttpUpdateAddressEvent(GetVerifyCodeBean getVerifyCodeBean) {
        this.getVerifyCodeBean = getVerifyCodeBean;
    }
}
