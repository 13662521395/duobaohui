package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.LoginTokenBean;

/**
 * Created by liugaopo on 15/10/8.
 * 登陆时候进行获取token的时候进行的操作；
 */
public class LoginTokenEvent {
    private LoginTokenBean loginTokenBean;

    public LoginTokenEvent(LoginTokenBean loginTokenBean) {
        this.loginTokenBean = loginTokenBean;
    }

    public LoginTokenBean getLoginTokenBean() {
        return loginTokenBean;
    }
}
