package com.shinc.duobaohui.bean;

import java.util.ArrayList;

/**
 * 名称：PayResultBean
 * 作者：zhaopl 时间: 15/10/15.
 * 实现的主要功能：
 *     支付结果页面的承载类；
 */
public class PayResultBean {

    private String code;
    private String msg;
    private PayResultDetailBean data;

    public PayResultBean(String code, String msg, PayResultDetailBean data) {
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

    public PayResultDetailBean getData() {
        return data;
    }

    public void setData(PayResultDetailBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PayResultBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class PayResultDetailBean {

        private ArrayList<String> codeList;
        private String codeTotalNum;


        public PayResultDetailBean(ArrayList<String> codeList, String codeTotalNum) {
            this.codeList = codeList;
            this.codeTotalNum = codeTotalNum;
        }

        public ArrayList<String> getCodeList() {
            return codeList;
        }

        public void setCodeList(ArrayList<String> codeList) {
            this.codeList = codeList;
        }

        public String getCodeTotalNum() {
            return codeTotalNum;
        }

        public void setCodeTotalNum(String codeTotalNum) {
            this.codeTotalNum = codeTotalNum;
        }

        @Override
        public String toString() {
            return "PayResultDetailBean{" +
                    "codeList=" + codeList +
                    ", codeTotalNum='" + codeTotalNum + '\'' +
                    '}';
        }
    }
}
