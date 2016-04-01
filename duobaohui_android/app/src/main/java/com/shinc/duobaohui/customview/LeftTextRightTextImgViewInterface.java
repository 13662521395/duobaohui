package com.shinc.duobaohui.customview;


import android.graphics.drawable.Drawable;

/**
 * 名称：LeftTextRightEditViewInterface
 * 作者：zhaopl 时间: 15/8/14.
 * 实现的主要功能：
 * 左边是Text,右边是Edit的控件；
 */
public interface LeftTextRightTextImgViewInterface {

    /**
     * 该条目点击的响应事件；
     * @param allClickListener
     */
    void setAllClick(AllClickListener allClickListener);


    /**
     * 该条目点击后回调的接口；
     */
    interface AllClickListener {
        void onAllClick();
    }

    /**
     * 设置右边的text的Image;
     *
     * @param drawable
     */
    void setRightTextImg(Drawable drawable);

    /**
     * 设置TextView中的文字；
     */
    void setLeftTextViewText(String text);

    void setRightTextText(String text);

}
