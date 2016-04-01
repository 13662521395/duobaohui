package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.CouponsHttpResultBean;
import com.shinc.duobaohui.event.CouponsEnableEvent;
import com.shinc.duobaohui.event.CouponsOverdueEvent;
import com.shinc.duobaohui.event.CouponsUnableEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @作者: efort
 * @日期: 15/12/21 - 11:25
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsListRequestImpl implements HttpSendInterFace {
    private String flag = "";

    public CouponsListRequestImpl(String flag) {
        this.flag = flag;
    }


    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {
        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            private CouponsHttpResultBean couponsResultBean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                couponsResultBean = GsonUtil.json2Bean(context, responseInfo.result, CouponsHttpResultBean.class);
                //  flag: enable 可用  unable:不可用／过期
                switch (flag) {
                    case "enable":
                        EventBus.getDefault().post(new CouponsEnableEvent(couponsResultBean));
                        break;
                    case "unable":
                        EventBus.getDefault().post(new CouponsUnableEvent(couponsResultBean));
                        break;
                    case "overdue":
                        EventBus.getDefault().post(new CouponsOverdueEvent(couponsResultBean));
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                switch (flag) {
                    case "enable":
                        EventBus.getDefault().post(new CouponsEnableEvent(couponsResultBean));
                        break;
                    case "unable":
                        EventBus.getDefault().post(new CouponsUnableEvent(couponsResultBean));
                        break;
                    case "overdue":
                        EventBus.getDefault().post(new CouponsOverdueEvent(couponsResultBean));
                        break;
                }
            }
        });
    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {

    }
}
