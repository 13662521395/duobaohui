package com.shinc.duobaohui.utils;

import android.content.Context;

/**
 * 名称：ScreenUtil
 * 作者：zhaopl 时间: 15/10/16.
 * 实现的主要功能：
 */
public class ScreenUtil {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
