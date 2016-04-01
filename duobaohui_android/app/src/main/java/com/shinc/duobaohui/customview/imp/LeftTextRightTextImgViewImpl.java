package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.LeftTextRightTextImgViewInterface;


/**
 * 名称：LeftTextRightEditViewImpl
 * 作者：zhaopl 时间: 15/8/14.
 * 实现的主要功能：
 */
public class LeftTextRightTextImgViewImpl extends RelativeLayout implements LeftTextRightTextImgViewInterface {


    private TextView leftTextView;

    private TextView rightTextImg;

    public LeftTextRightTextImgViewImpl(Context context) {
        super(context);
        initView(context);
    }

    public LeftTextRightTextImgViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LeftTextRightTextImgViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化view;
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.lefttext_righttextimg_view_layout, this);

        leftTextView = (TextView) findViewById(R.id.left_text_view_view);
        rightTextImg = (TextView) findViewById(R.id.right_text_img_view);

    }

    @Override
    public void setAllClick(final AllClickListener allClickListener) {

        rightTextImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                allClickListener.onAllClick();
            }
        });
    }

    @Override
    public void setRightTextImg(Drawable drawable) {

        if (rightTextImg != null) {
            rightTextImg.setCompoundDrawables(null, null, drawable, null);
        }
    }

    @Override
    public void setLeftTextViewText(String text) {

        if (leftTextView != null) {
            Log.e("leftTextView",text);
            leftTextView.setText(text);
        }
    }

    @Override
    public void setRightTextText(String text) {

        if (rightTextImg != null) {
            rightTextImg.setText(text);
        }
    }
}
