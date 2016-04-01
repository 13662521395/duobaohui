package com.shinc.duobaohui.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.shihe.shincdatastatisticssdk.ShiNcAgent;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.PushAgent;


/**
 * 所有activity继承的基类  其中目前包含友盟统计部分逻辑这里用的是页面统计方法；而不是按照activity来统计
 */
public class BaseActivity extends FragmentActivity {
    //友盟推送
    public static final String CALLBACK_RECEIVER_ACTION = "callback_receiver_action";
    private static PushAgent mPushAgent;
    public static IUmengRegisterCallback mRegisterCallback;
    public static IUmengUnregisterCallback mUnregisterCallback;
    protected SharedPreferencesUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        MyApplication.getInstance().addActivity(this);
        spUtils = new SharedPreferencesUtils(this, Constant.SP_LOGIN);
    }

    protected void setImmerseLayout(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivityName());
        MobclickAgent.onResume(this);
        ShiNcAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivityName());
        MobclickAgent.onPause(this);
        ShiNcAgent.onPause(this);

    }

    /**
     * 关闭N多activity
     *
     * @param activity
     */
    public void closeActivitys(Activity activity) {
        MyApplication.getInstance().destoryActivitys(activity);
    }

    /**
     * 这里如果有我们对activity的名称的要求的话  我们可以将这个方法重写；或者在这里将这个方法抽象化；
     *
     * @return
     */
    public String getActivityName() {

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();

        return runningActivity;
    }

    public void print(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
