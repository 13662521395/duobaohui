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
 * 名称：UserProtocolActivity
 * 作者：zhaopl 时间: 15/9/21.
 * 实现的主要功能：
 * 用户协议页面；
 */
public class UserProtocolActivity extends BaseActivity {

    private Context mContext;

    private CustomTextView titleText;

    private ImageView backImg;

    private WebView protocolWebView;

    private WebSettings webSet;
    private String flag;
    private String url;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null && getIntent().getExtras() != null) {
            flag = getIntent().getStringExtra("flag");
            if ("1".equals(flag)) {
                url = getIntent().getStringExtra("url");
            }
        }
        setContentView(R.layout.activity_user_protocol);

        mContext = this;
        loadingDialog = new LoadingDialog(mContext, R.style.dialog);
        initView();

        initData();

        initEvent();
    }

    /**
     * 初始化页面；
     */
    private void initView() {


        backImg = (ImageView) findViewById(R.id.user_protocol_back_img);
        protocolWebView = (WebView) findViewById(R.id.user_protocol_web_view);
        titleText = (CustomTextView) findViewById(R.id.user_protocol_title_tv);

        protocolWebView.setWebViewClient(new WebViewClient() {
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
        });
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
        if (!TextUtils.isEmpty(url)) {
            protocolWebView.loadUrl(url);
            titleText.setText("福彩查询");
        } else {
            protocolWebView.loadUrl("file:///android_asset/duobaohui_protocol.htm");
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
        protocolWebView.removeAllViews();
        protocolWebView.destroy();
    }
}
