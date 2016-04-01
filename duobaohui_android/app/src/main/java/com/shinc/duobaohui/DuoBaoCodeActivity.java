package com.shinc.duobaohui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shinc.duobaohui.adapter.DuoBaoCodeAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.customview.imp.LoadMoreGridView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.DuoBaoCodeEvent;
import com.shinc.duobaohui.model.impl.DuoBaoCodeModelImpl;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/8.
 * 夺宝号
 */

public class DuoBaoCodeActivity extends BaseActivity {

    private ImageView imgBack;
    private LinearLayout layoutCode;
    private TextView name;
    private TextView num;
    private LoadMoreGridView gridView;
    private DuoBaoCodeAdapter adapter;
    private DuoBaoCodeModelImpl model;
    private int page = 1;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private WaitLoadingUtils loadingUtils;
    private boolean isRefresh = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duobao_code_layout);
        EventBus.getDefault().register(this);
        initView();
        initModel();
        initListener();
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
                gridView.setLoadReset();
                isRefresh = true;
                model.getCodeData(getIntent().getStringExtra("sh_activity_period_id"), getIntent().getStringExtra("sh_period_user_id"), page);
            }
        });

        gridView.setLoadMoreListener(new LoadMoreGridView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                isRefresh = true;
                model.getCodeData(getIntent().getStringExtra("sh_activity_period_id"), getIntent().getStringExtra("sh_period_user_id"), page);
            }
        });
    }

    private void initModel() {
        imgBack = (ImageView) findViewById(R.id.login_back_img);
        model = new DuoBaoCodeModelImpl(DuoBaoCodeActivity.this, getIntent().getStringExtra("user_id"));
        page = 1;

        model.getCodeData(getIntent().getStringExtra("sh_activity_period_id"), getIntent().getStringExtra("sh_period_user_id"), page);
        name.setText(getIntent().getStringExtra("goodName"));
        num.setText(getIntent().getStringExtra("goodNumber"));
    }

    private void initView() {
        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();

        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_layout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        gridView = (LoadMoreGridView) findViewById(R.id.duobao_code_layout_gridview);
        layoutCode = (LinearLayout) getLayoutInflater().inflate(R.layout.duobao_code_detail_layout, null);
        name = (TextView) layoutCode.findViewById(R.id.duobao_code_layout_name);
        num = (TextView) layoutCode.findViewById(R.id.duobao_code_time);
        gridView.addHeaderView(layoutCode);

        adapter = new DuoBaoCodeAdapter(DuoBaoCodeActivity.this);
        gridView.setAdapter(adapter);

    }


    public void onEventMainThread(DuoBaoCodeEvent event) {
        loadingUtils.disable();
        if (event.getBaoCodeBean() == null) {
            print("网络链接错误，请检查网络");
            if (page == 1) {
                ptrClassicFrameLayout.refreshComplete();
                noWeb();
            }

        } else {
            if (event.getBaoCodeBean().getCode().equals("1")) {
                if (page == 1) {
                    if (adapter.getList().size() > 0) {
                        adapter.getList().clear();
                    }
                    adapter.setList(event.getBaoCodeBean().getData());
                } else {

                    adapter.getList().addAll(event.getBaoCodeBean().getData());
                }
            } else {
                ptrClassicFrameLayout.refreshComplete();
                gridView.setLoadComplete();
                page--;
            }
        }
        adapter.notifyDataSetChanged();
        if (isRefresh) {
            ptrClassicFrameLayout.refreshComplete();
            isRefresh = false;
        }
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
                model.getCodeData(getIntent().getStringExtra("sh_activity_period_id"), getIntent().getStringExtra("sh_period_user_id"), page);
            }
        });
    }
}
