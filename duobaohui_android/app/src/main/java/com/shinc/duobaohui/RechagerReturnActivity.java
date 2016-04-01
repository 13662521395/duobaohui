package com.shinc.duobaohui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.customview.imp.CustomTextView;

/**
 * Created by liugaopo on 15/11/9.
 * 充值结果页
 */

public class RechagerReturnActivity extends BaseActivity {

    private BaseActivity mActivity;
    private ImageView imgBack;
    private CustomTextView reValue;
    private CustomTextView returnHome;
    private CustomTextView reRecrod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = RechagerReturnActivity.this;
        setContentView(R.layout.recharge_return_layout);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        reValue.setText("恭喜您，获得" + getIntent().getStringExtra("num") + "个夺宝币");
    }

    private void initListener() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reRecrod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivity, RechargeRecrodActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.rechager_return_layout_imgBack);
        reValue = (CustomTextView) findViewById(R.id.rechager_return_layout_wineReturnWord);
        returnHome = (CustomTextView) findViewById(R.id.rechader_return_layout_returnHomePage);
        reRecrod = (CustomTextView) findViewById(R.id.rechader_return_layout_returnRechagerRecrod);

    }
}
