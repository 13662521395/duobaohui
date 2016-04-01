package com.shinc.duobaohui.bean;

/**
 * Created by liugaopo on 15/11/11.
 */
public class UmPushBean {
    private String user_id;
    private String title;
    private String descr;
    private String img;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "UmPushBean{" +
                "user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", descr='" + descr + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
