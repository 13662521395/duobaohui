package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.adapter.ComputionalDetailsAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.customview.imp.ReListView;
import com.shinc.duobaohui.event.ComputionalDetaillsEvent;
import com.shinc.duobaohui.model.ComputionalDetailsModelInterface;
import com.shinc.duobaohui.model.impl.ComputionDetailsModelImpl;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/8.
 * 计算详情
 */
public class ComputionalDetailsActivity extends BaseActivity {

    private Activity mActivity;
    private ImageView imgBack;
    private RelativeLayout openLayout;//展开
    private LinearLayout linearLayout;//夺宝账户 展开或隐藏
    private CustomTextView tvOpen;
    private ImageView imgOpen;

    private CustomTextView tvACode;//A 值
    private CustomTextView LunckyCode;//彩票期数
    private CustomTextView queayCode;//查询
    private CustomTextView lunck_code;
    private ReListView reListView;//夺宝账户
    private CustomTextView lotter_results;

    private ComputionalDetailsModelInterface computionalDetailsModelInterface;
    private ComputionalDetailsAdapter detailsAdapter;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computational_details_layout);
        mActivity = ComputionalDetailsActivity.this;
        EventBus.getDefault().register(this);
        initView();
        initModel();
        initListener();
    }

    private void initModel() {
        computionalDetailsModelInterface = new ComputionDetailsModelImpl(mActivity);
//        getIntent().getSerializableExtra("")
        computionalDetailsModelInterface.getData(getIntent().getStringExtra("id"));
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.back);
        tvACode = (CustomTextView) findViewById(R.id.computional_details_layout_tvACode);
        openLayout = (RelativeLayout) findViewById(R.id.computional_details_layout_open_layout);
        linearLayout = (LinearLayout) findViewById(R.id.computational_details_layout_show_data);
        reListView = (ReListView) findViewById(R.id.computional_details_layout_listview);
        tvOpen = (CustomTextView) findViewById(R.id.computional_details_layout_open_show_data);
        imgOpen = (ImageView) findViewById(R.id.computional_details_layout_open_icon);
        LunckyCode = (CustomTextView) findViewById(R.id.computional_details_layout_period);
        queayCode = (CustomTextView) findViewById(R.id.computional_details_layout_query);
        lunck_code = (CustomTextView) findViewById(R.id.computional_details_layout_lucky);
        lotter_results = (CustomTextView) findViewById(R.id.computional_details_layout_lotter_results);
        detailsAdapter = new ComputionalDetailsAdapter(mActivity);
        reListView.setAdapter(detailsAdapter);
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        openLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOpen) {
                    linearLayout.setVisibility(View.GONE);
                    tvOpen.setText("展开");
                    imgOpen.setImageResource(R.drawable.icon_details_close);
                    isOpen = false;
                } else {

                    linearLayout.setVisibility(View.VISIBLE);
                    tvOpen.setText("收起");

                    imgOpen.setImageResource(R.drawable.icon_details_open);
                    isOpen = true;
                }
            }
        });

        queayCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到彩票接口；
                if (lotter_results.getText().toString().equals("00000")) {
                    //了解详情
                } else {

                    Intent intent = new Intent(mActivity, UserProtocolActivity.class);
                    intent.putExtra("flag", "1");
                    intent.putExtra("url", ConstantApi.SHISHICAI);
                    startActivity(intent);
                }
            }
        });
    }

    public void onEventMainThread(ComputionalDetaillsEvent event) {
        if (event.getComputionalDetailsBean() == null) {
            print("网络链接错误，请检查");
        } else {
            if (event.getComputionalDetailsBean().getCode().equals("1")) {
                if (event.getComputionalDetailsBean().getData().getCount_detail_res().size() > 0) {
                    tvACode.setText(event.getComputionalDetailsBean().getData().getCount_detail_res().get(0).getA_code());
                    if (event.getComputionalDetailsBean().getData().getCount_detail_res().get(0).getShow_code_flag().equals("0")) {
                        lunck_code.setText("请等待开奖");
                        lotter_results.setText("正在等待");
                    } else {
                        lunck_code.setText(event.getComputionalDetailsBean().getData().getCount_detail_res().get(0).getLuck_code());
                        if (event.getComputionalDetailsBean().getData().getCount_detail_res().get(0).getLottery_code().equals("00000")) {
                            lotter_results.setText(event.getComputionalDetailsBean().getData().getCount_detail_res().get(0).getLottery_code());
                            LunckyCode.setVisibility(View.GONE);
                            queayCode.setVisibility(View.GONE);
                            queayCode.setText("了解详情");
                        } else {
                            lotter_results.setText(event.getComputionalDetailsBean().getData().getCount_detail_res().get(0).getLottery_code());
                            LunckyCode.setVisibility(View.VISIBLE);
                            queayCode.setVisibility(View.VISIBLE);
                            queayCode.setText("开奖查询");
                        }
                    }
                    LunckyCode.setText("(第" + event.getComputionalDetailsBean().getData().getCount_detail_res().get(0).getLottery_period() + ")");
                    detailsAdapter.setCountDetailResBeans(event.getComputionalDetailsBean().getData().getLottery_code_list());
                    detailsAdapter.notifyDataSetChanged();
                } else {
                    lunck_code.setText("请等待");
                }
            } else {
                lunck_code.setText("请等待");
                lotter_results.setText("00000");
                LunckyCode.setVisibility(View.GONE);
                queayCode.setVisibility(View.GONE);
                queayCode.setText("了解详情");
                print(event.getComputionalDetailsBean().getMsg());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        detailsAdapter = null;
        computionalDetailsModelInterface = null;
    }
}
