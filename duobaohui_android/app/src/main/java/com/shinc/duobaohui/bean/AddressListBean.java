package com.shinc.duobaohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liugaopo on 15/10/6.
 */
public class AddressListBean implements Serializable {

    private String code;
    private String msg;
    private List<AddressListChildBean> data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AddressListChildBean> getData() {
        return data;
    }

    public void setData(List<AddressListChildBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AddressListBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class AddressListChildBean implements Serializable {
        private String address_id;
        private String user_id;
        private String consignee;
        private String email;
        private String country;
        private String province;
        private String city;
        private String district;
        private String address;
        private String zipcode;
        private String tel;
        private String mobile;
        private String sign_building;
        private String best_time;
        private String is_default;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSign_building() {
            return sign_building;
        }

        public void setSign_building(String sign_building) {
            this.sign_building = sign_building;
        }

        public String getBest_time() {
            return best_time;
        }

        public void setBest_time(String best_time) {
            this.best_time = best_time;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        @Override
        public String toString() {
            return "AddressListChildBean{" +
                    "address_id='" + address_id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", consignee='" + consignee + '\'' +
                    ", email='" + email + '\'' +
                    ", country='" + country + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", address='" + address + '\'' +
                    ", zipcode='" + zipcode + '\'' +
                    ", tel='" + tel + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", sign_building='" + sign_building + '\'' +
                    ", best_time='" + best_time + '\'' +
                    ", is_default='" + is_default + '\'' +
                    '}';
        }


    }

}
