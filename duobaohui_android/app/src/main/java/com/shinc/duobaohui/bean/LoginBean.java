package com.shinc.duobaohui.bean;

/**
 * 名称：LoginBean
 * 作者：zhaopl 时间: 15/9/23.
 * 实现的主要功能：
 * 登陆接口返回数据Bean：
 */
public class LoginBean {

    private String code;
    private String msg;
    private UserBean data;

    public class UserBean {
        private String id;
        private String real_name;
        private String nick_name;
        private String tel;
        private String sh_id;
        private String signature;
        private String head_pic;
        private String money;

        public UserBean(String id, String real_name, String nick_name, String tel, String sh_id, String signature, String head_pic, String money) {
            this.id = id;
            this.real_name = real_name;
            this.nick_name = nick_name;
            this.tel = tel;
            this.sh_id = sh_id;
            this.signature = signature;
            this.head_pic = head_pic;
            this.money = money;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getSh_id() {
            return sh_id;
        }

        public void setSh_id(String sh_id) {
            this.sh_id = sh_id;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        @Override
        public String toString() {
            return "UserBean{" +
                    "id='" + id + '\'' +
                    ", real_name='" + real_name + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", tel='" + tel + '\'' +
                    ", sh_id='" + sh_id + '\'' +
                    ", signature='" + signature + '\'' +
                    ", head_pic='" + head_pic + '\'' +
                    ", money='" + money + '\'' +
                    '}';
        }
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

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public LoginBean(String code, String msg, UserBean data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
