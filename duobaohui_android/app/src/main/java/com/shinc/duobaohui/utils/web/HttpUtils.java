package com.shinc.duobaohui.utils.web;

import android.content.Context;

import com.lidroid.xutils.http.RequestParams;

import java.util.List;


/**
 * http工具类
 * 用于
 * Created by chaos on 15/6/25.
 */
public class HttpUtils {
    public void sendHttpPost(RequestParams requestParams, String url, HttpSendInterFace httpSendInterface, final Context context) {
        //这里进行公用的对网络的操作
        httpSendInterface.sendPostRequest(requestParams, url, context);
    }

    public void sendHttpPost(final RequestParams requestParams, String url, final List<String> request,HttpSendInterFace httpSendInterface, final Context context) {
        //这里进行公用的对网络的操作
        httpSendInterface.sendPostRequest(requestParams, url, request,context);
    }
}
