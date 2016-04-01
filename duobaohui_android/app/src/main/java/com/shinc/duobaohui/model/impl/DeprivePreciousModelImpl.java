package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.GetWinnerListHttpRequestImpl;
import com.shinc.duobaohui.http.IndexBannerHttpRequestImpl;
import com.shinc.duobaohui.http.ProductListHttpRequestImpl;
import com.shinc.duobaohui.model.DeprivePreciousModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * 名称：DeprivePreciousModelImpl
 * 作者：zhaopl 时间: 15/9/29.
 * 实现的主要功能：
 */
public class DeprivePreciousModelImpl implements DeprivePreciousModelInterface {

    private Activity mActivity;
    private HttpUtils httpUtils;

    public DeprivePreciousModelImpl(Activity mActivity) {

        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }

    @Override
    public void getBanner() {

        RequestParams requestParams = new RequestParams();
        httpUtils.sendHttpPost(requestParams, ConstantApi.GET_BANNER, new IndexBannerHttpRequestImpl(), mActivity);
    }

    @Override
    public void getProductInfo(String sort, String page, String deviceId) {


//        String params = "?sort=" + sort + "&page=" + page + "&device_id=" + deviceId;
        // 需要按照商品的type请求不同筛选条件的查询结果进行显示；
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("sort", sort);
        requestParams.addBodyParameter("page", page);
        requestParams.addBodyParameter("device_id", deviceId);
        // 需要按照商品的type请求不同筛选条件的查询结果进行显示；

        httpUtils.sendHttpPost(requestParams, ConstantApi.PRODUCT_LIST, new ProductListHttpRequestImpl(), mActivity);

    }

    @Override
    public void getwinnerList() {
        // 需要按照商品的type请求不同筛选条件的查询结果进行显示；
        RequestParams requestParams = new RequestParams();
        httpUtils.sendHttpPost(requestParams, ConstantApi.GET_WINNER_LIST, new GetWinnerListHttpRequestImpl(), mActivity);
    }


}
