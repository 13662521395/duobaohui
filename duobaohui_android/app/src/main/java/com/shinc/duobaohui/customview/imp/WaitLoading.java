package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.R;

/**
 * Created by efort on 15/10/8.
 * 加载页面 只有两个方法，一个开启一个终止
 */
public class WaitLoading extends RelativeLayout {
    LayoutInflater inflater;
    RelativeLayout view;
    ProgressBar progress;


    public WaitLoading(Context context) {
        super(context);
        initView(context);
    }

    public WaitLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WaitLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = (RelativeLayout) inflater.inflate(R.layout.wait_for_loading, this);
        view.setClickable(false);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
    }
}
