package com.shinc.duobaohui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.AddressListBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugaopo on 15/9/29.
 * 地址列表 适配器
 */
public class AddressListAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<AddressListBean.AddressListChildBean> listChildBeans;

    public List<AddressListBean.AddressListChildBean> getListChildBeans() {
        return listChildBeans;
    }

    public void setListChildBeans(List<AddressListBean.AddressListChildBean> listChildBeans) {
        this.listChildBeans.clear();
        this.listChildBeans.addAll(listChildBeans);
        notifyDataSetChanged();
    }

    public AddressListAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        listChildBeans = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return getListChildBeans().size();
    }

    @Override
    public AddressListBean.AddressListChildBean getItem(int position) {
        return getListChildBeans().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressItemViewHolder addressItemViewHolder;
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.address_list_layout_item, parent,false);
            addressItemViewHolder = new AddressItemViewHolder(convertView);
            convertView.setTag(addressItemViewHolder);
        } else {
            addressItemViewHolder = (AddressItemViewHolder) convertView.getTag();
        }
        AddressListBean.AddressListChildBean childBean = getListChildBeans().get(position);
        if (TextUtils.isEmpty(childBean.getConsignee())) {
            addressItemViewHolder.name.setText("");
        } else {
            addressItemViewHolder.name.setText(childBean.getConsignee());
        }
        if (TextUtils.isEmpty(childBean.getMobile())) {
            addressItemViewHolder.phone.setText("");
        } else {
            addressItemViewHolder.phone.setText(childBean.getMobile());
        }
        if (TextUtils.isEmpty(childBean.getAddress()) || TextUtils.isEmpty(childBean.getDistrict())) {
            addressItemViewHolder.address.setText("");
        } else {
            addressItemViewHolder.address.setText(childBean.getDistrict() + " " + childBean.getAddress());

        }
        return convertView;
    }

    class AddressItemViewHolder {
        CustomTextView name;
        CustomTextView phone;
        CustomTextView address;

        AddressItemViewHolder(View view) {
            name = (CustomTextView) view.findViewById(R.id.address_list_layout_item_name);
            phone = (CustomTextView) view.findViewById(R.id.address_list_layout_item_phone);
            address = (CustomTextView) view.findViewById(R.id.address_list_layout_item_address);
        }
    }
}
