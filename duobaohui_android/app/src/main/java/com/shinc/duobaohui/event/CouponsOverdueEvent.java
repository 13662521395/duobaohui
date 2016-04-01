package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.CouponsHttpResultBean;

/**
 * @作者: efort
 * @日期: 16/1/12 - 10:59
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsOverdueEvent {
    private CouponsHttpResultBean couponsBean;

    public CouponsOverdueEvent(CouponsHttpResultBean baseResp) {
        this.couponsBean = baseResp;
    }

    public CouponsHttpResultBean getCouponsBean() {
        return couponsBean;
    }
}
