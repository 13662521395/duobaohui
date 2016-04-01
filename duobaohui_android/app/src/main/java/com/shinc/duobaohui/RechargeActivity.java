package com.shinc.duobaohui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.bean.AddOrderBean;
import com.shinc.duobaohui.bean.RechargeImgBean;
import com.shinc.duobaohui.bean.PayStatusBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.customview.imp.DotsTextView;
import com.shinc.duobaohui.event.BaseRespRechargeEvent;
import com.shinc.duobaohui.event.PayStatusEvent;
import com.shinc.duobaohui.event.RechageOrderEvent;
import com.shinc.duobaohui.event.RechargeImgEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.event.WxRechargeEvent;
import com.shinc.duobaohui.http.GetPayStatusRequestImpl;
import com.shinc.duobaohui.http.GetRechageOrderRequestImpl;
import com.shinc.duobaohui.http.GetRechargeRequestImpl;
import com.shinc.duobaohui.http.WxChargeRequestImpl;
import com.shinc.duobaohui.paylibrary.model.impl.PayModel;
import com.shinc.duobaohui.utils.CodeVerifyUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.VerifyUtils;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/10/10.
 * 充值夺宝币
 */
public class RechargeActivity extends BaseActivity {

    private Activity mActivity;

    private ImageView imgBack;
    private CustomTextView customTextView20;
    private CustomTextView customTextView50;
    private CustomTextView customTextView100;
    private CustomTextView customTextView200;
    private CustomTextView customTextView500;
    private EditText customTextView;

    private LinearLayout alipayLinear;
    private CheckBox alipayCheckBox;
    private LinearLayout bankCradLinear;
    private CheckBox bankCradCheckBox;
    private LinearLayout WxLinear;
    private CheckBox WxCheckBox;

    private CustomTextView confrimRecharge;
    private int moenyNumber = 0;
    private int payType;
    private PayModel payMopdel;
    SharedPreferencesUtils spUtils;
    private ProgressDialog dialog;
    private LoadingDialog loadingDialog;
    private InputMethodManager imm;
    private RelativeLayout relativeLayout;
    private IWXAPI api;
    private ImageView rechargeImg;
    private DotsTextView loadingDots;
    private TextView info;
    private TextView info1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = RechargeActivity.this;
        setContentView(R.layout.recharge_layout);
        getWindow().setBackgroundDrawable(null);
        EventBus.getDefault().register(this);
        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);

        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        //默认充值金额 20
        if (TextUtils.isEmpty(getIntent().getStringExtra("total_fee"))) {
            moenyNumber = 20;
        } else {
            moenyNumber = Integer.parseInt(getIntent().getStringExtra("total_fee"));
        }

        api = WXAPIFactory.createWXAPI(mActivity, null);
        initView();
        intitModel();
        initFeeClooos();
        initListener();
    }

    private void intitModel() {
        payMopdel = new PayModel(mActivity);
        getPayStatus();
        getRechargeImg();

    }

    private void getRechargeImg() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();

        httpUtils.sendHttpPost(requestParams, ConstantApi.RECHARGE_IMG, new GetRechargeRequestImpl(), mActivity);

    }

    private void getPayStatus() {

        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        httpUtils.sendHttpPost(requestParams, ConstantApi.SWITCH_PAY, new GetPayStatusRequestImpl(), mActivity);
    }

    public void onEventMainThread(PayStatusEvent event) {
        if (event.getPayStatusBean() == null) {
//            print("网络链接错误，请检查网络");
            //noWeb();
        } else {
            if ("1".equals(event.getPayStatusBean().getCode())) {
                PayStatusBean.PayStatus payStatus = event.getPayStatusBean().getData();

                if ("true".equals(payStatus.getAlipay_show())) {
                    alipayLinear.setVisibility(View.VISIBLE);

                } else {
                    alipayLinear.setVisibility(View.GONE);
                    if(payType==Constant.ALIPAY){
                        payType=4;
                        alipayCheckBox.setChecked(false);
                    }

                }

                if ("true".equals(payStatus.getWeixinpay_show())) {
                    WxLinear.setVisibility(View.VISIBLE);
                } else {
                    WxLinear.setVisibility(View.GONE);
                    if(payType==Constant.WEIXIN){
                        payType = 4;
                        WxCheckBox.setChecked(false);
                    }

                }
            }
        }
    }

    private void initView() {

        relativeLayout = (RelativeLayout) findViewById(R.id.recharge_inputSoftKey);

        loadingDialog = new LoadingDialog(mActivity, R.style.dialog);
        imgBack = (ImageView) findViewById(R.id.recharge_layout_back);
        customTextView20 = (CustomTextView) findViewById(R.id.recharge_layout_customTextView20);
        customTextView50 = (CustomTextView) findViewById(R.id.recharge_layout_customTextView50);
        customTextView100 = (CustomTextView) findViewById(R.id.recharge_layout_customTextView100);
        customTextView200 = (CustomTextView) findViewById(R.id.recharge_layout_customTextView200);
        customTextView500 = (CustomTextView) findViewById(R.id.recharge_layout_customTextView500);
        customTextView = (EditText) findViewById(R.id.recharge_layout_customTextView);
        alipayLinear = (LinearLayout) findViewById(R.id.recharge_layout_alipayLinearLayout);
        alipayCheckBox = (CheckBox) findViewById(R.id.recharge_layout_alipay);
        bankCradLinear = (LinearLayout) findViewById(R.id.recharge_layout_BankCardLinearLayout);
        bankCradCheckBox = (CheckBox) findViewById(R.id.recharge_layout_collect_BankCard);
        WxLinear = (LinearLayout) findViewById(R.id.recharge_layout_WXLinearLayout);
        WxCheckBox = (CheckBox) findViewById(R.id.recharge_layout_collect_WXCard);

        alipayCheckBox.setChecked(true);
        bankCradCheckBox.setChecked(false);
        WxCheckBox.setChecked(false);
        confrimRecharge = (CustomTextView) findViewById(R.id.recharge_layout_confrimRecharge);

        loadingDots = (DotsTextView) findViewById(R.id.loading_dots);
        rechargeImg = (ImageView) findViewById(R.id.recharge_Img);
        info = (TextView) findViewById(R.id.recharge_layout_tvInfo);
        info1 = (TextView) findViewById(R.id.recharge_layout_tvInfo1);
        loadingDots.start();
    }

    private void initFeeClooos() {
        if (moenyNumber == 20) {

            customTextView20.setBackgroundResource(R.drawable.bg_edittext_focused);
            customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));

            customTextView50.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView100.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView200.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView500.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
        } else if (moenyNumber == 50) {

            customTextView20.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView50.setBackgroundResource(R.drawable.bg_edittext_focused);
            customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));

            customTextView100.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView200.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView500.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
        } else if (moenyNumber == 100) {

            customTextView20.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView50.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView100.setBackgroundResource(R.drawable.bg_edittext_focused);
            customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));

            customTextView200.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView500.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
        } else if (moenyNumber == 200) {

            customTextView20.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView50.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView100.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView200.setBackgroundResource(R.drawable.bg_edittext_focused);
            customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));

            customTextView500.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
        } else if (moenyNumber == 500) {

            customTextView20.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView50.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView100.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView200.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView500.setBackgroundResource(R.drawable.bg_edittext_focused);
            customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));

            customTextView.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
        } else {
            customTextView20.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView50.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView100.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView200.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView500.setBackgroundResource(R.drawable.bg_edittext_normal);
            customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

            customTextView.setBackgroundResource(R.drawable.bg_edittext_focused);
            customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
            //  customTextView.setText(moenyNumber + "");
        }

    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(customTextView.getWindowToken(), 0);
                finish();
            }
        });
        payMopdel.setAlipayResultListener(new PayModel.AlipayResultListener() {
            @Override
            public void result() {
                confrimRecharge.setEnabled(true);
                customTextView.clearFocus();
                //关闭的时候，禁掉键盘；

            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputSoftHeid(v);
            }
        });

        customTextView20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputSoftHeid(v);
                moenyNumber = 20;
                customTextView20.setBackgroundResource(R.drawable.bg_edittext_focused);
                customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));

                customTextView50.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView100.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView200.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView500.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
            }
        });

        customTextView50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputSoftHeid(v);
                moenyNumber = 50;
                customTextView20.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView50.setBackgroundResource(R.drawable.bg_edittext_focused);
                customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));

                customTextView100.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView200.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView500.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
            }
        });

        customTextView100.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputSoftHeid(v);
                moenyNumber = 100;
                customTextView20.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView50.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView100.setBackgroundResource(R.drawable.bg_edittext_focused);
                customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));

                customTextView200.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView500.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
            }
        });

        customTextView200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputSoftHeid(v);
                moenyNumber = 200;

                customTextView20.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView50.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView100.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView200.setBackgroundResource(R.drawable.bg_edittext_focused);
                customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));

                customTextView500.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
            }
        });

        customTextView500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moenyNumber = 500;
                inputSoftHeid(v);

                customTextView20.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView50.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView100.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView200.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView500.setBackgroundResource(R.drawable.bg_edittext_focused);
                customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));

                customTextView.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_666666));
            }
        });

        customTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                moenyNumber = 0;
                if (!customTextView.getText().toString().equals("")) {
                    moenyNumber = Integer.valueOf(customTextView.getText().toString());
                }
                customTextView20.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView20.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView50.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView50.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView100.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView100.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView200.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView200.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView500.setBackgroundResource(R.drawable.bg_edittext_normal);
                customTextView500.setTextColor(mActivity.getResources().getColor(R.color.c_666666));

                customTextView.setBackgroundResource(R.drawable.bg_edittext_focused);
                customTextView.setTextColor(mActivity.getResources().getColor(R.color.c_ff5a5a));
            }
        });
        customTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!customTextView.getText().toString().equals("")) {
                    if (Integer.valueOf(customTextView.getText().toString()) > 2000) {
                        moenyNumber = 2000;
                        customTextView.setText(moenyNumber + "");
                    } else {
                        moenyNumber = Integer.valueOf(customTextView.getText().toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        alipayLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = Constant.ALIPAY;
                alipayCheckBox.setChecked(true);
                bankCradCheckBox.setChecked(false);
                WxCheckBox.setChecked(false);
            }
        });
        alipayCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = Constant.ALIPAY;

                alipayCheckBox.setChecked(true);
                bankCradCheckBox.setChecked(false);
                WxCheckBox.setChecked(false);
            }
        });
        bankCradLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = Constant.BANKCARD;

                alipayCheckBox.setChecked(false);
                bankCradCheckBox.setChecked(true);
                WxCheckBox.setChecked(false);
            }
        });
        bankCradCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = Constant.BANKCARD;
                alipayCheckBox.setChecked(false);
                bankCradCheckBox.setChecked(true);
                WxCheckBox.setChecked(false);
            }
        });
        WxLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = Constant.WEIXIN;
                alipayCheckBox.setChecked(false);
                bankCradCheckBox.setChecked(false);
                WxCheckBox.setChecked(true);
            }
        });
        WxCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = Constant.WEIXIN;
                alipayCheckBox.setChecked(false);
                bankCradCheckBox.setChecked(false);
                WxCheckBox.setChecked(true);
            }
        });

        confrimRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confrimRecharge.setEnabled(false);
                if (moenyNumber == 0) {
                    print("金额不能为0");
                    confrimRecharge.setEnabled(true);
                } else if (moenyNumber > 2000) {
                    print("充值最大金额不能超过2000");
                    confrimRecharge.setEnabled(true);
                } else {

                    HttpUtils httpUtils = new HttpUtils();
                    RequestParams requestParams = new RequestParams();
                    requestParams.addBodyParameter("amount", moenyNumber + "");
                    switch (payType) {
                        case Constant.ALIPAY:
                            //支付宝
                            requestParams.addBodyParameter("recharge_channel", "0");
                            loadingDialog.showLoading();
                            httpUtils.sendHttpPost(requestParams, ConstantApi.GET_RECHAGE_ORDER, new GetRechageOrderRequestImpl(), mActivity);
                            break;
                        case Constant.WEIXIN:
                            //微信
                            requestParams.addBodyParameter("recharge_channel", "1");
                            loadingDialog.showLoading();
                            httpUtils.sendHttpPost(requestParams, ConstantApi.GET_RECHAGE_ORDER, new GetRechageOrderRequestImpl(), mActivity);
                            break;
                        case Constant.BANKCARD:
                            //银行卡
                            print("暂未开放");
                            confrimRecharge.setEnabled(true);
                            break;
                        default:
                            print("请选择支付方式");
                            confrimRecharge.setEnabled(true);
                            break;
                    }

                }
            }
        });
    }

    /**
     * 获取订单数据；
     *
     * @param rechageOrderEvent
     */
    public void onEventMainThread(RechageOrderEvent rechageOrderEvent) {
        if (rechageOrderEvent.getAddOrderBean() != null) {
            //判断用户session是否过期的方法；如果过期，引导用户进行登陆。
            AddOrderBean addOrderBean = rechageOrderEvent.getAddOrderBean();
            if (CodeVerifyUtils.verifyCode(addOrderBean.getCode())) {
                CodeVerifyUtils.verifySession(mActivity);
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
            } else {

                if ("1".equals(addOrderBean.getCode())) {
                    String params = spUtils.get(Constant.SP_USER_ID, "");
                    switch (payType) {
                        case Constant.ALIPAY:
                            //支付宝
                            payMopdel.payCommit(Constant.RECHARGE, moenyNumber + "", "夺宝会充值", "充值金额", params, addOrderBean.getData(), "");
                            break;
                        case Constant.WEIXIN:

                            if (VerifyUtils.isWXAppInstalledAndSupported(mActivity, api)) {

                                dialog = ProgressDialog.show(mActivity, mActivity.getResources().getString(R.string.app_tip), mActivity.getResources().getString(R.string.getting_prepayid));
                                HttpUtils httpUtils = new HttpUtils();
                                RequestParams param = new RequestParams();
                                param.addBodyParameter("user_id", spUtils.get(Constant.SP_USER_ID, ""));
                                param.addBodyParameter("period_id", "0");
                                param.addBodyParameter("total_fee", moenyNumber + "");
                                param.addBodyParameter("out_trade_no", addOrderBean.getData().getJnl_no());
                                param.addBodyParameter("goods_name", "夺宝会支付确认");
                                httpUtils.sendHttpPost(param, ConstantApi.WXInfoOrder, new WxChargeRequestImpl(Constant.WXRECHARGE), mActivity);
                            } else {
                                confrimRecharge.setEnabled(false);
                                loadingDialog.hideLoading();
                            }
                            break;
                        case Constant.BANKCARD:
                            //银行卡
                            print("暂未开放");
                            break;
                        default:
                            print("请选择支付方式");
                            break;
                    }
                } else {
                    print("充值失败，请重试！");
                    confrimRecharge.setEnabled(true);
                    loadingDialog.hideLoading();
                }
            }
        }
    }

    /*微信预支付返回*/
    public void onEventMainThread(WxRechargeEvent wxPayEvent) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (loadingDialog != null) {
            loadingDialog.hideLoading();
        }
        if (wxPayEvent == null) {
            print("网络链接出错，请检查网络");
            confrimRecharge.setEnabled(true);
        } else {
            if (wxPayEvent.getWxPayBean() != null) {
                if (wxPayEvent.getWxPayBean().getCode().equals("1")) {
                    wxPayEvent.getWxPayBean().getData().setName("充值金额");
                    wxPayEvent.getWxPayBean().getData().setMoeny(moenyNumber + "00");
                    payMopdel.getWxPay(mActivity, 1, wxPayEvent.getWxPayBean().getData());
                    MyApplication.WX_TYPE = Constant.WXRECHARGE;
                }
            }
        }
    }

    /*微信支付失败后返回*/
    public void onEventMainThread(BaseRespRechargeEvent baseRespRechargeEvent) {
        confrimRecharge.setEnabled(true);
        loadingDialog.hideLoading();
        Log.e("basePayWx", baseRespRechargeEvent.getBaseResp().errCode + "==" + baseRespRechargeEvent.getBaseResp().errStr);
        if (baseRespRechargeEvent.getBaseResp().getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseRespRechargeEvent.getBaseResp().errCode == BaseResp.ErrCode.ERR_OK) {
                EventBus.getDefault().post(new UtilsEvent("LOAD_INFO"));
                Intent intent = new Intent();
                intent.setClass(mActivity, RechagerReturnActivity.class);
                intent.putExtra("num", moenyNumber + "");
                startActivity(intent);
                finish();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "微信支付错误,请更换支付方式", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    /*微信支付失败后返回*/
    public void onEventMainThread(RechargeImgEvent rechargeImgEvent) {

        RechargeImgBean rechargeImgBean = rechargeImgEvent.getRechargeImgBean();

        if (rechargeImgBean != null) {

            if (!TextUtils.isEmpty(rechargeImgBean.getData().getPic_url())) {
                //ImageLoader.getInstance().displayImage(rechargeImgBean.getData().getPic_url(), rechargeImg);

                ImageLoader.getInstance().loadImage(rechargeImgBean.getData().getPic_url(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        setImgShow();
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                        rechargeImg.setImageBitmap(bitmap);
                        loadingDots.stop();
                        loadingDots.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        setImgShow();
                    }
                });
            }

        } else {
            setImgShow();
        }
    }

    private void setImgShow() {
        info.setVisibility(View.VISIBLE);
        info1.setVisibility(View.VISIBLE);
        loadingDots.stop();
        loadingDots.setVisibility(View.GONE);
    }


    /*关闭软键盘*/
    private void inputSoftHeid(View view) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //confrimRecharge.setEnabled(true);
        if (loadingDialog != null) {
            loadingDialog.hideLoading();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        confrimRecharge.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (loadingDialog.isShowing()) {
            loadingDialog.hideLoading();
        }
        payMopdel.onDestroyHandler();
    }


}
