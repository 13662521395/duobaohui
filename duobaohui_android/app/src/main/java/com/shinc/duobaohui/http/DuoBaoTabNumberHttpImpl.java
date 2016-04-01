package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.DuoBaoTabNumberBean;
import com.shinc.duobaohui.event.DuoBaoNumberEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/11/6.
 */
public class DuoBaoTabNumberHttpImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(final RequestParams requestParams, String url, final Context context) {


        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {

            DuoBaoTabNumberBean duoBaoCodeBean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                duoBaoCodeBean = GsonUtil.json2Bean(context, responseInfo.result, DuoBaoTabNumberBean.class);

                EventBus.getDefault().post(new DuoBaoNumberEvent(duoBaoCodeBean));
            }

            @Override
            public void onFailure(HttpException e, String s) {
                EventBus.getDefault().post(new DuoBaoNumberEvent(duoBaoCodeBean));
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }

}

