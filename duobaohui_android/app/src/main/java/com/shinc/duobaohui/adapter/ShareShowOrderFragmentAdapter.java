package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.ShowOrderDetailsActivity;
import com.shinc.duobaohui.UserMainPageActivity;
import com.shinc.duobaohui.bean.ShaiDanBean;
import com.shinc.duobaohui.bean.TakePartBean;
import com.shinc.duobaohui.customview.imp.CircleImageView;
import com.shinc.duobaohui.utils.ImageLoad;

import java.util.List;

/**
 * Created by efort on 15/10/12.
 */
public class ShareShowOrderFragmentAdapter extends BaseAdapter {


    private final Activity mActivity;
    private List<ShaiDanBean.ShaiDanItem> shaiDanItemList;
    ShowOrderItemViewHolder showOrderItemViewHolder;

    public ShareShowOrderFragmentAdapter(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        return shaiDanItemList.size();
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

        if (shaiDanItemList.get(position).getUserInfo().getId() != null) {
            if (!TextUtils.isEmpty(shaiDanItemList.get(position).getUserInfo().getHead_pic().trim())) {
                setImage(showOrderItemViewHolder.headImg, shaiDanItemList.get(position).getUserInfo().getHead_pic());
            }
            showOrderItemViewHolder.userName.setText(shaiDanItemList.get(position).getUserInfo().getNick_name() + "");
        }

        if (!TextUtils.isEmpty(shaiDanItemList.get(position).getOrderInfo().getIp())) {
            showOrderItemViewHolder.ip_address.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(shaiDanItemList.get(position).getOrderInfo().getIp_address())) {

                showOrderItemViewHolder.ip_address.setText("(" + shaiDanItemList.get(position).getOrderInfo().getIp() + ")");
            } else {
                showOrderItemViewHolder.ip_address.setText("(" + shaiDanItemList.get(position).getOrderInfo().getIp_address() + " " + shaiDanItemList.get(position).getOrderInfo().getIp() + ")");
            }
        } else {
            showOrderItemViewHolder.ip_address.setVisibility(View.GONE);
        }

        showOrderItemViewHolder.showTime.setText(shaiDanItemList.get(position).getOrderInfo().getA_code_create_time() + "");
        showOrderItemViewHolder.showTitle.setText(shaiDanItemList.get(position).getTitle() + "");
        showOrderItemViewHolder.product.setText("(第" + shaiDanItemList.get(position).getOrderInfo().getPeriod_number() + "期) " + shaiDanItemList.get(position).getOrderInfo().getGoods_name() + "");
        showOrderItemViewHolder.showValue.setText(shaiDanItemList.get(position).getContent() + "");
        showOrderItemViewHolder.bottom.setVisibility(View.VISIBLE);
        if (position == shaiDanItemList.size()) {
            showOrderItemViewHolder.bottom.setVisibility(View.GONE);
        }
        showOrderItemViewHolder.headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 点击头像，跳转到TA的主页；
                Intent intent = new Intent();
                intent.setClass(mActivity, UserMainPageActivity.class);
                ShaiDanBean.UserInfo userInfo = shaiDanItemList.get(position).getUserInfo();
                ShaiDanBean.OrderInfo orderInfo = shaiDanItemList.get(position).getOrderInfo();
                intent.putExtra("takePart", new TakePartBean(userInfo.getId(), userInfo.getNick_name(), orderInfo.getTimes(), orderInfo.getA_code_create_time(), userInfo.getHead_pic(), "", orderInfo.getIp(), orderInfo.getIp_address()));
                mActivity.startActivity(intent);
            }
        });
        showOrderItemViewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ShowOrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ORDER_INFO", shaiDanItemList.get(position));
                intent.putExtras(bundle);
                mActivity.startActivity(intent);
            }
        });
        showOrderItemViewHolder.imgs.setVisibility(View.GONE);
        if (shaiDanItemList.get(position).getImg() != null && shaiDanItemList.get(position).getImg().size() > 0) {
            showOrderItemViewHolder.imgs.setVisibility(View.VISIBLE);
            showOrderItemViewHolder.img1.setVisibility(View.VISIBLE);
            showOrderItemViewHolder.img1.setImageResource(R.drawable.icon_nopic);
            showOrderItemViewHolder.img2.setVisibility(View.VISIBLE);
            showOrderItemViewHolder.img2.setImageResource(R.drawable.icon_nopic);
            showOrderItemViewHolder.img3.setVisibility(View.VISIBLE);
            showOrderItemViewHolder.img3.setImageResource(R.drawable.icon_nopic);
            switch (shaiDanItemList.get(position).getImg().size()) {
                case 1:
                    setImage(showOrderItemViewHolder.img1, shaiDanItemList.get(position).getImg().get(0));
                    showOrderItemViewHolder.img2.setVisibility(View.GONE);
                    showOrderItemViewHolder.img3.setVisibility(View.GONE);
                    break;
                case 2:
                    setImage(showOrderItemViewHolder.img1, shaiDanItemList.get(position).getImg().get(0));
                    setImage(showOrderItemViewHolder.img2, shaiDanItemList.get(position).getImg().get(1));
                    showOrderItemViewHolder.img3.setVisibility(View.GONE);
                    break;
                default:
                    setImage(showOrderItemViewHolder.img1, shaiDanItemList.get(position).getImg().get(0));
                    setImage(showOrderItemViewHolder.img2, shaiDanItemList.get(position).getImg().get(1));
                    setImage(showOrderItemViewHolder.img3, shaiDanItemList.get(position).getImg().get(2));
                    break;
            }
        }
        return convertView;
    }

    public void setList(List<ShaiDanBean.ShaiDanItem> shaiDanItemList) {
        this.shaiDanItemList = shaiDanItemList;
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
        TextView ip_address;

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
            ip_address = (TextView) convertView.findViewById(R.id.share_user_ip_address);

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
