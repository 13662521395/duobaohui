package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.shinc.duobaohui.utils.FontsUtils;


/**
 * Created by efort on 15/7/23.
 */
public class CustomButton extends Button {
    public CustomButton(Context context) {
        super(context);
        initView(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setTypeface(FontsUtils.getTypeFace(context));
    }
}
