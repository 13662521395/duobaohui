package com.shinc.duobaohui.bean;

import java.util.List;

/**
 * Created by liugaopo on 15/11/23.
 * TA的主页－中奖纪录
 */
public class UserWinRecrodBean {
    private String code;
    private String msg;
    private List<UseWinRecrodata> data;


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

    public List<UseWinRecrodata> getData() {
        return data;
    }

    public void setData(List<UseWinRecrodata> data) {
        this.data = data;
    }


    public class UseWinRecrodata {
        private String user_id;
        private String goods_name;
        private String goods_img;
        private String period_id;
        private String times;
        private String period_number;
        private String real_need_times;
        private String current_times;
        private String remain_times;
        private String progress;
        private String flag;
        private String result_id;
        private String result_user_id;
        private String luck_code;
        private String luck_code_create_time;
        private String result_user_name;
        private String result_user_sum;
        private String total_times;

        public String getTotal_times() {
            return total_times;
        }

        public void setTotal_times(String total_times) {
            this.total_times = total_times;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getPeriod_id() {
            return period_id;
        }

        public void setPeriod_id(String period_id) {
            this.period_id = period_id;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getPeriod_number() {
            return period_number;
        }

        public void setPeriod_number(String period_number) {
            this.period_number = period_number;
        }

        public String getReal_need_times() {
            return real_need_times;
        }

        public void setReal_need_times(String real_need_times) {
            this.real_need_times = real_need_times;
        }

        public String getCurrent_times() {
            return current_times;
        }

        public void setCurrent_times(String current_times) {
            this.current_times = current_times;
        }

        public String getRemain_times() {
            return remain_times;
        }

        public void setRemain_times(String remain_times) {
            this.remain_times = remain_times;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getResult_id() {
            return result_id;
        }

        public void setResult_id(String result_id) {
            this.result_id = result_id;
        }

        public String getResult_user_id() {
            return result_user_id;
        }

        public void setResult_user_id(String result_user_id) {
            this.result_user_id = result_user_id;
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

        public String getResult_user_name() {
            return result_user_name;
        }

        public void setResult_user_name(String result_user_name) {
            this.result_user_name = result_user_name;
        }

        public String getResult_user_sum() {
            return result_user_sum;
        }

        public void setResult_user_sum(String result_user_sum) {
            this.result_user_sum = result_user_sum;
        }

        @Override
        public String toString() {
            return "UserShowOrdercHILData{" +
                    "user_id='" + user_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", goods_img='" + goods_img + '\'' +
                    ", period_id='" + period_id + '\'' +
                    ", times='" + times + '\'' +
                    ", period_number='" + period_number + '\'' +
                    ", real_need_times='" + real_need_times + '\'' +
                    ", current_times='" + current_times + '\'' +
                    ", remain_times='" + remain_times + '\'' +
                    ", progress='" + progress + '\'' +
                    ", flag='" + flag + '\'' +
                    ", result_id='" + result_id + '\'' +
                    ", result_user_id='" + result_user_id + '\'' +
                    ", luck_code='" + luck_code + '\'' +
                    ", luco_code_create_time='" + luck_code_create_time + '\'' +
                    ", result_user_name='" + result_user_name + '\'' +
                    ", result_user_sum='" + result_user_sum + '\'' +
                    '}';
        }
    }
}
