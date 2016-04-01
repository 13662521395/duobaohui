package com.shinc.duobaohui.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by efort on 15/10/12.
 * 获取到的晒单列表信息
 */
public class ShaiDanBean implements Serializable {
    private String code;
    private String msg;
    private List<ShaiDanItem> data;

    public class ShaiDanItem implements Serializable {
        private String id;
        private String sh_user_id;
        private String content;
        private String title;
        private String sh_order_id;
        private String sh_goods_id;

        private List<String> img;
        private UserInfo userInfo;
        private OrderInfo orderInfo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSh_user_id() {
            return sh_user_id;
        }

        public void setSh_user_id(String sh_user_id) {
            this.sh_user_id = sh_user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSh_order_id() {
            return sh_order_id;
        }

        public void setSh_order_id(String sh_order_id) {
            this.sh_order_id = sh_order_id;
        }

        public String getSh_goods_id() {
            return sh_goods_id;
        }

        public void setSh_goods_id(String sh_goods_id) {
            this.sh_goods_id = sh_goods_id;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }
    }

    public class UserInfo implements Serializable {
        private String id;
        private String tel;
        private String real_name;
        private String nick_name;
        private String head_pic;
        private String locked;
        private String sh_id;
        private String email;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getLocked() {
            return locked;
        }

        public void setLocked(String locked) {
            this.locked = locked;
        }

        public String getSh_id() {
            return sh_id;
        }

        public void setSh_id(String sh_id) {
            this.sh_id = sh_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "id='" + id + '\'' +
                    ", tel='" + tel + '\'' +
                    ", real_name='" + real_name + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", head_pic='" + head_pic + '\'' +
                    ", locked='" + locked + '\'' +
                    ", sh_id='" + sh_id + '\'' +
                    ", email='" + email + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    public class OrderInfo implements Serializable {
        private String id;
        private String order_sn;
        private String period_number;
        private String goods_name;
        private String goods_img;
        private String real_need_times;
        private String times;
        private String luck_code;
        private String a_code_create_time;
        private String luck_code_create_time;
        private String luck_corder_statusode_create_time;
        private String shipping_status;
        private String user_id;
        private String sh_activity_period_id;

        private String order_id;
        private String order_status;

        private String ip;
        private String ip_address;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getIp_address() {
            return ip_address;
        }

        public void setIp_address(String ip_address) {
            this.ip_address = ip_address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getPeriod_number() {
            return period_number;
        }

        public void setPeriod_number(String period_number) {
            this.period_number = period_number;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getReal_need_times() {
            return real_need_times;
        }

        public void setReal_need_times(String real_need_times) {
            this.real_need_times = real_need_times;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getLuck_code() {
            return luck_code;
        }

        public void setLuck_code(String luck_code) {
            this.luck_code = luck_code;
        }

        public String getA_code_create_time() {
            return a_code_create_time;
        }

        public void setA_code_create_time(String a_code_create_time) {
            this.a_code_create_time = a_code_create_time;
        }

        public String getLuck_code_create_time() {
            return luck_code_create_time;
        }

        public void setLuck_code_create_time(String luck_code_create_time) {
            this.luck_code_create_time = luck_code_create_time;
        }

        public String getLuck_corder_statusode_create_time() {
            return luck_corder_statusode_create_time;
        }

        public void setLuck_corder_statusode_create_time(String luck_corder_statusode_create_time) {
            this.luck_corder_statusode_create_time = luck_corder_statusode_create_time;
        }

        public String getShipping_status() {
            return shipping_status;
        }

        public void setShipping_status(String shipping_status) {
            this.shipping_status = shipping_status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSh_activity_period_id() {
            return sh_activity_period_id;
        }

        public void setSh_activity_period_id(String sh_activity_period_id) {
            this.sh_activity_period_id = sh_activity_period_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }
    }

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

    public List<ShaiDanItem> getData() {
        return data;
    }

    public void setData(List<ShaiDanItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShaiDanBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

