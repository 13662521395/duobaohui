package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.ProductListBean;

/**
 * 名称：CategoryContentEvent
 * 作者：zhaopl 时间: 15/11/23.
 * 实现的主要功能：
 *      商品分类下商品数据Event;
 */
public class CategoryContentEvent {

    private ProductListBean productListBean;

    public CategoryContentEvent(ProductListBean productListBean) {
        this.productListBean = productListBean;
    }

    public ProductListBean getProductListBean() {
        return productListBean;
    }
}
