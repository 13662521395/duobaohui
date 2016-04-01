package com.shinc.duobaohui.bean;

/**
 * Created by zhanggaoqi on 15/9/16.
 */
public class TokenBean {
    String code;
    String msg;
    Data data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {

        return code;
    }

    public Data getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public class Data {
        String token;

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {

            return token;
        }
    }
}
