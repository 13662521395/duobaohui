package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.DuoBaoBiPayBean;

/**
 * Created by liugaopo on 15/10/13.
 */
public class DuoBaiBiEvent {
    private DuoBaoBiPayBean getVerifyCodeBean;

    public DuoBaoBiPayBean getGetVerifyCodeBean() {
        return getVerifyCodeBean;
    }

    public DuoBaiBiEvent(DuoBaoBiPayBean getVerifyCodeBean) {
        this.getVerifyCodeBean = getVerifyCodeBean;
    }

}
