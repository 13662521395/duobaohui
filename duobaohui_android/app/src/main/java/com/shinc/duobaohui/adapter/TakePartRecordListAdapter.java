package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.TakePartBean;
import com.shinc.duobaohui.constant.Constant;
import com.shinc.duobaohui.customview.imp.CircleImageView;

import java.util.ArrayList;

/**
 * Created by liugaopo on 15/9/29.
 * 地址列表 适配器
 */
public class TakePartRecordListAdapter extends BaseAdapter {
    private Activity mActivity;
    private AddressItemViewHolder addressItemViewHolder;

    private ArrayList<TakePartBean> list = new ArrayList<>();
    private DisplayImageOptions options;

    public TakePartRecordListAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_head_small)
                .showStubImage(R.drawable.icon_head_small)
                .showImageForEmptyUri(R.drawable.icon_head_small)
                .showImageOnFail(R.drawable.icon_head_small)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)//设置为RGB565比起默认的ARGB_8888要节省大量的内存
                .delayBeforeLoading(100)//载入图片前稍做延时可以提高整体滑动的流畅度
                .build();
    }


    public ArrayList<TakePartBean> getList() {
        return list;
    }

    public void setList(ArrayList<TakePartBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.item_playerlist_productdetail, null);
            addressItemViewHolder = new AddressItemViewHolder(convertView);
            convertView.setTag(addressItemViewHolder);
        } else {
            addressItemViewHolder = (AddressItemViewHolder) convertView.getTag();
        }

        if (!TextUtils.isEmpty(list.get(position).getHead_pic())) {
            ImageLoader.getInstance().displayImage(list.get(position).getHead_pic() + Constant.QINIU30_30, addressItemViewHolder.headPic,options);
        } else {
            addressItemViewHolder.headPic.setImageResource(R.drawable.icon_head_small);
        }

        addressItemViewHolder.takePartName.setText(list.get(position).getNick_name());
        addressItemViewHolder.takepartTime.setText(Html.fromHtml("<font color=#666666 > 参与了</font>" + "<font color=red >" + list.get(position).getTimes() + "</font><font color=#666666 > 次</font>"));
        addressItemViewHolder.createTime.setText(list.get(position).getCreate_time());

        if (!TextUtils.isEmpty(list.get(position).getIp()) && !TextUtils.isEmpty(list.get(position).getIp_address())) {
            addressItemViewHolder.ipAddress.setText("(" + list.get(position).getIp_address() + " " + list.get(position).getIp() + ")");
        }
        return convertView;
    }

    class AddressItemViewHolder {
        TextView takePartName;
        TextView takepartTime;
        TextView createTime;
        CircleImageView headPic;
        TextView ipAddress;

        AddressItemViewHolder(View view) {
            takePartName = (TextView) view.findViewById(R.id.take_part_name);
            takepartTime = (TextView) view.findViewById(R.id.take_part_time);
            createTime = (TextView) view.findViewById(R.id.create_time);
            headPic = (CircleImageView) view.findViewById(R.id.head_im_productdetail);
            ipAddress = (TextView) view.findViewById(R.id.ip_address);
        }
    }
}
