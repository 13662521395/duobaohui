package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.ProductListBean;

/**
 * Created by zpl on 15/7/14.
 *    首页商品列表event：
 */
public class HttpProductEvent {
    private ProductListBean productListBean;

    public HttpProductEvent(ProductListBean productListBean) {
        this.productListBean = productListBean;
    }

    public ProductListBean getProductListBean() {
        return productListBean;
    }
}
