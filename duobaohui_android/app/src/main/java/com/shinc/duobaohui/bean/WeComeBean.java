package com.shinc.duobaohui.bean;

/**
 * Created by liugaopo on 15/12/15.
 */
public class WeComeBean {
    private String code;
    private String msg;
    private WeComeChildBean data;

    public WeComeChildBean getData() {
        return data;
    }

    public void setData(WeComeChildBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public class WeComeChildBean {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
