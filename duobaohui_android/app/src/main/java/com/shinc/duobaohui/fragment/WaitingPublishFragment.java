package com.shinc.duobaohui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.shinc.duobaohui.ProductDetailActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.adapter.WaitPublishGridListAdapter;
import com.shinc.duobaohui.base.BaseFragment;
import com.shinc.duobaohui.bean.WaitPublishBean;
import com.shinc.duobaohui.bean.WaitPublishListBean;
import com.shinc.duobaohui.customview.imp.LoadMoreGridView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.WaitPublishProductEvent;
import com.shinc.duobaohui.model.impl.WaitPublishModel;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * 名称：NewPublishFragment
 * 作者：zhaopl 时间: 15/9/29.
 * 实现的主要功能：
 * 即将揭晓
 */
public class WaitingPublishFragment extends BaseFragment {
    @Override
    public String getFragmentName() {
        return WaitingPublishFragment.class.getSimpleName();
    }

    private Activity mActivity;
    private PtrClassicFrameLayout ptrFrameLayout;
    private int page = 1;
    private ArrayList<WaitPublishBean> list = new ArrayList();
    private WaitPublishGridListAdapter adapter;

    private WaitPublishModel waitPublishModel;

    private WaitPublishListBean waitPublishListBean;

    private ArrayList<WaitPublishBean> data;

    private LoadMoreGridView gridView;

    private WaitLoadingUtils waitLoadingUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait_finish_layout, null);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.mActivity = getActivity();
        initView(view);
        initData();
        initEvent();

        return view;
    }

    private void initView(View view) {


        waitLoadingUtils = new WaitLoadingUtils(view);
        waitLoadingUtils.show();
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame_layout);
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        gridView = (LoadMoreGridView) view.findViewById(R.id.waiting_open_grid_view);
    }

    private void initData() {

        waitPublishModel = new WaitPublishModel(mActivity);

        waitPublishModel.getProductInfo(page + "");

        adapter = new WaitPublishGridListAdapter(mActivity, list);
        gridView.setAdapter(adapter);

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
                waitPublishModel.getProductInfo(page + "");
            }
        });

        gridView.setLoadMoreListener(new LoadMoreGridView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                waitPublishModel.getProductInfo(page + "");
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo 点击跳转到商品详情页面；
                if (!TextUtils.isEmpty(list.get(position).getPeriod_id())) {
                    Intent intent = new Intent(mActivity, ProductDetailActivity.class);
                    intent.putExtra("PRODUCTID", list.get(position).getPeriod_id());
                    startActivity(intent);
                }
            }
        });

    }


    public void onEventMainThread(WaitPublishProductEvent waitPublishProductEvent) {

        ptrFrameLayout.refreshComplete();
        waitPublishListBean = waitPublishProductEvent.getWaitPublishListBean();
        if (waitPublishListBean == null) {
            noWebLayout();
        } else {
            if ("1".equals(waitPublishListBean.getCode())) {
                // todo 得到初始化数据,对数据进行赋值操作；
                data = (ArrayList) waitPublishListBean.getData();

                if (data != null) {
                    Long currentTime = System.currentTimeMillis();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getLeft_second() != null) {
                            data.get(i).setEndTime(currentTime + (Long.parseLong(data.get(i).getLeft_second()) * 10) + "");
                        }

                    }

                    if (page == 1) {
                        list.clear();
                        list.addAll(data);
                    } else {
                        list.addAll(waitPublishListBean.getData());
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    if (page > 1) {

                        Toast.makeText(mActivity, "没有更多数据", Toast.LENGTH_SHORT).show();
                    }
                    ptrFrameLayout.refreshComplete();
                }

            } else {
                if (page == 1) {
                    //若是为1，则，没有数据
                    Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();

                } else {
                    //不为1，则－－；
                    page--;
                    ptrFrameLayout.refreshComplete();
                    Toast.makeText(getActivity(), waitPublishListBean.getMsg(), Toast.LENGTH_SHORT).show();

                }

            }
        }

        waitLoadingUtils.disable();

    }


    private void noWebLayout() {
        waitLoadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                waitLoadingUtils.show();
                waitLoadingUtils.haveWeb();
                waitPublishModel.getProductInfo(page + "");
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        data = null;
        list = null;
    }
}
