package com.shinc.duobaohui.utils;


import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.IWXAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 名称：PhoneNumVerify
 * 作者：zhaopl 时间: 15/7/9.
 * 实现的主要功能：
 */
public class VerifyUtils {

//    "中国大陆手机号号段：
//    移动号段：
//           130 131 132 133 134 135 136 137 138 139

//             145   147
//    联通号段：
//            150 151 152 153 155 156 157 158 159
//    电信号段：
//             170  175 176 177 178
//    虚拟运营商:
//             180 181 182 183 184 185 186  187 188 189

    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|14(5|7)|(15[^4])|17(0|5|6|7|8)|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 校验用户昵称的定义问题；
     * 昵称：汉字、数字、英文、下划线 限制：10个汉字以内
     *
     * @param nickName
     * @return
     */
    public static boolean isNickName(String nickName) {
        Pattern pattern = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$");
        Matcher matcher = pattern.matcher(nickName);
        return matcher.matches();
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    public static boolean isWXAppInstalledAndSupported(Context context,
                                                       IWXAPI api) {
        boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled()
                && api.isWXAppSupportAPI();
        if (!sIsWXAppInstalledAndSupported) {
            Toast.makeText(context, "没有安装微信~", Toast.LENGTH_SHORT).show();
        }

        return sIsWXAppInstalledAndSupported;
    }
}
