package com.shinc.duobaohui.model.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.CategoryContentRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;

/**
 * 名称：DeprivePreciousModelImpl
 * 作者：zhaopl 时间: 15/9/29.
 * 实现的主要功能：
 */
public class CategoryContentModelImpl {

    private Activity mActivity;
    private HttpUtils httpUtils;

    public CategoryContentModelImpl(Activity mActivity) {

        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }


    @SuppressWarnings("SameParameterValue")
    public void getProductInfo(String categoryId, @SuppressWarnings("SameParameterValue") String keyWord,int page) {
        RequestParams requestParams = new RequestParams();

        if (!TextUtils.isEmpty(categoryId)) {
            requestParams.addBodyParameter("category_id", categoryId);
        }
        if (!TextUtils.isEmpty(keyWord)) {
            requestParams.addBodyParameter("keyword", keyWord);
        }

        requestParams.addBodyParameter("page",page+"");

        // 需要按照商品的type请求不同筛选条件的查询结果进行显示；
        httpUtils.sendHttpPost(requestParams, ConstantApi.CATEGORY_SEARCH, new CategoryContentRequestImpl(), mActivity);
    }


}
