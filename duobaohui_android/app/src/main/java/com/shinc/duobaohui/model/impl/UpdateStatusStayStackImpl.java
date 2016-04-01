package com.shinc.duobaohui.model.impl;


import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.UpdateStatusStayStocksRequestImpl;
import com.shinc.duobaohui.model.UpdateStatusStayStackInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;

/**
 * Created by liugaopo on 15/10/7.
 * 中奖纪录－刷新订单状态
 */

public class UpdateStatusStayStackImpl implements UpdateStatusStayStackInterface {

    private Activity mActivity;
    private HttpUtils httpUtils;
    private SharedPreferencesUtils spUtils;

    public UpdateStatusStayStackImpl(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
    }

    @Override
    public void getUpdateStatus(String order_id, String order_status) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("user_id", spUtils.get(Constant.SP_USER_ID, ""));
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("order_status", "2");
        requestParams.addBodyParameter("shipping_status", order_status);

        httpUtils.sendHttpPost(requestParams, ConstantApi.UPDATEORDERSTATUS, new UpdateStatusStayStocksRequestImpl(), mActivity);
    }
}
