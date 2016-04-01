package com.shinc.duobaohui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.MainActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.adapter.UserWinRecrodAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.BaseFragment;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.UserWinRecrodEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.model.impl.UserWinRecrodModel;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/11/17.
 * TA的主页－中奖的纪录
 */
public class UseWinRecrodFragment extends BaseFragment {

    private BaseActivity fragmentActivity;

    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private LoadMoreListView listView;
    private UserWinRecrodAdapter adapter;
    private UserWinRecrodModel userWinRecrodModel;
    private int page;
    private WaitLoadingUtils waitLoadingUtils;
    private RelativeLayout layoutNoData;
    private TextView noDataBtn;
    private ImageView imgNoData;
    private TextView tvNoData;
    private String user_id;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.user_frag_layout, null);
        fragmentActivity = (BaseActivity) getActivity();
        user_id = getArguments().getString("userId");
        waitLoadingUtils = new WaitLoadingUtils(inflate);
        waitLoadingUtils.show();
        EventBus.getDefault().register(this);
        initView(inflate);
        initModel();
        initListener();
        return inflate;
    }

    private void initListener() {

        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                page = 1;
                listView.setLoadReset();
                userWinRecrodModel.getWinRecrodData(page);
            }
        });

        listView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                userWinRecrodModel.getWinRecrodData(page);
            }
        });

        noDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 去首页；

                Intent intent = new Intent(fragmentActivity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                EventBus.getDefault().post(new UtilsEvent("main"));
            }
        });
    }

    private void initModel() {
        userWinRecrodModel = new UserWinRecrodModel(fragmentActivity, user_id);
        page = 1;
        userWinRecrodModel.getWinRecrodData(page);
    }

    private void initView(View inflate) {

        ptrClassicFrameLayout = (PtrClassicFrameLayout) inflate.findViewById(R.id.ptr_frame_layout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        listView = (LoadMoreListView) inflate.findViewById(R.id.user_main_page_frag_layout_Listview);
        layoutNoData = (RelativeLayout) inflate.findViewById(R.id.inclue_userPage_no_data);
        layoutNoData.setVisibility(View.GONE);
        noDataBtn = (TextView) inflate.findViewById(R.id.goto_duobao);
        noDataBtn.setEnabled(false);
        noDataBtn.setVisibility(View.GONE);
        noDataBtn.setText("去夺宝");
        imgNoData = (ImageView) inflate.findViewById(R.id.no_date_layout_icon);
        imgNoData.setImageResource(R.drawable.icon_norecord_2);
        tvNoData = (TextView) inflate.findViewById(R.id.no_date_layout_tv);
        tvNoData.setText("对方还没有中奖记录哦");
        adapter = new UserWinRecrodAdapter(fragmentActivity);
        listView.setAdapter(adapter);
    }

    public void onEventMainThread(UserWinRecrodEvent userWinRecrodEvent) {
        if (userWinRecrodEvent.getBean() == null) {
            if (page == 1) {
                noWeb();
                layoutNoData.setVisibility(View.GONE);
                ptrClassicFrameLayout.refreshComplete();
                // print("网络的链接错误，请检查");
            }
        } else {
            if (userWinRecrodEvent.getBean().getCode().equals("1")) {
                if (userWinRecrodEvent.getBean().getData() != null && userWinRecrodEvent.getBean().getData().size() > 0) {
                    layoutNoData.setVisibility(View.GONE);
                    noDataBtn.setVisibility(View.GONE);
                    noDataBtn.setEnabled(false);
                    if (page == 1) {
                        if (adapter.getRecrodataList().size() > 0) {
                            adapter.getRecrodataList().clear();
                        }
                        adapter.setRecrodataList(userWinRecrodEvent.getBean().getData());
                    } else {
                        adapter.getRecrodataList().addAll(userWinRecrodEvent.getBean().getData());
                    }
                } else {

                    if (adapter.getRecrodataList().size() <= 0) {
                        layoutNoData.setVisibility(View.VISIBLE);
                        noDataBtn.setVisibility(View.GONE);
                        noDataBtn.setEnabled(false);
                        ptrClassicFrameLayout.refreshComplete();
                    } else {
                        page--;
                        layoutNoData.setVisibility(View.GONE);
                        noDataBtn.setVisibility(View.GONE);
                        noDataBtn.setEnabled(false);
                        ptrClassicFrameLayout.refreshComplete();
                        listView.setLoadComplete();
                    }

                }
            }
        }
        adapter.notifyDataSetChanged();
        waitLoadingUtils.disable();
        ptrClassicFrameLayout.refreshComplete();
    }


    private void noWeb() {
        waitLoadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                waitLoadingUtils.haveWeb();
                waitLoadingUtils.show();

                userWinRecrodModel.getWinRecrodData(page);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        noDataBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public String getFragmentName() {
        return UseWinRecrodFragment.class.getSimpleName();
    }
}
