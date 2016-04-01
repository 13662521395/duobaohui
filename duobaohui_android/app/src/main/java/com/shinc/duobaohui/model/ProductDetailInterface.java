package com.shinc.duobaohui.model;

/**
 * 名称：ProductDetailInterface
 * 作者：zhaopl 时间: 15/10/6.
 * 实现的主要功能：
 * 商品详情页面model层的顶级接口；
 */
public interface ProductDetailInterface {

    /**
     * 获取商品详情；
     *
     * @param productId
     */
    void getProductInfo(String productId);


    /**
     * 获取该活动的状态；
     *
     * @param activeId
     */
    void getActiveStatus(String activeId);


    /**
     * 获取该活动的参与记录；
     *
     * @param activeId
     */
    void getTakePartRecord(String activeId,String page);
}
