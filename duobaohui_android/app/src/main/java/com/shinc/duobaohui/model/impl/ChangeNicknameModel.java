package com.shinc.duobaohui.model.impl;

import android.app.Activity;
import android.util.Log;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.ChangeNicknameHttpRequestImpl;
import com.shinc.duobaohui.model.ChangeNicknameModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * 修改昵称 model
 * Created by yangtianhe on 15/10/12.
 */
public class ChangeNicknameModel implements ChangeNicknameModelInterface {
    private Activity mActivity;

    public ChangeNicknameModel(Activity mActivity) {
        this.mActivity = mActivity;
    }


    @Override
    public void ChangeNickname(String nickname) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("nick_name",nickname);
        httpUtils.sendHttpPost(requestParams, ConstantApi.CHANGENICKNAME, new ChangeNicknameHttpRequestImpl(), mActivity);
        Log.e("getcountDetail", ConstantApi.CHANGENICKNAME);
    }
}
