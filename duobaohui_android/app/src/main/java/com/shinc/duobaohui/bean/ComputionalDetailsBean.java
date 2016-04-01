package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liugaopo on 15/10/8.
 */
public class ComputionalDetailsBean implements Serializable {


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
        return "ComputionalDetailsBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {
        private List<CountDetailResBean> count_detail_res;
        private List<LotteryCodeListBean> lottery_code_list;

        public List<LotteryCodeListBean> getLottery_code_list() {
            return lottery_code_list;
        }

        public void setLottery_code_list(List<LotteryCodeListBean> lottery_code_list) {
            this.lottery_code_list = lottery_code_list;
        }

        public List<CountDetailResBean> getCount_detail_res() {
            return count_detail_res;
        }

        public void setCount_detail_res(List<CountDetailResBean> count_detail_res) {
            this.count_detail_res = count_detail_res;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "count_detail_res=" + count_detail_res +
                    ", lottery_code_list=" + lottery_code_list +
                    '}';
        }
    }

    public class CountDetailResBean {
        private String id;
        private String sh_activity_period_id;
        private String a_code_create_time;
        private String a_code;
        private String lottery_code;
        private String lottery_period;
        private String luck_code;
        private String show_code_flag;

        public String getShow_code_flag() {
            return show_code_flag;
        }

        public void setShow_code_flag(String show_code_flag) {
            this.show_code_flag = show_code_flag;
        }

        public String getLuck_code() {
            return luck_code;
        }

        public void setLuck_code(String luck_code) {
            this.luck_code = luck_code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSh_activity_period_id() {
            return sh_activity_period_id;
        }

        public void setSh_activity_period_id(String sh_activity_period_id) {
            this.sh_activity_period_id = sh_activity_period_id;
        }

        public String getA_code_create_time() {
            return a_code_create_time;
        }

        public void setA_code_create_time(String a_code_create_time) {
            this.a_code_create_time = a_code_create_time;
        }

        public String getA_code() {
            return a_code;
        }

        public void setA_code(String a_code) {
            this.a_code = a_code;
        }

        public String getLottery_code() {
            return lottery_code;
        }

        public void setLottery_code(String lottery_code) {
            this.lottery_code = lottery_code;
        }

        public String getLottery_period() {
            return lottery_period;
        }

        public void setLottery_period(String lottery_period) {
            this.lottery_period = lottery_period;
        }

        @Override
        public String toString() {
            return "CountDetailResBean{" +
                    "id='" + id + '\'' +
                    ", sh_activity_period_id='" + sh_activity_period_id + '\'' +
                    ", a_code_create_time='" + a_code_create_time + '\'' +
                    ", a_code='" + a_code + '\'' +
                    ", lottery_code='" + lottery_code + '\'' +
                    ", lottery_period='" + lottery_period + '\'' +
                    ", luck_code='" + luck_code + '\'' +
                    ", show_code_flag='" + show_code_flag + '\'' +
                    '}';
        }
    }

    public class LotteryCodeListBean {

        private String buy_time;
        private String date_time_mic_s;
        private String time_mic_s;
        private String user_id;
        private String nick_name;

        public String getBuy_time() {
            return buy_time;
        }

        public void setBuy_time(String buy_time) {
            this.buy_time = buy_time;
        }

        public String getDate_time_mic_s() {
            return date_time_mic_s;
        }

        public void setDate_time_mic_s(String date_time_mic_s) {
            this.date_time_mic_s = date_time_mic_s;
        }

        public String getTime_mic_s() {
            return time_mic_s;
        }

        public void setTime_mic_s(String time_mic_s) {
            this.time_mic_s = time_mic_s;
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
            return "LotteryCodeListBean{" +
                    "buy_time='" + buy_time + '\'' +
                    ", date_time_mic_s='" + date_time_mic_s + '\'' +
                    ", time_mic_s='" + time_mic_s + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    '}';
        }
    }

}
