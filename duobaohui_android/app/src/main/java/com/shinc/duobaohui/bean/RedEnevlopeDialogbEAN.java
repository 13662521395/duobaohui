package com.shinc.duobaohui.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by liugaopo on 15/12/18.
 * 红包bean
 */
public class RedEnevlopeDialogbean {
    private String code;
    private String msg;
    private List<RedEnevlopeDialogChildBean> data;

    public RedEnevlopeDialogbean(String code, String msg, List<RedEnevlopeDialogChildBean> data) {
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

    public List<RedEnevlopeDialogChildBean> getData() {
        return data;
    }

    public void setData(List<RedEnevlopeDialogChildBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RedEnevlopeDialogbean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class RedEnevlopeDialogChildBean {


        private String id;
        private String red_money_name;
        private String price;
        private String overdue_time;
        private String consumption;
        private String sh_red_money_batch_id;
        private String is_new_red_money;
        private String is_default;
        private String surplus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRed_money_name() {
            return red_money_name;
        }

        public void setRed_money_name(String red_money_name) {
            this.red_money_name = red_money_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOverdue_time() {
            return "有效期至：" + overdue_time + "后过期";
        }

        public void setOverdue_time(String overdue_time) {
            this.overdue_time = overdue_time;
        }

        public String getConsumption() {
            String tempS = consumption;
            if (!TextUtils.isEmpty(consumption) && consumption.contains(".")) {
                tempS = consumption.substring(0, consumption.indexOf("."));
            }
            if ("0".equals(tempS)) {
                tempS = "通用红包,任何条件可用";
            } else {
                tempS = "单次支付满" + tempS + "夺宝币可用";
            }
            return tempS;
        }

        public void setConsumption(String consumption) {
            this.consumption = consumption;
        }

        public String getSh_red_money_batch_id() {
            return sh_red_money_batch_id;
        }

        public void setSh_red_money_batch_id(String sh_red_money_batch_id) {
            this.sh_red_money_batch_id = sh_red_money_batch_id;
        }

        public String getIs_new_red_money() {
            return is_new_red_money;
        }

        public void setIs_new_red_money(String is_new_red_money) {
            this.is_new_red_money = is_new_red_money;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getSurplus() {
            if (Integer.valueOf(surplus.substring(0, 1)) > 0) {
                return surplus;
            } else {
                return "已过期";
            }
        }

        public void setSurplus(String surplus) {
            this.surplus = surplus;
        }
    }

}
