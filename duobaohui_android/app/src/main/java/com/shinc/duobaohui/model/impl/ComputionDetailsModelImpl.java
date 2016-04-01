package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.ComputionDetailsRequestImpl;
import com.shinc.duobaohui.model.ComputionalDetailsModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/10/8.
 */
public class ComputionDetailsModelImpl implements ComputionalDetailsModelInterface {

    private Activity mActivity;

    public ComputionDetailsModelImpl(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void getData(String id) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("sh_activity_period_id",id);
        httpUtils.sendHttpPost(requestParams, ConstantApi.COUNTDETAIL, new ComputionDetailsRequestImpl(), mActivity);

    }
}
