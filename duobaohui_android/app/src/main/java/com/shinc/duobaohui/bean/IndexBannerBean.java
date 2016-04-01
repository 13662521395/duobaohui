package com.shinc.duobaohui.bean;

import java.util.ArrayList;

/**
 * 名称：IndexBannerBean
 * 作者：zhaopl 时间: 15/9/30.
 * 实现的主要功能：
 *      首页banner及通知接口bean文件；
 */
public class IndexBannerBean {

    private String code;
    private String msg;
    private ArrayList<BannerBean> data;

    public IndexBannerBean() {
    }

    public IndexBannerBean(String code, String msg, ArrayList<BannerBean> data) {
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

    public ArrayList<BannerBean> getData() {
        return data;
    }

    public void setData(ArrayList<BannerBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IndexBannerBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
