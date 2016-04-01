package com.shinc.duobaohui.model.impl;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.WinListRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/11/26.
 * 中奖记录 V1.2
 */
public class WinListModel {
    private BaseActivity activity;

    public WinListModel(BaseActivity activity) {
        this.activity = activity;
    }

    public void getWinData(int page) {
        HttpUtils httpUtils = new HttpUtils();

        RequestParams params = new RequestParams();
        params.addBodyParameter("page", page + "");
        httpUtils.sendHttpPost(params, ConstantApi.WinListRecrod, new WinListRequestImpl(), activity);
    }
}
