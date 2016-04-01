package com.shinc.duobaohui.bean;

import java.util.ArrayList;

/**
 * 名称：TakePartListBean
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 */
public class TakePartListBean {

    private String code;
    private String msg;
    private ArrayList<TakePartBean> data;

    public TakePartListBean(String code, String msg, ArrayList<TakePartBean> data) {
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

    public ArrayList<TakePartBean> getData() {
        return data;
    }

    public void setData(ArrayList<TakePartBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TakePartListBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
