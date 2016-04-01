package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.LeftTextRightEditViewInterface;


/**
 * 名称：LeftTextRightEditViewImpl
 * 作者：zhaopl 时间: 15/8/14.
 * 实现的主要功能：
 */
public class LeftTextRightEditViewImpl extends RelativeLayout implements LeftTextRightEditViewInterface {


    private TextView leftTextView;

    private EditText rightEditText;

    public LeftTextRightEditViewImpl(Context context) {
        super(context);
        initView(context);
    }

    public LeftTextRightEditViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LeftTextRightEditViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
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
        inflater.inflate(R.layout.lefttext_rightedit_view_layout, this);

        leftTextView = (TextView) findViewById(R.id.left_text_view);
        rightEditText = (EditText) findViewById(R.id.right_edit_text);

    }

    @Override
    public String getEditText() {
        if (rightEditText != null) {
            return rightEditText.getText().toString();
        }
        return "";
    }

    /**
     * 设置右边edit text 的hint 文字；
     *
     * @param hintText
     */
    @Override
    public void setEditHintText(String hintText) {

        if (rightEditText != null) {
            Log.e("setrightEditTexthint", hintText);
            rightEditText.setHint(hintText);
        }
    }

    /**
     * 设置左边的textView的文字；
     *
     * @param text
     */
    @Override
    public void setTextViewText(String text) {

        if (leftTextView != null) {
            leftTextView.setText(text);
        }
    }

    /**
     * 设置右边的EditText的文字；
     *
     * @param text
     */
    @Override
    public void setEditTextText(String text) {
        if (rightEditText != null) {
            Log.e("setEditText", text);

            rightEditText.setText(text);
        }
    }
}
