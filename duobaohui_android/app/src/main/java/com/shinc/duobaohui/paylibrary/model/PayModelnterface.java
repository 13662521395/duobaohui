package com.shinc.duobaohui.paylibrary.model;


import android.app.Activity;

import com.shinc.duobaohui.bean.AddOrderBean;
import com.shinc.duobaohui.bean.WxPayBean;

/**
 * Created by liugaopo on 15/8/13.
 */
public interface PayModelnterface {

    //List<PayEntity> getDate();

    /*支付宝支付*/
    void payCommit(final int type, final String num, final String period_id, String gosName, String userId, AddOrderBean.Order order, String redpacket_id);


    void onDestroyHandler();
    /*微信支付*/

    void getWxPay(Activity activity, int type, WxPayBean.Data bean);

    /**
     * 得到商品数据；
     *
     * @param period_id
     */
    void getProductDetial(String period_id);


    /**
     * 支付失败,未付款
     */
    void getFailure(int pay_type, String total_fee);


    void getDuoBaoBi(String num, String period_id, String userId, String token);

    void getDuoBaoBi(String jnlNo,String id);
}
