package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.IndexBannerBean;

/**
 * Created by zpl on 15/7/14.
 * 首页banner图接口；
 */
public class HttpIndexBannerEvent {
    private IndexBannerBean indexBannerBean;

    public HttpIndexBannerEvent(IndexBannerBean indexBannerBean) {
        this.indexBannerBean = indexBannerBean;
    }

    public IndexBannerBean getIndexBannerBean() {
        return indexBannerBean;
    }
}
