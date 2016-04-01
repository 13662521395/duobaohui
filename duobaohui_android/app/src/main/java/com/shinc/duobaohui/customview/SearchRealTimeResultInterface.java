package com.shinc.duobaohui.customview;

import android.widget.BaseAdapter;

/**
 * 名称：SearchRealTimeResultInterface
 * 作者：zhaopl 时间: 15/9/6.
 * 实现的主要功能：
 *      实时搜索结果的展示View所应继承的接口；
 */
public interface SearchRealTimeResultInterface {

    /**
     * 设置其的显示隐藏；
     * @param flag
     */
    void setShowOrHide(boolean flag);


    void clickItem(ClickItemListener clickItemListener);

    interface ClickItemListener{
        void onClickItem(int position);
    }

    void setRealTimeSearchData(BaseAdapter baseAdapter);


    interface ScrollToBottomListener{
        void scrollToBottom();
    }


    void setScrollListener(ScrollToBottomListener scrollToBottomListener);

    void setTitle(String str);


}
