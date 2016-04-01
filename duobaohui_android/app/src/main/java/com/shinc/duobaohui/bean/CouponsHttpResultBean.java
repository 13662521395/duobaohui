package com.shinc.duobaohui.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * @作者: efort
 * @日期: 15/12/17 - 20:28
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsHttpResultBean {
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


    public class Data {
        private Count count;
        private List<CouponsItemBean> list;

        public List<CouponsItemBean> getList() {
            return list;
        }

        public void setList(List<CouponsItemBean> list) {
            this.list = list;
        }

        public Count getCount() {
            return count;
        }

        public void setCount(Count count) {
            this.count = count;
        }
    }

    public class Count {
        private String issue;
        private String use;
        private String is_overdue;

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getUse() {
            return use;
        }

        public void setUse(String use) {
            this.use = use;
        }

        public String getIs_overdue() {
            return is_overdue;
        }

        public void setIs_overdue(String is_overdue) {
            this.is_overdue = is_overdue;
        }
    }

    public class CouponsItemBean {
        private String red_money_name;
        private String overdue_time;
        private String surplus;
        private String activity;
        private String price;
        private String sh_red_money_batch_id;
        private String consumption;
        private String id;
        private String is_new_red_money;

        public CouponsItemBean() {
        }

        public CouponsItemBean(String s) {
            String tempS = "";
            switch (s) {
                case "1":
                    tempS = "_1";
                    break;
                case "2":
                    tempS = "_2";
                    break;
                case "3":
                    tempS = "_3";
                    break;
            }
            overdue_time = "" + tempS;
            activity = "0" + tempS;
            price = "10.00" + tempS;
            consumption = "20" + tempS;
            red_money_name = "测试数据" + tempS;
        }

        public String getIs_new_red_money() {
            String tempS = is_new_red_money;

            if (!TextUtils.isEmpty(is_new_red_money) && is_new_red_money.contains(".")) {
                tempS = is_new_red_money.substring(0, is_new_red_money.indexOf("."));
            }
            return tempS;
        }

        public void setIs_new_red_money(String is_new_red_money) {
            this.is_new_red_money = is_new_red_money;
        }

        public String getRed_money_name() {
            return red_money_name;
        }

        public void setRed_money_name(String red_money_name) {
            this.red_money_name = red_money_name;
        }

        public String getOverdue_time() {
            return overdue_time;
        }

        public void setOverdue_time(String overdue_time) {
            this.overdue_time = overdue_time;
        }

        public String getActivity() {
            if (!TextUtils.isEmpty(activity) && activity.equals("0")) {
                return "适用于全部商品";
            } else {
                return "适用于 " + activity + "款商品";
            }
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public String getPrice() {
            String tempS = price;
            if (!TextUtils.isEmpty(price) && price.contains(".")) {
                tempS = price.substring(0, price.indexOf("."));
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
                tempS = "单次支付满 " + tempS + " 夺宝币可用";
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

        public String getSurplus() {
            return surplus;
        }

        public void setSurplus(String surplus) {
            this.surplus = surplus;
        }
    }
}
