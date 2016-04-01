package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.ShaiDanBean;
import com.shinc.duobaohui.event.HttpGetShowOrderEvent;
import com.shinc.duobaohui.event.HttpGetShowOrderShareEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by efort on 15/10/12.
 * 晒单
 */
public class GetShowOrderHttpRequestImpl implements HttpSendInterFace {
    String flag;//判断event发送到那个类

    public GetShowOrderHttpRequestImpl(String flag) {
        this.flag = flag;
    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            ShaiDanBean shaiDanBean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                shaiDanBean = GsonUtil.json2Bean(context, responseInfo.result, ShaiDanBean.class);
                switch (flag) {
                    case "1":
                        //我的晒单
                        EventBus.getDefault().post(new HttpGetShowOrderEvent(shaiDanBean));
                        break;
                    case "2":
                        //晒单分享
                        EventBus.getDefault().post(new HttpGetShowOrderShareEvent(shaiDanBean));
                        break;
                    case "3":
                        EventBus.getDefault().post(new HttpGetShowOrderEvent(shaiDanBean));
                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                switch (flag) {
                    case "1":
                        //我的晒单
                        EventBus.getDefault().post(new HttpGetShowOrderEvent(shaiDanBean));
                        break;
                    case "2":
                        EventBus.getDefault().post(new HttpGetShowOrderShareEvent(shaiDanBean));
                        break;
                    case "3":
                        EventBus.getDefault().post(new HttpGetShowOrderEvent(shaiDanBean));

                        break;
                }
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}
