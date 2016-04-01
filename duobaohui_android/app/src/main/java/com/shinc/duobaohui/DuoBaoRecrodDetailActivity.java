package com.shinc.duobaohui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shinc.duobaohui.adapter.DuoBaoRecrodDetailAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.DuoBaoDetailEvent;
import com.shinc.duobaohui.model.impl.DuoBaoDdetailModelImpl;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/6.
 * 夺宝详情
 */
public class DuoBaoRecrodDetailActivity extends BaseActivity {

    private Activity mActivity;
    private ImageView imgBack;
    private CustomTextView produckName;
    private CustomTextView number;
    private LoadMoreListView reListView;
    private DuoBaoRecrodDetailAdapter adapter;
    private DuoBaoDdetailModelImpl duoBaoDdetailModel;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private WaitLoadingUtils loadingUtils;
    private int page;
    private LinearLayout infoLinearLayoutHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duobao_detail_layout);
        mActivity = DuoBaoRecrodDetailActivity.this;
        EventBus.getDefault().register(this);
        initView();
        page = 1;
        duoBaoDdetailModel = new DuoBaoDdetailModelImpl(mActivity, getIntent().getStringExtra("user_id"));
        initModel();
        initListener();
    }

    private void initModel() {
        duoBaoDdetailModel.getLotteryDate(getIntent().getStringExtra("sh_activity_period_id"), page);
        produckName.setText(getIntent().getStringExtra("Goods_name"));
        number.setText(getIntent().getStringExtra("Times"));
        adapter.setName(produckName.getText().toString());
        adapter.setNum(number.getText().toString());
    }

    public void onEventMainThread(DuoBaoDetailEvent duoBaoDetailEvent) {
        loadingUtils.disable();
        if (duoBaoDetailEvent.getLotteryTimesListbyPeriodidBean() == null) {
            if (page == 1) {
                noWeb();
                ptrClassicFrameLayout.refreshComplete();
            }
        } else {
            if (duoBaoDetailEvent.getLotteryTimesListbyPeriodidBean().getCode().equals("1")) {
                if (page == 1) {
                    if (adapter.getDataList().size() > 0) {
                        adapter.getDataList().clear();
                    }
                    adapter.setDataList(duoBaoDetailEvent.getLotteryTimesListbyPeriodidBean().getData());
                } else {
                    if (duoBaoDetailEvent.getLotteryTimesListbyPeriodidBean().getData().size() <= 0) {
                        page--;
                    } else {
                        adapter.getDataList().addAll(duoBaoDetailEvent.getLotteryTimesListbyPeriodidBean().getData());
                    }
                }
            } else {
                ptrClassicFrameLayout.refreshComplete();
                reListView.setLoadComplete();
            }
        }
        adapter.notifyDataSetChanged();
        ptrClassicFrameLayout.refreshComplete();
    }

    private void initView() {
        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();
        imgBack = (ImageView) findViewById(R.id.duobao_detail_layout_imgback);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_layout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        reListView = (LoadMoreListView) findViewById(R.id.duobao_detail_layout_reListview);
        infoLinearLayoutHeader = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.duobao_detail_head_layout, null);
        produckName = (CustomTextView) infoLinearLayoutHeader.findViewById(R.id.duobao_detail_layout_goodsName);
        number = (CustomTextView) infoLinearLayoutHeader.findViewById(R.id.duobao_detail_layout_duobaoNumber);
        reListView.addHeaderView(infoLinearLayoutHeader);
        adapter = new DuoBaoRecrodDetailAdapter(mActivity);
        reListView.setAdapter(adapter);
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
                reListView.setLoadReset();
                duoBaoDdetailModel.getLotteryDate(getIntent().getStringExtra("sh_activity_period_id"), page);
            }
        });
        reListView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                duoBaoDdetailModel.getLotteryDate(getIntent().getStringExtra("sh_activity_period_id"), page);
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
                initModel();
            }
        });
    }
}
