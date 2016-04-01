package com.shinc.duobaohui.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * @作者: efort
 * @日期: 15/12/18 - 16:45
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsDetailsHttpBean {
    private String code;
    private String msg;
    private CouponsDetialsBean data;

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

    public CouponsDetialsBean getData() {
        return data;
    }

    public void setData(CouponsDetialsBean data) {
        this.data = data;
    }

    public class CouponsDetialsBean {

        private String red_money_name;
        private String overdue_time;
        private String surplus;
        private String price;
        private String sh_red_money_batch_id;
        private String consumption;
        private String id;
        private String is_new_red_money;
        private String range;

        private List<CouponsProductBean> activity;

        public String getRed_money_name() {
            return red_money_name;
        }

        public void setRed_money_name(String red_money_name) {
            this.red_money_name = red_money_name;
        }

        public String getOverdue_time() {
            String tempS = "";
            if (!TextUtils.isEmpty(overdue_time)) {
                tempS = "有效期至: " + overdue_time;
            }
            return tempS;
        }

        public void setOverdue_time(String overdue_time) {
            this.overdue_time = overdue_time;
        }

        public String getSurplus() {
            return surplus;
        }

        public void setSurplus(String surplus) {
            this.surplus = surplus;
        }

        public String getPrice() {
            String tempS;

            if (!TextUtils.isEmpty(price) && price.contains(".")) {
                tempS = price.substring(0, price.indexOf("."));
            } else {
                tempS = "0";
            }
            return tempS;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSh_red_money_batch_id() {
            return sh_red_money_batch_id;
        }

        public void setSh_red_money_batch_id(String sh_red_money_batch_id) {
            this.sh_red_money_batch_id = sh_red_money_batch_id;
        }

        public String getConsumption() {
            String tempS = consumption;
            if (!TextUtils.isEmpty(consumption) && consumption.contains(".")) {
                tempS = consumption.substring(0, consumption.indexOf("."));
            }
            if ("0".equals(tempS)) {
                tempS = "通用红包,任何条件可用";
            } else {
                tempS = "单次支付满 " + tempS + " 元可用";
            }
            return tempS;
        }

        public void setConsumption(String consumption) {
            this.consumption = consumption;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_new_red_money() {
            return is_new_red_money;
        }

        public void setIs_new_red_money(String is_new_red_money) {
            this.is_new_red_money = is_new_red_money;
        }

        public List<CouponsProductBean> getActivity() {
            return activity;
        }

        public void setActivity(List<CouponsProductBean> activity) {
            this.activity = activity;
        }

        public String getRange() {
            if (activity != null && activity.size() > 0) {
                range = "适用于" + activity.size() + "款商品";
            } else {
                range = "适用于所有商品";
            }
            return range;
        }

        public class CouponsProductBean {

            private String current_period;
            private String goods_name;
            private String sh_red_money_batch_id;
            private String id;
            private String sh_activity_id;
            private String goods_img;
            private String period_id;

            public String getCurrent_period() {
                return current_period;
            }

            public void setCurrent_period(String current_period) {
                this.current_period = current_period;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getSh_red_money_batch_id() {
                return sh_red_money_batch_id;
            }

            public void setSh_red_money_batch_id(String sh_red_money_batch_id) {
                this.sh_red_money_batch_id = sh_red_money_batch_id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSh_activity_id() {
                return sh_activity_id;
            }

            public void setSh_activity_id(String sh_activity_id) {
                this.sh_activity_id = sh_activity_id;
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
        }
    }
}
