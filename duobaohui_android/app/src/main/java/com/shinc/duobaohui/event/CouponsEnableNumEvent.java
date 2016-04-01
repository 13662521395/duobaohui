package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.CouponsNumBean;

/**
 * @作者: efort
 * @日期: 15/12/18 - 11:42
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsEnableNumEvent {
    private CouponsNumBean couponsNumBean;

    public CouponsEnableNumEvent(CouponsNumBean couponsNumBean) {
        this.couponsNumBean = couponsNumBean;
    }

    public CouponsNumBean getCouponsNumBean() {
        return couponsNumBean;
    }
}
