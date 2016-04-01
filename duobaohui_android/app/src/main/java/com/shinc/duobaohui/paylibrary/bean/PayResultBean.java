package com.shinc.duobaohui.paylibrary.bean;

/**
 * Created by yangtianhe on 15/8/21.
 * 支付结果的 bean
 */
public class PayResultBean {
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PayResultBean(String code) {

        this.code = code;
    }
}
