package com.shinc.duobaohui.utils.web;

import android.content.Context;

/**
 * Created on 14/12/30.
 */
public class JsonFlagUtils {
    public static boolean isSuccessJson(String flag, Context context, OnJsonFlagListener onJsonFlagListener) {

        if ("1".equals(flag)) {
            onJsonFlagListener.success();

            return true;
        } else {
            onJsonFlagListener.error();
            return false;
        }
    }

    public static boolean isSuccessJson(String flag, final Context context) {
        return isSuccessJson(flag, context, new OnJsonFlagListener() {
            @Override
            public void success() {

            }

            @Override
            public void error() {
//                Toast.makeText(context, "网络连接失败,请您检查您的网络", Toast.LENGTH_LONG).show();

            }
        });

    }

}
