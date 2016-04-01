package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangtianhe on 15/11/20.
 * 通知的 bean
 */
public class NoticeListBean implements Serializable {

    private String code;
    private String msg;
    private List<NoticeChildListBean> data;

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

    public List<NoticeChildListBean> getData() {
        return data;
    }

    public void setData(List<NoticeChildListBean> data) {
        this.data = data;
    }

    public NoticeListBean() {
    }

    public NoticeListBean(String code, String msg, List<NoticeChildListBean> data) {

        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "NoticeListBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class NoticeChildListBean implements Serializable {
        private String id;
        private String create_time;
        private String update_time;
        private String title;
        private String content;

        public NoticeChildListBean() {
        }

        public NoticeChildListBean(String id, String create_time, String update_time, String title, String content) {
            this.id = id;
            this.create_time = create_time;
            this.update_time = update_time;
            this.title = title;
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "NoticeChildListBean{" +
                    "id='" + id + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

}
