package com.shinc.duobaohui.model.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.bean.LoginBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.http.LoginHttpRequestImpl;
import com.shinc.duobaohui.model.LoginModelInterface;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.md5.MD5;
import com.umeng.message.PushAgent;

/**
 * 名称：LoginModel
 * 作者：zhaopl 时间: 15/9/23.
 * 实现的主要功能：
 * 登陆model的实现类；
 */
public class LoginModel implements LoginModelInterface {

    private Activity mActivity;
    private SharedPreferencesUtils spUtils;
    private HttpUtils httpUtils;

    public LoginModel(Activity mActivity) {
        this.mActivity = mActivity;
        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
        httpUtils = new HttpUtils();
    }

    @Override
    public void login(String phoneNum, String psw, String token) {

        String params = "?tel=" + phoneNum + "&password=" + MD5.GetMD5Code(psw) + "&form_token=" + token + "&key=" + MD5.GetMD5Code(MD5.GetMD5Code(psw) + MyApplication.keyWord + token);
        httpUtils.sendHttpPost(null, ConstantApi.LOGIN + params, new LoginHttpRequestImpl(), mActivity);
    }

    @Override
    public void saveData(LoginBean loginBean) {
        // todo 保存获取到的用户数据；
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
    }
}
