package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.PreAnnounceListHttpRequestImpl;
import com.shinc.duobaohui.model.PreAnnounceInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by liugaopo on 15/10/6.
 * 地址列表 Model层
 */
public class PreAnnounceModelImpl implements PreAnnounceInterface {
    private Activity mActivity;
    private HttpUtils httpUtils;

    public PreAnnounceModelImpl(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }


    public void getListData(String activeId, String page, String length) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("sh_activity_id", activeId);
        requestParams.addBodyParameter("page", page);
        requestParams.addBodyParameter("length", length);
        httpUtils.sendHttpPost(requestParams, ConstantApi.WINNERHISTORY, new PreAnnounceListHttpRequestImpl(), mActivity);
    }


    public void getListData(String activeId, String page, String length, String period_number) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("sh_activity_id", activeId);
        requestParams.addBodyParameter("page", page);
        requestParams.addBodyParameter("length", length);
        requestParams.addBodyParameter("period_number", period_number);
        httpUtils.sendHttpPost(requestParams, ConstantApi.WINNERHISTORY, new PreAnnounceListHttpRequestImpl(), mActivity);
    }
}

