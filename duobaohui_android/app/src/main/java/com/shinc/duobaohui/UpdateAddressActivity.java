package com.shinc.duobaohui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.bean.AddressListBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.event.HttpUpdateAddressDeleteEvent;
import com.shinc.duobaohui.event.HttpUpdateAddressEvent;
import com.shinc.duobaohui.model.impl.UpdateAddressModelImpl;
import com.shinc.duobaohui.utils.VerifyUtils;
import com.shinc.duobaohui.utils.cascade.CascadeListener;
import com.shinc.duobaohui.utils.cascade.com.mrwujay.cascade.view.CascadeViewLayout;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/9/29.
 * 修改地址
 */
public class UpdateAddressActivity extends BaseActivity {
    private Activity mActivity;
    private CascadeViewLayout cascadeViewLayout;
    private ToggleButton toggleButton;
    private EditText name;
    private EditText phoneNum;
    private EditText area;
    private EditText addressDetail;
    private CustomTextView confrim;
    private CustomTextView layoutDalete;
    private ImageView imgBack;
    private UpdateAddressModelImpl updateAddressModel;
//    private LinearLayout linearLayout;


    private String address_id;
    private String is_default;
    private AddressListBean.AddressListChildBean address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_address_layout);
        EventBus.getDefault().register(this);
        mActivity = UpdateAddressActivity.this;
        initView();
        initModel();
        initValue();
        initListener();
    }

    private void initModel() {

        updateAddressModel = new UpdateAddressModelImpl(mActivity);
    }


    private void initView() {
        getWindow().setBackgroundDrawable(null);
        imgBack = (ImageView) findViewById(R.id.update_address_list_layout_img_back);
        confrim = (CustomTextView) findViewById(R.id.update_address_list_layout_tv_addAddress);
        cascadeViewLayout = (CascadeViewLayout) findViewById(R.id.update_address_Cascade);
        name = (EditText) findViewById(R.id.update_address_layout_name);
        phoneNum = (EditText) findViewById(R.id.update_address_layout_phoneNum);
        area = (EditText) findViewById(R.id.update_address_layout_area);
        addressDetail = (EditText) findViewById(R.id.update_address_layout_addressDetail);
        toggleButton = (ToggleButton) findViewById(R.id.upodate_address_layout_toggleButton);
        toggleButton.setBackgroundResource(R.drawable.icon_defaultadd_on);
        layoutDalete = (CustomTextView) findViewById(R.id.update_address_layout_updateLayout);
//      linearLayout = (LinearLayout) findViewById(R.id.update_address_layout_linear_area);
        cascadeViewLayout.setVisibility(View.GONE);

    }

    private void initValue() {
        Intent intent = getIntent();

        address = (AddressListBean.AddressListChildBean) intent.getSerializableExtra("update");
        address_id = address.getAddress_id();
        name.setText(address.getConsignee());
        phoneNum.setText(address.getMobile());
        area.setText(address.getDistrict());
        addressDetail.setText(address.getAddress());
        is_default = address.getIs_default();
        if (is_default.equals("1")) {
            toggleButton.setChecked(true);
            toggleButton.setBackgroundResource(R.drawable.icon_defaultadd_on);
        } else {
            toggleButton.setChecked(false);
            toggleButton.setBackgroundResource(R.drawable.icon_defaultadd_off);
        }
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cascadeViewLayout.setCascadeListener(new CascadeListener() {
            @Override
            public void getCascadeValue(String msg) {
                area.setText(msg.split(",")[0] + msg.split(",")[1] + msg.split(",")[2]);
            }
        });

        area.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cascadeViewLayout.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(area.getWindowToken(), 0);
                } else {
                    cascadeViewLayout.setVisibility(View.GONE);
                }
            }
        });
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButton.setBackgroundResource(R.drawable.icon_defaultadd_on);
                    is_default = "1";
                } else {
                    toggleButton.setBackgroundResource(R.drawable.icon_defaultadd_off);
                    is_default = "0";
                }
            }
        });

        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText())) {
                    print("收货人姓名不能为空");

                } else if (!VerifyUtils.isNickName(name.getText().toString().trim())) {
                    print("收货人姓名包含非法字符");

                } else if (TextUtils.isEmpty(phoneNum.getText())) {
                    print("收货人电话不能为空");

                } else if (!VerifyUtils.isMobile(phoneNum.getText().toString().trim())) {
                    print("收货人电话填写错误");

                } else if (TextUtils.isEmpty(area.getText())) {
                    print("收货人地区不能为空");

                } else if (TextUtils.isEmpty(addressDetail.getText())) {
                    print("收货人的详细地址不能为空");

                } else if (!VerifyUtils.isNickName(addressDetail.getText().toString().trim())) {
                    print("收货人的详细地址包含非法字符");
                } else if (name.getText().toString().equals(address.getConsignee()) && phoneNum.getText().toString().equals(address.getMobile()) && area.getText().toString().equals(address.getDistrict()) && addressDetail.getText().toString().equals(address.getAddress()) && is_default.equals(address.getIs_default())) {
                    print("没有修改任何信息");
                } else {
                    confrim.setEnabled(false);
                    updateAddressModel.getUpdate(address_id, name.getText().toString(), phoneNum.getText().toString(), area.getText().toString(), addressDetail.getText().toString(), is_default);
                }
            }
        });

        layoutDalete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDalete.setEnabled(false);
                updateAddressModel.getInfoDelete(address_id);
            }
        });
    }

    public void onEventMainThread(HttpUpdateAddressDeleteEvent deleteEvent) {
        if (deleteEvent == null) {
            print("网络链接错误，请检查网络");
        } else {
            if (deleteEvent.getVerifyCodeBean().getCode().equals("1")) {
                print(deleteEvent.getVerifyCodeBean().getMsg());
                //地址列表是否刷新
                MyApplication.is_Resume = true;
                finish();
            } else {
                print(deleteEvent.getVerifyCodeBean().getMsg());
            }
        }
        layoutDalete.setEnabled(true);
    }

    public void onEventMainThread(HttpUpdateAddressEvent deleteEvent) {
        if (deleteEvent.getGetVerifyCodeBean() == null) {
            print("网络链接错误，请检查网络");
        } else {
            if (deleteEvent.getGetVerifyCodeBean().getCode().equals("1")) {
                print(deleteEvent.getGetVerifyCodeBean().getMsg());
                //地址列表是否刷新
                MyApplication.is_Resume = true;
                finish();
            } else {
                print(deleteEvent.getGetVerifyCodeBean().getMsg());
            }
        }
        confrim.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
