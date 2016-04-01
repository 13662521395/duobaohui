package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.RechargeRecrodRequestImpl;
import com.shinc.duobaohui.model.RechargeRecrodInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/10/10.
 */
public class RechargeRecrodModelImpl implements RechargeRecrodInterface {

    private Activity mActivity;
    private HttpUtils httpUtils;

    public RechargeRecrodModelImpl(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }

    @Override
    public void getDate(int page) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("page", page + "");
        httpUtils.sendHttpPost(requestParams, ConstantApi.RECHARGE, new RechargeRecrodRequestImpl(), mActivity);
    }
}
