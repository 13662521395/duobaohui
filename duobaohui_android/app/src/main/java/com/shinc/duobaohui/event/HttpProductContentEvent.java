package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.ProductContentBean;

/**
 * 名称：HttpArticleInfoEvent
 * 作者：zhaopl 时间: 15/7/4.
 * 实现的主要功能：
 */
public class HttpProductContentEvent {

    private ProductContentBean productContentBean;

    public HttpProductContentEvent(ProductContentBean productContentBean) {
        this.productContentBean = productContentBean;
    }

    public ProductContentBean getProductContentBean() {
        return productContentBean;
    }
}
