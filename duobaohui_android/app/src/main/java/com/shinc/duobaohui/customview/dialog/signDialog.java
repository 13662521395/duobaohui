package com.shinc.duobaohui.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.shinc.duobaohui.R;

/**
 * 名称：signDialog
 * 作者：zhaopl 时间: 16/1/13.
 * 实现的主要功能：
 */
public class SignDialog extends Dialog {

    private LayoutInflater inflater;

    private View view;

    private TextView cancelImg;

    private TextView confirmImg;


    private DialogBtnListener dialogBtnListener;


    public DialogBtnListener getDialogBtnListener() {
        return dialogBtnListener;
    }

    public void setDialogBtnListener(DialogBtnListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    public SignDialog(Context context) {
        super(context);
        initView(context);
    }

    public SignDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    protected SignDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }


    private void initView(Context context) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.guide_sign_one_layout, null);


        initListener(view);


        Window win = this.getWindow();
        this.setContentView(view);
        this.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setAttributes(lp);

    }

    private void initListener(View view) {

        cancelImg = (TextView) view.findViewById(R.id.cancel_act);


        confirmImg = (TextView) view.findViewById(R.id.confirm_act);


        confirmImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialogBtnListener != null) {
                    dialogBtnListener.positiveClick();
                }
            }
        });

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogBtnListener != null) {
                    dialogBtnListener.navigateClick();
                }
            }
        });
    }

    public void showLoading() {
        this.show();
    }

    public void hideLoading() {
        this.dismiss();
    }


    public interface DialogBtnListener {

        void positiveClick();

        void navigateClick();
    }
}
