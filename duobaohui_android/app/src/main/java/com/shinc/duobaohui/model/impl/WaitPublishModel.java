package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.WaitPublishRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * 名称：ProductDetailImpl
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 * 等待揭晓的页面的model层；
 */
public class WaitPublishModel {

    private Activity mActivity;

    private HttpUtils httpUtils;

    public WaitPublishModel(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }

    public void getProductInfo(String page) {

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("page",page);
        httpUtils.sendHttpPost(requestParams, ConstantApi.WAIT_PUBLIC, new WaitPublishRequestImpl(), mActivity);
    }

}
