package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by liugaopo on 15/9/17.
 */
public class ReGridView extends GridView {
    public ReGridView(Context context) {
        super(context);
    }

    public ReGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
