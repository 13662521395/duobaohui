package com.shinc.duobaohui.paylibrary.model.impl;


import android.app.Activity;
import android.content.Intent;

import com.lidroid.xutils.http.RequestParams;
import com.shinc.duobaohui.PayResultActivity;
import com.shinc.duobaohui.RechagerReturnActivity;
import com.shinc.duobaohui.bean.AddOrderBean;
import com.shinc.duobaohui.bean.ProductDetailBean;
import com.shinc.duobaohui.bean.WxPayBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.http.DuoBaoBiRequestImpl;
import com.shinc.duobaohui.http.PayFailureRequestImpl;
import com.shinc.duobaohui.http.ProductDetailHttpRequestImpl;
import com.shinc.duobaohui.paylibrary.bean.PayInfoEntity;
import com.shinc.duobaohui.paylibrary.model.PayModelnterface;
import com.shinc.duobaohui.paylibrary.pay.alipay.AlipayUtils;
import com.shinc.duobaohui.paylibrary.pay.weixin.WxPayUtils;
import com.shinc.duobaohui.utils.web.HttpUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/8/13.
 */
public class PayModel implements PayModelnterface {

    private Activity mActivity;


    private AlipayResultListener alipayResultListener;

    private ProductDetailBean productDetailBean;

    private AlipayUtils alipayUtils;

    public PayModel(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * @param type         支付方式
     * @param period_id    期数Id
     * @param gosName      商品名
     * @param userId       用户iD
     * @param payment_type payment_type =1 直接夺宝 ？payment_type ＝2 充值夺宝币
     */
    PayInfoEntity payEntity;


    @Override
    public void payCommit(final int type, final String num, final String period_id, String gosName, String userId, final AddOrderBean.Order order, String redpacket_id) {
        payEntity = new PayInfoEntity();
        payEntity.setNum(num);
        /**
         * 测试时赋值0.01，正式改为num变量；
         */
        payEntity.setPrice(num);
        payEntity.setPeriod_id(period_id);
        payEntity.setName(gosName);
        payEntity.setUserId(userId);
        payEntity.setOut_trade_no(order.getJnl_no());
        payEntity.setRedpacket_id(redpacket_id);
        //支付结果回调的监听；
        alipayUtils = new AlipayUtils(mActivity, new AlipayUtils.PayResultStatusListener() {
            @Override
            public void paySuccess() {
                if (alipayResultListener != null) {
                    alipayResultListener.result();
                }
                switch (type) {
                    case Constant.RECHARGE:
                        //todo 充值支付成功后，对余额进行更新；
                        EventBus.getDefault().post(new UtilsEvent("LOAD_INFO"));
                        Intent ient = new Intent();
                        ient.setClass(mActivity, RechagerReturnActivity.class);
                        ient.putExtra("num", num);
                        mActivity.startActivity(ient);
                        break;
                    case Constant.DIRECTBUY:
                        Intent intent = new Intent(mActivity, PayResultActivity.class);
                        intent.putExtra("PRODUCTID", period_id);
                        intent.putExtra("PRODUCTDETAIL", order);
                        intent.putExtra("type", "1");
                        intent.putExtra("recharge_channel", "0");//支付方式
                        mActivity.startActivity(intent);
                        EventBus.getDefault().post(new UtilsEvent("LOAD_INFO"));
                        EventBus.getDefault().post(new UtilsEvent("REFRESH"));
                        break;
                }

                mActivity.finish();
            }

            @Override
            public void payWait() {

            }

            @Override
            public void payFailure() {
                // 支付失败；
                if (alipayResultListener != null) {

                    alipayResultListener.result();
                }
                getFailure(type, payEntity.getPrice());
            }
        });
        alipayUtils.pay(payEntity, mActivity);//支付方法调用；
    }

    @Override
    public void onDestroyHandler() {
        if (alipayUtils != null) {
            alipayUtils.onDestroy();
        }
    }

    /**
     * 微信支付
     *
     * @param activity 活动
     * @param type     支付的类型 充值，支付
     */
    @Override
    public void getWxPay(Activity activity, int type, WxPayBean.Data bean) {
        WxPayUtils payUtils = new WxPayUtils(activity, bean);
        payUtils.WxPay();
    }

    /**
     * 获取商品详情；
     *
     * @param period_id
     */
    @Override
    public void getProductDetial(String period_id) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        String params = "?period_id=" + period_id;
        httpUtils.sendHttpPost(requestParams, ConstantApi.GET_PRODUCT_DETAIL + params, new ProductDetailHttpRequestImpl(), mActivity);
    }

    /**
     * 支付宝支付失败触发的方法；后台添加支付记录的条目：
     *
     * @param pay_type
     * @param total_fee
     */
    @Override
    public void getFailure(int pay_type, String total_fee) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        pay_type = 0;
        String params = "?pay_type=" + pay_type;
        params = params + "&total_fee=" + total_fee;
        httpUtils.sendHttpPost(requestParams, ConstantApi.ORDER_RECHARGE + params, new PayFailureRequestImpl(), mActivity);
    }

    /**
     * 夺宝币支付；
     *
     * @param num
     * @param period_id
     * @param userId
     */

    @Override
    public void getDuoBaoBi(String num, String period_id, String userId, String token) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        String params = "?user_id=" + userId;
        params += "&period_id=" + period_id;
        params += "&num=" + num + "&form_token=" + token;
        httpUtils.sendHttpPost(requestParams, ConstantApi.NOTIFIY_BUY + params, new DuoBaoBiRequestImpl(), mActivity);
    }

    @Override
    public void getDuoBaoBi(String jnlNo, String redpacket_id) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("jnl_no", jnlNo);
        requestParams.addBodyParameter("redpacket_id", redpacket_id);
        httpUtils.sendHttpPost(requestParams, ConstantApi.DUOBAOBI_BUY, new DuoBaoBiRequestImpl(), mActivity);
    }

    /**
     * 支付结果回调接口；
     */
    public interface AlipayResultListener {
        void result();
    }


    public void setAlipayResultListener(AlipayResultListener alipayResultListener) {
        this.alipayResultListener = alipayResultListener;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
