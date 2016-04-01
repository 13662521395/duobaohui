package com.shinc.duobaohui.bean;

/**
 * 名称：GetVerifyCodeBean
 * 作者：zhaopl 时间: 15/9/23.
 * 实现的主要功能：
 *      获取短信验证码Bean;
 */
public class GetVerifyCodeBean {

    private String code;
    private String msg;

    public GetVerifyCodeBean(String code, String msg, String data) {
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
        return "GetVerifyCodeBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
