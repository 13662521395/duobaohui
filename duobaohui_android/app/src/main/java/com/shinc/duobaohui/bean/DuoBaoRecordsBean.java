package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liugaopo on 15/10/7.
 * 夺宝记录
 */
public class DuoBaoRecordsBean implements Serializable {

    private String code;
    private String msg;
    private List<DuoBaoReCrodChildBean> data;

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

    public List<DuoBaoReCrodChildBean> getData() {
        return data;
    }

    public void setData(List<DuoBaoReCrodChildBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DuoBaoRecordsBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class DuoBaoReCrodChildBean {
        private String id;
        private String create_time;
        private String period_number;
        private String goods_name;
        private String goods_img;
        private String real_need_times;
        private String remainder;
        private String times;
        private String is_online;
        private String sh_activity_period_id;
        private String rate;
        private String current_times;

        public String getCurrent_times() {
            return current_times;
        }

        public String getSh_activity_period_id() {
            return sh_activity_period_id;
        }

        public void setSh_activity_period_id(String sh_activity_period_id) {
            this.sh_activity_period_id = sh_activity_period_id;
        }

        private DuoBaoWinnerUser winning_user;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public String getReal_need_times() {
            return real_need_times;
        }

        public void setReal_need_times(String real_need_times) {
            this.real_need_times = real_need_times;
        }

        public String getRemainder() {
            return remainder;
        }

        public void setRemainder(String remainder) {
            this.remainder = remainder;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getIs_online() {
            return is_online;
        }

        public void setIs_online(String is_online) {
            this.is_online = is_online;
        }

        public DuoBaoWinnerUser getWinning_user() {
            return winning_user;
        }

        public void setWinning_user(DuoBaoWinnerUser winning_user) {
            this.winning_user = winning_user;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getPeriod_number() {
            return period_number;
        }

        public void setPeriod_number(String period_number) {
            this.period_number = period_number;
        }

        @Override
        public String toString() {
            return "DuoBaoReCrodChildBean{" +
                    "id='" + id + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", period_number='" + period_number + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", goods_img='" + goods_img + '\'' +
                    ", real_need_times='" + real_need_times + '\'' +
                    ", remainder='" + remainder + '\'' +
                    ", times='" + times + '\'' +
                    ", is_online='" + is_online + '\'' +
                    ", sh_activity_period_id='" + sh_activity_period_id + '\'' +
                    ", rate='" + rate + '\'' +
                    ", winning_user=" + winning_user +
                    '}';
        }
    }

    public class DuoBaoWinnerUser {
        private String user_id;
        private String nick_name;
        private String times;
        private String luck_code;
        private String luck_code_create_time;


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

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getLuck_code() {
            return luck_code;
        }

        public void setLuck_code(String luck_code) {
            this.luck_code = luck_code;
        }

        public String getLuck_code_create_time() {
            return luck_code_create_time;
        }

        public void setLuck_code_create_time(String luck_code_create_time) {
            this.luck_code_create_time = luck_code_create_time;
        }



        @Override
        public String toString() {
            return "DuoBaoWinnerUser{" +
                    ", user_id='" + user_id + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", times='" + times + '\'' +
                    ", luck_code='" + luck_code + '\'' +
                    ", luck_code_create_time='" + luck_code_create_time + '\'' +
                    '}';
        }
    }

}
