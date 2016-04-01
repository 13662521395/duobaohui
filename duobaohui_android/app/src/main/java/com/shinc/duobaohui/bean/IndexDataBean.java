package com.shinc.duobaohui.bean;

import java.util.List;

/**
 * 名称:IndexBean
 * Created by chaos on 15/8/19.
 * 功能:首页接口
 */

public class IndexDataBean {

    private List<IndexBean> picList;

    public List<IndexBean> getPicList() {

        return picList;
    }

    public void setPicList(List<IndexBean> picList) {
        this.picList = picList;
    }

    public String getMiddleImageUrl() {
        return "http://img5.imgtn.bdimg.com/it/u=1980971292,2910643605&fm=21&gp=0.jpg";
    }


    public static class IndexBean {
        private String url;
        private String id;
        private String type;

        public IndexBean() {
        }

        public IndexBean(String url, String id, String type) {
            this.url = url;
            this.id = id;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
