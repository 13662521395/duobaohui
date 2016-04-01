package com.shinc.duobaohui.http;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.LoginBean;
import com.shinc.duobaohui.event.HttpFastLoginEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by efort on 15/7/14.
 * 注册请求
 */
public class FastLoginHttpRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            LoginBean bean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                bean = GsonUtil.json2Bean(context, responseInfo.result, LoginBean.class);


                EventBus.getDefault().post(new HttpFastLoginEvent(bean));

                switch (bean.getCode()) {
                    case "1":
                        break;
                    case "-2":
                    case "-3":
                    case "-1":
                    case "-4":
                    case "20204":
                    case "20203":
                        Toast.makeText(context, bean.getMsg(), Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        Toast.makeText(context, "登陆出现异常，请稍后再试！", Toast.LENGTH_SHORT).show();

                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

                Toast.makeText(context, "网络连接失败,请您检查您的网络", Toast.LENGTH_LONG).show();


                EventBus.getDefault().post(new HttpFastLoginEvent(bean));
            }
        });
    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}