package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.PreferencesCookieStore;
import com.shihe.shincdatastatisticssdk.ShiNcAgent;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.UmSundryUpdateUtils;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/9/29.
 * 添加收货地址
 */
public class SettingActivity extends BaseActivity {

    private RelativeLayout backRl;
    private RelativeLayout calculateLunkyNum;
    private RelativeLayout checkUpdate;
    private RelativeLayout aboutApp;
    private RelativeLayout facebook;
    private Activity mActivity;
    private TextView loginOut;
    private SharedPreferencesUtils spUtils;
    private TextView tVisition;
    private RelativeLayout loginOutLayout;

    private UmSundryUpdateUtils uds;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        setContentView(R.layout.activity_set);
        getWindow().setBackgroundDrawable(null);
        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
        uds = new UmSundryUpdateUtils(SettingActivity.this);

        try {
            initView();
            initLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initListener();
    }

    /**
     * 初始化页面，判断用户是否登录，如果登录显示退出Layout,如果已登录不显示推出Layout；
     */
    private void initLayout() {

        if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
            //如果为空，证明用户未登陆，不显示退出按钮；
            loginOutLayout.setVisibility(View.GONE);
            loginOut.setEnabled(false);
        } else {
            loginOutLayout.setVisibility(View.VISIBLE);
            loginOut.setEnabled(true);
        }
    }

    private void initView() throws Exception {
        getWindow().setBackgroundDrawable(null);
        loadingDialog = new LoadingDialog(mActivity, R.style.dialog);
        backRl = (RelativeLayout) findViewById(R.id.back_rl);
        calculateLunkyNum = (RelativeLayout) findViewById(R.id.how_calculate_luck_number);
        checkUpdate = (RelativeLayout) findViewById(R.id.check_update);
        aboutApp = (RelativeLayout) findViewById(R.id.about_app);
        loginOut = (TextView) findViewById(R.id.login_out);
        tVisition = (TextView) findViewById(R.id.visition);
        facebook = (RelativeLayout) findViewById(R.id.how_set_faceback);
        tVisition.setText(getVersionName());
        loginOutLayout = (RelativeLayout) findViewById(R.id.login_out_layout);
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return "V" + version;
    }


    private void initListener() {
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        calculateLunkyNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, CalculateDetailActivity.class);
                startActivity(intent);
            }
        });
        checkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用友盟手动更新，检查更新；
                loadingDialog.show();
                checkVersion();
            }
        });

        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关于该app;
                Intent intent = new Intent(mActivity, AbortActivity.class);
                startActivity(intent);
            }
        });

        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出登陆；
                //清除本地数据；
                final PushAgent mPushAgent = PushAgent.getInstance(mActivity);
                final String userId = spUtils.get(Constant.SP_USER_ID, "");
                if (!TextUtils.isEmpty(userId)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mPushAgent.removeAlias(userId, "userId");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                PreferencesCookieStore cookieStore = new PreferencesCookieStore(mActivity);
                cookieStore.clear();
                spUtils.deleteAll();
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
                //todo 世和数据统计，用户解绑；
                ShiNcAgent.deleteUerIdentifier(mActivity);
                finish();
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(spUtils.get(Constant.SP_USER_ID, ""))) {
                    //意见反馈，只有登陆用户才能进行操作；
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, FaceBookActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void checkVersion() {

        uds.autoDialog(false);
        uds.isListener(new UmengUpdateListener() {

            @Override
            public void onUpdateReturned(int arg0, UpdateResponse arg1) {
                loadingDialog.hideLoading();
                switch (arg0) {
                    case UpdateStatus.Yes: // has update
                        UmengUpdateAgent.showUpdateDialog(SettingActivity.this,
                                arg1);
                        break;
                    case UpdateStatus.No: // has no update
                        Toast.makeText(SettingActivity.this, "已经是最新的版本",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        Toast.makeText(SettingActivity.this, "没有wifi连接， 只在wifi下更新",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.Timeout: // time out
                        Toast.makeText(SettingActivity.this, "网络连接超时",
                                Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        UmengUpdateAgent.update(this);
    }
}
