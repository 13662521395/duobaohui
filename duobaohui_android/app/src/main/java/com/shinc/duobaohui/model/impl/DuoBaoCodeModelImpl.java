package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.DuoBaoCodeRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/10/8.
 */
public class DuoBaoCodeModelImpl {
    private Activity mActivity;
    private String user_id;

    public DuoBaoCodeModelImpl(Activity mActivity, String user_id) {
        this.mActivity = mActivity;
        this.user_id = user_id;
    }

    //http://api.duobaohui.com/activity/record/lotterycodelistbyperiodid
    // ?user_id=20&sh_activity_period_id=1&sh_period_user_id=2&page=1&length=5
    public void getCodeData(String sh_activity_period_id, String sh_period_user_id, int page) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("user_id", user_id);
        requestParams.addBodyParameter("sh_activity_period_id", sh_activity_period_id);
        requestParams.addBodyParameter("sh_period_user_id", sh_period_user_id);
        requestParams.addBodyParameter("page", page + "");
        requestParams.addBodyParameter("length", "100");
        httpUtils.sendHttpPost(requestParams, ConstantApi.LOTTERYCODELISTBYPERIOD, new DuoBaoCodeRequestImpl(), mActivity);
    }
}
