package com.shinc.duobaohui.bean;

/**
 * 名称：ProductBean
 * 作者：zhaopl 时间: 15/9/30.
 * 实现的主要功能：产品bean;
 */
public class ProductBean {

    private String id;
    private String as;
    private String max_period;
    private String price;
    private String need_times;
    private String create_time;
    private String current_times;
    private String real_need_times;
    private String goods_name;
    private String goods_img;
    private String rate;
    private String period_id;

    public ProductBean() {
    }

    public ProductBean(String id, String as, String max_period, String price, String need_times, String create_time, String current_times, String real_need_times, String goods_name, String goods_img, String rate, String period_id) {
        this.id = id;
        this.as = as;
        this.max_period = max_period;
        this.price = price;
        this.need_times = need_times;
        this.create_time = create_time;
        this.current_times = current_times;
        this.real_need_times = real_need_times;
        this.goods_name = goods_name;
        this.goods_img = goods_img;
        this.rate = rate;
        this.period_id = period_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(String period_id) {
        this.period_id = period_id;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "id='" + id + '\'' +
                ", as='" + as + '\'' +
                ", max_period='" + max_period + '\'' +
                ", price='" + price + '\'' +
                ", need_times='" + need_times + '\'' +
                ", create_time='" + create_time + '\'' +
                ", current_times='" + current_times + '\'' +
                ", real_need_times='" + real_need_times + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_img='" + goods_img + '\'' +
                ", rate='" + rate + '\'' +
                ", period_id='" + period_id + '\'' +
                '}';
    }
}
