package com.shinc.duobaohui.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.shinc.duobaohui.FastLoginActivity;
import com.shinc.duobaohui.constant.Constant;

/**
 * 名称：CodeVerifyUtils
 * 作者：zhaopl 时间: 15/11/25.
 * 实现的主要功能：
 * 接口返回验证Code的方法；
 */
public class CodeVerifyUtils {

    private static SharedPreferencesUtils spUtils;

    public static boolean verifyCode(String code) {

        return "999999".equals(code);//验证session过期；
    }

    public static void verifySession(Activity activity) {

        init(activity);

        Toast.makeText(activity, "登陆信息失效，请重新登陆！", Toast.LENGTH_LONG).show();
        spUtils.deleteAll();
        Intent intent = new Intent(activity, FastLoginActivity.class);
        intent.putExtra("TOMAIN", "tomain");
        activity.startActivity(intent);
    }

    private static void init(Activity activity) {

        spUtils = new SharedPreferencesUtils(activity, Constant.SP_LOGIN);

    }
}
