package com.shinc.duobaohui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.adapter.WinRecrodAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.event.WinListEvent;
import com.shinc.duobaohui.model.impl.WinListModel;
import com.shinc.duobaohui.utils.CodeVerifyUtils;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/11/18.
 * 中奖记录-V1.2
 */
public class WinRecrodActivity extends BaseActivity {

    private BaseActivity mActivity;
    private ImageView imgBack;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private LoadMoreListView listView;
    private WinRecrodAdapter adapter;
    private WinListModel model;

    private WaitLoadingUtils waitLoadingUtils;
    private RelativeLayout noDataLayout;//无数据时加载得页面
    private ImageView imgNodata;
    private TextView tvNodata;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_recrod_layout);
        waitLoadingUtils = new WaitLoadingUtils(this);
        waitLoadingUtils.show();
        this.mActivity = WinRecrodActivity.this;
        EventBus.getDefault().register(this);
        initView();
        initModel();
        initListener();
    }

    private void initModel() {
        model = new WinListModel(mActivity);
        page = 1;
        model.getWinData(page);
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.win_recrod_layout_back);

        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_layout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        listView = (LoadMoreListView) findViewById(R.id.win_recrod_layout_listview);
        adapter = new WinRecrodAdapter(mActivity);
        listView.setAdapter(adapter);
        noDataLayout = (RelativeLayout) findViewById(R.id.recharge_layout_layout_noData);
        noDataLayout.setVisibility(View.GONE);
        imgNodata = (ImageView) findViewById(R.id.no_date_layout_icon);
        tvNodata = (TextView) findViewById(R.id.no_date_layout_tv);
        tvNodata.setText("暂时还没有中奖记录哦");
        imgNodata.setImageResource(R.drawable.icon_norecord_2);
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.setClass(mActivity, WinDetailActivity.class);
                intent.putExtra("period_id", adapter.getWinListBeans().get(position).getSh_activity_period_id());
                startActivity(intent);
            }
        });

        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                listView.setLoadReset();
                model.getWinData(page);
            }
        });

        listView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                model.getWinData(page);
            }
        });
    }

    public void onEventMainThread(WinListEvent event) {

        if (event.getBean() == null) {
            if (page == 1) {
                noWeb();
                noDataLayout.setVisibility(View.GONE);
                ptrClassicFrameLayout.refreshComplete();
            }
        } else {
            if (CodeVerifyUtils.verifyCode(event.getBean().getCode())) {
                CodeVerifyUtils.verifySession(mActivity);
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
                finish();
            } else {

                if (event.getBean().getCode().equals("1")) {
                    if (event.getBean().getData() != null) {
                        noDataLayout.setVisibility(View.GONE);
                        if (page == 1) {
                            if (adapter.getWinListBeans().size() > 0) {
                                adapter.getWinListBeans().clear();
                            }
                            adapter.setWinListBeans(event.getBean().getData());
                        } else {
                            adapter.getWinListBeans().addAll(event.getBean().getData());
                        }
                    } else {
                        if (adapter.getWinListBeans().size() > 0) {
                            page--;
                            listView.setLoadComplete();
                            noDataLayout.setVisibility(View.GONE);
                            ptrClassicFrameLayout.refreshComplete();
                        } else {
                            noDataLayout.setVisibility(View.VISIBLE);
                            ptrClassicFrameLayout.refreshComplete();
                        }
                    }
                } else {
                    if (event.getBean().getCode().equals("0")) {
                        noDataLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
        ptrClassicFrameLayout.refreshComplete();
        waitLoadingUtils.disable();
    }


    /**
     * 本页的兑奖信息
     */
    public void onEventMainThread(String event) {
        if (event.equals("winner")) {
            model.getWinData(page);
        }
    }

    private void noWeb() {
        waitLoadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                waitLoadingUtils.haveWeb();
                waitLoadingUtils.show();
                page = 1;
                model.getWinData(page);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
