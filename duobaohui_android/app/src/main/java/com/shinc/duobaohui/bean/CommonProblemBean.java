package com.shinc.duobaohui.bean;

import java.io.Serializable;

/**
 * Created by liugaopo on 15/10/22.
 */
public class CommonProblemBean implements Serializable {

    private String title;
    private String info;


    public CommonProblemBean(String title, String info) {
        this.title = title;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "CommonProblemBean{" +
                "title='" + title + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
