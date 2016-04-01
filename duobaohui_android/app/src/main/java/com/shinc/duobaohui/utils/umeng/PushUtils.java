package com.shinc.duobaohui.utils.umeng;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.shinc.duobaohui.base.MyApplication;
import com.umeng.common.message.UmengMessageDeviceConfig;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.tag.TagManager;

/**
 * Created by wangmingxing on 15/6/24.
 */
public class PushUtils {
    protected static final String TAG = "PushUtils";
    public Context context;
    public PushAgent mPushAgent;
    public static PushUtils mPushUtils = null;
    private CallbackReceiver callbackReceiver;


    public PushUtils(Context context) {
        this.context = context;
        mPushAgent = PushAgent.getInstance(context);
    }

    //单例模式
    public static PushUtils getInstance(Context context) {
        if (mPushUtils == null) {
            mPushUtils = new PushUtils(context);
        }
        return mPushUtils;
    }

    //添加广播接收
    private void addReceiver(Context context) {
        callbackReceiver = new CallbackReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyApplication.CALLBACK_RECEIVER_ACTION);
        context.registerReceiver(callbackReceiver, filter);
    }

    //广播
    class CallbackReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(MyApplication.CALLBACK_RECEIVER_ACTION)) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        updateStatus(context);
                    }
                });
            }
        }
    }

    //handler
    public Handler handler = new Handler();

    /**
     * 状态监听
     *
     * @param context
     */
    //updateStatus
    private void updateStatus(Context context) {
        String pkgName = context.getPackageName();
        String info = String.format("enabled:%s  isRegistered:%s  DeviceToken:%s " +
                        "SdkVersion:%s AppVersionCode:%s AppVersionName:%s",
                mPushAgent.isEnabled(), mPushAgent.isRegistered(),
                mPushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
                UmengMessageDeviceConfig.getAppVersionCode(context), UmengMessageDeviceConfig.getAppVersionName(context));
//        tvStatus.setText("应用包名："+pkgName+"\n"+info);
//
//        btnEnable.setImageResource(mPushAgent.isEnabled()?R.drawable.open_button:R.drawable.close_button);
//        btnEnable.setClickable(true);
        copyToClipBoard(context);

        Log.i(TAG, "updateStatus:" + String.format("enabled:%s  isRegistered:%s",
                mPushAgent.isEnabled(), mPushAgent.isRegistered()));
        Log.i(TAG, "=============================");
    }

    /**
     * 将友盟的token整到了宅铁板上
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void copyToClipBoard(Context context) {
        if (Build.VERSION.SDK_INT < 11)
            return;
        String deviceToken = mPushAgent.getRegistrationId();
        if (!TextUtils.isEmpty(deviceToken)) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(deviceToken);
            toast(context, "DeviceToken已经复制到剪贴板了");
        }
    }

    private Toast mToast;

    public void toast(Context context, String str) {
        if (mToast == null)
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        mToast.setText(str);
        mToast.show();
    }


    /**
     * 开启推送
     */
    public void enablePush() {
        mPushAgent.setDebugMode(true);
        mPushAgent.onAppStart();
        Log.e("token", mPushAgent.getRegistrationId() + "TOKEN");
        mPushAgent.enable(MyApplication.mRegisterCallback);
        addReceiver(context);
    }

    /**
     * 关闭推送
     */
    public void disablePush() {
        mPushAgent.disable(MyApplication.mUnregisterCallback);
    }

    /**
     * 设定推送标签（TAG）,给服务器端，进行分享使用。用于部分人的推送使用。
     *
     * @param tag 推送渠道。
     */
    public void setTag(String tag, Context context) {
        showLoading(context);
        new AddTagTask(tag).execute();
    }

    /**
     * 设定UserID（Alias）,给服务器端，进行分享使用
     * 结合用户的第三方登录方式，使用，例如QQ、微信、人人、新浪微博等
     *
     * @param alias     用户id。
     * @param aliasType 用户类型。
     */
    public void setAlias(String alias, String aliasType, Context context) {
        showLoading(context);
        new AddAliasTask(alias, aliasType).execute();
    }


    private ProgressDialog dialog;

    public void showLoading(Context context) {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Loading");
        }
        dialog.show();
    }

    class AddTagTask extends AsyncTask<Void, Void, String> {
        String tagString;
        String[] tags;

        public AddTagTask(String tag) {
            // TODO Auto-generated constructor stub
            tagString = tag;
            tags = tagString.split(",");
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                TagManager.Result result = mPushAgent.getTagManager().add(tags);
                Log.d(TAG, result.toString());
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Fail";
        }

        @Override
        protected void onPostExecute(String result) {
            updateInfo("Add Tag:\n" + result);
        }
    }

    class AddAliasTask extends AsyncTask<Void, Void, Boolean> {
        String alias;
        String aliasType;

        public AddAliasTask(String aliasString, String aliasTypeString) {
            // TODO Auto-generated constructor stub
            this.alias = aliasString;
            this.aliasType = aliasTypeString;
        }

        protected Boolean doInBackground(Void... params) {
            try {
                return mPushAgent.addAlias(alias, aliasType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (Boolean.TRUE.equals(result))
                Log.i(TAG, "alias was set successfully.");
            updateInfo("Add Alias:" + (result ? "Success" : "Fail"));
        }
    }

    public void updateInfo(String info) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

}
