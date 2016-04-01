package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.ShaiDanBean;

/**
 * Created by liugaopo on 15/10/20.
 */
public class HttpGetShowOrderShareEvent {
    private ShaiDanBean shaiDanBean;

    public ShaiDanBean getShaiDanBean() {
        return shaiDanBean;
    }

    public HttpGetShowOrderShareEvent(ShaiDanBean shaiDanBean) {
        this.shaiDanBean = shaiDanBean;
    }
}
