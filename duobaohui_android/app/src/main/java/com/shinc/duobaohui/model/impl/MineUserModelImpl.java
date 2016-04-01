package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.UserInfoRequestImpl;
import com.shinc.duobaohui.model.MineUserInModelfoInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/10/15.
 */

public class MineUserModelImpl implements MineUserInModelfoInterface {

    private Activity mActivity;
    private HttpUtils httpUtils;

    public MineUserModelImpl(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }

    @Override
    public void getMineUserInfoData() {

        RequestParams requestParams = new RequestParams();
        httpUtils.sendHttpPost(requestParams, ConstantApi.GET_MOENY, new UserInfoRequestImpl(), mActivity);
    }


}
