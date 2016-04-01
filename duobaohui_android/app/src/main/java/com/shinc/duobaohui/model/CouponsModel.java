package com.shinc.duobaohui.model;

import android.content.Context;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.CouponsListRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * @作者: efort
 * @日期: 15/12/17 - 20:23
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsModel {

    private final HttpUtils httpUtils;
    private final Context mContext;

    public CouponsModel(Context context) {
        this.mContext = context;
        httpUtils = new HttpUtils();
    }

    public void getEnableCoupons(int page) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("type", "0");
        requestParams.addBodyParameter("page", page + "");

        httpUtils.sendHttpPost(requestParams, ConstantApi.COUPONS_LIST, new CouponsListRequestImpl("enable"), mContext);
    }

    public void getUnableCoupons(int page) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("type", "1");
        requestParams.addBodyParameter("page", page + "");

        httpUtils.sendHttpPost(requestParams, ConstantApi.COUPONS_LIST, new CouponsListRequestImpl("unable"), mContext);
    }

    public void getOverdueCoupons(int page) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("type", "2");
        requestParams.addBodyParameter("page", page + "");

        httpUtils.sendHttpPost(requestParams, ConstantApi.COUPONS_LIST, new CouponsListRequestImpl("overdue"), mContext);
    }

}
