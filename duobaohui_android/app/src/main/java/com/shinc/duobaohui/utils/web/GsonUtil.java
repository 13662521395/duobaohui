package com.shinc.duobaohui.utils.web;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson解析工具类
 * 将Json数据转换成JavaBean
 *
 * @author Zane
 */
public class GsonUtil {
    public static <T> T json2Bean(Context ct, String result, Class<T> clazz) {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
//            Gson gson = new Gson();
            T t = gson.fromJson(result, clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
