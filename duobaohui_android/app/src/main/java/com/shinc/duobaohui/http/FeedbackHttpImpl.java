package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.FeedbackBean;
import com.shinc.duobaohui.event.HttpFeedbackEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/9/23.
 */
public class FeedbackHttpImpl implements HttpSendInterFace {
    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {


        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, true, context, new MyHttpUtils.MyRequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                FeedbackBean feedbackBean = GsonUtil.json2Bean(context, responseInfo.result, FeedbackBean.class);
                EventBus.getDefault().post(new HttpFeedbackEvent(feedbackBean));
            }

            @Override
            public void onFailure(HttpException e, String s) {
                EventBus.getDefault().post(new HttpFeedbackEvent(null));
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {

    }
}
