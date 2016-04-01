package com.shinc.duobaohui.utils.umeng;

import android.content.Context;

import com.umeng.update.UmengUpdateAgent;

/**
 * Created by efort on 15/8/11.
 * 检测版本；弹窗升级
 */
public class UpdataUtils {
    /**
     * 检测版本；弹窗升级
     *
     * @param mContext
     */
    public static void setCheckVersion(Context mContext) {
        UmengUpdateAgent.forceUpdate(mContext);
    }
}
