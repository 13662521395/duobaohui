package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.GetVerifyCodeBean;

/**
 * Created by liugaopo on 15/10/10.
 */
public class RechrageEvent {
    private GetVerifyCodeBean verifyCodeBean;

    public GetVerifyCodeBean getVerifyCodeBean() {
        return verifyCodeBean;
    }

    public RechrageEvent(GetVerifyCodeBean verifyCodeBean) {
        this.verifyCodeBean = verifyCodeBean;
    }
}
