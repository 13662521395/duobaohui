package com.shinc.duobaohui.bean;

import java.util.ArrayList;

/**
 * 名称：DuobaoListBean
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 *    夺宝记录list解析类；
 */
public class DuobaoListBean {

    private String code;
    private String msg;
    private ArrayList<DuobaoBean> data;

    public DuobaoListBean() {
    }

    public DuobaoListBean(String code, String msg, ArrayList<DuobaoBean> data) {
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

    public ArrayList<DuobaoBean> getData() {
        return data;
    }

    public void setData(ArrayList<DuobaoBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DuobaoListBean{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
