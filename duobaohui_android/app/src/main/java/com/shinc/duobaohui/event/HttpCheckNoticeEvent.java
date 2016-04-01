package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.FeedbackBean;

/**
 * Created by liugaopo on 15/10/19.
 * 查看消息是否更新的Event：
 */
public class HttpCheckNoticeEvent {
    private FeedbackBean bean;

    public HttpCheckNoticeEvent(FeedbackBean bean) {
        this.bean = bean;
    }

    public FeedbackBean getBean() {
        return bean;
    }
}
