package com.shinc.duobaohui.customview.imp;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * 名称：BannerAdapter
 * 作者：zhaopl 时间: 15/11/21.
 * 实现的主要功能：
 */
public class BannerAdapter extends PagerAdapter {


    private ArrayList<ImageView> viewlist;

    private Handler handler;


    public BannerAdapter(ArrayList<ImageView> viewlist, Handler handler) {
        this.viewlist = viewlist;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        //设置成最大，使用户看不到边界
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        //Warning：不要在这里调用removeView
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= viewlist.size();
        if (position < 0) {
            position = viewlist.size() + position;
        }
        ImageView view = viewlist.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }


}
