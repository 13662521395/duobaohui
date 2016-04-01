package com.shinc.duobaohui.bean;

/**
 * Created by liugaopo on 15/11/18.
 */
public class WxPayBean {
    /*{
    "code": 1,
    "msg": "成功",
    "data": {
        "return_code": "SUCCESS",
        "return_msg": "OK",
        "prepay_id": "wx201511182045350b1cf5f0650216393271",
        "trade_type": "APP",
        "nonce_str": "MWvKXP7JImaH4RTS",
        "timestamp": 1447850734,
        "sign": "821FB421A4959EA4C65A22D1AEFF4CC2"
    }
}*/

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WxPayBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {

        private String name;
        private String moeny;

        private String return_code;
        private String return_msg;
        private String prepay_id;
        private String trade_type;
        private String nonce_str;
        private String timestamp;
        private String sign;
        private String out_trade_no;

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoeny() {
            return moeny;
        }

        public void setMoeny(String moeny) {
            this.moeny = moeny;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "name='" + name + '\'' +
                    ", moeny='" + moeny + '\'' +
                    ", return_code='" + return_code + '\'' +
                    ", return_msg='" + return_msg + '\'' +
                    ", prepay_id='" + prepay_id + '\'' +
                    ", trade_type='" + trade_type + '\'' +
                    ", nonce_str='" + nonce_str + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", sign='" + sign + '\'' +
                    ", out_trade_no='" + out_trade_no + '\'' +
                    '}';
        }
    }

}
