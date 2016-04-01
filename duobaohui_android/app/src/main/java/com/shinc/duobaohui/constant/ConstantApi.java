package com.shinc.duobaohui.constant;

/**
 * Created by liugaopo on 15/9/22.
 */
public class ConstantApi {
    /**
     * 线上地址
     */
    public static final String HOST = "http://dev.api.duobaohui.com/";
    //http://test.duobaohui.comdev.
    //http://api.duobaohui.com/
    //首页 参数 ?location=1

    public static final String SHARE_HOST = "http://www.duobaohui.com/";

    /**
     * 登陆接口
     */
    public static final String LOGIN = HOST + "user/login/login-tel";

    /**
     * 获取短信验证码的接口
     */
    public static final String GETVERIFYCODE = HOST + "user/register/get-verify";


    /**
     * 注册接口；
     */
    public static final String REGISTER = HOST + "user/register/tel-register";


    /**
     * 首页banner接口；
     */
    public static final String GET_BANNER = HOST + "activity/index/banner";


    public static final String GET_WINNER_LIST = HOST + "activity/record/newestwinninglist";

    /**
     * 活动奖品列表；
     */
    public static final String PRODUCT_LIST = HOST + "activity/index/list";

    /**
     * 参与记录；
     */
    public static final String TAKEPARTRECORD = HOST + "activity/index/period-user";


    /**
     * 忘记密码获取验证码的接口；
     */
    public static final String FORGET_PSW_GET_VERTIFY = HOST + "user/reset/get-verify";


    /**
     * 重置密码的接口；
     */
    public static final String RESET_PASSWORD = HOST + "user/reset/reg-tel";


    /**
     * 获取商品详情的接口；
     */
    public static final String GET_PRODUCT_DETAIL = HOST + "activity/index/detail";


    /**
     * 获取地址列表信息；
     */
    public static final String ADDRESSLIST = HOST + "user/address/address-list";

    /**
     * 添加收货地址
     */
    public static final String ADD_ADDRESS = HOST + "user/address/add-address";

    /**
     * 根据地址Id,修改该地址；
     */
    public static final String ADDREDD_EDIT = HOST + "user/address/address-edit";

    /**
     * 根据地址Id,删除该地址的接口；
     */
    public static final String ADDREDD_DELETE = HOST + "user/address/address-delete";

    /**
     * 中奖记录 ~ 获得总值；
     */
    public static final String WINNER_SUMNUM = HOST + "order/status/orderstatus";
    /**
     * 中奖记录；
     */
    public static final String WINNER_LIST = HOST + "order/status/orderlistbystatus";
    /**
     * 修改订单状态 ~ 待确认；
     */
    public static final String UPDATEORDERSTATUS = HOST + "order/status/updateorderstatus";
    /**
     * 夺宝记录；
     */
    public static final String RECROD = HOST + "activity/record/records";
    /**
     * 计算详情；
     */
    public static final String COUNTDETAIL = HOST + "activity/record/countdetail";

    /**
     * 夺宝次数；
     */
    public static final String LOTTERYTIMESLISTBYPERIOD = HOST + "activity/record/lotterytimeslistbyperiodid";
    public static final String LOTTERYCODELISTBYPERIOD = HOST + "activity/record/lotterycodelistbyperiodid";

    /**
     * 用户的夺宝好的详细信息；
     */
    public static final String USER_DUOBAO_CODE = HOST + "activity/index/detail-usercode";
    /**
     * 添加晒单；
     */
    public static final String ADD_SHAI_DAN = HOST + "user/shaidan/add-shaidan";
    /**
     * 获取晒单列表；
     */
    public static final String GET_SHAI_DAN = HOST + "user/shaidan/get-shaidan";
    /**
     * 获取token;
     */
    public static final String GET_TOKEN = HOST + "individual/user/get-token";

    /**
     * 充值；
     */
    public static final String RECHARGE = HOST + "recharge/recharge/recharge-record";

    /**
     * 往期揭晓；
     */
    public static final String WINNERHISTORY = HOST + "activity/record/winninghistorylist";

    /**
     * 图文详情接口；
     */
    public static final String GOOD_DESC = HOST + "activity/index/goods-desc";
    /**
     * 修改昵称
     */
    public static final String CHANGENICKNAME = HOST + "user/reset/reg-nickname";

    /**
     * 充值记录；
     */
    public static final String ORDER_RECHARGE = HOST + "recharge/recharge/order-recharge";

    /**
     * 修改头像；
     */
    public static final String UPDATE_HEAD = HOST + "user/reset/head-pic";
    /**
     * 等待揭晓；
     */
    public static final String WAIT_PUBLIC = HOST + "activity/index/result";

    /**
     * 时时彩的查询接口；
     */
    public static final String SHISHICAI = "http://caipiao.163.com/award/cqssc/";

    public static final String NOTIFIY_BUY = HOST + "alipay/notifyUrl/buy";


    /**
     * 夺宝币支付接口；
     */
    public static final String DUOBAOBI_BUY = HOST + "period/duobao/duobao-balance";


    /**
     * 添加订单的接口；
     */
    public static final String ADD_ORDER = HOST + "period/duobao/duobao-confirm";

    /**
     * 支付宝支付回调接口；
     */

    public static final String ALIPAY = HOST + "callback/alipay/callback";

    /**
     * 支付宝充值回调接口；
     */
    public static final String alipay_rechage = HOST + "alipay/notifyUrl/recharge";

    /**
     * 充值时获取充值的订单号；
     */
    public static final String GET_RECHAGE_ORDER = HOST + "recharge/recharge/recharge-confirm";
    /**
     * 微信充值时获取充值的订单号；
     */
    public static final String WXInfoOrder = HOST + "alipay/weixinapi/wx-pay";
    /**
     * 支付结果访问的接口；
     */
    public static final String PAY_RESULT = HOST + "alipay/notifyUrl/get-user-code-list";

    /**
     * 更新用户的信息；
     */
    public static final String GET_MOENY = HOST + "recharge/recharge/get-money";

    /**
     * 登陆阶段获取token;
     */
    public static final String GET_LOGIN_TOKEN = HOST + "user/token";

    /**
     * 意见反馈
     */
    public static final String add_opinion = HOST + "system/opinion/add-opinion";

    /**
     * 传给后台生成夺宝订单；
     */
    public static final String GETORDERID = HOST + "alipay/notifyUrl/buy-order";

    /**
     * 夺宝记录；
     */
    public static final String records = HOST + "activity/record/records";

    /**
     * 夺宝记录Tab上的提示数量接口；
     */
    public static final String recordsNum = HOST + "/activity/record/records-num";

    /**
     * 快速注册时候获取验证码；
     */
    public static final String FAST_LOGIN_GET_VERTIFY = HOST + "user/login/send-login-sms";


    /**
     * 快速登陆的登陆验证；
     */
    public static final String FAST_LOGIN = HOST + "user/login/login-sms";

    /*
    * TA的主页－夺宝纪录
    * */
    public static final String tauser_DuoBao = HOST + "tauser/duobao-record";
    /*
    * TA的主页－中奖纪录
    * */
    public static final String tauser_Win = HOST + "tauser/winning-record";
    /**
     * TA的主页－晒单记录；
     */
    public static final String tauser_ShowOrder = HOST + "tauser/shaidan-record";

    /**
     * TA的主页－Tab（num）
     */
    public static final String tauser_GetSum = HOST + "tauser/get-num";
    /**
     * 举报晒单接口；
     */
    public static final String REPROT_SHARE_ORDER = HOST + "report/report/report-shaidan";

    /**
     * 检查消息的事件戳；（用于辨别有咩有新的消息）；
     */
    public static final String CHECK_MSG_NOTICE = HOST + "system/notice/latest-date";


    /**
     * 分类数据接口（同时也是兼顾搜索）；
     */
    public static final String CATEGORY_SEARCH = HOST + "activity/index/list";

    /**
     * 获取分类数据的接口地址；
     */
    public static final String CATEGORY_MENU = HOST + "activity/index/category";
    /**
     * 通知；
     */
    public static final String NOTICE = HOST + "system/notice/system-notice-list";


    /**
     * 中奖纪录,V1.2
     */
    public static final String WinListRecrod = HOST + "activity/record/winning-records";
    /**
     * 中奖纪录 item详情,V1.2
     */
    public static final String WinListRecroDetail = HOST + "activity/record/winning-detail";
    //http://dev.api.duobaohui.com/activity/index/welcome
    public static final String INDEXWECOME = HOST + "activity/index/welcome";


    public static final String COUPONS_LIST = HOST + "redpacket/redpacket/get-redpacket-list";
    public static final String COUPONS_DETAILS = HOST + "redpacket/redpacket/get-redpacket-by-redpacketid";

    /**
     * 获取热搜词汇；
     */
    public static final String SEARCH_HOT = HOST + "";


    /**
     * 支付页面网络图片的获取；
     */
    public static final String RECHARGE_IMG = HOST + "redpacket/redpacket/recharge-banner";


    public static final String GETREDPCKETBYACTIVITYID = "redpacket/redpacket/get-redpacket-by-activityid";


    /**
     * 签到的Url地址；
     */
    public static final String SIGNIN_URL = HOST + "client/sign/sign-in";
    /**
     * 支付页面（支付宝/微信）支付的开关；
     */
    public static final String SWITCH_PAY = HOST + "client/config/pay";
}
