package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.NoticeListRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * Created by yangtianhe on 15/11/20.
 */
public class NoticeListModelImpl {
    private Activity mActivity;
    public NoticeListModelImpl(Activity mActivity){
        this.mActivity = mActivity;
    }

    public void getNoticeList(){
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        httpUtils.sendHttpPost(requestParams, ConstantApi.NOTICE, new NoticeListRequestImpl (), mActivity);
    }


}
