package com.shinc.duobaohui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.ForgetPasswordBean;
import com.shinc.duobaohui.bean.GetVerifyCodeBean;
import com.shinc.duobaohui.event.HttpGetVerifyCodeEvent;
import com.shinc.duobaohui.event.HttpResetPasswordEvent;
import com.shinc.duobaohui.model.ResetPswModelInterface;
import com.shinc.duobaohui.model.impl.ResetPswModel;
import com.shinc.duobaohui.utils.VerifyUtils;

import de.greenrobot.event.EventBus;

/**
 * 名称：ForgetPasswordActivity
 * 作者：zhaopl 时间: 15/9/17.
 * 实现的主要功能：
 * 忘记密码页面（验证验证码，重置密码）；
 */
public class ForgetPasswordActivity extends BaseActivity {

    private Context mContext;

    private ImageView backImg;

    private EditText phoneNum;

    private TextView getVerifyCode;

    private EditText verifyCode;

    private EditText password;

    private EditText repeatPsw;

    private TextView commit;

    private int time = 60;
    private static final int COUNTDOWN = 0;

    private ResetPswModelInterface resetPswModle;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        EventBus.getDefault().register(this);
        this.mContext = this;
        mHandler = new FrogetPasswordHandler();
        initView();

        initData();

        initEvent();
    }


    /**
     * 初始化页面；
     */
    private void initView() {

        backImg = (ImageView) findViewById(R.id.find_password_back_img);

        //titleText = (TextView) findViewById(R.id.find_password_title_tv);

        phoneNum = (EditText) findViewById(R.id.find_password_edit_phone_num_et);

        getVerifyCode = (TextView) findViewById(R.id.find_password_get_verify_code_tv);

        verifyCode = (EditText) findViewById(R.id.find_password_edit_verify_code_et);

        password = (EditText) findViewById(R.id.find_password_edit_psw_et);

        repeatPsw = (EditText) findViewById(R.id.find_password_edit_psw_repeat_et);

        commit = (TextView) findViewById(R.id.find_password_commit_tv);
    }

    /**
     * 初始化Model层；
     */
    private void initData() {
        resetPswModle = new ResetPswModel(this);
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
                    mHandler.sendEmptyMessage(COUNTDOWN);
                    //todo 点击获取验证码；
                    resetPswModle.getVerifyCode(phoneNum.getText().toString().trim());
                } else {
                    Toast.makeText(mContext, "手机号码有误，请检查！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRegisterInfo()) {
                    //todo 提交数据；
                    String tel = phoneNum.getText().toString().trim();
                    String code = verifyCode.getText().toString().trim();
                    String psw = password.getText().toString().trim();
                    String rePsw = repeatPsw.getText().toString().trim();

                    resetPswModle.commit(tel, psw, rePsw, code);//提交数据；
                    commit.setEnabled(false);
                }
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
                if (TextUtils.isEmpty(password.getText().toString().trim())) {
                    //密码不能为空；
                    Toast.makeText(mContext, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    if (password.getText().toString().length() < 6 || password.getText().toString().length() > 20) {
                        Toast.makeText(mContext, "密码应为6~20个字符之间！", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        if (TextUtils.isEmpty(repeatPsw.getText().toString().trim())) {
                            //重复密码不能为空！
                            Toast.makeText(mContext, "请再次填写密码！", Toast.LENGTH_SHORT).show();
                            return false;
                        } else {
                            if (repeatPsw.getText().toString().trim().equals(password.getText().toString().trim())) {
                                return true;
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
                //todo 请求成功，进行成功后的操作；
                Toast.makeText(mContext, "验证码发送成功，请注意查收！", Toast.LENGTH_SHORT).show();
            } else {
                time = 0;//初始化倒计时；
            }
        }
    }


    /**
     * 获取提交信息的反馈数据；
     *
     * @param httpResetPasswordEvent
     */
    public void onEventMainThread(HttpResetPasswordEvent httpResetPasswordEvent) {
        commit.setEnabled(true);
        if (httpResetPasswordEvent.getForgetPasswordBean() != null) {
            ForgetPasswordBean forgetPasswordBean = httpResetPasswordEvent.getForgetPasswordBean();
            if ("1".equals(forgetPasswordBean.getCode())) {
                //todo 请求成功，进行成功后的操作；
                Toast.makeText(mContext, "密码修改成功，请登陆！", Toast.LENGTH_SHORT).show();
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

    class FrogetPasswordHandler extends Handler {

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

    }
}
