package com.shinc.duobaohui.event;

/**
 * Created by efort on 15/10/10.
 */
public class HttpUploadImgEvent {

    private int bean;

    public HttpUploadImgEvent(int bean) {

        this.bean = bean;
    }

    public int getNum() {
        return bean;
    }
}
