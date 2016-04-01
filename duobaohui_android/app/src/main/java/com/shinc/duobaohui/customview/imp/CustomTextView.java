package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.shinc.duobaohui.utils.FontsUtils;


/**
 * 文件名：
 * Created by chaos on 15/7/16.
 * 功能：
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        initView(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setTypeface(FontsUtils.getTypeFace(context));
    }



}
