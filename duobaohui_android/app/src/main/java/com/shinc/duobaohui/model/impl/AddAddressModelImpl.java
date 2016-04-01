package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.AddAddressRequestImpl;
import com.shinc.duobaohui.model.AddAddressListModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;


/**
 * Created by liugaopo on 15/10/6.
 */
public class AddAddressModelImpl implements AddAddressListModelInterface {
    private Activity mActivity;

    public AddAddressModelImpl(Activity mActivity) {
        this.mActivity = mActivity;
    }


    @Override
    public void setValue(String name, String phone, String area, String address) {
        HttpUtils httpUtils = new HttpUtils();
        SharedPreferencesUtils spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("user_id", spUtils.get(Constant.SP_USER_ID, ""));
        requestParams.addBodyParameter("name", name);
        requestParams.addBodyParameter("mobile", phone);
        requestParams.addBodyParameter("area", area);
        requestParams.addBodyParameter("address", address);
        httpUtils.sendHttpPost(requestParams, ConstantApi.ADD_ADDRESS, new AddAddressRequestImpl(), mActivity);
    }
}
