package com.shinc.duobaohui.constant;


/**
 * 名称：Constant
 * 作者：zhaopl 时间: 15/9/18.
 * 实现的主要功能：
 * 保存公用数据（常量）；
 */
public class Constant {
    /**
     * 第三方登陆的QQ登陆代号
     */
    public static final int LOGIN_OTHERWAY_QQ = 0x0a01;
    /**
     * 第三方登陆的sina登陆代号
     */
    public static final int LOGIN_OTHERWAY_SINA = 0x0a02;


    public static final String SP_LOGIN = "LOGIN";

    public static final String SP_DEVICE="SP_DEVICE";

    public static final String DEVICE_ID = "DEVICE_ID";//设备Id；

    public static final String PLATFORM = "Android";//平台信息；

    public static final String PHONE_COMPANY ="PHONE_COMPANY";//手机品牌；

    public static final String PHONE_MODEL = "PHONE_MODEL";//手机型号；

    /**
     * 存储常用数据；
     */
    public static final String COMMON_DATA = "DATA";

    /**
     * 存储是不是首次安装；
     */
    public static final String IFFIRST = "ISFIRST";


    public static final String MESSAGE_NOTICE = "MESSAGE_NOTICE";


    public static final String SP_USER_ID = "USER_ID";
    public static final String SP_TEL = "TEL";
    public static final String SP_REAL_NAME = "REAL_NAME";
    public static final String SP_NICK_NAME = "NICK_NAME";

    public static final String SH_ID = "SH_ID";
    public static final String SIGNATURE = "SIGNATURE";
    public static final String HEAD_PIC = "HEAD_PIC";
    public static final String MONEY = "MONEY";
    public static final String SP_SESSION_ID = "SESSION_ID";//session_id

    public final static int TEXT_SIZE_MIDDLE = 2;//正常字体；

    public final static int TEXT_SIZE_SMALL = 1;//小字体；

    public final static int TEXT_SIZE_BIG = 3;//大字体；

    public final static int UNPREPAREDEXIT = 1;

    public final static String QINIUSCALE75 = "?imageMogr2/thumbnail/!75p";


    public final static String QINIU320_220 = "?imageMogr2/thumbnail/!480x330r";

    public final static String QINIU30_30 = "?imageMogr2/thumbnail/!90x90r";

    public final static String QINIU160_160 = "?imageMogr2/thumbnail/!240x240r";//1080

    public final static String QINIU270_270 = "?imageMogr2/thumbnail/!270x270r";//1080

    public final static String QINIU80_80 = "?imageMogr2/thumbnail/!80x80r";

    public static String BUY_ID;

    public static String FORM_TOKEN;

    /**
     * 生成的订单号；
     */
    public static String BUY_ORDER_ID;

    /**
     * 支付方式的标示；
     */
    public static final int ALIPAY = 0;

    public static final int WEIXIN = 1;

    public static final int BANKCARD = 2;


    /*
    * lgp
    * */
    //支付
    public static final int WXPAY = 0;
    //充值
    public static final int WXRECHARGE = 1;

    /**
     * 支付类型；
     */

    //充值；
    public static final int RECHARGE = 3;
    //直接购买；
    public static final int DIRECTBUY = 4;

    public static boolean RELOAD = false;

    /**
     * 弹出中奖dialog的弹窗，判断是否登录，若是未登录，则传值给Login，登录完成后跳转到中间页面
     */
    public static final String WinDialog = "WinDialog";


}