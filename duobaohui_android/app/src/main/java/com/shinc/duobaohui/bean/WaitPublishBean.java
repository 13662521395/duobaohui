package com.shinc.duobaohui.bean;

/**
 * 名称：WaitPublishBean
 * 作者：zhaopl 时间: 15/10/12.
 * 实现的主要功能：
 */
public class WaitPublishBean {

    private String period_id;

    private String user_id;

    private String pre_luck_code_create_time;

    private String luck_code_create_time;

    private String luck_code;

    private String current_times;

    private String real_need_times;

    private String create_time;

    private String real_price;

    private String period_number;

    private String id;

    private String goods_id;

    private String goods_name;

    private String goods_img;

    private String left_second;

    private User user;

    private String endTime;

    private boolean isEnd;


    public class User {
        private String times;
        private String user_id;
        private String nick_name;

        public User(String times, String user_id, String nick_name) {
            this.times = times;
            this.user_id = user_id;
            this.nick_name = nick_name;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "times='" + times + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    '}';
        }
    }


    public WaitPublishBean(String period_id, String user_id, String pre_luck_code_create_time, String luck_code_create_time, String luck_code, String current_times, String real_need_times, String create_time, String real_price, String period_number, String id, String goods_id, String goods_name, String goods_img, String left_second, User user, String endTime, boolean isEnd) {
        this.period_id = period_id;
        this.user_id = user_id;
        this.pre_luck_code_create_time = pre_luck_code_create_time;
        this.luck_code_create_time = luck_code_create_time;
        this.luck_code = luck_code;
        this.current_times = current_times;
        this.real_need_times = real_need_times;
        this.create_time = create_time;
        this.real_price = real_price;
        this.period_number = period_number;
        this.id = id;
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.goods_img = goods_img;
        this.left_second = left_second;
        this.user = user;
        this.endTime = endTime;
        this.isEnd = isEnd;
    }

    public String getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(String period_id) {
        this.period_id = period_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPre_luck_code_create_time() {
        return pre_luck_code_create_time;
    }

    public void setPre_luck_code_create_time(String pre_luck_code_create_time) {
        this.pre_luck_code_create_time = pre_luck_code_create_time;
    }

    public String getLuck_code_create_time() {
        return luck_code_create_time;
    }

    public void setLuck_code_create_time(String luck_code_create_time) {
        this.luck_code_create_time = luck_code_create_time;
    }

    public String getLuck_code() {
        return luck_code;
    }

    public void setLuck_code(String luck_code) {
        this.luck_code = luck_code;
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

    public String getPeriod_number() {
        return period_number;
    }

    public void setPeriod_number(String period_number) {
        this.period_number = period_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLeft_second() {
        return left_second;
    }

    public void setLeft_second(String left_second) {
        this.left_second = left_second;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    public String toString() {
        return "WaitPublishBean{" +
                "period_id='" + period_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", pre_luck_code_create_time='" + pre_luck_code_create_time + '\'' +
                ", luck_code_create_time='" + luck_code_create_time + '\'' +
                ", luck_code='" + luck_code + '\'' +
                ", current_times='" + current_times + '\'' +
                ", real_need_times='" + real_need_times + '\'' +
                ", create_time='" + create_time + '\'' +
                ", real_price='" + real_price + '\'' +
                ", period_number='" + period_number + '\'' +
                ", id='" + id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_img='" + goods_img + '\'' +
                ", left_second='" + left_second + '\'' +
                ", user=" + user +
                ", endTime='" + endTime + '\'' +
                ", isEnd=" + isEnd +
                '}';
    }
}
