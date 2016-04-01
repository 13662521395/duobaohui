package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.UserDuoBaoRecrodRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;

/**
 * Created by liugaopo on 15/11/23.
 */
public class UserDuoBaoRecrodModel {


    private Activity mActiivty;
    private SharedPreferencesUtils utils;
    private String user_id;

    public UserDuoBaoRecrodModel(Activity mActivity, String user_id) {
        this.mActiivty = mActivity;
        utils = new SharedPreferencesUtils(mActiivty, Constant.SP_LOGIN);
        this.user_id = user_id;
    }

    public void getUserDuoBaoRecrodModel(int page) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("page", page + "");
        httpUtils.sendHttpPost(params, ConstantApi.tauser_DuoBao , new UserDuoBaoRecrodRequestImpl(), mActiivty);
    }
}
