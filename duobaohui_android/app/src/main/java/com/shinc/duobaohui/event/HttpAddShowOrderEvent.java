package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.ShaiDanBeanResult;

/**
 * Created by liugaopo on 15/10/8.
 */
public class HttpAddShowOrderEvent {
    private ShaiDanBeanResult shaiDanBean;

    public ShaiDanBeanResult getShaiDanBean() {
        return shaiDanBean;
    }

    public HttpAddShowOrderEvent(ShaiDanBeanResult baoCodeBean) {
        this.shaiDanBean = baoCodeBean;
    }
}
