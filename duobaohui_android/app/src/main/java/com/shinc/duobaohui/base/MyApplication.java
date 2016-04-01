package com.shinc.duobaohui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.shinc.duobaohui.MainActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.event.UmPhshMsgEvent;
import com.shinc.duobaohui.event.UtilsEvent;
import com.shinc.duobaohui.utils.DeviceFactory;
import com.shinc.duobaohui.utils.PhoneUtil;
import com.shinc.duobaohui.utils.SharedPreferencesUtils;
import com.shinc.duobaohui.utils.icon.LocalImageHelper;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.IUmengUnregisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by wangmingxing on 15/6/25.
 */
public class MyApplication extends Application {

    //友盟推送
    public static final String CALLBACK_RECEIVER_ACTION = "callback_receiver_action";
    private PushAgent mPushAgent;
    public static IUmengRegisterCallback mRegisterCallback;
    public static IUmengUnregisterCallback mUnregisterCallback;

    //Activity容器----用来做主题切换-夜间模式，所有的activity：recreate
    private List<Activity> list = new LinkedList<>();

    private static MyApplication myApplication;

    private static final String TAG = MyApplication.class.getSimpleName();
    //singleton
    private static MyApplication appContext = null;
    private Display display;
    public static int ImageViewNum = 9;//最多选几张
    public static int dyNamicNum = 0;//已经选了几张

    public static String keyWord = "shinc";//keyWord;

    /* 是否刷新地址列表（AddressListActivity)，增加地址，或者修改地址后刷新*/
    public static boolean is_Resume = false;
    //兑奖
    public static boolean is_Winner_refreash = false;

    //充值后，记录和余额是否刷新
    public static boolean recharge = false;

    public static SharedPreferencesUtils spUtils;

    /*在微信后调中，判断支付的类型*/
    public static int WX_TYPE;

    public MyApplication() {
        // TODO Auto-generated constructor stub
    }

    public static MyApplication getInstance() {
        if (null == myApplication) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public List<Activity> getList() {
        return list;
    }

    public void removeActivity(Activity activity) {
        if (list.contains(activity)) {
            list.remove(activity);
        }
    }

    /**
     * 关闭自某个activity之后的所有activity
     *
     * @param activity
     */
    public void destoryActivitys(Activity activity) {
        if (list.contains(activity)) {
            for (int i = list.size() - 1; i > list.indexOf(activity); i++) {
                list.get(i).finish();
                list.remove(i);
            }
        }
    }

    //遍历所有Activity并recreate,在需要实现效果的地方【按钮监听】，添加MyApplication.getInstance().recreateAll();
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void recreateAll() {
        for (Activity activity : list) {
            activity.recreate();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        spUtils = new SharedPreferencesUtils(this, Constant.SP_LOGIN);
        initDeviceInfo();
        onYMCreate();

        appContext = MyApplication.this;
        init();


    }

    /**
     * 初始化设备信息；
     */
    private void initDeviceInfo() {
        SharedPreferencesUtils deviceSpUtils = new SharedPreferencesUtils(this, Constant.SP_DEVICE);
        deviceSpUtils.add(Constant.DEVICE_ID, new DeviceFactory().currentDeviceMark(this));
        deviceSpUtils.add(Constant.PHONE_COMPANY, PhoneUtil.getInstance(this).getBrand());
        deviceSpUtils.add(Constant.PHONE_MODEL, PhoneUtil.getModel());
    }

    /*
    * 友盟 推送
    * */
    public void onYMCreate() {
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);
        mPushAgent.onAppStart();
        mPushAgent.enable();

        final String userId = spUtils.get(Constant.SP_USER_ID, "");
        if (!TextUtils.isEmpty(userId)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mPushAgent.addExclusiveAlias(userId, "userId");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }


        mRegisterCallback = new IUmengRegisterCallback() {
            @Override
            public void onRegistered(String registrationId) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(CALLBACK_RECEIVER_ACTION);
                sendBroadcast(intent);
            }
        };
//
        mPushAgent.setRegisterCallback(mRegisterCallback);

        mUnregisterCallback = new IUmengUnregisterCallback() {
            @Override
            public void onUnregistered(String registrationId) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(CALLBACK_RECEIVER_ACTION);
                sendBroadcast(intent);
            }
        };
        /**
         * 该Handler是在IntentService中被调用，故
         * 1. 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * 2. IntentService里的onHandleIntent方法是并不处于主线程中，因此，如果需调用到主线程，需如下所示;
         * 	      或者可以直接启动Service
         * */
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler(getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub 自定义消息
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = msg.custom != null;
                        if (isClickOrDismissed) {
                            //统计自定义消息的打开
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                            getNotification(context, msg);

                        } else {
                            //统计自定义消息的忽略
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                    }
                });
            }

            @Override
            public Notification getNotification(Context context, UMessage msg) {
                //todo Auto-generated method stub 通知
                //自定义的参数
                if (msg.extra != null) {
                    //判断是否是中奖时的消息推送
                    Map<String, String> extra = msg.extra;
                    //判断是否是中奖时的消息推送
                    if (extra.get("msg_type") != null && extra.get("msg_type").contains("win")) {
                        EventBus.getDefault().post(new UmPhshMsgEvent("第" + extra.get("period_number") + "期", extra.get("goods_name")));
                    }
                }


//                    if (extra.get("display_type") != null && extra.get("display_type").equals("notification")) {
//                        //判断类型
//                        return super.getNotification(context, msg);
//                    } else if (extra.get("display_type") != null && extra.get("display_type").equals("message")) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("Event", "LoginInfo");
                PendingIntent pendingIntent = PendingIntent.getActivity(appContext, 1, intent, Notification.FLAG_AUTO_CANCEL);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(appContext);
                mBuilder.setContentTitle(msg.title)//设置通知栏标题
                        .setContentText(msg.text) //<span style="font-family: Arial;">/设置通知栏显示内容</span>
                        .setContentIntent(pendingIntent) //设置通知栏点击意图
                        .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                        .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                                //.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                        .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                        .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//                          .setVibrate(new long[]{0, 300, 500, 700})//实现效果：延迟0ms，然后振动300ms，在延迟500ms，接着在振动700ms。
//                                .setLights(0xff0000ff, 300, 0)
                        .setSmallIcon(R.drawable.icon_my_about_logo)//设置通知小ICON
                        .setLargeIcon(BitmapFactory.decodeResource(MyApplication.this.getResources(), R.drawable.icon_my_about_logo));
                Notification notification = mBuilder.build();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                EventBus.getDefault().post(new UtilsEvent("LOAD_INFO"));

                return mBuilder.build();
//                    } else {
//                        return super.getNotification(context, msg);
//                    }
//                } else {
//                    return super.getNotification(context, msg);
//                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 该Handler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {

            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

    }

    private void init() {
        initImageLoader(getApplicationContext());
        //本地图片辅助类初始化
        LocalImageHelper.init(this);
        if (display == null) {
            WindowManager windowManager = (WindowManager)
                    getSystemService(Context.WINDOW_SERVICE);
            display = windowManager.getDefaultDisplay();
        }
    }

    public static void initImageLoader(Context context) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .delayBeforeLoading(100)
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡
                .build();
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache"); //缓存文件的存放地址
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY);
        config.defaultDisplayImageOptions(defaultOptions);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCache(new LruMemoryCache(10 * 1024 * 1024));
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 100 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.threadPoolSize(5);
        //修改连接超时时间5秒，下载超时时间5秒
        config.imageDownloader(new BaseImageDownloader(appContext, 5 * 1000, 5 * 1000));
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 获取
     *
     * @return
     */
    public String getCachePath() {
        File cacheDir;
        cacheDir = Environment.getExternalStorageDirectory();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir.getAbsolutePath();
    }

    /**
     * @return
     * @Description： 获取当前屏幕1/4宽度
     */
    public int getQuarterWidth() {
        return display.getWidth() / 4;
    }

    /**
     * 获取当前处于最顶端的Activity；（校验当前的应用是否处于激活状态）；
     *
     * @param context
     * @return
     */
    public static String getTopActivity(Activity context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName topActivity = runningTaskInfos.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                //判断当前应用处于活动状态，再进行判断，找出当前顶端的Activity;
                return (runningTaskInfos.get(0).topActivity).toString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}