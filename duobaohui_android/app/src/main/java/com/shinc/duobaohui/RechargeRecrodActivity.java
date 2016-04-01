package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.adapter.RechargeRecrodtAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.RechrageRecrodEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.model.impl.RechargeRecrodModelImpl;
import com.shinc.duobaohui.utils.CodeVerifyUtils;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/10.
 * 充值记录
 */
public class RechargeRecrodActivity extends BaseActivity {

    private Activity mActivity;
    private ImageView imgBack;
    private LoadMoreListView listView;
    private RechargeRecrodtAdapter recrodtAdapter;
    private RechargeRecrodModelImpl rechargeRecrodModel;
    private PtrClassicFrameLayout ptrFrameLayout;
    private CustomTextView customTextView;
    private WaitLoadingUtils waitLoadingUtils;
    private int page;
    private RelativeLayout noDataLayout;//无数据时加载得页面
    private ImageView imgNodata;
    private TextView tvNodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_recrod_layout);
        getWindow().setBackgroundDrawable(null);

        page = 1;
        waitLoadingUtils = new WaitLoadingUtils(this);
        waitLoadingUtils.show();
        mActivity = RechargeRecrodActivity.this;
        EventBus.getDefault().register(this);
        initView();
        initModel();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.recharge) {
            MyApplication.recharge = false;
            page = 1;
            rechargeRecrodModel.getDate(page);
        }
    }

    private void initListener() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                listView.setLoadReset();
                rechargeRecrodModel.getDate(page);
            }
        });

        listView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                rechargeRecrodModel.getDate(page);
            }
        });

    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.recharge_recrod_layout_back);
        ptrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_layout);
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        listView = (LoadMoreListView) findViewById(R.id.recharge_recrod_layout_listview);
        recrodtAdapter = new RechargeRecrodtAdapter(mActivity);
        listView.setAdapter(recrodtAdapter);
        customTextView = (CustomTextView) findViewById(R.id.recharge_recrod_layout_recharge);

        noDataLayout = (RelativeLayout) findViewById(R.id.recharge_layout_layout_noData);
        noDataLayout.setVisibility(View.GONE);
        imgNodata = (ImageView) findViewById(R.id.no_date_layout_icon);
        tvNodata = (TextView) findViewById(R.id.no_date_layout_tv);
        tvNodata.setText("暂时还没有充值记录哦");
        imgNodata.setImageResource(R.drawable.icon_norecord_4);
    }

    private void initModel() {
        rechargeRecrodModel = new RechargeRecrodModelImpl(mActivity);
        page = 1;
        rechargeRecrodModel.getDate(page);
    }

    public void onEventMainThread(RechrageRecrodEvent rechrageRecrodEvent) {
        waitLoadingUtils.disable();
        if (rechrageRecrodEvent.getBean() == null) {
            if (page == 1) {
                noWeb();
                noDataLayout.setVisibility(View.GONE);
            }
        } else {
            if (CodeVerifyUtils.verifyCode(rechrageRecrodEvent.getBean().getCode())) {
                CodeVerifyUtils.verifySession(mActivity);
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
                finish();
            } else {

                if (rechrageRecrodEvent.getBean().getCode().equals("1")) {
                    noDataLayout.setVisibility(View.GONE);
                    if (page == 1) {
                        if (recrodtAdapter.getDataList().size() > 0) {
                            recrodtAdapter.getDataList().clear();
                        }
                        recrodtAdapter.setDataList(rechrageRecrodEvent.getBean().getData());
                    } else {
                        recrodtAdapter.getDataList().addAll(rechrageRecrodEvent.getBean().getData());
                    }
                } else {
                    if (recrodtAdapter.getDataList().size() > 0) {
                        page--;
                        noDataLayout.setVisibility(View.GONE);
                        ptrFrameLayout.refreshComplete();
                        listView.setLoadComplete();
                    } else {
                        noDataLayout.setVisibility(View.VISIBLE);
                        ptrFrameLayout.refreshComplete();
                    }
                }
            }
        }

        customTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivity, RechargeActivity.class);
                startActivity(intent);
            }
        });
        recrodtAdapter.notifyDataSetChanged();
        ptrFrameLayout.refreshComplete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public String getActivityName() {
        return super.getActivityName();
    }

    private void noWeb() {
        waitLoadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                waitLoadingUtils.haveWeb();
                waitLoadingUtils.show();
                page = 1;
                rechargeRecrodModel.getDate(page);
            }
        });
    }
}
