package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.NoticeListBean;

/**
 * Created by yangtianhe on 15/11/20.
 */
public class HttpNoticeListEvent {
    private NoticeListBean listBean;

    public NoticeListBean getListBean() {
        return listBean;
    }

    public HttpNoticeListEvent(NoticeListBean listBean) {

        this.listBean = listBean;
    }
}
