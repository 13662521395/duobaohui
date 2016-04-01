package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.GetVerifyCodeBean;

/**
 * Created by liugaopo on 15/10/6.
 * 收货地址的删除操作
 */
public class HttpUpdateAddressDeleteEvent {
    private GetVerifyCodeBean verifyCodeBean;

    public GetVerifyCodeBean getVerifyCodeBean() {
        return verifyCodeBean;
    }

    public HttpUpdateAddressDeleteEvent(GetVerifyCodeBean verifyCodeBean) {
        this.verifyCodeBean = verifyCodeBean;
    }
}
