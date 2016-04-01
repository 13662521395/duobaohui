package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.bean.SearchResultBean;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.event.HttpSearchHistoryEvent;
import com.shinc.duobaohui.http.SearchHotRequestImpl;
import com.shinc.duobaohui.http.SearchRequestImpl;
import com.shinc.duobaohui.model.SearchModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * 名称：ProductsModelImpl
 * 作者：zhaopl 时间: 15/8/21.
 * 实现的主要功能：
 * 搜索页面的Model层；
 */
public class SearchModelImpl implements SearchModelInterface {


    private Activity mActivity;

    private HttpUtils httpUtils;

    public SearchModelImpl(Activity activity) {

        httpUtils = new HttpUtils();

        this.mActivity = activity;
    }

    @Override
    public void back() {

        mActivity.finish();

    }

    /**
     * 获取热门搜索；
     *
     * @param dbUtils
     */
    @Override
    public void getHistorySearchData(DbUtils dbUtils) {

        List<SearchResultBean.HotSearch> hotSearches = null;
        try {
            hotSearches = dbUtils.findAll(Selector.from(SearchResultBean.HotSearch.class).where("hotId", "=", "history").orderBy("id", true).limit(5));
            hotSearches = exchangeList(hotSearches);
            if (hotSearches != null) {
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        EventBus.getDefault().post(new HttpSearchHistoryEvent(new SearchResultBean("1", "success", (ArrayList<SearchResultBean.HotSearch>) hotSearches)));
    }

    /**
     * 对数据进行转化；
     *
     * @param hotSearches
     * @return
     */
    private List<SearchResultBean.HotSearch> exchangeList(List<SearchResultBean.HotSearch> hotSearches) {

        SearchResultBean.HotSearch temp = null;

        if (hotSearches != null && hotSearches.size() > 1) {
            int top = hotSearches.size() - 1;
            int center = hotSearches.size() / 2;
            for (int i = 0; i < center; i++) {
                temp = hotSearches.get(i);
                hotSearches.set(i, hotSearches.get(top - i));
                hotSearches.set(top - i, temp);
            }
        }
        return hotSearches;
    }

    /**
     * 获取热门搜素；
     */
    @Override
    public void getHotSearchData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        httpUtils.sendHttpPost(requestParams,ConstantApi.SEARCH_HOT, new SearchHotRequestImpl(),mActivity);

//        requestParams.addBodyParameter("element", element);
//        LoginHttpRequest loginHttpRequest = new LoginHttpRequest();
//        HttpSendInterFace productsHttpRequest = new ProductsHttpRequestImpl();
//        HttpSendInterFace hotSearchHttpRequest = new HotSearchHttpRequestImpl();
//        httpUtils.sendHttpPost(requestParams, Constant.HOST_LOGIN, hotSearchHttpRequest, mActivity);
    }

    /**
     * 获取实时搜索；
     *
     * @param keyword
     */
    @Override
    public void getRealTimeSearchData(String keyword) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();

        requestParams.addBodyParameter("keyword", keyword);
        //httpUtils.sendHttpPost(requestParams, ConstantApi.SEARCH_REAL_TIME, new RealTimeSearchResultHttpRequestImpl(), mActivity);
    }

    /**
     * 搜索的操作；
     *
     * @param keyword
     */
    @Override
    public void search(String keyword, int page) {

        RequestParams requestParams = new RequestParams();

        requestParams.addBodyParameter("keyword", keyword);
        requestParams.addBodyParameter("page", page + "");
        requestParams.addBodyParameter("length", "40");
        httpUtils.sendHttpPost(requestParams, ConstantApi.CATEGORY_SEARCH, new SearchRequestImpl(), mActivity);
    }

    /**
     * 清除搜索数据；
     *
     * @param dbUtils
     */
    @Override
    public void cleanSearchHistory(DbUtils dbUtils) {

        List<SearchResultBean.HotSearch> hotSearches = null;
        try {
            dbUtils.deleteAll(SearchResultBean.HotSearch.class);
            hotSearches = dbUtils.findAll(Selector.from(SearchResultBean.HotSearch.class).where("hotId", "=", "history").limit(5));
        } catch (DbException e) {
            e.printStackTrace();
        }

        EventBus.getDefault().post(new HttpSearchHistoryEvent(new SearchResultBean("1", "success", (ArrayList<SearchResultBean.HotSearch>) hotSearches)));
    }

}
