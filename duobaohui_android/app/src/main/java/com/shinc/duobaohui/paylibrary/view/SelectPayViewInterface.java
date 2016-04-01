package com.shinc.duobaohui.paylibrary.view;


import com.shinc.duobaohui.bean.IndexBannerBean;
import com.shinc.duobaohui.bean.ProductDetailBean;
import com.shinc.duobaohui.paylibrary.bean.PayEntity;

import java.util.List;

/**
 * Created by liugaopo on 15/8/12.
 */
public interface SelectPayViewInterface {

    void setDate(List<PayEntity> entityList);

    /**
     * 得到购买的数量;
     */
    String getNum();

    /**
     * 设置购买的数量；
     *
     * @param payNum
     */
    void setNum(String payNum);

    void setBannerData(IndexBannerBean indexBannerBean, SetBannerOnPageClick pageClick);

    interface SetBannerOnPageClick {
        void onClick(String id, String type);
    }


    void payCommit(CommitListener commitListener);

    interface CommitListener {
        void commit(int type, String num);
    }

    /**
     * 初始化数据；
     *
     * @param productDetailBean
     */
    void initData(ProductDetailBean productDetailBean);


    void setSubmitBtnStatus(Boolean flag);

    /*
    * 得到
    * */
    void getMoeny(String moeny);
}
