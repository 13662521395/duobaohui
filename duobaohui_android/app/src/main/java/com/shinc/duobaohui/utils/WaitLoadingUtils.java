package com.shinc.duobaohui.utils;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.imp.WaitLoading;


/**
 * Created by efort on 15/10/8.
 */
public class WaitLoadingUtils {
    WaitLoading waitLoading;
    RelativeLayout noWeb;

    public WaitLoadingUtils(Activity view) {
        waitLoading = (WaitLoading) view.findViewById(R.id.wait_loading);
        noWeb = (RelativeLayout) view.findViewById(R.id.no_web);
        haveWeb();
        disable();
    }

    public WaitLoadingUtils(View view) {
        waitLoading = (WaitLoading) view.findViewById(R.id.wait_loading);
        noWeb = (RelativeLayout) view.findViewById(R.id.no_web);
        haveWeb();
        disable();
    }

    public void haveWeb() {
        noWeb.setClickable(false);
        waitLoading.setClickable(false);
        noWeb.setVisibility(View.GONE);
    }

    public void isNoWeb(final OnNoWebClick onNoWebClick) {
        noWeb.setVisibility(View.VISIBLE);
        noWeb.setClickable(true);
        noWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoWebClick.onClick();
            }
        });
    }

    public void show() {
        noWeb.setClickable(false);
        waitLoading.setClickable(false);
        noWeb.setVisibility(View.GONE);
        waitLoading.setVisibility(View.VISIBLE);
    }

    public void disable() {
        waitLoading.setVisibility(View.GONE);
    }

    public interface OnNoWebClick {
        void onClick();
    }
}
