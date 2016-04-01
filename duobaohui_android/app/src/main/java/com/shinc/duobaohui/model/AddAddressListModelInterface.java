package com.shinc.duobaohui.model;

/**
 * Created by liugaopo on 15/10/6.
 */
public interface AddAddressListModelInterface {
    /**
     * @param name    收货人姓名
     * @param phone   收货人电话
     * @param area    收货人地区
     * @param address 收货人详细地址
     */
    void setValue(String name, String phone, String area, String address);
}
