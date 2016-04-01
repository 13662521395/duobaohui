package com.shinc.duobaohui.utils.web;

import android.content.Context;

import com.lidroid.xutils.http.RequestParams;

import java.util.List;


/**
 * Created by chaos on 15/6/18.
 * 网络请求接口，所有网络请求需要实现该接口
 */
public interface HttpSendInterFace {

    void sendPostRequest(RequestParams requestParams, String url, final Context context);

    void sendPostRequest(final RequestParams requestParams, String url, List<String> request, final Context context);
}