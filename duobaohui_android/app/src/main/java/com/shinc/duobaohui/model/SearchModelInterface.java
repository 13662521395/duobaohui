package com.shinc.duobaohui.model;

import com.lidroid.xutils.DbUtils;

/**
 * 名称：SearchModelInterface
 * 作者：zhaopl 时间: 15/8/21.
 * 实现的主要功能：
 * 搜索页面Modle层接口；
 */
public interface SearchModelInterface {

    /**
     * 返回；
     */
    void back();


    /**
     * 得到搜索历史；
     * @param dbUtils
     */
    void getHistorySearchData(DbUtils dbUtils);


    /**
     * 得到热搜数据；
     */
    void getHotSearchData();


    /**
     * 得到实时搜索的结果；
     * @param keyword
     */
    void getRealTimeSearchData(String keyword);


    /**
     * 搜索；
     * @param keyword
     */
    void search(String keyword,int page);


    /**
     * 清除搜索历史；
     */
    void cleanSearchHistory(DbUtils dbUtils);

}
