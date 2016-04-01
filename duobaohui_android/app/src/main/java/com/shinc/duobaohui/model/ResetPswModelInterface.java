package com.shinc.duobaohui.model;


import com.shinc.duobaohui.bean.RegisterBean;

/**
 * 名称：LoginModelInterface
 * 作者：zhaopl 时间: 15/9/23.
 * 实现的主要功能：
 * 注册页面的Model层的顶层接口；
 */
public interface ResetPswModelInterface {


    /**
     * 注册提交接口；
     *
     * @param tel
     * @param password
     * @param repassword
     * @param code
     */
    void commit(String tel, String password, String repassword, String code);


    /**
     * 获取短信验证码接口；
     * @param tel
     */
    void getVerifyCode(String tel);


    /**
     * 保存数据；
     *
     * @param registerBean
     */
    void saveData(RegisterBean registerBean);
}
