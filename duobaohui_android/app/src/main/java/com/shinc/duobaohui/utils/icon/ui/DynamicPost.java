package com.shinc.duobaohui.utils.icon.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.base.MyApplication;
import com.shinc.duobaohui.utils.icon.CapturePicturePathsBean;
import com.shinc.duobaohui.utils.icon.CapturePicturePathsEvent;
import com.shinc.duobaohui.utils.icon.DynamicPostInterface;
import com.shinc.duobaohui.utils.icon.LocalImageHelper;
import com.shinc.duobaohui.utils.icon.common.ImageUtils;
import com.shinc.duobaohui.utils.icon.widget.AlbumViewPager;
import com.shinc.duobaohui.utils.icon.widget.FilterImageView;
import com.shinc.duobaohui.utils.icon.widget.MatrixImageView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * @author linjizong
 * @Description:发布动态界面
 * @date 2015-5-14
 */

public class DynamicPost extends LinearLayout implements OnClickListener, MatrixImageView.OnSingleTapListener, DynamicPostInterface {

    private Activity mActivity;

    // private InputMethodManager imm;//软键盘管理
    private TextView textRemain;//字数提示
    private TextView picRemain;//图片数量提示
    private ImageView add;//添加图片按钮
    private LinearLayout picContainer;//图片容器
    private List<LocalImageHelper.LocalFile> pictures = new ArrayList<>();//图片路径数组
    HorizontalScrollView scrollView;//滚动的图片容器
    View editContainer;//动态编辑部分
    private View mainView;
    //显示大图的viewpager 集成到了Actvity中 下面是和viewpager相关的控件
    AlbumViewPager viewpager;//大图显示pager
    ImageView mBackView;//返回/关闭大图
    TextView mCountView;//大图数量提示
    View mHeaderBar;//大图顶部栏
    ImageView delete;//删除按钮
    FrameLayout pagerContainer;

    int size;//小图大小
    int padding;//小图间距
    DisplayImageOptions options;
    onViewVisible onViewVisible;
    private DeleteListener deleteListener;

    public DynamicPost(Context context) {
        super(context);
        mActivity = (Activity) context;
        onCreate();
    }

    public DynamicPost(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (Activity) context;
        onCreate();
    }

    public DynamicPost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivity = (Activity) context;

        onCreate();
    }

    protected void onCreate() {
        // TODO Auto-generated method stub
        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.post_dynamic, this);
        //imm = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
        //设置ImageLoader参数
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .showImageForEmptyUri(R.drawable.icon_nopic)
                .showImageOnFail(R.drawable.icon_nopic)
                .showImageOnLoading(R.drawable.icon_nopic)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        initViews();
        initData();
    }

    /**
     * @Description： 初始化Views
     */
    private void initViews() {
        // TODO Auto-generated method stub

        picRemain = (TextView) findViewById(R.id.post_pic_remain);
        add = (ImageView) findViewById(R.id.post_add_pic);
        picContainer = (LinearLayout) findViewById(R.id.post_pic_container);
        scrollView = (HorizontalScrollView) findViewById(R.id.post_scrollview);
        viewpager = (AlbumViewPager) findViewById(R.id.albumviewpager);
        mBackView = (ImageView) findViewById(R.id.header_bar_photo_back);
        mCountView = (TextView) findViewById(R.id.header_bar_photo_count);
        mHeaderBar = findViewById(R.id.album_item_header_bar);
        delete = (ImageView) findViewById(R.id.header_bar_photo_delete);
        editContainer = findViewById(R.id.post_edit_container);
        delete.setVisibility(View.VISIBLE);
        pagerContainer = (FrameLayout) findViewById(R.id.pagerview);


        viewpager.addOnPageChangeListener(pageChangeListener);
        viewpager.setOnSingleTapListener(this);
        mBackView.setOnClickListener(this);
        mCountView.setOnClickListener(this);

        add.setOnClickListener(this);
        delete.setOnClickListener(this);


    }


    private void initData() {
        size = (int) getResources().getDimension(R.dimen.s_55dp);
        padding = (int) getResources().getDimension(R.dimen.padding_10);
    }

    public void onBackPressed() {
        if (pagerContainer.getVisibility() != View.VISIBLE) {
            mActivity.finish();
        } else {
            hideViewPager();
            LocalImageHelper.getInstance().setCurrentSize(0);
        }
    }


    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        int i1 = view.getId();
        if (i1 == R.id.header_bar_photo_back || i1 == R.id.header_bar_photo_count) {
            hideViewPager();
        } else if (i1 == R.id.header_bar_photo_delete) {
            final int index = viewpager.getCurrentItem();

            pictures.remove(index);
            if (deleteListener != null)
                deleteListener.onDelete(index);
            if (pictures.size() == MyApplication.ImageViewNum) {
                add.setVisibility(View.GONE);
            } else {
                add.setVisibility(View.VISIBLE);
            }
            if (pictures.size() == 0) {
                hideViewPager();
            }
            picContainer.removeView(picContainer.getChildAt(index));
            picRemain.setText(pictures.size() + "/" + MyApplication.ImageViewNum);
            mCountView.setText((viewpager.getCurrentItem() + 1) + "/" + pictures.size());
            viewpager.getAdapter().notifyDataSetChanged();
            LocalImageHelper.getInstance().setCurrentSize(pictures.size());


        } else if (i1 == R.id.post_add_pic) {
            Intent intent = new Intent(mActivity, LocalAlbum.class);
            mActivity.startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);

        } else {
            if (view instanceof FilterImageView) {
                for (int i = 0; i < picContainer.getChildCount(); i++) {
                    if (view == picContainer.getChildAt(i)) {
                        showViewPager(i);
                    }
                }
            }

        }
    }

    public interface DeleteListener {
        void onDelete(int index);
    }

    public void setDeleteListenner(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    /**
     * 获取 当前所有图片
     *
     * @return
     */
    public List<String> getUrlPath() {
        List<String> urlPath = new ArrayList<>();
        for (int i = 0; i < pictures.size(); i++) {
//            ImageUtils.getAbsolutePathFromNoStandardUri();
            urlPath.add(pictures.get(i).getPath());
        }
        return urlPath;
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (viewpager.getAdapter() != null) {
                String text = (position + 1) + "/" + viewpager.getAdapter().getCount();
                mCountView.setText(text);
            } else {
                mCountView.setText("0/0");
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    };

    //显示大图pager
    private void showViewPager(int index) {
        pagerContainer.setVisibility(View.VISIBLE);
        editContainer.setVisibility(View.GONE);
        onViewVisible.onViewVisible(0);
        viewpager.setAdapter(viewpager.new LocalViewPagerAdapter(pictures));
        viewpager.setCurrentItem(index);
        mCountView.setText((index + 1) + "/" + pictures.size());
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation((float) 0.9, 1, (float) 0.9, 1, pagerContainer.getWidth() / 2, pagerContainer.getHeight() / 2);
        scaleAnimation.setDuration(200);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0.1, 1);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        pagerContainer.startAnimation(set);
    }

    //关闭大图显示
    private void hideViewPager() {
        pagerContainer.setVisibility(View.GONE);
        editContainer.setVisibility(View.VISIBLE);
        onViewVisible.onViewVisible(1);
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, (float) 0.9, 1, (float) 0.9, pagerContainer.getWidth() / 2, pagerContainer.getHeight() / 2);
        scaleAnimation.setDuration(200);
        set.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(200);
        set.addAnimation(alphaAnimation);
        pagerContainer.startAnimation(set);
    }

    @Override
    public void onSingleTap() {
        hideViewPager();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                if (LocalImageHelper.getInstance().isResultOk()) {
                    LocalImageHelper.getInstance().setResultOk(false);
                    //获取选中的图片
                    List<LocalImageHelper.LocalFile> files = LocalImageHelper.getInstance().getCheckedItems();

                    //选中图片的传值数组实体类
                    CapturePicturePathsBean capturePicturePathsBean = new CapturePicturePathsBean();
                    for (int i = 0; i < files.size(); i++) {
                        LayoutParams params = new LayoutParams(size, size);
                        params.rightMargin = padding;
                        params.topMargin = padding;
                        FilterImageView imageView = new FilterImageView(mActivity);
                        imageView.setLayoutParams(params);
                        imageView.setScaleType(ScaleType.CENTER_CROP);
                        ImageLoader.getInstance().displayImage(files.get(i).getThumbnailUri(), new ImageViewAware(imageView), options, null, null, files.get(i).getOrientation());
                        imageView.setOnClickListener(this);
                        pictures.add(files.get(i));
                        if (pictures.size() == 9) {
                            add.setVisibility(View.GONE);
                        } else {
                            add.setVisibility(View.VISIBLE);
                            capturePicturePathsBean.addPathToList(files.get(i).getThumbnailUri());
                        }
                        picContainer.addView(imageView, picContainer.getChildCount() - 1);
                        picRemain.setText(pictures.size() + "/9");
                        LocalImageHelper.getInstance().setCurrentSize(pictures.size());
                    }

                    EventBus.getDefault().post(new CapturePicturePathsEvent(capturePicturePathsBean));

                    //清空选中的图片
                    files.clear();
                    //设置当前选中的图片数量
                    LocalImageHelper.getInstance().setCurrentSize(pictures.size());
                    //延迟滑动至最右边
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                        }
                    }, 50L);
                }
                //清空选中的图片
                LocalImageHelper.getInstance().getCheckedItems().clear();
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:

                break;
            default:
                break;
        }
    }

    @Override
    public void setVisible(onViewVisible onViewVisible) {
        this.onViewVisible = onViewVisible;
    }

    @Override
    public void setGonepostremain(boolean flag) {
        if (flag) {
            picRemain.setVisibility(View.GONE);
        } else {
            picRemain.setVisibility(View.VISIBLE);
        }

    }
}
