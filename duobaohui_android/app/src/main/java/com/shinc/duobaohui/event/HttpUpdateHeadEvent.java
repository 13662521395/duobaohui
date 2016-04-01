package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.UpdateHeadBean;

/**
 * Created by yangtianhe on 15/10/12.
 */
public class HttpUpdateHeadEvent {
    private UpdateHeadBean updateHeadBean;

    public UpdateHeadBean getUpdateHeadBean() {
        return updateHeadBean;
    }

    public void setUpdateHeadBean(UpdateHeadBean updateHeadBean) {
        this.updateHeadBean = updateHeadBean;
    }

    public HttpUpdateHeadEvent(UpdateHeadBean updateHeadBean) {

        this.updateHeadBean = updateHeadBean;
    }

    @Override
    public String toString() {
        return "HttpUpdateHeadEvent{" +
                "updateHeadBean=" + updateHeadBean +
                '}';
    }
}
