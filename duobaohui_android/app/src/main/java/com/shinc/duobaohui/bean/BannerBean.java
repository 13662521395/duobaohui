package com.shinc.duobaohui.bean;

import java.io.Serializable;

/**
 * 名称:
 * Created by chaos on 15/9/29.
 * 功能:
 */

public class BannerBean implements Serializable {
    private String id;
    private String title;
    private String link_url;
    private String pic_url;
    private String file_path;
    private String type;
    private String start_time;
    private String end_time;
    private String display_order;

    public BannerBean(String id, String title, String link_url, String pic_url, String file_path, String type, String start_time, String end_time, String display_order) {
        this.id = id;
        this.title = title;
        this.link_url = link_url;
        this.pic_url = pic_url;
        this.file_path = file_path;
        this.type = type;
        this.start_time = start_time;
        this.end_time = end_time;
        this.display_order = display_order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(String display_order) {
        this.display_order = display_order;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "display_order='" + display_order + '\'' +
                ", end_time='" + end_time + '\'' +
                ", start_time='" + start_time + '\'' +
                ", type='" + type + '\'' +
                ", file_path='" + file_path + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", link_url='" + link_url + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
