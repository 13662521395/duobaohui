package com.shinc.duobaohui.bean;

/**
 * Created by liugaopo on 15/11/25.
 */
public class WinItemDetailBean {

/*    //详情
    {
        - code: 1,
            - msg: "成功",
            - data:
        - {
            - order_id: 2846,订单主键
            - order_status: 0,订单状态
            - shipping_status: 0,收货状态
            - is_shaidan: "0",是否晒单
            - express_company: null,快递公司
            - invoice_no: "",快递单号
            - consignee: "",收货人
            - address: "”,收货地址
            - mobile: "”,收货人手机号
            - add_time: "2015-11-19 18:52:01",中奖时间
            - confirm_time: "0000-00-00 00:00:00",确认订单时间
            - shipping_time: "0000-00-00 00:00:00”,发货时间
            - receive_time: null,收货时间
            - status: "0",当前状态，0:待领奖，1:派奖中，2:待收货，3:未晒单，4:一晒单
            - user_id: 39163,用户id
            - goods_name: "索尼（SONY） ILCE-5000L 微单单镜套机（16-50mm镜头 a5000L/α5000）",商品名称
            - goods_img: "http://7xlbf0.com1.z0.glb.clouddn.com/o_1a3644svlb5o1io81g1a132k1fr39.jpg",商品图片
            - real_need_times: 2499,总需人次
            - period_number: 5,第几期
            - luck_code: "10002292",幸运号码
            - luck_code_create_time: "2015-11-19 18:52:01",
            - pre_luck_code_create_time: "2015-11-19 18:57:40",揭晓时间
            - total_times: “1950"本期参与
            - }

    }*/

    private String code;
    private String msg;
    private WinItemListChildData data;

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

    public WinItemListChildData getData() {
        return data;
    }

    public void setData(WinItemListChildData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WinItemDetailBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public class WinItemListChildData {

        private String sh_activity_period_id;

        private String order_id;
        private String order_status;
        private String shipping_status;
        private String is_shaidan;
        private String express_company;
        private String invoice_no;
        private String consignee;
        private String address;
        private String mobile;
        private String add_time;
        private String confirm_time;
        private String shipping_time;
        private String receive_time;
        private String status;
        private String user_id;
        private String goods_name;
        private String goods_img;
        private String real_need_times;
        private String period_number;
        private String luck_code;
        private String luck_code_create_time;
        private String pre_luck_code_create_time;
        private String total_times;

        private String address_id;
        private String address_consignee;
        private String address_district;
        private String address_address;
        private String address_mobile;
        private String shaidan_create_time;

        private String goods_id;

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

        public String getShipping_status() {
            return shipping_status;
        }

        public void setShipping_status(String shipping_status) {
            this.shipping_status = shipping_status;
        }

        public String getIs_shaidan() {
            return is_shaidan;
        }

        public void setIs_shaidan(String is_shaidan) {
            this.is_shaidan = is_shaidan;
        }

        public String getExpress_company() {
            return express_company;
        }

        public void setExpress_company(String express_company) {
            this.express_company = express_company;
        }

        public String getInvoice_no() {
            return invoice_no;
        }

        public void setInvoice_no(String invoice_no) {
            this.invoice_no = invoice_no;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getConfirm_time() {
            return confirm_time;
        }

        public void setConfirm_time(String confirm_time) {
            this.confirm_time = confirm_time;
        }

        public String getShipping_time() {
            return shipping_time;
        }

        public void setShipping_time(String shipping_time) {
            this.shipping_time = shipping_time;
        }

        public String getReceive_time() {
            return receive_time;
        }

        public void setReceive_time(String receive_time) {
            this.receive_time = receive_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getPeriod_number() {
            return period_number;
        }

        public void setPeriod_number(String period_number) {
            this.period_number = period_number;
        }

        public String getLuck_code() {
            return luck_code;
        }

        public void setLuck_code(String luck_code) {
            this.luck_code = luck_code;
        }

        public String getLuck_code_create_time() {
            return luck_code_create_time;
        }

        public void setLuck_code_create_time(String luck_code_create_time) {
            this.luck_code_create_time = luck_code_create_time;
        }

        public String getPre_luck_code_create_time() {
            return pre_luck_code_create_time;
        }

        public void setPre_luck_code_create_time(String pre_luck_code_create_time) {
            this.pre_luck_code_create_time = pre_luck_code_create_time;
        }

        public String getTotal_times() {
            return total_times;
        }

        public void setTotal_times(String total_times) {
            this.total_times = total_times;
        }

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getAddress_consignee() {
            return address_consignee;
        }

        public void setAddress_consignee(String address_consignee) {
            this.address_consignee = address_consignee;
        }

        public String getAddress_district() {
            return address_district;
        }

        public void setAddress_district(String address_district) {
            this.address_district = address_district;
        }

        public String getAddress_address() {
            return address_address;
        }

        public void setAddress_address(String address_address) {
            this.address_address = address_address;
        }

        public String getAddress_mobile() {
            return address_mobile;
        }

        public void setAddress_mobile(String address_mobile) {
            this.address_mobile = address_mobile;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getShaidan_create_time() {
            return shaidan_create_time;
        }

        public void setShaidan_create_time(String shaidan_create_time) {
            this.shaidan_create_time = shaidan_create_time;
        }

        public String getSh_activity_period_id() {
            return sh_activity_period_id;
        }

        public void setSh_activity_period_id(String sh_activity_period_id) {
            this.sh_activity_period_id = sh_activity_period_id;
        }
    }


}
