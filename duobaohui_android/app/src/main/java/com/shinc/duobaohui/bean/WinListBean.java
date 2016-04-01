package com.shinc.duobaohui.bean;

import java.util.List;

/**
 * Created by liugaopo on 15/11/25.
 */
public class WinListBean {

    //            -data:
    private String code;
    private String msg;
    private List<WinListChildData> data;

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

    public List<WinListChildData> getData() {
        return data;
    }

    public void setData(List<WinListChildData> data) {
        this.data = data;
    }

    public class WinListChildData {
        private String sh_activity_period_id;
        private String order_id;
        private String order_status;
        private String shipping_status;
        private String is_shaidan;
        private String status;
        private String user_id;
        private String goods_name;
        private String goods_img;
        private String real_need_times;
        private String luck_code;
        private String luck_code_create_time;
        private String pre_luck_code_create_time;
        private String total_times;
        private String period_number;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getShipping_status() {
            return shipping_status;
        }

        public void setShipping_status(String shipping_status) {
            this.shipping_status = shipping_status;
        }

        public String getIs_shaidan() {
            return is_shaidan;
        }

        public void setIs_shaidan(String is_shaidan) {
            this.is_shaidan = is_shaidan;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getReal_need_times() {
            return real_need_times;
        }

        public void setReal_need_times(String real_need_times) {
            this.real_need_times = real_need_times;
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

        public String getPre_luck_code_create_time() {
            return pre_luck_code_create_time;
        }

        public void setPre_luck_code_create_time(String pre_luck_code_create_time) {
            this.pre_luck_code_create_time = pre_luck_code_create_time;
        }

        public String getTotal_times() {
            return total_times;
        }

        public void setTotal_times(String total_times) {
            this.total_times = total_times;
        }

        public String getSh_activity_period_id() {
            return sh_activity_period_id;
        }

        public void setSh_activity_period_id(String sh_activity_period_id) {
            this.sh_activity_period_id = sh_activity_period_id;
        }

        public String getPeriod_number() {
            return period_number;
        }

        public void setPeriod_number(String period_number) {
            this.period_number = period_number;
        }
    }


    @Override
    public String toString() {
        return "WinListBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
