package com.shinc.duobaohui.customview.dialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.shinc.duobaohui.utils.DensityUtil;

/**
 * 名称：SignGuideView
 * 作者：zhaopl 时间: 16/1/13.
 * 实现的主要功能：
 */
public class SignGuideView extends PopupWindow {

    //private View view;

    public SignGuideView(final Activity mActiviy, View view, View anchor, String type) {



        //view = mActiviy.getLayoutInflater().inflate(R.layout.guide_sign_one_layout, null);

        //ImageView guideImg = (ImageView) view.findViewById(R.id.guide_img);

        //guideImg.setImageResource(img);

        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽

        //设置SelectPicPopupWindow弹出窗体可点击
        //this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(android.R.anim.fade_out);


        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        int anchorHeight = DensityUtil.dip2px(mActiviy, 50);


        switch (type) {
            case "1":
                Log.e("height_location", location[0] + "----------" + location[1] + "");
                Log.e("height", (location[1] ) + "");
                ColorDrawable dw = new ColorDrawable(0x60000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
                this.setBackgroundDrawable(dw);
                this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
                //设置SelectPicPopupWindow弹出窗体的高
                this.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
                break;
            case "2":
                this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                //设置SelectPicPopupWindow弹出窗体的高
                this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                this.showAsDropDown(anchor, DensityUtil.dip2px(mActiviy, 40), 0);
                break;
        }
    }
}
