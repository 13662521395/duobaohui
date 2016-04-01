package com.shinc.duobaohui.bean;

/**
 * 名称：LoginTokenBean
 * 作者：zhaopl 时间: 15/10/15.
 * 实现的主要功能：
 * 登陆时候使用到的Token;
 */
public class LoginTokenBean {

    private String code;
    private String msg;
    private String data;

    public LoginTokenBean(String code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginTokenBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
