package com.shinc.duobaohui.bean;

import java.io.Serializable;

/**
 * 名称：TakePartBean
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 */
public class TakePartBean implements Serializable{

    private String user_id;
    private String nick_name;
    private String times;
    private String create_time;
    private String head_pic;
    private String id;
    private String ip;
    private String ip_address;

    public TakePartBean(String user_id, String nick_name, String times, String create_time, String head_pic, String id, String ip, String ip_address) {
        this.user_id = user_id;
        this.nick_name = nick_name;
        this.times = times;
        this.create_time = create_time;
        this.head_pic = head_pic;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TakePartBean{" +
                "user_id='" + user_id + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", times='" + times + '\'' +
                ", create_time='" + create_time + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", ip_address='" + ip_address + '\'' +
                '}';
    }
}
