package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.ShaiDanBeanResult;
import com.shinc.duobaohui.event.HttpAddShowOrderEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by efort on 15/10/10.
 */
public class AddShowOrderHttpRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            ShaiDanBeanResult shaiDanBean;

            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {
                shaiDanBean = GsonUtil.json2Bean(context, responseInfo.result, ShaiDanBeanResult.class);

                EventBus.getDefault().post(new HttpAddShowOrderEvent(shaiDanBean));
            }

            @Override
            public void onFailure(HttpException e, String s) {

                EventBus.getDefault().post(new HttpAddShowOrderEvent(shaiDanBean));
            }
        });
    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}