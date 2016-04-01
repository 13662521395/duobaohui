package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.AddressListRequestimpl;
import com.shinc.duobaohui.model.AddressListInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;

/**
 * Created by liugaopo on 15/10/6.
 * 地址列表 Model层
 */
public class AddressListModel implements AddressListInterface {
    private Activity mActivity;

    public AddressListModel(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void getListData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        SharedPreferencesUtils spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
        requestParams.addBodyParameter("user_id", spUtils.get(Constant.SP_USER_ID, ""));


        httpUtils.sendHttpPost(requestParams, ConstantApi.ADDRESSLIST, new AddressListRequestimpl(), mActivity);
    }
}

