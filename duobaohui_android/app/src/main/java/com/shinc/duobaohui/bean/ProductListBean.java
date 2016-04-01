package com.shinc.duobaohui.bean;

import java.util.ArrayList;

/**
 * 名称：ProductListBean
 * 作者：zhaopl 时间: 15/9/30.
 * 实现的主要功能：
 *    首页商品列表；
 */
public class ProductListBean {

    private String code;
    private String msg;
    private ArrayList<ProductBean> data;

    public ProductListBean(String code, String msg, ArrayList<ProductBean> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ProductListBean() {
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

    public ArrayList<ProductBean> getData() {
        return data;
    }

    public void setData(ArrayList<ProductBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProductListBean{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
