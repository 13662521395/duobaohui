package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.WxPayBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.event.WxPayEvent;
import com.shinc.duobaohui.event.WxRechargeEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/11/18.
 * 微信预支付
 */
public class WxChargeRequestImpl implements HttpSendInterFace {

    private int tag;

    public WxChargeRequestImpl(int tag) {
        this.tag = tag;
    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {
        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            private WxPayBean bean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                bean = GsonUtil.json2Bean(context, responseInfo.result, WxPayBean.class);
                if (tag == Constant.WXRECHARGE) {
                    EventBus.getDefault().post(new WxRechargeEvent(bean));
                } else if (tag == Constant.WXPAY) {
                    EventBus.getDefault().post(new WxPayEvent(bean));
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                if (tag == Constant.WXRECHARGE) {
                    EventBus.getDefault().post(new WxRechargeEvent(bean));
                } else if (tag == Constant.WXPAY) {
                    EventBus.getDefault().post(new WxPayEvent(bean));
                }
            }
        });
    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }

}