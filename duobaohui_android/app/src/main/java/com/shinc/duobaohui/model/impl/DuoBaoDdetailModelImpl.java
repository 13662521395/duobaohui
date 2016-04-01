package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.DuoBaoDetailsRequestImpl;
import com.shinc.duobaohui.model.DuoBaoDetailRequestInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/10/8.
 */
public class DuoBaoDdetailModelImpl implements DuoBaoDetailRequestInterface {

    private Activity mActivity;
    private String user_id;

    public DuoBaoDdetailModelImpl(Activity mActivity, String user_id) {
        this.mActivity = mActivity;
        this.user_id = user_id;
    }

    @Override
    public void getLotteryDate(String sh_activity_period_id, int page) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("user_id", user_id);
        requestParams.addBodyParameter("sh_activity_period_id", sh_activity_period_id);
        requestParams.addBodyParameter("page", page + "");
        requestParams.addBodyParameter("length", "10");

        httpUtils.sendHttpPost(requestParams, ConstantApi.LOTTERYTIMESLISTBYPERIOD, new DuoBaoDetailsRequestImpl(), mActivity);
    }
}
