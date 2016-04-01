package com.shinc.duobaohui.http;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.GetVerifyCodeBean;
import com.shinc.duobaohui.event.HttpGetVerifyCodeEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by efort on 15/7/14.
 * 登陆请求
 */
public class FastLoginGetVerifyCodeHttpRequestImpl implements HttpSendInterFace {

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {

            GetVerifyCodeBean bean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                bean = GsonUtil.json2Bean(context, responseInfo.result, GetVerifyCodeBean.class);


                EventBus.getDefault().post(new HttpGetVerifyCodeEvent(bean));

                if ("1".equals(bean.getCode())) {
                } else if ("20206".equals(bean.getCode()) || "-3".equals(bean.getCode())) {
                    Toast.makeText(context, bean.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "获取验证码异常，请稍后再试！", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "网络连接失败,请您检查您的网络", Toast.LENGTH_LONG).show();


                EventBus.getDefault().post(new HttpGetVerifyCodeEvent(bean));
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}