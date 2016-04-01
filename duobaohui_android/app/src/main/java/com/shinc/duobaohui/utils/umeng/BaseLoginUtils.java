package com.shinc.duobaohui.utils.umeng;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.Map;
import java.util.Set;

/**
 * Created by efort on 15/7/13.
 * 第三方登录的实现类
 */
public class BaseLoginUtils {
    UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    private static BaseLoginUtils loginUtils;
    private static Activity activity;

    /**
     * 唯一构造
     *
     * @param activity
     */
    public BaseLoginUtils(Activity activity) {
        BaseLoginUtils.activity = activity;
    }

    /**
     * 获取对象
     *
     * @param activity
     * @return
     */
    public static BaseLoginUtils getInstance(Activity activity) {
        if (loginUtils == null) {
            loginUtils = new BaseLoginUtils(activity);
        } else {
            BaseLoginUtils.activity = activity;
        }
        return loginUtils;
    }

    public void loginForWeixin() {
        String appId = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
        UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
        wxHandler.addToSocialSDK();

        login(SHARE_MEDIA.WEIXIN);
    }

    public void loginForQQ() {
//参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();
        login(SHARE_MEDIA.QQ);
    }

    public void loginForSina() {
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        login(SHARE_MEDIA.SINA);
    }

    /**
     * @param platform 平台
     */
    public void login(SHARE_MEDIA platform) {

        mController.doOauthVerify(activity, platform, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                Log.e(platform.name(), e.toString());
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
                    Toast.makeText(activity, "授权成功.", Toast.LENGTH_SHORT).show();
                    getOtherLoginInfo(platform);
                } else {
                    Toast.makeText(activity, "授权失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(activity, "取消登陆", Toast.LENGTH_SHORT).show();
            }


        });


    }

    private static SHARE_MEDIA platform;

    /**
     * @param platform
     */
    private void getOtherLoginInfo(SHARE_MEDIA platform) {
        BaseLoginUtils.platform = platform;

        mController.getPlatformInfo(activity, platform, new SocializeListeners.UMDataListener() {
            @Override
            public void onStart() {
                Toast.makeText(activity, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                if (status == 200 && info != null) {
                    //todo: 获得了第三方信息
                    StringBuilder sb = new StringBuilder();
                    Set<String> keys = info.keySet();
                    for (String key : keys) {
                        sb.append(key + "=" + info.get(key).toString() + "\r\n");
                    }
                    Log.d("TestData", sb.toString());
                } else {
                    Log.d("TestData", "发生错误：" + status);
                }
            }
        });
    }

    /**
     * sso 必须调用这个方法在 onActivityResult 方法中调用
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 登出第三方信息
     */
    public void logout() {

        mController.deleteOauth(activity, platform,
                new SocializeListeners.SocializeClientListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(int status, SocializeEntity entity) {
                        if (status == 200) {
                            Toast.makeText(activity, "登出成功.",
                                    Toast.LENGTH_SHORT).show();
                            platform = null;
                        } else {
                            Toast.makeText(activity, "登出失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}

