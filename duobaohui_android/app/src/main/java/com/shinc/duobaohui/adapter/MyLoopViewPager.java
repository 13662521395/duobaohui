package com.shinc.duobaohui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.bean.BannerBean;
import com.shinc.duobaohui.customview.imp.InfiniteLoopViewPager;

import java.util.List;

/**
 * 文件名：
 * Created by chaos on 15/7/6.
 * 功能：
 */
public class MyLoopViewPager extends PagerAdapter {
    private InfiniteLoopViewPager mViewPager;
    private float width;
    private List<BannerBean> bannars;
    private Context context;
    private List<ImageView> imageViews;

    public MyLoopViewPager(InfiniteLoopViewPager mViewPager, RelativeLayout rlBanner, float width, List<BannerBean> bannars, Context context, List<ImageView> imageViews) {
        this.mViewPager = mViewPager;
        this.width = width;
        this.bannars = bannars;
        this.context = context;
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return bannars.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
//        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container,
                                  final int position) {
        ImageView mImageView = imageViews.get(position);
        container.removeView(mImageView);

        if (bannars.size() > 0) {
//            final NewsBean.Data.AppBlock.ArticleInfo.PicList bannar = bannars.get(position);
//            final IndexDataBean.IndexBean bannar = bannars.get(position);
//            mImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//
//            });
//            ImageLoad.getInstance(context).setImageToView(bannar.getUrl(), mImageView, new ImageLoadingListener() {
//                @Override
//                public void onLoadingStarted(String s, View view) {
//
//                }
//
//                @Override
//                public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//                }
//
//                @Override
//                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
////                    rlBanner.setLayoutParams(new LinearLayout.LayoutParams(
////                            LinearLayout.LayoutParams.MATCH_PARENT,
////                            (int) (width / bitmap.getWidth() * bitmap.getHeight())));
//                }
//
//                @Override
//                public void onLoadingCancelled(String s, View view) {
//
//                }
//            });
        }
        container.addView(mImageView);

        return mImageView;
    }


}
