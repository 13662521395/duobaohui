package com.shinc.duobaohui.customview.web.impl;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.ProductContentBean;
import com.shinc.duobaohui.customview.ProductContentWebViewInterface;
import com.shinc.duobaohui.customview.imp.ProductContentWebViewImpl;
import com.shinc.duobaohui.customview.web.ProductContentViewInterface;

/**
 * 名称：NewsContentViewImpl(文章内容主要的呈现页面)
 * 作者：zhaopl 时间: 15/7/2.
 * 实现的主要功能：
 * 呈现文章内容；
 */
public class ProductContentViewImpl implements ProductContentViewInterface {

    private Activity mActivity;

    /**
     * Viwe层第二层的自定义View;
     */

    //回退按钮；
    private ImageView ivBack;
    //用户操作按钮；
    private ImageView ivMenu;
    //评论条数；
    private TextView tvCommentNum;
    //记载html页面的WebView；
    private ProductContentWebViewInterface newContentWebViewImpl;
    //用户添加评论的二级View;

    private LayoutInflater inflater;

    private ProgressBar loadingIv;

    private RelativeLayout reloadImg;

    public ProductContentViewImpl(Activity activity) {
        this.mActivity = activity;

        activity.setContentView(R.layout.activity_product_detail_content);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        settingView();

    }

    /**
     * 初始化页面；
     */
    private void settingView() {

        newContentWebViewImpl = new ProductContentWebViewImpl(mActivity);

        ivBack = (ImageView) mActivity.findViewById(R.id.news_content_title_backimg);

        ivMenu = (ImageView) mActivity.findViewById(R.id.news_content_title_menuimg);

        tvCommentNum = (TextView) mActivity.findViewById(R.id.news_content_comment_numtv);

        //承载正文内容的View;
        newContentWebViewImpl = (ProductContentWebViewImpl) mActivity.findViewById(R.id.news_content_webviewimpl);

        //添加评论View；

        loadingIv = (ProgressBar) mActivity.findViewById(R.id.news_content_loading_iv);

        reloadImg = (RelativeLayout) mActivity.findViewById(R.id.news_content_reload_img);



        setListener();
    }

    /**
     * 初始化新闻内容；
     */
    private void initContent(ProductContentBean productContentBean) {

        newContentWebViewImpl.initWebContent(productContentBean.getData().getGoods_desc().replaceAll(",", ""));
    }

    /**
     * 设置控件的点击事件；
     */
    private void setListener() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });


    }


    /**
     * 初始化页面数据；
     */
    @Override
    public void setNewsContentDate(ProductContentBean productContentBean) {
        initContent(productContentBean);
    }


    @Override
    public void showLoadingView() {
        if (loadingIv.getVisibility() == View.GONE) {
            loadingIv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoadingView() {

        if (loadingIv.getVisibility() == View.VISIBLE) {
            loadingIv.setVisibility(View.GONE);
        }
    }

    @Override
    public void showReloadView() {
        if (reloadImg.getVisibility() == View.GONE) {
            reloadImg.setVisibility(View.VISIBLE);
            reloadImg.setClickable(true);
        }
    }

    @Override
    public void hideReloadView() {

        if (reloadImg.getVisibility() == View.VISIBLE) {
            reloadImg.setVisibility(View.GONE);
            reloadImg.setClickable(false);
        }
    }

    @Override
    public void reloadData(final NewsContentReloadData newsContentReloadData) {
        reloadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsContentReloadData.reloadData();
            }
        });
    }

    @Override
    public WebView getWebView() {
        return newContentWebViewImpl.getWebView();
    }


}

