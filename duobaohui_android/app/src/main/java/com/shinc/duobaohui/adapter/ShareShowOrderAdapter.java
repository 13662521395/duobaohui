package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.ShowOrderDetailsActivity;
import com.shinc.duobaohui.bean.ShaiDanBean;
import com.shinc.duobaohui.customview.imp.CircleImageView;
import com.shinc.duobaohui.utils.ImageLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by efort on 15/10/12.
 */
public class ShareShowOrderAdapter extends BaseAdapter {


    private final Activity mActivity;
    private List<ShaiDanBean.ShaiDanItem> shaiDanItemList;
    ShowOrderItemViewHolder showOrderItemViewHolder;

    public ShareShowOrderAdapter(Activity activity) {
        this.mActivity = activity;
        this.shaiDanItemList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return getList().size();
    }

    public List<ShaiDanBean.ShaiDanItem> getList() {
        return shaiDanItemList;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.share_show_order_item, null);
            showOrderItemViewHolder = new ShowOrderItemViewHolder(convertView);
            convertView.setTag(showOrderItemViewHolder);
        } else {
            showOrderItemViewHolder = (ShowOrderItemViewHolder) convertView.getTag();
        }
        showOrderItemViewHolder.headImg.setImageResource(R.drawable.icon_head_small);
        if (shaiDanItemList.get(position).getUserInfo().getHead_pic() != null) {
            setImage(showOrderItemViewHolder.headImg, getList().get(position).getUserInfo().getHead_pic());
        }
        showOrderItemViewHolder.userName.setText(getList().get(position).getUserInfo().getNick_name() + "");
        showOrderItemViewHolder.showTime.setText(getList().get(position).getOrderInfo().getA_code_create_time() + "");
        showOrderItemViewHolder.showTitle.setText(getList().get(position).getTitle() + "");
        showOrderItemViewHolder.product.setText("(第" + getList().get(position).getOrderInfo().getPeriod_number() + "期) " + getList().get(position).getOrderInfo().getGoods_name() + "");
        showOrderItemViewHolder.showValue.setText(getList().get(position).getContent() + "");
        showOrderItemViewHolder.bottom.setVisibility(View.VISIBLE);
        if (position == getList().size()) {
            showOrderItemViewHolder.bottom.setVisibility(View.GONE);
        }
        showOrderItemViewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ShowOrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ORDER_INFO", getList().get(position));
                intent.putExtras(bundle);
                mActivity.startActivity(intent);
            }
        });
        showOrderItemViewHolder.imgs.setVisibility(View.GONE);
        if (getList().get(position).getImg() != null && getList().get(position).getImg().size() > 0) {
            showOrderItemViewHolder.imgs.setVisibility(View.VISIBLE);
            showOrderItemViewHolder.img1.setVisibility(View.VISIBLE);
            showOrderItemViewHolder.img1.setImageResource(R.drawable.icon_nopic);
            showOrderItemViewHolder.img2.setVisibility(View.VISIBLE);
            showOrderItemViewHolder.img2.setImageResource(R.drawable.icon_nopic);
            showOrderItemViewHolder.img3.setVisibility(View.VISIBLE);
            showOrderItemViewHolder.img3.setImageResource(R.drawable.icon_nopic);
            switch (shaiDanItemList.get(position).getImg().size()) {
                case 1:
                    setImage(showOrderItemViewHolder.img1, getList().get(position).getImg().get(0));
                    showOrderItemViewHolder.img2.setVisibility(View.GONE);
                    showOrderItemViewHolder.img3.setVisibility(View.GONE);
                    break;
                case 2:
                    setImage(showOrderItemViewHolder.img1, getList().get(position).getImg().get(0));
                    setImage(showOrderItemViewHolder.img2, getList().get(position).getImg().get(1));
                    showOrderItemViewHolder.img3.setVisibility(View.GONE);
                    break;
                default:
                    setImage(showOrderItemViewHolder.img1, getList().get(position).getImg().get(0));
                    setImage(showOrderItemViewHolder.img2, getList().get(position).getImg().get(1));
                    setImage(showOrderItemViewHolder.img3, getList().get(position).getImg().get(2));
                    break;
            }
        }
        return convertView;
    }

    public void setList(List<ShaiDanBean.ShaiDanItem> shaiDanItemList) {
        this.shaiDanItemList.clear();
        this.shaiDanItemList.addAll(shaiDanItemList);
    }

    private class ShowOrderItemViewHolder {
        RelativeLayout item;
        CircleImageView headImg;
        TextView userName;
        TextView showTime;
        TextView showTitle;
        TextView product;
        TextView showValue;
        View bottom;

        LinearLayout imgs;
        ImageView img1;
        ImageView img2;
        ImageView img3;

        public ShowOrderItemViewHolder(View convertView) {

            item = (RelativeLayout) convertView.findViewById(R.id.share_show_item);
            headImg = (CircleImageView) convertView.findViewById(R.id.share_show_item_headimg);
            userName = (TextView) convertView.findViewById(R.id.share_show_item_username);
            showTime = (TextView) convertView.findViewById(R.id.share_show_item_time);
            showTitle = (TextView) convertView.findViewById(R.id.share_show_item_title);
            product = (TextView) convertView.findViewById(R.id.share_show_item_product);
            showValue = (TextView) convertView.findViewById(R.id.share_show_item_value);

            bottom = convertView.findViewById(R.id.share_show_bottom);

            imgs = (LinearLayout) convertView.findViewById(R.id.share_show_item_imgs);
            img1 = (ImageView) convertView.findViewById(R.id.share_show_item_img1);
            img1.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img2 = (ImageView) convertView.findViewById(R.id.share_show_item_img2);
            img2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img3 = (ImageView) convertView.findViewById(R.id.share_show_item_img3);
            img3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    private void setImage(final ImageView imgs, final String url) {
        new Runnable() {
            @Override
            public void run() {
                ImageLoad.getInstance(mActivity).setImageToView(url + "?imageMogr2/thumbnail/!80x80r", imgs);
            }
        }.run();
    }
}
