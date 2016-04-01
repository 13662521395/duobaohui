package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.UserDuoBaoCodeRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/10/8.
 */
public class UserDuoBaoCodeModelImpl {
    private Activity mActivity;
    private HttpUtils httpUtils;

    public UserDuoBaoCodeModelImpl(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }

    public void getCodeData(String period_id, int page) {

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("period_id", period_id);
        requestParams.addBodyParameter("page", page + "");
        requestParams.addBodyParameter("length", "100");
        httpUtils.sendHttpPost(requestParams, ConstantApi.USER_DUOBAO_CODE, new UserDuoBaoCodeRequestImpl(), mActivity);
    }
}
