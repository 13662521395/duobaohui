package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.DeleteAddressRequestImpl;
import com.shinc.duobaohui.http.UpdateAddreesRequestImpl;
import com.shinc.duobaohui.model.UpdateAddressModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;

/**
 * Created by liugaopo on 15/10/6.
 * 修改收货地址信息
 */
public class UpdateAddressModelImpl implements UpdateAddressModelInterface {


    private Activity mActivity;
    private HttpUtils httpUtils;
    private SharedPreferencesUtils spUtils;

    public UpdateAddressModelImpl(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
    }

    //删除
    @Override
    public void getInfoDelete(String address_id) {


        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("user_id", spUtils.get(Constant.SP_USER_ID, ""));
        requestParams.addBodyParameter("address_id", address_id);
        httpUtils.sendHttpPost(requestParams, ConstantApi.ADDREDD_DELETE, new DeleteAddressRequestImpl(), mActivity);
    }

    //修改
    @Override
    public void getUpdate(String address_id, String name, String phoneNum, String area, String detail, String isDefault) {

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("user_id", spUtils.get(Constant.SP_USER_ID, ""));
        requestParams.addBodyParameter("address_id", address_id);
        requestParams.addBodyParameter("name", name);
        requestParams.addBodyParameter("mobile", phoneNum);
        requestParams.addBodyParameter("area", area);
        requestParams.addBodyParameter("address", detail.replaceAll("%20", " "));
        requestParams.addBodyParameter("isDefault", isDefault);

        httpUtils.sendHttpPost(requestParams, ConstantApi.ADDREDD_EDIT, new UpdateAddreesRequestImpl(), mActivity);
    }


}
