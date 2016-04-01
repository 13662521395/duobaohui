package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.UserWinRecrodBean;

/**
 * Created by liugaopo on 15/11/23.
 */
public class UserWinRecrodEvent {
    private UserWinRecrodBean bean;

    public UserWinRecrodEvent(UserWinRecrodBean bean) {
        this.bean = bean;
    }

    public UserWinRecrodBean getBean() {
        return bean;
    }
}
