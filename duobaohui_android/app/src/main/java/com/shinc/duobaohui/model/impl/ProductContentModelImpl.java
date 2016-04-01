package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.ProductContentHttpRequestImpl;
import com.shinc.duobaohui.model.ProductContentModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * 名称：NewsContentModelImpl
 * 作者：zhaopl 时间: 15/7/4.
 * 实现的主要功能：
 */
public class ProductContentModelImpl implements ProductContentModelInterface {

    /**
     * 传递过来的Activity参数；
     */
    private Activity mActivity;

    private String articleId;
    private HttpUtils httpUtils;

    public ProductContentModelImpl(Activity mActivity, String articleId) {

        this.mActivity = mActivity;
        this.articleId = articleId;
        httpUtils = new HttpUtils();
        initData(articleId);
    }


    /**
     * 初始化数据，加载本页的数据；
     *
     * @param articleId
     */
    @Override
    public void initData(String articleId) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("goods_id", articleId);
        httpUtils.sendHttpPost(requestParams, ConstantApi.GOOD_DESC, new ProductContentHttpRequestImpl(), mActivity);
    }

}
