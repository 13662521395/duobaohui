package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.DuoBaoCodeBean;

/**
 * Created by liugaopo on 15/10/8.
 */
public class DuoBaoCodeEvent {
    private DuoBaoCodeBean baoCodeBean;

    public DuoBaoCodeBean getBaoCodeBean() {
        return baoCodeBean;
    }

    public DuoBaoCodeEvent(DuoBaoCodeBean baoCodeBean) {
        this.baoCodeBean = baoCodeBean;
    }
}
