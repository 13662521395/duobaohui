package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 名称：TakePartUser
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 */
public class TakePartUser implements Serializable {

    private String times;
    private ArrayList<String> code;


    public TakePartUser() {
    }

    public TakePartUser(String times, ArrayList<String> code) {
        this.times = times;
        this.code = code;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public ArrayList<String> getCode() {
        return code;
    }

    public void setCode(ArrayList<String> code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "TakePartUser{" +
                "times='" + times + '\'' +
                ", code=" + code +
                '}';
    }
}
