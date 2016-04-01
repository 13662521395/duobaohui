package com.shinc.duobaohui.customview.banner;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.banner.holder.Holder;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<String> {
    private ImageView imageView;
    private String type;

    public NetworkImageHolderView(String type) {
        this.type = type;
    }

    public NetworkImageHolderView() {
    }

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        if (!TextUtils.isEmpty(type) && "1".equals(type)) {
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        imageView.setImageResource(R.drawable.pic_kvnopic);
        ImageLoader.getInstance().displayImage(data, imageView);
    }
}
