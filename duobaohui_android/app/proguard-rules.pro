# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/tong/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5   #指定代码的压缩级别；

-dontusemixedcaseclassnames #是否使用大小写混合；

-dontpreverify  #混淆时是否做预校验；

-verbose    #混淆时是否记录日志；

-dontskipnonpubliclibraryclasses
-ignorewarnings

-dontwarn android.support.**
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*    #混淆时所采用的算法；

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆

    public <init>(android.content.Context, android.util.AttributeSet, int);

}
-keepclassmembers class * extends android.app.Activity {  # 保持自定义控件类不被混淆
    public void *(android.view.View);
}

-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
 public static **[] values();
 public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
 public static final android.os.Parcelable$Creator *;
}


#友盟分享不混淆；
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**


-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**

-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**

-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class org.android.agoo.service.* {*;}

-keep class org.android.spdy.**{*;}

-keep public class com.shinc.duobaohui.R$*{
    public static final int *;
}

#友盟统计不混淆；
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

#友盟推送不被混淆
-keep class com.umeng.message.* {
        public <fields>;
        public <methods>;
}

-keep class com.umeng.message.protobuffer.* {
        public <fields>;
        public <methods>;
}

-keep class com.squareup.wire.* {
        public <fields>;
        public <methods>;
}

-keep class com.umeng.message.local.* {
        public <fields>;
        public <methods>;
}
-keep class org.android.agoo.impl.*{
        public <fields>;
        public <methods>;
}

-keep class com.square.** {*;}

-keep class org.android.** {*;}


-keep,allowshrinking class org.android.agoo.service.* {
    public <fields>;
    public <methods>;
}

-keep,allowshrinking class com.umeng.message.* {
    public <fields>;
    public <methods>;
}



#保持alipayjar不被混淆；
-keep class com.alipay.** {*;}
-dontwarn com.alipay.**
-keep class ta.utdid2.** {*;}
-dontwarn ta.utdid2.**
-keep class ut.device.** {*;}
-dontwarn ut.device.**

#保持async-http jar不被混淆；

-keep class com.loopj.android.http.** {*;}

#保持eventBus jar不被混淆；
-keep class de.greenrobot.event.** {*;}

#保持 gson jar不被混淆；
-keep class com.google.gson.** {*;}

#保持 httpmine jar不被混淆；

-keep class org.apache.http.entity.mime.** {*;}

-keep class org.jsoup.** {*;}

-keep class com.qiniu.android.** {*;}

-keep class com.nostra13.universalimageloader.** {*;}

-keep class test.com.nostra13.universalimageloader.core.** {*;}

-keep class test-gen.com.nostra13.universalimageloader.** {*;}

-keep class com.lidroid.xutils.** {*;}

#保持微信sdk不给混淆

-keep class com.tencent.mm.sdk.** {*;}


#EventBus混淆配置；由于EventBus通过反射来查找对象，如果混淆了onEvent开头的函数，会产生直接崩溃的现象；
-keepclassmembers class ** {
    public void onEvent*(**);
}

#管理Fragment的时候用到了反射，所以配置fragment不混淆

-keepclasseswithmembernames class com.shinc.duobaohui.fragment.**{
    public <fields>;
    public <methods>;
}

#Bean文件不应该被混淆，使用Gson解析数据的时候需要用到反射，所以不应该让Model层的Bean被混淆掉；
-keep class com.shinc.duobaohui.bean.** { *; }


#对于集成了Serializable的类，用于序列化的对象也不应该被混淆掉；
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}

## Only required if you use AsyncExecutor
#-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}
