package com.shinc.duobaohui.customview.web;


import android.webkit.WebView;

import com.shinc.duobaohui.bean.ProductContentBean;

/**
 * 名称：NewsContentViewInterface
 * 作者：zhaopl 时间: 15/7/1.
 * 实现的主要功能：
 * 文章内容模块中，View层的顶级接口类；
 */
public interface ProductContentViewInterface {


    /**
     * 点击调转到评论页面；
     *
     * @param mListener
     */
    //void setNewsCommentClick(NewsCommentNumClickListener mListener);

    /**
     * 点击操作时实现并回调；
     *
     * @param newsContentMenuClickListener
     */
   // void setNewsMenuClick(NewsContentMenuClickListener newsContentMenuClickListener);

    /**
     * 点击添加评论时实现并回调；
     *
     * @param newsContentAddCommentClickListner
     */
    //void setAddCommentClick(NewsContentAddCommentClickListener newsContentAddCommentClickListner);

    /**
     * 设置新闻主题内容；
     *
     * @param productContentBean
     */
    void setNewsContentDate(ProductContentBean productContentBean);


    //void initAddCommentEditText();


    void showLoadingView();

    void hideLoadingView();

    void showReloadView();

    void hideReloadView();

    void reloadData(NewsContentReloadData newsContentReloadData);


    interface NewsContentReloadData {
        void reloadData();
    }


    WebView getWebView();
    /**
     * 用户点击评论时实现并回调；
     */
//    public interface NewsCommentNumClickListener {
//
//        void clickComment();
//
//
//    }


    /**
     * 点击Menu时候实现并回调；
     */
//    public interface NewsContentMenuClickListener {
//
//        void clickMenu();
//
//        void deliverMenuReport();
//
//        void deliverMenuShare();
//
//        void deliverMenuCollect();
//
//        void deliverReportReason(String reasonId);
//    }


    /**
     * 点击添加评论时实现并回调；
     */
//    public interface NewsContentAddCommentClickListener {
//        void AddCommentClick(String comment);
//    }




}
