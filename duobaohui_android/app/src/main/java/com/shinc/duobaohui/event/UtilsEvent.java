package com.shinc.duobaohui.event;

/**
 * 名称：UtilsEvent
 * 作者：zhaopl 时间: 15/10/12.
 * 实现的主要功能：
 *    工具Event，用于传递标识；
 */
public class UtilsEvent {

    private String flag;

    public UtilsEvent(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
