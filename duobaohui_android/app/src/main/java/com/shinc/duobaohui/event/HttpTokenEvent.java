package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.TokenBean;

/**
 * Created by zhanggaoqi on 15/9/15.
 */
public class HttpTokenEvent {

    private TokenBean bean;

    public HttpTokenEvent(TokenBean bean) {

        this.bean = bean;
    }

    public TokenBean getBean() {
        return bean;
    }
}