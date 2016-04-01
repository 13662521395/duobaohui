package com.shinc.duobaohui.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.utils.DensityUtil;

/**
 * 名称：CommitOrderDialog
 * 作者：zhaopl 时间: 15/11/4.
 * 实现的主要功能：
 */
public class LoadingDialog extends Dialog {

    private LayoutInflater inflater;

    private View view;

    private ProgressBar dots;

    public LoadingDialog(Context context) {
        super(context);
        initView(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }


    private void initView(Context context) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.loading_dialog_layout, null);

        dots = (ProgressBar) view.findViewById(R.id.loading_pb);

        Window win = this.getWindow();
        this.setContentView(view);
        this.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = DensityUtil.dip2px(context, 60);
        lp.height = DensityUtil.dip2px(context, 60);
        lp.gravity = Gravity.CENTER;
        win.setAttributes(lp);

    }

    public void showLoading() {
        this.show();
    }

    public void hideLoading() {
        this.dismiss();
    }

}
