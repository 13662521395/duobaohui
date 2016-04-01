package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.MenuListBean;

/**
 * 名称：CategoryMenuEvent
 * 作者：zhaopl 时间: 15/11/23.
 * 实现的主要功能：分类List Event;
 */
public class CategoryMenuEvent {

    private MenuListBean menuListBean;

    public CategoryMenuEvent(MenuListBean menuListBean) {
        this.menuListBean = menuListBean;
    }

    public MenuListBean getMenuListBean() {
        return menuListBean;
    }
}
