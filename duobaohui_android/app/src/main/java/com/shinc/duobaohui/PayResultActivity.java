package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.adapter.BuyNumTwoLineAdapter;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.AddOrderBean;
import com.shinc.duobaohui.bean.PayResultBean;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.event.HttpPayResultEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.http.PayResultRequestImpl;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.WaitLoadingUtils;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * 名称：PayResultActivity
 * 作者：zhaopl 时间: 15/10/14.
 * 实现的主要功能：
 * <p/>
 * 支付结果
 */
public class PayResultActivity extends BaseActivity {


    private Activity mActivity;

    private TextView takePartTitle;

    private TextView takePartInfo;

    private TextView takePartNum;

    private GridView takePartCode;

    private TextView continueDuobao;
    private TextView gotoRecord;
    private BuyNumTwoLineAdapter buyNumTwoLineAdapter;
    private AddOrderBean.Order order;

    private RelativeLayout payUserInfoLayout;
    WaitLoadingUtils waitLoadingUtils;

    private ImageView icon;
    private TextView msgTitle;
    private TextView msgContent;
    private int requestNum = 0;
    private String payType = "";
    private String recharge_channel;
    private String jnlNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        mActivity = this;
        EventBus.getDefault().register(this);
        initView();
        waitLoadingUtils = new WaitLoadingUtils(this);
        waitLoadingUtils.show();
        if (getIntent() != null && getIntent().getExtras() != null) {
            order = (AddOrderBean.Order) getIntent().getExtras().get("PRODUCTDETAIL");//得到数据的操作；
            jnlNo = order.getJnl_no();
            payType = (String) getIntent().getExtras().get("type");
            recharge_channel = (String) getIntent().getExtras().get("recharge_channel");
            initData();
        }
        initEvent();
    }

    private void initView() {

        takePartTitle = (TextView) findViewById(R.id.user_info_title);
        takePartInfo = (TextView) findViewById(R.id.take_part_info);
        takePartNum = (TextView) findViewById(R.id.take_part_num);
        takePartCode = (GridView) findViewById(R.id.user_get_code);
        continueDuobao = (TextView) findViewById(R.id.continue_duobao);
        gotoRecord = (TextView) findViewById(R.id.goto_duobao_record);
        payUserInfoLayout = (RelativeLayout) findViewById(R.id.pay_user_info);
        icon = (ImageView) findViewById(R.id.icon_pay_ok);
        msgTitle = (TextView) findViewById(R.id.pay_ok_tv);
        msgContent = (TextView) findViewById(R.id.pay_ok_tv2);
    }

    private void initEvent() {

        continueDuobao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转回首页；

            }
        });

        gotoRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到夺宝记录中去；
                //   Intent intent = new Intent(mActivity, DuoBaoRecrodActivity.class);
                Intent intent = new Intent(mActivity, DuoBaoActivity.class);
                startActivity(intent);
            }
        });

        continueDuobao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 调到首页；
                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                EventBus.getDefault().post(new UtilsEvent("main"));
            }
        });
    }

    private void initData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("buy_id", order.getJnl_no());
        requestParams.addBodyParameter("pay_type", payType);
        requestParams.addBodyParameter("recharge_channel", recharge_channel);
        httpUtils.sendHttpPost(requestParams, ConstantApi.PAY_RESULT, new PayResultRequestImpl(), mActivity);
    }

    /**
     * 接收历史数据的操作；
     *
     * @param httpPayResultEvent
     */
    public void onEventMainThread(HttpPayResultEvent httpPayResultEvent) {
        waitLoadingUtils.disable();
        if (httpPayResultEvent != null) {

            PayResultBean payResultBean = httpPayResultEvent.getPayResultBean();
            if ("1".equals(payResultBean.getCode())) {
                if (payResultBean.getData() != null) {
                    payUserInfoLayout.setVisibility(View.VISIBLE);
                    takePartNum.setText(payResultBean.getData().getCodeTotalNum() + "人次");//设置参与次数；
                    takePartInfo.setText("(第" + order.getPeriod_number() + "期)" + " " + order.getGoods_name());
                    takePartTitle.setText("您成功参与了1件商品共" + payResultBean.getData().getCodeTotalNum() + "人次夺宝，信息如下：");
                    initBuyNumTwoLine(payResultBean.getData().getCodeList(), order);
                } else {
                    //todo 没有获取到用户参与的信息，进行友好提示；
                    payUserInfoLayout.setVisibility(View.GONE);
                    msgTitle.setText("交易未知");
                    msgContent.setText("暂时没有获取到购买信息，请去夺宝记录进行查看！");
                }
            } else if ("4002".equals(payResultBean.getCode()) || "4001".equals(payResultBean.getCode())) {
                payUserInfoLayout.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.icon_payfail);
                msgTitle.setText("交易出错");
                msgContent.setText("我们会在确认完支付记录后，将款项退回您的支付账户(预计24小时内)");
            } else if ("4003".equals(payResultBean.getCode()) || "4004".equals(payResultBean.getCode())) {
                payUserInfoLayout.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.icon_payfail);
                msgTitle.setText("交易失败");
                msgContent.setText("没有足够的剩余人次了，所支付的金额已经充入您的账户");
            } else if ("4006".equals(payResultBean.getCode()) || "4007".equals(payResultBean.getCode())) {
                //todo 等待分配夺宝号的时候，进行三次请求，如果三次请求都是这个的时候，就会展示交易未名页面；
                //进行刷新的操作；
                requestNum++;
                if (requestNum >= 3) {
                    //展示交易未名页面；
                    payUserInfoLayout.setVisibility(View.GONE);
                    msgTitle.setText("等待交易结果");
                    icon.setImageResource(R.drawable.icon_payunknown);
                    msgContent.setText("暂时没有查到您的交易结果，请关注您的夺宝记录或交易记录");
                    continueDuobao.setText("刷新");
                    continueDuobao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestNum = 0;
                            initData();
                            waitLoadingUtils.show();
                        }
                    });
                    gotoRecord.setText("返回首页");
                    gotoRecord.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            EventBus.getDefault().post(new UtilsEvent("main"));
                        }
                    });
                } else {

                    initData();
                }
            }
            //如果是重复请求的时候，在小于3次的时候，不让等待页面消失；
            if ("4006".equals(payResultBean.getCode()) || "4007".equals(payResultBean.getCode())) {
                if (requestNum >= 3) {
                    waitLoadingUtils.disable();
                }
            } else {
                waitLoadingUtils.disable();

            }
        } else {
            waitLoadingUtils.disable();
        }

    }

    /**
     * 设置用户的参与时的夺宝号展示；
     *
     * @param list
     * @param order
     */

    private void initBuyNumTwoLine(final ArrayList<String> list, final AddOrderBean.Order order) {

        buyNumTwoLineAdapter = new BuyNumTwoLineAdapter(this, list, takePartCode);
        takePartCode.setAdapter(buyNumTwoLineAdapter);

        if (list != null && list.size() > 7) {
            takePartCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //点击列表的数据；
                    if (position == list.size() - 1) {
                        //todo 跳转到新的页面进行操作；
                        Intent intent = new Intent(mActivity, UserDuoBaoCodeActivity.class);
                        intent.putExtra("period_id", order.getPeriod_id());
                        startActivity(intent);
                    }
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        requestNum = 0;
    }
}
