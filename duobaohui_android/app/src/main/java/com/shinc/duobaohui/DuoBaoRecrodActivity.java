package com.shinc.duobaohui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.adapter.DuoBaoRecordAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.DuoBaoRecrodEvent;
import com.shinc.duobaohui.model.DuoBaoRecrodModelInterface;
import com.shinc.duobaohui.model.impl.DuoBaoRecrodModelImpl;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * 夺宝记录
 */

public class DuoBaoRecrodActivity extends BaseActivity {

    private Activity mActivity;
    private ImageView imgBack;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private LoadMoreListView listView;
    private DuoBaoRecordAdapter duoBaoRecordAdapter;
    private DuoBaoRecrodModelInterface recrodModel;
    private int page;

    WaitLoadingUtils loadingUtils;

    private RelativeLayout noDataLayout;//无数据时加载得页面
    private ImageView imgNodata;
    private TextView tvNodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duobao_record_layout);
        EventBus.getDefault().register(this);
        mActivity = DuoBaoRecrodActivity.this;
        page = 1;
        initView();
        initModel();
        initListener();
    }

    private void initModel() {
        recrodModel = new DuoBaoRecrodModelImpl(mActivity);
        recrodModel.getDuoBaoDate(page);
    }

    private void initView() {
        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();

        imgBack = (ImageView) findViewById(R.id.duobao_record_layout_imgback);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_layout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        listView = (LoadMoreListView) findViewById(R.id.duobao_record_layout_listview);
        duoBaoRecordAdapter = new DuoBaoRecordAdapter(mActivity);
        listView.setAdapter(duoBaoRecordAdapter);
        noDataLayout = (RelativeLayout) findViewById(R.id.inclue_no_data);
        noDataLayout.setVisibility(View.GONE);
        imgNodata = (ImageView) findViewById(R.id.no_date_layout_icon);
        tvNodata = (TextView) findViewById(R.id.no_date_layout_tv);
        tvNodata.setText("暂时还没有夺宝记录哦");
        imgNodata.setImageResource(R.drawable.icon_norecord_1);
    }

    public void onEventMainThread(DuoBaoRecrodEvent recrodEvent) {
        loadingUtils.disable();
        if (recrodEvent.getDuobaoBean() == null) {
            if (page == 1) {
                noDataLayout.setVisibility(View.GONE);
                noWeb();
            } else {
                page--;
            }
        } else {
            if (recrodEvent.getDuobaoBean().getCode().equals("1")) {
                noDataLayout.setVisibility(View.GONE);
                if (page == 1) {
                    if (duoBaoRecordAdapter.getCrodChildBeans() != null && duoBaoRecordAdapter.getCrodChildBeans().size() > 0) {
                        duoBaoRecordAdapter.getCrodChildBeans().clear();
                    }
                    duoBaoRecordAdapter.setCrodChildBeans(recrodEvent.getDuobaoBean().getData());
                } else {
                    duoBaoRecordAdapter.getCrodChildBeans().addAll(recrodEvent.getDuobaoBean().getData());
                    duoBaoRecordAdapter.notifyDataSetChanged();
                }
            } else {
                if (duoBaoRecordAdapter.getCrodChildBeans().size() > 0) {
                    page--;

                    ptrClassicFrameLayout.refreshComplete();
                    listView.setLoadComplete();

                    noDataLayout.setVisibility(View.GONE);

                } else {
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }
        }
        ptrClassicFrameLayout.refreshComplete();
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                recrodModel.getDuoBaoDate(page);
            }
        });

        listView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                recrodModel.getDuoBaoDate(page);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void noWeb() {
        loadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                loadingUtils.haveWeb();
                loadingUtils.show();
                recrodModel.getDuoBaoDate(page);
            }
        });
    }
}
