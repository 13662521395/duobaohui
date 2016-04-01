package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.ComputionalDetailsBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/10/8.
 */
public class ComputionalDetailsAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<ComputionalDetailsBean.LotteryCodeListBean> countDetailResBeans;
    ComputionalDetailAViewHolder detailAViewHolder;

    public ComputionalDetailsAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        this.countDetailResBeans = new ArrayList<>();
    }

    public List<ComputionalDetailsBean.LotteryCodeListBean> getCountDetailResBeans() {
        return countDetailResBeans;
    }

    public void setCountDetailResBeans(List<ComputionalDetailsBean.LotteryCodeListBean> countDetailResBeans) {
        this.countDetailResBeans.clear();
        this.countDetailResBeans.addAll(countDetailResBeans);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return countDetailResBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return countDetailResBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.comoutional_details_item_layout, parent, false);
            detailAViewHolder = new ComputionalDetailAViewHolder(convertView);
            convertView.setTag(detailAViewHolder);
        } else {
            detailAViewHolder = (ComputionalDetailAViewHolder) convertView.getTag();
        }
        ComputionalDetailsBean.LotteryCodeListBean resBean = countDetailResBeans.get(position);

        if (TextUtils.isEmpty(resBean.getDate_time_mic_s())) {
            detailAViewHolder.date_time_mic_s.setText("");
        } else {
            detailAViewHolder.date_time_mic_s.setText(resBean.getDate_time_mic_s());
        }
        if (TextUtils.isEmpty(resBean.getTime_mic_s())) {
            detailAViewHolder.time_mic_s.setText("");
        } else {
            detailAViewHolder.time_mic_s.setText(resBean.getTime_mic_s());
        }
        if (TextUtils.isEmpty(resBean.getNick_name())) {
            detailAViewHolder.nick_name.setText("");
        } else {
            detailAViewHolder.nick_name.setText(resBean.getNick_name());
        }

        return convertView;
    }

    class ComputionalDetailAViewHolder {
        CustomTextView date_time_mic_s;
        CustomTextView time_mic_s;
        CustomTextView nick_name;

        public ComputionalDetailAViewHolder(View v) {
            date_time_mic_s = (CustomTextView) v.findViewById(R.id.computational_details_item_layout_time);
            time_mic_s = (CustomTextView) v.findViewById(R.id.computational_details_item_layout_lunckCode);
            nick_name = (CustomTextView) v.findViewById(R.id.computational_details_item_layout_NickName);
        }
    }
}
