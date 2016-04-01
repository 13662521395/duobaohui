package com.shinc.duobaohui.model;


import com.shinc.duobaohui.bean.LoginBean;

/**
 * 名称：LoginModelInterface
 * 作者：zhaopl 时间: 15/9/23.
 * 实现的主要功能：
 * 登陆页面的Model层的顶层接口；
 */
public interface LoginModelInterface {

    /**
     * 登陆接口；
     *
     * @param phoneNum
     * @param psw
     */
    void login(String phoneNum, String psw,String token);


    void saveData(LoginBean loginBean);
}
