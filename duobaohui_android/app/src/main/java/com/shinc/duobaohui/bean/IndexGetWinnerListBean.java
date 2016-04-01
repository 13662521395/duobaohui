package com.shinc.duobaohui.bean;

import java.util.ArrayList;

/**
 * 名称：IndexGetWinnerListBean
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 */
public class IndexGetWinnerListBean {

    private String code;
    private String msg;
    private ArrayList<IndexWinnerBean> data;

    public IndexGetWinnerListBean() {
    }

    public IndexGetWinnerListBean(String code, String msg, ArrayList<IndexWinnerBean> data) {
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

    public ArrayList<IndexWinnerBean> getData() {
        return data;
    }

    public void setData(ArrayList<IndexWinnerBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IndexGetWinnerListBean{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
