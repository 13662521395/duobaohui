package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.LoginTokenBean;

/**
 * Created by liugaopo on 15/10/19.
 */
public class TokenEvent {
    private LoginTokenBean loginTokenBean;

    public TokenEvent(LoginTokenBean loginTokenBean) {
        this.loginTokenBean = loginTokenBean;
    }

    public LoginTokenBean getLoginTokenBean() {
        return loginTokenBean;
    }
}
