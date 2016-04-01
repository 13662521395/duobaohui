package com.shinc.duobaohui.paylibrary.bean;

import android.view.View;

import java.io.Serializable;

/**
 * Created by liugaopo on 15/8/13.
 */
public class PayEntity implements Serializable {

    private String id;
    private String iconUrl;
    private String tv_word;
    private View.OnClickListener onClickListener;

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

    public String getTv_word() {
        return tv_word;
    }

    public void setTv_word(String tv_word) {
        this.tv_word = tv_word;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public PayEntity() {

    }

    public PayEntity(String id, String iconUrl, String tv_word, View.OnClickListener onClickListener) {
        this.id = id;
        this.iconUrl = iconUrl;
        this.tv_word = tv_word;
        this.onClickListener = onClickListener;
    }
}
