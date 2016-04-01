package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.CouponsHttpResultBean;

/**
 * @作者: efort
 * @日期: 15/12/17 - 20:55
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsUnableEvent {
    private CouponsHttpResultBean couponsBean;

    public CouponsUnableEvent(CouponsHttpResultBean baseResp) {
        this.couponsBean = baseResp;
    }

    public CouponsHttpResultBean getCouponsBean() {
        return couponsBean;
    }
}
