package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.LoginBean;

/**
 * Created by zpl on 15/7/14.
 */
public class HttpFastLoginEvent {
    private LoginBean loginBean;

    public HttpFastLoginEvent(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }
}
