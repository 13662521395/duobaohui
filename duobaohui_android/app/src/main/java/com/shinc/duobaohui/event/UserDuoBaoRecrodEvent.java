package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.UserDuoBaoBean;

/**
 * Created by liugaopo on 15/11/23.
 */
public class UserDuoBaoRecrodEvent {
    private UserDuoBaoBean duoBaoBean;

    public UserDuoBaoRecrodEvent(UserDuoBaoBean duoBaoBean) {

        this.duoBaoBean = duoBaoBean;
    }

    public UserDuoBaoBean getDuoBaoBean() {
        return duoBaoBean;
    }
}
