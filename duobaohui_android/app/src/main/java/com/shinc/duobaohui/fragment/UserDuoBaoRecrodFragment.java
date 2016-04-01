package com.shinc.duobaohui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.adapter.UserDuoBaoFragAdapter;
import com.shinc.duobaohui.base.BaseFragment;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.UserDuoBaoRecrodEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.model.impl.UserDuoBaoRecrodModel;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/11/17.
 * TA的主页－夺宝纪录
 */
@SuppressLint("ValidFragment")
public class UserDuoBaoRecrodFragment extends BaseFragment {

    private FragmentActivity fragmentActivity;

    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private LoadMoreListView listView;
    private UserDuoBaoFragAdapter adapter;
    private UserDuoBaoRecrodModel model;
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
        user_id = getArguments().getString("userId");
        fragmentActivity = getActivity();
        waitLoadingUtils = new WaitLoadingUtils(inflate);
        waitLoadingUtils.show();
        EventBus.getDefault().register(this);
        initView(inflate);
        page = 1;
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
                model.getUserDuoBaoRecrodModel(page);
            }
        });

        listView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                model.getUserDuoBaoRecrodModel(page);
            }
        });

    }

    private void initModel() {
        model = new UserDuoBaoRecrodModel(fragmentActivity, user_id);
        model.getUserDuoBaoRecrodModel(page);
    }

    private void initView(View inflate) {
        ptrClassicFrameLayout = (PtrClassicFrameLayout) inflate.findViewById(R.id.ptr_frame_layout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        listView = (LoadMoreListView) inflate.findViewById(R.id.user_main_page_frag_layout_Listview);
        layoutNoData = (RelativeLayout) inflate.findViewById(R.id.inclue_userPage_no_data);
        layoutNoData.setVisibility(View.GONE);
        noDataBtn = (TextView) inflate.findViewById(R.id.goto_duobao);
        noDataBtn.setVisibility(View.GONE);
        noDataBtn.setEnabled(false);
        noDataBtn.setText("去夺宝");
        imgNoData = (ImageView) inflate.findViewById(R.id.no_date_layout_icon);
        imgNoData.setImageResource(R.drawable.icon_norecord_1);
        tvNoData = (TextView) inflate.findViewById(R.id.no_date_layout_tv);
        tvNoData.setText("对方还没有夺宝记录哦");
        adapter = new UserDuoBaoFragAdapter(fragmentActivity);
        listView.setAdapter(adapter);
    }
    /**
     * 控制该页面刷新；通过EventBus进行数据传递；
     *
     * @param utilsEvent
     */
    public void onEventMainThread(UtilsEvent utilsEvent) {

        if ("REFRESH".equals(utilsEvent.getFlag())) {

            model.getUserDuoBaoRecrodModel(page);
        }
    }
    public void onEventMainThread(UserDuoBaoRecrodEvent userDuoBaoRecrodEvent) {
        if (userDuoBaoRecrodEvent.getDuoBaoBean() == null) {
            if (page == 1) {
                noWeb();
                layoutNoData.setVisibility(View.GONE);
                ptrClassicFrameLayout.refreshComplete();
                // print("网络的链接错误，请检查");
            }
        } else {
            if (userDuoBaoRecrodEvent.getDuoBaoBean().getCode().equals("1")) {
                if (userDuoBaoRecrodEvent.getDuoBaoBean().getData() != null) {
                    layoutNoData.setVisibility(View.GONE);
                    noDataBtn.setVisibility(View.GONE);
                    noDataBtn.setEnabled(false);
                    if (page == 1) {
                        if (adapter.getCrodChildBeans().size() > 0) {
                            adapter.getCrodChildBeans().clear();
                        }
                        adapter.setCrodChildBeans(userDuoBaoRecrodEvent.getDuoBaoBean().getData());
                    } else {
                        adapter.getCrodChildBeans().addAll(userDuoBaoRecrodEvent.getDuoBaoBean().getData());
                    }
                } else {
                    if (adapter.getCrodChildBeans().size() > 0) {
                        page--;
                        layoutNoData.setVisibility(View.GONE);
                        noDataBtn.setVisibility(View.GONE);
                        noDataBtn.setEnabled(false);
                        ptrClassicFrameLayout.refreshComplete();
                        listView.setLoadComplete();
                    } else {
                        layoutNoData.setVisibility(View.VISIBLE);
                        noDataBtn.setVisibility(View.GONE);
                        noDataBtn.setEnabled(false);
                        ptrClassicFrameLayout.refreshComplete();
                    }
                }
            } else {
                if (page == 1) {
                    layoutNoData.setVisibility(View.VISIBLE);
                    noDataBtn.setVisibility(View.VISIBLE);
                    noDataBtn.setEnabled(true);
                    ptrClassicFrameLayout.refreshComplete();
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

                model.getUserDuoBaoRecrodModel(page);
            }
        });
    }

    @Override
    public String getFragmentName() {
        return UserDuoBaoRecrodFragment.class.getSimpleName();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        noDataBtn.setVisibility(View.VISIBLE);
    }
}
