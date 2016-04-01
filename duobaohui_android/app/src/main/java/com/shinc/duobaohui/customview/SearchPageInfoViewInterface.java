package com.shinc.duobaohui.customview;

import android.widget.BaseAdapter;

/**
 * 名称：SearchPageInfoViewInterface
 * 作者：zhaopl 时间: 15/9/6.
 * 实现的主要功能：
 * 搜索页面显示的搜索历史和热门搜索的展示View；
 */
public interface SearchPageInfoViewInterface {

    /**
     * 给热搜列表赋值；
     */
    void initHotSearch(BaseAdapter hotSearchAdapter);

    /**
     * 为搜索历史赋值；
     */
    void initSearchHistory(BaseAdapter searchHistoryAdapter);


    /**
     * 点击热门搜索的响应；
     *
     * @param clickHotSearchItemListener
     */
    void clickHotSearchItem(ClickHotSearchItemListener clickHotSearchItemListener);

    interface ClickHotSearchItemListener {

        void onClickHotSearchItem(int position);
    }

    /**
     * 点击搜索历史的响应事件；
     *
     * @param clickSearchHistoryItemListener
     */
    void clickSearchHistoryItem(ClickSearchHistoryItemListener clickSearchHistoryItemListener);

    interface ClickSearchHistoryItemListener {

        void onClickSearchHistoryItem(int position);
    }

    /**
     * 隐藏/显示搜索历史title；
     */
    void hideSearchHistory(boolean flag);


    /**
     * 点击清除历史记录的响应事件；
     */
    void clickCleanHistorySearchBtn(CleanHistorySearchListener cleanHistorySearchListener);


    interface CleanHistorySearchListener{

        void cleanHidtorySearch();
    }


}
