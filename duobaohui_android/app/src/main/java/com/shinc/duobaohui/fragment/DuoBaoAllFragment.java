package com.shinc.duobaohui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shihe.shincdatastatisticssdk.ShiNcAgent;
import com.shinc.duobaohui.MainActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.adapter.DuoBaoRecordAdapter;
import com.shinc.duobaohui.base.BaseFragment;
import com.shinc.duobaohui.customview.imp.LoadMoreListView;
import com.shinc.duobaohui.customview.ptr.PtrClassicFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrDefaultHandler;
import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;
import com.shinc.duobaohui.customview.ptr.PtrHandler;
import com.shinc.duobaohui.event.DuoBaoAllFragmentEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.model.DuoBaoRecrodModelInterface;
import com.shinc.duobaohui.model.impl.DuoBaoRecrodModelImpl;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/11/4.
 * 夺宝纪录 － 全部（dev）
 */
public class DuoBaoAllFragment extends BaseFragment {

    private FragmentActivity activity;

    private View view;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private LoadMoreListView listView;
    private DuoBaoRecordAdapter duoBaoRecordAdapter;
    private DuoBaoRecrodModelInterface recrodModel;
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
        view = inflater.inflate(R.layout.duobao_fragment_layout, null);
        initView(view);
        initModel();
        initListener();

        testStatices();

        return view;
    }

    private void testStatices() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("ad_id", "test1");
        params.put("ad_pos", "23");
        params.put("seq_id", "test2");

        ShiNcAgent.onEvent(getActivity(), "ad_show", params);
    }

    private void initModel() {
        recrodModel = new DuoBaoRecrodModelImpl(activity);
        page = 1;
        recrodModel.getDuoBaoRecrod("0", page);
    }

    private void initView(View view) {
        loadingUtils = new WaitLoadingUtils(view);
        loadingUtils.show();

        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame_layout);
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        listView = (LoadMoreListView) view.findViewById(R.id.duobao_fragment_layout_listview);
        duoBaoRecordAdapter = new DuoBaoRecordAdapter(activity);
        listView.setAdapter(duoBaoRecordAdapter);

        noDataLayout = (RelativeLayout) view.findViewById(R.id.inclue_no_data);
        noDataLayout.setVisibility(View.GONE);
        noDataBtn = (TextView) view.findViewById(R.id.goto_duobao);
        noDataBtn.setEnabled(false);
        noDataBtn.setText("去夺宝");
        imgNodata = (ImageView) view.findViewById(R.id.no_date_layout_icon);
        tvNodata = (TextView) view.findViewById(R.id.no_date_layout_tv);
        tvNodata.setText("暂时还没有夺宝记录哦");
        imgNodata.setImageResource(R.drawable.icon_norecord_1);
    }

    /**
     * 控制该页面刷新；通过EventBus进行数据传递；
     *
     * @param utilsEvent
     */
    public void onEventMainThread(UtilsEvent utilsEvent) {

        if ("REFRESH".equals(utilsEvent.getFlag())) {

            recrodModel.getDuoBaoRecrod("0", page);
        }
    }

    public void onEventMainThread(DuoBaoAllFragmentEvent recrodEvent) {
        if (recrodEvent.getDuobaoBean() == null) {
            if (page == 1) {
                ptrClassicFrameLayout.refreshComplete();
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
                    listView.setLoadComplete();
                    ptrClassicFrameLayout.refreshComplete();
                    noDataLayout.setVisibility(View.GONE);
                    noDataBtn.setEnabled(false);
                } else {
                    noDataLayout.setVisibility(View.VISIBLE);
                    noDataBtn.setVisibility(View.VISIBLE);
                    noDataBtn.setEnabled(true);
                    ptrClassicFrameLayout.refreshComplete();
                }
            }
        }
        loadingUtils.disable();
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
                listView.setLoadReset();
                recrodModel.getDuoBaoRecrod("0", page);
            }
        });

        listView.setLoadMoreListener(new LoadMoreListView.LoadMoreDateListener() {
            @Override
            public void loadMore() {
                page++;
                recrodModel.getDuoBaoRecrod("0", page);
            }
        });

        noDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到首页；
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                EventBus.getDefault().post(new UtilsEvent("main"));
            }
        });
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
                loadingUtils.haveWeb();
                loadingUtils.show();
                recrodModel.getDuoBaoDate(page);
            }
        });
    }

    @Override
    public String getFragmentName() {
        return DuoBaoAllFragment.class.getSimpleName();
    }
}
