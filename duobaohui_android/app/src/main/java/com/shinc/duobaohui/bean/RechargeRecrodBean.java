package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liugaopo on 15/10/10.
 */
public class RechargeRecrodBean implements Serializable {
    private String code;
    private String msg;
    private List<RechargeRecrodBean.Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RechargeRecrodBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {
        private String jnl_status;
        private String amount;
        private String amount_pay;
        private String recharge_channel;
        private String create_time;
        private String status;

        public Data(String jnl_status, String amount, String amount_pay, String recharge_channel, String create_time, String status) {
            this.jnl_status = jnl_status;
            this.amount = amount;
            this.amount_pay = amount_pay;
            this.recharge_channel = recharge_channel;
            this.create_time = create_time;
            this.status = status;
        }

        public String getJnl_status() {
            return jnl_status;
        }

        public void setJnl_status(String jnl_status) {
            this.jnl_status = jnl_status;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAmount_pay() {
            return amount_pay;
        }

        public void setAmount_pay(String amount_pay) {
            this.amount_pay = amount_pay;
        }

        public String getRecharge_channel() {
            return recharge_channel;
        }

        public void setRecharge_channel(String recharge_channel) {
            this.recharge_channel = recharge_channel;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "jnl_status='" + jnl_status + '\'' +
                    ", amount='" + amount + '\'' +
                    ", amount_pay='" + amount_pay + '\'' +
                    ", recharge_channel='" + recharge_channel + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

}
