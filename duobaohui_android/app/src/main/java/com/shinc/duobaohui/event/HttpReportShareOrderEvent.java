package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.GetVerifyCodeBean;

/**
 * Created by liugaopo on 15/10/6.
 * 字段一样，复用获取短信验证码Bean
 * 晒单举报事件；
 */
public class HttpReportShareOrderEvent {
    private GetVerifyCodeBean getVerifyCodeBean;

    public HttpReportShareOrderEvent(GetVerifyCodeBean getVerifyCodeBean) {
        this.getVerifyCodeBean = getVerifyCodeBean;
    }

    public GetVerifyCodeBean getGetVerifyCodeBean() {
        return getVerifyCodeBean;
    }
}
