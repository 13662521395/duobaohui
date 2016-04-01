package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.UserShowOrderRecrodRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/11/23.
 */
public class UserShowOrderRecrodModel {
    private Activity mActivity;
    private String user_id;

    public UserShowOrderRecrodModel(Activity mActivity, String user_id) {
        this.mActivity = mActivity;
        this.user_id = user_id;

    }

    public void getUserShowOrderData(int page) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("page", page + "");
        httpUtils.sendHttpPost(params, ConstantApi.tauser_ShowOrder, new UserShowOrderRecrodRequestImpl(), mActivity);
    }
}
