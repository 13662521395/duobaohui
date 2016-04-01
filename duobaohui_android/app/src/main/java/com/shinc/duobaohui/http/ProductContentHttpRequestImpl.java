package com.shinc.duobaohui.http;

import android.content.Context;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.ProductContentBean;
import com.shinc.duobaohui.event.HttpProductContentEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * 名称：ArticleInfoHttpRequestImpl
 * 作者：zhaopl 时间: 15/7/4.
 * 实现的主要功能：文章页面请求；
 */
public class ProductContentHttpRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true, false, context, new MyHttpUtils.MyRequestCallBack() {
            ProductContentBean articleInfoBean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //todo 用gson等对返回数据进行解析


                articleInfoBean = GsonUtil.json2Bean(context, responseInfo.result, ProductContentBean.class);


                EventBus.getDefault().post(new HttpProductContentEvent(articleInfoBean));

            }

            @Override
            public void onFailure(HttpException e, String s) {

                EventBus.getDefault().post(new HttpProductContentEvent(articleInfoBean));
            }
        });


    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {

    }
}
