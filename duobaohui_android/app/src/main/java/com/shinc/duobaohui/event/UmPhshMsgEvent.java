package com.shinc.duobaohui.event;

/**
 * Created by liugaopo on 15/11/6.
 */
public class UmPhshMsgEvent {
    private String id;
    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UmPhshMsgEvent(String id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "UmPhshMsgEvent{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
