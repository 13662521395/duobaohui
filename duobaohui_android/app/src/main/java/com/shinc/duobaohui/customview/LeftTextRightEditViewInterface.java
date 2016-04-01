package com.shinc.duobaohui.customview;

/**
 * 名称：LeftTextRightEditViewInterface
 * 作者：zhaopl 时间: 15/8/14.
 * 实现的主要功能：
 * 左边是Text,右边是Edit的控件；
 */
public interface LeftTextRightEditViewInterface {

    /**
     * 得到右边的editText中输入的文字；
     *
     * @return
     */
    String getEditText();

    /**
     * 设置EditText中hint的值；
     */
    void setEditHintText(String hintText);


    /**
     * 设置TextView中的文字；
     */
    void setTextViewText(String text);

    void setEditTextText(String text);
}
