package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.imp.CustomTextView;

/**
 * Created by liugaopo on 15/9/29.
 * 计算详情；
 */
public class CalculateDetailActivity extends BaseActivity {

    private ImageView backImg;
    private Activity mActivity;
    //  private SharedPreferencesUtils spUtils;
    private RelativeLayout showData;
    private boolean isOpen = false;
    private LinearLayout dataLayout;
    private CustomTextView showDataTv;
    private ImageView openIcon;
    private TextView searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = CalculateDetailActivity.this;
        setContentView(R.layout.activity_calculate_detail);
        getWindow().setBackgroundDrawable(null);

        //    spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
        initView();
        initListener();
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back);
        showData = (RelativeLayout) findViewById(R.id.open_layout);
        dataLayout = (LinearLayout) findViewById(R.id.show_data);
        showDataTv = (CustomTextView) findViewById(R.id.open_show_data);
        openIcon = (ImageView) findViewById(R.id.open_icon);
        searchResult = (TextView) findViewById(R.id.search_result);
    }

    private void initListener() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 展开、隐藏的操作；
                if (isOpen) {
                    dataLayout.setVisibility(View.GONE);
                    showDataTv.setText("展开");

                    openIcon.setImageResource(R.drawable.icon_details_close);
                    isOpen = false;
                } else {
                    dataLayout.setVisibility(View.VISIBLE);
                    openIcon.setImageResource(R.drawable.icon_details_open);
                    showDataTv.setText("收起");

                    isOpen = true;
                }
            }
        });
        searchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到时时彩接口；
                Intent intent = new Intent(mActivity, UserProtocolActivity.class);
                intent.putExtra("flag", "1");
                intent.putExtra("url", ConstantApi.SHISHICAI);
                startActivity(intent);
            }
        });

    }
}
