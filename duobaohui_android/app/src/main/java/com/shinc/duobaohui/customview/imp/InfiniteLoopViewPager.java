package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.shinc.duobaohui.customview.ptr.PtrFrameLayout;


/**
 * 文件名：
 * Created by chaos on 15/7/6.
 * 功能：
 */
public class InfiniteLoopViewPager extends ViewPager {

    private BannerViewImpl indexBannerView;
    private Handler handler;
    private boolean scrollble = true;

    private PtrFrameLayout parent;
    private float mDownPosX = 0;
    private float mDownPosY = 0;

    public InfiniteLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InfiniteLoopViewPager(Context context) {
        super(context);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        setCurrentItem(0);
    }

    public void setNestParent(PtrFrameLayout parent) {
        this.parent = parent;
    }

    /**
     * 设置自动带有Handelr刷新的Adapter；
     *
     * @param handler         自动刷新操作的Handler：
     * @param adapter         PagerAdapter;
     * @param indexBannerView IndexBannerView对象；
     */
    public void setInfinateAdapter(Handler handler, PagerAdapter adapter, BannerViewImpl indexBannerView) {
        this.handler = handler;
        this.indexBannerView = indexBannerView;
        setAdapter(adapter);
    }

    @Override
    public void setCurrentItem(int item) {
//        item = getOffsetAmount() + (item % getAdapter().getCount());
        super.setCurrentItem(item);
    }

//    /**
//     * 得到数量；
//     *
//     * @return
//     */
//    private int getOffsetAmount() {
//        if (getAdapter() instanceof InfiniteLoopViewPagerAdapter) {
//            InfiniteLoopViewPagerAdapter infiniteAdapter = (InfiniteLoopViewPagerAdapter) getAdapter();
//            return infiniteAdapter.getRealCount() * 100000;
//        } else {
//            return 0;
//        }
//    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        final float x = ev.getX();
        final float y = ev.getY();
        if (scrollble) {

            int action = ev.getAction();
            if (indexBannerView != null) {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (parent != null) {
                            parent.disableWhenHorizontalMove(true);
                        }
                        mDownPosX = x;
                        mDownPosY = y;
                        handler.removeCallbacksAndMessages(null);
                        indexBannerView.isRun = false;
                        indexBannerView.isDown = true;
                        break;
                    case MotionEvent.ACTION_MOVE:

                        final float deltaX = Math.abs(x - mDownPosX);
                        final float deltaY = Math.abs(y - mDownPosY);
                        if (deltaX > deltaY) {
                            parent.disableWhenHorizontalMove(true);
                        } else {
                            parent.disableWhenHorizontalMove(false);
                        }
                        handler.removeCallbacksAndMessages(null);
                        indexBannerView.isDown = true;
                        indexBannerView.isRun = false;
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        handler.removeCallbacksAndMessages(null);
                        indexBannerView.isRun = true;
                        indexBannerView.isDown = false;
                        handler.sendEmptyMessageDelayed(BannerViewImpl.ROLL, BannerViewImpl.sleepTime);
                        parent.disableWhenHorizontalMove(false);
                        break;
                }
            }
            return super.dispatchTouchEvent(ev);
        } else {
            if (parent != null) {
                parent.disableWhenHorizontalMove(false);
            }
            return false;
        }
    }

    @Override
    public void setOffscreenPageLimit(int limit) {
        super.setOffscreenPageLimit(limit);
    }


    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }
}
