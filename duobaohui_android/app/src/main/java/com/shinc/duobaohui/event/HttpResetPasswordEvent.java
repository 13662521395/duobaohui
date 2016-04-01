package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.ForgetPasswordBean;

/**
 * Created by zpl on 15/7/14.
 * 找回密码网络请求的事件；
 */
public class HttpResetPasswordEvent {
    private ForgetPasswordBean forgetPasswordBean;

    public HttpResetPasswordEvent(ForgetPasswordBean forgetPasswordBean) {
        this.forgetPasswordBean = forgetPasswordBean;
    }

    public ForgetPasswordBean getForgetPasswordBean() {
        return forgetPasswordBean;
    }
}
