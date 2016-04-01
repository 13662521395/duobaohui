package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.customview.imp.CustomTextView;

/**
 * Created by liugaopo on 15/10/9.
 */
public class WinnerNotDryingAdapter extends BaseAdapter {
    private Activity mActivity;

    private WinnerStayConsigneeAdapterViewHolder winnerStayConsigneeAdapterViewHolder;

    public WinnerNotDryingAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.winner_not_drying_item_layout, null);
            winnerStayConsigneeAdapterViewHolder = new WinnerStayConsigneeAdapterViewHolder(convertView);
            convertView.setTag(winnerStayConsigneeAdapterViewHolder);
        } else {
            winnerStayConsigneeAdapterViewHolder = (WinnerStayConsigneeAdapterViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class WinnerStayConsigneeAdapterViewHolder {
        CustomTextView tvState;//状态（处理中。。。）
        ImageView imageView;//图片
        CustomTextView name;//名字
        CustomTextView totalNum;//共需
        CustomTextView frequency;//本期参与人数
        CustomTextView lunckyCode;//幸运号码
        CustomTextView outTime;//揭晓时间
        CustomTextView number;//订单号
        CustomTextView tvBtn;//确认收货

        CustomTextView addressName;//姓名
        CustomTextView addressPhone;//地址
        CustomTextView address;//地址
        RelativeLayout layoutAddress;


        public WinnerStayConsigneeAdapterViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.winner_not_drying_item_layout_imgUrl);
            tvState = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_state);
            name = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_name);
            totalNum = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_totalNum);
            frequency = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_frequency);
            lunckyCode = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_lunckyCode);
            outTime = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_outTime);
            tvBtn = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_tvBtn);
            number = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_number);


            addressName = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_address_name);
            addressPhone = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_address_phone);
            address = (CustomTextView) view.findViewById(R.id.winner_not_drying_item_layout_address);
            layoutAddress = (RelativeLayout) view.findViewById(R.id.winner_not_drying_item_layout_layoutAddress);
        }
    }

}
