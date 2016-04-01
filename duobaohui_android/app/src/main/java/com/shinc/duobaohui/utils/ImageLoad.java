package com.shinc.duobaohui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Name: ImageLoad
 * Author: chenkuan
 * Function:
 * Date: 2015/7/15
 */
public class ImageLoad {

    public static ImageLoad imageLoad;

    ImageLoader imageLoader;

    // DisplayImageOptions用于设置图片显示的类
    DisplayImageOptions defaultOptions;

    public static ImageLoad getInstance(Context context) {
        if (imageLoad == null)
            imageLoad = new ImageLoad(context);
        return imageLoad;
    }


    public ImageLoad(Context context) {

        defaultOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .delayBeforeLoading(100)
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                .memoryCacheSizePercentage(13)
                .diskCacheSize(50 * 1024 * 1024) // 100 MiB
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheFileCount(100)
                .threadPoolSize(4)//
                .threadPriority(Thread.MIN_PRIORITY - 2)
//                .writeDebugLogs()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    public void setImageToView(String url, ImageView imageView, ImageLoadingListener
            imageLoadingListener) {
        imageLoader.displayImage(url, imageView, imageLoadingListener);
    }

    public void setImageToView(String url, ImageView imageView) {

        /**
         * 显示图片
         * 参数1：图片url
         * 参数2：显示图片的控件
         * 参数3：显示图片的设置
         * 参数4：监听器
         */

        //url = url + Constant.QINIUSCALE75;//添加七牛的图片操作参数进行图片请求操作；
        imageLoader.displayImage(url, imageView, defaultOptions);
    }

    public void clearMemoryCache() {
        imageLoader.clearMemoryCache(); // 清除内存缓存
        // imageLoader.clearDiskCache();// 清除SD卡中的缓存
    }
}