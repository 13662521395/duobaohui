package com.shinc.duobaohui.utils.web;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.PreferencesCookieStore;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.utils.PhoneUtil;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 名称：MyHttpUtils
 * 作者：zhaopl 时间: 15/10/7.
 * 实现的主要功能：
 * 对xUtils中HttpUtils进行封装，配置一些全局变量；
 */
public class MyHttpUtils {

    private HttpUtils httpUtils;

    private JsonParser parser;

    private static MyHttpUtils myHttpUtils;

    private SharedPreferencesUtils sharedPreferencesUtils;


    /**
     * 通过单例模式进行的操作；
     *
     * @return
     */
    public static MyHttpUtils getInstance() {

        if (myHttpUtils == null) {
            myHttpUtils = new MyHttpUtils();
        }

        return myHttpUtils;
    }

    public MyHttpUtils() {
        init();
    }

    void init() {
        httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(2000);//设置缓存时间为2秒；
        httpUtils.configTimeout(30 * 1000);
    }

    /**
     * @param method
     * @param url
     * @param requestParams
     * @param isSetCookie       是否进行cookie的设置；true 是；false 否；
     * @param isAutoHandleCode  是否走全局Code处理？ true 是；false 否；
     * @param context
     * @param myRequestCallBack
     */
    public void send(HttpRequest.HttpMethod method, String url, RequestParams requestParams, final boolean isSetCookie, final boolean isAutoHandleCode, final Context context, final MyRequestCallBack myRequestCallBack) {

        //对参数进行全局性的操作；
        handleRequestParams(context, requestParams);

        handleSetCookie(isSetCookie, context);//根据标示进行Cookie的处理；

        httpUtils.send(method, url, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                handleCookie(isSetCookie, context);


                if (isAutoHandleCode) {
                    VerifyCode(responseInfo, myRequestCallBack, context);
                } else {
                    myRequestCallBack.onSuccess(responseInfo);//暂时不管如何都将结果返回到Activity层。保障原有逻辑不改变；
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

                myRequestCallBack.onFailure(e, s);
            }
        });
    }

    /**
     * 对参数进行加工的方法；
     *
     * @param requestParams
     */
    private void handleRequestParams(Context context, RequestParams requestParams) {

        //添加一些预处理参数；

        SharedPreferencesUtils spDevice = new SharedPreferencesUtils(context, Constant.SP_DEVICE);

        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = new SharedPreferencesUtils(context, Constant.SP_LOGIN);
        }
        requestParams.addBodyParameter("deviceId", spDevice.get(Constant.DEVICE_ID, "null"));
        requestParams.addBodyParameter("userId", sharedPreferencesUtils.get(Constant.SP_USER_ID, "0"));
        requestParams.addBodyParameter("platform", "Android");
        requestParams.addBodyParameter("phoneCompany", spDevice.get(Constant.PHONE_COMPANY, "null"));
        requestParams.addBodyParameter("phoneModel", spDevice.get(Constant.PHONE_MODEL, "null"));
        requestParams.addBodyParameter("osVersion", PhoneUtil.getVersion());
        requestParams.addBodyParameter("channel", PhoneUtil.getInstance(context).getMetaData("UMENG_CHANNEL"));
        requestParams.addBodyParameter("version", PhoneUtil.getInstance(context).getSoftVersion());
        requestParams.addBodyParameter("netType", PhoneUtil.getInstance(context).getNetworkType());

    }

    /**
     * 根据用户的配置确定是否配置Cookie到请求头中；
     *
     * @param isSetCookie
     * @param context
     */
    private void handleSetCookie(boolean isSetCookie, Context context) {
        if (isSetCookie) {
            //如果是确定设置Cookie的话，赋值；
            try {
                PreferencesCookieStore cookieStore = new PreferencesCookieStore(context);
                if (cookieStore != null) {
                    httpUtils.configCookieStore(cookieStore);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据用户的配置进行cookie的存取；
     *
     * @param isSetCookie
     * @param context
     */
    private void handleCookie(boolean isSetCookie, Context context) {
        if (isSetCookie) {
            DefaultHttpClient httpClient = (DefaultHttpClient) httpUtils.getHttpClient();

            CookieStore cs = httpClient.getCookieStore();
            List<Cookie> cookies = cs.getCookies();

            PreferencesCookieStore preferencesCookieStore = new PreferencesCookieStore(
                    context);
            for (Cookie cookie : cookies) {
                preferencesCookieStore.addCookie(cookie);
            }
        }
    }

    /**
     * 全局校验Code码，用于处理全局的错误问题；
     *
     * @param responseInfo
     * @param myRequestCallBack
     * @param context
     */
    private void VerifyCode(ResponseInfo<String> responseInfo, MyRequestCallBack myRequestCallBack, Context context) {
        String result = responseInfo.result;

        if (parser == null) {
            parser = new JsonParser();
        }
        try {
            JsonObject jsonObject = parser.parse(result).getAsJsonObject();
            String code = jsonObject.get("code").getAsString();

            if (!TextUtils.isEmpty(code)) {
                codeHandle(responseInfo, myRequestCallBack, context, code);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对于不同的错误码，进行全局配置；
     * 返回码为1的时候：进行回调；
     * 返回码为999999的时候，进行删除本地用户数据的操作，并提醒用户登陆；
     *
     * @param responseInfo
     * @param myRequestCallBack
     * @param context
     * @param code
     */
    private void codeHandle(ResponseInfo<String> responseInfo, MyRequestCallBack myRequestCallBack, Context context, String code) {
        switch (code) {
            case "1":
                myRequestCallBack.onSuccess(responseInfo);
                break;

            case "999999":
                //如果是用户session出现错误，清除本地用户数据，进行提示。
                if (sharedPreferencesUtils == null) {
                    sharedPreferencesUtils = new SharedPreferencesUtils(context, Constant.SP_LOGIN);
                }
                sharedPreferencesUtils.deleteAll();//清除本地用户数据；
                EventBus.getDefault().post(new UtilsEvent("RELOAD_HEAD"));
                Toast.makeText(context, "登陆信息失效，请重新登陆！", Toast.LENGTH_SHORT).show();
                break;
            default:
                try {
                    JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();
                    String msg = jsonObject.get("msg").getAsString();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                break;

        }
    }


    /**
     * 自定义的回调接口；
     */
    public interface MyRequestCallBack {

        void onSuccess(ResponseInfo<String> responseInfo);

        void onFailure(HttpException e, String s);
    }

}
