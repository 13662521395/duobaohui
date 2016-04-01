package com.shinc.duobaohui.http;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shinc.duobaohui.bean.ProductDetailBean;
import com.shinc.duobaohui.event.HttpProductDetailEvent;
import com.shinc.duobaohui.utils.web.GsonUtil;
import com.shinc.duobaohui.utils.web.HttpSendInterFace;
import com.shinc.duobaohui.utils.web.MyHttpUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by efort on 15/7/14.
 * 登陆请求
 */
public class ProductDetailHttpRequestImpl implements HttpSendInterFace {


    @Override
    public void sendPostRequest(RequestParams requestParams, String url, final Context context) {

        MyHttpUtils.getInstance().send(HttpRequest.HttpMethod.POST, url, requestParams, true,false, context, new MyHttpUtils.MyRequestCallBack() {
            ProductDetailBean bean;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                bean = GsonUtil.json2Bean(context, responseInfo.result, ProductDetailBean.class);


                EventBus.getDefault().post(new HttpProductDetailEvent(bean));

                switch (bean.getCode()) {
                    case "1":
                        break;
                    default:
                        Toast.makeText(context, "获取商品详情出现异常，请稍后再试!", Toast.LENGTH_SHORT).show();

                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "网络连接失败,请您检查您的网络", Toast.LENGTH_SHORT).show();


                EventBus.getDefault().post(new HttpProductDetailEvent(bean));


            }
        });

    }

    @Override
    public void sendPostRequest(RequestParams requestParams, String url, List<String> request, Context context) {
    }
}