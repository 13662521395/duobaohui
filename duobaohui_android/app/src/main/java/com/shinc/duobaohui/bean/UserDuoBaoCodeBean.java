package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liugaopo on 15/10/8.
 */
public class UserDuoBaoCodeBean implements Serializable {
    private String code;
    private String msg;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "UserDuoBaoCodeBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
