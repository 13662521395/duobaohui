package com.shinc.duobaohui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.AddressListBean;
import com.shinc.duobaohui.customview.imp.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 收货地址的 adapter
 * Created by yangtianhe on 15/9/30.
 */
public class RecieveAddressAdapter extends BaseAdapter {
    private List<AddressListBean.AddressListChildBean> addressItemBeanList;
    private LayoutInflater inflater;

    //是否是删除模式
    private boolean isDelete = false;

    public RecieveAddressAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        addressItemBeanList = new ArrayList<>();
    }

    public List<AddressListBean.AddressListChildBean> getAddressItemBeanList() {
        return addressItemBeanList;
    }

    public void setAddressItemBeanList(List<AddressListBean.AddressListChildBean> addressItemBeanList) {
        this.addressItemBeanList.clear();
        this.addressItemBeanList.addAll(addressItemBeanList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return addressItemBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return addressItemBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    CollectViewHolder collectViewHolder = null;

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {


        if (view == null) {
            view = inflater.inflate(R.layout.item_recieve_address, viewGroup, false);
            collectViewHolder = new CollectViewHolder(view);
            view.setTag(collectViewHolder);
        } else {
            collectViewHolder = (CollectViewHolder) view.getTag();
        }
        if (getAddressItemBeanList().get(i).getIs_default().equals("1")) {

            collectViewHolder.cb.setChecked(true);
        } else {
            collectViewHolder.cb.setChecked(false);
        }

        if (TextUtils.isEmpty(getAddressItemBeanList().get(i).getConsignee())) {
            collectViewHolder.name.setText("");
        } else {
            collectViewHolder.name.setText(getAddressItemBeanList().get(i).getConsignee());
        }
        if (TextUtils.isEmpty(getAddressItemBeanList().get(i).getMobile())) {
            collectViewHolder.phone.setText("");
        } else {
            collectViewHolder.phone.setText(getAddressItemBeanList().get(i).getMobile());
        }
        if (TextUtils.isEmpty(getAddressItemBeanList().get(i).getAddress())) {
            collectViewHolder.address.setText("");
        } else {
            collectViewHolder.address.setText(getAddressItemBeanList().get(i).getDistrict() + " " + getAddressItemBeanList().get(i).getAddress());
        }
        collectViewHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckbox(i);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckbox(i);
            }

        });

        return view;
    }

    static class CollectViewHolder {
        private CheckBox cb;
        private CustomTextView name;
        private CustomTextView phone;
        private CustomTextView address;

        public CollectViewHolder(View view) {
            cb = (CheckBox) view.findViewById(R.id.cb_collect);
            name = (CustomTextView) view.findViewById(R.id.item_recieve_address_layout_name);
            phone = (CustomTextView) view.findViewById(R.id.item_recieve_address_layout_phone);
            address = (CustomTextView) view.findViewById(R.id.item_recieve_address_layout_address);
        }
    }

    public void setCheckbox(int i) {
        for (int j = 0; j < addressItemBeanList.size(); j++) {

            if (j == i) {

                addressItemBeanList.get(j).setIs_default("1");

            } else {
                addressItemBeanList.get(j).setIs_default("0");
            }
        }

        notifyDataSetChanged();
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public boolean isDelete() {
        return isDelete;
    }
}
