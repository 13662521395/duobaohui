package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.GetVerifyCodeBean;
import com.shinc.duobaohui.event.HttpStayStocksEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/7.
 * 待确认－确认收货
 */
public class UpdateStatusStayStocksRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(final RequestParams requestParams, String url, final Context context) {


        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            GetVerifyCodeBean bean;

            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {

                bean = GsonUtil.json2Bean(context, responseInfo.result, GetVerifyCodeBean.class);

                EventBus.getDefault().post(new HttpStayStocksEvent(bean));

            }

            @Override
            public void onFailure(HttpException e, String s) {

                EventBus.getDefault().post(new HttpStayStocksEvent(bean));
            }
        });

    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}
