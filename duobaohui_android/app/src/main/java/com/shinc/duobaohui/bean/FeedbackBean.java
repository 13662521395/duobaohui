package com.shinc.duobaohui.bean;

import java.io.Serializable;

/**
 * Created by liugaopo on 15/9/23.
 * 意见反馈
 */
public class FeedbackBean implements Serializable {

    private String code;
    private String msg;
    private Data data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public String toString() {
        return "FeedbackBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data.toString();
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data {
        private String latest_date;
        private String redpacket;

        public String getLatest_date() {
            return latest_date;
        }

        public void setLatest_date(String latest_date) {
            this.latest_date = latest_date;
        }

        public String getRedpacket() {
            return redpacket;
        }

        public void setRedpacket(String redpacket) {
            this.redpacket = redpacket;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "latest_date='" + latest_date + '\'' +
                    ", redpacket='" + redpacket + '\'' +
                    '}';
        }
    }
}
