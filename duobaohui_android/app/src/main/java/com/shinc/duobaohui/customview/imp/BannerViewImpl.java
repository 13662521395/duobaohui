package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.adapter.PageChangeListener;
import com.shinc.duobaohui.bean.BannerBean;
import com.shinc.duobaohui.bean.IndexBannerBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.customview.IndexBannerViewInterface;
import com.shinc.duobaohui.utils.ImageLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：
 * Created by chaos on 15/7/6.
 * 功能：首页新闻列表Banner图。
 */
public class BannerViewImpl extends RelativeLayout implements IndexBannerViewInterface {
    private InfiniteLoopViewPager infiniteLoopViewPager;
    private RelativeLayout view;
    //用于加载view
    private LayoutInflater inflater;
    private LinearLayout ll_points;//轮播图线面的指示点的承载布局；

    private Context context;
    public static final int RESETQUIT = 0;
    public boolean mPreparedQuit = false;
    public static final int ROLL = 99;

    public boolean isViewPageReady = false;

    public boolean isRun = false;

    public boolean isDown = false;

    public static final int sleepTime = 3000;

    private Handler myHandler;


    public BannerViewImpl(Context context) {
        super(context);
        initView(context);
    }


    public BannerViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BannerViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化View;
     *
     * @param context
     */
    private void initView(Context context) {
        this.context = context;
        myHandler = new BannerViewHandler();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = (RelativeLayout) inflater.inflate(R.layout.view_index_banner, this);
        ll_points = (LinearLayout) view.findViewById(R.id.ll_points);

        infiniteLoopViewPager = (InfiniteLoopViewPager) view.findViewById(R.id.vp_banner);
    }

    /**
     * 设置Banner的内容
     */
    public void setData(int type, IndexBannerBean indexBannerBean, final SetOnPageClick pageClick) {
        if (indexBannerBean != null && indexBannerBean.getData() != null) {
            List<BannerBean> picLists = new ArrayList<>();
            picLists = indexBannerBean.getData();//拿到图片的数据；
            //对点进行初始化；
            ll_points.removeAllViews();
            View llView;//对线面的指示点进行初始化；
            LinearLayout.LayoutParams lp;

            for (int i = 0; i < picLists.size(); i++) {
                llView = new View(context);
                llView.setBackgroundResource(R.drawable.point_background);
                lp = new LinearLayout.LayoutParams(context.getResources().getDimensionPixelOffset(R.dimen.s_4dp), context.getResources().getDimensionPixelOffset(R.dimen.s_4dp));
                lp.leftMargin = context.getResources().getDimensionPixelOffset(R.dimen.s_4dp);
                llView.setLayoutParams(lp);
                llView.setEnabled(false);
                ll_points.addView(llView);
            }
            if (ll_points.getChildCount() > 0) {
                ll_points.getChildAt(0).setEnabled(true);
            }


            List<ImageView> imageViews = getImageViews(type, picLists, pageClick);

            BannerAdapter bannerAdapter = new BannerAdapter((ArrayList<ImageView>) imageViews, myHandler);
//            infiniteLoopViewPager.setAdapter(bannerAdapter);
            infiniteLoopViewPager.setInfinateAdapter(myHandler, bannerAdapter, BannerViewImpl.this);

            infiniteLoopViewPager.addOnPageChangeListener(new PageChangeListener(0, infiniteLoopViewPager, ll_points, 0, picLists.size(), view, context, picLists));
            myHandler.removeCallbacksAndMessages(null);
            if (picLists.size() > 1) {
                isViewPageReady = true;
                isRun = true;
                infiniteLoopViewPager.setScrollble(true);
                myHandler.sendEmptyMessageDelayed(ROLL, 3000);
            } else {
                infiniteLoopViewPager.setScrollble(false);
            }
        }
    }

    @Override
    public void onDestroyHandler() {
        myHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public InfiniteLoopViewPager getViewPager() {
        return infiniteLoopViewPager;
    }


    /**
     * 对图片数据进行初始化的操作。创建ImageView;
     *
     * @param type
     * @param imgBeans
     * @param pageClick
     * @return
     */
    public List<ImageView> getImageViews(int type, List<BannerBean> imgBeans, final SetOnPageClick pageClick) {
        List<ImageView> headImgs = new ArrayList<>();

        for (int i = 0; i < imgBeans.size(); i++) {

            String imgUrl = imgBeans.get(i).getPic_url();
            final String currentId = imgBeans.get(i).getId();
            final String currentType = imgBeans.get(i).getType();
            final String linkUrl = imgBeans.get(i).getLink_url();

            final ImageView im = new ImageView(context);
            im.setImageResource(R.drawable.pic_kvnopic);//设置默认图片；
            ImageLoad.getInstance(context).setImageToView(imgBeans.get(i).getPic_url() + Constant.QINIUSCALE75, im);
            if (type == 1) {
                im.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } else {
                im.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            im.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pageClick.onClick(currentId, currentType, linkUrl);

                }
            });
            headImgs.add(im);
        }

        return headImgs;
    }

    class BannerViewHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESETQUIT:
                    mPreparedQuit = false;
                    break;
                case ROLL:
                    if (isViewPageReady) {
                        infiniteLoopViewPager.setCurrentItem(infiniteLoopViewPager.getCurrentItem() + 1, true);
                    }

                    if (isRun && !isDown) {
                        this.sendEmptyMessageDelayed(ROLL, sleepTime);
                    }
                    break;
            }
        }
    }

}
