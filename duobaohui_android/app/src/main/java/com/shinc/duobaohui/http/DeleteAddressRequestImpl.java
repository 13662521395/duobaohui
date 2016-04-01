package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.GetVerifyCodeBean;
import com.shinc.duobaohui.event.HttpUpdateAddressDeleteEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/6.
 */
public class DeleteAddressRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            GetVerifyCodeBean bean;

            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {

                bean = GsonUtil.json2Bean(context, responseInfo.result, GetVerifyCodeBean.class);

                EventBus.getDefault().post(new HttpUpdateAddressDeleteEvent(bean));

            }

            @Override
            public void onFailure(HttpException e, String s) {

                EventBus.getDefault().post(new HttpUpdateAddressDeleteEvent(bean));
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {

    }
}