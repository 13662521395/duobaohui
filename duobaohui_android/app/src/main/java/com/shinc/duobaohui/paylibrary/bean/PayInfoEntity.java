package com.shinc.duobaohui.paylibrary.bean;

import java.io.Serializable;

/**
 * Created by liugaopo on 15/8/18.
 * 支付信息实体类
 */
public class PayInfoEntity implements Serializable {

    private String period_id;
    private String num;
    private String name;
    private String price;
    private String userId;
    private String payment_type;
    private String buy_id;
    private String buy_order_id;
    private String out_trade_no;
    private String redpacket_id;

    public PayInfoEntity() {
    }

    public PayInfoEntity(String period_id, String num, String name, String price, String userId, String payment_type, String buy_id, String buy_order_id, String out_trade_no, String redpacket_id) {
        this.period_id = period_id;
        this.num = num;
        this.name = name;
        this.price = price;
        this.userId = userId;
        this.payment_type = payment_type;
        this.buy_id = buy_id;
        this.buy_order_id = buy_order_id;
        this.out_trade_no = out_trade_no;
        this.redpacket_id = redpacket_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(String period_id) {
        this.period_id = period_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getBuy_id() {
        return buy_id;
    }

    public void setBuy_id(String buy_id) {
        this.buy_id = buy_id;
    }

    public String getBuy_order_id() {
        return buy_order_id;
    }

    public void setBuy_order_id(String buy_order_id) {
        this.buy_order_id = buy_order_id;
    }

    public String getRedpacket_id() {
        return redpacket_id;
    }

    public void setRedpacket_id(String redpacket_id) {
        this.redpacket_id = redpacket_id;
    }
}
