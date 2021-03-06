package com.shinc.duobaohui.http;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.IndexGetWinnerListBean;
import com.shinc.duobaohui.event.HttpIndexWinnerListEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by efort on 15/7/14.
 * 登陆请求
 */
public class GetWinnerListHttpRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {


        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {

            IndexGetWinnerListBean bean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    bean = GsonUtil.json2Bean(context, responseInfo.result, IndexGetWinnerListBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new HttpIndexWinnerListEvent(bean));
                }

                EventBus.getDefault().post(new HttpIndexWinnerListEvent(bean));
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "网络连接失败,请您检查您的网络", Toast.LENGTH_LONG).show();


                EventBus.getDefault().post(new HttpIndexWinnerListEvent(bean));
            }
        });

    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}