package com.shinc.duobaohui.paylibrary.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.WindowManager;

import com.alipay.sdk.app.PayTask;
import com.shinc.duobaohui.constant.ConstantApi;
import com.shinc.duobaohui.paylibrary.bean.PayInfoEntity;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/*支付宝支付工具类*/
@SuppressWarnings("ALL")
public class AlipayUtils {

    //商户PID
    public static final String PARTNER = "2088911708976095";
    //商户收款账号
    public static final String SELLER = "admin@shinc.net";
    //商户私钥
    public static final String RSA_PRIVATE = "MIICXAIBAAKBgQDFCzDihL3+00Ksw5gNtoeK66QumNEOVhuoNPAaEO2hBoXA7iMrKRAkrmAEn6J6j/EA6mJWXVQWmCcC54eOdVTCSs+lsE06MxfI2OpVs2ENKnXnpzxtQgJSEAUwZx+mHNbPoCM6jL2xQzlj7yisUQniShYkqtyRhF440dv8yQ0T3wIDAQABAoGAQr/kwoeHOWDlsFLeGp8f0X678pEQGNQwsE2mnJ3pcegcvVuujDtv3Y04t7hAOVazpMTBpVIeXz0R0hkOwcKbgFEX682wv4dnc9XwfEDaMdFwNOmUbZhI7AuYthTX5FT02Ez00nAp0mQa25rnWTBOxUy/2++nJC59hycwZKr6LmkCQQD4cZrtMPmIWI40AIxACcD991giEYVAL75PHik0yofk+y/7O7x8ccLV36TrFLd2WjVSu5fYE0uVZfZTn51XGLqVAkEAywlh7fb7WjEJk0FrnmprdjBUh61sfX+M0UGYrU+gk/okqw93RaXJQIPfTjYcOZ8cMDkLap06A8CC644YQoprowJAKjFQ+FtK5Yb0j4xQRUfiyL7K7u4+zQnOri0XHmoO7ipima4pcpD3X88dePcJuUXUrHpDbWTJf1PTo5wF4pbGiQJBALlj4tCHYXE0L3sPFDY9E8yCStzsRJC/I5R64e72MziLAmpTn+OvgnjvMLpYfCM9Hl8F51+9GpkMcj6lq48ulyMCQBwHxTjUTn0x8ug7/gVayPh+YZDtkA5AXixAmPWZV3sNPZbvnJcUvAuu4jii8RpsuX/drfNzKtaXbdqAOepD5ro=";
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private Activity mActivity;

    private PayResultStatusListener payResultStatusListener;

    private Handler mHandler;

    public AlipayUtils(Activity mActivity, PayResultStatusListener payResultStatusListener) {
        this.mActivity = mActivity;
        this.payResultStatusListener = payResultStatusListener;
        mHandler = new MyHandler(this.mActivity);
    }


    public static void backgroundAlpha(float bgAlpha, Activity mActivity) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();

        lp.alpha = bgAlpha; // 0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    } // 调用

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(PayInfoEntity entity, final Activity mActivity) {

        // 订单
        String orderInfo = getOrderInfo(entity);


        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

//        Log.e("orderInfo:", payInfo);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {

                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     */

    public void check(final Activity mActivity) {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(mActivity);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */

    public void getSDKVersion(final Activity mActivity) {
        PayTask payTask = new PayTask(mActivity);
        String version = payTask.getVersion();
//        Toast.makeText(mActivity, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */

    public String getOrderInfo(PayInfoEntity payInfoEntity) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + payInfoEntity.getOut_trade_no() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + "夺宝会 " + payInfoEntity.getNum() + " M网盘空间" + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + payInfoEntity.getPeriod_id() + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + payInfoEntity.getPrice() + "\"";

        //支付；
        orderInfo += "&notify_url=" + "\"" + ConstantApi.ALIPAY + "?out_trade_no=" + payInfoEntity.getOut_trade_no() + "&redpacket_id=" + payInfoEntity.getRedpacket_id() + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        //  orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */

    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */

    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }


    class MyHandler extends Handler {
        //弱应用
        WeakReference<Activity> mActivityReference;

        MyHandler(Activity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((String) msg.obj);

                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        // String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();

                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            payResultStatusListener.paySuccess();
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                payResultStatusListener.payWait();

                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误

                                payResultStatusListener.payFailure();

                            }
                        }
                        break;
                    }

                    case SDK_CHECK_FLAG: {

                        break;
                    }
                }
            }
        }
    }

    //回收Handler
    public void onDestroy() {
        //  如果为空，所有的回调函数和消息将被删除。
        mHandler.removeCallbacksAndMessages(null);
    }

    public interface PayResultStatusListener {

        void paySuccess();

        void payWait();

        void payFailure();
    }


}
