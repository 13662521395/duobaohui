package com.shinc.duobaohui.bean;

import java.util.ArrayList;

/**
 * 名称：PreAnnounceListBean
 * 作者：zhaopl 时间: 15/10/10.
 * 实现的主要功能：
 */
public class PreAnnounceListBean {

    private String code;
    private String msg;
    private ArrayList<PreAnnounceBean> data;


    public PreAnnounceListBean(String code, String msg, ArrayList<PreAnnounceBean> data) {
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

    public ArrayList<PreAnnounceBean> getData() {
        return data;
    }

    public void setData(ArrayList<PreAnnounceBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PreAnnounceListBean{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
