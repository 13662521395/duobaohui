package com.shinc.duobaohui.bean;

import java.io.Serializable;

/**
 * 名称：LuckUserBean
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 */
public class LuckUserBean implements Serializable {

    private String user_id;
    private String nick_name;
    private String times;
    private String create_time;
    private String head_pic;
    private String luck_code;
    private String ip;
    private String ip_address;

    public LuckUserBean(String user_id, String nick_name, String times, String create_time, String head_pic, String luck_code, String ip, String ip_address) {
        this.user_id = user_id;
        this.nick_name = nick_name;
        this.times = times;
        this.create_time = create_time;
        this.head_pic = head_pic;
        this.luck_code = luck_code;
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

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getLuck_code() {
        return luck_code;
    }

    public void setLuck_code(String luck_code) {
        this.luck_code = luck_code;
    }

    @Override
    public String toString() {
        return "LuckUserBean{" +
                "user_id='" + user_id + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", times='" + times + '\'' +
                ", create_time='" + create_time + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", luck_code='" + luck_code + '\'' +
                '}';
    }
}
