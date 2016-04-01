package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.SearchResultBean;

/**
 * 名称：SearchResultEvent
 * 作者：zhaopl 时间: 15/11/18.
 * 实现的主要功能：
 */
public class SearchResultEvent {

    private SearchResultBean searchResultBean;

    public SearchResultEvent(SearchResultBean searchResultBean) {
        this.searchResultBean = searchResultBean;
    }

    public SearchResultBean getSearchResultBean() {
        return searchResultBean;
    }
}
