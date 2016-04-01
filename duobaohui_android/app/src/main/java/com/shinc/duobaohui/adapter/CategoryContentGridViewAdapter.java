package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shinc.duobaohui.ProductDetailActivity;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.ProductBean;
import com.shinc.duobaohui.constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zpl on 15/9/17.
 */
public class CategoryContentGridViewAdapter extends BaseAdapter {
    private Activity mActivity;
    private ReCurseGridViewHolder reCurseGridViewHolder;
    private List<ProductBean> listChildDatas;
    private DisplayImageOptions options;

    public CategoryContentGridViewAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        listChildDatas = new ArrayList<>();
        //noinspection deprecation
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.pic_listnopic)
                .showStubImage(R.drawable.pic_listnopic)
                .showImageForEmptyUri(R.drawable.pic_listnopic)
                .showImageOnFail(R.drawable.pic_listnopic)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)//设置为RGB565比起默认的ARGB_8888要节省大量的内存
                .delayBeforeLoading(100)//载入图片前稍做延时可以提高整体滑动的流畅度
                .build();
    }

    public List getListChildDatas() {
        return listChildDatas;
    }

    public void setListChildDatas(List<ProductBean> listChildDatas) {
        this.listChildDatas = listChildDatas;
    }


    @Override
    public int getCount() {
        return listChildDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return listChildDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.product_grid_item, parent, false);
            reCurseGridViewHolder = new ReCurseGridViewHolder(convertView);
            convertView.setTag(reCurseGridViewHolder);
        } else {
            reCurseGridViewHolder = (ReCurseGridViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(listChildDatas.get(position).getGoods_img())) {
            ImageLoader.getInstance().displayImage(listChildDatas.get(position).getGoods_img() + Constant.QINIU320_220, reCurseGridViewHolder.productImg, options);
        } else {
            reCurseGridViewHolder.productImg.setImageResource(R.drawable.pic_listnopic);
        }

        reCurseGridViewHolder.productName.setText(listChildDatas.get(position).getGoods_name());
        reCurseGridViewHolder.productSchedule.setText("开奖进度" + listChildDatas.get(position).getRate() + "%");
        reCurseGridViewHolder.progressBar.setProgress(Integer.parseInt(listChildDatas.get(position).getRate()));
        reCurseGridViewHolder.gotoCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击进入到支付页面；
                Intent intent = new Intent(mActivity, ProductDetailActivity.class);
                intent.putExtra("PRODUCTID", listChildDatas.get(position).getPeriod_id());
                intent.putExtra("TYPE", "SHOWCOMMITORDER");//传递此参数跳转到详情页面，加载完数据后，立即显示购买数量dialog;
                mActivity.startActivity(intent);
            }
        });

        return convertView;
    }

    class ReCurseGridViewHolder {
        ImageView productImg;
        TextView productName;
        TextView productSchedule;
        ProgressBar progressBar;
        TextView gotoCurrent;

        ReCurseGridViewHolder(View view) {

            productImg = (ImageView) view.findViewById(R.id.product_img);
            productName = (TextView) view.findViewById(R.id.product_name);
            productSchedule = (TextView) view.findViewById(R.id.product_schedule);
            progressBar = (ProgressBar) view.findViewById(R.id.active_progress);
            gotoCurrent = (TextView) view.findViewById(R.id.goto_current);
        }
    }
}
