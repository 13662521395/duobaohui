package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.SearchResultBean;

/**
 * Created by zpl on 15/7/14.
 */
public class HttpSearchHistoryEvent {
    private SearchResultBean hotSearchBean;

    public HttpSearchHistoryEvent(SearchResultBean hotSearchBean) {
        this.hotSearchBean = hotSearchBean;
    }

    public SearchResultBean getHotSearchBean() {
        return hotSearchBean;
    }
}
