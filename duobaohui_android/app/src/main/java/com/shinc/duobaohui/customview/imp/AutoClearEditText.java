package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;

/**
 * 名称：AutoClearEditText
 * 作者：zhaopl 时间: 15/11/24.
 * 实现的主要功能：
 */
public class AutoClearEditText extends RelativeLayout {

    private EditText searchEdit;
    private ImageView iconClear;

    private EditTextChangeListener editTextChangeListener;

    private OnEditorActionListener onEditorActionListener;

    public void setEditTextChangeListener(EditTextChangeListener editTextChangeListener) {
        this.editTextChangeListener = editTextChangeListener;
    }

    public void setOnEditorActionListener(OnEditorActionListener onEditorActionListener) {
        this.onEditorActionListener = onEditorActionListener;
    }

    public AutoClearEditText(Context context) {
        super(context);
        initView(context);
    }

    public AutoClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AutoClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.auto_clear_layout, this);

        searchEdit = (EditText) findViewById(R.id.search_edit);
        iconClear = (ImageView) findViewById(R.id.icon_clear);

        initListener();
    }

    private void initListener() {
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s.toString().trim())) {
                    iconClear.setVisibility(VISIBLE);
                } else {
                    iconClear.setVisibility(GONE);
                    if (editTextChangeListener != null) {
                        editTextChangeListener.textChange(s.toString());
                    }
                }
            }
        });

        iconClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击清除文字；
                searchEdit.setText("");
            }
        });

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

//                if (onEditorActionListener != null) {
//                    return onEditorActionListener.onEditAction();
//                }
//                return false;
                return onEditorActionListener != null && onEditorActionListener.onEditAction();
            }
        });
    }

    public EditText getEdit() {
        return searchEdit;
    }

    interface OnEditorActionListener {
        boolean onEditAction();
    }

    /**
     * 得到text;
     *
     * @return
     */
    public String getText() {
        return searchEdit.getText().toString();
    }

    /**
     * 设置hint字符；
     *
     * @param str
     */
    public void setHint(String str) {

        searchEdit.setHint(str);
    }

    /**
     * 设置收入框中的文字；
     *
     * @param str
     */
    public void setSearchText(String str) {
        searchEdit.setText(str);
        searchEdit.setSelection(str.length());
    }


    interface EditTextChangeListener {

        void textChange(String str);
    }

}
