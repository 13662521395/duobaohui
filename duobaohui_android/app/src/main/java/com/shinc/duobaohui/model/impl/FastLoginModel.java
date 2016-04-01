package com.shinc.duobaohui.model.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.bean.LoginBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.FastLoginGetVerifyCodeHttpRequestImpl;
import com.shinc.duobaohui.http.FastLoginHttpRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.umeng.message.PushAgent;

/**
 * 名称：RegisterModel
 * 作者：zhaopl 时间: 15/9/23.
 * 实现的主要功能：
 * 快速注册Model层；
 */
public class FastLoginModel {

    private Activity mActivity;
    private HttpUtils httpUtils;
    private SharedPreferencesUtils spUtils;

    public FastLoginModel(Activity mActivity) {
        this.mActivity = mActivity;
        httpUtils = new HttpUtils();
        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
    }

    public void commit(String tel, String code) {

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("tel", tel);
        requestParams.addBodyParameter("code", code);

        httpUtils.sendHttpPost(requestParams, ConstantApi.FAST_LOGIN, new FastLoginHttpRequestImpl(), mActivity);
    }

    public void getVerifyCode(String tel) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("tel", tel);
        httpUtils.sendHttpPost(requestParams, ConstantApi.FAST_LOGIN_GET_VERTIFY, new FastLoginGetVerifyCodeHttpRequestImpl(), mActivity);
    }


    public void saveData(LoginBean loginBean) {
        spUtils.add(Constant.SP_USER_ID, loginBean.getData().getId());
        spUtils.add(Constant.SP_NICK_NAME, loginBean.getData().getNick_name());
        spUtils.add(Constant.SP_REAL_NAME, loginBean.getData().getReal_name());
        spUtils.add(Constant.SH_ID, loginBean.getData().getSh_id());
        spUtils.add(Constant.SIGNATURE, loginBean.getData().getSignature());
        spUtils.add(Constant.HEAD_PIC, loginBean.getData().getHead_pic());
        spUtils.add(Constant.SP_TEL, loginBean.getData().getTel());
        spUtils.add(Constant.MONEY, loginBean.getData().getMoney());
        //todo 如果用户未登陆，登陆后，立即更新用户的alias;
        final PushAgent mPushAgent = PushAgent.getInstance(mActivity);
        final String userId = spUtils.get(Constant.SP_USER_ID, "");
        if (!TextUtils.isEmpty(userId)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mPushAgent.addAlias(userId, "userId");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        // 世和数据分析，绑定用户Id；
//        if (!TextUtils.isEmpty(userId)) {
//            ShiNcAgent.bindUserIdentifier(mActivity, userId);
//        }

    }
}
