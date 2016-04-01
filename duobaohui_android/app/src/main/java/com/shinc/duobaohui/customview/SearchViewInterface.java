package com.shinc.duobaohui.customview;

import android.view.View;
import android.widget.BaseAdapter;

/**
 * 名称：SearchViewInterface
 * 作者：zhaopl 时间: 15/9/6.
 * 实现的主要功能：
 * 搜索页面接口；
 */
public interface SearchViewInterface {


    /**
     * 返回键监听；
     *
     * @param backListener
     */
    void back(BackListener backListener);

    interface BackListener {
        void onClickBack();
    }


    interface SearchListener {
        void onSearch();
    }


    /**
     * 给热门搜索赋值；
     */
    void setHotSearchData(BaseAdapter hotSearchAdapter);


    /**
     * 给实时搜索结果赋值；
     */
    void setSearchRealTimeResultData(BaseAdapter searchRealTimeResultAdapter);

    /**
     * 给搜索历史赋值；
     */
    void setSearchHistoryData(BaseAdapter searchHistoryAdapter);


    /**
     * 点击Item时的监听；
     *
     * @param itemClickListner
     */
    void onItemClick(ItemClickListner itemClickListner);

    interface ItemClickListner {
        void clickHotSearchItem(int position);

        void clickSearchRealTimeResultItem(int position);

        void clickSearchHistoryItem(int position);
    }

    void setSearchEditHintText(String text);


    void setSearchEditTextChange(SearchEditTextChangeListener searchEditTextChangeListener);


    interface SearchEditTextChangeListener {

        void onTextChanged(String keyword);

    }

    /**
     * 设置搜索结果的显示状态；
     */
    void setSearchResultShowState(boolean flag);


    void clickSearchBtn(ClickSearchBtnListener clickSearchBtnListener);

    interface ClickSearchBtnListener {

        void onClickSearchBtn(String searchWord);
    }

    /**
     * 设置历史搜索的显示；
     *
     * @param flag
     */
    void setHistorySearchShowState(boolean flag);


    String getSearchEditText();


    /**
     * 点击清除历史的响应；
     *
     * @param cleanSearchHistoryDeliver
     */
    void clickCleanHistoryBtn(CleanSearchHistoryDeliver cleanSearchHistoryDeliver);


    interface CleanSearchHistoryDeliver {
        void cleanHistory();
    }


    void setScrollListener(ListScrollToBottom listScrollToBottom);

    interface ListScrollToBottom {
        void scrollBottom();
    }

    void setSearchText(String str);


    View getSearchEdit();

    void setSearchResultTitle(String str);

    interface EditTextChangeListener{
        void textChange(String str);
    }

    void setTextChange(EditTextChangeListener editTextChangeListener);
}
