package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.ShaiDanBean;

/**
 * Created by efort on 15/10/12.
 */
public class HttpGetShowOrderEvent {
    private ShaiDanBean shaiDanBean;

    public HttpGetShowOrderEvent(ShaiDanBean shaiDanBean) {
        this.shaiDanBean = shaiDanBean;
    }

    public ShaiDanBean getShaiDanBean() {
        return shaiDanBean;
    }
}
