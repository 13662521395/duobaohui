package com.shinc.duobaohui.bean;

import java.io.Serializable;

/**
 * 名称：AddOrder
 * 作者：zhaopl 时间: 15/11/6.
 * 实现的主要功能：
 * 生成订单的返回数据Bean；
 */
public class AddOrderBean implements Serializable {

    private String code;
    private String msg;
    private Order data;

    public AddOrderBean(String code, String msg, Order data) {
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

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AddOrderBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Order implements Serializable {
        private String code;
        private String jnl_no;
        private String msg;
        private String period_id;
        private String period_number;
        private String current_times;
        private String real_need_times;
        private String create_time;
        private String real_price;
        private String sh_activity_id;
        private String sh_goods_id;
        private String goods_name;
        private String id;
        private String tel;
        private String nick_name;
        private String head_pic;
        private String money;
        private String redpackets;

        public Order(String code, String jnl_no, String msg, String period_id, String period_number, String current_times, String real_need_times, String create_time, String real_price, String sh_activity_id, String sh_goods_id, String goods_name, String id, String tel, String nick_name, String head_pic, String money, String redpackets) {
            this.code = code;
            this.jnl_no = jnl_no;
            this.msg = msg;
            this.period_id = period_id;
            this.period_number = period_number;
            this.current_times = current_times;
            this.real_need_times = real_need_times;
            this.create_time = create_time;
            this.real_price = real_price;
            this.sh_activity_id = sh_activity_id;
            this.sh_goods_id = sh_goods_id;
            this.goods_name = goods_name;
            this.id = id;
            this.tel = tel;
            this.nick_name = nick_name;
            this.head_pic = head_pic;
            this.money = money;
            this.redpackets = redpackets;
        }

        public String getRedpackets() {
            return redpackets;
        }

        public void setRedpackets(String redpackets) {
            this.redpackets = redpackets;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getJnl_no() {
            return jnl_no;
        }

        public void setJnl_no(String jnl_no) {
            this.jnl_no = jnl_no;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getPeriod_id() {
            return period_id;
        }

        public void setPeriod_id(String period_id) {
            this.period_id = period_id;
        }

        public String getPeriod_number() {
            return period_number;
        }

        public void setPeriod_number(String period_number) {
            this.period_number = period_number;
        }

        public String getCurrent_times() {
            return current_times;
        }

        public void setCurrent_times(String current_times) {
            this.current_times = current_times;
        }

        public String getReal_need_times() {
            return real_need_times;
        }

        public void setReal_need_times(String real_need_times) {
            this.real_need_times = real_need_times;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getReal_price() {
            return real_price;
        }

        public void setReal_price(String real_price) {
            this.real_price = real_price;
        }

        public String getSh_activity_id() {
            return sh_activity_id;
        }

        public void setSh_activity_id(String sh_activity_id) {
            this.sh_activity_id = sh_activity_id;
        }

        public String getSh_goods_id() {
            return sh_goods_id;
        }

        public void setSh_goods_id(String sh_goods_id) {
            this.sh_goods_id = sh_goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
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

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
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
            return "Order{" +
                    "code='" + code + '\'' +
                    ", jnl_no='" + jnl_no + '\'' +
                    ", msg='" + msg + '\'' +
                    ", period_id='" + period_id + '\'' +
                    ", period_number='" + period_number + '\'' +
                    ", current_times='" + current_times + '\'' +
                    ", real_need_times='" + real_need_times + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", real_price='" + real_price + '\'' +
                    ", sh_activity_id='" + sh_activity_id + '\'' +
                    ", sh_goods_id='" + sh_goods_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", id='" + id + '\'' +
                    ", tel='" + tel + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", head_pic='" + head_pic + '\'' +
                    ", money='" + money + '\'' +
                    '}';
        }
    }
}
