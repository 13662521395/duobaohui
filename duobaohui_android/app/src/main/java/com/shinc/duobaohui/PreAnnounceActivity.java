package com.shinc.duobaohui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.adapter.PreAnnounceListAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.PreAnnounceBean;
import com.shinc.duobaohui.bean.PreAnnounceListBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.HttpPreAnnounceListEvent;
import com.shinc.duobaohui.model.PreAnnounceInterface;
import com.shinc.duobaohui.model.impl.PreAnnounceModelImpl;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * 名称：PreAnnounceActivity 往期揭晓
 * 作者：zhaopl 时间: 15/10/10.
 * 实现的主要功能：
 */
public class PreAnnounceActivity extends BaseActivity {

    private Activity mActivity;

    private ImageView imgBack;

    private LoadMoreListView preAnnounceListView;

    private PtrClassicFrameLayout ptrFrameLayout;

    private PreAnnounceInterface preAnnounceImpl;

    private int page = 1;

    private String activeId;

    private boolean isRefresh = false;

    private View header;

    private WaitLoadingUtils loadingUtils;

    private PreAnnounceListAdapter adapter;

    private ArrayList<PreAnnounceBean> list;

    private RelativeLayout noDataLayout;//无数据时加载得页面
    private ImageView imgNodata;
    private CustomTextView tvNodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pre_announce);
        this.mActivity = this;
        list = new ArrayList();
        EventBus.getDefault().register(this);
        if (getIntent() != null || getIntent().getExtras() != null) {
            activeId = getIntent().getStringExtra("ACTIVEID");
        }
        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();

        initView();

        initData();

        initEvent();
    }

    private void initView() {
        getWindow().setBackgroundDrawable(null);
        imgBack = (ImageView) findViewById(R.id.back);
        ptrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_layout);
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        preAnnounceListView = (LoadMoreListView) findViewById(R.id.pre_announce_list_view);
        noDataLayout = (RelativeLayout) findViewById(R.id.pre_announce_noData);
        noDataLayout.setVisibility(View.GONE);
        imgNodata = (ImageView) findViewById(R.id.no_date_layout_icon);
        tvNodata = (CustomTextView) findViewById(R.id.no_date_layout_tv);
        tvNodata.setText("暂时还没有往期揭晓录哦");
        imgNodata.setImageResource(R.drawable.icon_norecord_6);
    }

    private void initData() {

        preAnnounceImpl = new PreAnnounceModelImpl(this);
        preAnnounceImpl.getListData(activeId, page + "", "20");
        adapter = new PreAnnounceListAdapter(mActivity, list);
        preAnnounceListView.setAdapter(adapter);
    }

    private void initEvent() {

        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                isRefresh = true;
                preAnnounceListView.setLoadReset();
                preAnnounceImpl.getListData(activeId, page + "", "20");
            }
        });

        preAnnounceListView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                isRefresh = true;
                preAnnounceImpl.getListData(activeId, page + "", "20", list.get(list.size() - 1).getPeriod_number());
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 关闭该页面；
                finish();
            }
        });


    }


    /**
     * 接收收到的请求数据；
     *
     * @param httpPreAnnounceListEvent
     */
    public void onEventMainThread(HttpPreAnnounceListEvent httpPreAnnounceListEvent) {


        PreAnnounceListBean preAnnounceListBean = httpPreAnnounceListEvent.getPreAnnounceListBean();
        if (preAnnounceListBean == null) {

        } else {

            switch (preAnnounceListBean.getCode()) {
                case "1":
                    ArrayList<PreAnnounceBean> data = preAnnounceListBean.getData();
                    if (data != null) {
                        if (page == 1) {
                            list.clear();
                            list.addAll(data);
                            noDataLayout.setVisibility(View.GONE);
                        } else {
                            list.addAll(preAnnounceListBean.getData());
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        if (page == 1) {
                            noDataLayout.setVisibility(View.VISIBLE);
                        } else {
//                            preAnnounceListView.setLoadComplete();
                            page--;
                        }
                    }

                    break;
                case "20302":
                    preAnnounceListView.setLoadComplete();
                    break;
            }

        }

        if (isRefresh) {
            ptrFrameLayout.refreshComplete();
            isRefresh = false;
        }
        loadingUtils.disable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
