package com.shinc.duobaohui.bean;

import java.util.List;

/**
 * 名称：WaitPublishListBean
 * 作者：zhaopl 时间: 15/10/12.
 * 实现的主要功能：
 */
public class WaitPublishListBean {

    private String code;
    private String msg;
    private List<WaitPublishBean> data;

    public WaitPublishListBean(String code, String msg, List<WaitPublishBean> data) {
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

    public List<WaitPublishBean> getData() {
        return data;
    }

    public void setData(List<WaitPublishBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WaitPublishListBean{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
