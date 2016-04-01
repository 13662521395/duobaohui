package com.shinc.duobaohui.http;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.TokenBean;
import com.shinc.duobaohui.event.HttpTokenEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.JsonFlagUtils;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by zhanggaoqi on 15/9/6.
 */
public class TokenRequestImpl implements HttpSendInterFace {

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {

                final TokenBean bean = GsonUtil.json2Bean(context, responseInfo.result, TokenBean.class);

                JsonFlagUtils.isSuccessJson(bean.getCode(), context);

                EventBus.getDefault().post(new HttpTokenEvent(bean));
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "网络连接失败,请您检查您的网络", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {

    }
}