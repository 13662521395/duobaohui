package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.adapter.NoticeListAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.NoticeListBean;
import com.shinc.duobaohui.event.HttpNoticeListEvent;
import com.shinc.duobaohui.model.impl.NoticeListModelImpl;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by yangtianhe on 15/11/17.
 * 通知界面
 */
public class NoticeActivity extends BaseActivity {
    private Activity mActivity;
    private ImageView imgBack;
    private ListView listview;
    private NoticeListModelImpl model;
    private NoticeListAdapter adapter;
    private List<NoticeListBean.NoticeChildListBean> listChildBeans;


    WaitLoadingUtils loadingUtils;
    private RelativeLayout noDataLayout;//无数据时加载得页面
    private ImageView imgNodata;
    private TextView tvNodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();
        EventBus.getDefault().register(this);
        mActivity = NoticeActivity.this;
        initView();
        initModel();
        initListener();
    }

    private void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, NoticeDetailActivity.class);
                intent.putExtra("bean", listChildBeans.get(position));
                startActivity(intent);
            }
        });
    }

    private void initView() {
        getWindow().setBackgroundDrawable(null);
        imgBack = (ImageView) findViewById(R.id.notice_back_img);
        listview = (ListView) findViewById(R.id.notice_listview);
        listChildBeans = new ArrayList<>();
        adapter = new NoticeListAdapter(mActivity, listChildBeans);
        listview.setAdapter(adapter);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        noDataLayout = (RelativeLayout) findViewById(R.id.inclue_notice_no_data);
        noDataLayout.setVisibility(View.GONE);
        imgNodata = (ImageView) findViewById(R.id.no_date_layout_icon);
        tvNodata = (TextView) findViewById(R.id.no_date_layout_tv);
        tvNodata.setText("暂时还没有通知信息哦");
        imgNodata.setImageResource(R.drawable.icon_nomessage);

    }

    private void initModel() {
        model = new NoticeListModelImpl(mActivity);
        model.getNoticeList();
    }

    public void onEventMainThread(HttpNoticeListEvent event) {
        loadingUtils.disable();
        if (event.getListBean() == null) {
//            print("网络链接错误，请检查网络");
            noWeb();
        } else {
            if (event.getListBean().getCode().endsWith("1")) {
                if (event.getListBean().getData() != null && event.getListBean().getData().size() > 0) {
                    listChildBeans.clear();
                    listChildBeans.addAll(event.getListBean().getData());
                    adapter.notifyDataSetChanged();
                    noDataLayout.setVisibility(View.GONE);
                } else {
                    //通知页面为空时做的逻辑判断
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void noWeb() {
        loadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                loadingUtils.haveWeb();
                loadingUtils.show();
                model.getNoticeList();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
