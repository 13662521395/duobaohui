package com.shinc.duobaohui.bean;

/**
 * 名称：RechargeImgBean
 * 作者：zhaopl 时间: 16/1/14.
 * 实现的主要功能：
 */
public class RechargeImgBean {

    private String code;
    private String msg;
    private RechargeImg data;


    public class RechargeImg {
        private String id;
        private String title;
        private String link_url;
        private String pic_url;
        private String type;
        private String start_time;
        private String end_time;
        private String sort;

        public RechargeImg(String id, String title, String link_url, String pic_url, String type, String start_time, String end_time, String sort) {
            this.id = id;
            this.title = title;
            this.link_url = link_url;
            this.pic_url = pic_url;
            this.type = type;
            this.start_time = start_time;
            this.end_time = end_time;
            this.sort = sort;
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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        @Override
        public String toString() {
            return "RechargeImg{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", link_url='" + link_url + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    ", type='" + type + '\'' +
                    ", start_time='" + start_time + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", sort='" + sort + '\'' +
                    '}';
        }
    }


    public RechargeImgBean(String code, String msg, RechargeImg data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RechargeImg getData() {
        return data;
    }

    public void setData(RechargeImg data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "RechargeImgBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
