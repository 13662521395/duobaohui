package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liugaopo on 15/10/6.
 */
public class WinnerRecrodNumBean implements Serializable {
    private String code;
    private String msg;
    private List<WinnerRecordChildBean> data;

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

    public List<WinnerRecordChildBean> getData() {
        return data;
    }

    public void setData(List<WinnerRecordChildBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WinnerRecrodNumBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class WinnerRecordChildBean {
        private String order_status_count_all;
        private String order_status_count_0;
        private String order_status_count_1;
        private String order_status_count_2;

        public String getOrder_status_count_0() {
            return order_status_count_0;
        }

        public void setOrder_status_count_0(String order_status_count_0) {
            this.order_status_count_0 = order_status_count_0;
        }

        public String getOrder_status_count_1() {
            return order_status_count_1;
        }

        public void setOrder_status_count_1(String order_status_count_1) {
            this.order_status_count_1 = order_status_count_1;
        }

        public String getOrder_status_count_2() {
            return order_status_count_2;
        }

        public void setOrder_status_count_2(String order_status_count_2) {
            this.order_status_count_2 = order_status_count_2;
        }

        public String getOrder_status_count_all() {
            return order_status_count_all;
        }

        public void setOrder_status_count_all(String order_status_count_all) {
            this.order_status_count_all = order_status_count_all;
        }

        @Override
        public String toString() {
            return "WinnerRecordChildBean{" +
                    "order_status_count_0='" + order_status_count_0 + '\'' +
                    ", order_status_count_1='" + order_status_count_1 + '\'' +
                    ", order_status_count_2='" + order_status_count_2 + '\'' +
                    '}';
        }
    }
}
