package com.shinc.duobaohui.bean;

/**
 * 名称：PreAnnounceBean
 * 作者：zhaopl 时间: 15/10/10.
 * 实现的主要功能：
 */
public class PreAnnounceBean {

    private String period_number;//第几期；
    private String sh_period_result_id;//
    private String sh_activity_id;//活动id；
    private String sh_activity_period_id;//期数主键；
    private String nick_name;
    private String user_id;
    private String luck_code;
    private String times;//本期参与人次；
    private String pre_luck_code_create_time;//揭晓时间；
    private String is_winninghistory;//是否为开奖历史（0：不是，既即将揭晓，正在倒计时；1:是）：
    private String head_pic;
    private String ip;
    private String ip_address;

    public PreAnnounceBean(String period_number, String sh_period_result_id, String sh_activity_id, String sh_activity_period_id, String nick_name, String user_id, String luck_code, String times, String pre_luck_code_create_time, String is_winninghistory, String head_pic, String ip, String ip_address) {
        this.period_number = period_number;
        this.sh_period_result_id = sh_period_result_id;
        this.sh_activity_id = sh_activity_id;
        this.sh_activity_period_id = sh_activity_period_id;
        this.nick_name = nick_name;
        this.user_id = user_id;
        this.luck_code = luck_code;
        this.times = times;
        this.pre_luck_code_create_time = pre_luck_code_create_time;
        this.is_winninghistory = is_winninghistory;
        this.head_pic = head_pic;
        this.ip = ip;
        this.ip_address = ip_address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getPeriod_number() {
        return period_number;
    }

    public void setPeriod_number(String period_number) {
        this.period_number = period_number;
    }

    public String getSh_period_result_id() {
        return sh_period_result_id;
    }

    public void setSh_period_result_id(String sh_period_result_id) {
        this.sh_period_result_id = sh_period_result_id;
    }

    public String getSh_activity_id() {
        return sh_activity_id;
    }

    public void setSh_activity_id(String sh_activity_id) {
        this.sh_activity_id = sh_activity_id;
    }

    public String getSh_activity_period_id() {
        return sh_activity_period_id;
    }

    public void setSh_activity_period_id(String sh_activity_period_id) {
        this.sh_activity_period_id = sh_activity_period_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLuck_code() {
        return luck_code;
    }

    public void setLuck_code(String luck_code) {
        this.luck_code = luck_code;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getPre_luck_code_create_time() {
        return pre_luck_code_create_time;
    }

    public void setPre_luck_code_create_time(String pre_luck_code_create_time) {
        this.pre_luck_code_create_time = pre_luck_code_create_time;
    }

    public String getIs_winninghistory() {
        return is_winninghistory;
    }

    public void setIs_winninghistory(String is_winninghistory) {
        this.is_winninghistory = is_winninghistory;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    @Override
    public String toString() {
        return "PreAnnounceBean{" +
                "period_number='" + period_number + '\'' +
                ", sh_period_result_id='" + sh_period_result_id + '\'' +
                ", sh_activity_id='" + sh_activity_id + '\'' +
                ", sh_activity_period_id='" + sh_activity_period_id + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", luck_code='" + luck_code + '\'' +
                ", times='" + times + '\'' +
                ", pre_luck_code_create_time='" + pre_luck_code_create_time + '\'' +
                ", is_winninghistory='" + is_winninghistory + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", ip='" + ip + '\'' +
                ", ip_address='" + ip_address + '\'' +
                '}';
    }
}
