package com.shinc.duobaohui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.CouponsDetailsActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.adapter.CouponsAdapter;
import com.shinc.duobaohui.base.BaseFragment;
import com.shinc.duobaohui.bean.CouponsHttpResultBean;
import com.shinc.duobaohui.bean.CouponsNumBean;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.CouponsEnableEvent;
import com.shinc.duobaohui.event.CouponsEnableNumEvent;
import com.shinc.duobaohui.model.CouponsModel;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @作者: efort
 * @日期: 15/12/17 - 20:05
 * @工程名: duobaohui
 * @类简介: 可用红包的那啥页
 */
public class CouponsCanUseFragment extends BaseFragment {

    private FragmentActivity activity;

    private View view;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private LoadMoreListView listView;

    private CouponsModel couponsModel;

    private CouponsAdapter couponsAdapter;

    private int page;

    private WaitLoadingUtils loadingUtils;
    private RelativeLayout noDataLayout;// 无数据时加载得页面
    private ImageView imgNodata;
    private TextView tvNodata;
    private TextView noDataBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        EventBus.getDefault().register(this);
        view = inflater.inflate(R.layout.coupons_fragment_layout, null);
        initView(view);
        initModel();
        initListener();
        return view;
    }

    private void initModel() {
        couponsModel = new CouponsModel(activity);
        page = 1;
        couponsModel.getEnableCoupons(page);
//        test();
    }

    private void test() {
        loadingUtils.disable();
        noDataLayout.setVisibility(View.GONE);
        List<CouponsHttpResultBean.CouponsItemBean> testList = new ArrayList<CouponsHttpResultBean.CouponsItemBean>();
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("1"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("2"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        testList.add(new CouponsHttpResultBean().new CouponsItemBean("3"));
        couponsAdapter.refreshAdapter(testList);
    }

    private void initView(View view) {
        loadingUtils = new WaitLoadingUtils(view);
        loadingUtils.show();

        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame_layout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        listView = (LoadMoreListView) view.findViewById(R.id.coupons_fragment_layout_listview);
        couponsAdapter = new CouponsAdapter(getActivity(), new ArrayList<CouponsHttpResultBean.CouponsItemBean>(), "0");
        listView.setAdapter(couponsAdapter);
        noDataLayout = (RelativeLayout) view.findViewById(R.id.inclue_no_data);
        noDataLayout.setVisibility(View.GONE);

        noDataBtn = (TextView) view.findViewById(R.id.goto_duobao);
        noDataBtn.setVisibility(View.GONE);

        imgNodata = (ImageView) view.findViewById(R.id.no_date_layout_icon);
        tvNodata = (TextView) view.findViewById(R.id.no_date_layout_tv);
        tvNodata.setText("暂时还没有红包");

        imgNodata.setImageResource(R.drawable.no_coupons);
    }

    // TODO: 15/12/17 改成红包的
    public void onEventMainThread(CouponsEnableEvent couponsEvent) {
        loadingUtils.disable();
        if (couponsEvent.getCouponsBean() == null) {
            if (page == 1) {
                noDataLayout.setVisibility(View.VISIBLE);
            } else {
                page--;
                listView.setLoadComplete();
            }
        } else {
            if (couponsEvent.getCouponsBean().getCode().equals("1")) {
                if (page == 1 && couponsEvent.getCouponsBean().getData().getList().size() == 0) {
                    noDataLayout.setVisibility(View.VISIBLE);
                } else {
                    noDataLayout.setVisibility(View.GONE);
                }

                if (page == 1) {
                    couponsAdapter.refreshAdapter(couponsEvent.getCouponsBean().getData().getList());
                } else {
                    couponsAdapter.addItemList(couponsEvent.getCouponsBean().getData().getList());
                }
                if (couponsEvent.getCouponsBean().getData() != null && couponsEvent.getCouponsBean().getData().getCount() != null) {
                    CouponsHttpResultBean.Count count = couponsEvent.getCouponsBean().getData().getCount();
                    EventBus.getDefault().post(new CouponsEnableNumEvent(new CouponsNumBean(count.getIssue(), count.getUse(), count.getIs_overdue())));
                }
            } else {
                if (couponsAdapter != null && couponsAdapter.getItemList().size() > 0) {
                    page--;
                    listView.setLoadComplete();
                    noDataLayout.setVisibility(View.GONE);
                    noDataBtn.setEnabled(false);
                } else {
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }


        }
        ptrClassicFrameLayout.refreshComplete();
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
                couponsModel.getEnableCoupons(page);
                ptrClassicFrameLayout.refreshComplete();
            }
        });
        listView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                listView.setLoadReset();
                couponsModel.getEnableCoupons(page);
                listView.setLoadComplete();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                couponsAdapter.getItemList().get(position).setIs_new_red_money("1");

                Intent intent = new Intent(getActivity(), CouponsDetailsActivity.class);
                intent.putExtra("coupons_id", couponsAdapter.getItemList().get(position).getId());
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        couponsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void noWeb() {
        loadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                loadingUtils.show();
                page = 1;
                couponsModel.getEnableCoupons(page);
            }
        });
    }


    @Override
    public String getFragmentName() {
        return "可用红包Fragment";
    }
}
