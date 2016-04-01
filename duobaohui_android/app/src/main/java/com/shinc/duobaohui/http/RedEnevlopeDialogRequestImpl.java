package com.shinc.duobaohui.http;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.RedEnevlopeDialogbean;
import com.shinc.duobaohui.event.RedEnevlopeDialogEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 文件名： 支付页面-红包列表
 * Created by chaos on 16/1/18.
 * 功能：
 * 版本：v1.3
 */
public class RedEnevlopeDialogRequestImpl implements HttpSendInterFace {

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            RedEnevlopeDialogbean bean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                bean = GsonUtil.json2Bean(context, responseInfo.result, RedEnevlopeDialogbean.class);
                EventBus.getDefault().post(new RedEnevlopeDialogEvent(bean));
            }

            @Override
            public void onFailure(HttpException e, String s) {
                EventBus.getDefault().post(new RedEnevlopeDialogEvent(bean));

            }
        });



    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}