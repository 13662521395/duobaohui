package com.shinc.duobaohui.customview.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by efort on 15/9/10.
 */
public class SharePopLayout extends PopupWindow {
    private OnShareClickListener onClick;
    private RelativeLayout view;
    //用于加载view
    private LayoutInflater inflater;
    private Activity mActivity;
    private CustomTextView tvWord;
    RelativeLayout shareForWXZone;
    RelativeLayout shareForWX;
    RelativeLayout shareForQQ;
    RelativeLayout shareForSina;
    RelativeLayout shareForTXWB;
    RelativeLayout shareForEmail;
    TextView cancel;

    private OnCancelListener onCancelListener;

    public SharePopLayout(final Activity context, OnShareClickListener onClick) {
        super(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = (RelativeLayout) inflater.inflate(R.layout.ument_share_custom_pop, null);
        this.mActivity = context;

        tvWord = (CustomTextView) view.findViewById(R.id.share_show_text);
        shareForWXZone = (RelativeLayout) view.findViewById(R.id.share_wx_zone);
        shareForWX = (RelativeLayout) view.findViewById(R.id.share_wx);
        shareForQQ = (RelativeLayout) view.findViewById(R.id.share_qq);
        shareForSina = (RelativeLayout) view.findViewById(R.id.share_sina);
        shareForTXWB = (RelativeLayout) view.findViewById(R.id.share_tx_wb);
        shareForEmail = (RelativeLayout) view.findViewById(R.id.share_email);
        shareForEmail.setVisibility(View.INVISIBLE);
        cancel = (TextView) view.findViewById(R.id.share_cancel);

        initClick(onClick);

        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(android.R.anim.fade_out);

        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x60000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //view添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    }

    public void setTvWord(String var) {
        if (!TextUtils.isEmpty(var)) {
            tvWord.setText(var);
            tvWord.setVisibility(View.VISIBLE);
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    return false;
                }
            });
        } else {
            tvWord.setVisibility(View.GONE);

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    int height = view.findViewById(R.id.share_layout).getTop();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            cancel();
                        }
                    }
                    return true;
                }
            });
        }
    }

    private void initClick(final OnShareClickListener onClickListener) {
        shareForWXZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(SHARE_MEDIA.WEIXIN_CIRCLE);
                cancel();
            }
        });
        shareForWX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(SHARE_MEDIA.WEIXIN);
                cancel();
            }
        });
        shareForQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(SHARE_MEDIA.QQ);
                cancel();
            }
        });
        shareForSina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(SHARE_MEDIA.SINA);
                cancel();
            }
        });
        shareForTXWB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(SHARE_MEDIA.QZONE);
                cancel();
            }
        });
        shareForEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(SHARE_MEDIA.EMAIL);
                cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(null);
                cancel();
            }
        });
    }

    //窗口
    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    private void cancel() {

        if (onCancelListener != null) {
            onCancelListener.onCancel();
        }
        dismiss();
        Log.e("","cancel");
        //backgroundAlpha(1f, mActivity);

    }

    /* 关闭PopupWindow触发该监听 */
    public interface OnCancelListener {
        void onCancel();
    }

    public interface OnShareClickListener {
        void onClick(SHARE_MEDIA share_media);
    }

    /**
     * 控制背景变暗；
     *
     * @param bgAlpha   设置背景的透明值;
     * @param mActivity 传递Activity;
     */
    public void backgroundAlpha(float bgAlpha, Activity mActivity) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();

        lp.alpha = bgAlpha; // 0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    } // 调用

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {

        super.setOnDismissListener(onDismissListener);
    }
}
