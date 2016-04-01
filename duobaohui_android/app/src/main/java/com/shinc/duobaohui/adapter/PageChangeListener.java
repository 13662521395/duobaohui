package com.shinc.duobaohui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.bean.BannerBean;
import com.shinc.duobaohui.customview.imp.InfiniteLoopViewPager;

import java.util.List;

/**
 * 文件名：
 * Created by chaos on 15/7/7.
 * 功能：
 */
public class PageChangeListener implements ViewPager.OnPageChangeListener {
    public PageChangeListener(int lastState, InfiniteLoopViewPager mViewPager, LinearLayout ll_points, int previousPosition, int posSize, RelativeLayout rlBanner, Context context, List<BannerBean> picLists) {
        this.lastState = lastState;
        this.mViewPager = mViewPager;
        this.ll_points = ll_points;
        this.previousPosition = previousPosition;
        this.posSize = posSize;
        this.rlBanner = rlBanner;
        this.context = context;
        this.picLists = picLists;
    }

    private int previousPosition;
    private InfiniteLoopViewPager mViewPager;
    private LinearLayout ll_points;
    private int lastState;
    private RelativeLayout rlBanner;
    private List<BannerBean> picLists;
    //图片的数量
    private int posSize;
    private Context context;
    private int oldPosition = 0;

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
            case ViewPager.SCROLL_STATE_SETTLING:
                lastState = state;

                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (lastState == ViewPager.SCROLL_STATE_DRAGGING) {
                    if (mViewPager.getCurrentItem() == picLists.size() - 1) {
                        mViewPager.setCurrentItem(0);
                    } else if (mViewPager.getCurrentItem() == 0) {
                        mViewPager.setCurrentItem(picLists.size() - 1);
                    }
                }
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (posSize != 0 || ll_points.getChildCount() > 0) {
            position = position % posSize;
            if (ll_points.getChildCount() <= 1) {
                position = 0;
            }
            if (ll_points.getChildCount() == 2) {
                ll_points.getChildAt(previousPosition).setEnabled(false);
                ll_points.getChildAt(position % 2).setEnabled(true);
                previousPosition = position % 2;
            } else {
                if (ll_points.getChildCount() >= previousPosition) {
                    ll_points.getChildAt(previousPosition).setEnabled(false);
                    ll_points.getChildAt(position).setEnabled(true);
                    previousPosition = position;
                }
            }
        }

    }
}
