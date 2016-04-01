package com.shinc.duobaohui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.shinc.duobaohui.base.BaseActivity;

/**
 * 修改手机号码
 * Created by yangtianhe on 15/10/9.
 */
public class PerosnalChangePhoneNumberActivity extends BaseActivity {
    private ImageView backImg;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        setContentView(R.layout.perosnal_changephonenumber_layout);
        initView();
        initListener();
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.person_info_layout_img_back);

    }

    private void initListener() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
