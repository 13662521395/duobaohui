package com.shinc.duobaohui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.NoticeListBean;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;

/**
 * 通知详情
 * Created by yangtianhe on 15/11/20.
 */
public class NoticeDetailActivity extends BaseActivity {

    private ImageView imgBack;
    private Activity mActivity;

    private WebView webDetail;
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        initView();
    }

    private void initView() {
        getWindow().setBackgroundDrawable(null);
        mActivity = NoticeDetailActivity.this;
        imgBack = (ImageView) findViewById(R.id.notice_detail_back_img);
        NoticeListBean.NoticeChildListBean bean = (NoticeListBean.NoticeChildListBean) getIntent().getSerializableExtra("bean");
        loadingDialog = new LoadingDialog(mActivity, R.style.dialog);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

        webDetail = (WebView) findViewById(R.id.notice_detail_webview);
        webDetail.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webDetail.getSettings().setJavaScriptEnabled(true);
        webDetail.getSettings().setDisplayZoomControls(false);
        webDetail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webDetail.setWebViewClient(new ＷebViewClient1());

        StringBuilder strHtml = new StringBuilder();
        strHtml
                .append("<html> <meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=utf-8\\\" />")
                .append("<body topmargin=15 leftmargin=20>")
                .append("<font  color=\"#666666\">")
                .append(bean.getTitle())
                .append("</font>")
                .append("</br>")
                .append("<font  color=\"#afafaf\" topmargin=10>")
                .append(bean.getCreate_time())
                .append("</font>")
                .append("</br>")
                .append("</br>")
                .append(bean.getContent())
                .append("</body>")
                .append("</html>");
        webDetail.loadDataWithBaseURL(null, strHtml.toString().replaceAll("nowrap", "normal"), "text/html", "utf-8", null);

    }

    //Web视图
    private class ＷebViewClient1 extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
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
