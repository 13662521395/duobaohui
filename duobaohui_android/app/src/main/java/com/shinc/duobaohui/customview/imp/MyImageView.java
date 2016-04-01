package com.shinc.duobaohui.customview.imp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.utils.ImageLoad;

/**
 * Created by efort on 15/10/12.
 */
public class MyImageView extends LinearLayout {
    private LayoutInflater inflater;
    private View view;
    ImageView myImageVIew;

    public MyImageView(Context context) {
        super(context);
        initView(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.my_image_view, this);

        myImageVIew = (ImageView) view.findViewById(R.id.my_image_view);
        myImageVIew.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void setUrl(String url, Context context) {
        ImageLoad.getInstance(context).setImageToView(url + "-dbhsy", myImageVIew);

    }

}
