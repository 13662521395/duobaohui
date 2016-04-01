package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.CouponsDetailsHttpBean;

/**
 * @作者: efort
 * @日期: 15/12/18 - 16:44
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsDetailsEvent {
    private CouponsDetailsHttpBean couponsDetailsHttpBeanBean;

    public CouponsDetailsEvent(CouponsDetailsHttpBean baseResp) {
        this.couponsDetailsHttpBeanBean = baseResp;
    }

    public CouponsDetailsHttpBean getCouponsBean() {
        return couponsDetailsHttpBeanBean;
    }
}
