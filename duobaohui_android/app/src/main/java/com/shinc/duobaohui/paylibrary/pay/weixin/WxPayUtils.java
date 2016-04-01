package com.shinc.duobaohui.paylibrary.pay.weixin;

import android.content.Context;

import com.shinc.duobaohui.bean.WxPayBean;


import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/*微信支付工具类*/
public class WxPayUtils {

    Context context;
    PayReq req;
    IWXAPI msgApi;
    WxPayBean.Data bean;

    public WxPayUtils(Context context, WxPayBean.Data bean) {
        this.context = context;
        this.bean = bean;
        msgApi = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);

    }

    public void WxPay() {
        req = new PayReq();
        genPayReq();
    }


    private void genPayReq() {

        req.appId = Constants.APP_ID; //公众账号ID
        req.partnerId = Constants.MCH_ID; //微信支付分配的商户号
        req.prepayId = bean.getPrepay_id();  //微信返回的支付交易会话ID
        req.packageValue = "Sign=WXPay"; //暂填写固定值Sign=WXPay
        req.nonceStr = bean.getNonce_str();//随机字符串，不长于32位。
        req.timeStamp = bean.getTimestamp();//时间戳，请见接口规则-参数规定
        req.sign = bean.getSign();
        sendPayReq();

    }

    private void sendPayReq() {

        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
    }

}

