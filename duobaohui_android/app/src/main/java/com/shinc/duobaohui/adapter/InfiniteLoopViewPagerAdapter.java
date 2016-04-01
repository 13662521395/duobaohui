package com.shinc.duobaohui.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用户处理轮播图自动刷新操作的Adapter;
 */
public class InfiniteLoopViewPagerAdapter extends PagerAdapter {
    private PagerAdapter adapter;

    /**
     * 构造器；
     *
     * @param adapter
     */
    public InfiniteLoopViewPagerAdapter(PagerAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    /**
     * 得到数据的数量；
     *
     * @return
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 拿到真实的数量；
     *
     * @return
     */
    public int getRealCount() {
        return adapter.getCount();
    }

    public int getRealItemPosition(Object object) {
        return adapter.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition;
        if (getRealCount() != 0) {
            realPosition = position % getRealCount();
        } else {
            realPosition = 0;
        }
        adapter.destroyItem(container, realPosition, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition;
        if (getRealCount() != 0) {
            realPosition = position % getRealCount();
        } else {
            realPosition = 0;
        }
        return adapter.instantiateItem(container, realPosition);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        adapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        adapter.restoreState(state, loader);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }


}
