package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.RechargeRecrodBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/10/10.
 */
public class RechargeRecrodtAdapter extends BaseAdapter {

    private Activity mActivity;
    private ViewHodler viewHodler;
    private List<RechargeRecrodBean.Data> dataList;

    public List<RechargeRecrodBean.Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<RechargeRecrodBean.Data> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public RechargeRecrodtAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        dataList = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return getDataList().size();
    }

    @Override
    public Object getItem(int position) {
        return getDataList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {                                        //racharge_recrod_item_layout
            convertView = mActivity.getLayoutInflater().inflate(R.layout.racharge_recrod_item_layout, null);

            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        RechargeRecrodBean.Data data = getDataList().get(position);

        viewHodler.name.setText(data.getRecharge_channel());

        viewHodler.state.setText(data.getStatus());

        viewHodler.pay.setText(data.getAmount());

        viewHodler.time.setText(data.getCreate_time());

        return convertView;
    }

    class ViewHodler {
        CustomTextView name;
        CustomTextView time;
        CustomTextView pay;
        CustomTextView state;

        public ViewHodler(View view) {
            name = (CustomTextView) view.findViewById(R.id.racharge_recrod_item_layout_name);
            time = (CustomTextView) view.findViewById(R.id.racharge_recrod_item_layout_payCreateTime);
            pay = (CustomTextView) view.findViewById(R.id.racharge_recrod_item_layout_pay_price);
            state = (CustomTextView) view.findViewById(R.id.racharge_recrod_item_layout_item_layout_state);
        }
    }
}
