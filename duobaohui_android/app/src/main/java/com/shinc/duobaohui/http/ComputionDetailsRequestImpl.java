package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.ComputionalDetailsBean;
import com.shinc.duobaohui.event.ComputionalDetaillsEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/8.
 */
public class ComputionDetailsRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(final RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {

            ComputionalDetailsBean computionalDetailsBean = null;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                computionalDetailsBean = GsonUtil.json2Bean(context, responseInfo.result, ComputionalDetailsBean.class);

                EventBus.getDefault().post(new ComputionalDetaillsEvent(computionalDetailsBean));
            }

            @Override
            public void onFailure(HttpException e, String s) {
                EventBus.getDefault().post(new ComputionalDetaillsEvent(computionalDetailsBean));
            }
        });

    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }


}
