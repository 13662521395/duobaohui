package com.shinc.duobaohui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.customview.imp.CustomTextView;

/**
 * 名称：WebActiveActivity
 * 作者：zhaopl 时间: 15/9/21.
 * 实现的主要功能：
 * 活动页面；
 */
public class WebActiveActivity extends BaseActivity {

    private Context mContext;

    private CustomTextView titleText;

    private ImageView backImg;

    private WebView protocolWebView;

    private WebSettings webSet;
    private String type;
    private String url;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        if (getIntent() != null && getIntent().getExtras() != null) {
            type = getIntent().getStringExtra("type");
            if ("1".equals(type)) {//当type=1的时候，进行的操作是
                url = getIntent().getStringExtra("url");
            }
        }
        setContentView(R.layout.activity_web_active_layout);

        initView();

        initData();

        initEvent();
    }

    /**
     * 初始化页面；
     */
    private void initView() {

        loadingDialog = new LoadingDialog(mContext, R.style.dialog);
        backImg = (ImageView) findViewById(R.id.user_protocol_back_img);
        protocolWebView = (WebView) findViewById(R.id.user_protocol_web_view);
        titleText = (CustomTextView) findViewById(R.id.user_protocol_title_tv);
    }

    /**
     * 初始化数据；
     */
    private void initData() {

        webSet = protocolWebView.getSettings();
        // webSet.setSupportZoom(true);
        webSet.setJavaScriptEnabled(true);
        webSet.setSupportZoom(false);
        // webSet.setDefaultFontSize(50);
        webSet.setTextSize(WebSettings.TextSize.NORMAL);
        webSet.setBuiltInZoomControls(true);
        webSet.setUseWideViewPort(true);
        setUrl();


        protocolWebView.setWebViewClient(new MyWebViewClient());
    }

    private void setUrl() {
        if (!TextUtils.isEmpty(url)) {
            protocolWebView.loadUrl(url);
            titleText.setText("夺宝规则");
        }
    }

    /**
     * 初始化点击事件；
     */
    private void initEvent() {

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击后退按钮的时候进行的操作；

                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
        protocolWebView.clearHistory();
        protocolWebView.clearCache(true);//对数据缓存进行清除；
        protocolWebView.removeAllViews();
        protocolWebView.destroy();
    }


    private class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            setUrl();
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingDialog.showLoading();
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingDialog.hideLoading();
        }
    }
}
