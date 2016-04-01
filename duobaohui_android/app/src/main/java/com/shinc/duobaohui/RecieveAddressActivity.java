package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.adapter.RecieveAddressAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.customview.SwipeMenu;
import com.shinc.duobaohui.customview.SwipeMenuCreator;
import com.shinc.duobaohui.customview.SwipeMenuItem;
import com.shinc.duobaohui.customview.SwipeMenuListView;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.event.HttpAddressListEvent;
import com.shinc.duobaohui.event.HttpUpdateAddressDeleteEvent;
import com.shinc.duobaohui.event.HttpUpdateOrderStatusEvent;
import com.shinc.duobaohui.model.impl.AddressListModel;
import com.shinc.duobaohui.model.impl.RecieveAddressModelImpl;
import com.shinc.duobaohui.model.impl.UpdateAddressModelImpl;
import com.shinc.duobaohui.customview.dialog.DialogLottery;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * 选择收货地址的 activity
 * Created by yangtianhe on 15/9/30.
 */
public class RecieveAddressActivity extends BaseActivity {

    /**
     * 本页的提交兑奖在 139line
     */

    private Activity mActivity;
    private ImageView imgBack;
    private SwipeMenuListView addresslv;
    private RecieveAddressAdapter recieveAddressAdapter;
    private TextView add;
    private AddressListModel addressListModel;
    private CustomTextView relativeLayoutBtn;
    private UpdateAddressModelImpl updateAddressModel;
    private RecieveAddressModelImpl recieveAddressModel;
    private String address_id;
    private int i;
    private WaitLoadingUtils loadingUtils;
    private RelativeLayout relative;
    private ImageView noDataImg;
    private CustomTextView noDataTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_address);
        loadingUtils = new WaitLoadingUtils(this);
        loadingUtils.show();
        EventBus.getDefault().register(this);
        mActivity = RecieveAddressActivity.this;
        initView();
        initModel();
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.find_password_back_img);
        addresslv = (SwipeMenuListView) findViewById(R.id.recieve_address_listview);
        add = (TextView) findViewById(R.id.recieve_address_add);

        relativeLayoutBtn = (CustomTextView) findViewById(R.id.activity_recicve_address_layout_btn);
        recieveAddressAdapter = new RecieveAddressAdapter(this);
        addresslv.setAdapter(recieveAddressAdapter);
        relative = (RelativeLayout) findViewById(R.id.inclue_winner_all_no_data);
        relative.setVisibility(View.GONE);
        noDataImg = (ImageView) findViewById(R.id.no_date_layout_icon);
        noDataTv = (CustomTextView) findViewById(R.id.no_date_layout_tv);
        noDataTv.setText("暂时还没有地址信息哦");
        noDataImg.setImageResource(R.drawable.icon_norecord_5);
        initSwipeMenu();
    }

    private void initModel() {
        addressListModel = new AddressListModel(mActivity);
        updateAddressModel = new UpdateAddressModelImpl(mActivity);
        recieveAddressModel = new RecieveAddressModelImpl(mActivity);
        addressListModel.getListData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.is_Resume) {
            addressListModel.getListData();
        }

        MyApplication.is_Resume = false;
    }

    private void initSwipeMenu() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加地址信息
                Intent intent = new Intent();
                intent.setClass(mActivity, AddAddressActivity.class);
                startActivity(intent);
            }
        });
        relativeLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < recieveAddressAdapter.getAddressItemBeanList().size(); j++) {
                    if (recieveAddressAdapter.getAddressItemBeanList().get(j).getIs_default().equals("1")) {
                        // address_id = recieveAddressAdapter.getAddressItemBeanList().get(j).getAddress_id();

                        Intent mIntent = new Intent();
                        mIntent.putExtra("address_id", recieveAddressAdapter.getAddressItemBeanList().get(j).getAddress_id());
                        mIntent.putExtra("Consignee", recieveAddressAdapter.getAddressItemBeanList().get(j).getConsignee());
                        mIntent.putExtra("Mobile", recieveAddressAdapter.getAddressItemBeanList().get(j).getMobile());
                        mIntent.putExtra("District", recieveAddressAdapter.getAddressItemBeanList().get(j).getDistrict() + " " + recieveAddressAdapter.getAddressItemBeanList().get(j).getAddress());
                        // 设置结果，并进行传送
                        setResult(RESULT_OK, mIntent);
                        finish();
                        // recieveAddressModel.getUpdateOrderStatus(getIntent().getStringExtra("order_id"), getIntent().getStringExtra("order_status"), getIntent().getStringExtra("shipping_status"), address_id, "1");
                    }
                }


            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // 创建删除选项
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        RecieveAddressActivity.this);
                // set item background   删除选项卡的背景颜色
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xfc,
                        0x51, 0x1d)));
                // set item width  删除选项卡的大小
                deleteItem.setWidth((int) getResources().getDimension(R.dimen.s_57dp));
                // set a icon  删除选项卡的图片
                deleteItem.setTitle("删除");
                // add to menu 将这个删除选项添加到菜单中
                menu.addMenuItem(deleteItem);
                // set creator  为list设置侧拉选项卡
            }
        };
        addresslv.setMenuCreator(creator);
        // step 2. listener item click event  list的侧滑点击监听器
        addresslv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener()

        {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //滑动删除
                updateAddressModel.getInfoDelete(recieveAddressAdapter.getAddressItemBeanList().get(position).getAddress_id());
                return false;
            }
        });
        addresslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i = position;
                recieveAddressAdapter.setCheckbox(i);
            }
        });
    }

    /*删除方法*/
    public void onEventMainThread(HttpUpdateAddressDeleteEvent deleteEvent) {
        if (deleteEvent == null) {
            print("网络链接错误，请检查网络");
            noWeb();
        } else {
            if (deleteEvent.getVerifyCodeBean().getCode().equals("1")) {
                print(deleteEvent.getVerifyCodeBean().getMsg());
                recieveAddressAdapter.getAddressItemBeanList().remove(i);
                recieveAddressAdapter.notifyDataSetChanged();
            } else {
                print(deleteEvent.getVerifyCodeBean().getMsg());
            }
        }
    }

    /**
     * 获取收货地址列表信息
     *
     * @param addressListEvent
     */

    public void onEventMainThread(HttpAddressListEvent addressListEvent) {
        loadingUtils.disable();

        if (addressListEvent.getListBean() == null) {
            Toast.makeText(mActivity, "网络链接出错，请检查网络", Toast.LENGTH_LONG).show();
            relativeLayoutBtn.setVisibility(View.GONE);
            relative.setVisibility(View.VISIBLE);
            noWeb();
        } else {
            if (addressListEvent.getListBean().getData() == null) {
                //无数据作出的操作
                relativeLayoutBtn.setVisibility(View.GONE);
                relative.setVisibility(View.VISIBLE);
            } else {
                recieveAddressAdapter.setAddressItemBeanList(addressListEvent.getListBean().getData());
                relativeLayoutBtn.setVisibility(View.VISIBLE);
                relative.setVisibility(View.GONE);
            }
        }
    }

    /*兑奖信息返回*/
    public void onEventMainThread(HttpUpdateOrderStatusEvent addressListEvent) {
        if (addressListEvent.getGetVerifyCodeBean() == null) {
            Toast.makeText(mActivity, "网络链接出错，请检查网络", Toast.LENGTH_LONG).show();
        } else {
            if (addressListEvent.getGetVerifyCodeBean().getCode().equals("1")) {
                print(addressListEvent.getGetVerifyCodeBean().getMsg());
                MyApplication.is_Winner_refreash = true;
                //刷新Tab的number
                EventBus.getDefault().post("winnerDetail");
                //弹出对话框，选择是否分享
                DialogLottery.createLunckyDialog(mActivity, getIntent().getStringExtra("name"), "2");
            } else {
                print(addressListEvent.getGetVerifyCodeBean().getMsg());
            }
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
                addressListModel.getListData();
            }
        });
    }
}
