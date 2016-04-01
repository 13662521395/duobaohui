package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.bean.AddOrderBean;
import com.shinc.duobaohui.bean.PayStatusBean;
import com.shinc.duobaohui.bean.WxPayBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.dialog.DialogLottery;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.customview.dialog.PayLoadingDialog;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.event.BaseRespPayEvent;
import com.shinc.duobaohui.event.DuoBaiBiEvent;
import com.shinc.duobaohui.event.PayStatusEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.event.WxPayEvent;
import com.shinc.duobaohui.http.GetPayStatusRequestImpl;
import com.shinc.duobaohui.http.WxChargeRequestImpl;
import com.shinc.duobaohui.paylibrary.model.impl.PayModel;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.VerifyUtils;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/11/3.
 * 支付订单
 */
public class PayFastionOrderActivity extends BaseActivity {

    private Activity mActivity;

    private ImageView imgBack;

    private CustomTextView commitBtn;

    private RelativeLayout biPayLayout;//夺宝币付款layout;
    private RelativeLayout aliPayLayout;//支付宝付款layout;
    private RelativeLayout wxPayLayout;//微信付款layout;
    private RelativeLayout redEnevlopeLayout;//红包

    private LinearLayout linearPayType;


    private CheckBox biCheckBox;//夺宝币选择框
//    private CheckBox redCheckBox;//是否用红包

    private CheckBox aliCheckBox;//支付宝选择框；
    private CheckBox wxCheckBox;//微信选择框；

    private TextView biLastNum;//夺宝币的剩余数量；
    private TextView biPayNum;//夺宝币应该支付的金额；
    private TextView redEnevlopeNum;//可用的红包数量

    private TextView redMoeny;//红包的金额

    /*1代表使用夺宝币，2代表使用支付宝，3代表使用微信*/
    private int payType = 0;//支付方式

    private SharedPreferencesUtils spUtils;

    private AddOrderBean.Order order;

    private String num;

    private PayModel payModelnterface;

    private String periodId;

    private TextView productName;

    private TextView takePartNum;

    private TextView payNum;

    private String balance;

    private CustomTextView totalPay;//总支付

    private CustomTextView biPayCut;//夺宝币默认支付 最大额

    private CustomTextView cutBiPay;//还需支付


    private LoadingDialog loadingDialog1;

    private PayLoadingDialog payLoadingDialog;

    private boolean biFlag = false;

    private IWXAPI api;

    private int needPay = 0;


    private WxPayBean wxPayBean;

    private int redEnevlopeNumber = 0;

    /*避免重复＋||－红包的金额 设置了两个变量*/
    //判断是否减去支付金额
    private boolean isIfRedEnevlope = true;
    //判断是否加上支付金额
    private boolean isElseRedEnevlope = false;

    private String redpacket_id;

    private boolean isRedPacket = false;


    private String totalNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_fashion_layout);
        EventBus.getDefault().register(this);
        this.mActivity = this;
        api = WXAPIFactory.createWXAPI(mActivity, null);
        getExtraData();
        initView();
        getPayStatus();
        initListener();
    }

    /**
     * 获取传递过来的信息；
     */
    private void getExtraData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            order = (AddOrderBean.Order) getIntent().getSerializableExtra("ORDERINFO");
            num = getIntent().getStringExtra("NUM");
            periodId = getIntent().getStringExtra("PEROIDID");
            //todo   统计触发事件；
        }
    }

    /**
     * 初始化控件；
     */
    private void initView() {

        loadingDialog1 = new LoadingDialog(mActivity, R.style.dialog);

        payLoadingDialog = new PayLoadingDialog(mActivity, R.style.dialog);
        imgBack = (ImageView) findViewById(R.id.pay_layout_back);
        commitBtn = (CustomTextView) findViewById(R.id.pay_fashion_commit_btn);
        biPayLayout = (RelativeLayout) findViewById(R.id.bi_pay_rl);
        biCheckBox = (CheckBox) findViewById(R.id.bi_check_box);
        aliPayLayout = (RelativeLayout) findViewById(R.id.alipay_rl);
        aliCheckBox = (CheckBox) findViewById(R.id.ali_check_box);
        wxPayLayout = (RelativeLayout) findViewById(R.id.wx_rl);
        wxCheckBox = (CheckBox) findViewById(R.id.weixin_check_box);
        biLastNum = (TextView) findViewById(R.id.bi_last_num);
        biPayNum = (TextView) findViewById(R.id.bi_pay_num);
        productName = (TextView) findViewById(R.id.product_name_tv);
        takePartNum = (TextView) findViewById(R.id.total_take_part_num_tv);
        payNum = (TextView) findViewById(R.id.total_pay_num_tv);
        totalPay = (CustomTextView) findViewById(R.id.pay_fashion_pay_total);
        biPayCut = (CustomTextView) findViewById(R.id.bi_last_paynum);
        cutBiPay = (CustomTextView) findViewById(R.id.pay_fashion_pay_cutNumber);
        linearPayType = (LinearLayout) findViewById(R.id.pay_faston_patType);
        redEnevlopeLayout = (RelativeLayout) findViewById(R.id.bi_pay_layout_red_envelope);
        redEnevlopeNum = (TextView) findViewById(R.id.red_envelope_last_num);
        redMoeny = (TextView) findViewById(R.id.red_envelope_last_paynum);
        //todo 余额支付默认选中；

        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
        payModelnterface = new PayModel(mActivity);
        payType = 2;
        if (order != null) {
            totalNum = num;
            productName.setText("(第" + order.getPeriod_id() + "期)" + order.getGoods_name());
            totalPay.setText(num + "元");
            takePartNum.setText(num + "人次");
            payNum.setText("￥" + num + ".00");
        }
        if (order.getRedpackets() == null || order.getRedpackets().equals("0")) {
            redEnevlopeNum.setText("(无红包可用)");
        } else {
            redEnevlopeNum.setText("(" + order.getRedpackets() + "个红包可用)");
        }
    }

    /**
     * 初始化数据；
     */
    private void initData() {
        balance = spUtils.get(Constant.MONEY, "");
        //判断红包是否被选中，若是选中，则将支付金额－红包金额
        biLastNum.setText("(账户余额：" + balance + "元)");
        if (("").equals(balance) || ("0").equals(balance)) {
            //证明用户余额为0；
            /*判断余额是否为0，为0，则支付宝支付，默认未选中且不能点击，只能第三方支付*/
            payType = 2;
            biPayLayout.setEnabled(false);
            biCheckBox.setChecked(false);
            biCheckBox.setEnabled(false);
            aliCheckBox.setChecked(true);
            biPayCut.setText("0");
            needPay = Integer.parseInt(num);
            cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + num + "</font>元"));
        } else {
            /*判断余额是否 大于或等于 购买的金额*/
            if (Integer.valueOf(balance) - Integer.valueOf(num) >= 0) {
                /*余额大于订单金额，则默认选择夺宝币支付*/
                payType = 1;//代表夺宝币；
                biFlag = true;
                biPayLayout.setEnabled(true);
                biCheckBox.setChecked(true);
                biPayCut.setText("-" + num);
                cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + 0 + "</font>元"));
            } else {
                /*余额小于订单金额，可选择第三方支付，默认支付宝*/
//                payType = 2;//代表使用支付宝；

                biPayLayout.setEnabled(true);
                biCheckBox.setEnabled(true);
                biCheckBox.setChecked(true);
                ifBiCheckout();
                biPayCut.setText("-" + balance);
                needPay = (Integer.valueOf(num) - Integer.valueOf(balance));
                cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + (Integer.valueOf(num) - Integer.valueOf(balance)) + "</font>元"));
            }
        }

    }

    /**
     * 获取支付条件的显示；
     */
    private void getPayStatus() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        httpUtils.sendHttpPost(requestParams, ConstantApi.SWITCH_PAY, new GetPayStatusRequestImpl(), mActivity);
        loadingDialog1.showLoading();
    }


    public void onEventMainThread(PayStatusEvent event) {
        if (event.getPayStatusBean() == null) {
//            print("网络链接错误，请检查网络");
            //noWeb();
        } else {
            if ("1".equals(event.getPayStatusBean().getCode())) {
                PayStatusBean.PayStatus payStatus = event.getPayStatusBean().getData();

                if ("true".equals(payStatus.getAlipay_show())) {
                    aliPayLayout.setVisibility(View.VISIBLE);

                } else {
                    aliPayLayout.setVisibility(View.GONE);
                    if (payType == 2) {
                        aliCheckBox.setChecked(false);
                        payType = 0;
                    }
                }

                if ("true".equals(payStatus.getWeixinpay_show())) {
                    wxPayLayout.setVisibility(View.VISIBLE);
                } else {
                    wxPayLayout.setVisibility(View.GONE);
                    if (payType == 3) {
                        wxCheckBox.setChecked(false);
                        payType = 0;
                    }
                }
            }
        }
        initData();

    }

    /**
     * 初始化事件监听；
     */
    private void initListener() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 返回按钮，关闭该activity；
                finish();
            }
        });

        payModelnterface.setAlipayResultListener(new PayModel.AlipayResultListener() {
            @Override
            public void result() {
                commitBtn.setEnabled(true);
            }
        });

        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 提交订单的操作；
                String userId = spUtils.get(Constant.SP_USER_ID, "");
                if (TextUtils.isEmpty(userId)) {
                    Intent intent = new Intent(mActivity, FastLoginActivity.class);
                    startActivity(intent);
                } else {
                    switch (payType) {
                        case 1:
                            if (Float.compare(Float.parseFloat(num), Float.parseFloat(balance)) > 0) {
                                Toast.makeText(mActivity, "余额不足，请使用其它支付方式！", Toast.LENGTH_SHORT).show();
                            } else {
                                //todo 余额支付；
                                commitBtn.setEnabled(false);
                                payLoadingDialog.startShow();
                                payModelnterface.getDuoBaoBi(order.getJnl_no(), redpacket_id);
                            }
                            break;
                        case 2:
                            //todo 支付宝支付；
                            loadingDialog1.showLoading();
                            payModelnterface.payCommit(Constant.DIRECTBUY, needPay + "", periodId, order.getGoods_name(), userId, order, redpacket_id);
                            commitBtn.setEnabled(false);
                            break;
                        case 3:
                            //todo 微信支付；
                            if (VerifyUtils.isWXAppInstalledAndSupported(mActivity, api)) {
                                commitBtn.setEnabled(false);
                                loadingDialog1.showLoading();
                                HttpUtils httpUtils = new HttpUtils();
                                RequestParams params = new RequestParams();
                                params.addBodyParameter("user_id", spUtils.get(Constant.SP_USER_ID, ""));
                                params.addBodyParameter("period_id", periodId);
                                params.addBodyParameter("total_fee", needPay + "");
                                params.addBodyParameter("out_trade_no", order.getJnl_no());
                                params.addBodyParameter("goods_name", "夺宝会支付确认");
                                params.addBodyParameter("redpacket_id", redpacket_id);
                                httpUtils.sendHttpPost(params, ConstantApi.WXInfoOrder, new WxChargeRequestImpl(Constant.WXPAY), mActivity);
                            } else {
                                commitBtn.setEnabled(true);
                            }
                            break;
                        default:
                            Toast.makeText(mActivity, "请选择支付方式", Toast.LENGTH_SHORT).show();
                            commitBtn.setEnabled(true);
                            break;
                    }
                }
            }
        });

        biCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO 是否使用夺宝币
                if (arg1) {
                    initRedEnevlopeBiCheckboxTrue();
                    initBiCheckboxTrue();
                } else {
                    initRedEnevlopeBiCheckboxTrue();
                    initBiCheckboxFalse();
                }
            }
        });
//
//        redCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO 是否使用红包
//                if (arg1) {
//                    initRedEnevlopeTrue();
//                } else {
//                    initReEnevlopeFlase();
//                }
//            }
//        });

        redEnevlopeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 红包列表
                loadingDialog1.showLoading();
                if (order.getRedpackets() == null || order.getRedpackets().equals("0")) {
                    loadingDialog1.hideLoading();
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, RedEnevlopeDialogActivity.class);
                    intent.putExtra("activityId", order.getSh_activity_id());
                    intent.putExtra("NUM", getIntent().getStringExtra("NUM"));
                    mActivity.startActivityForResult(intent, 0);
                }
            }
        });

        biPayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击选中夺宝币支付；
                if (biCheckBox.isChecked()) {
                    biCheckBox.setChecked(false);
                } else {
                    biCheckBox.setChecked(true);
                }
            }
        });

        aliPayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击选中支付宝支付；
                aliCheckBox.setChecked(true);
                wxCheckBox.setChecked(false);
                if (Integer.valueOf(balance) - Integer.valueOf(num) == 0) {
                    biCheckBox.setChecked(false);
                }
                if (biFlag) {
                    biCheckBox.setChecked(false);
                }
                payType = 2;
            }
        });

        wxPayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliCheckBox.setChecked(false);
                wxCheckBox.setChecked(true);
                if (Integer.valueOf(balance) - Integer.valueOf(num) == 0) {
                    biCheckBox.setChecked(false);
                }
                if (biFlag) {
                    biCheckBox.setChecked(false);
                }
                payType = 3;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        commitBtn.setEnabled(true);
        if (loadingDialog1 != null) {
            loadingDialog1.hideLoading();
        }
    }

    /**
     * 夺宝币支付的结果接口；
     *
     * @param duoBaiBiEvent
     */

    public void onEventMainThread(DuoBaiBiEvent duoBaiBiEvent) {
        if (duoBaiBiEvent.getGetVerifyCodeBean() == null) {
            print("网络链接错误，请检查网络");
        } else {
            if (duoBaiBiEvent.getGetVerifyCodeBean().getCode().equals("1")) {
                //成功后，设置buy_id的数值；PayResultActivity 中时需要这个东西；
                Intent intent = new Intent(mActivity, PayResultActivity.class);
                intent.putExtra("PRODUCTDETAIL", order);
                intent.putExtra("type", "0");
                startActivity(intent);
                if (!TextUtils.isEmpty(duoBaiBiEvent.getGetVerifyCodeBean().getData().getMoney())) {
                    spUtils.add(Constant.MONEY, duoBaiBiEvent.getGetVerifyCodeBean().getData().getMoney());
                }
                EventBus.getDefault().post(new UtilsEvent("RELOAD_BALANCE"));
                EventBus.getDefault().post(new UtilsEvent("REFRESH"));
                finish();
            } else {
                print(duoBaiBiEvent.getGetVerifyCodeBean().getData().getMsg());
            }
        }
        commitBtn.setEnabled(true);
        if (payLoadingDialog != null) {
            payLoadingDialog.endHide();
        }
    }

    public void onEventMainThread(WxPayEvent wxPayEvent) {

        if (wxPayEvent == null) {
            print("网络链接出错，请检查网络");
        } else {
            if (wxPayEvent.getWxPayBean() != null) {
                if (wxPayEvent.getWxPayBean().getCode().equals("1")) {
                    wxPayBean = wxPayEvent.getWxPayBean();
                    payModelnterface.getWxPay(mActivity, 1, wxPayEvent.getWxPayBean().getData());
                    MyApplication.WX_TYPE = Constant.WXPAY;
                }
            }
        }
        commitBtn.setEnabled(true);
    }

    /*微信支付后返回*/
    public void onEventMainThread(BaseRespPayEvent baseRespRechargeEvent) {
        if (baseRespRechargeEvent.getBaseResp().getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseRespRechargeEvent.getBaseResp().errCode == BaseResp.ErrCode.ERR_OK) {
                //成功后，设置buy_id的数值；跳转到payresultactivity 中时需要这个东西；
                Intent intent = new Intent(mActivity, PayResultActivity.class);
                intent.putExtra("PRODUCTID", periodId);
                intent.putExtra("PRODUCTDETAIL", order);
                intent.putExtra("type", "1");
                intent.putExtra("recharge_channel", "1");
                mActivity.startActivity(intent);
                EventBus.getDefault().post(new UtilsEvent("LOAD_INFO"));
                EventBus.getDefault().post(new UtilsEvent("REFRESH"));
                finish();
            } else {
                DialogLottery.createFinishPayPageDialog(mActivity, new DialogLottery.DialogOnListener() {
                    @Override
                    public void onClick(View view) {
                        payModelnterface.getWxPay(mActivity, 1, wxPayBean.getData());
                        MyApplication.WX_TYPE = Constant.WXPAY;
                    }
                });
            }
        }
    }

    /*
      * 回调地址参数（本地操作）红包返回
      * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataT) {
        super.onActivityResult(requestCode, resultCode, dataT);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String redEnevlope = dataT.getStringExtra("redEnevlope");
            if (redEnevlope.equals("yes")) {
                redEnevlopeNumber = Integer.parseInt(dataT.getStringExtra("price").substring(0, 1));
                redpacket_id = dataT.getStringExtra("redpacket_id");
                isRedPacket = true;
                initRedEnevlopeTrue();
            } else {
                isRedPacket = false;
                initReEnevlopeFlase();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        payModelnterface.onDestroyHandler();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /*夺宝币，选中时的金额逻辑处理*/
    private void initBiCheckboxTrue() {
        if (biLastNum.getText().toString().equals("") || biLastNum.getText().toString().equals("0")) {
            //证明用户余额为0；
            payType = 2;
            biPayLayout.setEnabled(false);
            biCheckBox.setChecked(false);
            biCheckBox.setEnabled(false);
            aliCheckBox.setChecked(true);
            needPay = Integer.parseInt(num);
            cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + num + "</font>元"));
        } else {
            if (Integer.valueOf(balance) - Integer.valueOf(num) >= 0) {
                //余额大于订单金额；
                payType = 1;
                biPayLayout.setEnabled(true);
                biCheckBox.setChecked(true);
                aliCheckBox.setChecked(false);
                wxCheckBox.setChecked(false);
                biPayCut.setText("-" + num);
                needPay = 0;
                cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + 0 + "</font>元"));
            } else {
                ifBiCheckout();
                biPayLayout.setEnabled(true);
                biCheckBox.setEnabled(true);
                biPayCut.setText("-" + balance);
                needPay = (Integer.valueOf(num) - Integer.valueOf(balance));
                cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + (Integer.valueOf(num) - Integer.valueOf(balance)) + "</font>元"));
            }
        }
    }


    /*夺宝币，未选中时的金额逻辑处理*/
    private void initBiCheckboxFalse() {
        ifBiCheckout();
        biPayCut.setText("0");
        needPay = Integer.parseInt(num);
        cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + num + "</font>元"));
    }

    private void ifBiCheckout() {
        if (wxCheckBox.isChecked() && wxPayLayout.getVisibility() == View.VISIBLE) {
            payType = 3;
            aliCheckBox.setChecked(false);
            wxCheckBox.setChecked(true);

        } else {
            if (aliPayLayout.getVisibility() == View.VISIBLE) {
                payType = 2;
                aliCheckBox.setChecked(true);
                wxCheckBox.setChecked(false);
            } else if (wxPayLayout.getVisibility() == View.VISIBLE) {
                payType = 3;
                aliCheckBox.setChecked(false);
                wxCheckBox.setChecked(true);
            } else {
                payType = 0;
            }
        }
    }

    /*夺宝币，选中时，红包的逻辑处理*/
    private void initRedEnevlopeBiCheckboxTrue() {
        if (isRedPacket) {
            //判断红包被选中
            redMoeny.setText("-" + redEnevlopeNumber);
            if (isIfRedEnevlope) {
                //订单金额减去红包的金额
                num = (Integer.valueOf(num) - redEnevlopeNumber) + "";
                isIfRedEnevlope = false;
                isElseRedEnevlope = true;
            }
        } else {
            redMoeny.setText("");
            if (isElseRedEnevlope) {
                //判断红包被取消，把减去的红包金额在加上
                num = (Integer.valueOf(num) + redEnevlopeNumber) + "";
                isIfRedEnevlope = true;
                isElseRedEnevlope = false;
            }
        }
    }


    private void initRedEnevlopeTrue() {
        redMoeny.setText("-" + redEnevlopeNumber);
        if (isIfRedEnevlope) {
            num = (Integer.valueOf(num) - redEnevlopeNumber) + "";
            isIfRedEnevlope = false;
            isElseRedEnevlope = true;
        } else {
            num = totalNum;//获取总支付的订单金额
            num = (Integer.valueOf(num) - redEnevlopeNumber) + "";//获取总订单原先的金额后再减去红包金额
        }

        if (biLastNum.getText().toString().equals("") || biLastNum.getText().toString().equals("0")) {
            //证明用户余额为0；(夺宝币的剩余数量)
            payType = 2;
            needPay = Integer.parseInt(num);
            cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + num + "</font>元"));
        } else {
            initReEnevlopeBiFalse();
        }
    }

    private void initReEnevlopeFlase() {
        redMoeny.setText("");
        if (isElseRedEnevlope) {
            num = (Integer.valueOf(num) + redEnevlopeNumber) + "";
            isIfRedEnevlope = true;
            isElseRedEnevlope = false;
        }
        initReEnevlopeBiFalse();

    }

    private void initReEnevlopeBiFalse() {
        if (biCheckBox.isChecked()) {
            if (Integer.valueOf(balance) - Integer.valueOf(num) >= 0) {
                payType = 1;
                //余额大于订单金额；
                biPayCut.setText("-" + num);
                needPay = 0;
                cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + 0 + "</font>元"));
                ifRedPackCheckout();
            } else {

                biPayCut.setText("-" + balance);
                needPay = (Integer.valueOf(num) - Integer.valueOf(balance));
                cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<fo    nt color='red'>" + (Integer.valueOf(num) - Integer.valueOf(balance)) + "</font>元"));
                ifRedPackCheckout();
            }
        } else {
            biPayCut.setText("0");
            needPay = Integer.parseInt(num);
            cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + num + "</font>元"));
            ifRedPackCheckout();
        }
    }


    private void ifRedPackCheckout() {
        if (aliCheckBox.isChecked() && aliPayLayout.getVisibility() == View.VISIBLE) {
            aliCheckBox.setChecked(true);
            payType = 2;
        } else {
            aliCheckBox.setChecked(false);
        }
        if (wxCheckBox.isChecked() && wxPayLayout.getVisibility() == View.VISIBLE) {
            wxCheckBox.setChecked(true);
            payType = 3;
        } else {
            wxCheckBox.setChecked(false);
        }
    }

    private void initReEnevlopeBiTrue() {
        if (Integer.valueOf(balance) - Integer.valueOf(num) >= 0) {
            //余额大于订单金额；
            if (biCheckBox.isChecked()) {
                payType = 1;
                biPayCut.setText("-" + num);
                needPay = 0;
                cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + 0 + "</font>元"));
                aliCheckBox.setChecked(false);
                wxCheckBox.setChecked(false);
            } else {
                payType = 2;
                biPayCut.setText("0");
                needPay = Integer.parseInt(num);
                cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + num + "</font>元"));
                aliCheckBox.setChecked(true);
                wxCheckBox.setChecked(false);
            }
        } else {
            if (wxCheckBox.isChecked()) {
                payType = 3;
                aliCheckBox.setChecked(false);
                wxCheckBox.setChecked(true);
            } else {
                payType = 2;
                aliCheckBox.setChecked(true);
                wxCheckBox.setChecked(false);
            }
            biPayCut.setText("-" + balance);
            needPay = (Integer.valueOf(num) - Integer.valueOf(balance));
            cutBiPay.setText(Html.fromHtml("还需支付:&nbsp<font color='red'>" + (Integer.valueOf(num) - Integer.valueOf(balance)) + "</font>元"));
        }
    }
}
