package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.RecieveAddressRequestImpl;
import com.shinc.duobaohui.model.RecieveAddressModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;

/**
 * Created by liugaopo on 15/10/6.
 * 提交兑奖信息
 */
public class RecieveAddressModelImpl implements RecieveAddressModelInterface {
    private Activity mActivity;
    private HttpUtils httpUtils;

    public RecieveAddressModelImpl(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }

    @Override
    public void getUpdateOrderStatus(String order_id, String order_status, String shipping_status, String address_id, String flag) {
        SharedPreferencesUtils spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);


        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("user_id", spUtils.get(Constant.SP_USER_ID, ""));
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("order_status", "1");
        requestParams.addBodyParameter("shipping_status", shipping_status);
        requestParams.addBodyParameter("address_id", address_id);

        httpUtils.sendHttpPost(requestParams, ConstantApi.UPDATEORDERSTATUS, new RecieveAddressRequestImpl(flag), mActivity);
    }
}
