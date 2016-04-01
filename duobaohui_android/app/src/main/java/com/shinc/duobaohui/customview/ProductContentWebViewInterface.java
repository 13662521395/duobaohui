package com.shinc.duobaohui.customview;

import android.content.SharedPreferences;
import android.webkit.WebView;

/**
 * 名称：NewsContentWebViewInterface
 * 作者：zhaopl 时间: 15/7/1.
 * 实现的主要功能：
 * 文章内容模块，View第二层WebView的实现接口类；
 */
public interface ProductContentWebViewInterface {


    /**
     * 读取用户对文字大小的设置，用于对页面的设置；
     */
    void initTextSize(SharedPreferences sharedPreferences, String key);


    /**
     * 加载新闻主体内容的方法；
     *
     * @param content
     */
    void initWebContent(String content);


    WebView getWebView();

}