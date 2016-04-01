package com.shinc.duobaohui.model;

/**
 * 名称：deprivePreciousModelInterface
 * 作者：zhaopl 时间: 15/9/29.
 * 实现的主要功能：
 * 首页的页面Model层；
 */
public interface DeprivePreciousModelInterface {

    /**
     * 得到banner图，以及中奖通知的方法；
     */
    void getBanner();

    /**
     * 根据条件获取对应的数据；
     *
     * @param sort 请求类型；
     * @param page 请求页数；
     */
    void getProductInfo(String sort, String page,String deviceId);


    /**
     *  获取获奖用户接口；
     */
    void getwinnerList();




}
