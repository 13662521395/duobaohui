package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.UserDuoBaoCodeBean;

/**
 * Created by liugaopo on 15/10/8.
 */
public class UserDuoBaoCodeEvent {
    private UserDuoBaoCodeBean userDuoBaoCodeBean;

    public UserDuoBaoCodeEvent(UserDuoBaoCodeBean userDuoBaoCodeBean) {
        this.userDuoBaoCodeBean = userDuoBaoCodeBean;
    }

    public UserDuoBaoCodeBean getUserDuoBaoCodeBean() {
        return userDuoBaoCodeBean;
    }
}
