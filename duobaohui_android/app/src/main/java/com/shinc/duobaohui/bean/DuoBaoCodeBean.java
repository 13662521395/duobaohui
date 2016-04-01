package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liugaopo on 15/10/8.
 */
public class DuoBaoCodeBean implements Serializable {
    private String code;
    private String msg;
    private List<DuoBaoCodeBean.CodeData> data;

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

    public List<CodeData> getData() {
        return data;
    }

    public void setData(List<CodeData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DuoBaoCodeBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class CodeData {
        private String code_num;
        private String create_time;

        public String getCode_num() {
            return code_num;
        }

        public void setCode_num(String code_num) {
            this.code_num = code_num;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        @Override
        public String toString() {
            return "CodeData{" +
                    "code_num='" + code_num + '\'' +
                    ", create_time='" + create_time + '\'' +
                    '}';
        }
    }
}
