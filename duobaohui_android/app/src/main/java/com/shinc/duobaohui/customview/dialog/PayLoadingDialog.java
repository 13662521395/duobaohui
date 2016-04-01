package com.shinc.duobaohui.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.imp.DotsTextView;
import com.shinc.duobaohui.utils.DensityUtil;

/**
 * 名称：CommitOrderDialog
 * 作者：zhaopl 时间: 15/11/4.
 * 实现的主要功能：
 */
public class PayLoadingDialog extends Dialog {

    private LayoutInflater inflater;

    private View view;

    private DotsTextView dots;

    public PayLoadingDialog(Context context) {
        super(context);
        initView(context);
    }

    public PayLoadingDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    protected PayLoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }


    private void initView(Context context) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pay_loading, null);

        dots = (DotsTextView) view.findViewById(R.id.pay_dots);

        Window win = this.getWindow();
        this.setContentView(view);
        this.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = DensityUtil.dip2px(context, 100);
        lp.height = DensityUtil.dip2px(context, 100);
        lp.gravity = Gravity.CENTER;
        win.setAttributes(lp);

    }

    public void startShow() {
        this.show();
        dots.showAndPlay();
    }

    public void endHide() {
        this.dismiss();
        dots.hideAndStop();
    }

}
