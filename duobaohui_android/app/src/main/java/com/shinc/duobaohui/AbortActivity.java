package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinc.duobaohui.base.BaseActivity;

/**
 * Created by zpl on 15/9/29.
 * 关于页面；(给服务协议添加下划线)
 */
public class AbortActivity extends BaseActivity {

    private ImageView backImg;
    private Activity mActivity;
    private TextView Protocal;
    private TextView versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        setContentView(R.layout.activity_about);
        getWindow().setBackgroundDrawable(null);
        initView();
        initListener();
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back);
        Protocal = (TextView) findViewById(R.id.Protocal);
        versionCode = (TextView) findViewById(R.id.versioncode);
        versionCode.setText(getVersion());
    }

    private void initListener() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Protocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, UserProtocolActivity.class);
                startActivity(intent);
            }
        });

    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "v" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "v++";
        }
    }
}
