package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.event.HttpAddAddressEvent;
import com.shinc.duobaohui.model.AddAddressListModelInterface;
import com.shinc.duobaohui.model.impl.AddAddressModelImpl;
import com.shinc.duobaohui.utils.VerifyUtils;
import com.shinc.duobaohui.utils.cascade.CascadeListener;
import com.shinc.duobaohui.utils.cascade.com.mrwujay.cascade.view.CascadeViewLayout;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/9/29.
 * 添加收货地址
 */

public class AddAddressActivity extends BaseActivity {

    private Activity mActivity;
    private CascadeViewLayout cascadeViewLayout;
    private ImageView imgBack;
    private CustomTextView tvAddSubmit;
    private EditText editName;
    private EditText editPhone;
    private EditText editAddress;
    private EditText editAddressDetail;
    private AddAddressListModelInterface addAddressModel;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address_layout);
        getWindow().setBackgroundDrawable(null);
        mActivity = AddAddressActivity.this;
        EventBus.getDefault().register(this);
        initView();
        initModel();
        initListener();
    }

    private void initModel() {
        addAddressModel = new AddAddressModelImpl(mActivity);
    }

    private void initView() {
        loadingDialog = new LoadingDialog(mActivity, R.style.dialog);
        imgBack = (ImageView) findViewById(R.id.add_address_layout_img_back);
        editName = (EditText) findViewById(R.id.add_address_layout_name);
        editPhone = (EditText) findViewById(R.id.add_address_layout_phone);
        editAddress = (EditText) findViewById(R.id.add_address_layout_address);
        editAddressDetail = (EditText) findViewById(R.id.add_address_layout_addressDetail);
        cascadeViewLayout = (CascadeViewLayout) findViewById(R.id.add_address_Cascade);
        cascadeViewLayout.setVisibility(View.GONE);
        tvAddSubmit = (CustomTextView) findViewById(R.id.add_address_layout_tv_addAddress);
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAddSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editName.getText())) {
                    print("收货人姓名不能为空");
                } else if (!VerifyUtils.isNickName(editName.getText().toString().trim())) {
                    print("收货人姓名包含非法字符");
                } else if (TextUtils.isEmpty(editPhone.getText())) {
                    print("收货人电话不能为空");
                } else if (!VerifyUtils.isMobile(editPhone.getText().toString().trim())) {
                    print("收货人电话填写错误");
                } else if (TextUtils.isEmpty(editAddress.getText())) {
                    print("收货人地区不能为空");
                } else if (TextUtils.isEmpty(editAddressDetail.getText())) {
                    print("收货人的详细地址不能为空");
                } else if (!VerifyUtils.isNickName(editAddressDetail.getText().toString().trim())) {
                    print("收货人的详细地址包含非法字符");
                } else {
                    tvAddSubmit.setEnabled(false);
                    loadingDialog.show();
                    addAddressModel.setValue(editName.getText().toString(), editPhone.getText().toString(), editAddress.getText().toString(), editAddressDetail.getText().toString());
                }
            }
        });
        editAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editAddress.getWindowToken(), 0);
                    cascadeViewLayout.setVisibility(View.VISIBLE);
                } else {
                    cascadeViewLayout.setVisibility(View.GONE);
                }
            }
        });

        cascadeViewLayout.setCascadeListener(new CascadeListener() {
            @Override
            public void getCascadeValue(String msg) {
                //安徽省,安庆市,大观区,246000
                editAddress.setText(msg.split(",")[0] + msg.split(",")[1] + msg.split(",")[2]);
            }
        });
    }

    public void onEventMainThread(HttpAddAddressEvent httpAddressEvent) {

        loadingDialog.hideLoading();
        if (httpAddressEvent.getGetVerifyCodeBean() == null) {
            print("网络链接错误，请检查网络");
        } else {
            if (httpAddressEvent.getGetVerifyCodeBean().getCode().equals("1")) {
                print(httpAddressEvent.getGetVerifyCodeBean().getMsg().toString());
                finish();
                MyApplication.is_Resume = true;
            } else {
                print(httpAddressEvent.getGetVerifyCodeBean().getMsg().toString());
            }
        }
        tvAddSubmit.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
