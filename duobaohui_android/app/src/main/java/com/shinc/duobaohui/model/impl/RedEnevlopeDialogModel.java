package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.RedEnevlopeDialogRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * 文件名：
 * Created by chaos on 16/1/18.
 * 功能：
 */
public class RedEnevlopeDialogModel {

    private Activity mActivity;

    public RedEnevlopeDialogModel(Activity mActivity) {
        this.mActivity = mActivity;
    }

    //http://dev.api.duobaohui.com/redpacket/redpacket/get-redpacket-by-activityid?activity_id=77
    public void getRequestDate(String activityId,String num) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("activity_id", activityId);
        requestParams.addBodyParameter("num", num);
        httpUtils.sendHttpPost(requestParams, ConstantApi.HOST + ConstantApi.GETREDPCKETBYACTIVITYID, new RedEnevlopeDialogRequestImpl(), mActivity);
    }

}
