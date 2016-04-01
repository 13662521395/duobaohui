package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.LoginBean;

/**
 * Created by zpl on 15/7/14.
 */
public class HttpLoginEvent {
    private LoginBean loginBean;

    public HttpLoginEvent(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }
}
