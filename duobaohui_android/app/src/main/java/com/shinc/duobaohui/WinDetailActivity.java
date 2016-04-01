package com.shinc.duobaohui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.bean.WinItemDetailBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.event.HttpStayStocksEvent;
import com.shinc.duobaohui.event.HttpWinUpdateOrderStatusEvent;
import com.shinc.duobaohui.event.WinItemDetailEvent;
import com.shinc.duobaohui.model.impl.RecieveAddressModelImpl;
import com.shinc.duobaohui.model.impl.UpdateStatusStayStackImpl;
import com.shinc.duobaohui.model.impl.WinitemDetailModel;
import com.shinc.duobaohui.customview.dialog.DialogLottery;
import com.shinc.duobaohui.utils.DialogUtils;
import com.shinc.duobaohui.utils.ImageLoad;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/11/18.
 */
public class WinDetailActivity extends BaseActivity {
    private ImageView imgBack;
    /*恭喜中奖了*/
    private ImageView img0;//图片
    private CustomTextView tvTime0;//中奖时间
    private View v0;
    /*兑奖*/
    private ImageView img1;
    private CustomTextView tvWord1;//兑奖｜｜ 已兑奖
    private CustomTextView tvTime1;//兑奖时间
    private View v1;
    private View v11;
    private RelativeLayout layoutAddress;
    private CustomTextView tvAddressDetail0;//详细地址
    private CustomTextView tvAddressName0;//姓名＋手机号
    private CustomTextView btnDefualt;//使用默认的地址
    private CustomTextView btnOuthor;//使用其他的地址
    /*派奖*/
    private LinearLayout sendPrizeLayout;
    private ImageView img2;
    private CustomTextView tvword2;
    private CustomTextView tvtime2;
    private View v2;
    private CustomTextView tvInfo2;

    //收货
    private ImageView img3;
    private RelativeLayout goodsReceivedLayout;
    private CustomTextView tvword3;
    private CustomTextView tvtime3;
    private View v3;
    private View v33;
    private CustomTextView tvInfo3;
    private CustomTextView btnInfo3;

    //晒单
    private ImageView img4;
    private CustomTextView tvWrod4;
    private CustomTextView tvTime4;
    private CustomTextView v4;
    private CustomTextView tvInfo4;
    private CustomTextView btnInfo4;

    //物流信息win_detail_layout_LogisticsInformation
    private LinearLayout logisticslayout;
    private CustomTextView tvLogistics;
    private CustomTextView tvLogisticsValue;
    private CustomTextView tvLogisticsCode;

    //收货信息
    private LinearLayout addresslayout;
    private CustomTextView tvAddressInfomation;
    private CustomTextView tvAddressInfomationValue;
    private CustomTextView tvAddressNameInfomation;

    //奖品详情
    private ImageView imgIcon;
    private CustomTextView tvName;
    private CustomTextView totalPersonNumber;
    private CustomTextView periodPersonNumber;
    private CustomTextView lunckyyNumber;
    private CustomTextView announcedTime;
    private WinitemDetailModel model;
    private Activity mActivity;

    //根据判断 快递信息 和 收货地址信息 是否赋值 默认值0
    private int logisticsType = 0;
    private int addressType = 0;

    //兑奖model
    private RecieveAddressModelImpl recieveAddressModel;
    //确认收货
    private UpdateStatusStayStackImpl updateStatusStayStack;

    private WaitLoadingUtils waitLoadingUtils;

    private RelativeLayout noDataLayout;//无数据时加载得页面
    private ImageView imgNodata;
    private TextView tvNodata;
    private WinItemDetailBean.WinItemListChildData data;
    private String period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_detail_layout);
        waitLoadingUtils = new WaitLoadingUtils(this);
        waitLoadingUtils.show();
        period = getIntent().getStringExtra("period_id");
        mActivity = WinDetailActivity.this;
        EventBus.getDefault().register(this);
        initView();
        initModel();
        initListener();
    }

    private void initModel() {
        model = new WinitemDetailModel(mActivity);
        model.getWinDetailData(period);
        recieveAddressModel = new RecieveAddressModelImpl(mActivity);
        updateStatusStayStack = new UpdateStatusStayStackImpl(mActivity);
    }

    public void onEventMainThread(WinItemDetailEvent bean) {

        if (bean.getBean() == null) {
            print("网络链接出错，请检查网络链接");
        } else {
            if (bean.getBean().getCode().equals("1")) {
                if (bean.getBean().getData() != null) {
                    data = bean.getBean().getData();
                    initData();
                }
            }
        }
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.win_recrod_layout_back);
        img0 = (ImageView) findViewById(R.id.win_detail_layout_imgZXZJ);
        tvTime0 = (CustomTextView) findViewById(R.id.win_detail_layout_tvTime);
        v0 = findViewById(R.id.view_line_0);

        img1 = (ImageView) findViewById(R.id.win_detail_layout_ImgExpiryDate);
        tvWord1 = (CustomTextView) findViewById(R.id.win_detail_layout_tvWrodExpiryDate);
        tvTime1 = (CustomTextView) findViewById(R.id.win_detail_layout_tvWrodExpiryDateTime);
        v1 = findViewById(R.id.view_line_v1);
        v11 = findViewById(R.id.view_line_v11);
        layoutAddress = (RelativeLayout) findViewById(R.id.win_detail_layout_layoutAddress);
        tvAddressDetail0 = (CustomTextView) findViewById(R.id.win_detail_layout_addressDetail);
        tvAddressName0 = (CustomTextView) findViewById(R.id.win_detail_layout_addressNamePhone);
        btnDefualt = (CustomTextView) findViewById(R.id.win_detail_layout_btnOkAddressOuthor);
        btnOuthor = (CustomTextView) findViewById(R.id.win_detail_layout_btnOkAddressDefualt);

        sendPrizeLayout = (LinearLayout) findViewById(R.id.win_detail_layout_SendParizelayout);
        img2 = (ImageView) findViewById(R.id.win_detail_layout_ImgSendThrPize);
        tvword2 = (CustomTextView) findViewById(R.id.win_detail_layout_TvSendThrPize);
        tvtime2 = (CustomTextView) findViewById(R.id.win_detail_layout_TvSendThrPizeTime);
        v2 = findViewById(R.id.view_line_v2);
        tvInfo2 = (CustomTextView) findViewById(R.id.win_detail_layout_tvInfo2);

        goodsReceivedLayout = (RelativeLayout) findViewById(R.id.win_detail_layout_GoodsReceived);
        img3 = (ImageView) findViewById(R.id.win_detail_layout_GoodToBeReceived);
        tvword3 = (CustomTextView) findViewById(R.id.win_detail_layout_tvGoodToBeReceived);
        tvtime3 = (CustomTextView) findViewById(R.id.win_detail_layout_tvGoodToBeReceivedTime);
        v3 = findViewById(R.id.view_line_v3);
        v33 = findViewById(R.id.view_line_v33);
        tvInfo3 = (CustomTextView) findViewById(R.id.win_detail_layout_tvGoodToBeReceivedInfo);
        btnInfo3 = (CustomTextView) findViewById(R.id.win_detail_layout_btnGoodToBeReceivedInfo);

        img4 = (ImageView) findViewById(R.id.win_detail_layout_Completed);
        tvWrod4 = (CustomTextView) findViewById(R.id.win_detail_layout_CompletedShowOrder);
        tvTime4 = (CustomTextView) findViewById(R.id.win_detail_layout_CompletedTime);
        btnInfo4 = (CustomTextView) findViewById(R.id.win_detail_layout_btnCompletedTime);

        logisticslayout = (LinearLayout) findViewById(R.id.win_detail_layout_LogisticsInformationLayout);
        tvLogistics = (CustomTextView) findViewById(R.id.win_detail_layout_LogisticsInformation);
        tvLogisticsValue = (CustomTextView) findViewById(R.id.win_detail_layout_LogisticsInformationValue);
        tvLogisticsCode = (CustomTextView) findViewById(R.id.win_detail_layout_LogisticsInformationCode);

        addresslayout = (LinearLayout) findViewById(R.id.win_detail_layout_tvAddressInfomationLayout);
        tvAddressInfomation = (CustomTextView) findViewById(R.id.win_detail_layout_tvAddressInfomation);
        tvAddressInfomationValue = (CustomTextView) findViewById(R.id.win_detail_layout_tvAddressInfomationValue);
        tvAddressNameInfomation = (CustomTextView) findViewById(R.id.win_detail_layout_tvAddressNameValue);

        imgIcon = (ImageView) findViewById(R.id.win_detail_layout_img);
        tvName = (CustomTextView) findViewById(R.id.win_detail_layout_name);
        totalPersonNumber = (CustomTextView) findViewById(R.id.win_detail_layout_total_num);
        periodPersonNumber = (CustomTextView) findViewById(R.id.win_detail_layout_period_num);
        lunckyyNumber = (CustomTextView) findViewById(R.id.win_detail_layout_luncky_code);
        announcedTime = (CustomTextView) findViewById(R.id.win_detail_layout_time);
        noDataLayout = (RelativeLayout) findViewById(R.id.recharge_layout_layout_noData);
        noDataLayout.setVisibility(View.GONE);
        imgNodata = (ImageView) findViewById(R.id.no_date_layout_icon);
        tvNodata = (TextView) findViewById(R.id.no_date_layout_tv);
        tvNodata.setText("暂时还没有中奖记录哦");
        imgNodata.setImageResource(R.drawable.icon_norecord_4);
    }


    private void initData() {
        //"0",当前状态，0:待领奖，1:派奖中，2:待收货，3:未晒单，4:一晒单
        img0.setImageResource(R.drawable.icon_h);
        tvTime0.setText(data.getAdd_time());
        if (!TextUtils.isEmpty(data.getAddress_address())) {
            tvAddressDetail0.setText(data.getAddress_address() + data.getAddress_district());
            tvAddressName0.setText(data.getAddress_consignee() + data.getAddress_mobile());
            btnDefualt.setVisibility(View.VISIBLE);
        } else {
            tvAddressDetail0.setText("您还没有设置地址,");
            tvAddressName0.setText("请赶快设置收货地址并领奖吧");
            btnDefualt.setVisibility(View.GONE);
        }
        if (data.getStatus().equals("0")) {
            img1.setImageResource(R.drawable.icon_r);
            img2.setImageResource(R.drawable.icon_h);
            img3.setImageResource(R.drawable.icon_h);
            img4.setImageResource(R.drawable.icon_h);
            tvTime1.setText("请确认收货信息");
            tvWord1.setText("待领奖");
            tvword2.setText("派奖");
            tvword3.setText("收货");
            tvWrod4.setText("晒单");
            v1.setVisibility(View.VISIBLE);
            v11.setVisibility(View.GONE);
            v3.setVisibility(View.VISIBLE);
            v33.setVisibility(View.GONE);
            layoutAddress.setVisibility(View.VISIBLE);
            tvInfo2.setVisibility(View.GONE);
            goodsReceivedLayout.setVisibility(View.GONE);
            btnInfo4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            logisticslayout.setVisibility(View.GONE);
            addresslayout.setVisibility(View.GONE);
            logisticsType = 0;
            addressType = 0;
        } else if (data.getStatus().equals("1")) {
            img1.setImageResource(R.drawable.icon_h);
            img2.setImageResource(R.drawable.icon_r);
            img3.setImageResource(R.drawable.icon_h);
            img4.setImageResource(R.drawable.icon_h);
            tvTime1.setText(data.getConfirm_time());
            tvtime2.setText(data.getShipping_time());
            tvWord1.setText("已领奖");
            tvword2.setText("派奖中");
            tvword3.setText("收货");
            tvWrod4.setText("晒单");
            v1.setVisibility(View.GONE);
            v11.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
            v33.setVisibility(View.GONE);
            layoutAddress.setVisibility(View.GONE);
            tvInfo2.setVisibility(View.VISIBLE);
            goodsReceivedLayout.setVisibility(View.GONE);
            btnInfo4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            logisticslayout.setVisibility(View.GONE);
            addresslayout.setVisibility(View.VISIBLE);
            logisticsType = 0;
            addressType = 1;
        } else if (data.getStatus().equals("2")) {
            img1.setImageResource(R.drawable.icon_h);
            img2.setImageResource(R.drawable.icon_h);
            img3.setImageResource(R.drawable.icon_r);
            img4.setImageResource(R.drawable.icon_h);
            tvTime1.setText(data.getConfirm_time());
            tvtime2.setText(data.getShipping_time());
            tvtime3.setText(data.getReceive_time());
            tvWord1.setText("已领奖");
            tvword2.setText("派奖中");
            tvword3.setText("待收货");
            tvWrod4.setText("晒单");
            v1.setVisibility(View.GONE);
            v11.setVisibility(View.VISIBLE);
            v3.setVisibility(View.GONE);
            v33.setVisibility(View.VISIBLE);
            layoutAddress.setVisibility(View.GONE);
            tvInfo2.setVisibility(View.GONE);
            goodsReceivedLayout.setVisibility(View.VISIBLE);
            btnInfo4.setVisibility(View.GONE);
            tvTime4.setVisibility(View.GONE);
            logisticslayout.setVisibility(View.GONE);
            addresslayout.setVisibility(View.VISIBLE);
            logisticsType = 1;
            addressType = 1;
        } else if (data.getStatus().equals("3")) {
            img1.setImageResource(R.drawable.icon_h);
            img2.setImageResource(R.drawable.icon_h);
            img3.setImageResource(R.drawable.icon_h);
            img4.setImageResource(R.drawable.icon_r);
            tvTime1.setText(data.getConfirm_time());
            tvtime2.setText(data.getShipping_time());
            tvtime3.setText(data.getReceive_time());
            tvWord1.setText("已领奖");
            tvword2.setText("派奖中");
            tvword3.setText("已收货");
            tvWrod4.setText("未晒单");
            v1.setVisibility(View.GONE);
            v11.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
            v33.setVisibility(View.GONE);
            layoutAddress.setVisibility(View.GONE);
            tvInfo2.setVisibility(View.GONE);
            goodsReceivedLayout.setVisibility(View.GONE);
            btnInfo4.setVisibility(View.VISIBLE);
            tvTime4.setVisibility(View.GONE);
            logisticslayout.setVisibility(View.VISIBLE);
            addresslayout.setVisibility(View.VISIBLE);
            logisticsType = 1;
            addressType = 1;
        } else if (data.getStatus().equals("4")) {
            img1.setImageResource(R.drawable.icon_h);
            img2.setImageResource(R.drawable.icon_h);
            img3.setImageResource(R.drawable.icon_h);
            img4.setImageResource(R.drawable.icon_r);
            tvTime1.setText(data.getConfirm_time());
            tvtime2.setText(data.getShipping_time());
            tvtime3.setText(data.getReceive_time());
            tvTime4.setText(data.getShaidan_create_time());
            tvWord1.setText("已领奖");
            tvword2.setText("派奖中");
            tvword3.setText("已收货");
            tvWrod4.setText("已晒单");
            v1.setVisibility(View.GONE);
            v11.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
            v33.setVisibility(View.GONE);
            layoutAddress.setVisibility(View.GONE);
            tvInfo2.setVisibility(View.GONE);
            goodsReceivedLayout.setVisibility(View.GONE);
            tvTime4.setVisibility(View.VISIBLE);
            btnInfo4.setVisibility(View.GONE);
            logisticslayout.setVisibility(View.VISIBLE);
            addresslayout.setVisibility(View.VISIBLE);
            logisticsType = 1;
            addressType = 1;
        }
        if (TextUtils.isEmpty(data.getGoods_img())) {
            imgIcon.setImageResource(R.drawable.icon_nopic);
        } else {
            ImageLoad.getInstance(mActivity).setImageToView(data.getGoods_img(), imgIcon);
        }
        tvName.setText("(第" + data.getPeriod_number() + "期)" + data.getGoods_name());
        totalPersonNumber.setText("共需人次: " + data.getReal_need_times());
        periodPersonNumber.setText(Html.fromHtml("本期参与: <font color='#ff5a5a'>" + data.getTotal_times() + "</font>"));
        lunckyyNumber.setText(Html.fromHtml("幸运号码: <font color='#ff5a5a'>" + data.getLuck_code() + "</font>"));
        announcedTime.setText(Html.fromHtml("揭晓时间: " + data.getLuck_code_create_time()));
        if (addressType == 1) {
            tvAddressInfomationValue.setText(data.getAddress());
            tvAddressNameInfomation.setText(data.getConsignee() + "  " + data.getMobile());
        }
        if (logisticsType == 1) {
            if (TextUtils.isEmpty(data.getExpress_company())) {
                logisticslayout.setVisibility(View.GONE);
            } else {
                tvInfo3.setText(data.getExpress_company() + ": " + data.getInvoice_no());
                tvLogisticsValue.setText(data.getExpress_company());
                tvLogisticsCode.setText(data.getInvoice_no());
            }
        }
        waitLoadingUtils.disable();
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //监听兑奖。
        btnDefualt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieveAddressModel.getUpdateOrderStatus(data.getOrder_id(), data.getOrder_status(), data.getShipping_status(), data.getAddress_id(), "2");
                btnDefualt.setEnabled(false);

            }
        });
        //设置收货地址
        btnOuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivity, RecieveAddressActivity.class);
                intent.putExtra("order_id", data.getOrder_id());
                intent.putExtra("order_status", data.getOrder_status());
                intent.putExtra("shipping_status", data.getShipping_status());
                intent.putExtra("name", "(第" + data.getPeriod_number() + "期) " + data.getGoods_name());
//                mActivity.startActivity(intent);
                mActivity.startActivityForResult(intent, 0);

            }
        });
        //监听收货
        btnInfo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(data.getOrder_id());
                btnInfo3.setEnabled(false);
            }
        });
        //监听晒单
        btnInfo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivity, ShowOrderActivity.class);
                intent.putExtra("NAME", "(第" + data.getPeriod_number() + "期) " + data.getGoods_name());
                intent.putExtra("NUM", data.getTotal_times());
                intent.putExtra("PRODUCTNUM", data.getLuck_code());
                intent.putExtra("TIME", data.getPre_luck_code_create_time());
                intent.putExtra("ORDER_ID", data.getOrder_id());
                intent.putExtra("GOODS_ID", data.getGoods_id());
                mActivity.startActivity(intent);
            }
        });
    }

    private Dialog dialog;

    private void setDialog(final String orderid) {
        View v = mActivity.getLayoutInflater().inflate(R.layout.winner_dialog_stay_layout, null);
        dialog = DialogUtils.createDialog(mActivity, v, 340, 195);

        v.findViewById(R.id.winner_dialog_stay_layout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateStatusStayStack.getUpdateStatus(orderid, "2");
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                btnInfo3.setEnabled(true);
            }
        });
    }

    /*兑奖信息返回*/
    public void onEventMainThread(HttpWinUpdateOrderStatusEvent addressListEvent) {
        if (addressListEvent.getGetVerifyCodeBean() == null) {
            Toast.makeText(mActivity, "网络链接出错，请检查网络", Toast.LENGTH_LONG).show();
        } else {
            if (addressListEvent.getGetVerifyCodeBean().getCode().equals("1")) {
                print(addressListEvent.getGetVerifyCodeBean().getMsg());
                MyApplication.is_Winner_refreash = true;
                waitLoadingUtils.show();
                model.getWinDetailData(period);
                //刷新Tab的number
                EventBus.getDefault().post("winner");
                //弹出对话框，选择是否分享
                DialogLottery.createLunckyDialog(mActivity, data.getGoods_name(), "2");
            } else {
                print(addressListEvent.getGetVerifyCodeBean().getMsg());
            }
        }
        btnDefualt.setEnabled(true);
    }

    //确认收货
    public void onEventMainThread(HttpStayStocksEvent httpStayStocksEvent) {
        if (httpStayStocksEvent.getGetVerifyCodeBean() == null) {
            print("网络链接出错，请检查");
        } else {
            if (httpStayStocksEvent.getGetVerifyCodeBean().getCode().equals("1")) {
                waitLoadingUtils.show();
                model.getWinDetailData(period);
                EventBus.getDefault().post("winner");
            }
        }
        btnInfo3.setEnabled(true);
    }

    /*
    * 回调地址参数（本地操作）
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataT) {
        super.onActivityResult(requestCode, resultCode, dataT);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            data.setAddress_id(dataT.getStringExtra("address_id"));
            tvAddressDetail0.setText(dataT.getStringExtra("District"));
            tvAddressName0.setText(dataT.getStringExtra("Consignee") + dataT.getStringExtra("Mobile"));
            btnDefualt.setVisibility(View.VISIBLE);
        }
    }

    //确认收货 返回信息
    public void onEventMainThread(String sre) {
        if (sre.equals("winnerDetail")) {
            waitLoadingUtils.show();
            model.getWinDetailData(period);
            EventBus.getDefault().post("winner");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
