package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.GetVerifyCodeBean;
import com.shinc.duobaohui.event.HttpUpdateOrderStatusEvent;
import com.shinc.duobaohui.event.HttpWinUpdateOrderStatusEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/6.
 */

public class RecieveAddressRequestImpl implements HttpSendInterFace {


    private String flag;

    public RecieveAddressRequestImpl(String flag) {
        this.flag = flag;
    }

    @Override
    public void sendPostRequest(final RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            GetVerifyCodeBean bean;

            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {

                bean = GsonUtil.json2Bean(context, responseInfo.result, GetVerifyCodeBean.class);
                switch (flag) {
                    case "1":
                        EventBus.getDefault().post(new HttpUpdateOrderStatusEvent(bean));
                        break;
                    default:
                        EventBus.getDefault().post(new HttpWinUpdateOrderStatusEvent(bean));
                        break;
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

                if (flag.equals("1")) {
                    EventBus.getDefault().post(new HttpUpdateOrderStatusEvent(bean));
                } else {
                    EventBus.getDefault().post(new HttpWinUpdateOrderStatusEvent(bean));
                }
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}