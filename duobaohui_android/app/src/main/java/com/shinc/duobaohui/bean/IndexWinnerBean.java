package com.shinc.duobaohui.bean;

/**
 * 名称：IndexWinnerBean
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 */
public class IndexWinnerBean {

    private String user_id;
    private String nick_name;
    private String period_number;
    private String luck_code_create_time;
    private String goods_name;
    private String sh_activity_period_id;
    private String head_pic;

    public IndexWinnerBean() {
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

    public String getPeriod_number() {
        return period_number;
    }

    public void setPeriod_number(String period_number) {
        this.period_number = period_number;
    }

    public String getLuck_code_create_time() {
        return luck_code_create_time;
    }

    public void setLuck_code_create_time(String luck_code_create_time) {
        this.luck_code_create_time = luck_code_create_time;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getSh_activity_period_id() {
        return sh_activity_period_id;
    }

    public void setSh_activity_period_id(String sh_activity_period_id) {
        this.sh_activity_period_id = sh_activity_period_id;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public IndexWinnerBean(String user_id, String nick_name, String period_number, String luck_code_create_time, String goods_name, String sh_activity_period_id, String head_pic) {
        this.user_id = user_id;
        this.nick_name = nick_name;
        this.period_number = period_number;
        this.luck_code_create_time = luck_code_create_time;
        this.goods_name = goods_name;
        this.sh_activity_period_id = sh_activity_period_id;
        this.head_pic = head_pic;
    }

    @Override
    public String toString() {
        return "IndexWinnerBean{" +
                "user_id='" + user_id + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", period_number='" + period_number + '\'' +
                ", luck_code_create_time='" + luck_code_create_time + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", sh_activity_period_id='" + sh_activity_period_id + '\'' +
                ", head_pic='" + head_pic + '\'' +
                '}';
    }
}
