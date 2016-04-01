package com.shinc.duobaohui.bean;

/**
 * 名称：PayStatusBean
 * 作者：zhaopl 时间: 16/1/20.
 * 实现的主要功能：
 */
public class PayStatusBean {

    private String code;
    private String msg;
    private PayStatus data;


    public class PayStatus {
        private String alipay_show;
        private String weixinpay_show;

        public PayStatus() {
        }

        public PayStatus(String alipay_show, String weixinpay_show) {
            this.alipay_show = alipay_show;
            this.weixinpay_show = weixinpay_show;
        }

        public String getAlipay_show() {
            return alipay_show;
        }

        public void setAlipay_show(String alipay_show) {
            this.alipay_show = alipay_show;
        }

        public String getWeixinpay_show() {
            return weixinpay_show;
        }

        public void setWeixinpay_show(String weixinpay_show) {
            this.weixinpay_show = weixinpay_show;
        }

        @Override
        public String toString() {
            return "PayStatus{" +
                    "alipay_show='" + alipay_show + '\'' +
                    ", weixinpay_show='" + weixinpay_show + '\'' +
                    '}';
        }
    }


    public PayStatusBean() {

    }

    public PayStatusBean(String code, String msg, PayStatus data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

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

    public PayStatus getData() {
        return data;
    }

    public void setData(PayStatus data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PayStatusBean{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
