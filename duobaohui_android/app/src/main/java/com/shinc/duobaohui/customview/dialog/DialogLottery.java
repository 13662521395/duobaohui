package com.shinc.duobaohui.customview.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.FastLoginActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.WinRecrodActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.customview.share.ShareOncilckListener;
import com.shinc.duobaohui.utils.umeng.ShareUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;


/**
 * Created by liugaopo on 15/11/6.
 */
public class DialogLottery {

    /*中奖通知*/
    public static void createLotteryDialog(final Activity mActivity, String period, String name) {
        View inflate = mActivity.getLayoutInflater().inflate(R.layout.lottery_layout, null);
        TextView tvPeriod = (TextView) inflate.findViewById(R.id.lattery_layout_period);
        tvPeriod.setText(period);
        TextView tvName = (TextView) inflate.findViewById(R.id.lattery_layout_periodName);
        tvName.setText(name);
        final Dialog dialog = new Dialog(mActivity);
        //   前面加一句:
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        Window win = dialog.getWindow();
        win.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
        dialog.show();
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (TextUtils.isEmpty(MyApplication.spUtils.get(Constant.SP_USER_ID, ""))) {
                    intent.setClass(mActivity, FastLoginActivity.class);
                    intent.putExtra(Constant.WinDialog, "1");
                } else {
                    intent.setClass(mActivity, WinRecrodActivity.class);
                }
                mActivity.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Window win = mActivity.getWindow();
                win.setBackgroundDrawableResource(android.R.color.white);
            }
        });
    }

    /*恭喜您领奖成功*/
    public static void createLunckyDialog(final Activity mActivity, final String msg, final String type) {
        View inflate = mActivity.getLayoutInflater().inflate(R.layout.dialog_shaidan_layout, null);
        final CustomTextView ok = (CustomTextView) inflate.findViewById(R.id.dialog_shaidan_layout_yes);
        CustomTextView no = (CustomTextView) inflate.findViewById(R.id.dialog_shaidan_layout_no);
        final Dialog dia = new Dialog(mActivity);
        //   前面加一句:
        dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dia.setContentView(inflate);
        Window win = dia.getWindow();
        win.setBackgroundDrawableResource(android.R.color.transparent);
        dia.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        dia.show();

        final ShareUtils instance = ShareUtils.getInstance(mActivity);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok.setEnabled(false);
                dia.dismiss();
                instance.shareProduct(v, "我在夺宝会中了" + msg + "，膜拜吧", "夺宝会APP", "http://7xnef1.com1.z0.glb.clouddn.com/icon_app.png", ConstantApi.SHARE_HOST + "system/download/share?userId=" + MyApplication.spUtils.get(Constant.SP_USER_ID, ""), "中奖了，快去向小伙伴们炫耀一下吧");
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia.dismiss();
                if (type.equals("1")) {
                    mActivity.finish();
                }
            }
        });
        dia.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ok.setEnabled(true);
            }
        });
        instance.setShareOncilckListener(new ShareOncilckListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int stCode, SocializeEntity entity) {
                //todo 分享完成的监听
                if (type.equals("1")) {
                    mActivity.finish();
                }
            }
        });
    }


    /*取消订单*/
    public static void createFinishPayPageDialog(final Activity mActivity, final DialogOnListener dialogOnListener) {
        View inflate = mActivity.getLayoutInflater().inflate(R.layout.finish_pay_page_layout, null);
        Button tvYes = (Button) inflate.findViewById(R.id.finish_dialog_yes);
        Button tvNo = (Button) inflate.findViewById(R.id.finish_dialog_no);
        final Dialog dialog = new Dialog(mActivity,R.style.blackDialog);
        //   前面加一句:
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = mActivity.getResources().getDimensionPixelSize(R.dimen.s_256dp);
        lp.height = mActivity.getResources().getDimensionPixelSize(R.dimen.s_140dp);
        dialog.getWindow().setAttributes(lp);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Window win = mActivity.getWindow();
                win.setBackgroundDrawableResource(android.R.color.white);
                Toast.makeText(mActivity, "您已取消此次交易！", Toast.LENGTH_LONG).show();
                mActivity.finish();
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
//                    return true;
//                } else {
                return true; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
//                }
            }
        });

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "您已取消此次交易！", Toast.LENGTH_LONG).show();
                mActivity.finish();
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOnListener.onClick(v);
            }
        });


    }

    public interface DialogOnListener {
        void onClick(View view);
    }

}
