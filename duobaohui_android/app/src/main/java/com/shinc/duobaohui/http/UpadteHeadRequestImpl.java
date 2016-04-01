package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.UpdateHeadBean;
import com.shinc.duobaohui.event.HttpUpdateHeadEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by yangtianhe on 15/10/12.
 */
public class UpadteHeadRequestImpl implements HttpSendInterFace {
    @Override
    public void sendPostRequest(final RequestParams requestParams, String url, final Context context) {


        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            UpdateHeadBean updateHeadBean;

            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {
                updateHeadBean = GsonUtil.json2Bean(context, responseInfo.result, UpdateHeadBean.class);

                EventBus.getDefault().post(new HttpUpdateHeadEvent(updateHeadBean));

            }

            @Override
            public void onFailure(HttpException e, String s) {

                EventBus.getDefault().post(new HttpUpdateHeadEvent(updateHeadBean));
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}
