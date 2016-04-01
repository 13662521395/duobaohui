package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.PreAnnounceListBean;

/**
 * Created by liugaopo on 15/10/6.
 * 获取往期揭晓的数据的操作；
 */
public class HttpPreAnnounceListEvent {
    private PreAnnounceListBean preAnnounceListBean;

    public HttpPreAnnounceListEvent(PreAnnounceListBean preAnnounceListBean) {
        this.preAnnounceListBean = preAnnounceListBean;
    }

    public PreAnnounceListBean getPreAnnounceListBean() {
        return preAnnounceListBean;
    }
}
