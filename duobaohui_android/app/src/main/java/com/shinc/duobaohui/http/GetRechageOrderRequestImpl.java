package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.AddOrderBean;
import com.shinc.duobaohui.event.RechageOrderEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/19.
 * 获取充值订单Id；
 */
public class GetRechageOrderRequestImpl implements HttpSendInterFace {

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            AddOrderBean addOrderBean = null;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                addOrderBean = GsonUtil.json2Bean(context, responseInfo.result, AddOrderBean.class);
                EventBus.getDefault().post(new RechageOrderEvent(addOrderBean));
            }

            @Override
            public void onFailure(HttpException e, String s) {
                EventBus.getDefault().post(new RechageOrderEvent(addOrderBean));
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }

}
