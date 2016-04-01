package com.shinc.duobaohui.bean;

import java.io.Serializable;

/**
 * 名称：ArticleInfoBean
 * 作者：zhaopl 时间: 15/7/7.
 * 实现的主要功能：文章正文获取的信息实体类；
 */
public class ProductContentBean implements Serializable {


    private String code;
    private String msg;
    private GoodDesc data;


    public class GoodDesc{
        private String goods_desc;

        public GoodDesc(String goods_desc) {
            this.goods_desc = goods_desc;
        }

        public String getGoods_desc() {
            return goods_desc;
        }

        public void setGoods_desc(String goods_desc) {
            this.goods_desc = goods_desc;
        }

        @Override
        public String toString() {
            return "GoodDesc{" +
                    "goods_desc='" + goods_desc + '\'' +
                    '}';
        }
    }

    public ProductContentBean(String code, String msg, GoodDesc data) {
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

    public GoodDesc getData() {
        return data;
    }

    public void setData(GoodDesc data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProductContentBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
