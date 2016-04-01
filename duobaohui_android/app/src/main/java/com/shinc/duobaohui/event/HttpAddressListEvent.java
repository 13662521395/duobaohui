package com.shinc.duobaohui.event;

import com.shinc.duobaohui.bean.AddressListBean;

/**
 * Created by liugaopo on 15/10/6.
 */
public class HttpAddressListEvent {
    private AddressListBean listBean;

    public AddressListBean getListBean() {
        return listBean;
    }

    public HttpAddressListEvent(AddressListBean listBean) {
        this.listBean = listBean;
    }
}
