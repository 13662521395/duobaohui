package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.ProductListBean;

/**
 * 名称：SearchPruductEvent
 * 作者：zhaopl 时间: 15/11/23.
 * 实现的主要功能：
 */
public class SearchProductEvent {

    private ProductListBean productListBean;

    public SearchProductEvent(ProductListBean productListBean) {
        this.productListBean = productListBean;
    }

    public ProductListBean getProductListBean() {
        return productListBean;
    }
}
