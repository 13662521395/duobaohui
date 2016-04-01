package com.shinc.duobaohui.paylibrary;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.LoginTokenBean;
import com.shinc.duobaohui.bean.ProductDetailBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.event.TokenEvent;
import com.shinc.duobaohui.paylibrary.model.impl.PayModel;
import com.shinc.duobaohui.paylibrary.view.imp.SelectPayView;
import com.shinc.duobaohui.utils.web.HttpUtils;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;

import de.greenrobot.event.EventBus;


/*支付  */
public class PayActivity extends BaseActivity {

    private SelectPayView selectPayView;
    private PayModel payModelnterface;
    private Activity mActivity;
    private ProductDetailBean productDetailBean;

    private String period_id;
    private SharedPreferencesUtils spUtils;
    private String numBer = "0";
    private String sum;
    private Dialog loadingDialog;
    HttpUtils httpUtils = new HttpUtils();
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mActivity = this;

        spUtils = new SharedPreferencesUtils(mActivity, Constant.SP_LOGIN);
        EventBus.getDefault().register(this);

        if (getIntent() != null && getIntent().getExtras() != null) {
            period_id = getIntent().getStringExtra("PRODUCTID");
        }

        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras().getBundle("PRODUCT");
            if (bundle != null) {
                productDetailBean = (ProductDetailBean) bundle.get("PRODUCTDETAIL");
            }
        }
        selectPayView = new SelectPayView(PayActivity.this);
        payModelnterface = new PayModel(PayActivity.this);
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        selectPayView.getMoeny(spUtils.get(Constant.MONEY, "0"));
    }

    private void initData() {

        if (productDetailBean != null) {
            //todo 初始化数据；
            selectPayView.loadingDisable();
            selectPayView.initData(productDetailBean);
        } else {
            if (!TextUtils.isEmpty(period_id)) {
                payModelnterface.getProductDetial(period_id);
            }
        }
    }


    /**
     * 获取Token数据；
     *
     * @param loginTokenEvent
     */

    public void onEventMainThread(TokenEvent loginTokenEvent) {
        if (loginTokenEvent.getLoginTokenBean() != null) {
            LoginTokenBean loginTokenBean = loginTokenEvent.getLoginTokenBean();
            if ("1".equals(loginTokenBean.getCode())) {
                String userId = spUtils.get(Constant.SP_USER_ID, "");
                payModelnterface.getDuoBaoBi(sum, productDetailBean.getData().getPeriod_id(), userId, loginTokenBean.getData());
            } else {
                print("交易失败，请重试！");
                selectPayView.setSubmitBtnStatus(true);
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



