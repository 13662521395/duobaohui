package com.shinc.duobaohui.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shinc.duobaohui.R;

/**
 * 名称：CommitOrderDialog
 * 作者：zhaopl 时间: 15/11/4.
 * 实现的主要功能：
 */
public class CreateOrderDialog extends Dialog {

    private LayoutInflater inflater;

    private View view;

    private ImageView dots;
    private AnimationDrawable animationDrawable;

    public CreateOrderDialog(Context context) {
        super(context);
        initView(context);
    }

    public CreateOrderDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    protected CreateOrderDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }


    private void initView(Context context) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.loading_create_order_layout, null);

        dots = (ImageView) view.findViewById(R.id.anim);
        dots.setImageResource(R.drawable.create_order_anim);
        animationDrawable = (AnimationDrawable) dots.getDrawable();

        Window win = this.getWindow();
        Display display = win.getWindowManager().getDefaultDisplay();
//        win.requestFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
//        win.setBackgroundDrawable(new ColorDrawable(R.color.trans));
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        win.setAttributes(lp);
        this.setCanceledOnTouchOutside(false);

    }

    public void showLoading() {
        this.show();
        animationDrawable.start();
    }

    public void hideLoading() {
        this.dismiss();
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }

}
