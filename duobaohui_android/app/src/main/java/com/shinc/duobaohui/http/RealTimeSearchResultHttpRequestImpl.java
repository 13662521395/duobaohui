package com.shinc.duobaohui.http;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.SearchResultBean;
import com.shinc.duobaohui.event.SearchResultEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 搜索的网络请求；
 */
public class RealTimeSearchResultHttpRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false,context, new MyHttpUtils.MyRequestCallBack() {
            SearchResultBean bean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                bean = GsonUtil.json2Bean(context, responseInfo.result, SearchResultBean.class);

                EventBus.getDefault().post(new SearchResultEvent(bean));

                if (bean == null || "0000".equals(bean.getCode()) || "10021".equals(bean.getCode())) {

                } else if (bean.getCode().equals("10013") || "5004".equals(bean.getCode())) {
                    Toast.makeText(context, bean.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "网络故障,请稍后再试！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                EventBus.getDefault().post(new SearchResultEvent(bean));
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}