package com.shinc.duobaohui.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.Map;
import java.util.Set;

/**
 * @author zhaopenglei SharedPreferences工具类；
 */
public class SharedPreferencesUtils {

    private SharedPreferences sp;
    private Editor editor;
    private String name = "";// 创建一个默认的常用的name;
    private int mode = Context.MODE_PRIVATE;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesUtils(Context ctx) {
        this.sp = ctx.getSharedPreferences(name, mode);
        this.editor = sp.edit();
    }

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesUtils(Context ctx, String name, int mode) {
        this.sp = ctx.getSharedPreferences(name, mode);
        this.editor = sp.edit();
    }

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesUtils(Context ctx, String name) {
        this.sp = ctx.getSharedPreferences(name, mode);
        this.editor = sp.edit();
    }

    /**
     * 向SharedPreferences中添加一组数据；
     *
     * @param map
     */
    public void add(Map<String, String> map) {

        Set<String> set = map.keySet();
        for (String key : set) {
            editor.putString(key, map.get(key));
        }
        editor.commit();
    }

    /**
     * 向SharedPreferences中添加一组数据；
     *
     * @param key
     * @param value
     */
    public void add(String key, String value) {

        editor.putString(key, value).commit();
    }

    /**
     * 向SharedPreferences中添加一组数据；
     *
     * @param key
     * @param value
     */
    public void add(String key, int value) {

        editor.putInt(key, value).commit();
    }

    /**
     * 清除指定SharedPreferences下所有的数据；
     */
    public void deleteAll() {
        editor.clear().commit();
    }

    /**
     * 清楚指定SharedPreferences下的指定某一个数据；
     *
     * @param key
     */
    public void delete(String key) {
        editor.remove(key).commit();
    }

    /**
     * 获取指定SharedPreferences下的指定的数据；
     *
     * @param key
     * @param defValue
     * @return
     */
    public String get(String key, String defValue) {
        String string = sp.getString(key, defValue);
        return string;
    }

    /**
     * 获取指定SharedPreferences下的指定的数据；
     *
     * @param key
     * @param defValue
     * @return
     */
    public int get(String key, int defValue) {
        int i = sp.getInt(key, defValue);
        Log.e("Result", key + "===" + defValue);
        return i;
    }

    /**
     * 获取SharedPreferences下的编辑器editor；
     *
     * @return
     */
    public Editor getEditor() {
        return sp.edit();
    }

}
