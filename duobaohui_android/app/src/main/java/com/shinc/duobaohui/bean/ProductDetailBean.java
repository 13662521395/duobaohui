package com.shinc.duobaohui.bean;

import java.io.Serializable;

/**
 * 名称:
 * Created by chaos on 15/9/29.
 * 功能:
 */

public class ProductDetailBean implements Serializable {

    private String code;
    private String msg;
    private ProductDetail data;

    public ProductDetailBean() {
    }

    public ProductDetailBean(String code, String msg, ProductDetail data) {
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

    public ProductDetail getData() {
        return data;
    }

    public void setData(ProductDetail data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProductDetailBean{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
