package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.TakePartListBean;

/**
 * Created by zpl on 15/7/14.
 */
public class HttpTakePartListEvent {
    private TakePartListBean takePartListBean;

    public HttpTakePartListEvent(TakePartListBean takePartListBean) {
        this.takePartListBean = takePartListBean;
    }

    public TakePartListBean getTakePartListBean() {
        return takePartListBean;
    }
}
