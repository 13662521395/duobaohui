package com.shinc.duobaohui.bean;

/**
 * 名称：RegisterBean
 * 作者：zhaopl 时间: 15/9/23.
 * 实现的主要功能：
 *    注册返回的Bean;
 */
public class RegisterBean {

    private String code;
    private String msg;
    private String data;

    public RegisterBean(String code, String msg, String data) {
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
        return "RegisterBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
