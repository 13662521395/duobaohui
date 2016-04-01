package com.shinc.duobaohui.bean;

/**
 * Created by liugaopo on 15/11/24.
 */
public class UserPageTabNumberBean {

    private String code;
    private String msg;
    private UserPageTabNumberChildData data;

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

    public UserPageTabNumberChildData getData() {
        return data;
    }

    public void setData(UserPageTabNumberChildData data) {
        this.data = data;
    }

    public class UserPageTabNumberChildData {
        private String duobao_num;
        private String winning_num;
        private String shaidan_num;

        public String getDuobao_num() {
            return duobao_num;
        }

        public void setDuobao_num(String duobao_num) {
            this.duobao_num = duobao_num;
        }

        public String getWinning_num() {
            return winning_num;
        }

        public void setWinning_num(String winning_num) {
            this.winning_num = winning_num;
        }

        public String getShaidan_num() {
            return shaidan_num;
        }

        public void setShaidan_num(String shaidan_num) {
            this.shaidan_num = shaidan_num;
        }
    }

}
