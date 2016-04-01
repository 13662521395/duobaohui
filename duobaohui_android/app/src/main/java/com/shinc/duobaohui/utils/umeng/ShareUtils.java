package com.shinc.duobaohui.utils.umeng;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.share.ShareOncilckListener;
import com.shinc.duobaohui.customview.share.SharePopLayout;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import static com.umeng.socialize.bean.SHARE_MEDIA.DOUBAN;
import static com.umeng.socialize.bean.SHARE_MEDIA.RENREN;

/**
 * Created by efort on 15/8/11.
 */
public class ShareUtils {
    private static Activity context;

    private String url;

    private UMImage umImage;

    private View view;
    // 首先在您的Activity中添加如下成员变量
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private static ShareUtils shareUtils;

    private ShareOncilckListener shareOncilckListener;

    private SharePopLayout menuWindow;

    private WeiXinShareContent weixinContent;

    private CircleShareContent circleMedia;

    private QQShareContent qqShareContent;

    private QZoneShareContent qzone;

    private SinaShareContent sina;

    public ShareUtils(Activity activity) {
        context = activity;
        init(context);
    }

    public static ShareUtils getInstance(Activity activity) {

        if (shareUtils == null) {
            shareUtils = new ShareUtils(activity);
        } else {
            context = activity;
        }

        return shareUtils;
    }

    /**
     * 图文分享方法参考上文，纯图片分享方法只需要去掉文本内容设置即可，代码如下：
     * 纯文本分享方法与纯图片类似，去掉分享图片即可
     *
     * @param shareValues 分享内容
     * @param title       分享title
     * @param imgUrl      图片
     * @param url         地址
     */
    public void shareProduct(View view, String shareValues, String title, String imgUrl, String url, String var) {

        this.url = url;
        this.view = view;
        // 设置分享内容
        mController.setShareContent(shareValues);
        //        // 设置分享图片, 参数2为图片的url地址
        umImage = new UMImage(context, imgUrl);
        umImage.setTargetUrl(url);

        umImage.setTitle(title);
        mController.setShareMedia(umImage);
        mController.setAppWebSite(RENREN, url);
        // 设置分享图片，参数2为本地图片的资源引用
        mController.setShareMedia(new UMImage(context, R.drawable.icon));

        //设置微信好友分享内容
        weixinContent = new WeiXinShareContent();
        //设置分享文字
        weixinContent.setShareContent(shareValues);
        //设置title
        weixinContent.setTitle(title);
        //设置分享内容跳转URL
        weixinContent.setTargetUrl(url);
        //设置分享图片
        weixinContent.setShareImage(new UMImage(context, imgUrl));
        mController.setShareMedia(weixinContent);

        //设置微信朋友圈分享内容
        circleMedia = new CircleShareContent();
        circleMedia.setShareContent(shareValues);
        //设置朋友圈title
        circleMedia.setTitle(title);
        circleMedia.setShareImage(new UMImage(context, imgUrl));
        circleMedia.setTargetUrl(url);
        mController.setShareMedia(circleMedia);

        qqShareContent = new QQShareContent();
        //设置分享文字
        qqShareContent.setShareContent(shareValues);
        //设置分享title
        qqShareContent.setTitle(title);
        //设置分享图片
        qqShareContent.setShareImage(new UMImage(context, imgUrl));
        //设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl(url);
        mController.setShareMedia(qqShareContent);

        qzone = new QZoneShareContent();
        //设置分享文字
        qzone.setShareContent(shareValues);
        //设置点击消息的跳转URL
        qzone.setTargetUrl(url);
        //设置分享内容的标题
        qzone.setTitle(title);
        //设置分享图片
        qzone.setShareImage(new UMImage(context, imgUrl));
        mController.setShareMedia(qzone);

        sina = new SinaShareContent();
        //设置分享文字
        sina.setShareContent(shareValues);
        //设置点击消息的跳转URL
        sina.setTargetUrl(url);
        //设置分享内容的标题
        sina.setTitle(title);
        //设置分享图片
        sina.setShareImage(new UMImage(context, imgUrl));
        mController.setShareMedia(sina);

        onClick(false, var);

    }


    /**
     * 分享应用
     */
    public void shareApp(String var) {
        // 设置分享内容
        mController.setShareMedia(new UMImage(context, R.drawable.ic_launcher));
        mController.setShareContent("哈尔冰小树商城；优惠大促销！");
        mController.setAppWebSite("http://www.baidu.com");

        onClick(false, var);
    }

    /**
     * 调用这个方法出现分享框  在此之前请先调用 onActivityResult  shareProduct 方法
     *
     * @param isLoginer
     */
    public void onClick(boolean isLoginer, String var) {
        // 是否只有已登录用户才能打开分享选择页
        if (menuWindow == null) {
            menuWindow = new SharePopLayout(context, onShareClickListener);
        }
        menuWindow.setTvWord(var);
        menuWindow.setAnimationStyle(R.style.popupAnimation);
        //显示窗口
        try {
            menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
//            menuWindow.backgroundAlpha(0.5f, context);

            WindowManager.LayoutParams params = context.getWindow().getAttributes();
            params.alpha = 0.7f;

            context.getWindow().setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        menuWindow.setOnCancelListener(new SharePopLayout.OnCancelListener() {
            @Override
            public void onCancel() {
                if (shareOncilckListener != null) {
                    shareOncilckListener.onComplete(null, 0, null);
                }
                WindowManager.LayoutParams params = context.getWindow().getAttributes();
                params.alpha = 1.0f;

                context.getWindow().setAttributes(params);
            }
        });

        menuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //todo
                WindowManager.LayoutParams params = context.getWindow().getAttributes();
                params.alpha = 1.0f;

                context.getWindow().setAttributes(params);
            }
        });
    }

    SharePopLayout.OnShareClickListener onShareClickListener = new SharePopLayout.OnShareClickListener() {
        @Override
        public void onClick(SHARE_MEDIA share_media) {

            if (share_media != null) {
                switch (share_media) {
                    case WEIXIN_CIRCLE:

                        url = url + "&channel=1";
                        umImage.setTargetUrl(url);
                        circleMedia.setTargetUrl(url);
                        break;
                    case WEIXIN:
                        url = url + "&channel=2";
                        umImage.setTargetUrl(url);
                        weixinContent.setTargetUrl(url);
                        break;
                    case SINA:
                        url = url + "&channel=3";
                        umImage.setTargetUrl(url);
                        sina.setTargetUrl(url);
                        break;
                    case QQ:
                        url = url + "&channel=4";
                        umImage.setTargetUrl(url);
                        qqShareContent.setTargetUrl(url);
                        break;
                    case QZONE:
                        url = url + "&channel=5";
                        umImage.setTargetUrl(url);
                        qzone.setTargetUrl(url);
                        break;
                }


                selector(share_media);
            }
        }
    };

    /**
     * 自定义分享的点击事件；shareProduct需要在这之前调用
     *
     * @param share_media
     */
    public void selector(final SHARE_MEDIA share_media) {

        mController.postShare(context, share_media, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
            }
        });
    }

    /**
     * 初始化分享相关内容   去掉无用平台，添加自定义平台 设置sso
     *
     * @param activity
     */
    public void init(Activity activity) {
        mController.getConfig().removePlatform(RENREN, DOUBAN);
//        setResult(activity);

        String appId = "wx82644dcb3aeafdf0";//   wx72146d9976a01133
        String appSecret = "892bb3d291a60260d01f19608b3c5c24";//  9d2a97ab531d932125182d0d03f154ea
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        //qq 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, "1104899820", "MpmFFonOT4OPFPOx");
        qqSsoHandler.addToSocialSDK();
        //qq空间 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, "1104899820", "MpmFFonOT4OPFPOx");
        qZoneSsoHandler.addToSocialSDK();
        //sina 微博
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSinaCallbackUrl("http://sns.whalecloud.com/sina2/callback");
        mController.getConfig().closeToast();

        EmailHandler emailHandler = new EmailHandler();
        emailHandler.addToSocialSDK();
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

    public void setShareOncilckListener(ShareOncilckListener shareOncilckListener) {
        this.shareOncilckListener = shareOncilckListener;
    }

    /**
     * 注册回调
     *
     * @param context
     */

    private void setResult(Activity context) {
        SocializeListeners.SnsPostListener mSnsPostListener = new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int stCode, SocializeEntity entity) {


            }
        };
        mController.registerListener(mSnsPostListener);
    }
}
