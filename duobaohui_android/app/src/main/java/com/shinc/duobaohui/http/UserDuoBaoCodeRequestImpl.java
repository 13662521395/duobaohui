package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.UserDuoBaoCodeBean;
import com.shinc.duobaohui.event.UserDuoBaoCodeEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/8.
 */
public class UserDuoBaoCodeRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(final RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            UserDuoBaoCodeBean duoBaoCodeBean;

            @Override
            public void onSuccess(final ResponseInfo<String> responseInfo) {
                duoBaoCodeBean = GsonUtil.json2Bean(context, responseInfo.result, UserDuoBaoCodeBean.class);

                EventBus.getDefault().post(new UserDuoBaoCodeEvent(duoBaoCodeBean));

            }

            @Override
            public void onFailure(HttpException e, String s) {

                EventBus.getDefault().post(new UserDuoBaoCodeEvent(duoBaoCodeBean));
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }

}
