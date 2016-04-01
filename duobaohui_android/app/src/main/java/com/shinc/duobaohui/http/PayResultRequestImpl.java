package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.PayResultBean;
import com.shinc.duobaohui.event.HttpPayResultEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by zpl on 15/10/6.
 * 支付结果接口的操作；
 */
public class PayResultRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            PayResultBean payResultBean;

            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {
                payResultBean = GsonUtil.json2Bean(context, responseInfo.result, PayResultBean.class);


                EventBus.getDefault().post(new HttpPayResultEvent(payResultBean));


            }

            @Override
            public void onFailure(HttpException e, String s) {

                EventBus.getDefault().post(new HttpPayResultEvent(payResultBean));
            }
        });

    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}