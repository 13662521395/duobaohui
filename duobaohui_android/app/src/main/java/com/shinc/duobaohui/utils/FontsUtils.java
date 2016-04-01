package com.shinc.duobaohui.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * 文件名：
 * Created by chaos on 15/7/16.
 * 功能：更换字体；
 */
public class FontsUtils {

    public static Typeface typeFace;
    public static Typeface getTypeFace(Context context){
//        if(typeFace == null) {
//            typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/fzzd.TTF");
//        }
        return  typeFace;
    }
}
