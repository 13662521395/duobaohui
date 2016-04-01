package com.shinc.duobaohui.customview.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.shinc.duobaohui.customview.ptr.indicator.PtrTensionIndicator;

public class PtrClassicFrameLayout extends PtrFrameLayout {

    private PtrClassicDefaultHeader mPtrClassicHeader;
    private PtrTensionIndicator ptrIndicator;
    private float mDownPosY = 0;

    private float mPrevX;
    private float mPrevY;
    private int slop;

    public PtrClassicFrameLayout(Context context) {
        super(context);
        initViews(context);
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    private void initViews(Context context) {
        slop = ViewConfiguration.get(context).getScaledTouchSlop();
        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        ptrIndicator = new PtrTensionIndicator();
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
        setPtrIndicator(ptrIndicator);
    }

    public PtrClassicDefaultHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(ev).getX();
                mPrevY = MotionEvent.obtain(ev).getY();
                break;
            case MotionEvent.ACTION_MOVE:

                final float eventX = ev.getX();
                final float eventY = ev.getY();
                float xDiff = Math.abs(eventX - mPrevX);
                float yDiff = Math.abs(eventY - mPrevY);
                if (xDiff > yDiff) {
                    disableWhenHorizontalMove(true);
                } else {
                    disableWhenHorizontalMove(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                disableWhenHorizontalMove(false);
                break;
        }
        return super.dispatchTouchEvent(ev);

    }


}
