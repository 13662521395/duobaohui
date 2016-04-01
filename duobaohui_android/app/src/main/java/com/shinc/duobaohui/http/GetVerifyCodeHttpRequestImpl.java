package com.shinc.duobaohui.http;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
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
public class GetVerifyCodeHttpRequestImpl implements HttpSendInterFace {

    private DbUtils dbUtils;

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {
        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {

            GetVerifyCodeBean bean = null;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                bean = GsonUtil.json2Bean(context, responseInfo.result, GetVerifyCodeBean.class);


                EventBus.getDefault().post(new HttpGetVerifyCodeEvent(bean));

                switch (bean.getCode()) {
                    case "1":
                        break;
                    case "20206":
                    case "10013":
                        Toast.makeText(context, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "获取验证码异常，请稍后再试！", Toast.LENGTH_SHORT).show();

                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

                EventBus.getDefault().post(new HttpGetVerifyCodeEvent(bean));
            }
        });

    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}