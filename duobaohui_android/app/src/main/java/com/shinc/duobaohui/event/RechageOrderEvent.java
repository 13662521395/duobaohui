package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.AddOrderBean;

/**
 * Created by liugaopo on 15/10/19.
 * 生成Order的结果传递的Event：
 */
public class RechageOrderEvent {
    private AddOrderBean addOrderBean;

    public RechageOrderEvent(AddOrderBean addOrderBean) {
        this.addOrderBean = addOrderBean;
    }

    public AddOrderBean getAddOrderBean() {
        return addOrderBean;
    }
}
