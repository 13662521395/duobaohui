package com.shinc.duobaohui.paylibrary.customview.imp;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.utils.ImageLoad;

/**
 * Created by liugaopo on 15/8/12.
 */
public class PayChildView extends RelativeLayout {

    private TextView tv_word;
    private ImageView icon;

    private Activity mAcivity;

    public PayChildView(Context context) {
        super(context);
        mAcivity = (Activity) context;
        initView();
    }

    public PayChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAcivity = (Activity) context;
        initView();
    }

    public PayChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAcivity = (Activity) context;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) mAcivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.alipay_layout, this);
        icon = (ImageView) findViewById(R.id.alipay_layout_icon);
        tv_word = (TextView) findViewById(R.id.alipay_layout_tv_word);
    }

    public void setIcon(String iconUrl, int id) {
        if (iconUrl == null || iconUrl.equals("")) {
            icon.setImageResource(id);
        } else {
            ImageLoad.getInstance(mAcivity).setImageToView(iconUrl, icon);
        }
    }

    public void setTv_word(String mrg) {
        if (!mrg.equals("") && mrg != null) {
            tv_word.setText(mrg);
        }
    }
}
