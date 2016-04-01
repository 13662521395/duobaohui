package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 名称：ProductDetail
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 * 商品详情接口；
 */
public class ProductDetail implements Serializable {

    private String period_id;
    private String create_time;
    private String current_times;
    private String real_need_times;
    private String max_period;
    private String price;
    private String need_times;
    private String period_number;
    private String goods_id;
    private String goods_name;
    private ArrayList<BannerBean> goods_pic;
    private String rate;
    private String is_online;
    private String form_token;

    private String current_period;
    private String next_period_id;
    private String status;
    private String left_second;
    private TakePartUser login_user;
    private String luck_code_create_time;
    private String id;
    private LuckUserBean luck_user;


    public ProductDetail() {
    }

    public ProductDetail(String period_id, String create_time, String current_times, String real_need_times, String max_period, String price, String need_times, String period_number, String goods_id, String goods_name, ArrayList<BannerBean> goods_pic, String rate, String is_online, String form_token, String current_period, String next_period_id, String status, String left_second, TakePartUser login_user, String luck_code_create_time, String id, LuckUserBean luck_user) {
        this.period_id = period_id;
        this.create_time = create_time;
        this.current_times = current_times;
        this.real_need_times = real_need_times;
        this.max_period = max_period;
        this.price = price;
        this.need_times = need_times;
        this.period_number = period_number;
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.goods_pic = goods_pic;
        this.rate = rate;
        this.is_online = is_online;
        this.form_token = form_token;
        this.current_period = current_period;
        this.next_period_id = next_period_id;
        this.status = status;
        this.left_second = left_second;
        this.login_user = login_user;
        this.luck_code_create_time = luck_code_create_time;
        this.id = id;
        this.luck_user = luck_user;
    }




    public String getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(String period_id) {
        this.period_id = period_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public String getMax_period() {
        return max_period;
    }

    public void setMax_period(String max_period) {
        this.max_period = max_period;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNeed_times() {
        return need_times;
    }

    public void setNeed_times(String need_times) {
        this.need_times = need_times;
    }

    public String getPeriod_number() {
        return period_number;
    }

    public void setPeriod_number(String period_number) {
        this.period_number = period_number;
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

    public ArrayList<BannerBean> getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(ArrayList<BannerBean> goods_pic) {
        this.goods_pic = goods_pic;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeft_second() {
        return left_second;
    }

    public void setLeft_second(String left_second) {
        this.left_second = left_second;
    }

    public TakePartUser getLogin_user() {
        return login_user;
    }

    public void setLogin_user(TakePartUser login_user) {
        this.login_user = login_user;
    }

    public LuckUserBean getLuck_user() {
        return luck_user;
    }

    public void setLuck_user(LuckUserBean luck_user) {
        this.luck_user = luck_user;
    }

    public String getIs_online() {
        return is_online;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
    }

    public String getLuck_code_create_time() {
        return luck_code_create_time;
    }

    public void setLuck_code_create_time(String luck_code_create_time) {
        this.luck_code_create_time = luck_code_create_time;
    }

    public String getNext_period_id() {
        return next_period_id;
    }

    public void setNext_period_id(String next_period_id) {
        this.next_period_id = next_period_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrent_period() {
        return current_period;
    }

    public void setCurrent_period(String current_period) {
        this.current_period = current_period;
    }

    public String getForm_token() {
        return form_token;
    }

    public void setForm_token(String form_token) {
        this.form_token = form_token;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "period_id='" + period_id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", current_times='" + current_times + '\'' +
                ", real_need_times='" + real_need_times + '\'' +
                ", max_period='" + max_period + '\'' +
                ", price='" + price + '\'' +
                ", need_times='" + need_times + '\'' +
                ", period_number='" + period_number + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_pic=" + goods_pic +
                ", rate='" + rate + '\'' +
                ", is_online='" + is_online + '\'' +
                ", form_token='" + form_token + '\'' +
                ", current_period='" + current_period + '\'' +
                ", next_period_id='" + next_period_id + '\'' +
                ", status='" + status + '\'' +
                ", left_second='" + left_second + '\'' +
                ", login_user=" + login_user +
                ", luck_code_create_time='" + luck_code_create_time + '\'' +
                ", id='" + id + '\'' +
                ", luck_user=" + luck_user +
                '}';
    }
}
