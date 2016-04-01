package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.shinc.duobaohui.utils.FontsUtils;


/**
 * Created by efort on 15/7/23.
 */
public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        super(context);
        initView(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setTypeface(FontsUtils.getTypeFace(context));
    }

}
