package com.shinc.duobaohui.model;

/**
 * Created by liugaopo on 15/10/6.
 */
public interface PreAnnounceInterface {

    void getListData(String activeId, String page, String length);


    void getListData(String activeId, String page, String length, String period_number);
}
