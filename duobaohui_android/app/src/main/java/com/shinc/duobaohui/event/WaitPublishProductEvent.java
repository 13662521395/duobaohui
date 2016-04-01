package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.WaitPublishListBean;

/**
 * Created by liugaopo on 15/10/8.
 */
public class WaitPublishProductEvent {
    private WaitPublishListBean waitPublishListBean;

    public WaitPublishProductEvent(WaitPublishListBean waitPublishListBean) {
        this.waitPublishListBean = waitPublishListBean;
    }

    public WaitPublishListBean getWaitPublishListBean() {
        return waitPublishListBean;
    }
}
