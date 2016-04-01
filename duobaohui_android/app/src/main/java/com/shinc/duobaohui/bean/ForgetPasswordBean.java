package com.shinc.duobaohui.bean;

/**
 * 名称：ForgetPasswordBean
 * 作者：zhaopl 时间: 15/9/23.
 * 实现的主要功能：
 *     忘记密码接口；
 */
public class ForgetPasswordBean {

    private String code;
    private String msg;

    public ForgetPasswordBean(String code, String msg, String data) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public String toString() {
        return "ForgetPasswordBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
