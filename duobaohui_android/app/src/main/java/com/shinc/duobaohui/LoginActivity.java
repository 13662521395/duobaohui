package com.shinc.duobaohui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.LoginBean;
import com.shinc.duobaohui.bean.LoginTokenBean;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.event.HttpLoginEvent;
import com.shinc.duobaohui.event.LoginTokenEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.http.GetLoginTokenRequestImpl;
import com.shinc.duobaohui.model.LoginModelInterface;
import com.shinc.duobaohui.model.impl.LoginModel;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.VerifyUtils;

import de.greenrobot.event.EventBus;

/**
 * 名称：LoginActivity
 * 作者：zhaopl 时间: 15/9/17.
 * 实现的主要功能：
 * 登陆页面；(修补布局页面部分细节)
 */
public class LoginActivity extends BaseActivity {


    private ImageView backImg;

    private EditText loginName;

    private EditText loginPassword;

    private TextView loginCommit;

    private TextView goToRegister;

    private TextView forgetPassword;

    private ImageView loginByQQ;

    private ImageView loginByWChat;

    private ImageView loginBySina;

    private Context mContext;

    private LoadingDialog loadingDialog;

    private LoginModelInterface loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EventBus.getDefault().register(this);
        this.mContext = this;

        initView();

        initData();

        initEvent();
    }

    /**
     * 初始化model层；
     */
    private void initData() {
        loginModel = new LoginModel(this);
    }

    /**
     * 初始化控件；
     */

    private void initView() {

        backImg = (ImageView) findViewById(R.id.login_back_img);

        loginName = (EditText) findViewById(R.id.login_edit_name);
        loginPassword = (EditText) findViewById(R.id.login_edit_password);

        loginCommit = (TextView) findViewById(R.id.login_commit_btn);

        goToRegister = (TextView) findViewById(R.id.login_goto_register_tv);
        forgetPassword = (TextView) findViewById(R.id.login_forget_password);

        loginByQQ = (ImageView) findViewById(R.id.login_by_qq);
        loginByWChat = (ImageView) findViewById(R.id.login_by_weixin);
        loginBySina = (ImageView) findViewById(R.id.login_by_sina);
        loadingDialog = new LoadingDialog(mContext, R.style.dialog);
    }

    /**
     * 初始化事件；
     */
    private void initEvent() {

        //后退按钮点击事件；
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//关闭该页面；
            }
        });

        //点击登陆；
        loginCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyLoginInfo()) {
                    loadingDialog.show();
                    //进行登陆请求；置登陆按钮为不可点击；
                    loginCommit.setEnabled(false);
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.sendHttpPost(null, ConstantApi.GET_LOGIN_TOKEN, new GetLoginTokenRequestImpl(), mContext);

                }
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 本地校验登陆信息；
     *
     * @return
     */

    private boolean verifyLoginInfo() {
        if (TextUtils.isEmpty(loginName.getText().toString())) {
            Toast.makeText(mContext, "手机号码不能为空！", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            if (VerifyUtils.isMobile(loginName.getText().toString())) {
                if (TextUtils.isEmpty(loginPassword.getText()
                        .toString())) {
                    Toast.makeText(mContext, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    return true;
                }
            } else {
                Toast.makeText(mContext, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    /**
     * 获取登陆数据；
     *
     * @param httpLoginEvent
     */
    public void onEventMainThread(HttpLoginEvent httpLoginEvent) {
        loginCommit.setEnabled(true);
        loadingDialog.hideLoading();
        if (httpLoginEvent.getLoginBean() != null) {
            LoginBean loginBean = httpLoginEvent.getLoginBean();
            if ("1".equals(loginBean.getCode())) {
                loginModel.saveData(loginBean);
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
                finish();

            } else {
                print(loginBean.getMsg());
                loginCommit.setEnabled(true);
            }
        } else {
            print("网络连接错误,请检查");
            loginCommit.setEnabled(true);
        }
    }


    /**
     * 获取登陆Token数据；
     *
     * @param loginTokenEvent
     */
    public void onEventMainThread(LoginTokenEvent loginTokenEvent) {
        if (loginTokenEvent.getLoginTokenBean() != null) {
            LoginTokenBean loginTokenBean = loginTokenEvent.getLoginTokenBean();
            if ("1".equals(loginTokenBean.getCode())) {
                loginModel.login(loginName.getText().toString().trim(), loginPassword.getText().toString().trim(), loginTokenBean.getData());
            } else {
                print(loginTokenBean.getMsg());
                loginCommit.setEnabled(true);
                loadingDialog.hideLoading();
            }
        } else {
            print("网络连接错误,请检查");
            loginCommit.setEnabled(true);
            loadingDialog.hideLoading();
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
