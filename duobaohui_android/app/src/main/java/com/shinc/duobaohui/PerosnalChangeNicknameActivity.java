package com.shinc.duobaohui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.ChangeNicknameBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.event.HttpChangeNicknameEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.model.ChangeNicknameModelInterface;
import com.shinc.duobaohui.model.impl.ChangeNicknameModel;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.VerifyUtils;

import de.greenrobot.event.EventBus;

/**
 * 修改手机号码
 * Created by yangtianhe on 15/10/12.
 */
public class PerosnalChangeNicknameActivity extends BaseActivity {

    private ImageView backImg;
    private Activity mActivity;
    private TextView submit;
    private EditText changenickname;
    private ChangeNicknameModelInterface ChangeNicknameModel;
    private SharedPreferencesUtils spUtils;
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        setContentView(R.layout.perosnal_changenickname_layout);
        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
        initView();
        initListener();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        loadingDialog = new LoadingDialog(mActivity, R.style.dialog);
        ChangeNicknameModel = new ChangeNicknameModel(this);
        backImg = (ImageView) findViewById(R.id.person_info_layout_img_back);
        changenickname = (EditText) findViewById(R.id.change_nickname_et);
        submit = (TextView) findViewById(R.id.submit);
        changenickname.setText(spUtils.get(Constant.SP_NICK_NAME, ""));
        changenickname.setSelection(changenickname.getText().length());
        changenickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (changenickname.getText().toString().length() > 10) {
                    changenickname.setText(changenickname.getText().subSequence(0, 10));
                }
                changenickname.setSelection(changenickname.getText().length());
            }
        });
    }

    private void initListener() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changenickname.getText().toString().equals(spUtils.get(Constant.SP_NICK_NAME, ""))) {
                    print("没有修改信息");
                } else if (verifyNickName(changenickname.getText().toString().trim())) {
                    loadingDialog.show();
                    ChangeNicknameModel.ChangeNickname(changenickname.getText().toString());
                }
            }
        });
    }

    /**
     * 对用户收入的昵称进行校验的方法；
     *
     * @return
     */
    private boolean verifyNickName(String nickeName) {

        if (TextUtils.isEmpty(nickeName)) {
            Toast.makeText(this, "用户昵称不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!VerifyUtils.isNickName(nickeName)) {
            Toast.makeText(this, "用户昵称包含非法字符！", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取修改昵称数据；
     *
     * @param
     */
    public void onEventMainThread(HttpChangeNicknameEvent httpChangeNicknameEvent) {
        loadingDialog.hideLoading();
        if (httpChangeNicknameEvent.getChangeNicknameBean() != null) {
            ChangeNicknameBean changeNicknameBean = httpChangeNicknameEvent.getChangeNicknameBean();
            if ("1".equals(changeNicknameBean.getCode())) {
                //todo 请求成功，进行成功后的操作；
                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                spUtils.add(Constant.SP_NICK_NAME, changenickname.getText().toString());
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
                finish();

            } else if ("0".equals(changeNicknameBean.getCode())) {
                Toast.makeText(this, "修改失败，请重试！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, changeNicknameBean.getMsg(), Toast.LENGTH_SHORT).show();
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
