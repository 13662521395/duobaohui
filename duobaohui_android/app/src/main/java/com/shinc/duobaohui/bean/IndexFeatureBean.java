package com.shinc.duobaohui.bean;

import java.io.Serializable;

/**
 * Created by liugaopo on 15/9/1.
 */
public class IndexFeatureBean implements Serializable {
    private String id;
    private String iconUrl;

    public IndexFeatureBean() {

    }

    public IndexFeatureBean(String iconUrl, String id) {
        this.id = id;
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "IndexFeatureBean{" +
                "id='" + id + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
