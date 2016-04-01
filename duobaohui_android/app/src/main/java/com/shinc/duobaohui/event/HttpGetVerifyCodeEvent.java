package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.GetVerifyCodeBean;

/**
 * Created by zpl on 15/7/14.
 */
public class HttpGetVerifyCodeEvent {
    private GetVerifyCodeBean getVerifyCodeBean;

    public HttpGetVerifyCodeEvent(GetVerifyCodeBean getVerifyCodeBean) {
        this.getVerifyCodeBean = getVerifyCodeBean;
    }

    public GetVerifyCodeBean getGetVerifyCodeBean() {
        return getVerifyCodeBean;
    }
}
