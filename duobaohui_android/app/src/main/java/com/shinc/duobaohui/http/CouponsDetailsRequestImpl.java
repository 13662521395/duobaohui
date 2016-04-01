package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.CouponsDetailsHttpBean;
import com.shinc.duobaohui.event.CouponsDetailsEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @作者: efort
 * @日期: 15/12/21 - 11:33
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsDetailsRequestImpl implements HttpSendInterFace {
    @Override
    public void sendPostRequest(final RequestParams requestParams, String url, final Context context) {
        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            private CouponsDetailsHttpBean couponsDetailsHttpBean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                couponsDetailsHttpBean = GsonUtil.json2Bean(context, responseInfo.result, CouponsDetailsHttpBean.class);

                EventBus.getDefault().post(new CouponsDetailsEvent(couponsDetailsHttpBean));
            }

            @Override
            public void onFailure(HttpException e, String s) {
                EventBus.getDefault().post(new CouponsDetailsEvent(couponsDetailsHttpBean));
            }
        });
    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {

    }
}
