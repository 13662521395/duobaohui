package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.RechargeImgBean;

/**
 * Created by liugaopo on 15/10/19.
 * 生成Order的结果传递的Event：
 */
public class RechargeImgEvent {
    private RechargeImgBean rechargeImgBean;

    public RechargeImgEvent(RechargeImgBean rechargeImgBean) {
        this.rechargeImgBean = rechargeImgBean;
    }

    public RechargeImgBean getRechargeImgBean() {
        return rechargeImgBean;
    }
}
