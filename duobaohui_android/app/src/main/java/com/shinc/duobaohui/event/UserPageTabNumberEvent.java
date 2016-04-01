package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.UserPageTabNumberBean;

/**
 * Created by liugaopo on 15/11/24.
 */
public class UserPageTabNumberEvent {

    private UserPageTabNumberBean bean;

    public UserPageTabNumberEvent(UserPageTabNumberBean bean) {
        this.bean = bean;
    }
    public UserPageTabNumberBean getBean() {
        return bean;
    }
}
