package com.shinc.duobaohui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinc.duobaohui.R;
import com.shinc.duobaohui.bean.CouponsHttpResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者: efort
 * @日期: 15/12/17 - 20:20
 * @工程名: duobaohui
 * @类简介:
 */
public class CouponsAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    //数据源
    private List<CouponsHttpResultBean.CouponsItemBean> itemList;
    private CouponsItemDetailsClickListener clickListaner;
    private String couponsType = "0";

    public CouponsAdapter(Context context, List<CouponsHttpResultBean.CouponsItemBean> itemList, String couponsType) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.itemList = itemList;
        this.couponsType = couponsType;
    }

    public List<CouponsHttpResultBean.CouponsItemBean> getItemList() {
        return itemList;
    }

    public void setItemDetailsClick(CouponsItemDetailsClickListener clickListener) {
        this.clickListaner = clickListener;
    }

    /**
     * 添加一列数据到adapter 中
     *
     * @param otherItemList
     */
    public void addItemList(List<CouponsHttpResultBean.CouponsItemBean> otherItemList) {
        if (itemList == null) {
            itemList = new ArrayList<CouponsHttpResultBean.CouponsItemBean>();
        }
        itemList.addAll(otherItemList);
        notifyDataSetChanged();
    }

    /**
     * 刷新adapter 重新载入数据
     *
     * @param otherItemList
     */
    public void refreshAdapter(List<CouponsHttpResultBean.CouponsItemBean> otherItemList) {
        if (itemList == null) {
            itemList = new ArrayList<CouponsHttpResultBean.CouponsItemBean>();
        } else {
            itemList.clear();
        }
        itemList.addAll(otherItemList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyCouponsHolder myCouponsHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.coupons_item, parent, false);
            myCouponsHolder = new MyCouponsHolder(convertView);
            convertView.setTag(myCouponsHolder);
        } else {
            myCouponsHolder = (MyCouponsHolder) convertView.getTag();
        }

        myCouponsHolder.couponsMoney.setText(itemList.get(position).getPrice() + "");
        myCouponsHolder.couponsName.setText(itemList.get(position).getRed_money_name() + "");
        myCouponsHolder.couponsDeadline.setText("有效期至: " + itemList.get(position).getOverdue_time());
        myCouponsHolder.couponsValue.setText(itemList.get(position).getConsumption() + "");
        myCouponsHolder.couponsRange.setText(itemList.get(position).getActivity() + "");

        myCouponsHolder.topView.setVisibility(View.GONE);
        if (position == 0) {
            myCouponsHolder.topView.setVisibility(View.VISIBLE);
        }

        //类型先睹为快
        switch (couponsType) {
            case "0"://可用
                myCouponsHolder.isNew.setVisibility("0".equals(itemList.get(position).getIs_new_red_money()) ? View.VISIBLE : View.GONE);

                myCouponsHolder.imgView1.setVisibility(View.VISIBLE);
                myCouponsHolder.imgView2.setVisibility(View.GONE);
                myCouponsHolder.couponsMoney.setTextColor(context.getResources().getColor(R.color.c_caa40c));
                myCouponsHolder.couponsType.setVisibility(View.GONE);
                break;
            case "1"://已用
                myCouponsHolder.isNew.setVisibility(View.GONE);
                myCouponsHolder.imgView1.setVisibility(View.GONE);
                myCouponsHolder.imgView2.setVisibility(View.VISIBLE);
                myCouponsHolder.couponsMoney.setTextColor(context.getResources().getColor(R.color.c_efe4b6));
                myCouponsHolder.couponsType.setVisibility(View.VISIBLE);
                myCouponsHolder.couponsType.setText("已使用");
                break;
            case "2"://过期
                myCouponsHolder.isNew.setVisibility(View.GONE);
                myCouponsHolder.imgView1.setVisibility(View.GONE);
                myCouponsHolder.imgView2.setVisibility(View.VISIBLE);
                myCouponsHolder.couponsMoney.setTextColor(context.getResources().getColor(R.color.c_efe4b6));
                myCouponsHolder.couponsType.setVisibility(View.VISIBLE);
                myCouponsHolder.couponsType.setText("已过期");
                break;
        }
        return convertView;
    }

    class MyCouponsHolder {
        private TextView topView;

        private ImageView imgView1;
        private ImageView imgView2;
        private TextView couponsMoney;
        private TextView couponsType;

        private TextView couponsName;
        private TextView couponsDeadline;
        private TextView couponsValue;
        private TextView couponsRange;

        private ImageView isNew;

        public MyCouponsHolder(View view) {
            topView = (TextView) view.findViewById(R.id.coupons_top_view);

            imgView1 = (ImageView) view.findViewById(R.id.coupons_item_img1);
            imgView2 = (ImageView) view.findViewById(R.id.coupons_item_img2);
            couponsMoney = (TextView) view.findViewById(R.id.coupons_money);
            couponsType = (TextView) view.findViewById(R.id.coupons_type);

            couponsName = (TextView) view.findViewById(R.id.coupons_item_name);
            couponsDeadline = (TextView) view.findViewById(R.id.coupons_item_deadline);
            couponsValue = (TextView) view.findViewById(R.id.coupons_item_value);
            couponsRange = (TextView) view.findViewById(R.id.coupons_item_range);

            isNew = (ImageView) view.findViewById(R.id.coupons_item_is_new);
        }
    }

    public interface CouponsItemDetailsClickListener {
        void onItemDetailsClick(int position, String couponsId);
    }
}
