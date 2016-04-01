package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.IndexGetWinnerListBean;

/**
 * Created by zpl on 15/7/14.
 */
public class HttpIndexWinnerListEvent {
    private IndexGetWinnerListBean indexGetWinnerListBean;

    public HttpIndexWinnerListEvent(IndexGetWinnerListBean indexGetWinnerListBean) {
        this.indexGetWinnerListBean = indexGetWinnerListBean;
    }

    public IndexGetWinnerListBean getIndexGetWinnerListBean() {
        return indexGetWinnerListBean;
    }
}
