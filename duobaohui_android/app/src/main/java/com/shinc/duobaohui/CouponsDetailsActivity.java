package com.shinc.duobaohui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.adapter.recycleradapter.CouponsDetailsRecyclerAdater;
import com.shinc.duobaohui.bean.CouponsDetailsHttpBean;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.recycler.CouponsDetailsHeader;
import com.shinc.duobaohui.event.CouponsDetailsEvent;
import com.shinc.duobaohui.http.CouponsDetailsRequestImpl;
import com.shinc.duobaohui.utils.WaitLoadingUtils;
import com.shinc.duobaohui.utils.icon.ui.BaseActivity;
import com.shinc.duobaohui.utils.web.HttpUtils;

import de.greenrobot.event.EventBus;

/**
 * @作者: efort
 * @日期: 15/12/17 - 21:03
 * @工程名: duobaohui
 * @类简介: 红包详情
 */
public class CouponsDetailsActivity extends BaseActivity {

    private CouponsDetailsHeader recyclerHeader;

    CouponsDetailsRecyclerAdater adater;
    RecyclerView mRecyclerView;

    private WaitLoadingUtils loadingUtils;
    private RelativeLayout noDataLayout;
    private TextView noDataBtn;
    private ImageView imgNodata;
    private TextView tvNodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_details);
        EventBus.getDefault().register(this);
        initView();
        getData();
        initEvent();
    }

    private void getData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("redpacket_id", getIntent().getStringExtra("coupons_id"));
        httpUtils.sendHttpPost(requestParams, ConstantApi.COUPONS_DETAILS, new CouponsDetailsRequestImpl(), this);
    }

    private void initView() {
        ImageView goBack = (ImageView) findViewById(R.id.coupons_details_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.coupons_details_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new MyListViewItemDecoration(this, LinearLayoutManager.VERTICAL));

        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();

        noDataLayout = (RelativeLayout) findViewById(R.id.inclue_no_data);
        noDataLayout.setVisibility(View.GONE);

        imgNodata = (ImageView) findViewById(R.id.no_date_layout_icon);
        tvNodata = (TextView) findViewById(R.id.no_date_layout_tv);
        tvNodata.setText("没有获取到红包详情呢");

        imgNodata.setImageResource(R.drawable.icon_norecord_1);

        adater = new CouponsDetailsRecyclerAdater(this);
        recyclerHeader = new CouponsDetailsHeader(this);
        adater.addHeaderView(recyclerHeader);
        mRecyclerView.setAdapter(adater);

    }

    private void initEvent() {

        adater.setItemClick(new CouponsDetailsRecyclerAdater.ItemClickListner() {
            @Override
            public void onItemClick(String productId) {
                Intent intent = new Intent(CouponsDetailsActivity.this, ProductDetailActivity.class);
                intent.putExtra("PRODUCTID", productId);
                startActivity(intent);
            }
        });

    }


    public void onEventMainThread(CouponsDetailsEvent couponsDetilasEvent) {
        loadingUtils.disable();
        if (couponsDetilasEvent.getCouponsBean() != null) {
            if ("1".equals(couponsDetilasEvent.getCouponsBean().getCode())) {
                CouponsDetailsHttpBean.CouponsDetialsBean couponsDetialsBean = couponsDetilasEvent.getCouponsBean().getData();
                recyclerHeader.initData(getIntent().getIntExtra("type", 0), couponsDetialsBean.getPrice(), couponsDetialsBean.getRed_money_name(), couponsDetialsBean.getOverdue_time(), couponsDetialsBean.getConsumption(), couponsDetialsBean.getRange(), couponsDetialsBean.getRange());

                adater.setItemList(couponsDetilasEvent.getCouponsBean().getData().getActivity());
            } else {
                noDataLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
