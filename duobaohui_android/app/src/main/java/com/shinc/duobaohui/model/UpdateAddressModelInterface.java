package com.shinc.duobaohui.model;

/**
 * Created by liugaopo on 15/10/6.
 * 修改地址
 */
public interface UpdateAddressModelInterface {


    void getInfoDelete(String address_id);


    void getUpdate(String address_id, String name, String phoneNum, String area, String detail, String isDefault);

}
