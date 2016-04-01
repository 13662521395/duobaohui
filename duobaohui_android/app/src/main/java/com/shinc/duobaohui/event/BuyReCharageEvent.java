package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.LoginTokenBean;

/**
 * Created by liugaopo on 15/11/2.
 */
public class BuyReCharageEvent {
    private LoginTokenBean loginTokenBean;

    public BuyReCharageEvent(LoginTokenBean loginTokenBean) {
        this.loginTokenBean = loginTokenBean;
    }

    public LoginTokenBean getLoginTokenBean() {
        return loginTokenBean;
    }
}
