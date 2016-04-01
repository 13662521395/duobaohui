package com.shinc.duobaohui.model.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.UpadteHeadRequestImpl;
import com.shinc.duobaohui.model.UpadteHeadModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by yangtianhe on 15/10/12.
 */
public class UpdateHeadModel implements UpadteHeadModelInterface {
    private Activity mActivity;
    private HttpUtils httpUtils;

    public UpdateHeadModel(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }

    @Override
    public void sendurl(String url) {
        String params = "?head_pic=" + url;

        if (TextUtils.isEmpty(params)) {
            Toast.makeText(mActivity, "未登录", Toast.LENGTH_LONG).show();
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("head_pic",url);
        httpUtils.sendHttpPost(requestParams, ConstantApi.UPDATE_HEAD, new UpadteHeadRequestImpl(), mActivity);
    }

}

