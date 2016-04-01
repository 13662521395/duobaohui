package com.shinc.duobaohui.bean;

/**
 * Created by liugaopo on 15/11/6.
 * 夺宝纪录 Tab Number
 */
public class DuoBaoTabNumberBean {
    /*{

        "code": ​1,
            "msg": "成功",
            "data":

        {
            "all": ​0,
                "run": ​0,
                "finish": ​0
        }

    }*/
    private String code;
    private String msg;
    private DuoBaoNumberDataBean data;


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

    public DuoBaoNumberDataBean getData() {
        return data;
    }

    public void setData(DuoBaoNumberDataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DuoBaoTabNumberBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class DuoBaoNumberDataBean {
        private String all;
        private String run;
        private String finish;

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public String getRun() {
            return run;
        }

        public void setRun(String run) {
            this.run = run;
        }

        public String getFinish() {
            return finish;
        }

        public void setFinish(String finish) {
            this.finish = finish;
        }

        @Override
        public String toString() {
            return "DuoBaoNumberDataBean{" +
                    "all='" + all + '\'' +
                    ", run='" + run + '\'' +
                    ", finish='" + finish + '\'' +
                    '}';
        }
    }
}
