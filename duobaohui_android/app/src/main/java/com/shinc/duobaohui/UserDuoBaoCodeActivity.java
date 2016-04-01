package com.shinc.duobaohui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinc.duobaohui.adapter.UserDuoBaoCodeAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.customview.imp.LoadMoreGridView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.UserDuoBaoCodeEvent;
import com.shinc.duobaohui.model.impl.UserDuoBaoCodeModelImpl;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/8.
 * 夺宝号码
 */
public class UserDuoBaoCodeActivity extends BaseActivity {
    private ImageView imgBack;
    private TextView name;
    private TextView num;
    private LoadMoreGridView gridView;
    private UserDuoBaoCodeAdapter adapter;
    private UserDuoBaoCodeModelImpl model;
    private int page = 1;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private String peroid_id;
    private View title;
    WaitLoadingUtils loadingUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duobao_code_layout);
        EventBus.getDefault().register(this);
        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();
        peroid_id = (String) getIntent().getExtras().get("period_id");
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
                model.getCodeData(peroid_id, page);
            }
        });

        gridView.setLoadMoreListener(new LoadMoreGridView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                model.getCodeData(peroid_id, page);
            }
        });
    }

    private void initModel() {
        imgBack = (ImageView) findViewById(R.id.login_back_img);
        model = new UserDuoBaoCodeModelImpl(UserDuoBaoCodeActivity.this);
        model.getCodeData(peroid_id, page);
    }

    private void initView() {
        name = (TextView) findViewById(R.id.duobao_code_layout_name);
        num = (TextView) findViewById(R.id.duobao_code_time);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_layout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        gridView = (LoadMoreGridView) findViewById(R.id.duobao_code_layout_gridview);
        adapter = new UserDuoBaoCodeAdapter(UserDuoBaoCodeActivity.this);
        gridView.setAdapter(adapter);
    }

    public void onEventMainThread(UserDuoBaoCodeEvent event) {
        loadingUtils.disable();
        if (event.getUserDuoBaoCodeBean() == null) {
            print("网络链接错误，请检查网络");
            noWeb();
        } else {
            if (event.getUserDuoBaoCodeBean().getCode().equals("1")) {
                if (page <= 1) {
                    if (adapter.getList().size() > 0) {
                        adapter.getList().clear();
                    }
                    adapter.setList(event.getUserDuoBaoCodeBean().getData());

                } else {

                    adapter.getList().addAll(event.getUserDuoBaoCodeBean().getData());

                }
            } else {
                gridView.setLoadComplete();
                page--;
            }
        }
        adapter.notifyDataSetChanged();
        ptrClassicFrameLayout.refreshComplete();
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
                model.getCodeData(peroid_id, page);
            }
        });
    }

}
