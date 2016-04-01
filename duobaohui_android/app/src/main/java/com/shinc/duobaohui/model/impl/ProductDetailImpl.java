package com.shinc.duobaohui.model.impl;

import android.app.Activity;


import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.ProductDetailHttpRequestImpl;
import com.shinc.duobaohui.http.TakePartListHttpRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * 名称：ProductDetailImpl
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 */
public class ProductDetailImpl {

    private Activity mActivity;

    private HttpUtils httpUtils;

    public ProductDetailImpl(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }

    public void getProductInfo(String productId, boolean isBanner) {
        RequestParams requestParams = new RequestParams();
        if (isBanner) {
            requestParams.addBodyParameter("activity_id",productId);
        } else {
            requestParams.addBodyParameter("period_id",productId);
        }


        httpUtils.sendHttpPost(requestParams, ConstantApi.GET_PRODUCT_DETAIL, new ProductDetailHttpRequestImpl(), mActivity);
    }

    public void getTakePartRecord(String activeId, String page, int endId) {

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("period_id", activeId);
        requestParams.addBodyParameter("page", page);
        requestParams.addBodyParameter("end_id", endId + "");
        httpUtils.sendHttpPost(requestParams, ConstantApi.TAKEPARTRECORD, new TakePartListHttpRequestImpl(), mActivity);
    }
}
