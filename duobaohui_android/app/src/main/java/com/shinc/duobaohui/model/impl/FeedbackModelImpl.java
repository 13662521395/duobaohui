package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.FeedbackHttpImpl;
import com.shinc.duobaohui.model.FeedbackModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/9/23.
 */
public class FeedbackModelImpl implements FeedbackModelInterface {

    private Activity mActivity;

    public FeedbackModelImpl(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void getDateSubmit(String msg) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("content",msg);
        httpUtils.sendHttpPost(requestParams, ConstantApi.add_opinion, new FeedbackHttpImpl(), mActivity);

    }
}
