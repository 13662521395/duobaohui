package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.R;


/**
 * 名称：SearchEditText
 * 作者：zhaopl 时间: 15/9/8.
 * 实现的主要功能：
 */
public class SearchEditText extends RelativeLayout {
    public SearchEditText(Context context) {
        super(context);
        initView(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化数据；
     */
    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.lefttext_righttextimg_view_layout, this);
    }
}
