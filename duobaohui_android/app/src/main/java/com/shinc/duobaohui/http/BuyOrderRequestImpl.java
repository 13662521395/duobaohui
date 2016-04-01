package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.LoginTokenBean;
import com.shinc.duobaohui.event.BuyOrderEvent;
import com.shinc.duobaohui.event.BuyReCharageEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/19.
 * 获取订单Id；
 */
public class BuyOrderRequestImpl implements HttpSendInterFace {

    private String tag;

    public BuyOrderRequestImpl(String tag) {
        this.tag = tag;
    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {
        final HttpUtils httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(2000);


        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            LoginTokenBean loginTokenBean = null;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                loginTokenBean = GsonUtil.json2Bean(context, responseInfo.result, LoginTokenBean.class);

                if (tag.equals("1")) {
                    EventBus.getDefault().post(new BuyOrderEvent(loginTokenBean));
                } else if (tag.equals("2")) {
                    EventBus.getDefault().post(new BuyReCharageEvent(loginTokenBean));

                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

                if (tag.equals("1")) {
                    EventBus.getDefault().post(new BuyOrderEvent(loginTokenBean));
                } else if (tag.equals("2")) {
                    EventBus.getDefault().post(new BuyReCharageEvent(loginTokenBean));

                }
            }
        });

    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }

}
