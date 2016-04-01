package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.DuoBaoBiPayBean;

/**
 * Created by liugaopo on 15/10/15.
 */
public class UserInfoEvent {

    private DuoBaoBiPayBean duoBaoBiPayBean;

    public DuoBaoBiPayBean getDuoBaoBiPayBean() {
        return duoBaoBiPayBean;
    }

    public UserInfoEvent(DuoBaoBiPayBean duoBaoBiPayBean) {
        this.duoBaoBiPayBean = duoBaoBiPayBean;
    }
}
