package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.ChangeNicknameBean;

/**
 * Created by yangtianhe on 15/10/12.
 */
public class HttpChangeNicknameEvent {
    private ChangeNicknameBean changeNicknameBean;

    public ChangeNicknameBean getChangeNicknameBean() {
        return changeNicknameBean;
    }

    public HttpChangeNicknameEvent(ChangeNicknameBean changeNicknameBean) {

        this.changeNicknameBean = changeNicknameBean;
    }
}
