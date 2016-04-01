package com.shinc.duobaohui.event;


import com.shinc.duobaohui.bean.FeedbackBean;

/**
 * Created by liugaopo on 15/9/23.
 */
public class HttpFeedbackEvent {

    private FeedbackBean feedbackBean;

    public FeedbackBean getFeedbackBean() {
        return feedbackBean;
    }

    public HttpFeedbackEvent(FeedbackBean feedbackBean) {
        this.feedbackBean = feedbackBean;
    }
}
