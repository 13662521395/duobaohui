package com.shinc.duobaohui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.GetVerifyCodeBean;
import com.shinc.duobaohui.bean.RegisterBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;
import com.shinc.duobaohui.event.HttpGetVerifyCodeEvent;
import com.shinc.duobaohui.event.HttpRegisterEvent;
import com.shinc.duobaohui.model.RegisterModelInterface;
import com.shinc.duobaohui.model.impl.RegisterModel;
import com.shinc.duobaohui.utils.VerifyUtils;

import de.greenrobot.event.EventBus;

/**
 * 名称：RegisterActivity
 * 作者：zhaopl 时间: 15/9/17.
 * 实现的主要功能：
 * 注册页面；
 */
public class RegisterActivity extends BaseActivity {

    private Context mContext;

    private ImageView backImg;

    private EditText phoneNum;

    private EditText verifyCode;

    private TextView getVerifyCode;

    private EditText password;

    private EditText repeatPassword;

    private TextView registerCommit;

    private TextView goToLogin;

    private CheckBox userProtocol;

    private CustomTextView typeLinkUserProtocol;

    private boolean isCheckedProtocol;//是否选择用户协议；

    private int time = 60;
    private static final int COUNTDOWN = 0;

    private int isFlag;

    private RegisterModelInterface registerModle;

    private EditText nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EventBus.getDefault().register(this);
        this.mContext = this;

        getExtraData();
        initView();

        initData();

        initEvent();
    }

    private void initData() {

        registerModle = new RegisterModel(this);
    }

    /**
     * 得到传递的数据；
     */
    private void getExtraData() {

        if (getIntent() != null && getIntent().getExtras() != null) {
            isFlag = getIntent().getIntExtra("isFlag", 0);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case COUNTDOWN:
                    if (time > 0) {
                        this.sendEmptyMessageDelayed(COUNTDOWN, 1000);
                        getVerifyCode.setText("重新获取 " + time);
                        getVerifyCode.setTextColor(0xffffffff);
                        getVerifyCode.setEnabled(false);
                        time--;
                    } else {
                        time = 60;
                        getVerifyCode.setText("获取验证码");
                        getVerifyCode.setTextColor(0xffffffff);
                        getVerifyCode.setEnabled(true);
                        break;
                    }

                    break;
            }
        }

    };


    /**
     * 初始化控件；
     */
    private void initView() {

        backImg = (ImageView) findViewById(R.id.register_back_img);

        phoneNum = (EditText) findViewById(R.id.register_edit_phone_num_et);

        verifyCode = (EditText) findViewById(R.id.register_edit_verify_code_et);

        getVerifyCode = (TextView) findViewById(R.id.register_get_verify_code_tv);

        password = (EditText) findViewById(R.id.register_edit_psw_et);

        repeatPassword = (EditText) findViewById(R.id.register_edit_psw_repeat_et);

        registerCommit = (TextView) findViewById(R.id.register_commit_tv);

        goToLogin = (TextView) findViewById(R.id.register_to_login_tv);

        typeLinkUserProtocol = (CustomTextView) findViewById(R.id.register_user_protocol);

        userProtocol = (CheckBox) findViewById(R.id.check_user_protocol);

        nickName = (EditText) findViewById(R.id.register_edit_nick_name_et);
        nickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nickName.getText().toString().length() > 10) {
                    nickName.setText(nickName.getText().subSequence(0, 10));
                    nickName.setSelection(nickName.getText().length());
                }
            }
        });
    }

    /**
     * 初始化事件；
     */
    private void initEvent() {

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VerifyUtils.isMobile(phoneNum.getText().toString().trim())) {
                    //todo 触发获取验证码的网络请求；
                    mHandler.sendEmptyMessage(COUNTDOWN);
                    registerModle.getVerifyCode(phoneNum.getText().toString().trim());

                } else {
                    Toast.makeText(mContext, "手机号码输入有误，请检查！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkRegisterInfo()) {
                    //todo 触发提交注册信息的网络请求；
                    String tel = phoneNum.getText().toString().trim();
                    String code = verifyCode.getText().toString().trim();
                    String psw = password.getText().toString().trim();
                    String rePsw = nickName.getText().toString().trim();

                    registerModle.commit(tel, psw, rePsw, code);//
                }

            }
        });

        //跳转到登陆页面；
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到登陆页面，其实就是返回；finish掉这个页面；
                if (isFlag == 1) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });

        //选择遵守用户协议的CheckBox;
        userProtocol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckedProtocol = isChecked;
            }
        });

        typeLinkUserProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到用户协议页面；

                Intent intent = new Intent(mContext, UserProtocolActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 检查注册信息的方法；
     */
    private boolean checkRegisterInfo() {
        if (!VerifyUtils.isMobile(phoneNum.getText().toString().trim())) {
            //为空；
            Toast.makeText(mContext, "手机号码不正确!", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            if (TextUtils.isEmpty(verifyCode.getText().toString().trim())) {
                //验证码为空;
                Toast.makeText(mContext, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (TextUtils.isEmpty(nickName.getText().toString().trim())) {

                    Toast.makeText(mContext, "昵称不能为空！", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    if (!VerifyUtils.isNickName(nickName.getText().toString().trim())) {
                        Toast.makeText(mContext, "昵称中包含不合法字符！", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        if (TextUtils.isEmpty(password.getText().toString().trim())) {
                            //密码不能为空；
                            Toast.makeText(mContext, "密码不能为空！", Toast.LENGTH_SHORT).show();
                            return false;
                        } else {
                            if (repeatPassword.getText().toString().trim().equals(password.getText().toString().trim())) {

                                if (isCheckedProtocol) {
                                    return true;
                                } else {
                                    Toast.makeText(mContext, "请勾选用户协议！", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            } else {
                                Toast.makeText(mContext, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }
                    }
                }
            }

        }
    }


    /**
     * 获取获取验证码信息的反馈数据；
     *
     * @param httpGetVerifyCodeEvent
     */

    public void onEventMainThread(HttpGetVerifyCodeEvent httpGetVerifyCodeEvent) {

        if (httpGetVerifyCodeEvent.getGetVerifyCodeBean() != null) {
            GetVerifyCodeBean getVerifyCodeBean = httpGetVerifyCodeEvent.getGetVerifyCodeBean();
            if ("1".equals(getVerifyCodeBean.getCode())) {
                // 请求成功，进行成功后的操作；
                Toast.makeText(mContext, "验证码发送成功，请注意查收！", Toast.LENGTH_SHORT).show();
            } else {
                time = 0;
            }
        }
    }

    /**
     * 获取注册提交信息的反馈数据；
     *
     * @param httpRegisterEvent
     */
    public void onEventMainThread(HttpRegisterEvent httpRegisterEvent) {
        if (httpRegisterEvent.getRegisterBean() != null) {

            RegisterBean registerBean = httpRegisterEvent.getRegisterBean();
            if ("1".equals(registerBean.getCode())) {
                //请求成功，进行成功后的操作；
                Toast.makeText(mContext, "注册成功，请登录！", Toast.LENGTH_SHORT).show();
                finish();
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
}
