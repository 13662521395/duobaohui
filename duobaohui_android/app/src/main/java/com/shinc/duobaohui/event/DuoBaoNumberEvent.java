package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.DuoBaoTabNumberBean;

/**
 * Created by liugaopo on 15/11/6.
 */
public class DuoBaoNumberEvent {

    private DuoBaoTabNumberBean duoBaoTabNumberBean;

    public DuoBaoTabNumberBean getDuoBaoTabNumberBean() {
        return duoBaoTabNumberBean;
    }

    public DuoBaoNumberEvent(DuoBaoTabNumberBean duoBaoTabNumberBean) {
        this.duoBaoTabNumberBean = duoBaoTabNumberBean;
    }
}
