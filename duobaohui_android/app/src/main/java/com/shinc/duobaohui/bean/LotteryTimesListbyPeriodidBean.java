package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liugaopo on 15/10/8.
 */
public class LotteryTimesListbyPeriodidBean implements Serializable {
    private String code;
    private String msg;
    private List<LotteryTimesListbyPeriodidBean.Data> data;

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

    public List<LotteryTimesListbyPeriodidBean.Data> getData() {
        return data;
    }

    public void setData(List<LotteryTimesListbyPeriodidBean.Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LotteryTimesListbyPeriodidBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {
        private String sh_period_user_id;
        private String sh_activity_period_id;
        private String create_time;
        private String user_id;
        private String times;

        public String getSh_period_user_id() {
            return sh_period_user_id;
        }

        public void setSh_period_user_id(String sh_period_user_id) {
            this.sh_period_user_id = sh_period_user_id;
        }

        public String getSh_activity_period_id() {
            return sh_activity_period_id;
        }

        public void setSh_activity_period_id(String sh_activity_period_id) {
            this.sh_activity_period_id = sh_activity_period_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "sh_period_user_id='" + sh_period_user_id + '\'' +
                    ", sh_activity_period_id='" + sh_activity_period_id + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", times='" + times + '\'' +
                    '}';
        }
    }

}

