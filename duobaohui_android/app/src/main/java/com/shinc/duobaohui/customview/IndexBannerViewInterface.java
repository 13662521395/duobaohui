package com.shinc.duobaohui.customview;


import com.shinc.duobaohui.bean.IndexBannerBean;
import com.shinc.duobaohui.customview.imp.InfiniteLoopViewPager;

public interface IndexBannerViewInterface {

    void setData(int type, IndexBannerBean indexBannerBean, SetOnPageClick pageClick);

    void onDestroyHandler();

    InfiniteLoopViewPager getViewPager();

    /**
     * 用于监听页面点击事件的Listener;
     */
    interface SetOnPageClick {
        void onClick(String id, String type, String linkUrl);
    }
}

