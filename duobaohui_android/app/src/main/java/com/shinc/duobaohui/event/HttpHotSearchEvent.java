package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.HotSearchBean;

/**
 * Created by zpl on 15/7/14.
 */
public class HttpHotSearchEvent {
    private HotSearchBean hotSearchBean;

    public HttpHotSearchEvent(HotSearchBean hotSearchBean) {
        this.hotSearchBean = hotSearchBean;
    }

    public HotSearchBean getHotSearchBean() {
        return hotSearchBean;
    }
}
