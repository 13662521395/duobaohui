package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.WinItemDetailRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/11/26.
 */
public class WinitemDetailModel {
    private Activity activity;

    public WinitemDetailModel(Activity activity) {
        this.activity = activity;
    }

    public void getWinDetailData(String period) {
        HttpUtils httpUtils = new HttpUtils();
         RequestParams params = new RequestParams();
        params.addBodyParameter("sh_activity_period_id",period);
        httpUtils.sendHttpPost(params, ConstantApi.WinListRecroDetail , new WinItemDetailRequestImpl(), activity);
    }
}
