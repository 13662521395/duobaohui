package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.ProductDetailBean;

/**
 * Created by zpl on 15/7/14.
 */
public class HttpProductDetailEvent {
    private ProductDetailBean productDetailBean;

    public HttpProductDetailEvent(ProductDetailBean productDetailBean) {
        this.productDetailBean = productDetailBean;
    }

    public ProductDetailBean getProductDetailBean() {
        return productDetailBean;
    }
}
