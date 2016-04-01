package com.shinc.duobaohui.bean;

/**
 * 名称：ReportReasonBean
 * 作者：zhaopl 时间: 15/11/18.
 * 实现的主要功能：
 *      举报理由的承载类；
 */
public class ReportReasonBean {

    private String id;
    private String isChecked;
    private String name;

    public ReportReasonBean(String id, String isChecked, String name) {
        this.id = id;
        this.isChecked = isChecked;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
