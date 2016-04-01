package com.shinc.duobaohui.bean;

/**
 * 收货地址的 bean
 * Created by yangtianhe on 15/9/30.
 */
class AddressItemBean {
    private String id;
    private String name;
    private String phone;
    private String address;
    private String flag;

    public AddressItemBean(String id, String name, String phone, String address, String flag) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.flag = flag;
    }

    public AddressItemBean() {
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
