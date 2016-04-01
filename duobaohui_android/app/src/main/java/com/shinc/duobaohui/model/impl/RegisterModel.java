package com.shinc.duobaohui.model.impl;

import android.app.Activity;

import com.shinc.duobaohui.bean.RegisterBean;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.GetVerifyCodeHttpRequestImpl;
import com.shinc.duobaohui.http.RegisterHttpRequestImpl;
import com.shinc.duobaohui.model.RegisterModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.md5.MD5;

/**
 * 名称：RegisterModel
 * 作者：zhaopl 时间: 15/9/23.
 * 实现的主要功能：
 * 注册Model层；
 */
public class RegisterModel implements RegisterModelInterface {

    private Activity mActivity;
    private HttpUtils httpUtils;

    public RegisterModel(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
    }

    @Override
    public void commit(String tel, String password, String repassword, String code) {

        String params = "?tel=" + tel + "&password=" + MD5.GetMD5Code(password) + "&nick_name=" + repassword + "&code=" + code;
        httpUtils.sendHttpPost(null, ConstantApi.REGISTER + params, new RegisterHttpRequestImpl(), mActivity);
    }

    @Override
    public void getVerifyCode(String tel) {
        String params = "?tel=" + tel;
        httpUtils.sendHttpPost(null, ConstantApi.GETVERIFYCODE + params, new GetVerifyCodeHttpRequestImpl(), mActivity);
    }

    @Override
    public void saveData(RegisterBean registerBean) {

    }
}
