package com.shinc.duobaohui.bean;

import java.io.Serializable;

/**
 * Created by liugaopo on 15/10/14.
 */
public class DuoBaoBiPayBean implements Serializable {

    private String code;
    private String msg;
    private DuoBaoBiPayData data;


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

    public DuoBaoBiPayData getData() {
        return data;
    }

    public void setData(DuoBaoBiPayData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DuoBaoBiPayBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class DuoBaoBiPayData {


        private String id;
        private String tel;
        private String email;
        private String real_name;
        private String nick_name;
        private String password;
        private String locked;
        private String sh_id;
        private String status;
        private String money;
        private String buy_id;
        private String signature;
        private String salt;
        private String create_time;
        private String token;
        private String token_exptime;
        private String is_delete;
        private String head_pic;
        private String is_real;

        private String code;
        private String msg;


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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLocked() {
            return locked;
        }

        public void setLocked(String locked) {
            this.locked = locked;
        }

        public String getSh_id() {
            return sh_id;
        }

        public void setSh_id(String sh_id) {
            this.sh_id = sh_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }


        public String getBuy_id() {
            return buy_id;
        }

        public void setBuy_id(String buy_id) {
            this.buy_id = buy_id;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken_exptime() {
            return token_exptime;
        }

        public void setToken_exptime(String token_exptime) {
            this.token_exptime = token_exptime;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getIs_real() {
            return is_real;
        }

        public void setIs_real(String is_real) {
            this.is_real = is_real;
        }

        @Override
        public String toString() {
            return "DuoBaoBiPayData{" +
                    "id='" + id + '\'' +
                    ", tel='" + tel + '\'' +
                    ", email='" + email + '\'' +
                    ", real_name='" + real_name + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", password='" + password + '\'' +
                    ", locked='" + locked + '\'' +
                    ", sh_id='" + sh_id + '\'' +
                    ", status='" + status + '\'' +
                    ", money='" + money + '\'' +
                    ", buy_id='" + buy_id + '\'' +
                    ", signature='" + signature + '\'' +
                    ", salt='" + salt + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", token='" + token + '\'' +
                    ", token_exptime='" + token_exptime + '\'' +
                    ", is_delete='" + is_delete + '\'' +
                    ", head_pic='" + head_pic + '\'' +
                    ", is_real='" + is_real + '\'' +
                    ", code='" + code + '\'' +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }

}
