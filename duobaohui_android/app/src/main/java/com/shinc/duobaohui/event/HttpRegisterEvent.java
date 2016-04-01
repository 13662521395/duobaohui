package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.RegisterBean;

/**
 * Created by zpl on 15/7/14.
 */
public class HttpRegisterEvent {
    private RegisterBean registerBean;

    public HttpRegisterEvent(RegisterBean registerBean) {
        this.registerBean = registerBean;
    }

    public RegisterBean getRegisterBean() {
        return registerBean;
    }
}
