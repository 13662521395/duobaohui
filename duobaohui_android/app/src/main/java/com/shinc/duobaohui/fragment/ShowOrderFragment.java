package com.shinc.duobaohui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.adapter.ShareShowOrderFragmentAdapter;
import com.shinc.duobaohui.base.BaseFragment;
import com.shinc.duobaohui.bean.ShaiDanBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.HttpGetShowOrderShareEvent;
import com.shinc.duobaohui.http.GetShowOrderHttpRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 名称：CartFragment
 * 作者：zhaopl 时间: 15/9/29.
 * 实现的主要功能：
 * 晒单页面的展示；(取消了在onResume中对页面进行刷新的操作，删除了onResume()的重写方法)
 */
public class ShowOrderFragment extends BaseFragment {
    @Override
    public String getFragmentName() {
        return null;
    }

    RelativeLayout title;
    ImageView back;
    TextView titleText;
    LoadMoreListView listView;
    WaitLoadingUtils waitLoadingUtils;


    //    private int state;
    private String user_id;

    PtrClassicFrameLayout ptrFrameLayout;
    private int page = 1;

    private boolean isRefresh;

    List<ShaiDanBean.ShaiDanItem> fragmentShaiDanItemList;
    ShareShowOrderFragmentAdapter adapter;

    private RelativeLayout layoutNoData;
    private ImageView imgNoData;
    private TextView tvNoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_show_order, null);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initView(view);
        waitLoadingUtils = new WaitLoadingUtils(view);
        waitLoadingUtils.show();
        getData(1);
        SharedPreferencesUtils spUtils = new SharedPreferencesUtils(getActivity(), Constant.SP_LOGIN);
        user_id = spUtils.get(Constant.SP_USER_ID, "");
        initPullToRefresh();
        return view;
    }

    private void initView(View view) {
        title = (RelativeLayout) view.findViewById(R.id.share_show_title);
        back = (ImageView) view.findViewById(R.id.add_show_order_layout_img_back);
        titleText = (TextView) view.findViewById(R.id.share_show_title_text);
        //如果是由首页点击进来那么我们的title是有变化的
        back.setVisibility(View.GONE);
        title.setBackgroundColor(getResources().getColor(R.color.c_ff5a5a));
        titleText.setTextColor(getResources().getColor(R.color.fff));


        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame_layout);
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        listView = (LoadMoreListView) view.findViewById(R.id.share_order_listview);
//        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));


        layoutNoData = (RelativeLayout) view.findViewById(R.id.fragment_share_show_order_noLayout);
        layoutNoData.setVisibility(View.GONE);
        imgNoData = (ImageView) view.findViewById(R.id.no_date_layout_icon);
        imgNoData.setImageResource(R.drawable.icon_norecord_2);
        tvNoData = (TextView) view.findViewById(R.id.no_date_layout_tv);
        tvNoData.setText("暂时还没有晒单记录哦");
    }

    private void getData(int page) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("page", page + "");

        httpUtils.sendHttpPost(requestParams, ConstantApi.GET_SHAI_DAN, new GetShowOrderHttpRequestImpl("2"), getActivity());
    }

    public void onEventMainThread(HttpGetShowOrderShareEvent getShowOrderEvent) {
        ShaiDanBean shaiDanBean = getShowOrderEvent.getShaiDanBean();

        if (shaiDanBean == null || shaiDanBean.getCode() == null) {
            if (ptrFrameLayout != null && isRefresh) {
                ptrFrameLayout.refreshComplete();
                isRefresh = false;
            }
            if (page == 1) {
                noWeb();
                layoutNoData.setVisibility(View.GONE);
                if (fragmentShaiDanItemList != null) {
                    fragmentShaiDanItemList.clear();
                    adapter.notifyDataSetChanged();
                }
            } else {
                page--;
            }
            return;
        }
        if ("1".equals(shaiDanBean.getCode())) {

            //todo 成功，设置页面信息；
            if (shaiDanBean.getData() != null && shaiDanBean.getData().size() > 0) {
                layoutNoData.setVisibility(View.GONE);
                if (page == 1) {
                    if (fragmentShaiDanItemList == null) {
                        fragmentShaiDanItemList = shaiDanBean.getData();
                    } else {
                        fragmentShaiDanItemList.clear();
                        fragmentShaiDanItemList.addAll(shaiDanBean.getData());
                    }
                    if (adapter == null) {
                        initAdapter();
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {

                    fragmentShaiDanItemList.addAll(shaiDanBean.getData());
                    adapter.notifyDataSetChanged();
                }
            } else {
                if (page > 1) {
                    page--;
                }
            }
        } else {
            if (page > 1) {

                print("没有更多数据");
                layoutNoData.setVisibility(View.GONE);
                page--;
            } else {

                layoutNoData.setVisibility(View.VISIBLE);
            }

            ptrFrameLayout.refreshComplete();

        }
        if (ptrFrameLayout != null && isRefresh) {
            ptrFrameLayout.refreshComplete();

            isRefresh = false;
        }
        waitLoadingUtils.disable();
    }

    /**
     * 上下拉
     */
    private void initPullToRefresh() {


        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame,content,header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                isRefresh = true;
                getData(page);
            }
        });

        listView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                isRefresh = true;
                getData(page);
            }
        });


    }


    private void initAdapter() {
        adapter = new ShareShowOrderFragmentAdapter(getActivity());
        adapter.setList(fragmentShaiDanItemList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void noWeb() {
        waitLoadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                waitLoadingUtils.haveWeb();
                waitLoadingUtils.show();
                getData(1);
            }
        });
    }
}
