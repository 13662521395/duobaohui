package com.shinc.duobaohui.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * 名称：DialogUtils
 * 作者：zhaopl 时间: 15/7/2.
 * 实现的主要功能：
 */
public class DialogUtils {

    public static AlertDialog createDialog(final Activity mActivity, View view) {

        final AlertDialog alertDialog = new AlertDialog.Builder(mActivity)
                .create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams params = alertDialog.getWindow()
                .getAttributes();

        params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;

        alertDialog.getWindow().setAttributes(params);
        window.setContentView(view);
        return alertDialog;

    }

    public static AlertDialog createDialog(final Context context, View view,
                                           int width, int height) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .create();
        alertDialog.show();
        int w = DensityUtil.dip2px(context, width);
        int h = DensityUtil.dip2px(context, height);
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams params = alertDialog.getWindow()
                .getAttributes();

        params.width = w;
        params.height = h;

        alertDialog.getWindow().setAttributes(params);
        window.setContentView(view);
        return alertDialog;

    }

    public static AlertDialog createLoadingDialog(final Context context, View view,
                                                  int width, int height) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .create();
        alertDialog.show();
        int w = DensityUtil.dip2px(context, width);
        int h = DensityUtil.dip2px(context, height);
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams params = alertDialog.getWindow()
                .getAttributes();
//        Log.e("w+h", w + "---" + h);

//        params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
//        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;

        params.width = w;
        params.height = h;

        alertDialog.getWindow().setAttributes(params);
        window.setContentView(view);
        return alertDialog;

    }
}
