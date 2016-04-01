package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.shinc.duobaohui.utils.FontsUtils;


/**
 * Created by efort on 15/7/23.
 */
public class CustomCheckBox extends CheckBox {
    public CustomCheckBox(Context context) {
        super(context);
        initView(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setTypeface(FontsUtils.getTypeFace(context));
    }


}
