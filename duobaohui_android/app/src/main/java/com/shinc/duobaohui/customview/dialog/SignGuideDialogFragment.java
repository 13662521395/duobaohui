package com.shinc.duobaohui.customview.dialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.shinc.duobaohui.R;

/**
 * 名称：SignGuideDialogFragment
 * 作者：zhaopl 时间: 16/1/13.
 * 实现的主要功能：
 */
public class SignGuideDialogFragment extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        WindowManager windowManager = getActivity().getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
//        lp.width = (int)(display.getWidth()); //设置宽度
//        getDialog().getWindow().setAttributes(lp);
        View view = inflater.inflate(R.layout.guide_sign_one_layout, container);
        return view;
    }
}
