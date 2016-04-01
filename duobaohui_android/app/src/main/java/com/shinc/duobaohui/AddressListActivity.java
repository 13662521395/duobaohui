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

import com.shinc.duobaohui.adapter.AddressListAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.event.HttpAddressListEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.model.impl.AddressListModel;
import com.shinc.duobaohui.utils.CodeVerifyUtils;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/9/29.
 * 地址列表
 */
public class AddressListActivity extends BaseActivity {

    private Activity mActivity;
    private ImageView imgBack;
    private CustomTextView tvAddAddress;
    private ListView listView;

    private RelativeLayout noDataLayout;//无数据时加载得页面
    private ImageView imgNodata;
    private TextView tvNodata;

    private AddressListAdapter addressListAdapter;
    private AddressListModel addressListModel;
    WaitLoadingUtils loadingUtils;
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_list_layout);
        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();
        EventBus.getDefault().register(this);
        mActivity = AddressListActivity.this;
        initView();
        initModel();
        initListener();
    }

    private void initModel() {
        addressListModel = new AddressListModel(mActivity);
        addressListModel.getListData();
    }

    /**
     * 获取收货地址列表信息
     *
     * @param addressListEvent
     */
    public void onEventMainThread(HttpAddressListEvent addressListEvent) {
        loadingUtils.disable();
        if (addressListEvent.getListBean() == null) {
//            Toast.makeText(mActivity, "网络链接出错，请检查网络", Toast.LENGTH_LONG).show();
            noDataLayout.setVisibility(View.GONE);
            noWeb();
        } else {
            if (CodeVerifyUtils.verifyCode(addressListEvent.getListBean().getCode())) {
                CodeVerifyUtils.verifySession(mActivity);
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
                finish();
            } else {
                if (addressListEvent.getListBean().getData() == null) {

                    //无数据作出的操作
                    addressListAdapter.getListChildBeans().clear();

                    noDataLayout.setVisibility(View.VISIBLE);
                } else {
                    noDataLayout.setVisibility(View.GONE);
                    addressListAdapter.setListChildBeans(addressListEvent.getListBean().getData());
                }

                addressListAdapter.notifyDataSetChanged();

            }
        }
    }

    private void initView() {

        imgBack = (ImageView) findViewById(R.id.address_list_layout_img_back);
        tvAddAddress = (CustomTextView) findViewById(R.id.address_list_layout_tv_addAddress);
        listView = (ListView) findViewById(R.id.address_list_layout_listview);
        addressListAdapter = new AddressListAdapter(mActivity);
        listView.setAdapter(addressListAdapter);
        noDataLayout = (RelativeLayout) findViewById(R.id.address_list_no_data_layout);
        noDataLayout.setVisibility(View.GONE);
        imgNodata = (ImageView) findViewById(R.id.no_date_layout_icon);
        tvNodata = (TextView) findViewById(R.id.no_date_layout_tv);
        tvNodata.setText("暂时还没有地址信息哦");
        imgNodata.setImageResource(R.drawable.icon_norecord_5);
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //修改地址信息
                Intent intent = new Intent();
                intent.setClass(mActivity, UpdateAddressActivity.class);
                intent.putExtra("update", addressListAdapter.getListChildBeans().get(position));
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加地址信息
                Intent intent = new Intent();
                intent.setClass(mActivity, AddAddressActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.is_Resume) {
            loadingUtils.show();
            addressListModel.getListData();
            MyApplication.is_Resume = false;
        } else {
            MyApplication.is_Resume = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void noWeb() {
        loadingUtils.isNoWeb(new WaitLoadingUtils.OnNoWebClick() {
            @Override
            public void onClick() {
                loadingUtils.haveWeb();
                loadingUtils.show();
                page = 1;
                addressListModel.getListData();
            }
        });
    }
}
