package com.shinc.duobaohui.wxapi;


import android.content.Intent;
import android.os.Bundle;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.event.BaseRespPayEvent;
import com.shinc.duobaohui.event.BaseRespRechargeEvent;
import com.shinc.duobaohui.paylibrary.pay.weixin.Constants;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

//    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (MyApplication.WX_TYPE == Constant.WXRECHARGE) {
            EventBus.getDefault().post(new BaseRespRechargeEvent(resp));
        } else if (MyApplication.WX_TYPE == Constant.WXPAY) {
            EventBus.getDefault().post(new BaseRespPayEvent(resp));
        }
        finish();
    }


}